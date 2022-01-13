package runners;

import io.cucumber.junit.CucumberOptions;

@CucumberOptions(
      plugin = {"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm","json:RunReports/cucumberJson/cucumber-report.json"},
        features = {"src/test/java/featureFiles"},
        glue = {"stepsDefinitions", "hooks"}
        )

public class BaseRunner{}


//"io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm",











//import org.testng.annotations.AfterTest;
//import automation.library.cucumber.selenium.RunnerClassSequential;
//import automation.library.reporting.TextReport;
//public class BaseRunner extends RunnerClassSequential {
//
//        @AfterTest
//        public void teardown() {
//                TextReport tr = new TextReport();
//                tr.createReport(true);
//        }
//}