package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features/app_functionality",
        glue = {"steps"},
        plugin = {"pretty","html:target/cucumber-report.html"},
        monochrome = true
)
public class TestRunner {
}


