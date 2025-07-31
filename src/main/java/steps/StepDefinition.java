package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BaseSetup;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinition {

    WebDriver driver = BaseSetup.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    @Given("I open the app")
    public void i_Open_The_App() {
        driver.get("https://www.takealot.com/");
    }

    //Login with valid/invalid credentials.

    @When("I login with username {string} and password {string}")
    public void i_Login_With_Username_And_Password(String invalidUsername, String invalidPassword) throws InterruptedException {
        //Wait for page to be successfully loaded
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Login')]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));

        //Populating Invalid Credentials
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys(invalidUsername);
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(invalidPassword);

        //Click log in button
        driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
    }

    @Then("I should {string} with valid username {string} and password {string}")
    public void i_Should(String outcome, String validUsername, String validPassword) {
        switch (outcome) {
            case "See an error message":

                By errorDiv = By.xpath("/html/body/div[5]/div/div/div/div/div/div/div[1]/div/div/div[1]/form/div[1]/div/div/div/div/div[2]/div/div");
                wait.until(ExpectedConditions.visibilityOfElementLocated(errorDiv));

                String errorText = driver.findElement(errorDiv).getText();
                assertTrue(errorText.contains("Incorrect Email or Password. Please try again and ensure Caps Lock is not enabled."));

                By closeIcon = By.xpath("/html/body/div[5]/div/div/div/button");
                wait.until(ExpectedConditions.elementToBeClickable(closeIcon)).click();

                retryWithValidCredentials(validUsername, validPassword);
                break;

            case "Homepage logo ":
                //Promotion Pop
                By btnNotNow = By.xpath("/html/body/div[5]/div[2]/div[3]/button[1]");
                wait.until(ExpectedConditions.elementToBeClickable(btnNotNow)).click();

                // Wait for Homepage
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"shopfront-app\"]/div[3]/div/div/div[1]/a/img")));
                break;

            default:
                throw new IllegalArgumentException("Unexpected result: " + outcome);
        }
    }

    private void retryWithValidCredentials(String username, String password) {
        // Click login again
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Login')]"))).click();

        // Populating valid credentials
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys(username);
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(password);

        driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();

        // Wait for home page to confirm login
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"shopfront-app\"]/div[3]/div/div/div[1]/a/img")));
    }
    //Creating or Adding a new item on the Cart.
    @Given("I am logged in")
    public void i_Am_Logged_In() {

        this.i_Open_The_App();
        retryWithValidCredentials("gideon.mokgabodi@gmail.com", "B@khira88");

        try {
            Thread.sleep(2000);
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"shopfront-app\"]/div[2]/div/div/button"))).click();
        } catch (Exception e) {

            System.out.println("Cookie popup not displayed");
        }
    }

    @When("I select an Item and add it to Cart\"")
    public void i_Select_An_Item_And_Add_It_To_Cart() throws Throwable {
        try {
            Thread.sleep(3000);
            WebElement item = driver.findElement(By.xpath("//*[@id=\"carousel-0-item-2\"]/article/a"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", item);
            item.click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"shopfront-app\"]/div[5]/div[1]/div[2]/aside/div[2]/div[2]/div/div/div[2]/button"))).click();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Then("I should go to cart and see my item in the cart")
    public void i_Should_Go_To_Cart_And_See_My_Item_In_TheCart() {
        try {
            Thread.sleep(3000);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div[1]/div/div/div/div/div[2]/div/div[1]/div/div[1]/section/a"))).click();
            By cartIcon = By.xpath("//*[@id=\"cart-title\"]");
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(cartIcon));
            assertTrue(driver.findElement(cartIcon).isDisplayed(), "Shopping cart icon is not visible!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Editing 0r Deleting an exiting item on cart
    @And("I want to edit or delete an existing item in my cart")
    public void i_Want_To_Edit_Or_Delete_An_Existing_Item_In_MyCart() throws InterruptedException {
        Thread.sleep(3000);
        //Deleting item on cart
        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[@id=\"shopfront-app\"]/section[1]/div[2]/div/div[2]/ul/li[1]/ul/div[1]/div/article/div/div/div[3]/div/div[2]/div/div[2]/div/button[1]"))).click();

    }


    @Then("Click Take a Lot home page to add another item")
    public void click_Take_a_Lot_Home_Page_To_Add_Another_Item() throws InterruptedException {
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//*[@id=\"shopfront-app\"]/div[3]/div/div/div[1]/a/img"))).click();
    }
}


