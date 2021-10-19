package com.TestAutomation.TestRunner;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.*;

import com.TestAutomation.Reporting.CucumberReports;
import com.TestAutomation.Reporting.ExtentTestManager;
import com.TestAutomation.StepDef.TestBase;

import io.cucumber.testng.*;

@CucumberOptions(features = ".", glue = {
		"com.TestAutomation.StepDef" }, tags = ("@smoke")

				+ "", monochrome = true, publish = true, plugin = {
				"rerun:rerun/rerun.txt", "json:target/cucumber.json" }

)
public class TestRunner {
	private TestNGCucumberRunner testNGCucumberRunner;
	ExtentTestManager reports = new ExtentTestManager();

	@BeforeSuite(alwaysRun = true)
	public void setUpClass() throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		PropertyConfigurator.configure(System.getProperty("user.dir") + "\\resources\\log4j.properties");
		System.setProperty("org.freemarker.loggerLibrary", "none");
	}

	@SuppressWarnings("unused")
	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios", invocationCount=1)
	public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
		// the 'featureWrapper' parameter solely exists to display the feature
		// file in a test report
		testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
	}

	@DataProvider
	public Object[][] scenarios() {
		if (testNGCucumberRunner == null) {
			return new Object[0][0];
		}
		return testNGCucumberRunner.provideScenarios();
	}

	@AfterSuite(alwaysRun = true)
	public void tearDownClass() throws Exception {
		ExtentTestManager.endTest();
		testNGCucumberRunner.finish();
		CucumberReports r = new CucumberReports();
		r.generateReport();

		TestBase.writeToFile(TestBase.applicationNumberOfBTL, "Blue Trust");
		TestBase.writeToFile(TestBase.applicationNumberOfMLL, "Max Lend");

		try {
			FileUtils.deleteDirectory(new File(".\\Screenshots"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
