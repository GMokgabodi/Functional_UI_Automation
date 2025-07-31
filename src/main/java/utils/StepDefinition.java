package utils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinition {

    WebDriver driver = BaseSetup.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    @Given("I open the app")
    public void i_Open_The_App() {
        driver.get("https://www.takealot.com/");
    }

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
    public void i_Should(String outcome,String validUsername, String validPassword) {
        switch (outcome) {
            case "See an error message":

                By errorDiv = By.xpath("/html/body/div[5]/div/div/div/div/div/div/div[1]/div/div/div[1]/form/div[1]/div/div/div/div/div[2]/div/div");
                wait.until(ExpectedConditions.visibilityOfElementLocated(errorDiv));

                String errorText = driver.findElement(errorDiv).getText();
                assertTrue(errorText.contains("Incorrect Email or Password. Please try again and ensure Caps Lock is not enabled."));

                By closeIcon = By.xpath("/html/body/div[5]/div/div/div/button");
                wait.until(ExpectedConditions.elementToBeClickable(closeIcon)).click();

                retryWithValidCredentials(validUsername, validPassword );
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
}

