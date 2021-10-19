package com.TestAutomation.TestRunner;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.*;

import com.TestAutomation.Reporting.ExtentTestManager;
import com.TestAutomation.Utility.PropertiesFileReader;

import io.cucumber.testng.*;

@CucumberOptions(
		features = "@rerun/rerun.txt",
		glue= {"com.TestAutomation.StepDef"},
		monochrome = true,
		plugin= {"rerun:rerun/rerun.txt"}
	
		)
public class ReRunFailed {

	private TestNGCucumberRunner testNGCucumberRunner;
	ExtentTestManager reports=new ExtentTestManager();
	//TestBase obj2;
	@BeforeSuite(alwaysRun = true)
	public void setUpClass() throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());

		PropertyConfigurator.configure(System.getProperty("user.dir") +"\\resources\\log4j.properties");

		 System.setProperty("org.freemarker.loggerLibrary", "none");
		 
		Properties pr = new PropertiesFileReader().getProperties(System.getProperty("user.dir") + "//resources//browser-config.properties");
		 if(pr.getProperty("delete").equalsIgnoreCase("yes"))
		 {
					FileUtils.deleteDirectory(new File("./target/Screenshots"));
		 }



	}
	@SuppressWarnings("unused")
    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
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
		//CucumberReports r = new CucumberReports();
	//	r.generateReport();
	//	obj2.sendMail();
		// Created : Shivam Sharma
		try {
			FileUtils.deleteDirectory(new File("./Screenshots"));
		} catch (IOException e) {
		}
	}


}
