package com.TestAutomation.Reporting;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;



public class ExtentTestManager {
	static Map extentTestMap = new HashMap();
    static ExtentReports extent=null;
 
    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }
 
    public static synchronized void endTest() {
        extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
        //extent.flush();
        //extent.close();
    }
    
    public static synchronized void endTest(String scenario) {
        extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
        extent.flush();
        //extent.close();
    }
 
    public static synchronized ExtentTest startTest(String testName, String desc) {
    	extent = ExtentManager.getReporter();
        ExtentTest test = extent.startTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }
    
    
}
