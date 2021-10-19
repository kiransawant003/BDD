package com.TestAutomation.StepDef;

import java.util.NoSuchElementException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.TestAutomation.CustomException.SLCException;
import com.relevantcodes.extentreports.LogStatus;

public class TestUtil extends TestBase {

	public void waitTillElementIsVisible(String locator,Properties prop, int time,String error) {

		String[] OR = locator.split("_");

		String path = OR[OR.length - 1];

		try {

			if (path.equalsIgnoreCase("ID")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.id(prop.getProperty(locator.trim()))));
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(prop.getProperty(locator.trim())))));
				
			} else if (path.equalsIgnoreCase("NAME")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.name(prop.getProperty(locator.trim()))));
				new WebDriverWait(driver, time).until(
						ExpectedConditions.visibilityOf(driver.findElement(By.name(prop.getProperty(locator.trim())))));
				
			} else if (path.equalsIgnoreCase("XPATH")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(locator.trim()))));
				new WebDriverWait(driver, time).until(
						ExpectedConditions.visibilityOf(driver.findElement(By.xpath(prop.getProperty(locator.trim())))));
				
			} else if (path.equalsIgnoreCase("className")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.className(prop.getProperty(locator.trim()))));
				new WebDriverWait(driver, time).until(
						ExpectedConditions.visibilityOf(driver.findElement(By.className(prop.getProperty(locator.trim())))));
				
			} else if (path.equalsIgnoreCase("cssSelector")) {
				new WebDriverWait(driver, time).until(
						ExpectedConditions.presenceOfElementLocated(By.cssSelector(prop.getProperty(locator.trim()))));
				new WebDriverWait(driver, time).until(ExpectedConditions
						.visibilityOf(driver.findElement(By.cssSelector(prop.getProperty(locator.trim())))));
				
			} else if (path.equalsIgnoreCase("linkText")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.linkText(prop.getProperty(locator.trim()))));
				new WebDriverWait(driver, time).until(
						ExpectedConditions.visibilityOf(driver.findElement(By.linkText(prop.getProperty(locator.trim())))));
				
			} else if (path.equalsIgnoreCase("linkText")) {
				new WebDriverWait(driver, time).until(
						ExpectedConditions.presenceOfElementLocated(By.partialLinkText(prop.getProperty(locator.trim()))));
				new WebDriverWait(driver, time).until(ExpectedConditions
						.visibilityOf(driver.findElement(By.partialLinkText(prop.getProperty(locator.trim())))));
				
			} else if (path.equalsIgnoreCase("tagName")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.tagName(prop.getProperty(locator.trim()))));
				new WebDriverWait(driver, time).until(
						ExpectedConditions.visibilityOf(driver.findElement(By.tagName(prop.getProperty(locator.trim())))));
				
			} else {
				this.errorMessage = error;
				log.info(error);
				test.log(LogStatus.FAIL, error);
				throw new SLCException(error);
			}

		} catch (TimeoutException e) {
			this.errorMessage = "Time out exception occured. "+ error;
			test.log(LogStatus.FAIL, error);
			Assert.fail("Time out exception occured. "+ error);
		} catch (NoSuchElementException e) {
			this.errorMessage = "NoSuchElementException: " + error;
			test.log(LogStatus.FAIL, error);
			Assert.fail("NoSuchElementException: " + error);
		}

	}

	public void checkPresenceOfElementInDOM(String locator,Properties prop, int time,String error) {

		String[] OR = locator.trim().split("_");

		String path = OR[OR.length - 1];

		try {
			if (path.equalsIgnoreCase("ID")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.id(prop.getProperty(locator.trim()))));

			} else if (path.equalsIgnoreCase("NAME")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.name(prop.getProperty(locator.trim()))));

			} else if (path.equalsIgnoreCase("XPATH")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(locator.trim()))));

			} else if (path.equalsIgnoreCase("className")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.className(prop.getProperty(locator.trim()))));

			} else if (path.equalsIgnoreCase("cssSelector")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(prop.getProperty(locator.trim()))));

			} else if (path.equalsIgnoreCase("linkText")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.linkText(prop.getProperty(locator.trim()))));

			} else if (path.equalsIgnoreCase("linkText")) {
				new WebDriverWait(driver, time).until(
						ExpectedConditions.presenceOfElementLocated(By.partialLinkText(prop.getProperty(locator.trim()))));

			} else if (path.equalsIgnoreCase("tagName")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.presenceOfElementLocated(By.tagName(prop.getProperty(locator.trim()))));

			} else {
				this.errorMessage = "WebElement is not present in DOM";
				log.info("WebElement is not present in DOM");
				throw new SLCException("WebElement is not present in DOM");
			}
		} catch (TimeoutException e) {
			errorMessage = error;
			log.info(error);
			Assert.fail(error);
		}

	}

	public void checkVisibilityOfElementOnWebPage(String locator,Properties prop, int time,String error) {

		String[] OR = locator.split("_");

		String path = OR[OR.length - 1];

		try {
			if (path.equalsIgnoreCase("ID")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(prop.getProperty(locator.trim())))));
			} else if (path.equalsIgnoreCase("NAME")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.visibilityOf(driver.findElement(By.name(prop.getProperty(locator.trim())))));
			} else if (path.equalsIgnoreCase("XPATH")) {
				new WebDriverWait(driver, time)
						.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(prop.getProperty(locator.trim())))));
			} else if (path.equalsIgnoreCase("className")) {
				new WebDriverWait(driver, time).until(
						ExpectedConditions.visibilityOf(driver.findElement(By.className(prop.getProperty(locator.trim())))));
			} else if (path.equalsIgnoreCase("cssSelector")) {
				new WebDriverWait(driver, time).until(
						ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(prop.getProperty(locator.trim())))));
			} else if (path.equalsIgnoreCase("linkText")) {
				new WebDriverWait(driver, time).until(
						ExpectedConditions.visibilityOf(driver.findElement(By.linkText(prop.getProperty(locator.trim())))));
			} else if (path.equalsIgnoreCase("linkText")) {
				new WebDriverWait(driver, time).until(ExpectedConditions
						.visibilityOf(driver.findElement(By.partialLinkText(prop.getProperty(locator.trim())))));
			} else if (path.equalsIgnoreCase("tagName")) {
				new WebDriverWait(driver, time).until(
						ExpectedConditions.visibilityOf(driver.findElement(By.tagName(prop.getProperty(locator.trim())))));
			} else {
				this.errorMessage = "WebElement is not visible";
				log.info("WebElement is not visible");
				throw new SLCException("WebElement is not visible");
			}
		} catch (Exception e) {
			errorMessage = error;
			log.info(e.getMessage());
			Assert.fail(error);
		}

	}
	



}
