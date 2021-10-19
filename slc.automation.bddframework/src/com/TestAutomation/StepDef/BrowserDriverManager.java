package com.TestAutomation.StepDef;

import org.openqa.selenium.WebDriver;

public class BrowserDriverManager {

	private BrowserDriverManager() {

	}

	private final static ThreadLocal<WebDriver> td = new ThreadLocal<WebDriver>();
	private static BrowserDriverManager wbManager = null;

	public static BrowserDriverManager getDriverInstance() {
		if (wbManager == null) {
			wbManager = new BrowserDriverManager();
		}

		return wbManager;
	}

	public void setDriver(WebDriver driver) {
		td.set(driver);
	}

	public WebDriver getDriver() {
		return td.get();
	}

}
