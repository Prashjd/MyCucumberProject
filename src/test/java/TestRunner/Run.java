package TestRunner;
import org.junit.runner.RunWith;

import io.cucumber.testng.CucumberOptions;

//import io.cucumber.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

//@RunWith(Cucumber.class)
@CucumberOptions(
		features = {".//Features/LoginFeature.feature",".//Features/Customers.feature"},
		//".//Features/Customers.feature",
		//".//Features/LoginFeature.feature",
		//features = ".//Features/",
		glue= "StepDefinition",
		dryRun= false,
		monochrome= true,
		tags ="@Sanity",    //scenarios under @sanity tag will be executed
		//plugin = {"pretty","html:target/cucumber-reports/reports_html.html"}
		plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
		)

// "html:target/cucumber-reports/reports1.html"
// "junit:target/cucumber-reports/report_xml.xml"
//"json:target/cucumber-reports/report_json.json"

public class Run extends AbstractTestNGCucumberTests {
	// this class is empty
}
