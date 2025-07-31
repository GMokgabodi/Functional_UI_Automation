import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BaseSetup;

import java.time.Duration;

public class StepDefinition {

    WebDriver driver = BaseSetup.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    @Given("I open the app")
    public void i_Open_The_App() {
        driver.get("https://www.takealot.com/");
    }

    @When("I login with username {string} and password {string}")
    public void i_Login_With_Username_And_Password(String arg0, String arg1, String arg2, String arg3) {

    }

    @Then("I should {}")
    public void iShould(String arg0) {
    }
}
