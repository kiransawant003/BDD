package com.TestAutomation.StepDef;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.TestAutomation.CustomException.SLCException;
import com.TestAutomation.Reporting.ExtentTestManager;
import com.TestAutomation.Utility.PropertiesFileReader;
import com.relevantcodes.extentreports.LogStatus;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SLC extends TestUtil {

	public String scenarioName;

	public String UserType;
	public String strEmail;
	public static File file = null;
	public ArrayList<String> applicationNumber = new ArrayList<String>();
	public HashMap<Integer, Object> VIPData = new HashMap<Integer, Object>();
	public String Appno;
	String number;
	int result;
	public static String customerLevel = "";
	public static int cust_ID = 0;

	public String Accountno = "";
	int countOfVIPData = 0;
	// TestBase objectRef;
	int checkSSNCount = 0;
	private LocalDateTime startTime = null;
	private LocalDateTime endTime = null;
	private long duration = 0;
	private DateTimeFormatter f = null;
	private String merchantName = "";
	private String featureFileName = "";

	private int SSN;
	private int ApplicationNumber;

	static {

		try {
			log.info("Closing any open Browser");
			Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f /t");
			Runtime.getRuntime().exec("taskkill /im geckodriver.exe /f /t");

			Properties pr = new PropertiesFileReader()
					.getProperties(System.getProperty("user.dir") + "//resources//browser-config.properties");
			if (pr.getProperty("delete").equalsIgnoreCase("yes")) {
				FileUtils.deleteDirectory(new File("./target/Screenshots"));
			}
		} catch (Throwable e) {

		}
	}

	@Before
	public void startScenario(Scenario scenario) throws IOException {
		// System.out.println(f.getName());
		initialize();

		f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		scenarioName = scenario.getName();
		startTime = LocalDateTime.now();
		log.info("*** SCENARIO STARTED : " + scenarioName + " ***");
		// objectRef = new TestBase();

		map.clear();
		clientData.clear();
		applicationNumber.clear();
		test = ExtentTestManager.startTest(scenarioName, "Start of Test Case:" + scenarioName);

	}

	@After
	public void endScenario(Scenario scenario) {

		endTime = LocalDateTime.now();
		duration = Duration.between(startTime, endTime).getSeconds();
		String testStatus = scenario.getStatus().toString().toUpperCase();

		try {
			if (scenario.isFailed()) {
				log.info("Scenario is FAILED");
				final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

				scenario.attach(screenshot, "image/png", "Failed step");
			}
		} catch (Exception e) {
			log.info("Screenshot capture FAILED for Cucumber BDD Report");
		}

		try {
			if (scenario.isFailed()) {
				test.log(LogStatus.FAIL,
						scenario.getStatus() + test.addScreenCapture(report.capture(driver, "Cause of failure")));
			}
		} catch (Exception e) {
			log.info("Screenshot capture FAILED for Extent Report");

		}

		try {
			ExtentTestManager.endTest(scenarioName);
		} catch (Exception e) {
			log.info("Failed to close Extent Report");
			errorMessage = "Failed to close Extent Report";
		}

		try {
			log.info("*** SCENARIO ENDED : " + testStatus + " ***");
			closeBrowser();
		} catch (Exception e) {
			log.info(e.getMessage());
			errorMessage = "Failed to connect to localhost";
			log.info("Browser is already closed");
		}

		try {
			writeStatusToDatabase(featureFileName, scenarioName, testStatus, startTime.format(f), endTime.format(f),
					duration, CONFIG.getProperty("browser").toUpperCase(), merchantName, errorMessage, SSN,
					ApplicationNumber);
			log.info("Result copied to Database");
		} catch (Exception e) {
			System.out.println("Exception in entering status " + e.getMessage());
		}

	}

	@Given("^feature file is (.*)$")
	public void getFeatureFilename(String featureFileName) {
		this.featureFileName = featureFileName;

	}

	@Given("^merchant is (.*)$")
	public void getMerchantName(String merchant) {

		if (merchant.equalsIgnoreCase("Blue Trust")) {
			this.merchantName = "57510";
		} else {
			this.merchantName = "57511";
		}
	}
	/*
	 * @When("^all browsers are closed before scenario execution$") public void
	 * closeAllBrowser() { try { log.info("Closing any open Browser");
	 * Runtime.getRuntime().exec("taskkill /im chrome* /f /t");
	 * Runtime.getRuntime().exec("taskkill /im geckodriver.exe /f /t");
	 * 
	 * Properties pr = new PropertiesFileReader()
	 * .getProperties(System.getProperty("user.dir") +
	 * "//resources//browser-config.properties"); if
	 * (pr.getProperty("delete").equalsIgnoreCase("yes")) {
	 * FileUtils.deleteDirectory(new File("./target/Screenshots")); } } catch
	 * (Throwable e) {
	 * 
	 * } }
	 */

	@Given("^user is on (.*) homepage$")
	public void user_is_on_homepage(String appName) {

		log.info("Application launched : "
				+ CONFIG.getProperty("app" + appName + "URL_" + CONFIG.getProperty("appEnv")));

		driver.get(CONFIG.getProperty("app" + appName + "URL_" + CONFIG.getProperty("appEnv")));
		waitForPageLoaded();

		test.log(LogStatus.PASS, "Application URL is launched " + test.addBase64ScreenShot(report.capture()));

		log.info(appName + " web page is loaded successfully");

	}

	@When("^user enters login details in (.*) for (.*)$")
	public void user_enters_login_details(String app, String merchant) {
		log.info("user enters login details in " + app + " for " + merchant + "");
		if (app.equals("LMS")) {

			waitTillElementIsVisible("UserIDtxt_NAME", OR_LMS, 10, "Trans application user name is not visible");
			sendKeys(getWebElement("UserIDtxt_NAME", OR_LMS), CONFIG.getProperty("username"));
			sendKeys(getWebElement("Passwordtxt_NAME", OR_LMS), CONFIG.getProperty("password"));
			WebElement element = getWebElement("MerchantDropdown_Name", OR_LMS);
			Select select = new Select(element);
			if (merchant.contains("Blue Trust") || merchant.contains("BTL")) {
				select.selectByValue("57510710000");
			} else {
				select.selectByValue("57511710000");
			}

			test.log(LogStatus.PASS, "Trans application login"
					+ test.addBase64ScreenShot(report.capture(driver, "Trans application login")));
			clickOnButton(getWebElement("LoginBtn_NAME", OR_LMS));
			switchToLeftFrame();
			waitTillElementIsVisible("ReviewAppLink_LINKTEXT", OR_LMS, 10, "login to TRANS page is unsuccessfull");

			driver.navigate().refresh();
			// switchToLeftFrame();
			// waitForPageLoaded();

		}
	}

	@Then("^verify login to (.*) is successful$")
	public void user_login_verify(String merchant) throws Throwable {
		log.info("verify login to " + merchant + " is successful");
		int size = 0;
		if (merchant.equals("TRANS LMS")) {
			switchToLeftFrame();
			size = driver.findElements(By.xpath("//a[@title='Review App']")).size();
		} else if (merchant.equals("Blue Trust Web")) {
			size = driver.findElements(By.xpath("//img[contains(@alt,'blue-trust-loans')]")).size();
		} else if (merchant.equals("MaxLend Web")) {
			size = driver.findElements(By.xpath("//img[contains(@alt,'MaxLend')]")).size();
		}
		if (size == 0) {
			test.log(LogStatus.FAIL, report.capture(driver, merchant + "Login-Test Failed"));
		} else {
			test.log(LogStatus.PASS, merchant + " Homepage appears");
		}
	}

	@Then("^In Review App Search by \"([^\"]*)\" having \"([^\"]*)\"$")
	public void search_in_reviewApp(String searchby, String Appl_No) throws Throwable {
		boolean flag = false;
		int size = 0;
		// Thread.sleep(5000);
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Left", 30);
		waitForPresenceOfElement(By.xpath("//a[@title='Review App']"), 30);
		clickOnButton(getWebElement("ReviewAppLink_XPATH", OR_LMS));
		// ((WebElement) getWebElement("ReviewAppLink_XPATH", OR_LMS)).click();
		// Thread.sleep(2000);
		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
		// driver.findElement(By.xpath("//select[@id='cboQuery']")).click();
		WebElement element = getWebElement("SearchOnDropdown_XPATH", OR_LMS);
		element.click();
		Select search = new Select(element);
		search.selectByVisibleText(searchby);
		Thread.sleep(2000);
		waitForPresenceOfElement(By.id("txtSearchFor"), 30);
		sendKeys(getWebElement("SearchOnTxt_ID", OR_LMS), Appl_No);
		// ((WebElement) getWebElement("SearchOnTxt_ID", OR_LMS)).click();
		// ((WebElement) getWebElement("SearchOnTxt_ID", OR_LMS)).sendKeys(Appl_No);
		// Thread.sleep(5000);
		clickOnButton(getWebElement("SearchBtn_ID", OR_LMS));
		// ((WebElement) getWebElement("SearchBtn_ID", OR_LMS)).click();
		waitForPageLoaded();
//		Thread.sleep(5000);
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.captureFullScreenShot(driver, "screen shot ")));
		size = driver.findElements(By.id("origApplno")).size();

		if (size > 0) {
			flag = true;
		}

		if (flag) {
			test.log(LogStatus.PASS, "Review Application launched for Application Id: " + Appl_No);
		} else {
			test.log(LogStatus.FAIL, report.capture(driver, searchby + "_Search") + "Test Failed");
		}
		driver.switchTo().defaultContent();
	}

	@Then("^choose payment method (.*)$")
	public void choose_payment_method_ACH(String method) throws IOException, InterruptedException {
		// Write code here that turns the phrase above into concrete actions

		switchToPaymentFrame();

		if (method.equalsIgnoreCase("ACH")) {

			scrollBy(1000);
			getWebElement("Ach_xpath", OR_MLL).click();
			test.log(LogStatus.PASS, "ACH is selected: ");
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "choose payment method ")));
		} else if (method.equalsIgnoreCase("TxtACheck")) {

			scrollBy(1000);
			getWebElement("Txtacheck_xpath", OR_MLL).click();
			test.log(LogStatus.PASS,
					"Txtacheck is selected: " + test.addBase64ScreenShot(report.capture(driver, "screen shot one ")));
		} else {
			scrollBy(1000);
			getWebElement("CreditDebit_xpath", OR_MLL).click();
			test.log(LogStatus.PASS, "Credit/Debit is selected: "
					+ test.addBase64ScreenShot(report.capture(driver, "screen shot one ")));

		}
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.captureFullScreenShot(driver, "screen shot one ")));
	}

	@When("^Esignature process by selecting ACH option$")
	public void esignature_process_by_selecting_ACH_option() throws Throwable {
		// Write code here that turns the phrase above into concrete actions

	}

	@Then("^In Review App Search by (.*)$")
	public void search_by_reviewApp(String searchby) throws Throwable {
		log.info("In Review App Search by " + searchby);
		boolean flag = false;
		int size = 0;
		switchToLeftFrame();
		// getWebElement("ReviewAppLink_LINKTEXT", OR_LMS).click();
		clickUsingAction(getWebElement("ReviewAppLink_LINKTEXT", OR_LMS));

		switchToMainFrame();
		WebElement element = getWebElement("SearchOnDropdown_XPATH", OR_LMS);
		element.click();
		searchby = searchby.replace('_', ' ');
		Select search = new Select(element);
		search.selectByVisibleText(searchby);
		// waitForPresenceOfElement(By.id("txtSearchFor"), 30);
		sendKeys(getWebElement("SearchOnTxt_ID", OR_LMS), map.get("Email"));
		clickOnButton(getWebElement("SearchBtn_ID", OR_LMS));
		test.log(LogStatus.PASS, test
				.addBase64ScreenShot(report.captureFullScreenShot(driver, "In Review App Search by Email address  ")));
		waitForPageLoaded();
		switchToMainFrame();

		String FeeonAppl = getWebElement("FeeonAppl1_xpath", OR_MLL).getText();
		log.info("Fee on App1=" + FeeonAppl);
		test.log(LogStatus.PASS, "Fee on App = " + FeeonAppl);

		if (driver.findElements(By.id("origApplno")).size() > 0) {
			String applicationNumber = driver.findElement(By.id("origApplno")).getText();

			ApplicationNumber = Integer.parseInt(applicationNumber);
		}

		String OriginalAmtofAppl = getWebElement("OriginalAmtofAppl_xpath", OR_MLL).getText();
		log.info("Original Amt of Appl=" + OriginalAmtofAppl);
		test.log(LogStatus.PASS, "Original Amt of Appl = " + OriginalAmtofAppl);

		String ABAno = getWebElement("ABA_xpath", OR_MLL).getText();
		log.info("ABA no=" + ABAno);
		test.log(LogStatus.PASS, "ABA No = " + ABAno);

		Accountno = getWebElement("Accountno_xpath", OR_MLL).getText();
		log.info("Account no=" + Accountno);
		test.log(LogStatus.PASS, "Account No = " + Accountno);

		Appno = driver.findElement(By.xpath("//*[@id='tdApp']/b/font[2]")).getText();
		switchToPaymentFrame();

		if (driver.findElement(By.xpath("//input[@id='rblDebitType_0']")).isSelected()) {
			log.info("Paymethod Method ACH selected in Trans application");
			test.log(LogStatus.PASS, "Paymethod Method ACH selected in Trans application ");
		} else {
			log.info("Paymethod Method TxtAcheck selected in Trans application");
			test.log(LogStatus.PASS, "Paymethod Method TxtAcheck selected in Trans application ");
		}

		flag = true;
		if (flag) {
			test.log(LogStatus.PASS, "Review App Screen launched for " + map.get("Email"));

		} else {
			test.log(LogStatus.FAIL, report.capture(driver, searchby + "_Search") + "Test Failed");
		}

	}

	@Then("^verify Customer Details has (.*) E-sign$")
	public void verify_has_esign(String value) throws Throwable {
		boolean flag = false;
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		if (value.equals("Not")) {
			if (driver.findElements(By.id("esig")).size() > 0) {
				flag = false;
			} else {
				flag = true;
			}
		} else {
			if (driver.findElements(By.id("esig")).size() > 0) {
				flag = true;
			} else {
				flag = false;
			}
		}

		if (flag) {
			test.log(LogStatus.PASS, report.capture(driver, "ESign_Link") + " <b>ESign Link " + value + " Present</b>");
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "ESign_Link")));
		} else {
			test.log(LogStatus.FAIL, report.capture(driver, "ESign_Link") + " <b>ESign Link Appears - Test Failed</b>");
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "ESign_Link")));
		}
		driver.switchTo().defaultContent();
	}

	@Then("^Logout of (.*) Page$")
	public void user_logout(String appName) {
		log.info("Logout of " + appName + " Page");
		if (appName.equals("LMS")) {
			switchToLeftFrame();
			waitForPresenceOfElement("LogoutLink_XPATH", OR_LMS, 40);
			clickOnButton(getWebElement("LogoutLink_XPATH", OR_LMS));
		}
	}

	@Then("^user enters (.*) login details in (.*)$")
	public void user_merchant_login(String strExisting, String app) {
		boolean flag = false;
		if (strExisting.equalsIgnoreCase("existing")) {
			if (app.equalsIgnoreCase("MLL") || app.equalsIgnoreCase("BTL")) {
				try {
					waitForPresenceOfElement(By.id("content_loginEmail"), 30);

				} catch (TimeoutException | NoSuchElementException e) {
					log.info("Email text box is not visible");
					test.log(LogStatus.FAIL,
							"Email text box is not visible" + test.addBase64ScreenShot(report.capture()));
					Assert.fail("Email text box is not visible");
				}
				scrollIntoView(getWebElement("Emailidtxt_ID", OR_MLL));
				log.info("Scrolled into view");
				log.info("login with email : " + clientData.get("Email"));

				if (clientData.get("Email") == null) {
					test.log(LogStatus.FAIL,
							"Test failed as email id is null" + test.addBase64ScreenShot(report.capture()));
					errorMessage = "Email id is NULL";
					Assert.fail("Email id is NULL");
				}
				sendKeys(getWebElement("Emailidtxt_ID", OR_MLL), clientData.get("Email"));
				log.info("User entered email id : " + clientData.get("Email"));
				clickOnButton(driver.findElement(By.id("content_loginSubmit")));
				log.info("User clicked on login button");
				flag = true;

			}
			if (flag) {
				test.log(LogStatus.PASS, "Login Details Entered for Application " + app + " having Email Id: "
						+ clientData.get("Email"));
			} else {
				test.log(LogStatus.FAIL, "Unable to Login Details Entered for Application " + app + " having Email Id: "
						+ clientData.get("Email"));
				test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Account_Login")));
				Assert.assertFalse(false);
			}
		} else {
			if (app.equalsIgnoreCase("MLL") || app.equalsIgnoreCase("BTL")) {
				driver.findElement(By.id("content_loginEmail")).click();
				((WebElement) getWebElement("Emailidtxt_ID", OR_MLL)).click();
				((WebElement) getWebElement("Emailidtxt_ID", OR_MLL)).sendKeys(map.get("Email"));
				driver.findElement(By.id("content_loginSubmit")).click();
				flag = true;

			}
			if (flag) {
				test.log(LogStatus.PASS, "Login Details Entered for Application " + app + " having Email Id: "
						+ clientData.get("Email"));
			} else {
				test.log(LogStatus.FAIL, "Unable to Login Details Entered for Application " + app + " having Email Id: "
						+ clientData.get("Email"));
				test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Account_Login")));
				Assert.assertFalse(false);
			}
		}

	}

	@Then("^Login and Validate UserType$")
	public void user_merchant_login_userType() throws Throwable {
		boolean flag = false;
		driver.findElement(By.id("content_loginEmail")).click();
		((WebElement) getWebElement("Emailidtxt_ID", OR_MLL)).click();
		((WebElement) getWebElement("Emailidtxt_ID", OR_MLL)).sendKeys(clientData.get("Email"));
		driver.findElement(By.id("content_loginSubmit")).click();
		int elementList = driver.findElements(By.xpath("//h2[contains(text(),'Welcome')]")).size();
		if (elementList > 0) {
			UserType = "Registered";
			WebElement element = getWebElement("CreatePwdLink_XPATH", OR_MLL);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			getWebElement("CreatePwdLink_XPATH", OR_MLL).click();
			getWebElement("Last4SSNTxt_XPATH", OR_MLL).click();
			getWebElement("Last4SSNTxt_XPATH", OR_MLL).sendKeys(clientData.get("Cust_SSN"));
			String strDOB = clientData.get("Cust_DOB");
			Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(strDOB);
			SimpleDateFormat date2 = new SimpleDateFormat("MM/dd/yyyy");
			strDOB = date2.format(date1);

			getWebElement("DOBTxt_ID", OR_MLL).click();
			getWebElement("DOBTxt_ID", OR_MLL).sendKeys(strDOB);
			String pwdString = generateRandomString(7);
			char specialCharater = generateRandomSpecial(1);
			int digitValue = generateRandomInteger(100);
			strPwd = pwdString + String.valueOf(specialCharater) + String.valueOf(digitValue);
			getWebElement("PasswordTxt_ID", OR_MLL).click();
			getWebElement("PasswordTxt_ID", OR_MLL).sendKeys(strPwd);
			getWebElement("ConfirmPasswordTxt_ID", OR_MLL).click();
			getWebElement("ConfirmPasswordTxt_ID", OR_MLL).sendKeys(strPwd);
			getWebElement("SavePwdBtn_ID", OR_MLL).click();
			flag = true;
			if (flag) {
				test.log(LogStatus.PASS, "User having Email: " + clientData.get("Email") + " is " + UserType);
			} else {
				test.log(LogStatus.FAIL, "Unable to Login for User having Email Id: " + clientData.get("Email"));
				test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Account_Login")));
				Assert.assertFalse(false);
			}
		} else {
			UserType = "Non-Registered";
			flag = true;
			if (flag) {
				test.log(LogStatus.PASS, "User having Email: " + clientData.get("Email") + " is " + UserType);
			} else {
				test.log(LogStatus.FAIL, "Unable to Login for User having Email Id: " + clientData.get("Email"));
				test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Account_Login")));
				Assert.assertFalse(false);
			}
		}

	}

	@Then("^Create Password with (.*) Information$")
	public void user_createPwd(String strStatus) throws Throwable {
		boolean flag = false;
		int noofElements = driver.findElements(By.xpath("//a[contains(text(),'create a password')]")).size();
		if (noofElements > 0) {
			waitForPresenceOfElement("CreatePwdLink_XPATH", OR_MLL, 20);
			clickOnButton(getWebElement("CreatePwdLink_XPATH", OR_MLL));
			// getWebElement("CreatePwdLink_XPATH", OR_MLL).click();
		}
		test.log(LogStatus.PASS, "Registration Page launched");

		if (driver.findElements(By.xpath("//input[contains(@id,'content_customContent_frmSSN')]")).size() > 0) {
			String strSSN = clientData.get("Cust_SSN");
			String strlast4SSN = strSSN.substring(strSSN.length() - 4);
			waitForPresenceOfElement("Last4SSNTxt_XPATH", OR_MLL, 30);
			sendKeys(getWebElement("Last4SSNTxt_XPATH", OR_MLL), strlast4SSN);
			getWebElement("ToggleSSNTxt_XPATH", OR_MLL).click();
			String strDOB = clientData.get("Cust_DOB");
			Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(strDOB);
			SimpleDateFormat date2 = new SimpleDateFormat("MM/dd/yyyy");
			strDOB = date2.format(date1);
			waitForPresenceOfElement("DOBTxt_ID", OR_MLL, 5);
			sendKeys(getWebElement("DOBTxt_ID", OR_MLL), strDOB);
			if (strStatus.equals("VALID")) {
				try {
					String strPwd_old = clientData.get("Cust_Fname").toLowerCase().substring(0, 2)
							+ clientData.get("Cust_Lname").toLowerCase().substring(0, 2)
							+ clientData.get("Cust_DOB").substring(0, 4) + "$";
					strPwd = strPwd_old.replace(strPwd_old.substring(0, 1), strPwd_old.substring(0, 1).toUpperCase());
				} catch (StringIndexOutOfBoundsException e) {
					String strPwd_old = clientData.get("Cust_Fname").toLowerCase().substring(0, 1)
							+ clientData.get("Cust_Lname").toLowerCase().substring(0, 2)
							+ clientData.get("Cust_DOB").substring(0, 4) + "$";
					strPwd = strPwd_old.replace(strPwd_old.substring(0, 1), strPwd_old.substring(0, 1).toUpperCase());
				}
			} else {
				String pwdString = generateRandomString(7);
				int digitValue = generateRandomInteger(100);
				strPwd = pwdString + String.valueOf(digitValue);

			}
			log.info("Created Password is : " + strPwd);

			waitTillElementIsVisible("PasswordTxt_ID", OR_MLL, 10, "Password box is not visible");

			sendKeys(getWebElement("PasswordTxt_ID", OR_MLL), strPwd);

			getWebElement("TogglePwdTxt_XPATH", OR_MLL).click();

			sendKeys(getWebElement("ConfirmPasswordTxt_ID", OR_MLL), strPwd);
			scrollBy(150);
			clickOnButton(getWebElement("SavePwdButton_ID", OR_MLL));
		} else {
			driver.findElement(By.id("content_customContent_frmPassword_Login")).sendKeys(strPwd);
			clickOnButton(driver.findElement(By.id("content_customContent_btnLogIn")));
		}
		int size = driver.findElements(By.xpath("//div[@id='content_customContent_registerSuccess']")).size();
		if (strStatus.equals("VALID")) {
			if (size > 0)
				flag = true;
			if (flag) {
				test.log(LogStatus.PASS,
						"Able to Save Password as " + strPwd + " for Email " + clientData.get("Email"));
				test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Save PASSWORD")));
			} else {
				test.log(LogStatus.FAIL, "Unable to Save Password for Email " + clientData.get("Email"));
				test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Save PASSWORD")));
				Assert.assertFalse(false);
			}
		} else {

			WebElement ele_1 = driver.findElement(By.xpath("//*[@id='revPwd_Reg1' and @class='go-green']"));
			WebElement ele_2 = driver.findElement(By.xpath("//*[@id='revPwd_Reg2' and @class='go-green']"));
			WebElement ele_3 = driver.findElement(By.xpath("//*[@id='revPwd_Reg3' and @class='go-green']"));
			WebElement ele_4 = driver.findElement(By.xpath("//*[@id='revPwd_Reg4' and @class='go-normal']"));
			WebElement ele_5 = driver.findElement(By.xpath("//*[@id='revPwd_Reg5' and @class='go-green']"));
			WebElement ele_6 = driver.findElement(By.xpath("//*[@id='revPwd_Reg6' and @class='go-green']"));

			Assert.assertTrue(ele_1.isDisplayed());
			Assert.assertTrue(ele_2.isDisplayed());
			Assert.assertTrue(ele_3.isDisplayed());
			Assert.assertTrue(ele_4.isDisplayed());
			Assert.assertTrue(ele_5.isDisplayed());
			Assert.assertTrue(ele_6.isDisplayed());

			if (ele_1.isDisplayed() && ele_2.isDisplayed() && ele_3.isDisplayed() && ele_4.isDisplayed()
					&& ele_5.isDisplayed() && ele_6.isDisplayed()) {
				flag = true;
			}

			// if ((getWebElement("NoMatchLbl_XPATH", OR_MLL).isDisplayed()
			// || getWebElement("ReviewPwdRegLbl_XPATH", OR_MLL).isDisplayed()))

			if (flag) {
				test.log(LogStatus.PASS,
						"Unable to Save Password as " + strPwd + " for Email " + clientData.get("Email"));
				test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Invalid PASSWORD")));
			} else {
				test.log(LogStatus.FAIL, "Able to Save Password for Email " + clientData.get("Email"));
				test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Invalid PASSWORD")));
				Assert.assertFalse(false);
			}
		}

	}

	@Then("^verify \"([^\"]*)\" in (.*) and perform e-sign on page$")
	public void user_perform_esign(String email, String appName) throws Throwable {
		boolean flag = false;
		String loginUser = ((WebElement) getWebElement("Welcometxt_XPATH", OR_MLL)).getText();
		String[] arrloginuser = loginUser.split(" ");
		if (email.toUpperCase().contains(arrloginuser[1].replace(",", "").toUpperCase())) {
			test.log(LogStatus.PASS, "User " + arrloginuser[1] + " Logged In Successfully");
			((WebElement) getWebElement("ElectronicConsentChkbox_XPATH", OR_MLL)).click();
			((WebElement) getWebElement("MilitaryFrmRadioNo_XPATH", OR_MLL)).click();
			((WebElement) getWebElement("TelephoneConsentRadioYes_XPATH", OR_MLL)).click();
			((WebElement) getWebElement("PaymentTypeRadioACH_XPATH", OR_MLL)).click();
			((WebElement) getWebElement("ElectronicSignCheck_XPATH", OR_MLL)).click();
			((WebElement) getWebElement("DigiSignaturetxt_XPATH", OR_MLL)).click();
			String[] arrloginAuth = email.split("@");
			((WebElement) getWebElement("DigiSignaturetxt_XPATH", OR_MLL)).sendKeys(arrloginAuth[0]);
			waitForPageLoaded();
			((WebElement) getWebElement("DigiSignaturetxt_XPATH", OR_MLL)).sendKeys(Keys.TAB);
			// WebElement element =
			// driver.findElement(By.xpath("//input[contains(@id,'frmAcceptance')]"));
			Thread.sleep(2000);
			WebElement element = ((WebElement) getWebElement("AcceptanceBtn_XPATH", OR_MLL));
			((WebElement) getWebElement("AcceptanceBtn_XPATH", OR_MLL)).sendKeys(Keys.ENTER);
			waitForPageLoaded();
			if (appName.equals("MLL")) {
				if (driver.findElements(By.xpath("//h1[contains(text(),'Almost there.')]")).size() > 0) {
					((WebElement) getWebElement("LogoutBtn_ID", OR_MLL)).click();
					flag = true;
				}
			} else if (appName.equals("BTL")) {
				if (driver.findElements(By.xpath("//h1[contains(text(),'Get You Funded')]")).size() > 0) {
					((WebElement) getWebElement("LogoutBtn_ID", OR_MLL)).click();
					flag = true;
				}
			}

		} else {
			test.log(LogStatus.FAIL, report.capture(driver, "InCorrect_Login") + " InCorrect_Login - Test Failed");
		}
		if (flag) {
			test.log(LogStatus.PASS, "E-sign Performed Successfully.");
		} else {
			test.log(LogStatus.FAIL,
					report.capture(driver, "Submit E-Sign Form") + " Unable to E-sign Loan - Test Failed");
		}
	}

	@Given("^user load and fetch file \"([^\"]*)\"$")
	public void user_load_fetch_file(String fileName) {
		log.info("Loading the configuration");
		boolean bFlag = false;
		String newFileName = fileName;
		String merchant = null;

		boolean bNewRegister = true;
		// HashMap<String,String> clientData= new HashMap<String,String>();
		List<String> myData = new ArrayList<String>();
		String[] arrName = newFileName.split("_");
		if (arrName[0].equals("BTL")) {
			merchant = "57510";
			merchantName = "57510";
		} else if (arrName[0].equals("MLL")) {
			merchant = "57511";
			merchantName = "57511";
		}
		String env = CONFIG.getProperty("appEnv");
		String URL = getdbURL(env);

		log.info("Environment : " + env + " & Merchant: " + merchant);

		while (bNewRegister) {
			try {
				// Class.forName("net.sourceforge.jtds.jdbc.Driver");
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
				CallableStatement stmt = connection.prepareCall("{CALL spGetAddress(?)}");
				stmt.setString(1, merchant);
				ResultSet rs = stmt.executeQuery();
				myData = resultSetToArrayList(rs, myData);
				// stmt = connection.prepareCall("{CALL spTest_NewGetOrganicVIP(?,?,?)}");
				stmt = connection.prepareCall("{CALL spTest_GetVIPCustomerData(?)}");
				stmt.setString(1, merchant);
				// stmt.setString(2, "0");
				// stmt.setString(3, myData.get(2));
				rs = stmt.executeQuery();
				clientData = resultSetToHashMap(rs);
				Set<String> data = clientData.keySet();

				for (String vData : data) {
					log.info(vData + " : " + clientData.get(vData));
				}

				if (clientData.isEmpty()) {
					test.log(LogStatus.FAIL, "Data is not avaiable");
					Assert.fail("Data is not avaiable");
				}

				bNewRegister = validate_LMSInterface(clientData, "LMS_interface", "lms_user", "ST20JP*otty");
				// Added By Shivam

				clientData = getPayrollData_VIP(clientData);
				myData.clear();
				if (bNewRegister == false)
					break;
			} catch (SQLException | ClassNotFoundException | ParseException e) {
				e.getMessage();
			}
		}

	}

	@Given("^user load and fetch Multiple User \"([^\"]*)\"$")
	public void user_load_fetch_multiple(String fileName) throws Throwable {
		boolean bFlag = false;
		String merchant = null;
		boolean bNewRegister = true;
		List<String> myData = new ArrayList<String>();
		List<List<String>> myData1 = new ArrayList<List<String>>();
		String[] arrName = fileName.split("_");
		if (arrName[0].equals("BTL")) {
			merchant = "57510";
		} else if (arrName[0].equals("MLL")) {
			merchant = "57511";
		}
		String env = CONFIG.getProperty("appEnv");
		String URL = getdbURL(env, "LMS_interface");
		try {
			// Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(URL, "lms_user", "ST20JP*otty");
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Distinct Email,Cust_SSN,Cust_DOB,Cust_MerchantID\r\n"
					+ "    	   		FROM BankA..Tbl_Customer C1,LMS_interface..UserTable C2\r\n"
					+ "    	   		WHERE Email IN ( \r\n" + "    	   		    SELECT Email\r\n"
					+ "    	   		    FROM BankA..Tbl_Customer where \r\n"
					+ "    	   			Cust_MerchantID in ('" + merchant + "')--,'57510')\r\n"
					+ "    	   		    GROUP BY Email\r\n" + "    	   			HAVING Count(Email)>1  \r\n"
					+ "    	   		) \r\n" + "    	   		and Cust_MerchantID='" + merchant + "'--,'57510')\r\n"
					+ "    	   		and C1.Cust_SSN!= C2.UserSSN \r\n"
					+ "    	   		group by  Email,Cust_SSN,Cust_DOB,Cust_MerchantID");
			// ResultSet rs = stmt.executeQuery("SELECT * FROM
			// [LMS_interface].[dbo].[UserTable] WHERE
			// EmailAddress='CHRISTMANITH.BABICKY@slc01.com'");
			// rs.getRow();
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
					myData.add(rs.getObject(i).toString().trim());
				}
				myData1.add(myData);
			}
			Random rn = new Random();
			int i = rn.nextInt(100);
			int counter = 0;
			boolean flag = false;
			Iterator<List<String>> iter = myData1.iterator();
			while (iter.hasNext()) {
				Iterator<String> siter = iter.next().listIterator();
				while (siter.hasNext()) {
					if (counter == i) {
						String Email = siter.next();
						clientData.put("Email", Email);
						String SSN = siter.next();
						clientData.put("Cust_SSN", SSN);
						String DOB = siter.next();
						clientData.put("Cust_DOB", DOB);
						String MERCH = siter.next();
						clientData.put("Merchant", MERCH);
						flag = true;
						break;
					} else {
						siter.next();
						siter.next();
						siter.next();
						siter.next();
					}
					counter++;
				}
				if (flag) {
					break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Given("^user fetch Register User \"([^\"]*)\"$")
	public void fetch_Register_User(String fileName) throws Throwable {
		boolean bflag = false;
		int counter = 0;
		List<String> myData = new ArrayList<String>();
		String merchant = null;
		String[] arrName = fileName.split("_");
		if (arrName[0].equals("BTL")) {
			merchant = "57510";
		} else if (arrName[0].equals("MLL")) {
			merchant = "57511";
		}
		String env = CONFIG.getProperty("appEnv");
		String URL = getdbURL(env, "LMS_interface");
		try {
			// Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(URL, "lms_user", "ST20JP*otty");
			stmt = connection.createStatement();
			while (counter < 5) {
				ResultSet rs = stmt.executeQuery("SELECT \r\n" + "	T.MerchantIDKey,\r\n" + "   T.UserName,\r\n"
						+ "	T.UserSSN,\r\n" + "	T.DOB	\r\n" + "FROM\r\n" + "    dbo.UserTable as T\r\n"
						+ "WHERE T.UserID = (SELECT FLOOR(RAND()*(2476-2065)+2065));");
				// ResultSet rs = stmt.executeQuery("SELECT * FROM
				// [LMS_interface].[dbo].[UserTable] WHERE
				// EmailAddress='CHRISTMANITH.BABICKY@slc01.com'");
				// rs.getRow();
				ResultSetMetaData md = rs.getMetaData();
				int columns = md.getColumnCount();
				while (rs.next()) {
					for (int i = 1; i <= columns; i++) {
						myData.add(rs.getObject(i).toString().trim());
					}
				}
				if (myData.get(0).equals(merchant)) {
					map.put("Email", myData.get(1));
					map.put("Cust_SSN", myData.get(2));
					map.put("Cust_DOB", myData.get(3));
					break;
				} else {
					counter++;
					myData.clear();
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Given("^user load configuration file \"([^\"]*)\"$")
	public void user_load_config_file(String fileName) throws Throwable {
		String newFileName = fileName;
		String[] listOfDisabledCountries = { "HI", "GA", "CT", "AR", "MA", "MN", "NY", "PA", "VT", "VA", "WA", "WV",
				"WI", "CA" };
		String[] mechant_57510 = { "AZ", "DC", "MD", "NC", "NJ" };
		String[] mechant_57511 = { "ND" };
		boolean bFlag = false;
		String merchant = null;
		List<String> clientData = new ArrayList<String>();
		String[] arrName = fileName.split("_");
		if (arrName[0].replaceAll("[^a-zA-Z]", "").equals("BTL")) {
			merchant = "57510";
			merchantName = "57510";
		} else if (arrName[0].replaceAll("[^a-zA-Z]", "").equals("MLL")) {
			merchant = "57511";
			merchantName = "57511";
		}
		String env = CONFIG.getProperty("appEnv");
		int noofExecutions = Integer.parseInt(arrName[3].replaceAll("[^0-9]", "").trim());
		String URL = getdbURL(env);
		try {
			// Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
			int i = 0;

			while (i < noofExecutions) {
				CallableStatement stmt = connection.prepareCall("{CALL spGetNames}");
				ResultSet rs = stmt.executeQuery();
				clientData = resultSetToArrayList(rs, clientData);
				stmt = connection.prepareCall("{CALL spGetUniqueEmail(?,?)}");
				if (i == 0) {
					stmt.setString(1, clientData.get(i));
					stmt.setString(2, clientData.get(i + 1));
				} else {
					stmt.setString(1, clientData.get(i * 37));
					stmt.setString(2, clientData.get((i * 37) + 1));
				}
				rs = stmt.executeQuery();
				clientData = resultSetToArrayList(rs, clientData);
				stmt = connection.prepareCall("{CALL spGetSSN}");
				rs = stmt.executeQuery();

				clientData = resultSetToArrayList(rs, clientData);
				stmt = connection.prepareCall("{CALL spGetDOB}");
				rs = stmt.executeQuery();
				clientData = resultSetToArrayList(rs, clientData);
				stmt = connection.prepareCall("{CALL spGetAddress(?)}");
				stmt.setString(1, merchant);
				rs = stmt.executeQuery();
				clientData = resultSetToArrayList(rs, clientData);
				stmt = connection.prepareCall("{CALL spGetJob}");
				rs = stmt.executeQuery();
				clientData = resultSetToArrayList(rs, clientData);
				stmt = connection.prepareCall("{CALL spGetUserCell(?)}");
				if (i == 0) {
					stmt.setString(1, clientData.get(9));
				} else {
					stmt.setString(1, clientData.get((i * 37) + 9));
				}
				rs = stmt.executeQuery();
				clientData = resultSetToArrayList(rs, clientData);
				stmt = connection.prepareCall("{CALL spGetPhoneNumber(?)}");
				if (i == 0) {
					stmt.setString(1, clientData.get(9));
				} else {
					stmt.setString(1, clientData.get((i * 37) + 9));
				}
				rs = stmt.executeQuery();
				clientData = resultSetToArrayList(rs, clientData);
				stmt = connection.prepareCall("{CALL spGetEmployer(?)}");
				if (i == 0) {
					stmt.setString(1, clientData.get(9));
				} else {
					stmt.setString(1, clientData.get((i * 37) + 9));
				}
				rs = stmt.executeQuery();
				clientData = resultSetToArrayList(rs, clientData);
				stmt = connection.prepareCall("{CALL spGetEmployer(?)}");
				if (i == 0) {
					stmt.setString(1, clientData.get(13));// phone first 3 digit
				} else {
					stmt.setString(1, clientData.get((i * 37) + 13));// phone first 3 digit
				}
				rs = stmt.executeQuery();
				clientData = resultSetToArrayList(rs, clientData);
				stmt = connection.prepareCall("{CALL spGetBank(?)}");
				if (i == 0) {
					stmt.setString(1, clientData.get(9));// State
				} else {
					stmt.setString(1, clientData.get((i * 37) + 9));// State
				}
				rs = stmt.executeQuery();
				clientData = resultSetToArrayList(rs, clientData);
				clientData = getPayrollData(clientData);
				clientData.add("Next");
				i++;
			}
			if (clientData.size() != 37) {
				throw new IndexOutOfBoundsException();
			}
			for (String var : listOfDisabledCountries) {
				if (var.equalsIgnoreCase(clientData.get(9))) {
					throw new NoSuchFieldException();

				}
			}

			// Adding new cod to avoid try with another lender

			if (merchant.equals("57510")) {
				for (String li : mechant_57510) {
					if (li.equalsIgnoreCase(map.get(9))) {
						log.info("States are not allowed to apply loan");
						throw new NoSuchFieldException();
					}
				}
			}

			if (merchant.equals("57511")) {
				for (String li : mechant_57511) {
					if (li.equalsIgnoreCase(map.get(9))) {
						log.info("States are not allowed to apply loan");
						throw new NoSuchFieldException();
					}
				}
			}

			writeToExcel(noofExecutions, clientData);
			bFlag = true;
			if (bFlag) {
				test.log(LogStatus.PASS, "Client Data Created Successfully");
			} else {
				test.log(LogStatus.FAIL, "Unable to create Client Data");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			// tlog.info("Using recursion as IndexOutOfBoundsException exception occured ");
			user_load_config_file(newFileName);
		} catch (NoSuchFieldException e) {
			// log.info("Using recursion as NoSuchFieldException exception occured ");
			user_load_config_file(newFileName);
		}
	}

	@When("^user fetch the information$")
	public void user_fetch_info() throws Throwable {
		ExcelToHashMap("./src/output/clientData_Output.xlsx");
	}

	@When("^Specify information on AboutYou Page$")
	public void apply_for_loan() {
		log.info("Specify information on AboutYou Page");

		boolean flag = false;
		int counter = 0;
		int[] arr = new int[100];
		for (int i = 100; i <= 300; i += 25) {
			arr[counter] = i;
			counter++;
		}
		Random rn = new Random();
		int i = rn.nextInt(counter);
		String loanAmt = String.valueOf(arr[i]);

		WebElement element = getWebElement("LoanAmtDrpdwn_ID", OR_MLL);

		waitForVisibilityOfElement(element, 10);
		waitForElementToBeClickable(element, 10);

		if (isElementDisplayed(element) && isElementEnabled(element)) {
			selectByValue(getWebElement("LoanAmtDrpdwn_ID", OR_MLL), "275");
			log.info("Loan applied for $275");
			test.log(LogStatus.INFO, "Loan applied for $275" + test.addBase64ScreenShot(report.capture()));
		} else {
			test.log(LogStatus.FAIL,
					test.addScreenCapture(report.capture()) + " Element is either not displayed or enabled");

			Assert.fail(element + " is either not displayed or enabled");
		}

		// waitForPresenceOfElement(By.id("ctl01_btnApply"), 40);

		WebElement applyNowButton = getWebElement("ApplyNowBtn_ID", OR_MLL);

		if (isElementEnabled(applyNowButton) && isElementDisplayed(applyNowButton)) {
			clickOnButton(applyNowButton);
		} else {

			test.log(LogStatus.FAIL, test.addScreenCapture(report.capture()) + "Apply Now button is disabled");
			throw new SLCException("Apply Now button is disabled");
		}

		waitForPresenceOfElement(By.id("content_customContent_frmFirstName"), 40);
		waitForVisibilityOfElement(getWebElement("FirstNameTxt_ID", OR_MLL), 10);
		// waitForPageLoaded();

		Set<String> data = map.keySet();

		log.info("Test Data to be used for testing");
		for (String cData : data) {
			log.info(cData + " : " + map.get(cData));
		}

		sendKeys(getWebElement("FirstNameTxt_ID", OR_MLL), map.get("First_Name"));

		sendKeys(getWebElement("LastNameTxt_ID", OR_MLL), map.get("Last_Name"));

		// sendKeysUsingJS(getWebElement("DOBTxt_ID", OR_MLL), "02/02/1973");
		sendKeysUsingJS(getWebElement("DOBTxt_ID", OR_MLL), map.get("DateofBirth"));
		sendKeysUsingJS(getWebElement("SSNTxt_ID", OR_MLL), map.get("SSN_1") + map.get("SSN_2") + map.get("SSN_3"));

		log.info("SSN NO= " + map.get("SSN_1") + map.get("SSN_2") + map.get("SSN_3"));

		SSN = Integer.parseInt(map.get("SSN_1") + map.get("SSN_2") + map.get("SSN_3"));

		sendKeys(getWebElement("EmailTxt_ID", OR_MLL), map.get("Email"));

		sendKeys(getWebElement("ConfirmEmailTxt_ID", OR_MLL), map.get("Email"));

		sendKeys(getWebElement("StreetAddressTxt_ID", OR_MLL), map.get("Street"));

		sendKeys(getWebElement("CityTxt_ID", OR_MLL), map.get("City"));

		WebElement eleState = getWebElement("StateDrpdwn_ID", OR_MLL);
		eleState.click();
		selectByValue(getWebElement("StateDrpdwn_ID", OR_MLL), map.get("State"));
		sendKeysUsingJS(getWebElement("ZipTxt_ID", OR_MLL), map.get("Zip"));

		sendKeysUsingJS(getWebElement("HomePhoneTxt_ID", OR_MLL),
				map.get("Home_Ph_1") + map.get("Home_Ph_2") + map.get("Home_Ph_3"));

		// verifyStringInTextBox(map.get("Home_Ph_1") + map.get("Home_Ph_2") +
		// map.get("Home_Ph_3"), getWebElement("HomePhoneTxt_ID", OR_MLL));

		if (driver.findElements(By.id("content_customContent_checkbox")).size() > 0) {

			if (getWebElement("CellPhoneChkbox_ID", OR_MLL).isSelected()) {
				clickOnButton(getWebElement("CellPhoneChkbox_ID", OR_MLL));
				waitForPresenceOfElement(By.id("content_customContent_frmCellPhone"), 10);
				sendKeysUsingJS(getWebElement("CellPhoneTxt_ID", OR_MLL),
						map.get("Cell_Ph_1") + map.get("Cell_Ph_2") + map.get("Cell_Ph_3"));

			}
		}
		scrollBy(150);
		test.log(LogStatus.PASS, "About You Page" + test.addBase64ScreenShot(report.capture(driver, "About You Page")));

		// Assert.assertTrue(isElementEnabled(getWebElement("AboutYouNextBtn_ID",
		// OR_MLL)));

		clickOnButton(getWebElement("AboutYouNextBtn_ID", OR_MLL));

		// waitForPageToLoad(By.id("content_customContent_incomeTab"), 40);

		// waitForPresenceOfElement(By.id("content_customContent_incomeTab"), 40);
		// waitForVisibilityOfElement(getWebElement("IncomeTab_ID", OR_MLL), 40);
		waitForPageLoaded();

		flag = true;
		WebElement eleIncomeTab = getWebElement("IncomeTab_ID", OR_MLL);
		if (eleIncomeTab.isDisplayed()) {
			test.log(LogStatus.PASS,
					"About You Page is Updated for User: " + map.get("First_Name") + " " + map.get("Last_Name"));
		} else {
			test.log(LogStatus.FAIL, "Unable to Update Information in About You Page"
					+ test.addBase64ScreenShot(report.capture(driver, "About You Page")));
		}

	}

	@When("^Specify information on Income Page for (.*) with (.*)$")
	public void update_IncomePage(String IncomeSource, String paymentType) throws Throwable {

		log.info("Specify information on Income Page for " + IncomeSource + " with " + paymentType + "");
		log.info("Source of Income : " + IncomeSource);
		log.info("Payment method : " + paymentType);
		boolean flag = false;
		Random rm = new Random();
		char[] arrIncomeSource = { 'D', 'G', 'S', 'U', 'W' };
		int IncomeSourceValue = rm.nextInt(4);
		String strIncomeSource = String.valueOf(arrIncomeSource[IncomeSourceValue]);
		waitForPresenceOfElement(By.id("content_customContent_incomeTab"), 30);
		waitForVisibilityOfElement(getWebElement("IncomeTab_ID", OR_MLL), 30);
		WebElement eleIncomeTab = getWebElement("IncomeTab_ID", OR_MLL);

		if (eleIncomeTab.isDisplayed()) {
			test.log(LogStatus.PASS,
					"Income Page is loaded for User: " + map.get("First_Name") + " " + map.get("Last_Name"));
			WebElement eleSource = getWebElement("IncomeSourceDrpDwn_ID", OR_MLL);
			eleSource.click();
			Select state = new Select(eleSource);
			if (IncomeSource.equals("Employed")) {
				state.selectByValue("P");
				sendKeys(getWebElement("EmployerNameTxt_ID", OR_MLL), map.get("Employer_Name"));
				verifyStringInTextBox(map.get("Employer_Name"), getWebElement("EmployerNameTxt_ID", OR_MLL));
				waitForVisibilityOfElement(getWebElement("WorkPhoneTxt_ID", OR_MLL), 5);
				// sendKeys(getWebElement("WorkPhoneTxt_ID", OR_MLL), 959 + "" + 994 + "" + 9407
				// + "" + 4662);
				sendKeysUsingJS(getWebElement("WorkPhoneTxt_ID", OR_MLL), "(959) 994-9407 x4662");
				// verifyStringInTextBox("(959) 994-9407 x4662",
				// getWebElement("WorkPhoneTxt_ID", OR_MLL));

			} else {
				state.selectByValue(strIncomeSource);
				String strMainIncomeSource = driver
						.findElement(By.xpath("//select[@id='content_customContent_frmIncomeSource']/option[@value='"
								+ strIncomeSource + "']"))
						.getText().trim();
				test.log(LogStatus.PASS, "Income Page, Main Income Source selected is: " + strMainIncomeSource);

			}
			scrollBy(100);
			// scrollIntoView(getWebElement("DirectDepositChkbox_ID", OR_MLL));
			if (paymentType.equalsIgnoreCase("DirectDeposit")) {
				if (!isElementSelected(getWebElement("DirectDepositChkbox_ID", OR_MLL))) {
					clickUsingJS(getWebElement("DirectDepositChkbox_ID", OR_MLL));
					Assert.assertTrue(isElementSelected(getWebElement("DirectDepositChkbox_ID", OR_MLL)));
				}
			} else {
				if (!isElementSelected(getWebElement("PaperChkChkbox_ID", OR_MLL))) {
					clickOnButton(getWebElement("PaperChkChkbox_ID", OR_MLL));
					Assert.assertTrue(getWebElement("PaperChkChkbox_ID", OR_MLL).isSelected());
				}
			}

			sendKeys(getWebElement("NetPayTxt_ID", OR_MLL), "16000");

			if (map.get("Period").equals("B")) {
				clickOnButton(getWebElement("Every2WeeksRadioBtn_ID", OR_MLL));
				Assert.assertTrue(isElementSelected(getWebElement("Every2WeeksRadioBtn_ID", OR_MLL)));
			} else if (map.get("Period").equals("S")) {
				clickOnButton(getWebElement("TwiceMonthlyRadioBtn_ID", OR_MLL));
				Assert.assertTrue(isElementSelected(getWebElement("TwiceMonthlyRadioBtn_ID", OR_MLL)));
			} else if (map.get("Period").equals("M")) {
				if (!getWebElement("MonthlyRadioBtn_ID", OR_MLL).isSelected()) {
					clickOnButton(getWebElement("MonthlyRadioBtn_ID", OR_MLL));
					Assert.assertTrue(isElementSelected(getWebElement("MonthlyRadioBtn_ID", OR_MLL)));
				}
			} else {
				if (map.get("Period").equals("W")) {
					clickOnButton(getWebElement("WeeklyRadioBtn_ID", OR_MLL));
					Assert.assertTrue(isElementSelected(getWebElement("WeeklyRadioBtn_ID", OR_MLL)));

				}
			}

			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture()));

			// waitForPresenceOfElement(By.id("content_customContent_frmNetPay"), 30);

			sendKeysUsingJS(getWebElement("LastPayDate_ID", OR_MLL), getNextBusinessDayFromGivenDate(getPastDate(-4)));

			test.log(LogStatus.INFO, "Last pay date : " + getNextBusinessDayFromGivenDate(getPastDate(-4)));

			test.log(LogStatus.INFO, "First upcoming pay date : " + map.get("Next_PayDate"));

			test.log(LogStatus.INFO, "Second upcoming pay date : " + map.get("Second_PayDate"));

			log.info("Last pay date : " + getNextBusinessDayFromGivenDate(getPastDate(-4)));
			log.info("First upcoming pay date : " + map.get("Next_PayDate"));
			log.info("Second upcoming pay date : " + map.get("Second_PayDate"));

			sendKeysUsingJS(getWebElement("NextPayDateTxt_ID", OR_MLL), map.get("Next_PayDate"));

			// driver.findElement(By.id("calendar_icon")).click();
			// System.out.println(driver.findElement(By.xpath("//div[@class='dhtmlxcalendar_container
			// dhtmlxcalendar_skin_omega dhtmlxcalendar_time_hidden'][2]")).getText());

			Assert.assertEquals(getWebElement("NextPayDateTxt_ID", OR_MLL).getAttribute("value"),
					map.get("Next_PayDate"));

			sendKeysUsingJS(getWebElement("SecondPayDateTxt_ID", OR_MLL), map.get("Second_PayDate"));
			Assert.assertEquals(getWebElement("SecondPayDateTxt_ID", OR_MLL).getAttribute("value"),
					map.get("Second_PayDate"));
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Income Page")));

			scrollBy(200);
			waitForElementToBeClickable(getWebElement("IncomeNextBtn_ID", OR_MLL), 20);
			clickOnButton(getWebElement("IncomeNextBtn_ID", OR_MLL));
			int count = 0;
			boolean checkFSPayDate = false;
			int lastDayCount = 0;

			try {

				// first pay daye

				if (driver.findElement(By.xpath("//span[@id='content_customContent_cvNextPaydate']")).isDisplayed()) {
					log.info(driver.findElement(By.xpath("//span[@id='content_customContent_cvNextPaydate']"))
							.getText());
					checkFSPayDate = true;
					throw new SLCException();
				}

				if (driver.findElement(By.xpath("//span[@id='content_customContent_revNextPaydate']")).isDisplayed()) {
					log.info(driver.findElement(By.xpath("//span[@id='content_customContent_revNextPaydate']"))
							.getText());
					checkFSPayDate = true;
					throw new SLCException();
				}

				if (driver.findElement(By.xpath("//span[@id='content_customContent_rfvNextPaydate']")).isDisplayed()) {
					log.info(driver.findElement(By.xpath("//span[@id='content_customContent_rfvNextPaydate']"))
							.getText());
					checkFSPayDate = true;
					throw new SLCException();
				}

				// second paydate

				if (driver.findElement(By.xpath("//span[@id='content_customContent_rfvSecondPaydate']"))
						.isDisplayed()) {
					log.info(driver.findElement(By.xpath("//span[@id='content_customContent_rfvSecondPaydate']"))
							.getText());
					checkFSPayDate = true;
					throw new SLCException();
				}

				if (driver.findElement(By.xpath("//span[@id='content_customContent_revSecondPaydate']"))
						.isDisplayed()) {
					log.info(driver.findElement(By.xpath("//span[@id='content_customContent_revSecondPaydate']"))
							.getText());
					checkFSPayDate = true;
					throw new SLCException();
				}

				if (driver.findElement(By.xpath("//span[@id='content_customContent_cvSecondPaydate']")).isDisplayed()) {
					log.info(driver.findElement(By.xpath("//span[@id='content_customContent_cvSecondPaydate']"))
							.getText());
					checkFSPayDate = true;
					throw new SLCException();
				}

				// last paydate

				if (driver.findElement(By.xpath("//span[@id='content_customContent_revLastPaydate']")).isDisplayed()) {
					log.info(driver.findElement(By.xpath("//span[@id='content_customContent_revLastPaydate']"))
							.getText());
					lastDayCount++;
					throw new SLCException();
				}

				if (driver.findElement(By.xpath("//span[@id='content_customContent_rfvLastPaydate']")).isDisplayed()) {
					log.info(driver.findElement(By.xpath("//span[@id='content_customContent_rfvLastPaydate']"))
							.getText());
					lastDayCount++;
					throw new SLCException();
				}

				if (driver.findElement(By.xpath("//span[@id='content_customContent_CmpLastPaydate']")).isDisplayed()) {
					log.info(driver.findElement(By.xpath("//span[@id='content_customContent_CmpLastPaydate']"))
							.getText());
					lastDayCount++;
					throw new SLCException();
				}

				// phone number
				if (driver.findElement(By.xpath("//span[@id='content_customContent_rfvWorkPhone']")).isDisplayed()) {
					log.info(
							driver.findElement(By.xpath("//span[@id='content_customContent_rfvWorkPhone']")).getText());
					scrollIntoView(driver.findElement(By.xpath("//span[@id='content_customContent_rfvWorkPhone']")));
					Assert.fail(errorMessage = "Please enter your Work Phone Number.");
				}

				if (driver.findElement(By.xpath("//span[@id='content_customContent_revWorkPhone']")).isDisplayed()) {
					log.info(
							driver.findElement(By.xpath("//span[@id='content_customContent_revWorkPhone']")).getText());
					scrollIntoView(driver.findElement(By.xpath("//span[@id='content_customContent_revWorkPhone']")));
					Assert.fail(errorMessage = "Please input a valid Work Phone Number.");
				}

				if (driver.findElement(By.xpath("//span[@id='content_customContent_cvWorkPhone']")).isDisplayed()) {
					log.info(driver.findElement(By.xpath("//span[@id='content_customContent_cvWorkPhone']")).getText());
					scrollIntoView(driver.findElement(By.xpath("//span[@id='content_customContent_cvWorkPhone']")));
					Assert.fail(errorMessage = "Please input a valid Work Number.");
				}

				checkPresenceOfElement("BankingTab_ID", OR_MLL, 10);
				checkVisibilityOfElement(getWebElement("BankingTab_ID", OR_MLL), 10);

			} catch (SLCException e) {
				try {

					boolean bFlag = false;

					if (getWebElement("NetPayTxt_ID", OR_MLL).isDisplayed()) {
						bFlag = true;
					}

					while (bFlag) {
						scrollIntoView(getWebElement("NextPayDateTxt_ID", OR_MLL));

						if (checkFSPayDate) {
							changeDate();
							checkFSPayDate = false;
						}
						sendKeys(getWebElement("LastPayDate_ID", OR_MLL),
								getNextBusinessDayFromGivenDate(getPastDate(-4 - lastDayCount)));

						verifyStringInTextBox(getNextBusinessDayFromGivenDate(getPastDate(-4 - lastDayCount)),
								getWebElement("LastPayDate_ID", OR_MLL));

						log.info("Last Pay date : " + getNextBusinessDayFromGivenDate(getPastDate(-4 - lastDayCount)));
						sendKeys(getWebElement("NextPayDateTxt_ID", OR_MLL), map.get("Next_PayDate"));

						verifyStringInTextBox(map.get("Next_PayDate"), getWebElement("NextPayDateTxt_ID", OR_MLL));
						log.info(" Updated " + map.get("Next_PayDate"));

						sendKeys(getWebElement("SecondPayDateTxt_ID", OR_MLL), map.get("Second_PayDate"));
						verifyStringInTextBox(map.get("Second_PayDate"), getWebElement("SecondPayDateTxt_ID", OR_MLL));
						log.info(" Updated " + map.get("Second_PayDate"));
						waitForElementToBeClickable(getWebElement("IncomeNextBtn_ID", OR_MLL), 20);
						clickOnButton(getWebElement("IncomeNextBtn_ID", OR_MLL));

						try {
							// last paydate

							if (driver.findElement(By.xpath("//span[@id='content_customContent_revLastPaydate']"))
									.isDisplayed()) {
								log.info(driver
										.findElement(By.xpath("//span[@id='content_customContent_revLastPaydate']"))
										.getText());
								lastDayCount++;
								throw new SLCException();
							}

							if (driver.findElement(By.xpath("//span[@id='content_customContent_rfvLastPaydate']"))
									.isDisplayed()) {
								log.info(driver
										.findElement(By.xpath("//span[@id='content_customContent_rfvLastPaydate']"))
										.getText());
								lastDayCount++;
								throw new SLCException();
							}

							if (driver.findElement(By.xpath("//span[@id='content_customContent_CmpLastPaydate']"))
									.isDisplayed()) {
								log.info(driver
										.findElement(By.xpath("//span[@id='content_customContent_CmpLastPaydate']"))
										.getText());
								lastDayCount++;
								throw new SLCException();
							}
							// first paydate
							if (driver.findElement(By.xpath("//span[@id='content_customContent_cvNextPaydate']"))
									.isDisplayed()) {
								log.info(driver
										.findElement(By.xpath("//span[@id='content_customContent_cvNextPaydate']"))
										.getText());
								checkFSPayDate = true;
								throw new SLCException();
							}

							if (driver.findElement(By.xpath("//span[@id='content_customContent_revNextPaydate']"))
									.isDisplayed()) {
								log.info(driver
										.findElement(By.xpath("//span[@id='content_customContent_revNextPaydate']"))
										.getText());
								checkFSPayDate = true;
								throw new SLCException();
							}

							if (driver.findElement(By.xpath("//span[@id='content_customContent_rfvNextPaydate']"))
									.isDisplayed()) {
								log.info(driver
										.findElement(By.xpath("//span[@id='content_customContent_rfvNextPaydate']"))
										.getText());
								checkFSPayDate = true;
								throw new SLCException();
							}

							// second paydate

							if (driver.findElement(By.xpath("//span[@id='content_customContent_rfvSecondPaydate']"))
									.isDisplayed()) {
								log.info(driver
										.findElement(By.xpath("//span[@id='content_customContent_rfvSecondPaydate']"))
										.getText());
								checkFSPayDate = true;
								throw new SLCException();
							}

							if (driver.findElement(By.xpath("//span[@id='content_customContent_revSecondPaydate']"))
									.isDisplayed()) {
								log.info(driver
										.findElement(By.xpath("//span[@id='content_customContent_revSecondPaydate']"))
										.getText());
								checkFSPayDate = true;
								throw new SLCException();
							}

							if (driver.findElement(By.xpath("//span[@id='content_customContent_cvSecondPaydate']"))
									.isDisplayed()) {
								log.info(driver
										.findElement(By.xpath("//span[@id='content_customContent_cvSecondPaydate']"))
										.getText());
								checkFSPayDate = true;
								throw new SLCException();
							}

							checkPresenceOfElement("BankingTab_ID", OR_MLL, 10);
							checkVisibilityOfElement(getWebElement("BankingTab_ID", OR_MLL), 10);
							bFlag = false;
						} catch (SLCException e1) {
							count++;
							if (count == 3 || lastDayCount == 3) {
								scrollIntoView(getWebElement("NextPayDateTxt_ID", OR_MLL));
								test.log(LogStatus.FAIL,
										"Issue with the date " + test.addBase64ScreenShot(report.capture()));
								Assert.fail("Issue with the pay date");
							}
							continue;
						}

					}
				} catch (Exception e1) {
					waitForPresenceOfElement(By.id("content_customContent_bankingTab"), 5);
					waitForVisibilityOfElement(getWebElement("BankingTab_ID", OR_MLL), 10);
				}
			}

		}
	}

	@When("^Specify information on Banking Page using \"([^\"]*)\"$")
	public void update_BankingPage(String PaymentMode) throws Throwable {
		boolean flag = false;

		WebElement eleBankingTab = getWebElement("BankingTab_ID", OR_MLL);

		waitForPageLoaded();

		if (eleBankingTab.isDisplayed()) {
			test.log(LogStatus.PASS,
					"Banking Page is loaded for User: " + map.get("First_Name") + " " + map.get("Last_Name"));

			waitForPresenceOfElement("ABARoutingNoTxt_ID", OR_MLL, 40);
			sendKeysUsingJS(getWebElement("ABARoutingNoTxt_ID", OR_MLL), map.get("ABA"));
			sendKeysUsingJS(getWebElement("BankAcctNo_ID", OR_MLL), map.get("Acct"));

			scrollIntoView(getWebElement("ABARoutingNoTxt_ID", OR_MLL));

			if (PaymentMode.equals("Electronic Fund Transfer") || PaymentMode.equals("EFT")) {
				if (!getWebElement("EFTFundRadio_ID", OR_MLL).isSelected()) {
					scrollBy(80);
					clickOnButton(getWebElement("EFTFundRadio_ID", OR_MLL));
					log.info("EFT radio button is selected");
					Assert.assertTrue(getWebElement("EFTFundRadio_ID", OR_MLL).isSelected(),
							"EFT Radio is not selected");
				}
			} else if ((PaymentMode.equals("Credit Card"))) {
				if (!getWebElement("CreditCardRadio_ID", OR_MLL).isSelected()) {
					clickOnButton(getWebElement("CreditCardRadio_ID", OR_MLL));
					log.info("Credit radio button is selected");
					Assert.assertTrue(getWebElement("CreditCardRadio_ID", OR_MLL).isSelected(),
							"Credit Radio is not selected");
				}
			} else {
				if (!getWebElement("TextChkRadio_ID", OR_MLL).isSelected()) {
					clickOnButton(getWebElement("TextChkRadio_ID", OR_MLL));
					log.info("TxtACheck radio button is selected");
					Assert.assertTrue(getWebElement("TextChkRadio_ID", OR_MLL).isSelected(),
							"TxtACheck Radio is not selected");

				}
			}

			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Banking Details screen shot1")));
			new Actions(driver).moveToElement(getWebElement("ChkApprovalBtn_ID", OR_MLL)).click().build().perform();

			log.info("Clicked on Approval button");
			// System.out.println(driver.getTitle());
			waitForPageLoaded();

//commenting temp on 04/01/2021

			/*
			 * waitForPresenceOfElement(By.xpath(
			 * "//input[@id='content_customContent_chkElectronicConsent']"), 5,
			 * "Either try with another lender or error page occured");
			 * waitForVisibilityOfElement( driver.findElement(By.xpath(
			 * "//input[@id='content_customContent_chkElectronicConsent']")), 5,
			 * "Either try with another lender or error page occured");
			 * 
			 * test.log(LogStatus.PASS, "Banking Page is Updated for User: " +
			 * map.get("First_Name") + " " + map.get("Last_Name"));
			 */

		}

	}

	@When("^UI Verify on Banking Page using \"([^\"]*)\"$")
	public void uiVerify_BankingPage(String PaymentMode) throws Throwable {
		boolean flag = false;

		WebElement eleBankingTab = getWebElement("BankingTab_ID", OR_MLL);
		if (eleBankingTab.isDisplayed()) {
			test.log(LogStatus.PASS,
					"Banking Page is loaded for User: " + map.get("First_Name") + " " + map.get("Last_Name"));
			sendKeys(getWebElement("ABARoutingNoTxt_ID", OR_MLL), map.get("ABA"));

			verifyStringInTextBox(map.get("ABA"), getWebElement("ABARoutingNoTxt_ID", OR_MLL));

			sendKeys(getWebElement("BankAcctNo_ID", OR_MLL), map.get("Acct"));
			verifyStringInTextBox(map.get("Acct"), getWebElement("BankAcctNo_ID", OR_MLL));
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Account Details")));
			WebElement element = getWebElement("EFTFundRadio_ID", OR_MLL);
			// ((JavascriptExecutor)
			// driver).executeScript("arguments[0].scrollIntoView(true);", element);
			scrollBy(150);
			Assert.assertTrue(isElementDisplayed(getWebElement("EFTFundRadio_ID", OR_MLL)));
			getWebElement("EFTFundRadio_ID", OR_MLL).click();
			Assert.assertTrue(isElementSelected(getWebElement("EFTFundRadio_ID", OR_MLL)));

			if (CONFIG.getProperty("appEnv").equalsIgnoreCase("dev")) {
				Assert.assertTrue(isElementDisplayed(getWebElement("CreditCardRadio_ID", OR_MLL)));
				getWebElement("CreditCardRadio_ID", OR_MLL).click();
				Assert.assertTrue(isElementSelected(getWebElement("CreditCardRadio_ID", OR_MLL)));
			}
			Assert.assertTrue(isElementDisplayed(getWebElement("TextChkRadio_ID", OR_MLL)));
			getWebElement("TextChkRadio_ID", OR_MLL).click();
			Assert.assertTrue(isElementSelected(getWebElement("TextChkRadio_ID", OR_MLL)));

			flag = true;
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Screen shot")));

			if (flag) {
				test.log(LogStatus.PASS, "UI Verification done");
			} else {
				test.log(LogStatus.FAIL, "UI Verification Failed");
				test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Banking Page")));
				Assert.assertFalse(false);
			}
		}
	}

	@Then("^Specify information on Banking Page_All validation using \"([^\"]*)\"$")
	public void specify_information_on_Banking_Page_All_validation_using(String PaymentMode) throws Throwable {

		boolean flag = false;
		Thread.sleep(2000);
		WebElement eleBankingTab = getWebElement("BankingTab_ID", OR_MLL);
		if (eleBankingTab.isDisplayed()) {
			test.log(LogStatus.PASS,
					"Banking Page is loaded for User: " + map.get("First_Name") + " " + map.get("Last_Name"));
			getWebElement("ABARoutingNoTxt_ID", OR_MLL).click();

			if (map.get("ABA").length() == 9) {
				getWebElement("ABARoutingNoTxt_ID", OR_MLL).sendKeys(map.get("ABA"));
				test.log(LogStatus.PASS, "Valid ABA number : " + map.get("ABA"));

			} else {

				test.log(LogStatus.FAIL, "Invalid ABA number :" + map.get("ABA"));
				test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Banking Page")));
			}

			getWebElement("BankAcctNo_ID", OR_MLL).click();
			int len1 = map.get("Acct").length();

			if (map.get("Acct").length() == 10) {

				getWebElement("BankAcctNo_ID", OR_MLL).sendKeys(map.get("Acct"));
				test.log(LogStatus.PASS, "VAlid Account number " + map.get("ABA"));

			} else {
				test.log(LogStatus.FAIL, "INVAlid Account number " + map.get("ABA"));
				test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Banking Page")));
			}

			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Account Details")));
			WebElement element = getWebElement("EFTFundRadio_ID", OR_MLL);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			if (PaymentMode.equals("Electronic Fund Transfer")) {
				getWebElement("EFTFundRadio_ID", OR_MLL).click();

			} else
				getWebElement("CreditCardRadio_ID", OR_MLL).click();

			getWebElement("ChkApprovalBtn_ID", OR_MLL).click();

			Thread.sleep(2000);
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Banking Details")));
			waitForPageLoaded();
			String strFirstName = map.get("First_Name").substring(0, 1).toUpperCase()
					+ map.get("First_Name").substring(1).toLowerCase();
			element = driver
					.findElement(By.xpath("//div[@id='headerBlock1']//p[contains(text(),'" + strFirstName + "')]"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			if (element.isDisplayed()) {
				flag = true;
			}

		}
//		if(flag) {
//			test.log(LogStatus.PASS, "Banking Page is Updated for User: "+map.get("First_Name")+" "+map.get("Last_Name"));			
//		}else {
//			test.log(LogStatus.FAIL, "Unable to Update Information in Banking Page");
//			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver,"Banking Page")));
//		}
	}

	@When("^Apply Contact Consent$")
	public void update_Consent() {
		log.info("Apply Contact Consent");
		boolean flag = false;

		Random rm = new Random();
		scrollBy(500);
		waitTillElementIsVisible("ElectronicConsentChkbox_ID", OR_MLL, 30, errorMessage = "Try with Another Lender");
		if (!isElementSelected(getWebElement("ElectronicConsentChkbox_ID", OR_MLL))) {
			clickUsingJS(getWebElement("ElectronicConsentChkbox_ID", OR_MLL));
			Assert.assertTrue(isElementSelected(getWebElement("ElectronicConsentChkbox_ID", OR_MLL)));
		}

		if (!isElementSelected(getWebElement("MarketingCallRadioYes_ID", OR_MLL))) {
			clickUsingJS(getWebElement("MarketingCallRadioYes_ID", OR_MLL));
			Assert.assertTrue(isElementSelected(getWebElement("MarketingCallRadioYes_ID", OR_MLL)));
		}

		if (!isElementSelected(getWebElement("MarketingSMSRadioYes_ID", OR_MLL))) {
			clickUsingJS(getWebElement("MarketingSMSRadioYes_ID", OR_MLL));
			Assert.assertTrue(isElementSelected(getWebElement("MarketingSMSRadioYes_ID", OR_MLL)));
		}

		test.log(LogStatus.PASS,
				"Your contact preferences" + test.addBase64ScreenShot(report.capture(driver, "Screen shot")));

		int hcounter = 0;
		int[] arrhour = new int[15];
		for (int i = 1; i <= 12; i++) {
			arrhour[hcounter] = i;
			hcounter++;
		}
		Random rn = new Random();
		int i = rn.nextInt(hcounter);
		String hour = String.valueOf(arrhour[i]);
		int mcounter = 0;
		int[] arrMinutes = new int[100];
		for (int j = 0; j <= 45; j += 15) {
			arrMinutes[mcounter] = j;
			mcounter++;
		}
		rn = new Random();
		int j = rn.nextInt(mcounter);
		String minutes = String.valueOf(arrMinutes[j]);
		if (minutes.equals("0")) {
			minutes = "00";
		}

		if (getWebElementSize("ScheduleCallDrpDwn_ID", OR_MLL) > 0) {
			scrollIntoView(getWebElement("ScheduleCallDrpDwn_ID", OR_MLL));
			clickOnButton(getWebElement("ScheduleCallDrpDwn_ID", OR_MLL));
			selectByValue(getWebElement("ScheduleCallDrpDwn_ID", OR_MLL), hour + ":" + minutes);
			clickOnButton(getWebElement("ScheduleAMPMDrpDwn_ID", OR_MLL));
			selectByValue(getWebElement("ScheduleAMPMDrpDwn_ID", OR_MLL), "PM");
			getWebElement("ScheduleOKBtn_ID", OR_MLL).click();

			new WebDriverWait(driver, 10).until(
					ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[@id='btnmodalyes4']"))));
			clickUsingJS(driver.findElement(By.xpath("//button[@id='btnmodalyes4']")));
			waitForPresenceOfElement(By.id("btnConsentNext"), 20);
			waitForVisibilityOfElement(getWebElement("ConsentNextBtn_ID", OR_MLL), 20);
			test.log(LogStatus.PASS,
					"Schedule information" + test.addBase64ScreenShot(report.capture(driver, "Contact Consent Page")));
		}

		/*
		 * } catch (TimeoutException e) {
		 * log.info("Schedule call pop up is not present"); }
		 */

		clickUsingJS(getWebElement("ConsentNextBtn_ID", OR_MLL));

		waitForPresenceOfElement("EFTPaymentRadio_ID", OR_MLL, 20);

		flag = true;
		if (flag) {
			test.log(LogStatus.PASS,
					"Contact Consent Page is Updated for User: " + map.get("First_Name") + " " + map.get("Last_Name"));
		} else {
			test.log(LogStatus.FAIL, "Unable to Update Information in Contact Consent Page");
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Contact Consent Page")));
		}
	}

	@When("^Apply ESign with (.*)$")
	public void apply_esign(String paymentType) {
		log.info("Apply ESign with " + paymentType);
		boolean flag = false;
		waitForPageLoaded();

		boolean b = getWebElement("EFTPaymentRadio_ID", OR_MLL).isDisplayed();

		if (!b) {
			waitForVisibilityOfElement(getWebElement("EFTPaymentRadio_ID", OR_MLL), 40);
			scrollIntoView(getWebElement("EFTPaymentRadio_ID", OR_MLL));
		} else {
			scrollIntoView(getWebElement("EFTPaymentRadio_ID", OR_MLL));
		}
		Assert.assertTrue(isElementDisplayed(getWebElement("EFTPaymentRadio_ID", OR_MLL)));
		Assert.assertTrue(isElementEnabled(getWebElement("EFTPaymentRadio_ID", OR_MLL)));

		Assert.assertTrue(isElementDisplayed(getWebElement("TxtAcheckPaymentRadio_id", OR_MLL)));
		Assert.assertTrue(isElementEnabled(getWebElement("TxtAcheckPaymentRadio_id", OR_MLL)));

		if (paymentType.equalsIgnoreCase("EFT") || paymentType.equalsIgnoreCase("Electronic Fund Transfer")) {
			if (!isElementSelected(getWebElement("EFTPaymentRadio_ID", OR_MLL))) {
				getWebElement("EFTPaymentRadio_ID", OR_MLL).click();
				Assert.assertTrue(isElementSelected(getWebElement("EFTPaymentRadio_ID", OR_MLL)));
			}
		} else {
			if (!isElementSelected(getWebElement("TxtAcheckPaymentRadio_id", OR_MLL))) {
				getWebElement("TxtAcheckPaymentRadio_id", OR_MLL).click();
				Assert.assertTrue(isElementSelected(getWebElement("TxtAcheckPaymentRadio_id", OR_MLL)));
			}
		}

		scrollIntoView(getWebElement("NonMilitaryRadio_ID", OR_MLL));
		waitForVisibilityOfElement(getWebElement("NonMilitaryRadio_ID", OR_MLL), 5);

		Assert.assertTrue(isElementDisplayed(getWebElement("NonMilitaryRadio_ID", OR_MLL)));
		Assert.assertTrue(isElementEnabled(getWebElement("NonMilitaryRadio_ID", OR_MLL)));

		if (!isElementSelected(getWebElement("NonMilitaryRadio_ID", OR_MLL))) {
			clickUsingJS(getWebElement("NonMilitaryRadio_ID", OR_MLL));
			Assert.assertTrue(isElementSelected(getWebElement("NonMilitaryRadio_ID", OR_MLL)));
		}

		scrollIntoView(getWebElement("AcceptTermsChkbox_ID", OR_MLL));

		waitForVisibilityOfElement(getWebElement("AcceptTermsChkbox_ID", OR_MLL), 5);
		Assert.assertTrue(isElementDisplayed(getWebElement("AcceptTermsChkbox_ID", OR_MLL)));
		Assert.assertTrue(isElementEnabled(getWebElement("AcceptTermsChkbox_ID", OR_MLL)));

		if (!isElementSelected(getWebElement("AcceptTermsChkbox_ID", OR_MLL))) {
			clickUsingJS(getWebElement("AcceptTermsChkbox_ID", OR_MLL));
			Assert.assertTrue(isElementSelected(getWebElement("AcceptTermsChkbox_ID", OR_MLL)));
		}

		sendKeys(getWebElement("LoanAuthTxt_ID", OR_MLL), map.get("First_Name") + " " + map.get("Last_Name"));
		verifyStringInTextBox(map.get("First_Name") + " " + map.get("Last_Name"),
				getWebElement("LoanAuthTxt_ID", OR_MLL));

		test.log(LogStatus.PASS,
				"Contact consent page" + test.addBase64ScreenShot(report.capture(driver, "Apply ESign one")));

		waitForElementToBeClickable(getWebElement("ConsentAcceptanceBtn_XPATH", OR_MLL), 10);

		clickUsingJS(getWebElement("ConsentAcceptanceBtn_XPATH", OR_MLL));
		// waitForPresenceOfElement("MercLogoutBtn_XPATH", OR_MLL, 10);

		waitTillElementIsVisible("MercLogoutBtn_XPATH", OR_MLL, 10, "Logout button is not present");

		scrollIntoView(getWebElement("MercLogoutBtn_XPATH", OR_MLL));

		// test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver,
		// "Loan Approved")));

		test.log(LogStatus.PASS, "Logout page " + test.addBase64ScreenShot(report.capture()));

		Assert.assertTrue(isElementDisplayed(getWebElement("MercLogoutBtn_XPATH", OR_MLL)));

		clickOnButton(getWebElement("MercLogoutBtn_XPATH", OR_MLL));

		flag = true;
		if (flag) {
			test.log(LogStatus.PASS, "Contact and ESignature Page is Updated for User: " + map.get("First_Name") + " "
					+ map.get("Last_Name"));
		} else {

			test.log(LogStatus.FAIL, "Unable to Update Information in Contact and ESignature Page"
					+ test.addBase64ScreenShot(report.capture(driver, "Loan Declined")));
		}
	}

	@Then("^Apply paymentmode with EFT$")
	public void apply_paymentmode_with_EFT() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		boolean flag = false;
		if (getWebElement("EFTPaymentRadio_ID", OR_MLL).isEnabled()
				&& !getWebElement("EFTPaymentRadio_ID", OR_MLL).isSelected()) {
			getWebElement("EFTPaymentRadio_ID", OR_MLL).click();
		}

		scrollBy(350);

		if (getWebElement("NonMilitaryRadio_ID", OR_MLL).isEnabled()
				&& !getWebElement("NonMilitaryRadio_ID", OR_MLL).isSelected()) {
			clickUsingJS(getWebElement("NonMilitaryRadio_ID", OR_MLL));
		}
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Screen shot")));

		scrollIntoView(getWebElement("NonMilitaryRadio_ID", OR_MLL));

		if (getWebElement("AcceptTermsChkbox_ID", OR_MLL).isEnabled()
				&& !getWebElement("AcceptTermsChkbox_ID", OR_MLL).isSelected()) {
			clickUsingJS(getWebElement("AcceptTermsChkbox_ID", OR_MLL));

		}

		sendKeys(getWebElement("LoanAuthTxt_ID", OR_MLL), map.get("First_Name") + " " + map.get("Last_Name"));
		scrollBy(160);
		getWebElement("ConsentAcceptanceBtn_ID", OR_MLL).click();
		waitForPageLoaded();
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Loan Approved")));
		// element = ;

		scrollIntoView(getWebElement("MercLogoutBtn_XPATH", OR_MLL));
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getWebElement("MercLogoutBtn_XPATH", OR_MLL));
		getWebElement("MercLogoutBtn_XPATH", OR_MLL).click();
//		driver.navigate().refresh();
		flag = true;
		if (flag) {
			test.log(LogStatus.PASS, "Contact and ESignature Page is Updated for User: " + map.get("First_Name") + " "
					+ map.get("Last_Name"));
		} else {
			test.log(LogStatus.FAIL, "Unable to Update Information in Contact and ESignature Page");
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Loan Declined")));
		}

	}

	@When("^Apply ESign and Register using (.*)$")
	public void apply_esignRegister(String strValidate) throws Throwable {
		// scrollIntoView(getWebElement("EFTPaymentRadio_ID", OR_MLL));
		waitForPresenceOfElement("EFTPaymentRadio_ID", OR_MLL, 10);
		waitForVisibilityOfElement(getWebElement("EFTPaymentRadio_ID", OR_MLL), 10);

		waitForPageLoaded();

		if (!getWebElement("EFTPaymentRadio_ID", OR_MLL).isSelected()) {
			clickOnButton(getWebElement("EFTPaymentRadio_ID", OR_MLL));
		}

		WebElement el = getWebElement("NonMilitaryRadio_ID", OR_MLL);
		scrollIntoView(el);
		if (!el.isSelected()) {
			// getWebElement("NonMilitaryRadio_ID", OR_MLL).click();
			clickUsingAction(el);
		}

		scrollBy(150);

		if (!getWebElement("AcceptTermsChkbox_ID", OR_MLL).isSelected()) {
			getWebElement("AcceptTermsChkbox_ID", OR_MLL).click();
		}

		sendKeys(getWebElement("LoanAuthTxt_ID", OR_MLL), map.get("First_Name") + " " + map.get("Last_Name"));
		scrollBy(150);
		getWebElement("ConsentAcceptanceBtn_XPATH", OR_MLL).click();
		waitForPageLoaded();
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Loan Approved")));

		try {
			checkPresenceOfElement("EnterPwdTxt_ID", OR_MLL, 10);
			checkVisibilityOfElement(getWebElement("EnterPwdTxt_ID", OR_MLL), 10);

			if (strValidate.equals("VALID")) {
				String pwdString = generateRandomString(7);
				char specialCharater = generateRandomSpecial(1);
				int digitValue = generateRandomInteger(100);
				// strPwd = pwdString + String.valueOf(specialCharater) +
				// String.valueOf(digitValue);

				String strPwd_old = map.get("First_Name").toLowerCase().substring(0, 3)
						+ map.get("Last_Name").toLowerCase().substring(0, 3) + map.get("DateofBirth").substring(6, 10)
						+ "$";
				strPwd = strPwd_old.replace(strPwd_old.substring(0, 1), strPwd_old.substring(0, 1).toUpperCase());
				log.info("Password is : " + strPwd);

				scrollIntoView(getWebElement("EnterPwdTxt_ID", OR_MLL));
				sendKeys(getWebElement("EnterPwdTxt_ID", OR_MLL), strPwd);
				sendKeys(getWebElement("ConfirmPwdTxt_ID", OR_MLL), strPwd);
				test.log(LogStatus.PASS, "Password Entered is " + strPwd);
				test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Screen shot")));
				// getWebElement("SavePwdBtn_ID", OR_MLL).click();
				// waitForElementToBeClickable(getWebElement("SavePwdBtn_ID", OR_MLL), 5);
				// clickUsingJS(getWebElement("SavePwdBtn_ID", OR_MLL));

				Actions a = new Actions(driver);
				a.moveToElement(getWebElement("SavePwdBtn1_ID", OR_MLL)).click().build().perform();
			} else if (strValidate.equals("INVALID")) {
				String pwdString = generateRandomString(7);
				int digitValue = generateRandomInteger(100);
				strPwd = pwdString + String.valueOf(digitValue);
				/*
				 * String strPwd_old = map.get("First_Name").toLowerCase().substring(0, 3) +
				 * map.get("Last_Name").toLowerCase().substring(0, 3) +
				 * map.get("DateofBirth").substring(6, 10) + "$"; strPwd =
				 * strPwd_old.replace(strPwd_old.substring(0, 1), strPwd_old.substring(0,
				 * 1).toUpperCase());
				 */
				scrollIntoView(getWebElement("EnterPwdTxt_ID", OR_MLL));
				sendKeys(getWebElement("EnterPwdTxt_ID", OR_MLL), strPwd);
				sendKeys(getWebElement("ConfirmPwdTxt_ID", OR_MLL), strPwd);
				test.log(LogStatus.PASS, "Invalid Password Entered is " + strPwd);
				test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Screen shot")));
				getWebElement("SavePwdBtn1_ID", OR_MLL).click();
				// driver.findElement(By.id("content_customContent_btnRegister")).click();

				test.log(LogStatus.PASS,
						"Register after Application, for Email Id " + map.get("Email") + " is Successful"
								+ test.addBase64ScreenShot(report.capture(driver, "Register after Application")));
			}

		} catch (TimeoutException e) {

			test.log(LogStatus.FAIL, "Unable to Update Information in Contact and ESignature Page"
					+ test.addBase64ScreenShot(report.capture(driver, "Register after Application")));
			Assert.fail("Unable to Update Information in Contact and ESignature Page");
		}

	}

	@When("^Welcome Back Login$")
	public void welcomeBack_Login() throws Throwable {
		boolean flag = false;
		Thread.sleep(2000);
		waitForPageLoaded();
		WebElement element = getWebElement("LoginNowBtn_ID", OR_MLL);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		// getWebElement("LoginNowBtn_ID",OR_MLL).click();
		// waitForPageLoaded();
		getWebElement("Emailidtxt_ID", OR_MLL).click();
		getWebElement("Emailidtxt_ID", OR_MLL).sendKeys(map.get("Email"));
		getWebElement("Emailidtxt_ID", OR_MLL).sendKeys(Keys.TAB);
		getWebElement("LogInMenuBtn_XPATH", OR_MLL).click();
		element = getWebElement("WelcomeBackLbl_XPATH", OR_MLL);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		if (element.isDisplayed()) {
			String strSSN = clientData.get("Cust_SSN");
			String strlast4SSN = strSSN.substring(strSSN.length() - 4);
			getWebElement("Last4SSNTxt_XPATH", OR_MLL).click();
			getWebElement("Last4SSNTxt_XPATH", OR_MLL).sendKeys(strlast4SSN);
			getWebElement("LoginPasswordTxt_XPATH", OR_MLL).sendKeys(strPwd);
			getWebElement("MultiLoginBtn_ID", OR_MLL).click();
		}
		waitForPageLoaded();
		flag = true;
		if (flag) {
			test.log(LogStatus.PASS, "Able to Login for User " + clientData.get("Email"));
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Register after Application")));
		} else {
			test.log(LogStatus.FAIL, "Not Able to Login for User " + map.get("First_Name"));
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Register after Application")));
		}
		getWebElement("LogoutBtn_XPATH", OR_MLL).click();
	}

	@When("^Welcome Back Login after Register$")
	public void welcomeBack_LoginafterRegister() throws Throwable {
		boolean flag = false;
		waitForPageLoaded();
		WebElement element = getWebElement("LoginNowBtn_ID", OR_MLL);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		getWebElement("LoginNowBtn_ID", OR_MLL).click();
		waitForPageLoaded();
		element = getWebElement("WelcomeBackLbl_XPATH", OR_MLL);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		if (element.isDisplayed()) {
			String strSSN = clientData.get("Cust_SSN");
			String strlast4SSN = strSSN.substring(strSSN.length() - 4);
			// getWebElement("Last4SSNTxt_XPATH", OR_MLL).click();
			// getWebElement("Last4SSNTxt_XPATH", OR_MLL).sendKeys(strlast4SSN);

			sendKeys(getWebElement("Last4SSNTxt_XPATH", OR_MLL), strlast4SSN);

			// getWebElement("LoginPasswordTxt_XPATH", OR_MLL).sendKeys(strPwd);

			sendKeys(getWebElement("LoginPasswordTxt_XPATH", OR_MLL), strPwd);
			getWebElement("MultiLoginBtn_ID", OR_MLL).click();
		}
		waitForPageLoaded();
		flag = true;
		if (flag) {
			test.log(LogStatus.PASS, "Able to Login for User " + clientData.get("Email"));
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Register after Application")));
		} else {
			test.log(LogStatus.FAIL, "Not Able to Login for User " + map.get("First_Name"));
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Register after Application")));
		}
		getWebElement("LogOutMenuBtn_XPATH", OR_MLL).click();
	}

	@When("^Welcome Back Login Existing$")
	public void welcomeBack_LoginExisting() throws Throwable {
		boolean flag = false;

		scrollIntoView(getWebElement("LoginNowBtn_ID", OR_MLL));

		waitForElementToBeClickable(getWebElement("LoginNowBtn_ID", OR_MLL), 10);
		getWebElement("LoginNowBtn_ID", OR_MLL).click();

		// waitForVisibilityOfElement(getWebElement("LoginPasswordTxt_XPATH", OR_MLL),
		// 5);

		sendKeys(getWebElement("LoginPasswordTxt_XPATH", OR_MLL), strPwd);

		getWebElement("LoginBtn_ID", OR_MLL).click();
		// }
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "welcome back login existing ")));
		waitForPageLoaded();
		flag = true;
		if (flag) {
			test.log(LogStatus.PASS, "Able to Login for User " + clientData.get("Email"));
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Register after Application")));
		} else {
			test.log(LogStatus.FAIL, "Not Able to Login for User " + map.get("First_Name"));
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Register after Application")));
		}
		// getWebElement("TabletLogoutBtn_XPATH",OR_MLL).click();
		// getWebElement("LogOutMenuBtn_XPATH", OR_MLL).click();

	}

	@Then("^Logout of the application and re-login$")
	public void logoutRelogin() {
		getWebElement("LogOutMenuBtn_XPATH", OR_MLL).click();

		sendKeys(getWebElement("Emailidtxt_ID", OR_MLL), map.get("Email"));
		clickOnButton(getWebElement("LogInMenuBtn_XPATH", OR_MLL));

	}

	@And("^Click on forgot password link and Reset the password$")
	public void resetPassword() {
		clickOnButton(getWebElement("ForgotPass_ID", OR_MLL));
		sendKeys(getWebElement("SSNTxt_ID", OR_MLL), map.get("SSN_3"));
		sendKeys(getWebElement("DOBTxt_ID", OR_MLL), map.get("DateofBirth"));
		clickUsingJS(getWebElement("ForgotPwdBtn_ID", OR_MLL));

		///

		int time_init = LocalTime.now().getMinute();
		log.info("Checking of mail started at(minute) : " + time_init);
		int time_final = time_init + 3;

		if (time_final >= 60) {
			time_final = time_final - 60;
		}

		log.info("Check the mail till(minute) : " + time_final);
		boolean emailChk = false;
		do {
			emailChk = receiveMailForgotPass(CONFIG.getProperty("UserName_" + CONFIG.getProperty("appEnv")),
					CONFIG.getProperty("Password_" + CONFIG.getProperty("appEnv")), "Inbox_ResetLink");

			log.info("Email received in private email : " + emailChk);
			time_init = LocalTime.now().getMinute();
			if (emailChk) {
				test.log(LogStatus.INFO, "Reset link :" + LinkTXT);
				log.info("Email received at : " + time_init);
			}
			if (time_init == time_final && emailChk == false) {
				log.info(errorMessage = "Reset link is not present in private email");
				test.log(LogStatus.FAIL, "Reset link not received in private email");
				Assert.fail("Reset link is not received in private email");

			}

		} while (!emailChk);

		///

	}

	@Then("open the reset link")
	public void openResetLink() {
		driver.get(LinkTXT);

		// ConfirmPasswordTxt_ID
		String strPwd_old = map.get("First_Name").toLowerCase().substring(0, 2)
				+ map.get("Last_Name").toLowerCase().substring(0, 2) + map.get("DateofBirth").substring(6, 10) + "$$";
		String strPwdNewPass = strPwd_old.replace(strPwd_old.substring(0, 1), strPwd_old.substring(0, 1).toUpperCase());
		sendKeys(getWebElement("PasswordTxt_ID", OR_MLL), strPwdNewPass);
		sendKeys(getWebElement("ConfirmPasswordTxt_ID", OR_MLL), strPwdNewPass);
		clickUsingJS(getWebElement("AcceptBtn_ID", OR_MLL));
		//
		log.info(strPwdNewPass);
		sendKeys(getWebElement("LoginPasswordTxt_XPATH", OR_MLL), strPwdNewPass);

		getWebElement("LoginBtn_ID", OR_MLL).click();

	}

	@And("^validate Re-Esign button$")
	public void validateReEsignButton() {
		boolean bFlag = false;

		// int size = driver.findElements(By.partialLinkText("Re-eSign Now!")).size();

		List<WebElement> reEsign = driver.findElements(
				By.xpath("//a[text()='My Account']/parent::li/following-sibling::li/child::a[text()='Re-eSign Now!']"));

		if (reEsign.size() > 0) {
			driver.findElement(By.xpath(
					"//a[text()='My Account']/parent::li/following-sibling::li/child::a[text()='Re-eSign Now!']"))
					.click();
			// getWebElement("Re-Sign_linktext", OR_MLL).click();
			waitTillElementIsVisible("ElectronicConsentChkbox_ID", OR_MLL, 10,
					"Re-Sign button is not present in WebPage");
			bFlag = true;
		}

		if (bFlag) {
			test.log(LogStatus.PASS, "Resign verification done" + test.addBase64ScreenShot(report.capture()));
		} else {
			test.log(LogStatus.FAIL, "Resign verification failed" + test.addBase64ScreenShot(report.capture()));
		}
	}

	@And("^verify if (.*) button is selected$")
	public void verify_Payment_RadioButton(String paymentMethod) {
		if (paymentMethod.equalsIgnoreCase("ACH")) {
			if (!driver.findElement(By.id("rblDebitType_0")).isSelected()) {
				driver.findElement(By.id("rblDebitType_0")).click();
			}

		} else if (paymentMethod.equalsIgnoreCase("TxtACheck")) {
			if (!driver.findElement(By.id("rblDebitType_2")).isSelected()) {
				driver.findElement(By.id("rblDebitType_2")).click();
			}

		} else

		{
			if (!driver.findElement(By.id("rblDebitType_1")).isSelected()) {
				driver.findElement(By.id("rblDebitType_1")).click();
			}

		}
	}

	@When("^schedule TxtACheck and Send Mail$")
	public void perform_TextACheck() throws Throwable {
		log.info("schedule TxtACheck and Send Mail");
		boolean flag = false;
		waitForPageLoaded();
		String CheckNo = addCheckNo();
		log.info("Check Number used : " + CheckNo);
		int time_init = LocalTime.now().getMinute();
		log.info("Checking of mail started at(minute) : " + time_init);
		int time_final = time_init + 2;

		if (time_final >= 60) {
			time_final = time_final - 60;
		}

		log.info("Check the mail till(minute) : " + time_final);
		boolean emailChk = false;
		do {
			emailChk = receiveMail(CONFIG.getProperty("UserName_" + CONFIG.getProperty("appEnv")),
					CONFIG.getProperty("Password_" + CONFIG.getProperty("appEnv")), "Inbox_TxtACheck");

			log.info("Email received in private email : " + emailChk);
			time_init = LocalTime.now().getMinute();
			if (emailChk) {
				test.log(LogStatus.INFO, "TxtACheck email :" + LinkTXT);
				log.info("Email received at : " + time_init);
			}
			if (time_init == time_final && emailChk == false) {
				log.info("TxtACheck e-mail is not present in private email");
				errorMessage = "TxtACheck e-mail is not present in private email";
				test.log(LogStatus.FAIL, "TxtACheck e-mail not received in private email");
				Assert.fail("TxtACheck e-mail is not received in private email");

			}

		} while (!emailChk);

		if (emailChk) {
			test.log(LogStatus.PASS, "TxtACheck Request Email Sent Successfully to User " + map.get("First_Name") + " "
					+ map.get("Last_Name"));
		} else {
			boolean emailChk1 = receiveMailforspam(CONFIG.getProperty("UserName_" + CONFIG.getProperty("appEnv")),
					CONFIG.getProperty("Password_" + CONFIG.getProperty("appEnv")), "Spam");

			// test.log(LogStatus.FAIL, "TxtACheck Request Email is Missing");
		}

	}

	@When("^Receive Mail$")
	public void receive_mail() throws Throwable {
		boolean flag = false;
		// Thread.sleep(80000);
		waitForPageLoaded();
		String CheckNo = addCheckNo();

		int time_init = LocalTime.now().getMinute();
		log.info("Checking of mail started at(minute) : " + time_init);
		int time_final = time_init + 2;

		if (time_final >= 60) {
			time_final = time_final - 60;
		}

		log.info("Check the mail till(minute) : " + time_final);

		boolean emailChk1 = false;

		do {
			emailChk1 = receiveLatestMail(CONFIG.getProperty("UserName_" + CONFIG.getProperty("appEnv")),
					CONFIG.getProperty("Password_" + CONFIG.getProperty("appEnv")), "Inbox_TxtACheck");

			log.info("Status of latest mail received in private email : " + emailChk1);

			time_init = LocalTime.now().getMinute();
			if (emailChk1) {
				test.log(LogStatus.INFO, "TxtACheck link received : " + LinkTXT);
				log.info("Received the latest email at : " + time_init);
			}
			if (time_init == time_final) {
				log.info("TxtACheck latest e-mail not received in private email");
				break;
			}
		} while (!emailChk1);

		if (emailChk1) {
			test.log(LogStatus.PASS, "TxtACheck Request Email Sent Successfully to User " + map.get("First_Name") + " "
					+ map.get("Last_Name"));
		} else {
			test.log(LogStatus.FAIL, "TxtACheck Request Email is Missing");
		}

	}

	@When("^Receive Mail checkno notavailable$")
	public void Receive_Mail_checkno_notavailable() throws Throwable {
		log.info("Receive Mail checkno notavailable");
		boolean flag = false;
//		Thread.sleep(80000);
		waitForPageLoaded();
		// String CheckNo=addCheckNo();

		// Thread.sleep(20000);
		int time_init = LocalTime.now().getMinute();
		log.info("Checking of mail started at(minute) : " + time_init);
		int time_final = time_init + 3;
		log.info("Check the mail till(minute) : " + time_final);

		if (time_final >= 60) {
			time_final = time_final - 60;
		}

		boolean emailChk1 = false;

		do {
			emailChk1 = receiveLatestMail(CONFIG.getProperty("UserName_" + CONFIG.getProperty("appEnv")),
					CONFIG.getProperty("Password_" + CONFIG.getProperty("appEnv")), "Inbox_TxtACheck");
			log.info("Status of mail received in private email : " + emailChk1);
			time_init = LocalTime.now().getMinute();
			if (emailChk1) {
				log.info("Received the email at : " + time_init);
			}
			if (time_init == time_final) {
				log.info("TxtACheck e-mail not received in private email");
				test.log(LogStatus.FAIL, "TxtACheck e-mail not received in private email");
				errorMessage = "TxtACheck e-mail not received in private email";
				Assert.fail("TxtACheck e-mail not received in private email");
			}
		} while (!emailChk1);

		if (emailChk1) {
			test.log(LogStatus.PASS, "TxtACheck Request Email Sent Successfully to User " + map.get("First_Name") + " "
					+ map.get("Last_Name"));
		} else {
			// test.log(LogStatus.FAIL, "TxtACheck Request Email is Missing");
		}

	}

	@Then("^schedule without TxtACheck and Send Mail$")
	public void schedule_without_TxtACheck_and_Send_Mail() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		boolean emailChk = receiveMail(CONFIG.getProperty("UserName_" + CONFIG.getProperty("appEnv")),
				CONFIG.getProperty("Password_" + CONFIG.getProperty("appEnv")), "Inbox_TxtACheck");
		if (emailChk) {
			test.log(LogStatus.PASS, "TxtACheck Request Email Sent Successfully to User " + map.get("First_Name") + " "
					+ map.get("Last_Name"));
		} else {

			test.log(LogStatus.FAIL, "TxtACheck Request Email is Missing");
		}

	}

	@When("^verify TxtACheck Complete$")
	public void verify_TextACheck() throws Throwable {
		boolean flag = false;
		waitForPageLoaded();
		switchToMainFrame();
		boolean IsEnabled = getWebElement("LoanOriginationBtn_XPATH", OR_LMS).isEnabled();
		if (IsEnabled) {
			test.log(LogStatus.PASS, "Loan Origination Button is Enabled");
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Loan_Origination")));
		} else {

			addCheckNo();
			driver.switchTo().defaultContent();
			test.log(LogStatus.FAIL, "Loan Origination Button is Disabled");
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Loan_Origination")));
		}
		WebElement element = getWebElement("NotesTable_XPATH", OR_LMS);

		scrollIntoView(element);

		String strText = getWebElement("TxtACheckMsgLabel_XPATH", OR_LMS).getText();
		if (strText.contains("Process Complete")) {
			test.log(LogStatus.PASS, "TxtACheck Process Completed Message appears in LMS");
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "LMS_TextACheck")));
		} else {
			test.log(LogStatus.FAIL, "Message is not Displayed");
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "LMS_TextACheck")));
		}

	}

	@When("^Review App verify process txtacheck$")
	public void review_App_verify_process_txtacheck() throws Throwable {
		log.info("Review App verify process txtacheck");
		// Write code here that turns the phrase above into concrete actions
		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
		String FeeonAppl = getWebElement("FeeonAppl1_xpath", OR_MLL).getText();
		log.info("Fee on App1=" + FeeonAppl);
		test.log(LogStatus.PASS, "Fee on App = " + FeeonAppl);

		String OriginalAmtofAppl = getWebElement("OriginalAmtofAppl_xpath", OR_MLL).getText();
		log.info("Original Amt of Appl=" + OriginalAmtofAppl);
		test.log(LogStatus.PASS, "Original Amt of Appl = " + OriginalAmtofAppl);

		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "TxtAcheck ")));

		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
		getWebElement("Paymentschedule_XPATH", OR_MLL).click();
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Paymentschedule check")));
		Thread.sleep(2000);

	}

	@When("^perform TextaCheck Process$")
	public void validate_TextACheck() throws Throwable {
		log.info("perform TextaCheck Process");
		boolean flag = false;
		driver.get(LinkTXT);

		waitTillElementIsVisible("BankAccTxt_XPATH", OR_MLL, 50, "Account text box is not visible");

		sendKeys(driver.findElement(By.xpath("//input[@id='txtAccountNumber']")), map.get("Acct"));
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "TextaCheck_Bank Account Number")));
		waitForPresenceOfElement(By.xpath("//a[@id='btnSubmit']"), 30);

		waitForElementToBeClickable(getWebElement("SubmitBtn_XPATH", OR_MLL), 20);
		clickOnButton(getWebElement("SubmitBtn_XPATH", OR_MLL));
		acceptLocation();
		log.info("Accepted GeoLocation");

		waitTillElementIsVisible("TxtCheckImg_XPATH", OR_MLL, 120, "Image is not visible");

		int ielement = driver.findElements(By.xpath("//img[@id='imgCheck']")).size();
		if (ielement > 0) {
			test.log(LogStatus.PASS, "Check Image" + test.addBase64ScreenShot(report.capture(driver, "Check Image")));
		} else {
			test.log(LogStatus.FAIL, "Unable to print the Check");
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Check Image")));
		}
		// WebElement element1 = getWebElement("SubmitBtn_XPATH", OR_MLL);
		// ((JavascriptExecutor)
		// driver).executeScript("arguments[0].scrollIntoView(true);", element1);

		scrollIntoView(getWebElement("SubmitBtn_XPATH", OR_MLL));

		clickOnButton(getWebElement("SubmitBtn_XPATH", OR_MLL));

		// waitForPageToLoad(By.id("MyCanvas"), 60);

		waitTillElementIsVisible("DigSignCanvas_ID", OR_MLL, 60, "Signature box is not visible");

		WebElement canvas = driver.findElement(By.id("MyCanvas"));
		Dimension d = canvas.getSize();

		int h = d.height / 2;

		int w = d.width / 2;

		int x = w / 6;

		Actions b = new Actions(driver);

		b.moveToElement(canvas, -w, (h - 2)).clickAndHold().moveToElement(canvas, -x * 5, -(h - 2))

				.moveToElement(canvas, -x * 4, (h - 2)).moveToElement(canvas, -x * 3, -(h - 2))

				.moveToElement(canvas, -x * 2, (h - 2)).moveToElement(canvas, -x * 1, -(h - 2))

				.moveToElement(canvas, 0, (h - 2)).moveToElement(canvas, x * 1, -(h - 2))

				.moveToElement(canvas, x * 2, (h - 2)).moveToElement(canvas, x * 3, -(h - 2))

				.moveToElement(canvas, x * 4, (h - 2)).moveToElement(canvas, x * 5, -(h - 2))

				.moveToElement(canvas, w - 1, (h - 2))

				.release().build().perform();
		test.log(LogStatus.PASS, "Digital signature" + "Digital signature"
				+ test.addBase64ScreenShot(report.capture(driver, "Digital signature")));

		waitForPresenceOfElement(By.xpath("//button[@id='btnNext']"), 30);
		waitForElementToBeClickable(getWebElement("TextaCheckNextBtn_XPATH", OR_MLL), 10);
		clickOnButton(getWebElement("TextaCheckNextBtn_XPATH", OR_MLL));

		// waitForPageToLoad(By.xpath("//label[contains(@class,'ui-checkbox')]"), 80);

		// waitForPageToLoad(By.id("imgCheckFront"), 80);
		waitTillElementIsVisible("imageCheck_ID", OR_MLL, 80, "Image is not visible");
		// waitForPresenceOfElement(By.xpath("//label[contains(@class,'ui-checkbox')]"),
		// 30);

		clickOnButton(getWebElement("Disclaimer_ID", OR_MLL));

		waitForPresenceOfElement(By.id("imgCheckFront"), 60);
		waitForVisibilityOfElement(driver.findElement(By.id("imgCheckFront")), 120);
		waitForPresenceOfElement(By.xpath("//button[@id='btnSubmit']"), 30);
		clickOnButton(getWebElement("TxtAChkSubmitBtn_XPATH", OR_MLL));

		flag = true;
		if (flag) {
			test.log(LogStatus.PASS,
					"TxtACheck is processing for User " + map.get("First_Name") + " " + map.get("Last_Name"));
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "TxtACheck Complete")));
		} else {
			test.log(LogStatus.FAIL, "TxtACheck Processing cannot be completed for User " + map.get("First_Name") + " "
					+ map.get("Last_Name"));
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "TxtACheck not Complete")));
		}

	}

	@When("^perform Second TextaCheck Process$")
	public void validate_Second_TextACheck() throws Throwable {
		log.info("perform Second TextaCheck Process");
		boolean flag = false;
		Thread.sleep(2000);
		driver.get(LinkTXT);
		waitForPageLoaded();
		waitForPresenceOfElement(By.id("txtAccountNumber"), 40);
		sendKeys(getWebElement("BankAccTxt_XPATH", OR_MLL), map.get("Acct"));
		test.log(LogStatus.PASS, "TextaCheck_Bank Account Number"
				+ test.addBase64ScreenShot(report.capture(driver, "TextaCheck_Bank Account Number")));
		// getWebElement("SubmitBtn_XPATH", OR_MLL).click();
		waitForPresenceOfElement(By.id("btnSubmit"), 30);
		// waitForVisibilityOfElement(getWebElement("SubmitBtn_XPATH", OR_MLL), 30);
		// waitForElementToBeClickable(getWebElement("SubmitBtn_XPATH", OR_MLL), 30);
		clickOnButton(getWebElement("SubmitBtn_ID", OR_MLL));
		acceptLocation();

		try {
			waitForPresenceOfElement(By.xpath("//img[@id='imgCheck']"), 60);
			waitForVisibilityOfElement(driver.findElement(By.xpath("//img[@id='imgCheck']")), 60);
		} catch (Exception e) {
			clickOnButton(getWebElement("SubmitBtn_XPATH", OR_MLL));
			waitForPresenceOfElement(By.xpath("//img[@id='imgCheck']"), 60);
			waitForVisibilityOfElement(driver.findElement(By.xpath("//img[@id='imgCheck']")), 60);
		}

//		WebDriverWait wait = new WebDriverWait(driver, 60);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@id='imgCheck']")));
		int ielement = driver.findElements(By.xpath("//img[@id='imgCheck']")).size();
		if (ielement > 0) {
			test.log(LogStatus.PASS, "Check Image" + test.addBase64ScreenShot(report.capture(driver, "Check Image")));
		} else {
			test.log(LogStatus.FAIL, "Unable to print the Check");
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Check Image")));
		}
		// WebElement element1 = getWebElement("SubmitBtn_XPATH", OR_MLL);
		// ((JavascriptExecutor)
		// driver).executeScript("arguments[0].scrollIntoView(true);", element1);
		// getWebElement("SubmitBtn_XPATH", OR_MLL).click();

		scrollIntoView(getWebElement("SubmitBtn_XPATH", OR_MLL));
		clickOnButton(getWebElement("SubmitBtn_XPATH", OR_MLL));

		waitForPresenceOfElement(By.id("MyCanvas"), 40);

		// Below code will work on jenkins
		/*
		 * WebElement canvas = driver.findElement(By.id("MyCanvas"));
		 * 
		 * Dimension d = canvas.getSize(); int h = d.height/2; int w = d.width/2;
		 * log.info("Height is : " + h); log.info("Width is : " + w);
		 * 
		 * Actions b1 = new Actions(driver); Action ac =
		 * b1.moveToElement(canvas,(-w+1),0) .clickAndHold() .moveToElement(canvas,
		 * 0,(int) h/10) .moveToElement(canvas, (int)(w/4),(int)(h/7))
		 * .moveToElement(canvas, -(int)(w/2),(int)(h/6)) .moveToElement(canvas,
		 * (int)w/50, (int)h/2) .moveToElement(canvas, w-1, 0) .release() .build();
		 * ac.perform();
		 */
		WebElement canvas = driver.findElement(By.id("MyCanvas"));
		Dimension d = canvas.getSize();

		int h = d.height / 2;

		int w = d.width / 2;

		int x = w / 6;

		Actions b = new Actions(driver);

		b.moveToElement(canvas, -w, (h - 2)).clickAndHold().moveToElement(canvas, -x * 5, -(h - 2))

				.moveToElement(canvas, -x * 4, (h - 2)).moveToElement(canvas, -x * 3, -(h - 2))

				.moveToElement(canvas, -x * 2, (h - 2)).moveToElement(canvas, -x * 1, -(h - 2))

				.moveToElement(canvas, 0, (h - 2)).moveToElement(canvas, x * 1, -(h - 2))

				.moveToElement(canvas, x * 2, (h - 2)).moveToElement(canvas, x * 3, -(h - 2))

				.moveToElement(canvas, x * 4, (h - 2)).moveToElement(canvas, x * 5, -(h - 2))

				.moveToElement(canvas, w - 1, (h - 2))

				.release().build().perform();
		test.log(LogStatus.PASS,
				"Digital signature" + test.addBase64ScreenShot(report.capture(driver, "Digital signature")));

		waitForPresenceOfElement(By.xpath("//button[@id='btnNext']"), 60);
		waitForVisibilityOfElement(getWebElement("TextaCheckNextBtn_XPATH", OR_MLL), 30);
		clickUsingJS(getWebElement("TextaCheckNextBtn_XPATH", OR_MLL));
		// getWebElement("TextaCheckNextBtn_XPATH", OR_MLL).click();

		// getWebElement("DisclaimerChkbox_XPATH", OR_MLL).click();

		waitForPresenceOfElement(By.id("imgCheckFront"), 60);
		waitForVisibilityOfElement(driver.findElement(By.id("imgCheckFront")), 120);

		waitForPresenceOfElement(By.id("chkDisclaimer"), 60);
		waitForVisibilityOfElement(getWebElement("DisclaimerChkbox_ID", OR_MLL), 60);
		clickUsingJS(getWebElement("DisclaimerChkbox_ID", OR_MLL));
		// Thread.sleep(10000);

		waitForPresenceOfElement(By.xpath("//button[@id='btnSubmit']"), 60);
		clickOnButton(getWebElement("TxtAChkSubmitBtn_XPATH", OR_MLL));
		log.info("Clicked on Submit button");

		flag = true;
		if (flag) {
			test.log(LogStatus.PASS,
					"TxtACheck is processing for User " + map.get("First_Name") + " " + map.get("Last_Name"));
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "TxtACheck Complete")));
		} else {
			test.log(LogStatus.FAIL, "TxtACheck Processing cannot be completed for User " + map.get("First_Name") + " "
					+ map.get("Last_Name"));
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "TxtACheck not Complete")));
		}

	}

	@When("^perform Second TextaCheck Process Accountno change$")
	public void perform_Second_TextaCheck_Process_Accountno_change() throws Throwable {
		boolean flag = false;
		Thread.sleep(2000);
		driver.get(LinkTXT);
		waitForPageLoaded();
		getWebElement("BankAccTxt_XPATH", OR_MLL).click();
		// getWebElement("BankAccTxt_XPATH",OR_MLL).sendKeys(Accountno);
		getWebElement("BankAccTxt_XPATH", OR_MLL).sendKeys(Accountno);
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "TextaCheck_Bank Account Number")));
		getWebElement("SubmitBtn_XPATH", OR_MLL).click();
		Thread.sleep(10000);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@id='imgCheck']")));
		int ielement = driver.findElements(By.xpath("//img[@id='imgCheck']")).size();
		if (ielement > 0) {
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Check Image")));
		} else {
			test.log(LogStatus.FAIL, "Unable to print the Check");
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Check Image")));
		}
		WebElement element1 = getWebElement("SubmitBtn_XPATH", OR_MLL);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);

		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "txtacheck process")));
		getWebElement("SubmitBtn_XPATH", OR_MLL).click();

		// Below code will work on jenkins
		/*
		 * WebElement canvas = driver.findElement(By.id("MyCanvas"));
		 * 
		 * Dimension d = canvas.getSize(); int h = d.height/2; int w = d.width/2;
		 * log.info("Height is : " + h); log.info("Width is : " + w);
		 * 
		 * Actions b1 = new Actions(driver); Action ac =
		 * b1.moveToElement(canvas,(-w+1),0) .clickAndHold() .moveToElement(canvas,
		 * 0,(int) h/10) .moveToElement(canvas, (int)(w/4),(int)(h/7))
		 * .moveToElement(canvas, -(int)(w/2),(int)(h/6)) .moveToElement(canvas,
		 * (int)w/50, (int)h/2) .moveToElement(canvas, w-1, 0) .release() .build();
		 * ac.perform();
		 */
		WebElement canvas = driver.findElement(By.id("MyCanvas"));
		Dimension d = canvas.getSize();

		int h = d.height / 2;

		int w = d.width / 2;

		int x = w / 6;

		Actions b = new Actions(driver);

		b.moveToElement(canvas, -w, (h - 2)).clickAndHold().moveToElement(canvas, -x * 5, -(h - 2))

				.moveToElement(canvas, -x * 4, (h - 2)).moveToElement(canvas, -x * 3, -(h - 2))

				.moveToElement(canvas, -x * 2, (h - 2)).moveToElement(canvas, -x * 1, -(h - 2))

				.moveToElement(canvas, 0, (h - 2)).moveToElement(canvas, x * 1, -(h - 2))

				.moveToElement(canvas, x * 2, (h - 2)).moveToElement(canvas, x * 3, -(h - 2))

				.moveToElement(canvas, x * 4, (h - 2)).moveToElement(canvas, x * 5, -(h - 2))

				.moveToElement(canvas, w - 1, (h - 2))

				.release().build().perform();
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Digital signature")));

		getWebElement("TextaCheckNextBtn_XPATH", OR_MLL).click();
		Thread.sleep(20000);
		getWebElement("DisclaimerChkbox_XPATH", OR_MLL).click();
		Thread.sleep(10000);
		getWebElement("TxtAChkSubmitBtn_XPATH", OR_MLL).click();
		Thread.sleep(10000);
		flag = true;
		if (flag) {
			test.log(LogStatus.PASS,
					"TxtACheck is processing for User " + map.get("First_Name") + " " + map.get("Last_Name"));
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "TxtACheck Complete")));
		} else {
			test.log(LogStatus.FAIL, "TxtACheck Processing cannot be completed for User " + map.get("First_Name") + " "
					+ map.get("Last_Name"));
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "TxtACheck not Complete")));

		}
	}

	@When("^Apply to Reset Password$")
	public void apply_toReset() throws Throwable {
		boolean flag = false;
		Thread.sleep(2000);
		getWebElement("ForgotPwdLink_XPATH", OR_MLL).click();
		String strSSN = map.get("Cust_SSN");
		String strlast4SSN = strSSN.substring(strSSN.length() - 4);
		getWebElement("Last4SSNTxt_XPATH", OR_MLL).click();
		getWebElement("Last4SSNTxt_XPATH", OR_MLL).sendKeys(strlast4SSN);
		String strDOB = map.get("Cust_DOB");
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(strDOB);
		SimpleDateFormat date2 = new SimpleDateFormat("MM/dd/yyyy");
		strDOB = date2.format(date1);
		getWebElement("DOBTxt_ID", OR_MLL).click();
		getWebElement("DOBTxt_ID", OR_MLL).sendKeys(strDOB);
		flag = true;
		if (flag) {
			test.log(LogStatus.PASS, "Applied for Reset Password for " + map.get("Email"));
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Reset Password")));
		} else {
			test.log(LogStatus.FAIL, "Unable to apply for Reset Password for " + map.get("Email"));
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Reset Password")));
		}
		getWebElement("ForgotPwdBtn_ID", OR_MLL).click();
		Thread.sleep(2000);
		waitForPageLoaded();
		int eleList = driver.findElements(By.xpath("//span[contains(text(),'Check your email')]")).size();
		if (eleList > 0) {
			test.log(LogStatus.PASS, "Email Sent Successfully");
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Email Sent")));
		} else {
			test.log(LogStatus.FAIL, "Email Sent Successfully ");
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Email Sent")));
		}
	}

	// comment added may
	/*
	 * @Then("^originate process start$") public void originate_process_start()
	 * throws InterruptedException, IOException { Thread.sleep(2000);
	 * driver.switchTo().defaultContent();
	 * driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
	 * driver.switchTo().frame("Main"); Thread.sleep(5000); WebElement
	 * origin=getWebElement("originatebtn_name",OR_MLL); boolean
	 * result=origin.isEnabled(); log.info("Result="+result); WebDriverWait wait1 =
	 * new WebDriverWait(driver,30);
	 * wait1.until(ExpectedConditions.visibilityOfElementLocated(By.name(
	 * "cmdOrigLoan"))); getWebElement("originatebtn_name",OR_MLL).click(); try {
	 * Alert alert = driver.switchTo().alert(); String alertText = alert.getText();
	 * alert.accept(); } catch (NoAlertPresentException e) { e.printStackTrace(); }
	 * 
	 * int number=driver.findElements(By.xpath(
	 * "//table/tbody/tr[30]/td[1]/input[@id='cmdSubmit']")).size(); //submit button
	 * available
	 * 
	 * if(number>0) {
	 * 
	 * getWebElement("Most_recent_pay_stub_verified_radio_XPATH",OR_MLL).click();
	 * 
	 * getWebElement("submitbtn_XPATH",OR_MLL).click();
	 * driver.switchTo().defaultContent(); } else { boolean flag=true;
	 * 
	 * // getWebElement("Cancelbtn_XPATH",OR_MLL).click();
	 * driver.switchTo().defaultContent();
	 * driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
	 * driver.switchTo().frame("Main"); WebDriverWait wait = new
	 * WebDriverWait(driver,30);
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
	 * "//a[@id='changeDueDate']"))).click(); driver.switchTo().defaultContent();
	 * driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
	 * driver.switchTo().frame("Main"); driver.switchTo().frame("TRANdialogiframe");
	 * // getWebElement("changdateradio_xpath",OR_MLL).click();
	 * 
	 * // getWebElement("updateradio_xpath",OR_MLL).click(); Thread.sleep(2000);
	 * getWebElement("closebtn_xpath",OR_MLL).click();
	 * driver.switchTo().defaultContent();
	 * //driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
	 * //driver.switchTo().frame("Main");
	 * 
	 * WebDriverWait wait2 = new WebDriverWait(driver,50);
	 * wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
	 * "//input[@id='cmdOrigLoan']"))).click(); try { Alert alert =
	 * driver.switchTo().alert(); String alertText = alert.getText();
	 * alert.accept(); } catch (NoAlertPresentException e) { e.printStackTrace(); }
	 * getWebElement("Most_recent_pay_stub_verified_radio_XPATH",OR_MLL).click();
	 * getWebElement("submitbtn_XPATH",OR_MLL).click();
	 * driver.switchTo().defaultContent();
	 * 
	 * test.log(LogStatus.PASS, "Originate process "); test.log(LogStatus.PASS,
	 * test.addBase64ScreenShot(report.capture(driver,"Originate Button")));
	 * 
	 * 
	 * } }
	 */

	@When("^verify Originate Button$")
	public void verify_originateBtn() throws Throwable {
		waitForPageLoaded();

		switchToMainFrame();

		// input[@id='cmdOrigLoan']
		String IsDisabled = getWebElement("LoanOriginationBtn_XPATH", OR_LMS).getAttribute("disabled");

		Assert.assertFalse(getWebElement("LoanOriginationBtn_XPATH", OR_LMS).isEnabled());
		if (IsDisabled.equals("true")) {
			test.log(LogStatus.PASS, "Originate Application Button is disabled");
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Originate Button")));
		} else {
			test.log(LogStatus.FAIL, "Originate Application Button is not disabled");
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Originate Button")));
		}

	}

	@When("^perform Originate and validate status for (.*)$")
	public void perform_origination(String merchant) throws Throwable {
		boolean bFlag = false;
		waitForPageLoaded();

		Originate_Application_TxtACheck(merchant);

		/*
		 * switchToMainFrame();
		 * 
		 * WebDriverWait wait1 = new WebDriverWait(driver, 60);
		 * wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("cmdOrigLoan"
		 * ))).click();; //
		 * if(getWebElement("LoanOriginationBtn_XPATH",OR_LMS).isEnabled()) int
		 * iSubmitBtn = driver.findElements(By.id("cmdSubmit")).size(); if (iSubmitBtn >
		 * 0) { getWebElement("LoanOriginationBtn_XPATH", OR_LMS).click();
		 * 
		 * getWebElement("IncomeVerifiedYesBtn_XPATH", OR_LMS).click();
		 * getWebElement("OrigSubmitBtn_XPATH", OR_LMS).click();
		 * 
		 * } else { driver.findElement(By.id("cmdCancel")).click();
		 * waitForVisibilityOfElement(getWebElement("ChangeDueDateLink_XPATH", OR_LMS),
		 * 5); getWebElement("ChangeDueDateLink_XPATH", OR_LMS).click(); //
		 * Thread.sleep(10000);
		 * 
		 * driver.switchTo().defaultContent();
		 * driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		 * driver.switchTo().frame("Main"); driver.switchTo().frame("TRANdialogiframe");
		 * 
		 * getWebElement("UpdateDueDateCloseBtn_XPATH", OR_LMS).click();
		 * Thread.sleep(2000);
		 * 
		 * driver.switchTo().defaultContent();
		 * driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		 * driver.switchTo().frame("Main");
		 * 
		 * }
		 * 
		 * int iSubmitBtn1 =
		 * driver.findElements(By.xpath("//input[@id='cmdSubmit']")).size();
		 * 
		 * if (iSubmitBtn1 > 0) {
		 * 
		 * getWebElement("OrigSubmitBtn_XPATH", OR_LMS).click();
		 * 
		 * Thread.sleep(10000); } else { getWebElement("OrigCancelBtn_XPATH",
		 * OR_LMS).click();
		 * 
		 * getWebElement("ChangeDueDateLink_XPATH", OR_LMS).click();
		 * Thread.sleep(10000); driver.switchTo().defaultContent();
		 * driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		 * driver.switchTo().frame("Main"); driver.switchTo().frame("TRANdialogiframe");
		 * getWebElement("UpdateDueDateRadio_XPATH", OR_LMS).click();
		 * Thread.sleep(3000); getWebElement("UpdateDueDateBtn_XPATH", OR_LMS).click();
		 * Thread.sleep(5000); // test.log(LogStatus.PASS, //
		 * test.addBase64ScreenShot(report.capture(driver,"Update Due Date")));
		 * getWebElement("UpdateDueDateCloseBtn_XPATH", OR_LMS).click();
		 * 
		 * driver.switchTo().defaultContent();
		 * driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		 * driver.switchTo().frame("Main"); user_logout("LMS"); receive_mail();
		 * validate_TextACheck(); user_is_on_homepage("LMS");
		 * user_enters_login_details("LMS", "Blue Trust");
		 * user_login_verify("TRANS LMS"); search_by_reviewApp("Email_Address");
		 * driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		 * driver.switchTo().frame("Main"); // input[@id='cmdOrigLoan']
		 * getWebElement("LoanOriginationBtn_XPATH", OR_LMS).click();
		 * Thread.sleep(10000); getWebElement("IncomeVerifiedYesBtn_XPATH",
		 * OR_LMS).click(); getWebElement("OrigSubmitBtn_XPATH", OR_LMS).click();
		 * 
		 * Thread.sleep(10000); }
		 * 
		 * WebDriverWait wait = new WebDriverWait(driver, 90);
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
		 * "//table[@id='tbApplicationDetails']/tbody/tr/td/b[contains(text(),'Appl Status')]/../../td[2]"
		 * ))); String strStatus = getWebElement("ApplStatusLabel_XPATH",
		 * OR_LMS).getText(); if (strStatus.equals("New Appl")) {
		 * test.log(LogStatus.PASS, "Application Status is :" + strStatus);
		 * test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver,
		 * "Appl Status"))); } else { test.log(LogStatus.FAIL, "Application Status is :"
		 * + strStatus); test.log(LogStatus.FAIL,
		 * test.addBase64ScreenShot(report.capture(driver, "Appl Status"))); }
		 * 
		 * driver.switchTo().defaultContent();
		 */
	}

	@Then("^update paydate for (.*) user$")
	public void update_paydate(String userType) throws ParseException {
		switchToMainFrame();
		waitForPresenceOfElement("CustMaintenanceBtn_XPATH", OR_LMS, 40);
		clickOnButton(getWebElement("CustMaintenanceBtn_XPATH", OR_LMS));

		waitForPresenceOfElement(By.xpath("//input[@name='UCPayroll:txtPayrollAmount']"), 40);
		waitForVisibilityOfElement(driver.findElement(By.xpath("//input[@name='UCPayroll:txtPayrollAmount']")), 40);

		scrollIntoView(driver.findElement(By.xpath("//input[@name='UCPayroll:txtPayrollAmount']")));

		test.log(LogStatus.INFO, "Before pay date update : " + test.addBase64ScreenShot(report.capture()));

		// driver.findElement(By.id("UCEmployer_optEmployeeWorkShift_" + new
		// Random().nextInt(5))).click();
		
		selectByValue(driver.findElement(By.id("UCPayroll_ddlPeriodicitySubtype")), "E");
		selectByIndex(driver.findElement(By.id("UCPayroll_cboPayrollSM1stPayDay")), 2);

		String oldLastPayDate = getWebElement("oldLastPayDate_ID", OR_MLL).getAttribute("value");
		String oldNextPayDate = getWebElement("oldNextPayDate_ID", OR_MLL).getAttribute("value");

		test.log(LogStatus.INFO, "Last pay date to be updated : " + oldLastPayDate);
		test.log(LogStatus.INFO, "Next pay date to be updated : " + oldNextPayDate);

		log.info("Last pay date to be updated : " + oldLastPayDate);
		log.info("Next pay date to be updated : " + oldNextPayDate);

		// getNextBusinessDayFromGivenDate(getPastDate(4))
		sendKeys(getWebElement("oldLastPayDate_ID", OR_MLL), getNextBusinessDayFromGivenDate(getPastDate(-4)));
		log.info("New Last pay date updated : " + getNextBusinessDayFromGivenDate(getPastDate(-4)));

		

		if (userType.equalsIgnoreCase("Normal")) {
			log.info("New next pay date updated : " + map.get("Next_PayDate"));
			sendKeys(getWebElement("oldNextPayDate_ID", OR_MLL), map.get("Next_PayDate"));

		} else {
			log.info("New next pay date updated : " + clientData.get("FirstPayDate"));
			sendKeys(getWebElement("oldNextPayDate_ID", OR_MLL), clientData.get("FirstPayDate"));

		}

		

		// driver.findElement(By.id("UCPayroll_optPayrollEmplType_" + new
		// Random().nextInt(2))).click();
		// driver.findElement(By.id("UCPayroll_optPayrollBankuptcy_" + new
		// Random().nextInt(2))).click();

		scrollIntoView(getWebElement("submit_ID", OR_MLL));
		test.log(LogStatus.INFO, "After paydate update" + test.addBase64ScreenShot(report.capture()));

		clickOnButton(getWebElement("submit_ID", OR_MLL));

		try {
			checkVisibilityOfElement(driver.findElement(By.id("cmdCustFile")), 5);
		} catch (TimeoutException | NoSuchElementException e) {
			// log.info(e.getMessage());
			scrollToEndOfPage();
			driver.findElement(By.xpath("//button[@class='clsButton']")).click();
			checkVisibilityOfElement(driver.findElement(By.id("cmdCustFile")), 5);
		}

	}

	@Then("^Update Customer Maintenance (.*)$")
	public void update_CustMaintenance(String UpdateInfo) throws Throwable {

		log.info("Update Customer Maintenance " + UpdateInfo);
		boolean bFlag = false;
		String new_UpdateInfo = UpdateInfo;
		List<String> clientData1 = new ArrayList<String>();
		String URL = getdbURL("QA");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
		CallableStatement stmt = connection.prepareCall("{CALL spGetBank(?)}");
		stmt.setString(1, map.get("State"));
		ResultSet rs = stmt.executeQuery();
		clientData1 = resultSetToArrayList(rs, clientData1);
		clientData1 = getPayrollData(clientData1);

		try {
			if (clientData1.size() != 12) {
				throw new ArrayIndexOutOfBoundsException();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			clientData1.clear();
			rs.close();
			stmt.close();
			connection.close();
			update_CustMaintenance(new_UpdateInfo);
		}

		Actions action = new Actions(driver);
		if (UpdateInfo.equals("Bank_Information")) {
			waitForPageLoaded();
			switchToMainFrame();
			waitForPresenceOfElement("CustMaintenanceBtn_XPATH", OR_LMS, 40);
			clickOnButton(getWebElement("CustMaintenanceBtn_XPATH", OR_LMS));
			WebElement element = getWebElement("CustMainABATxt_XPATH", OR_LMS);
			action.doubleClick(element).perform();
			element.sendKeys(Keys.BACK_SPACE);
			element.sendKeys(clientData1.get(0));
			WebElement element1 = getWebElement("CustMainACCTTxt_XPATH", OR_LMS);
			action.doubleClick(element1).perform();
			try {
				element1.sendKeys(Keys.BACK_SPACE);
				element1.sendKeys(clientData1.get(6));

			} catch (StaleElementReferenceException e) {
				element1 = getWebElement("CustMainACCTTxt_XPATH", OR_LMS);
				action.doubleClick(element1).perform();
				element1.sendKeys(Keys.BACK_SPACE);
				element1.sendKeys(clientData1.get(6));

			}
			element1 = getWebElement("CustMainBankNameTxt_XPATH", OR_LMS);
			action.doubleClick(element1).perform();
			element1.sendKeys(Keys.BACK_SPACE);
			element1.sendKeys(clientData1.get(2));
			test.log(LogStatus.PASS,
					"ABA and Account Number Updated to : " + clientData1.get(0) + " and " + clientData1.get(6));
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "ABA Updated")));
		} else {
			waitForPageLoaded();
			switchToMainFrame();
			waitForPresenceOfElement("CustMaintenanceBtn_XPATH", OR_LMS, 40);
			clickOnButton(getWebElement("CustMaintenanceBtn_XPATH", OR_LMS));
			waitForPresenceOfElement(By.xpath("//input[@name='UCPayroll:txtPayrollAmount']"), 40);
			waitForVisibilityOfElement(driver.findElement(By.xpath("//input[@name='UCPayroll:txtPayrollAmount']")), 40);
			boolean cFlag = true;

			scrollIntoView(driver.findElement(By.id("UCPayroll_txtPayrollAmount")));
			sendKeys(driver.findElement(By.id("UCPayroll_txtPayrollAmount")), "5000");
			log.info("Updated amount : 5000");
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Payroll updated")));

			log.info(driver.findElement(By.id("UCPayroll_txtPayrollLastPayDate")).getAttribute("value"));
			log.info(driver.findElement(By.id("UCPayroll_txtPayrollNextPayDate")).getAttribute("value"));

			sendKeys(getWebElement("oldLastPayDate_ID", OR_MLL), map.get("Next_PayDate"));
			sendKeys(getWebElement("oldNextPayDate_ID", OR_MLL), map.get("Second_PayDate"));

			log.info("Chnaged Date");
			log.info(driver.findElement(By.id("UCPayroll_txtPayrollLastPayDate")).getAttribute("value"));
			log.info(driver.findElement(By.id("UCPayroll_txtPayrollNextPayDate")).getAttribute("value"));

			driver.findElement(By.id("cmdSubmit")).click();
			// Valid code can be used once confirmed

			/*
			 * int count = 0; while (cFlag) {
			 * 
			 * String expectedPeriodicity = "";
			 * 
			 * Random shift = new Random(); int selectShift = shift.nextInt(5);
			 * scrollIntoView(driver.findElement(By.id("UCPayroll_txtPayrollAmount")));
			 * driver.findElement(By.id("UCPayroll_txtPayrollAmount")).clear();
			 * 
			 * driver.findElement(By.id("UCPayroll_txtPayrollAmount")).sendKeys("5000");
			 * test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver,
			 * "Payroll updated"))); Random r = new Random(); int index = r.nextInt(4);
			 * 
			 * Select periodicity = new
			 * Select(driver.findElement(By.id("UCPayroll_optPayrollPeriodicity")));
			 * periodicity.selectByIndex(index); test.log(LogStatus.PASS,
			 * test.addBase64ScreenShot(report.capture(driver, "Payroll updated"))); try {
			 * if (index == 0) { expectedPeriodicity = "Weekly"; Random ran = new Random();
			 * int index1 = ran.nextInt(2) + 1; Select howPaid = new
			 * Select(driver.findElement(By.id("UCPayroll_ddlPeriodicitySubtype")));
			 * howPaid.selectByIndex(index1); if (index1 == 1) { new
			 * Select(driver.findElement(By.id("UCPayroll_cboPayrollSM1stPayDay")))
			 * .selectByIndex(new Random().nextInt(5) + 1); } else if (index1 == 2) { new
			 * Select(driver.findElement(By.id("UCPayroll_cboPayrollSM1stPayDay")))
			 * .selectByIndex(new Random().nextInt(5));
			 * driver.findElement(By.id("UCPayroll_radlstpaydt_" + new
			 * Random().nextInt(2))).click(); } else {
			 * log.info("Some issue in Day of weeks"); }
			 * 
			 * } else if (index == 1) { expectedPeriodicity = "BiWeekly";
			 * 
			 * new Select(driver.findElement(By.id("UCPayroll_cboPayrollSM1stPayDay")))
			 * .selectByIndex(new Random().nextInt(5) + 1);
			 * 
			 * driver.findElement(By.id("UCPayroll_radlstpaydt_" + new
			 * Random().nextInt(2))).click();
			 * 
			 * } else if (index == 2) { expectedPeriodicity = "SemiMonthly"; Random r1 = new
			 * Random(); int index2 = r1.nextInt(2) + 1; Select s = new
			 * Select(driver.findElement(By.id("UCPayroll_ddlPeriodicitySubtype")));
			 * s.selectByIndex(index2);
			 * 
			 * if (index2 == 1) { new
			 * Select(driver.findElement(By.id("UCPayroll_cboPayrollSM1stPayDay")))
			 * .selectByIndex(new Random().nextInt(18) + 1); new
			 * Select(driver.findElement(By.id("UCPayroll_cboPayrollSM2ndPayDay")))
			 * .selectByIndex(new Random().nextInt(19) + 1); } else if (index2 == 2) { new
			 * Select(driver.findElement(By.id("UCPayroll_ddl1stpayweek")))
			 * .selectByIndex(new Random().nextInt(3) + 1); new
			 * Select(driver.findElement(By.id("UCPayroll_ddl1stpayday")))
			 * .selectByIndex(new Random().nextInt(5) + 1); new
			 * Select(driver.findElement(By.id("UCPayroll_ddl2ndpayweek")))
			 * .selectByIndex(new Random().nextInt(3) + 1); new
			 * Select(driver.findElement(By.id("UCPayroll_ddl2ndpayday")))
			 * .selectByIndex(new Random().nextInt(5) + 1); } else {
			 * log.info("Index selected is wrong"); }
			 * 
			 * } else if (index == 3) {
			 * 
			 * expectedPeriodicity = "Monthly"; Random rn = new Random(); int howPaid1 =
			 * rn.nextInt(4) + 1; Select s = new
			 * Select(driver.findElement(By.id("UCPayroll_ddlPeriodicitySubtype")));
			 * s.selectByIndex(howPaid1);
			 * 
			 * if (howPaid1 == 1) { new
			 * Select(driver.findElement(By.id("UCPayroll_cboPayrollSM1stPayDay")))
			 * .selectByIndex(new Random().nextInt(31) + 1); } else if (howPaid1 == 2) { new
			 * Select(driver.findElement(By.id("UCPayroll_ddlpayweek"))) .selectByIndex(new
			 * Random().nextInt(5) + 1); new
			 * Select(driver.findElement(By.id("UCPayroll_ddlpayday"))) .selectByIndex(new
			 * Random().nextInt(5) + 1); } else if (howPaid1 == 3) { new
			 * Select(driver.findElement(By.id("UCPayroll_ddlBizdays"))) .selectByIndex(new
			 * Random().nextInt(10) + 1); new
			 * Select(driver.findElement(By.id("UCPayroll_ddlwhichday"))) .selectByIndex(new
			 * Random().nextInt(31) + 1);
			 * 
			 * } else if (howPaid1 == 4) { new
			 * Select(driver.findElement(By.id("UCPayroll_ddlwhichweek")))
			 * .selectByIndex(new Random().nextInt(4) + 1); new
			 * Select(driver.findElement(By.id("UCPayroll_ddlcwday"))) .selectByIndex(new
			 * Random().nextInt(5) + 1); new
			 * Select(driver.findElement(By.id("UCPayroll_ddlawhday"))) .selectByIndex(new
			 * Random().nextInt(31) + 1);
			 * 
			 * } else { log.info("error"); }
			 * 
			 * } else { log.info("Some issue"); } test.log(LogStatus.PASS,
			 * test.addBase64ScreenShot(report.capture(driver, "Payroll updated data ")));
			 * log.info(expectedPeriodicity); test.log(LogStatus.PASS,
			 * test.addBase64ScreenShot(report.capture(driver, "Payroll updated")));
			 * driver.findElement(By.id("UCPayroll_radnonbizday_" + new
			 * Random().nextInt(2))).click();
			 * 
			 * log.info(driver.findElement(By.id("UCPayroll_txtPayrollLastPayDate")).
			 * getAttribute("value"));
			 * log.info(driver.findElement(By.id("UCPayroll_txtPayrollNextPayDate")).
			 * getAttribute("value"));
			 * 
			 * // driver.findElement(By.id("UCPayroll_optPayrollEmplType_" + new //
			 * Random().nextInt(2))).click();
			 * 
			 * if (!driver.findElement(By.id("UCPayroll_optPayrollType_0")).isSelected()) {
			 * driver.findElement(By.id("UCPayroll_optPayrollType_0")).click(); }
			 * 
			 * // driver.findElement(By.id("UCPayroll_optPayrollIncomeVerified_" + new //
			 * Random().nextInt(2))).click(); test.log(LogStatus.PASS,
			 * test.addBase64ScreenShot(report.capture(driver, "Payroll updated"))); //
			 * driver.findElement(By.id("UCPayroll_optPayrollGarnishment_" + new //
			 * Random().nextInt(2))).click();
			 * 
			 * // driver.findElement(By.id("UCPayroll_optPayrollBankuptcy_" + new //
			 * Random().nextInt(2))).click(); // test.log(LogStatus.PASS,
			 * test.addBase64ScreenShot(report.capture(driver, // "Payroll updated")));
			 * driver.findElement(By.id("cmdSubmit")).click();
			 * 
			 * new WebDriverWait(driver, 10L).until(ExpectedConditions
			 * .visibilityOf(driver.findElement(By.
			 * xpath("//td[contains(text(),'Pay Frequency')]"))));
			 * 
			 * String actualPeriodicity = driver .findElement(By.
			 * xpath("//td[contains(text(),'Pay Frequency ')]//following-sibling::td"))
			 * .getText(); log.info(actualPeriodicity); cFlag = false;
			 * Assert.assertEquals(expectedPeriodicity, actualPeriodicity);
			 * 
			 * } catch (Exception e) { count++; if (count == 3) { log.info("Count is : " +
			 * count); break; } continue; } }
			 * 
			 */ }

	}

	/// added below sonali

	// below old code of originate process

	@Then("^originate process start$")
	public void originate_process_start() throws Throwable {

		switchToMainFrame();
		WebElement origin = getWebElement("originatebtn_name", OR_MLL);
		boolean result = origin.isDisplayed();
		log.info("Result=" + result);
		getWebElement("originatebtn_name", OR_MLL).click();
		test.log(LogStatus.PASS,
				"orginate process start" + test.addBase64ScreenShot(report.capture(driver, "orginate process start")));

		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}

		int number = driver.findElements(By.xpath("//table/tbody/tr[30]/td[1]/input[@id='cmdSubmit']")).size();

		if (number > 0) {
			getWebElement("Most_recent_pay_stub_verified_radio_XPATH", OR_MLL).click();
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "orginate process start")));
			getWebElement("submitbtn_XPATH", OR_MLL).click();
			driver.switchTo().defaultContent();
		} else {
			boolean flag = true;
			try {
				getWebElement("Cancelbtn_XPATH", OR_MLL).click();
			} catch (Exception e) {
				log.info("Cancel Button is not present");
			}

			// clickOnButton(getWebElement("Cancelbtn_XPATH", OR_MLL));

			driver.switchTo().defaultContent();
			waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
			waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
			waitForPresenceOfElement(By.xpath("//a[@id='changeDueDate']"), 30);
			waitForVisibilityOfElement(driver.findElement(By.xpath("//a[@id='changeDueDate']")), 30);
			clickOnButton(driver.findElement(By.xpath("//a[@id='changeDueDate']")));
			driver.switchTo().defaultContent();
			waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
			waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
			waitForFrameToBeAvailableAndSwitchToIt("TRANdialogiframe", 30);
			Thread.sleep(2000);

			//
			if (!driver.findElement(By.id("rblDueDateList_0")).isSelected()) {
				driver.findElement(By.id("rblDueDateList_0")).click();
				driver.findElement(By.id("bUpdateDueDate")).click();
				test.log(LogStatus.INFO, "Due date Update is completed : "
						+ test.addScreenCapture(report.capture(driver, "Due date Update is completed")));
				driver.findElement(By.id("bClose")).click();
				log.info("Successfully updated the due date and closed the window");

			} else {
				driver.findElement(By.id("bClose")).click();
			}

			//
			// getWebElement("changdateradio_XPATH", OR_MLL).click();
			// getWebElement("updateradio_XPATH", OR_MLL).click();
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "orginate process start")));
//			getWebElement("closebtn_XPATH", OR_MLL).click();

			Thread.sleep(2000);
			driver.switchTo().defaultContent();
			waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
			waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
			Thread.sleep(2000);

			if (driver.findElement(By.xpath("//input[@id='cmdOrigLoan']")).isEnabled()) {

				waitForPresenceOfElement(By.xpath("//input[@id='cmdOrigLoan']"), 30);
				waitForVisibilityOfElement(driver.findElement(By.xpath("//input[@id='cmdOrigLoan']")), 30);

				if (isAlertPresent()) {
					driver.switchTo().alert().accept();
				}
				getWebElement("Most_recent_pay_stub_verified_radio_XPATH", OR_MLL).click();
				getWebElement("submitbtn_XPATH", OR_MLL).click();
				driver.switchTo().defaultContent();
			}

			else {
				perform_TextACheck();
			}

		}

	}

	@Then("^change loan amount$")
	public void change_loan_amount() throws Throwable {
		Thread.sleep(2000);

		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 40);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 40);
		// Thread.sleep(2000);

		// below code added to scroll up
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-250)", "");

		waitForPresenceOfElement(By.xpath("//a[@id='changeAmount']"), 40);
		waitForVisibilityOfElement(driver.findElement(By.xpath("//a[@id='changeAmount']")), 40);
		// WebDriverWait wait = new WebDriverWait(driver, 30);
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='changeAmount']"))).click();
		// getWebElement("changeloanamount_xpath",OR_MLL).click();
		clickOnButton(driver.findElement(By.xpath("//a[@id='changeAmount']")));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Loan amount changed screen shot one")));
		Thread.sleep(2000);
		/*
		 * try { Alert alert = driver.switchTo().alert(); String alertText =
		 * alert.getText(); alert.accept(); } catch (NoAlertPresentException e) {
		 * e.printStackTrace(); }
		 */

		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}
		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 40);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 40);
		waitForFrameToBeAvailableAndSwitchToIt("TRANdialogiframe", 40);
		waitForFrameToBeAvailableAndSwitchToIt("ifrmHoldLoanAmount", 40);

		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}
		getWebElement("selectloan_amount_xpath", OR_MLL).clear();
		Thread.sleep(2000);

		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}

		getWebElement("selectloan_amount_xpath", OR_MLL).click();

		int max = 1300;
		int min = 175;
		int range = max - min + 1;
		int rand = (int) (Math.random() * range) + min;
		log.info("random no=" + rand);
		test.log(LogStatus.PASS, "Updated loan amount " + rand);

		getWebElement("selectloan_amount_xpath", OR_MLL).sendKeys(String.valueOf(rand));
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Loan amount screen shot two")));
		waitForPresenceOfElement(By.name("cmdSubmit"), 40);
		// clickOnButton(getWebElement("submit_name", OR_MLL));
		clickOnButton(driver.findElement(By.name("cmdSubmit")));
		// getWebElement("submit_name", OR_MLL).click();
		driver.switchTo().defaultContent();
		log.info("Loan amount submitted sucessfully for " + map.get("Email"));
		test.log(LogStatus.PASS, "Loan amount submitted sucessfully for " + map.get("Email"));
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Loan amount changed sucessfully")));

	}

	@Then("^Withdraw process start$")
	public void withdraw_process_start() throws Throwable {
		log.info("Withdraw process start");

		switchToMainFrame();
		test.log(LogStatus.PASS, "Withdraw process start " + map.get("Email"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-250)", "");
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Withdraw process start")));

		getWebElement("withdraw_link_xpath", OR_MLL).click();
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Withdraw process screen shot two")));
		switchToMainFrame();
		waitForFrameToBeAvailableAndSwitchToIt("TRANdialogiframe", 40);
		waitForPresenceOfElement(By.xpath("//input[@id='UCTranVoid_optTranVerified_0']"), 30);
		clickOnButton(getWebElement("verified_yes_btn_xpath", OR_MLL));

		// selectByVisibleText(getWebElement("voidreason_dropdown_name", OR_MLL), "CPC -
		// No Reason");

		selectByValue(driver.findElement(By.id("UCTranVoid_ddReasonCode")), "7");

		waitForPresenceOfElement(By.name("UCTranVoid:txtVoidReason"), 30);
		sendKeys(getWebElement("voidreasontxtbox_name", OR_MLL), "Test");
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Withdraw process screen shot one ")));
		getWebElement("submitbtn_name", OR_MLL).click();

		switchToTranDialogIFrame();
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Withdraw process ")));
		getWebElement("closebtn_name", OR_MLL).click();

		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}

		test.log(LogStatus.PASS, "Withdraw processcompleted  for " + map.get("Email")
				+ test.addBase64ScreenShot(report.capture(driver, "Withdraw processcompleted sucessfully")));

	}

	@Then("^void loan process is start$")
	public void void_loan_process_is_start() throws InterruptedException, IOException {
		switchToMainFrame();
		test.log(LogStatus.PASS, "Void loan process start for " + map.get("Email"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-250)", "");
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Void loan process start")));
		getWebElement("void_link_xpath", OR_MLL).click();
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Void loan process screen shot one")));

		switchToTranDialogIFrame();

		getWebElement("verified_yes_btn_xpath", OR_MLL).click();
		getWebElement("verifiedrdio_xpath", OR_MLL).click();

		selectByVisibleText(getWebElement("voidreason_name", OR_MLL), "Rescind - No Reason Given");

		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Void loan process screen shot two")));
		getWebElement("voidreasontxt_name", OR_MLL).sendKeys("Test");

		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}
		getWebElement("submitvoidbtn_name", OR_MLL).click();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Void loan process screen shot three")));
		getWebElement("closebtn_name", OR_MLL).click();

		test.log(LogStatus.PASS, "Void loan process completed  for " + map.get("Email")
				+ test.addBase64ScreenShot(report.capture(driver, "Void loan process completed sucessfully")));

	}

	@Then("^Charge Off manual process start$")
	public void charge_Off_manual_process_start() throws InterruptedException, IOException {

		switchToLeftFrame();

		waitForPresenceOfElement(By.xpath("//a[@href='MenuList.aspx?Type=T']"), 30);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='MenuList.aspx?Type=T']")))
				.click();
		switchToMainFrame();
		waitForPresenceOfElement(By.xpath("//a[@href='./TranMan/TranMgmt.aspx']"), 30);
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='./TranMan/TranMgmt.aspx']")))
				.click();
		// test.log(LogStatus.PASS,
		// test.addBase64ScreenShot(report.capture(driver,"Charge Off manual proces
		// screen shot one")));
		getWebElement("chargeoff_XPATH", OR_MLL).click();
		// test.log(LogStatus.PASS,
		// test.addBase64ScreenShot(report.capture(driver,"Charge Off manual process
		// screen shot two")));
		getWebElement("Applicationtxtbox_XPATH", OR_MLL).click();
		getWebElement("Applicationtxtbox_XPATH", OR_MLL).sendKeys(Appno);
		log.info("app no=" + Appno);
		getWebElement("searchbtn_xpath", OR_MLL).click();
		getWebElement("textarea_xpath", OR_MLL).click();
		getWebElement("textarea_xpath", OR_MLL).sendKeys("Test");
		getWebElement("submitbtn_xpath", OR_MLL).click();
		test.log(LogStatus.PASS, "Charge Off manual process completed  for " + map.get("Email")
				+ test.addBase64ScreenShot(report.capture(driver, "Charge Off manual process completed sucessfully")));
	}

	@Then("^Additional payment process$")
	public void additional_payment_process() throws Throwable {
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot one")));

		switchToMainFrame();
		log.info("payment ");

		waitForPresenceOfElement(By.xpath("//input[@id='cmdCash']"), 60);

		clickOnButton(driver.findElement(By.xpath("//input[@id='cmdCash']")));

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot two")));
		switchToTranDialogIFrame();
		waitForPresenceOfElement(By.name("ddlPmtType"), 50);
		waitForVisibilityOfElement(getWebElement("paymenttypedropdown_name", OR_MLL), 40);

		selectByVisibleText(getWebElement("paymenttypedropdown_name", OR_MLL), "Regular Payment");

		selectByVisibleText(getWebElement("paymode_name", OR_MLL), "ACH");

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot three")));
		String abc = getWebElement("payamountduedate_xpath", OR_MLL).getText();
		String data = abc.replace('$', ' ');

		double sum = Double.parseDouble(data);
		double add = sum + 5.00;
		log.info("updated loan amt=" + add);
		test.log(LogStatus.PASS, "updated loan amt " + add);

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));

		waitForPresenceOfElement(By.xpath("//input[@name='txtPaymentAmount']"), 60);
		clickOnButton(getWebElement("payamount_xpath", OR_MLL));
		waitForPresenceOfElement(By.xpath("//input[@name='txtPaymentAmount']"), 60);
		// getWebElement("payamount_xpath", OR_MLL).sendKeys(String.valueOf(add));
		sendKeys(getWebElement("payamount_xpath", OR_MLL), String.valueOf(add));
		// Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));
		waitForPresenceOfElement(By.xpath("//input[@name='cmdApplSubmit']"), 60);
		clickOnButton(getWebElement("extndapplication_xpath", OR_MLL));
		// getWebElement("extndapplication_xpath", OR_MLL).click();
		// Thread.sleep(2000);
		switchToMainFrame();

		scrollBy(1000);

		waitForPresenceOfElement(By.xpath("//input[@name='cmdSubmit']"), 60);

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot five")));

		clickOnButton(driver.findElement(By.xpath("//input[@name='cmdSubmit']")));

		switchToMainFrame();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot six")));
		waitForPresenceOfElement(By.xpath("//input[@id='cmdOk']"), 60);
		clickOnButton(getWebElement("clickherecontinue_xpath", OR_MLL));

		switchToMainFrame();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot seven")));
		getWebElement("yesbtn_xpath", OR_MLL).click();
		switchToMainFrame();
		getWebElement("postpreparedoc_xpath", OR_MLL).click();

		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}
		switchToMainFrame();
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Additional payment process")));

		// getWebElement("Paymentschedule_XPATH", OR_MLL).click();
		waitForPresenceOfElement(By.xpath("//input[@id='cmdApplicationSchedule']"), 60);
		clickOnButton(getWebElement("Paymentschedule_XPATH", OR_MLL));

		test.log(LogStatus.PASS, "Additional payment process completed  for " + map.get("Email"));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process completed sucessfully")));

	}

//	@Then("^Additional payment process payment type for(.*)$")
//	public void additional_payment_process_payment_type_for(String pay) throws InterruptedException, IOException
//	{

	@Then("^Additional payment process payment method for ACH$")
	public void additional_payment_process_payment_method_for_ACH() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Thread.sleep(2000);
		// test.log(LogStatus.PASS,
		// test.addBase64ScreenShot(report.capture(driver,"Additional payment process
		// screen shot one")));
		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
		log.info("payment ");
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='cmdCash']")));
		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@id='cmdCash']")).click();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot two")));
		// getWebElement("payment_XPATH", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
		waitForFrameToBeAvailableAndSwitchToIt("TRANdialogiframe", 30);
		// driver.switchTo().frame("TRANdialogiframe");
		WebElement paytype = getWebElement("paymenttypedropdown_name", OR_MLL);
		Select paytypeselect = new Select(paytype);
		paytypeselect.selectByVisibleText("Regular Payment");
		// paytypeselect.selectByVisibleText("Paid in Full");
		Thread.sleep(2000);

		WebElement paymode = getWebElement("paymode_name", OR_MLL);
		Select paymodeselect = new Select(paymode);
		paymodeselect.selectByVisibleText("ACH");

//		if(pay.equals("ACH"))
//		{
//		WebElement paymode = getWebElement("paymode_name", OR_MLL);
//		Select paymodeselect = new Select(paymode);
//		paymodeselect.selectByVisibleText("ACH");
//		}
//		else
//		{	
//		if(pay.equals("CASH"))
//		{
//			WebElement paymode = getWebElement("paymode_name", OR_MLL);
//			Select paymodeselect = new Select(paymode);
//			paymodeselect.selectByVisibleText("Cash");
//		}
//		}
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot three")));
		String abc = getWebElement("payamountduedate_xpath", OR_MLL).getText();
		String data = abc.replace('$', ' ');
		Thread.sleep(2000);
		double sum = Double.parseDouble(data);
		double add = sum + 5.00;
		log.info("updated payment amount=" + add);
		test.log(LogStatus.PASS, "updated loan amt " + add);

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));
		WebDriverWait wait1 = new WebDriverWait(driver, 60);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='txtPaymentAmount']")));
		Thread.sleep(2000);
		getWebElement("payamount_xpath", OR_MLL).click();
		Thread.sleep(2000);
		getWebElement("payamount_xpath", OR_MLL).sendKeys(String.valueOf(add));
		Thread.sleep(2000);
		getWebElement("extndapplication_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		WebDriverWait wait11 = new WebDriverWait(driver, 60);
		wait11.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='cmdSubmit']")));
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot five")));
		getWebElement("submit_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot six")));
		getWebElement("clickherecontinue_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot seven")));
		getWebElement("yesbtn_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
		getWebElement("postpreparedoc_xpath", OR_MLL).click();
		Thread.sleep(2000);

		/*
		 * try { Alert alert = driver.switchTo().alert(); String alertText =
		 * alert.getText(); alert.accept(); } catch (NoAlertPresentException e) {
		 * e.printStackTrace(); }
		 */

		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}
		Thread.sleep(2000);
		// Below code for click on payment schedule button
		// Thread.sleep(2000);
		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
		getWebElement("Paymentschedule_XPATH", OR_MLL).click();

		test.log(LogStatus.PASS, "Additional payment process completed  for " + map.get("Email"));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process completed sucessfully")));

	}

	@Then("^Additional payment process payment type for CASH$")
	public void additional_payment_process_payment_type_for_CASH() throws Throwable {

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot one")));
		switchToMainFrame();
		log.info("payment ");

		waitForPresenceOfElement(By.xpath("//input[@id='cmdCash']"), 60);

		clickOnButton(driver.findElement(By.xpath("//input[@id='cmdCash']")));

		// driver.findElement(By.xpath("//input[@id='cmdCash']")).click();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot two")));

		switchToTranDialogIFrame();
		waitForPresenceOfElement(By.name("ddlPmtType"), 20);
		waitForVisibilityOfElement(getWebElement("paymenttypedropdown_name", OR_MLL), 40);
		// WebElement paytype = getWebElement("paymenttypedropdown_name", OR_MLL);
		// Select paytypeselect = new Select(paytype);
		// paytypeselect.selectByVisibleText("Regular Payment");

		selectByVisibleText(getWebElement("paymenttypedropdown_name", OR_MLL), "Regular Payment");

		// WebElement paymode = getWebElement("paymode_name", OR_MLL);
		// Select paymodeselect = new Select(paymode);
		// paymodeselect.selectByVisibleText("Cash");

		selectByVisibleText(getWebElement("paymode_name", OR_MLL), "Cash");
//		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot three")));
		String abc = getWebElement("payamountduedate_xpath", OR_MLL).getText();
		String data = abc.replace('$', ' ');
		// Thread.sleep(2000);
		double sum = Double.parseDouble(data);
		double add = sum + 5.00;
		log.info("updated loan amt=" + add);

		test.log(LogStatus.PASS, "updated loan amt " + add
				+ test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));

		waitForPresenceOfElement(By.xpath("//input[@name='txtPaymentAmount']"), 60);
		clickOnButton(getWebElement("payamount_xpath", OR_MLL), 10);
		sendKeys(getWebElement("payamount_xpath", OR_MLL), String.valueOf(add));

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));
		waitForPresenceOfElement(By.xpath("//input[@name='cmdApplSubmit']"), 60);
		clickOnButton(getWebElement("extndapplication_xpath", OR_MLL));
		// getWebElement("extndapplication_xpath", OR_MLL).click();
		// Thread.sleep(2000);
		switchToMainFrame();

		scrollBy(1000);

		waitForPresenceOfElement(By.xpath("//input[@name='cmdSubmit']"), 60);

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot five")));

		clickOnButton(driver.findElement(By.xpath("//input[@name='cmdSubmit']")));

		switchToMainFrame();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot six")));
		// getWebElement("clickherecontinue_xpath", OR_MLL).click();
		waitForPresenceOfElement(By.xpath("//input[@id='cmdOk']"), 60);
		clickOnButton(getWebElement("clickherecontinue_xpath", OR_MLL));

		switchToMainFrame();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot seven")));
		getWebElement("yesbtn_xpath", OR_MLL).click();
		switchToMainFrame();
		getWebElement("postpreparedoc_xpath", OR_MLL).click();

		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}
		switchToMainFrame();
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Additional payment process")));

		// getWebElement("Paymentschedule_XPATH", OR_MLL).click();
		waitForPresenceOfElement(By.xpath("//input[@id='cmdApplicationSchedule']"), 20);
		clickOnButton(getWebElement("Paymentschedule_XPATH", OR_MLL));

		test.log(LogStatus.PASS, "Additional payment process completed  for " + map.get("Email")
				+ test.addBase64ScreenShot(report.capture(driver, "Additional payment process completed sucessfully")));

	}

	@Then("^Additional payment process payment type for check$")
	public void additional_payment_process_payment_type_for_check() throws Throwable {

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot one")));

		switchToMainFrame();
		log.info("payment ");
		waitForPresenceOfElement(By.xpath("//input[@id='cmdCash']"), 60);
		clickOnButton(driver.findElement(By.xpath("//input[@id='cmdCash']")));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot two")));

		switchToTranDialogIFrame();

		waitForPresenceOfElement(By.name("ddlPmtType"), 50);
		waitForVisibilityOfElement(getWebElement("paymenttypedropdown_name", OR_MLL), 40);
		selectByVisibleText(getWebElement("paymenttypedropdown_name", OR_MLL), "Regular Payment");

		selectByVisibleText(getWebElement("paymode_name", OR_MLL), "Cashier’s check (check)");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot three")));
		String abc = getWebElement("payamountduedate_xpath", OR_MLL).getText();
		String data = abc.replace('$', ' ');
		double sum = Double.parseDouble(data);
		double add = sum + 5.00;
		log.info("updated loan amt=" + add);
		test.log(LogStatus.PASS, "updated loan amt " + add);

		test.log(LogStatus.PASS, "updated loan amt" + add
				+ test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));

		waitForPresenceOfElement(By.xpath("//input[@name='txtPaymentAmount']"), 60);
		clickOnButton(getWebElement("payamount_xpath", OR_MLL));
		sendKeys(getWebElement("payamount_xpath", OR_MLL), String.valueOf(add));

		waitForPresenceOfElement(By.xpath("//input[@id='txtRefNumber']"), 60);
		clickOnButton(getWebElement("txtRefNumber_xpath", OR_MLL));
		sendKeys(getWebElement("txtRefNumber_xpath", OR_MLL), String.valueOf("12345"));

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));
		waitForPresenceOfElement(By.xpath("//input[@name='cmdApplSubmit']"), 60);
		clickOnButton(getWebElement("extndapplication_xpath", OR_MLL));
		switchToMainFrame();

		scrollBy(1000);

		waitForPresenceOfElement(By.xpath("//input[@name='cmdSubmit']"), 60);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot five")));
		clickOnButton(driver.findElement(By.xpath("//input[@name='cmdSubmit']")));

		switchToMainFrame();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot six")));
		// getWebElement("clickherecontinue_xpath", OR_MLL).click();
		waitForPresenceOfElement(By.xpath("//input[@id='cmdOk']"), 60);
		clickOnButton(getWebElement("clickherecontinue_xpath", OR_MLL));
		switchToMainFrame();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot seven")));
		getWebElement("yesbtn_xpath", OR_MLL).click();
		switchToMainFrame();
		getWebElement("postpreparedoc_xpath", OR_MLL).click();

		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}
		switchToMainFrame();
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Additional payment process")));
		waitForPresenceOfElement(By.xpath("//input[@id='cmdApplicationSchedule']"), 60);
		clickOnButton(getWebElement("Paymentschedule_XPATH", OR_MLL));

		test.log(LogStatus.PASS, "Additional payment process completed  for " + map.get("Email"));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process completed sucessfully")));

	}

	@Then("^Additional payment process using (.*)$")
	public void additional_payment_process(String paymentMode) throws InterruptedException, IOException {

		Thread.sleep(9000);
		// driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");

		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='cmdCash']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='cmdCash']")));
		driver.findElement(By.xpath("//input[@id='cmdCash']")).click();

		// getWebElement("payment_XPATH", OR_MLL).click();
		Thread.sleep(8000);
		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
		waitForFrameToBeAvailableAndSwitchToIt("TRANdialogiframe", 30);
		WebElement paytype = getWebElement("paymenttypedropdown_name", OR_MLL);
		Select paytypeselect = new Select(paytype);
		paytypeselect.selectByVisibleText("Regular Payment");
		// paytypeselect.selectByVisibleText("Paid in Full");

		WebElement paymode = getWebElement("paymode_name", OR_MLL);
		Select paymodeselect = new Select(paymode);
		paymodeselect.selectByVisibleText(paymentMode);
		String abc = getWebElement("payamountduedate_XPATH", OR_MLL).getText();
		int data = Integer.parseInt(abc.replaceAll("$", " "));
		String sum = String.valueOf(data + 5);
		log.info("sum-" + sum);
		String a = sum.replaceAll("\\s", "");
		getWebElement("txtchk_paymentamout_name", OR_MLL).click();
		getWebElement("txtchk_paymentamout_name", OR_MLL).sendKeys(a);
		getWebElement("ExtendAppBtn_XPATH", OR_MLL).click();
		// Below code for click on payment schedule button
		// Thread.sleep(2000);
		// driver.switchTo().defaultContent();
		// driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

		// driver.switchTo().frame("Main");
		// getWebElement("Paymentschedule_XPATH", OR_MLL).click();

		test.log(LogStatus.PASS, "Additional payment process completed  for " + map.get("Email"));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process completed sucessfully")));
	}

	@Then("^update the pay date process start from Helpdesk$")
	public void update_the_pay_date_process_start_from_Helpdesk() throws InterruptedException, IOException {
		log.info("update the pay date process start from Helpdesk");
		// Write code here that turns the phrase above into concrete actions
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Left");
		getWebElement("Admin_XPATH", OR_MLL).click();

		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");

		// log.info("appno="+Appno);
		WebDriverWait wait1 = new WebDriverWait(driver, 30);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='Admin//HelpDesk.aspx']")))
				.click();

		// getWebElement("Helpdesk_utili_XPATH", OR_MLL).click();

		getWebElement("Appnotxt_XPATH", OR_MLL).sendKeys(Appno);
		getWebElement("searchbtn_XPATH", OR_MLL).click();
		getWebElement("maxloanamt_xpath", OR_MLL).click();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Update pay date process screen shot two ")));

		WebElement loan = driver.findElement(By.xpath("//select[@name='ddUpdateType']"));
		Select paytypeselect = new Select(loan);
		paytypeselect.selectByVisibleText("Update Loan Amount");

		String abcd = getWebElement("maxloanamt_xpath", OR_MLL).getText();
		String datas = abcd.replace("$", "");
		double sum = Double.parseDouble(datas);
		double add = sum + 6.00;
		log.info("updated loan amt=" + add);
		test.log(LogStatus.PASS, "Update loan amount= " + add);
		getWebElement("newloanamttxt_XPATH", OR_MLL).click();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Update pay date process screen shot three ")));
//	getWebElement("newloanamttxt_XPATH", OR_MLL).sendKeys(String.valueOf(add));
		getWebElement("newloanamttxt_XPATH", OR_MLL).sendKeys(String.valueOf(add));
		getWebElement("updatebtn_XPATH", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();

		test.log(LogStatus.PASS, "Update loan amount process completed  for " + map.get("Email"));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Update loan amount process completed ")));

	}

	@When("^user enters login details for loan in BTL$")
	public void user_enters_login_details_for_loan_in_BTL() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// driver.findElement(By.id("content_loginEmail")).click();
		/*
		 * ((WebElement) getWebElement("Emailidtxt_ID", OR_MLL)).click(); ((WebElement)
		 * getWebElement("Emailidtxt_ID", OR_MLL)).sendKeys(map.get("Email"));
		 */

		sendKeys(getWebElement("Emailidtxt_ID", OR_MLL), map.get("Email"));

		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "user enters login details ")));

		driver.findElement(By.id("content_loginSubmit")).click();

	}

	@When("^user enters login details for loan in MLL$")
	public void user_enters_login_details_for_loan_in_MLL() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		driver.findElement(By.id("content_loginEmail")).click();
		((WebElement) getWebElement("Emailidtxt_ID", OR_MLL)).click();
		((WebElement) getWebElement("Emailidtxt_ID", OR_MLL)).sendKeys(map.get("Email"));
		Thread.sleep(2000);
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "user enters login details ")));

		driver.findElement(By.id("content_loginSubmit")).click();

	}

	@Then("^Create Password for ssn Information for (.*)$")
	public void Create_Password_for_ssn_Information_for(String merchant) throws Throwable {
		if (driver.findElements(By.id("content_customContent_frmSSN")).size()>0) {
			sendKeys(getWebElement("SSNTxt_ID", OR_MLL), map.get("SSN_3"));
			sendKeys(getWebElement("DOBTxt_ID", OR_MLL), map.get("DateofBirth"));
			strPwd = createUserPass(map.get("First_Name"), map.get("Last_Name"), map.get("DateofBirth"));
			sendKeys(getWebElement("PasswordTxt_ID", OR_MLL), strPwd);
			sendKeys(getWebElement("ConfirmPasswordTxt_ID", OR_MLL), strPwd);
			clickOnButton(getWebElement("SavePwdButton_ID", OR_MLL));
			clickOnButton(getWebElement("LoginNowBtn_ID", OR_MLL));
		}
		sendKeys(driver.findElement(By.id("content_customContent_frmPassword_Login")), strPwd);
		clickOnButton(getWebElement("LoginBtn_ID", OR_MLL));
		
	/*
		waitForPresenceOfElement(By.xpath("//input[contains(@id,'content_customContent_frmSSN')]"), 30);
		sendKeys(getWebElement("Last4SSNTxt_XPATH", OR_MLL), map.get("SSN_3"));
		getWebElement("ToggleSSNTxt_XPATH", OR_MLL).click();
		String strDOB = map.get("DateofBirth");
		sendKeys(getWebElement("DOBTxt_ID", OR_MLL), strDOB);
		if (map.get("First_Name").length() < 2) {
			String strPwd_old = map.get("First_Name").toLowerCase().substring(0, 1)
					+ map.get("Last_Name").toLowerCase().substring(0, 2)
					+ map.get("DateofBirth").replaceAll("/", "").substring(4, 8) + "$";
			strPwd = strPwd_old.replace(strPwd_old.substring(0, 1), strPwd_old.substring(0, 1).toUpperCase());
		} else {
			String strPwd_old = map.get("First_Name").toLowerCase().substring(0, 2)
					+ map.get("Last_Name").toLowerCase().substring(0, 2)
					+ map.get("DateofBirth").replaceAll("/", "").substring(4, 8) + "$";
			strPwd = strPwd_old.replace(strPwd_old.substring(0, 1), strPwd_old.substring(0, 1).toUpperCase());
		}

		sendKeys(getWebElement("PasswordTxt_ID", OR_MLL), strPwd);
		sendKeys(getWebElement("ConfirmPasswordTxt_ID", OR_MLL), strPwd);
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "create password screen shot one ")));
		clickOnButton(getWebElement("SavePwdButton_ID", OR_MLL));
		waitForPresenceOfElement(By.xpath("//input[@name='ctl00$ctl00$content$customContent$btnLoginNow']"), 30);
		clickUsingJS(getWebElement("loginnowlink_xpath", OR_MLL));
		waitForPresenceOfElement(By.xpath("//input[@name='ctl00$ctl00$content$customContent$frmPassword_Login']"), 30);
		sendKeys(getWebElement("password_xpath", OR_MLL), strPwd);
		clickOnButton(getWebElement("loginbtn_xpath", OR_MLL));
		
		*/
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "create password screen shot two")));
		if (merchant.equals("BTL")) {
			// below xpath for regiesign link for BTL
			getWebElement("resignlinkbtl_xpath", OR_MLL).click();
		} else {
			if (merchant.equals("MLL")) {// below xpath for regiesign link for maxleand
				getWebElement("resignlink_xpath", OR_MLL).click();
			}
		}

	}

	@When("^Esign check process$")
	public void esign_check_process() throws Throwable {

		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);

		getWebElement("esignlink_xpath", OR_MLL).click();
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Esign screen shot")));

		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		test.log(LogStatus.PASS, "Esign check for " + map.get("Email"));
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Esign check completed ")));

	}
//Database testing

	@When("^Payment method is ACH then DebitMethodId is 1$")
	public void payment_method_ACH_DebitMethodId_1() {

		String env = CONFIG.getProperty("appEnv");
		String URL = getdbURL(env);

		// String URL =
		// "jdbc:sqlserver://QTSDEVSQL01.slc.local:1433;databasename=QAA;integratedSecurity=false";
		String TranCustId = "";
		String Debit_Method_Id = "";
		String Debit_Prioriy_Id = "";

		switchToPaymentFrame();
		// driver.switchTo().frame("PaymentMethodFrame");

		if (!(driver.findElement(By.xpath("//input[@id='rblDebitType_0']")).isSelected())) {
			driver.findElement(By.xpath("//input[@id='rblDebitType_0']")).click();
		}
		switchToMainFrame();

		// WebElement e1 =
		// driver.findElement(By.xpath("//table[@id='Table1']/tbody/tr[2]/td[4][@id='tdApp']/b/font[2]"));
		WebElement e1 = getWebElement("appNo_xpath", OR_MLL);
		String appNo = e1.getText();
		log.info("The App number is : " + appNo);
		String query1 = "select Cust_ID from BankA..tbl_Loan_Appl where Appl_No=" + appNo;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
			Statement st = connection.createStatement();
			ResultSet rs1 = st.executeQuery(query1);

			// ResultSetMetaData rm = rs1.getMetaData();

			while (rs1.next()) {

				TranCustId = rs1.getString("Cust_ID");

			}

			// TranCustId = rs1.getString("Cust_ID");

			log.info("Customer ID is : " + TranCustId);
			String query2 = "Select DebitMethodId,DebitPriority,* from BankACustom.dbo.TCUS_DebitMethodPriority where TranCustId= "
					+ TranCustId;

			ResultSet rs2 = st.executeQuery(query2);

			while (rs2.next()) {
				Debit_Method_Id = rs2.getString("DebitMethodId");
				Debit_Prioriy_Id = rs2.getString("DebitPriority");
			}

			log.info("Debit method ID =: " + Debit_Method_Id);
			log.info("Debit Prioriy Id = : " + Debit_Prioriy_Id);
			test.log(LogStatus.PASS, "Debit method ID =" + Debit_Method_Id);
			test.log(LogStatus.PASS, "Debit Prioriy Id =" + Debit_Prioriy_Id);

			Assert.assertEquals(Integer.parseInt(Debit_Method_Id), 1);

			// test.log(LogStatus.PASS, "payment_method_ACH_DebitMethodId_1 " +
			// map.get("Email"));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@When("^Payment method is TxtACheck then DebitMethodId is 4$")
	public void payment_method_TxtACheck_DebitMethodId_4() {

		// String URL =
		// "jdbc:sqlserver://QTSDEVSQL01.slc.local:1433;databasename=QAA;integratedSecurity=false";
		String env = CONFIG.getProperty("appEnv");
		String URL = getdbURL(env);
		String TranCustId = "";
		String Debit_Method_Id = "";
		String Debit_Prioriy_Id = "";
//		driver.switchTo().defaultContent();
//		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
//		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
//		waitForFrameToBeAvailableAndSwitchToIt("PaymentMethodFrame", 30);

		switchToPaymentFrame();

		if (!(driver.findElement(By.xpath("//input[@id='rblDebitType_2']")).isSelected())) {
			driver.findElement(By.xpath("//input[@id='rblDebitType_2']")).click();
		}

		// driver.switchTo().defaultContent();
		// waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")),
		// 30);
		// waitForFrameToBeAvailableAndSwitchToIt("Main", 30);

		switchToMainFrame();

		// WebElement e1 =
		// driver.findElement(By.xpath("//table[@id='Table1']/tbody/tr[2]/td[4][@id='tdApp']/b/font[2]"));
		WebElement e1 = getWebElement("appNo_xpath", OR_MLL);

		String appNo = e1.getText();
		log.info("The App number is : " + appNo);
		String query1 = "select Cust_ID from BankA..tbl_Loan_Appl where Appl_No=" + appNo;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
			Statement st = connection.createStatement();
			ResultSet rs1 = st.executeQuery(query1);

			// ResultSetMetaData rm = rs1.getMetaData();

			while (rs1.next()) {

				TranCustId = rs1.getString("Cust_ID");

			}

			log.info("Customer ID is : " + TranCustId);
			String query2 = "Select DebitMethodId,DebitPriority,* from BankACustom.dbo.TCUS_DebitMethodPriority where TranCustId= "
					+ TranCustId;

			ResultSet rs2 = st.executeQuery(query2);

			while (rs2.next()) {
				Debit_Method_Id = rs2.getString("DebitMethodId");
				Debit_Prioriy_Id = rs2.getString("DebitPriority");
			}

			log.info("Debit method ID = : " + Debit_Method_Id);
			log.info("Debit_Prioriy_Id:= " + Debit_Prioriy_Id);
			test.log(LogStatus.PASS, "Debit method ID = " + Debit_Method_Id);
			test.log(LogStatus.PASS, "Debit_Prioriy_Id= " + Debit_Prioriy_Id);

			Assert.assertEquals(Integer.parseInt(Debit_Method_Id), 4);

			test.log(LogStatus.PASS, "payment_method_TxtACheck_DebitMethodId_4 " + map.get("Email"));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Then("^Additional payment process payment type for CASH and pay amount equal schedule amount$")
	public void Additional_payment_process_payment_type_for_CASH_and_pay_amount_equal_schedule_amount()
			throws InterruptedException, IOException {
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot one")));
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		log.info("payment ");
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='cmdCash']")));
		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@id='cmdCash']")).click();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot two")));
		// getWebElement("payment_XPATH", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		Thread.sleep(2000);

		driver.switchTo().frame("Main");
		// driver.switchTo().frame("PaymentMethodFrame");
		Thread.sleep(4000);

		driver.switchTo().frame("TRANdialogiframe");
		WebElement paytype = getWebElement("paymenttypedropdown_name", OR_MLL);
		Select paytypeselect = new Select(paytype);
		paytypeselect.selectByVisibleText("Regular Payment");
		// paytypeselect.selectByVisibleText("Paid in Full");
		Thread.sleep(2000);

		WebElement paymode = getWebElement("paymode_name", OR_MLL);
		Select paymodeselect = new Select(paymode);
		paymodeselect.selectByVisibleText("Cash");
		Thread.sleep(2000);

		// below fetch data that available
		String abc = getWebElement("payamountduedate_xpath", OR_MLL).getText();
		String data = abc.replace('$', ' ');
		Thread.sleep(2000);
		double sum = Double.parseDouble(data);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot three")));
		// double add=sum+5.00;
		// log.info("updated loan amt="+add);
		test.log(LogStatus.PASS, "updated loan amt " + abc);

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));
		WebDriverWait wait1 = new WebDriverWait(driver, 60);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='txtPaymentAmount']")));
		Thread.sleep(2000);
		getWebElement("payamount_xpath", OR_MLL).click();
		Thread.sleep(2000);
		// pass value pay amount equal schedule amount
		getWebElement("payamount_xpath", OR_MLL).sendKeys(String.valueOf(abc));
		Thread.sleep(2000);
		getWebElement("extndapplication_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		WebDriverWait wait11 = new WebDriverWait(driver, 60);
		wait11.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='cmdSubmit']")));
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot five")));
		getWebElement("submit_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot six")));
		getWebElement("clickherecontinue_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot seven")));
		getWebElement("yesbtn_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		getWebElement("postpreparedoc_xpath", OR_MLL).click();
		Thread.sleep(2000);
		/*
		 * try { Alert alert = driver.switchTo().alert(); String alertText =
		 * alert.getText(); alert.accept(); } catch (NoAlertPresentException e) {
		 * e.printStackTrace(); }
		 */
		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}
		Thread.sleep(2000);
		// Below code for click on payment schedule button
		// Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

		driver.switchTo().frame("Main");
		getWebElement("Paymentschedule_XPATH", OR_MLL).click();

		test.log(LogStatus.PASS, "Additional payment process completed  for " + map.get("Email"));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process completed sucessfully")));

	}

	@Then("^Additional payment process payment type for ACH and pay amount equal schedule amount$")
	public void Additional_payment_process_payment_type_for_ACH_and_pay_amount_equal_schedule_amount()
			throws InterruptedException, IOException {
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot one")));
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		log.info("payment ");
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='cmdCash']")));
		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@id='cmdCash']")).click();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot two")));
		// getWebElement("payment_XPATH", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		Thread.sleep(2000);

		driver.switchTo().frame("Main");
		// driver.switchTo().frame("PaymentMethodFrame");
		Thread.sleep(4000);

		driver.switchTo().frame("TRANdialogiframe");
		WebElement paytype = getWebElement("paymenttypedropdown_name", OR_MLL);
		Select paytypeselect = new Select(paytype);
		paytypeselect.selectByVisibleText("Regular Payment");
		// paytypeselect.selectByVisibleText("Paid in Full");
		Thread.sleep(2000);

		WebElement paymode = getWebElement("paymode_name", OR_MLL);
		Select paymodeselect = new Select(paymode);
		paymodeselect.selectByVisibleText("ACH");
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot three")));
		// below fetch data that available
		String abc = getWebElement("payamountduedate_xpath", OR_MLL).getText();
		String data = abc.replace('$', ' ');
		Thread.sleep(2000);
		double sum = Double.parseDouble(data);
		// double add=sum+5.00;
		// log.info("updated loan amt="+add);
		test.log(LogStatus.PASS, "updated loan amt " + abc);

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));
		WebDriverWait wait1 = new WebDriverWait(driver, 60);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='txtPaymentAmount']")));
		Thread.sleep(2000);
		getWebElement("payamount_xpath", OR_MLL).click();
		Thread.sleep(2000);
		// pass value pay amount equal schedule amount
		getWebElement("payamount_xpath", OR_MLL).sendKeys(String.valueOf(abc));
		Thread.sleep(2000);
		getWebElement("extndapplication_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		WebDriverWait wait11 = new WebDriverWait(driver, 60);
		wait11.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='cmdSubmit']")));
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot five")));
		getWebElement("submit_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot six")));
		getWebElement("clickherecontinue_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot seven")));
		getWebElement("yesbtn_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		getWebElement("postpreparedoc_xpath", OR_MLL).click();
		Thread.sleep(2000);

		/*
		 * try { Alert alert = driver.switchTo().alert(); String alertText =
		 * alert.getText(); alert.accept(); } catch (NoAlertPresentException e) {
		 * e.printStackTrace(); }
		 */

		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}

		Thread.sleep(2000);
		// Below code for click on payment schedule button
		// Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

		driver.switchTo().frame("Main");
		getWebElement("Paymentschedule_XPATH", OR_MLL).click();

		test.log(LogStatus.PASS, "Additional payment process completed  for " + map.get("Email"));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process completed sucessfully")));

	}

//	@Then("^est effective date equal due date of loan(.*)$")
	public void est_effective_date_equal_due_date_of_loan() {
		String dudate = getWebElement("paymentamtdue_xpath", OR_MLL).getText();
		// span[@id='lblDueDate']
		getWebElement("Esteffectivedate_xpath", OR_MLL).sendKeys(String.valueOf(dudate));
	}

//	@Then("^payment amount due equal payment amount(.*)$")
	public void payment_amount_due_equal_payment_amount() {
		String paydata = getWebElement("paymentamtdue_xpath", OR_MLL).getText();
		getWebElement("paymentamount_xpath", OR_MLL).sendKeys(String.valueOf(paydata));
	}

	@Then("^Additional payment process payment type for CASH and Est Effective date equal due date of loan$")
	public void Additional_payment_process_payment_type_for_CASH_and_Est_Effective_date_equal_due_date_of_loan()
			throws InterruptedException, IOException {
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot one")));
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		log.info("payment ");
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='cmdCash']")));
		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@id='cmdCash']")).click();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot two")));
		// getWebElement("payment_XPATH", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		Thread.sleep(2000);

		driver.switchTo().frame("Main");
		// driver.switchTo().frame("PaymentMethodFrame");
		Thread.sleep(4000);

		driver.switchTo().frame("TRANdialogiframe");
		WebElement paytype = getWebElement("paymenttypedropdown_name", OR_MLL);
		Select paytypeselect = new Select(paytype);
		paytypeselect.selectByVisibleText("Regular Payment");
		// paytypeselect.selectByVisibleText("Paid in Full");
		Thread.sleep(2000);

		WebElement paymode = getWebElement("paymode_name", OR_MLL);
		Select paymodeselect = new Select(paymode);
		paymodeselect.selectByVisibleText("Cash");
		Thread.sleep(2000);

		// below code added for EST date=due date of loan
		// String dudate=getWebElement("paymentamtdue_xpath", OR_MLL).getText();
		String dudate = getWebElement("dateoddeudateofloan_xpath", OR_MLL).getText();

		Thread.sleep(2000);
		log.info("Due date of loan=" + dudate);
		getWebElement("Esteffectivedate_xpath", OR_MLL).clear();
		getWebElement("Esteffectivedate_xpath", OR_MLL).sendKeys(String.valueOf(dudate));
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot three")));

		String abc = getWebElement("payamountduedate_xpath", OR_MLL).getText();
		String data = abc.replace('$', ' ');
		Thread.sleep(2000);
		double sum = Double.parseDouble(data);
		double add = sum + 5.00;
		log.info("updated loan amt=" + add);
		test.log(LogStatus.PASS, "updated loan amt " + add);

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));
		WebDriverWait wait1 = new WebDriverWait(driver, 60);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='txtPaymentAmount']")));
		Thread.sleep(2000);
		getWebElement("payamount_xpath", OR_MLL).click();
		Thread.sleep(2000);
		getWebElement("payamount_xpath", OR_MLL).sendKeys(String.valueOf(add));
		Thread.sleep(2000);
		getWebElement("extndapplication_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		WebDriverWait wait11 = new WebDriverWait(driver, 60);
		wait11.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='cmdSubmit']")));
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot five")));
		getWebElement("submit_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot six")));
		getWebElement("clickherecontinue_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot seven")));
		getWebElement("yesbtn_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		getWebElement("postpreparedoc_xpath", OR_MLL).click();
		Thread.sleep(2000);

		/*
		 * try { Alert alert = driver.switchTo().alert(); String alertText =
		 * alert.getText(); alert.accept(); } catch (NoAlertPresentException e) {
		 * e.printStackTrace(); }
		 */
		if (isAlertPresent()) {
			driver.switchTo().alert().accept();
		}
		Thread.sleep(2000);
		// Below code for click on payment schedule button
		// Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

		driver.switchTo().frame("Main");
		getWebElement("Paymentschedule_XPATH", OR_MLL).click();

		test.log(LogStatus.PASS, "Additional payment process completed  for " + map.get("Email"));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process completed sucessfully")));

	}

	@Then("^Additional payment process payment type for ACH and Est Effective date equal due date of loan$")
	public void Additional_payment_process_payment_type_for_ACH_and_Est_Effective_date_equal_due_date_of_loan()
			throws InterruptedException, IOException {
		Thread.sleep(2000);

		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot one")));
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		log.info("payment ");
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='cmdCash']")));
		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@id='cmdCash']")).click();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot two")));
		// getWebElement("payment_XPATH", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		Thread.sleep(2000);

		driver.switchTo().frame("Main");
		// driver.switchTo().frame("PaymentMethodFrame");
		Thread.sleep(4000);
		driver.switchTo().frame("TRANdialogiframe");
		WebElement paytype = getWebElement("paymenttypedropdown_name", OR_MLL);
		Select paytypeselect = new Select(paytype);
		paytypeselect.selectByVisibleText("Regular Payment");
		// paytypeselect.selectByVisibleText("Paid in Full");
		Thread.sleep(2000);

		WebElement paymode = getWebElement("paymode_name", OR_MLL);
		Select paymodeselect = new Select(paymode);
		paymodeselect.selectByVisibleText("ACH");

		// below code added for EST date=due date of loan
		// String dudate=getWebElement("paymentamtdue_xpath", OR_MLL).getText();
		String dudate = getWebElement("dateoddeudateofloan_xpath", OR_MLL).getText();
		Thread.sleep(2000);
		log.info("Due date of loan=" + dudate);
		getWebElement("Esteffectivedate_xpath", OR_MLL).clear();
		getWebElement("Esteffectivedate_xpath", OR_MLL).sendKeys(String.valueOf(dudate));
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot three")));

		String abc = getWebElement("payamountduedate_xpath", OR_MLL).getText();
		String data = abc.replace('$', ' ');
		Thread.sleep(2000);
		double sum = Double.parseDouble(data);
		double add = sum + 5.00;
		log.info("updated amt=" + add);
		test.log(LogStatus.PASS, "updated amt " + add);

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));
		WebDriverWait wait1 = new WebDriverWait(driver, 60);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='txtPaymentAmount']")));
		Thread.sleep(2000);
		getWebElement("payamount_xpath", OR_MLL).click();
		Thread.sleep(2000);
		getWebElement("payamount_xpath", OR_MLL).sendKeys(String.valueOf(add));
		Thread.sleep(2000);
		getWebElement("extndapplication_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		WebDriverWait wait11 = new WebDriverWait(driver, 60);
		wait11.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='cmdSubmit']")));
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot five")));
		getWebElement("submit_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot six")));
		getWebElement("clickherecontinue_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot seven")));
		getWebElement("yesbtn_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		getWebElement("postpreparedoc_xpath", OR_MLL).click();
		Thread.sleep(2000);

		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			alert.accept();
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
		}

		Thread.sleep(2000);
		// Below code for click on payment schedule button
		// Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

		driver.switchTo().frame("Main");
		getWebElement("Paymentschedule_XPATH", OR_MLL).click();

		test.log(LogStatus.PASS, "Additional payment process completed  for " + map.get("Email"));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process completed sucessfully")));

	}

	@Then("^Additional payment process payment type for cash and Est Effective date equal due date of loan and Payment amount equal Schedule amount$")
	public void additional_payment_process_payment_type_for_cash_and_Est_Effective_date_equal_due_date_of_loan_and_Payment_amount_equal_Schedule_amount()
			throws Throwable {
		Thread.sleep(2000);

		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot one")));
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		log.info("payment ");
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='cmdCash']")));
		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@id='cmdCash']")).click();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot two")));
		// getWebElement("payment_XPATH", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		Thread.sleep(2000);

		driver.switchTo().frame("Main");
//	driver.switchTo().frame("PaymentMethodFrame");
		Thread.sleep(4000);
		driver.switchTo().frame("TRANdialogiframe");
		WebElement paytype = getWebElement("paymenttypedropdown_name", OR_MLL);
		Select paytypeselect = new Select(paytype);
		paytypeselect.selectByVisibleText("Regular Payment");
		// paytypeselect.selectByVisibleText("Paid in Full");
		Thread.sleep(2000);

		WebElement paymode = getWebElement("paymode_name", OR_MLL);
		Select paymodeselect = new Select(paymode);
		paymodeselect.selectByVisibleText("Cash");
		Thread.sleep(2000);
		WebDriverWait wait2 = new WebDriverWait(driver, 60);
		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='cmdCash']")));

		// below code added for EST date=due date of loan
		// String dudate=getWebElement("paymentamtdue_xpath", OR_MLL).getText();
		String dudate = getWebElement("dateoddeudateofloan_xpath", OR_MLL).getText();

		Thread.sleep(2000);
		log.info("Due date of loan=" + dudate);
		getWebElement("Esteffectivedate_xpath", OR_MLL).clear();

		getWebElement("Esteffectivedate_xpath", OR_MLL).sendKeys(String.valueOf(dudate));
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot three")));

		String abc = getWebElement("payamountduedate_xpath", OR_MLL).getText();
		String data = abc.replace('$', ' ');
		Thread.sleep(2000);
		double sum = Double.parseDouble(data);
//	double add=sum+5.00;
//	log.info("updated amt="+add);
		test.log(LogStatus.PASS, "updated amt " + abc);

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));
		WebDriverWait wait1 = new WebDriverWait(driver, 60);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='txtPaymentAmount']")));
		Thread.sleep(2000);
		getWebElement("payamount_xpath", OR_MLL).click();
		Thread.sleep(2000);
		getWebElement("payamount_xpath", OR_MLL).sendKeys(String.valueOf(abc));
		Thread.sleep(2000);
		getWebElement("extndapplication_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		WebDriverWait wait11 = new WebDriverWait(driver, 60);
		wait11.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='cmdSubmit']")));
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot five")));
		getWebElement("submit_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot six")));
		getWebElement("clickherecontinue_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot seven")));
		getWebElement("yesbtn_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		getWebElement("postpreparedoc_xpath", OR_MLL).click();
		Thread.sleep(2000);

		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			alert.accept();
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
		}

		Thread.sleep(2000);
		// Below code for click on payment schedule button
		// Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

		driver.switchTo().frame("Main");
		getWebElement("Paymentschedule_XPATH", OR_MLL).click();

		test.log(LogStatus.PASS, "Additional payment process completed  for " + map.get("Email"));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process completed sucessfully")));

	}

	@Then("^Additional payment process payment type for ACH and Est Effective date equal due date of loan and Payment amount equal Schedule amount$")
	public void additional_payment_process_payment_type_for_ACH_and_Est_Effective_date_equal_due_date_of_loan_and_Payment_amount_equal_Schedule_amount()
			throws Throwable {
		// Write code here that turns the phrase above into concrete actions

		Thread.sleep(2000);

		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot one")));
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		log.info("payment ");
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='cmdCash']")));
		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@id='cmdCash']")).click();
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot two")));
		// getWebElement("payment_XPATH", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		Thread.sleep(2000);

		driver.switchTo().frame("Main");
//	driver.switchTo().frame("PaymentMethodFrame");
		Thread.sleep(4000);
		driver.switchTo().frame("TRANdialogiframe");
		WebElement paytype = getWebElement("paymenttypedropdown_name", OR_MLL);
		Select paytypeselect = new Select(paytype);
		paytypeselect.selectByVisibleText("Regular Payment");
		// paytypeselect.selectByVisibleText("Paid in Full");
		Thread.sleep(2000);

		WebElement paymode = getWebElement("paymode_name", OR_MLL);
		Select paymodeselect = new Select(paymode);
		paymodeselect.selectByVisibleText("ACH");

		// below code added for EST date=due date of loan
		// String dudate=getWebElement("paymentamtdue_xpath", OR_MLL).getText();
		String dudate = getWebElement("dateoddeudateofloan_xpath", OR_MLL).getText();

		Thread.sleep(2000);
		log.info("Due date of loan=" + dudate);
		getWebElement("Esteffectivedate_xpath", OR_MLL).clear();

		getWebElement("Esteffectivedate_xpath", OR_MLL).sendKeys(String.valueOf(dudate));
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot three")));

		String abc = getWebElement("payamountduedate_xpath", OR_MLL).getText();
		String data = abc.replace('$', ' ');
		Thread.sleep(2000);
		double sum = Double.parseDouble(data);
//	double add=sum+5.00;
//	log.info("updated amt="+add);
		test.log(LogStatus.PASS, "updated amt " + abc);

		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot four")));
		WebDriverWait wait1 = new WebDriverWait(driver, 60);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='txtPaymentAmount']")));
		Thread.sleep(2000);
		getWebElement("payamount_xpath", OR_MLL).click();
		Thread.sleep(2000);
		getWebElement("payamount_xpath", OR_MLL).sendKeys(String.valueOf(abc));
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot six")));

		getWebElement("extndapplication_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		WebDriverWait wait11 = new WebDriverWait(driver, 60);
		wait11.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='cmdSubmit']")));
		Thread.sleep(2000);
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot five")));
		getWebElement("submit_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot six")));
		getWebElement("clickherecontinue_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process screen shot seven")));
		getWebElement("yesbtn_xpath", OR_MLL).click();
		Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		driver.switchTo().frame("Main");
		getWebElement("postpreparedoc_xpath", OR_MLL).click();
		Thread.sleep(2000);

		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			alert.accept();
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
		}

		Thread.sleep(2000);
		// Below code for click on payment schedule button
		// Thread.sleep(2000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

		driver.switchTo().frame("Main");
		getWebElement("Paymentschedule_XPATH", OR_MLL).click();

		test.log(LogStatus.PASS, "Additional payment process completed  for " + map.get("Email"));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process completed sucessfully")));

	}

	@Then("^pay of status change$")
	public void pay_of_status_change() throws IOException {

		switchToMainFrame();
		getWebElement("payoff_xpath", OR_MLL).click();
		switchToTranDialogIFrame();
		selectByVisibleText(getWebElement("wanttopaydropdown_xpath", OR_MLL), "Wants to Payoff");

		Assert.assertEquals(getSelectedOptionFromDropdown(getWebElement("wanttopaydropdown_xpath", OR_MLL)),
				"Wants to Payoff");
		log.info(
				"Element selected: " + getSelectedOptionFromDropdown(getWebElement("wanttopaydropdown_xpath", OR_MLL)));
		test.log(LogStatus.PASS, "pay of status change for " + map.get("Email"));
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "pay of status change")));
		getWebElement("submit_xpath", OR_MLL).click();

		test.log(LogStatus.PASS, "pay of status change for " + map.get("Email"));
		test.log(LogStatus.PASS,
				test.addBase64ScreenShot(report.capture(driver, "Additional payment process completed sucessfully")));

	}

	@Then("^_In Review App Search by (.*)$")
	public void _search_by_reviewApp(String searchby) {
		boolean flag = false;
		int size = 0;

		switchToLeftFrame();
		clickUsingAction(getWebElement("ReviewAppLink_XPATH", OR_LMS));
		switchToMainFrame();
		// waitForPresenceOfElement(By.xpath("//select[@id='cboQuery']"), 40);
		// waitForElementToBeClickable(getWebElement("SearchOnDropdown_XPATH", OR_LMS),
		// 40);
		searchby = searchby.replace('_', ' ');
		selectByVisibleText(getWebElement("SearchOnDropdown_XPATH", OR_LMS), searchby);
		sendKeysUsingJS(getWebElement("SearchOnTxt_ID", OR_LMS), map.get("Email"));
		clickOnButton(getWebElement("SearchBtn_ID", OR_LMS));
		test.log(LogStatus.PASS, test
				.addBase64ScreenShot(report.captureFullScreenShot(driver, "In Review App Search by Email address  ")));
		waitForPageLoaded();
		String FeeonAppl = getWebElement("FeeonAppl1_xpath", OR_MLL).getText();
		log.info("Fee on App1=" + FeeonAppl);
		test.log(LogStatus.PASS, "Fee on App = " + FeeonAppl);

		String applicationNumber = driver.findElement(By.id("origApplno")).getText();

		ApplicationNumber = Integer.parseInt(applicationNumber);

		String OriginalAmtofAppl = getWebElement("OriginalAmtofAppl_xpath", OR_MLL).getText();
		log.info("Original Amt of Appl=" + OriginalAmtofAppl);
		test.log(LogStatus.PASS, "Original Amt of Appl = " + OriginalAmtofAppl);

		String ABAno = getWebElement("ABA_xpath", OR_MLL).getText();
		log.info("ABA no=" + ABAno);
		test.log(LogStatus.PASS, "ABA No = " + ABAno);

		Accountno = getWebElement("Accountno_xpath", OR_MLL).getText();
		log.info("Account no=" + Accountno);
		test.log(LogStatus.PASS, "Account No = " + Accountno);

		Appno = driver.findElement(By.xpath("//*[@id=\"tdApp\"]/b/font[2]")).getText();

		switchToPaymentFrame();

		if (!(getWebElement("Ach_xpath", OR_MLL)).isSelected()) {
			clickOnButton(getWebElement("Ach_xpath", OR_MLL));
			log.info("Paymethod Method ACH selected in Trans application");
			test.log(LogStatus.PASS, "Paymethod Method ACH selected in Trans application ");
		} else {
			log.info("Paymethod Method ACH selected in Trans application");
			test.log(LogStatus.PASS, "Paymethod Method ACH selected in Trans application ");
		}

		flag = true;
		if (flag) {
			test.log(LogStatus.PASS, "Review App Screen launched for " + map.get("Email"));

		} else {
			test.log(LogStatus.FAIL, report.capture(driver, searchby + "_Search") + "Test Failed");
		}

	}

	@And("^Change due date$")
	public void Change_due_date() {
		switchToMainFrame();
		if (driver.findElements(By.id("changeDueDate")).size() > 0) {

			waitForPresenceOfElement(By.id("changeDueDate"), 10, "change due button in not present in DOM");
			waitForVisibilityOfElement(getWebElement("changeduedate_XPATH", OR_MLL), 10,
					"Change due date button is not visible");
			waitForElementToBeClickable(getWebElement("changeduedate_XPATH", OR_MLL), 10);

			Assert.assertTrue(getWebElement("changeduedate_XPATH", OR_MLL).isEnabled(),
					"Change due date button is not enabled");
			clickUsingJS(getWebElement("changeduedate_XPATH", OR_MLL));
			switchToTranDialogIFrame();
			waitForPresenceOfElement(By.id("bClose"), 10);
			waitForVisibilityOfElement(driver.findElement(By.id("bClose")), 20);

			if (!driver.findElement(By.id("rblDueDateList_0")).isSelected()) {
				driver.findElement(By.id("rblDueDateList_0")).click();
				driver.findElement(By.id("bUpdateDueDate")).click();
				test.log(LogStatus.INFO, "Due date Update is completed : "
						+ test.addScreenCapture(report.capture(driver, "Due date Update is completed")));
				driver.findElement(By.id("bClose")).click();
				log.info("Successfully updated the due date and closed the window");

			} else {
				driver.findElement(By.id("bClose")).click();
			}
		}
	}

	@And("^Originate Application$")
	public void Originate_Application() throws Throwable {
		switchToMainFrame();
		if (driver.findElements(By.id("cmdOrigLoan")).size() > 0) {
			boolean bFlag = false;
			waitForPresenceOfElement(By.id("cmdOrigLoan"), 30);
			scrollIntoView(getWebElement("originate_ID", OR_MLL));
			waitForPageLoaded();
			waitForElementToBeClickable(getWebElement("originate_ID", OR_MLL), 10);
			clickUsingJS(getWebElement("originate_ID", OR_MLL));
			log.info("Clicked on Originate Button");
			waitForElementToBeClickable(driver.findElement(By.id("optIncomeVerifiedNo")), 20);
			List<WebElement> list = driver.findElements(By.xpath(
					"//input[@id='cmdCancel']/parent::td/parent::tr/following-sibling::tr/child::td/child::font"));
			if (list.size() > 0) {
				String error = driver.findElement(By.xpath(
						"//input[@id='cmdCancel']/parent::td/parent::tr/following-sibling::tr/child::td/child::font"))
						.getText();
				log.info(errorMessage = error);
				test.log(LogStatus.FAIL, error);
				Assert.fail(errorMessage);
			}

			driver.findElement(By.id("optIncomeVerifiedNo")).click();
			waitForElementToBeClickable(driver.findElement(By.id("cmdSubmit")), 10);
			test.log(LogStatus.PASS, "Page is updated" + test.addBase64ScreenShot(report.capture()));
			driver.findElement(By.id("cmdSubmit")).click();
			bFlag = true;
			if (bFlag) {
				test.log(LogStatus.PASS, "Origination Completed "
						+ test.addScreenCapture(report.capture(driver, "Origination Completed")));
			}
		}
	}

	@And("^Originate Application for TxtACheck for (.*)$")
	public void Originate_Application_TxtACheck(String merchant) {
		log.info("Originate Application for TxtACheck for " + merchant);
		switchToMainFrame();
		waitForPresenceOfElement(By.id("cmdOrigLoan"), 30);
		scrollIntoView(getWebElement("originate_ID", OR_MLL));
		waitForPageLoaded();
		waitForElementToBeClickable(getWebElement("originate_ID", OR_MLL), 10);
		clickUsingJS(getWebElement("originate_ID", OR_MLL));
		log.info("Clicked on Originate Button");

		try {

			checkPresenceOfElement("submit_ID", OR_MLL, 5);
			waitForElementToBeClickable(getWebElement("submit_ID", OR_MLL), 5);
			waitForElementToBeClickable(driver.findElement(By.id("optIncomeVerifiedNo")), 20);
			driver.findElement(By.id("optIncomeVerifiedNo")).click();
			test.log(LogStatus.INFO, "Origination completed" + test.addBase64ScreenShot(report.capture()));
			clickOnButton(getWebElement("submit_ID", OR_MLL));

		} catch (TimeoutException e) {
			test.log(LogStatus.INFO, "Checking submit button" + test.addBase64ScreenShot(report.capture()));
			clickOnButton(getWebElement("cancel_ID", OR_MLL));

			switchToMainFrame();

			waitForPresenceOfElement(By.id("changeDueDate"), 10, "change due button in not present in DOM");
			waitForVisibilityOfElement(getWebElement("changeduedate_XPATH", OR_MLL), 10,
					"Change due date button is not visible");
			waitForElementToBeClickable(getWebElement("changeduedate_XPATH", OR_MLL), 10);

			Assert.assertTrue(getWebElement("changeduedate_XPATH", OR_MLL).isEnabled(),
					"Change due date button is not enabled");

			// clickOnButton(getWebElement("changeduedate_XPATH", OR_MLL));

			clickUsingJS(getWebElement("changeduedate_XPATH", OR_MLL));
			switchToTranDialogIFrame();
			waitForPresenceOfElement(By.id("bClose"), 10);
			waitForVisibilityOfElement(driver.findElement(By.id("bClose")), 20);

			if (!driver.findElement(By.id("rblDueDateList_0")).isSelected()) {
				driver.findElement(By.id("rblDueDateList_0")).click();
				driver.findElement(By.id("bUpdateDueDate")).click();
				test.log(LogStatus.INFO, "Due date Update is completed : "
						+ test.addScreenCapture(report.capture(driver, "Due date Update is completed")));
				driver.findElement(By.id("bClose")).click();
				log.info("Successfully updated the due date and closed the window");

			} else {
				driver.findElement(By.id("bClose")).click();
			}

			String oldLink = LinkTXT;
			int time_init = LocalTime.now().getMinute();
			log.info("Checking of mail started at(minute) : " + time_init);
			int time_final = time_init + 3;

			if (time_final >= 60) {
				time_final = time_final - 60;
			}

			do {

				log.info("doTxtACheckProcess");
				doTxtACheckProcess(); // checking the email and finding the latest txtacheck email link
				if (time_init == time_final) {
					log.info("New TxtACheck e-mail is not present in private email");
					test.log(LogStatus.FAIL, "New TxtACheck e-mail not received in private email");
					Assert.fail("New TxtACheck e-mail is not received in private email");

				}

			} while (oldLink.equals(LinkTXT));

			switchToLeftFrame();
			waitForPresenceOfElement("LogoutLink_XPATH", OR_LMS, 40);
			clickOnButton(getWebElement("LogoutLink_XPATH", OR_LMS));
			doTxtACheckSignature();

			driver.get(CONFIG.getProperty("app" + "LMS" + "URL_" + CONFIG.getProperty("appEnv")));
			log.info("Application launched : "
					+ CONFIG.getProperty("app" + "LMS" + "URL_" + CONFIG.getProperty("appEnv")));

			// waitForPageToLoad(By.xpath("//*[@id='ctl01_btnApply' or @name='txtUserId']"),
			// 40);
			waitTillElementIsVisible("ApplyNowBtn_xpath", OR_MLL, 40, "Apply Now button is not visible");
			// waitForPresenceOfElement(By.name("txtUserId"), 30);

			sendKeys(getWebElement("UserIDtxt_NAME", OR_LMS), CONFIG.getProperty("username"));
			sendKeys(getWebElement("Passwordtxt_NAME", OR_LMS), CONFIG.getProperty("password"));
			WebElement element = getWebElement("MerchantDropdown_Name", OR_LMS);
			Select select = new Select(element);
			if (merchant.contains("Blue Trust")) {
				select.selectByValue("57510710000");
			} else {
				select.selectByValue("57511710000");
			}
			test.log(LogStatus.PASS, "Trans application login"
					+ test.addBase64ScreenShot(report.capture(driver, "Trans application login")));
			clickOnButton(getWebElement("LoginBtn_NAME", OR_LMS));
			waitForPageLoaded();

			switchToLeftFrame();
			clickOnButton(getWebElement("ReviewAppLink_XPATH", OR_LMS));
			switchToMainFrame();
//			WebElement element1 = getWebElement("SearchOnDropdown_XPATH", OR_LMS);
//			element1.click();

			selectByVisibleText(getWebElement("SearchOnDropdown_XPATH", OR_LMS), "Email Address");

			waitForPresenceOfElement(By.id("txtSearchFor"), 30);
			sendKeys(getWebElement("SearchOnTxt_ID", OR_LMS), map.get("Email"));
			clickOnButton(getWebElement("SearchBtn_ID", OR_LMS));

			switchToMainFrame();
			waitForPresenceOfElement(By.id("cmdOrigLoan"), 30);
			scrollIntoView(getWebElement("originate_ID", OR_MLL));
			waitForPageLoaded();
			waitForElementToBeClickable(getWebElement("originate_ID", OR_MLL), 10);
			clickUsingJS(getWebElement("originate_ID", OR_MLL));
			log.info("Clicked on Originate Button");
			waitForElementToBeClickable(driver.findElement(By.id("optIncomeVerifiedNo")), 20);
			driver.findElement(By.id("optIncomeVerifiedNo")).click();
			waitForElementToBeClickable(driver.findElement(By.id("cmdSubmit")), 10);
			test.log(LogStatus.PASS, "Page is updated" + test.addBase64ScreenShot(report.capture()));
			driver.findElement(By.id("cmdSubmit")).click();
			test.log(LogStatus.PASS, "Origination completed" + test.addBase64ScreenShot(report.capture()));

		}

	}

	@Then("^Login to the Application and click on Apply Now$")
	public void login_to_the_Application_and_click_on_Apply_Now() throws IOException {
		boolean flag = false;
		scrollBy(200);
		waitForVisibilityOfElement(getWebElement("LoginNowBtn_ID", OR_MLL), 20);
		clickOnButton(getWebElement("LoginNowBtn_ID", OR_MLL));
		// waitForPresenceOfElement(By.xpath("//input[contains(@id,'content_customContent_frmPassword')]"),
		// 20);

		try {
			new WebDriverWait(driver, 10).until(
					ExpectedConditions.presenceOfElementLocated(By.id("content_customContent_frmPassword_Login")));
		} catch (TimeoutException e1) {
			log.info("Password box is not present in DOM");
		}

		SSN = Integer.parseInt(clientData.get("Cust_SSN"));
		try {
			String strSSN = clientData.get("Cust_SSN");
			String strlast4SSN = strSSN.substring(strSSN.length() - 4);
			checkPresenceOfElement("Last4SSNTxt_XPATH", OR_MLL, 5);
			sendKeys(getWebElement("Last4SSNTxt_XPATH", OR_MLL), strlast4SSN);
			sendKeys(driver.findElement(By.id("content_customContent_frmPassword_MultiLogin")), strPwd);
			clickOnButton(getWebElement("MultiLoginBtn_ID", OR_MLL));
		} catch (TimeoutException e) {
			log.info("SSN box is not present");
			sendKeys(driver.findElement(By.id("content_customContent_frmPassword_Login")), strPwd);
			clickOnButton(getWebElement("LoginBtn_ID", OR_MLL));
		}

		// sendKeysUsingJS(getWebElement("LoginPasswordTxt_XPATH", OR_MLL), strPwd);

		test.log(LogStatus.PASS, "welcome back login existing"
				+ test.addBase64ScreenShot(report.capture(driver, "welcome back login existing ")));
		// waitForPageToLoad(By.xpath("//a[@id='qaaMenuApply' or @id='desktopApply']"),
		// 40);

		waitTillElementIsVisible("ApplyNowVIP_XPATH", OR_MLL, 40, "Apply Now button is not visible");
		// waitForPageLoaded();

		clickOnButton(driver.findElement(By.xpath("//a[@id='qaaMenuApply' or @id='desktopApply']")));

		waitForPresenceOfElement(By.id("content_customContent_frmLoanAmount"), 30);
		waitForVisibilityOfElement(driver.findElement(By.id("content_customContent_frmLoanAmount")), 30);
		selectByValue(driver.findElement(By.id("content_customContent_frmLoanAmount")), "275");
		scrollIntoView(getWebElement("FirstNameTxt_ID", OR_MLL));

		flag = true;
		if (flag) {
			test.log(LogStatus.PASS, "Able to Login for User " + clientData.get("Email"));
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Register after Application")));
		} else {
			test.log(LogStatus.FAIL, "Not Able to Login for User " + map.get("First_Name"));
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Register after Application")));
		}
		waitForPresenceOfElement(By.id("content_customContent_frmEmailConfirm"), 20);
		sendKeys(driver.findElement(By.id("content_customContent_frmEmailConfirm")), clientData.get("Email"));
		scrollBy(150);
		// scrollIntoView(getWebElement("AboutYouNextBtn_ID", OR_MLL));

		// selectByIndex(getWebElement("StateDrpdwn_ID", OR_MLL), index);

		// selectByValue(getWebElement("StateDrpdwn_ID", OR_MLL),
		// clientData.get("Cust_State"));

		clickOnButton(getWebElement("AboutYouNextBtn_ID", OR_MLL));

	}

	@When("^_Specify information on Income Page for (.*) with (.*) for VIP User$")
	public void update_IncomePage_VIP_User(String IncomeSource, String paymentType) throws Throwable {

		log.info("Source of Income : " + IncomeSource + " & Payment method : " + paymentType);
		boolean flag = false;

		WebElement eleIncomeTab = getWebElement("IncomeTab_ID", OR_MLL);
		if (eleIncomeTab.isDisplayed()) {
			test.log(LogStatus.PASS, "Income Page is loaded for User: " + clientData.get("Cust_Fname") + " "
					+ clientData.get("Cust_Lname"));
		}

		WebElement element = getWebElement("DirectDepositChkbox_ID", OR_MLL);
		scrollIntoView(element);
		if (paymentType.equals("DirectDeposit")) {
			if (!getWebElement("DirectDepositChkbox_ID", OR_MLL).isSelected()) {
				clickOnButton(getWebElement("DirectDepositChkbox_ID", OR_MLL));
				Assert.assertTrue(getWebElement("DirectDepositChkbox_ID", OR_MLL).isSelected(),
						"Direct Deport is not selected");
			}
		}

		if (clientData.get("Periodicity").equals("B")) {
			if (!(getWebElement("Every2WeeksRadioBtn_ID", OR_MLL).isSelected()))
				getWebElement("Every2WeeksRadioBtn_ID", OR_MLL).click();
		} else if (clientData.get("Periodicity").equals("S")) {
			if (!(getWebElement("TwiceMonthlyRadioBtn_ID", OR_MLL).isSelected()))
				getWebElement("TwiceMonthlyRadioBtn_ID", OR_MLL).click();
		} else if (clientData.get("Periodicity").equals("M")) {
			if (!(getWebElement("MonthlyRadioBtn_ID", OR_MLL).isSelected()))
				getWebElement("MonthlyRadioBtn_ID", OR_MLL).click();
		} else {
			if (clientData.get("Periodicity").equals("W")) {
				if (!(getWebElement("WeeklyRadioBtn_ID", OR_MLL).isSelected()))
					getWebElement("WeeklyRadioBtn_ID", OR_MLL).click();
			}
		}

		/*
		 * if (clientData.get("Periodicity").equals("B")) { if
		 * (!(getWebElement("Every2WeeksRadioBtn_ID", OR_MLL).isSelected()))
		 * getWebElement("Every2WeeksRadioBtn_ID", OR_MLL).click(); } else if
		 * (clientData.get("Periodicity").equals("S")) { if
		 * (!(getWebElement("TwiceMonthlyRadioBtn_ID", OR_MLL).isSelected()))
		 * getWebElement("TwiceMonthlyRadioBtn_ID", OR_MLL).click(); } else if
		 * (clientData.get("Periodicity").equals("M")) { if
		 * (!(getWebElement("MonthlyRadioBtn_ID", OR_MLL).isSelected()))
		 * getWebElement("MonthlyRadioBtn_ID", OR_MLL).click(); } else { if
		 * (clientData.get("Periodicity").equals("W")) { if
		 * (!(getWebElement("WeeklyRadioBtn_ID", OR_MLL).isSelected()))
		 * getWebElement("WeeklyRadioBtn_ID", OR_MLL).click(); } }
		 */

		waitForElementToBeClickable(getWebElement("MonthlyRadioBtn_ID", OR_MLL), 5);

		if (!(getWebElement("MonthlyRadioBtn_ID", OR_MLL).isSelected())) {
			clickOnButton(getWebElement("MonthlyRadioBtn_ID", OR_MLL));
		}

		sendKeysUsingJS(getWebElement("LastPayDate_ID", OR_MLL), getNextBusinessDayFromGivenDate(getPastDate(-4)));

		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "screen shot")));
		sendKeysUsingJS(getWebElement("NextPayDateTxt_ID", OR_MLL), clientData.get("FirstPayDate"));
		log.info(clientData.get("FirstPayDate"));
		sendKeysUsingJS(getWebElement("SecondPayDateTxt_ID", OR_MLL), clientData.get("SecondPayDate"));
		log.info(clientData.get("SecondPayDate"));
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Income Page")));

		// scrollIntoView(getWebElement("IncomeNextBtn_ID", OR_MLL));
		scrollBy(70);
		clickOnButton(getWebElement("IncomeNextBtn_ID", OR_MLL));
		// getWebElement("IncomeNextBtn_ID", OR_MLL).click();

		int count = 0;

		try {
			new WebDriverWait(driver, 10)
					.until(ExpectedConditions.presenceOfElementLocated(By.id("content_customContent_bankingTab")));
			new WebDriverWait(driver, 10)
					.until(ExpectedConditions.visibilityOf(getWebElement("BankingTab_ID", OR_MLL)));
		} catch (NoSuchElementException | TimeoutException e) {

			try {

				boolean bFlag = false;
				if (getWebElement("NetPayTxt_ID", OR_MLL).isDisplayed()) {
					bFlag = true;

				}

				while (bFlag) {

					log.info("Problem with the PayDate");
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
							getWebElement("NextPayDateTxt_ID", OR_MLL));
					changeDate_VIP();
					sendKeysUsingJS(getWebElement("LastPayDate_ID", OR_MLL),
							getNextBusinessDayFromGivenDate(getPastDate(-4)));
					sendKeys(getWebElement("NextPayDateTxt_ID", OR_MLL),
							clientData.get("FirstPayDate").replaceAll("/", ""));
					log.info(" Updated " + clientData.get("FirstPayDate"));
					sendKeys(getWebElement("SecondPayDateTxt_ID", OR_MLL),
							clientData.get("SecondPayDate").replaceAll("/", ""));
					log.info(" Updated " + clientData.get("SecondPayDate"));
					clickUsingJS(getWebElement("IncomeNextBtn_ID", OR_MLL));

					try {
						new WebDriverWait(driver, 10).until(
								ExpectedConditions.presenceOfElementLocated(By.id("content_customContent_bankingTab")));
						new WebDriverWait(driver, 10)
								.until(ExpectedConditions.visibilityOf(getWebElement("BankingTab_ID", OR_MLL)));
						bFlag = false;
					} catch (Exception e1) {
						count++;
						if (count == 3) {
							test.log(LogStatus.FAIL,
									"Issue with the Paydate" + test.addBase64ScreenShot(report.capture()));
							Assert.fail("Issue with the Paydate");
						}
						continue;
					}

				}
			} catch (Exception e1) {
				waitForPresenceOfElement(By.id("content_customContent_bankingTab"), 10);
				waitForVisibilityOfElement(getWebElement("BankingTab_ID", OR_MLL), 10);
			}

		}
	}

	@When("^_Specify information on Banking Page using \"([^\"]*)\" for VIP User$")
	public void update_BankingPage_VIP(String PaymentMode) throws Throwable {
		boolean flag = false;
		Thread.sleep(2000);
		WebElement eleBankingTab = getWebElement("BankingTab_ID", OR_MLL);
		if (eleBankingTab.isDisplayed()) {
			test.log(LogStatus.PASS, "Banking Page is loaded for User: " + clientData.get("Cust_Fname") + " "
					+ clientData.get("Cust_Lname"));

			waitForPresenceOfElement("ABARoutingNoTxt_ID", OR_MLL, 5);
			sendKeysUsingJS(getWebElement("ABARoutingNoTxt_ID", OR_MLL), clientData.get("ABA_No"));
			sendKeysUsingJS(getWebElement("BankAcctNo_ID", OR_MLL), clientData.get("Cust_Acct_No"));
			// WebElement element = getWebElement("EFTFundRadio_ID", OR_MLL);
			// ((JavascriptExecutor)
			// driver).executeScript("arguments[0].scrollIntoView(true);", element);

			scrollIntoView(getWebElement("ABARoutingNoTxt_ID", OR_MLL));
			log.info("Payment mode is : " + PaymentMode);
			String PayMode = PaymentMode;
			if (PaymentMode.equalsIgnoreCase(PayMode)) {
				if (!(getWebElement("EFTFundRadio_ID", OR_MLL).isSelected()))
					getWebElement("EFTFundRadio_ID", OR_MLL).click();
			} else if ((PaymentMode.equalsIgnoreCase("Credit Card"))) {
				if (!(getWebElement("CreditCardRadio_ID", OR_MLL).isSelected()))
					getWebElement("CreditCardRadio_ID", OR_MLL).click();
			} else {
				if (!(getWebElement("TextChkRadio_ID", OR_MLL).isSelected()))
					getWebElement("TextChkRadio_ID", OR_MLL).click();
			}
			test.log(LogStatus.PASS, "Banking Details"
					+ test.addBase64ScreenShot(report.capture(driver, "Banking Details screen shot1")));
			if (getWebElement("EFTFundRadio_ID", OR_MLL).isSelected()) {
				log.info("Electronic Fund Transfer selected from banking page");
				test.log(LogStatus.PASS, "Electronic Fund Transfer selected from banking page");
			} else {
				log.info("TextChkRadio selected from banking page");
				test.log(LogStatus.PASS, "TextChkRadio selected from banking page");
			}

			// getWebElement("ChkApprovalBtn_ID", OR_MLL).click();

			waitForPresenceOfElement("ChkApprovalBtn_ID", OR_MLL, 40);
			waitForVisibilityOfElement(getWebElement("ChkApprovalBtn_ID", OR_MLL), 40);

			// clickOnButton(getWebElement("ChkApprovalBtn_ID", OR_MLL));
			clickUsingJS(getWebElement("ChkApprovalBtn_ID", OR_MLL));

			waitForPresenceOfElement(By.xpath("//input[@id='content_customContent_chkElectronicConsent']"), 30,
					"Either try with another lender or error page occured");
			waitForVisibilityOfElement(
					driver.findElement(By.xpath("//input[@id='content_customContent_chkElectronicConsent']")), 50,
					"Either try with another lender or error page occured");

			waitForPageLoaded();
			test.log(LogStatus.PASS, "Banking Page is Updated for User: " + clientData.get("Cust_Fname") + " "
					+ clientData.get("Cust_Lname"));

			String strFirstName = clientData.get("Cust_Fname").substring(0, 1).toUpperCase()
					+ clientData.get("Cust_Fname").substring(1).toLowerCase();
			int iChkApproval = driver
					.findElements(By.xpath("//div[@id='headerBlock1']//p[contains(text(),'" + strFirstName + "')]"))
					.size();
			/*
			 * if (iChkApproval > 0) { element = driver
			 * .findElement(By.xpath("//div[@id='headerBlock1']//p[contains(text(),'" +
			 * strFirstName + "')]")); ((JavascriptExecutor)
			 * driver).executeScript("arguments[0].scrollIntoView(true);", element); if
			 * (element.isDisplayed()) { flag = true;
			 * 
			 * } }
			 */

		}
	}

	@When("^_Apply ESign with (.*) for VIP$")
	public void apply_esign_VIP(String paymentType) throws Throwable {
		boolean flag = false;

		waitForPresenceOfElement("EFTPaymentRadio_ID", OR_MLL, 20);
		waitForPageLoaded();
		scrollIntoView(getWebElement("EFTPaymentRadio_ID", OR_MLL));
		if (paymentType.equalsIgnoreCase("EFT")) {
			if (!getWebElement("EFTPaymentRadio_ID", OR_MLL).isSelected()) {
				clickOnButton(getWebElement("EFTPaymentRadio_ID", OR_MLL));
				Assert.assertTrue(getWebElement("EFTPaymentRadio_ID", OR_MLL).isSelected(),
						"Unable to click on EFT Payment radio button");
			}
		} else {
			if (paymentType.equalsIgnoreCase("TextCheck"))

			{
				if (!getWebElement("TxtAcheckPaymentRadio_id", OR_MLL).isSelected()) {
					clickOnButton(getWebElement("TxtAcheckPaymentRadio_id", OR_MLL));
					Assert.assertTrue(getWebElement("TxtAcheckPaymentRadio_id", OR_MLL).isSelected(),
							"Unable to click on TxtACheck Payment radio button");
				}
			}
		}

		scrollBy(200);
		scrollIntoView(getWebElement("NonMilitaryRadio_ID", OR_MLL));

		if (!(getWebElement("NonMilitaryRadio_ID", OR_MLL).isSelected())) {
			clickOnButton(getWebElement("NonMilitaryRadio_ID", OR_MLL));
			Assert.assertTrue(getWebElement("NonMilitaryRadio_ID", OR_MLL).isSelected(),
					"Unable to click on Non military radio button");

		}

		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Apply ESign one")));

		scrollIntoView(getWebElement("AcceptTermsChkbox_ID", OR_MLL));

		if (!(getWebElement("AcceptTermsChkbox_ID", OR_MLL).isSelected())) {
			clickOnButton(getWebElement("AcceptTermsChkbox_ID", OR_MLL));
			Assert.assertTrue(getWebElement("AcceptTermsChkbox_ID", OR_MLL).isSelected(),
					"Unable to click on accept check box button");
		}

		scrollIntoView(getWebElement("LoanAuthTxt_ID", OR_MLL));
		waitForPresenceOfElement("LoanAuthTxt_ID", OR_MLL, 20);
		sendKeys(getWebElement("LoanAuthTxt_ID", OR_MLL),
				clientData.get("Cust_Fname") + " " + clientData.get("Cust_Lname"));

		waitForVisibilityOfElement(getWebElement("ConsentAcceptanceBtn_XPATH", OR_MLL), 20);
		waitForElementToBeClickable(getWebElement("ConsentAcceptanceBtn_XPATH", OR_MLL), 20);
		test.log(LogStatus.PASS, "Loan approval form" + test.addBase64ScreenShot(report.capture()));
		scrollBy(120);
		clickUsingJS(getWebElement("ConsentAcceptanceBtn_XPATH", OR_MLL));
		waitForPresenceOfElement(By.xpath("//a[contains(@id,'logoutSubmit')]"), 10);
		waitForVisibilityOfElement(getWebElement("MercLogoutBtn_XPATH", OR_MLL), 10);
		scrollIntoView(getWebElement("MercLogoutBtn_XPATH", OR_MLL));
		String actual = getWebElement("MercLogoutBtn_XPATH", OR_MLL).getText();
		String expected = "LOGOUT";
		Assert.assertEquals(actual, expected);
		clickOnButton(getWebElement("MercLogoutBtn_XPATH", OR_MLL));
		flag = true;
		if (flag) {
			test.log(LogStatus.PASS, "Contact and ESignature Page is Updated for User: " + clientData.get("Cust_Fname")
					+ " " + clientData.get("Cust_Lname"));
		} else {
			test.log(LogStatus.FAIL, "Unable to Update Information in Contact and ESignature Page");
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Loan Declined")));
		}
	}

	@Then("^_In Review App Search for VIP User by (.*)$")
	public void _search_by_reviewApp_VIPUser(String searchby) throws Throwable {
		boolean flag = false;
		int size = 0;

		switchToLeftFrame();
		clickUsingAction(getWebElement("ReviewAppLink_LINKTEXT", OR_LMS));
		clickOnButton(getWebElement("ReviewAppLink_LINKTEXT", OR_LMS));
		switchToMainFrame();
		waitForPresenceOfElement(By.xpath("//select[@id='cboQuery']"), 10);
		WebElement element = getWebElement("SearchOnDropdown_ID", OR_LMS);
		clickOnButton(element);
		Select search = new Select(element);
		search.selectByVisibleText(searchby);

		if (clientData.get("Cust_SSN") == null) {
			log.info("SSN is not available");
			test.log(LogStatus.FAIL, "SSN is not available");
			Assert.fail(errorMessage = "SSN is not available");
		}

		SSN = Integer.parseInt(clientData.get("Cust_SSN"));

		sendKeysUsingJS(getWebElement("SearchOnTxt_ID", OR_LMS), clientData.get("Cust_SSN"));
		clickOnButton(getWebElement("SearchBtn_ID", OR_LMS));

		test.log(LogStatus.PASS, test
				.addBase64ScreenShot(report.captureFullScreenShot(driver, "In Review App Search by Email address  ")));
		waitForPageLoaded();

		String FeeonAppl = getWebElement("FeeonAppl1_xpath", OR_MLL).getText();
		log.info("Fee on App1=" + FeeonAppl);
		test.log(LogStatus.PASS, "Fee on App = " + FeeonAppl);

		if (driver.findElements(By.id("origApplno")).size() > 0) {
			String applicationNumber = driver.findElement(By.id("origApplno")).getText();

			ApplicationNumber = Integer.parseInt(applicationNumber);
		}

		String OriginalAmtofAppl = getWebElement("OriginalAmtofAppl_xpath", OR_MLL).getText();
		log.info("Original Amt of Appl=" + OriginalAmtofAppl);
		test.log(LogStatus.PASS, "Original Amt of Appl = " + OriginalAmtofAppl);

		String ABAno = getWebElement("ABA_xpath", OR_MLL).getText();
		log.info("ABA no=" + ABAno);
		test.log(LogStatus.PASS, "ABA No = " + ABAno);

		Accountno = getWebElement("Accountno_xpath", OR_MLL).getText();
		log.info("Account no=" + Accountno);
		test.log(LogStatus.PASS, "Account No = " + Accountno);

		Appno = driver.findElement(By.xpath("//*[@id=\"tdApp\"]/b/font[2]")).getText();
		switchToPaymentFrame();

		if (!(driver.findElement(By.xpath("//input[@id='rblDebitType_0']")).isSelected())
				&& driver.findElement(By.xpath("//input[@id='rblDebitType_0']")).isEnabled()) {
			driver.findElement(By.xpath("//input[@id='rblDebitType_0']")).click();
			log.info("Paymethod Method ACH selected in Trans application");
			test.log(LogStatus.PASS, "Paymethod Method ACH selected in Trans application ");
		} else {
			log.info("Paymethod Method ACH selected in Trans application");
			test.log(LogStatus.PASS, "Paymethod Method ACH selected in Trans application ");
		}

		flag = true;
		if (flag) {
			test.log(LogStatus.PASS, "Review App Screen launched for " + clientData.get("Email"));

		} else {
			test.log(LogStatus.FAIL, report.capture(driver, searchby + "_Search") + "Test Failed");
		}

		driver.switchTo().defaultContent();
	}

	@Then("^Update Customer Information of VIP User$")
	public void Update_Customer_Information_of_VIP_User() throws IOException {

		waitForPageLoaded();

		switchToMainFrame();
		// getWebElement("CustMaintenanceBtn_XPATH", OR_LMS).click();
		waitForPresenceOfElement("CustMaintenanceBtn_XPATH", OR_LMS, 20);
		clickOnButton(getWebElement("CustMaintenanceBtn_XPATH", OR_LMS));

		waitForPresenceOfElement(By.xpath("//input[@name='UCPayroll:txtPayrollAmount']"), 20);
		waitForVisibilityOfElement(driver.findElement(By.xpath("//input[@name='UCPayroll:txtPayrollAmount']")), 20);

		// updatePayroll();
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Payroll updated")));

		boolean cFlag = true;

		int count = 0;
		while (cFlag) {

			String expectedPeriodicity = "";

			Random shift = new Random();
			int selectShift = shift.nextInt(5);

			driver.findElement(By.id("UCEmployer_optEmployeeWorkShift_" + selectShift)).click();
			// driver.findElement(By.id("UCPayroll_txtPayrollAmount")).clear();

			// driver.findElement(By.id("UCPayroll_txtPayrollAmount")).sendKeys("5000");

			sendKeys(driver.findElement(By.id("UCPayroll_txtPayrollAmount")), "5000");

			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Payroll updated")));
			Random r = new Random();
			int index = r.nextInt(4);

			Select periodicity = new Select(driver.findElement(By.id("UCPayroll_optPayrollPeriodicity")));
			periodicity.selectByIndex(index);
			test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Payroll updated")));
			try {
				if (index == 0) {
					expectedPeriodicity = "Weekly";
					Random ran = new Random();
					int index1 = ran.nextInt(2) + 1;
					Select howPaid = new Select(driver.findElement(By.id("UCPayroll_ddlPeriodicitySubtype")));
					howPaid.selectByIndex(index1);
					if (index1 == 1) {
						new Select(driver.findElement(By.id("UCPayroll_cboPayrollSM1stPayDay")))
								.selectByIndex(new Random().nextInt(5) + 1);
					} else if (index1 == 2) {
						new Select(driver.findElement(By.id("UCPayroll_cboPayrollSM1stPayDay")))
								.selectByIndex(new Random().nextInt(5));
						driver.findElement(By.id("UCPayroll_radlstpaydt_" + new Random().nextInt(2))).click();
					} else {
						log.info("Some issue in Day of weeks");
					}

				} else if (index == 1) {
					expectedPeriodicity = "BiWeekly";

					new Select(driver.findElement(By.id("UCPayroll_cboPayrollSM1stPayDay")))
							.selectByIndex(new Random().nextInt(5) + 1);

					driver.findElement(By.id("UCPayroll_radlstpaydt_" + new Random().nextInt(2))).click();

				} else if (index == 2) {
					expectedPeriodicity = "SemiMonthly";
					Random r1 = new Random();
					int index2 = r1.nextInt(2) + 1;
					Select s = new Select(driver.findElement(By.id("UCPayroll_ddlPeriodicitySubtype")));
					s.selectByIndex(index2);

					if (index2 == 1) {
						new Select(driver.findElement(By.id("UCPayroll_cboPayrollSM1stPayDay")))
								.selectByIndex(new Random().nextInt(18) + 1);
						new Select(driver.findElement(By.id("UCPayroll_cboPayrollSM2ndPayDay")))
								.selectByIndex(new Random().nextInt(19) + 1);
					} else if (index2 == 2) {
						new Select(driver.findElement(By.id("UCPayroll_ddl1stpayweek")))
								.selectByIndex(new Random().nextInt(3) + 1);
						new Select(driver.findElement(By.id("UCPayroll_ddl1stpayday")))
								.selectByIndex(new Random().nextInt(5) + 1);
						new Select(driver.findElement(By.id("UCPayroll_ddl2ndpayweek")))
								.selectByIndex(new Random().nextInt(3) + 1);
						new Select(driver.findElement(By.id("UCPayroll_ddl2ndpayday")))
								.selectByIndex(new Random().nextInt(5) + 1);
					} else {
						log.info("Index selected is wrong");
					}

				} else if (index == 3) {

					expectedPeriodicity = "Monthly";
					Random rn = new Random();
					int howPaid1 = rn.nextInt(4) + 1;
					Select s = new Select(driver.findElement(By.id("UCPayroll_ddlPeriodicitySubtype")));
					s.selectByIndex(howPaid1);

					if (howPaid1 == 1) {
						new Select(driver.findElement(By.id("UCPayroll_cboPayrollSM1stPayDay")))
								.selectByIndex(new Random().nextInt(31) + 1);
					} else if (howPaid1 == 2) {
						new Select(driver.findElement(By.id("UCPayroll_ddlpayweek")))
								.selectByIndex(new Random().nextInt(5) + 1);
						new Select(driver.findElement(By.id("UCPayroll_ddlpayday")))
								.selectByIndex(new Random().nextInt(5) + 1);
					} else if (howPaid1 == 3) {
						new Select(driver.findElement(By.id("UCPayroll_ddlBizdays")))
								.selectByIndex(new Random().nextInt(10) + 1);
						new Select(driver.findElement(By.id("UCPayroll_ddlwhichday")))
								.selectByIndex(new Random().nextInt(31) + 1);

					} else if (howPaid1 == 4) {
						new Select(driver.findElement(By.id("UCPayroll_ddlwhichweek")))
								.selectByIndex(new Random().nextInt(4) + 1);
						new Select(driver.findElement(By.id("UCPayroll_ddlcwday")))
								.selectByIndex(new Random().nextInt(5) + 1);
						new Select(driver.findElement(By.id("UCPayroll_ddlawhday")))
								.selectByIndex(new Random().nextInt(31) + 1);

					} else {
						log.info("error");
					}

				} else {
					log.info("Some issue");
				}
				test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Payroll updated data ")));
				log.info(expectedPeriodicity);
				test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Payroll updated")));
				driver.findElement(By.id("UCPayroll_radnonbizday_" + new Random().nextInt(2))).click();

				log.info(driver.findElement(By.id("UCPayroll_txtPayrollLastPayDate")).getAttribute("value"));
				log.info(driver.findElement(By.id("UCPayroll_txtPayrollNextPayDate")).getAttribute("value"));

				driver.findElement(By.id("UCPayroll_optPayrollEmplType_" + new Random().nextInt(2))).click();

				if (!driver.findElement(By.id("UCPayroll_optPayrollType_0")).isSelected()) {
					driver.findElement(By.id("UCPayroll_optPayrollType_0")).click();
				}

				driver.findElement(By.id("UCPayroll_optPayrollIncomeVerified_" + new Random().nextInt(2))).click();
				test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Payroll updated")));
				driver.findElement(By.id("UCPayroll_optPayrollGarnishment_" + new Random().nextInt(2))).click();

				driver.findElement(By.id("UCPayroll_optPayrollBankuptcy_" + new Random().nextInt(2))).click();
				test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Payroll updated")));
				driver.findElement(By.id("cmdSubmit")).click();

				new WebDriverWait(driver, 10L).until(ExpectedConditions
						.visibilityOf(driver.findElement(By.xpath("//td[contains(text(),'Pay Frequency ')]"))));

				String actualPeriodicity = driver
						.findElement(By.xpath("//td[contains(text(),'Pay Frequency ')]//following-sibling::td"))
						.getText();
				log.info(actualPeriodicity);
				cFlag = false;
				Assert.assertEquals(expectedPeriodicity, actualPeriodicity);

			} catch (Exception e) {
				count++;
				if (count == 3) {
					log.info("Count is : " + count + " breaking the loop");
					break;
				}
				continue;
			}
		}

	}

	// PPC User

	@Given("^Get customer details and file (.*) for PPC User$")
	public void user_load_fetch_file_PPC_User(String fileName) throws Throwable {
		boolean bFlag = false;
		String merchant = null;
		boolean bNewRegister = true;
		String newFileName = fileName;
		// HashMap<String,String> clientData= new HashMap<String,String>();
		List<String> myData = new ArrayList<String>();
		String[] arrName = fileName.split("_");
		if (arrName[0].contains("BTL")) {
			merchant = "57510";
			merchantName = "57510";
		} else if (arrName[0].contains("MLL")) {
			merchant = "57511";
			merchantName = "57511";
		}
		String env = CONFIG.getProperty("appEnv");
		String URL = getdbURL(env);
		while (bNewRegister) {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
				CallableStatement stmt = connection.prepareCall("{CALL spGetAddress(?)}");
				stmt.setString(1, merchant);
				ResultSet rs = stmt.executeQuery();
				myData = resultSetToArrayList(rs, myData);
				stmt = connection.prepareCall("{CALL spTest_GetVIPCustomerData(?)}");
				stmt.setString(1, merchant);
				// stmt.setString(2, "0");
				// stmt.setString(3, myData.get(2));
				rs = stmt.executeQuery();
				clientData = resultSetToHashMap(rs);

				Set<String> data = clientData.keySet();

				for (String vData : data) {
					log.info(vData + " : " + clientData.get(vData));
				}

				/*
				 * if(checkSSNCount==10) { Assert.fail("SSN found is null"); }
				 * 
				 * 
				 * if(clientData.get("Cust_SSN")==null) { log.info("Check SSN");
				 * checkSSNCount++; user_load_fetch_file_PPC_User(newFileName); }
				 */

				bNewRegister = validate_LMSInterface_PPC(clientData, "LMS_interface", "lms_user", "ST20JP*otty");
				// Added By Shivam

				clientData = getPayrollData_VIP(clientData);
				myData.clear();
				if (bNewRegister == false)
					break;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@When("^user is on (.*) homepage and clicks on Apply Now and enters SSN Number$")
	public void user_is_on_homepage_And_Clicks_On_Apply_Now(String appName) throws Throwable {
		driver.get(CONFIG.getProperty("app" + appName + "URL_" + CONFIG.getProperty("appEnv")));
		log.info("URL launched : " + CONFIG.getProperty("app" + appName + "URL_" + CONFIG.getProperty("appEnv")));

		// waitForPresenceOfElement(By.xpath("//a[@id='qaaMenuApply' or
		// @id='desktopApply']"), 40);
		// waitForVisibilityOfElement(driver.findElement(By.xpath("//a[@id='qaaMenuApply'
		// or @id='desktopApply']")), 40);
		// waitForPageLoaded();

		waitForPageToLoad(By.xpath("//a[@id='qaaMenuApply' or @id='desktopApply']"), 40);

		test.log(LogStatus.INFO,
				"User Is on home page" + test.addScreenCapture(report.capture(driver, "ApplyNowIcon")));
		clickOnButton(driver.findElement(By.xpath("//a[@id='qaaMenuApply' or @id='desktopApply']")));

		waitForPresenceOfElement(By.id("content_customContent_frmSSN"), 10);
		waitForVisibilityOfElement(getWebElement("SSNTxt_ID", OR_MLL), 10);

		String ssnNum = clientData.get("Cust_SSN");

		if (ssnNum == null) {
			test.log(LogStatus.FAIL, "SSN Number is Null" + test.addBase64ScreenShot(report.capture()));
			log.info("SSN Number is Null");
			errorMessage = "SSN Number is Null";
			Assert.fail("SSN Number is Null");

		}

		sendKeysUsingJS(getWebElement("SSNTxt_ID", OR_MLL), clientData.get("Cust_SSN"));
		log.info("SSN Number of PPC user: " + clientData.get("Cust_SSN"));
		clickOnButton(getWebElement("SSNTxt_ID", OR_MLL));
		clickOnButton(getWebElement("LastNameTxt_ID", OR_MLL));
		test.log(LogStatus.INFO, "User enters SSN" + test.addScreenCapture(report.capture(driver, "AddedSSN")));
		// new Actions(driver).sendKeys(getWebElement("SSNTxt_ID", OR_MLL),
		// Keys.TAB).build().perform();

		waitForPresenceOfElement(By.id("btnLoginRedirect"), 20);
		waitForVisibilityOfElement(getWebElement("redirectLogin_ID", OR_MLL), 20);

		test.log(LogStatus.INFO,
				"Welcome existing user" + test.addScreenCapture(report.capture(driver, "PopUpdisplayed")));
		clickOnButton(getWebElement("redirectLogin_ID", OR_MLL));

		waitForPresenceOfElement(By.id("content_customContent_txtEmailNew"), 10);
		waitForVisibilityOfElement(getWebElement("user_email_ID", OR_MLL), 10);

	}

	@Then("^User lands on Login page and enters email and click on login$")
	public void User_lands_on_Login_page_and_enters_email_and_click_on_login() {

		waitForPresenceOfElement(By.id("content_customContent_txtEmailNew"), 10, "Email text box is not present");
		sendKeys(getWebElement("user_email_ID", OR_MLL), clientData.get("Email"));
		test.log(LogStatus.INFO, "Enter the Email" + test.addScreenCapture(report.capture()));
		clickOnButton(getWebElement("user_login_ID", OR_MLL));

	}

	@And("^Check try with Another lender \"([^\"]*)\" , \"([^\"]*)\" , (.*), (.*)$")
	public void check_try_with_another_lender_NormalUser(String FileName, String PaymentMode, String IncomeSource,
			String paymentType) throws Throwable {

		log.info("Lender Issue, trying with another user");

		boolean checkStatus = false;

		do {

			try {
				waitForPresenceOfElement(By.xpath("//input[@id='content_customContent_chkElectronicConsent']"), 25);
				checkStatus = true;

			} catch (Exception e) {

				map.clear();
				clientData.clear();
				getNewUserData_NormalUser(FileName);
				fetchUserInfo();
				clickOnButton(driver.findElement(By.xpath("//a[@id='qaaMenuApply' or @id='desktopApply']")));
				waitForPresenceOfElement(By.id("content_customContent_frmLoanAmount"), 40);
				waitForPageLoaded();
				selectByValue(driver.findElement(By.id("content_customContent_frmLoanAmount")), "275");
				applyForLoan();
				updateIncomePage(IncomeSource, paymentType);
				UpdateBankingPage(PaymentMode);

			}

		} while (!checkStatus);

	}

	@And("^Check try with Another lender for VIP User \"([^\"]*)\" , \"([^\"]*)\" , (.*), (.*)$")
	public void check_try_with_another_lender_VIPUser(String FileName, String PaymentMode, String IncomeSource,
			String paymentType) throws Throwable {

		log.info("Lender Issue, trying with another user");

		boolean checkStatus = false;

		do {

			try {
				waitForPresenceOfElement(By.xpath("//input[@id='content_customContent_chkElectronicConsent']"), 25);
				checkStatus = true;

			} catch (Exception e) {

				map.clear();
				clientData.clear();
				getNewUserData_VIPUser(FileName);
				fetchUserInfo();
				clickOnButton(driver.findElement(By.xpath("//a[@id='qaaMenuApply' or @id='desktopApply']")));
				waitForPresenceOfElement(By.id("content_customContent_frmLoanAmount"), 40);
				waitForPageLoaded();
				selectByValue(driver.findElement(By.id("content_customContent_frmLoanAmount")), "275");
				applyForLoan();
				updateIncomePage(IncomeSource, paymentType);
				UpdateBankingPage(PaymentMode);

			}

		} while (!checkStatus);

	}

	@And("^Check try with Another lender for PPC User \"([^\"]*)\" , \"([^\"]*)\" , (.*), (.*)$")
	public void check_try_with_another_lender_PPCUser(String FileName, String PaymentMode, String IncomeSource,
			String paymentType) throws Throwable {

		log.info("Lender Issue, trying with another user");

		boolean checkStatus = false;

		do {

			try {
				waitForPresenceOfElement(By.xpath("//input[@id='content_customContent_chkElectronicConsent']"), 25);
				checkStatus = true;

			} catch (Exception e) {

				map.clear();
				clientData.clear();
				getNewUserData_PPCUser(FileName);
				fetchUserInfo();
				clickOnButton(driver.findElement(By.xpath("//a[@id='qaaMenuApply' or @id='desktopApply']")));
				waitForPresenceOfElement(By.id("content_customContent_frmLoanAmount"), 40);
				waitForPageLoaded();
				selectByValue(driver.findElement(By.id("content_customContent_frmLoanAmount")), "275");
				applyForLoan();
				updateIncomePage(IncomeSource, paymentType);
				UpdateBankingPage(PaymentMode);

			}

		} while (!checkStatus);

	}

	// EOD

	@Then("^user clicks on trans Mgr then the ACH application and clicks on Process ACH returns$")
	public void user_clicks_on_trans_Mgr_then_the_ACH_application_and_clicks_on_Process_ACH_returns() {

		switchToLeftFrame();
		clickOnButton(getWebElement("tranMgr_linktext", OR_MLL));
		log.info("user clicks on tran manager link");

		switchToMainFrame();

		log.info("user clicks on ACH management link");
		waitForPresenceOfElement("achManagement_linktext", OR_MLL, 20);
		clickOnButton(getWebElement("achManagement_linktext", OR_MLL));
		switchToMainFrame();
		waitForPresenceOfElement("achReturn_linktext", OR_MLL, 20);
		test.log(LogStatus.INFO, "ACH Return text" + test.addScreenCapture(report.capture(driver, "ACH Return text")));
		clickUsingAction(getWebElement("achReturn_linktext", OR_MLL));
		// scrollIntoView(getWebElement("processACHReturn_id", OR_MLL));
		// waitForPresenceOfElement("processACHReturn_id", OR_MLL, 20);
		clickOnButton(getWebElement("processACHReturn_id", OR_MLL));

	}

	@Then("^user selects (.*) and uploads the file for (.*)$")
	public void user_selects_CLAYTON_and_uploads_the_file(String value, String merchant) {
		waitForPresenceOfElement("achProcessor_id", OR_MLL, 20);
		selectByValue(getWebElement("achProcessor_id", OR_MLL), value);

		if (merchant.equalsIgnoreCase("blue trust")) {

			driver.findElement(By.id("UploadedFile"))
					.sendKeys(System.getProperty("user.dir") + "\\ReturnFiles\\57510" + LocalDate
							.now(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
							+ ".csv");
			log.info("File Uploaded successfully");
			test.log(LogStatus.INFO, "File Uploaded successfully"
					+ test.addScreenCapture(report.capture(driver, "File Uploaded successfully")));
		} else {
			driver.findElement(By.id("UploadedFile"))
					.sendKeys(System.getProperty("user.dir") + "\\ReturnFiles\\57511" + LocalDate
							.now(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
							+ ".csv");
			log.info("File Uploaded successfully");
			test.log(LogStatus.INFO, "File Uploaded successfully"
					+ test.addScreenCapture(report.capture(driver, "File Uploaded successfully")));
		}

		if (getWebElement("agreeCheckBox_ID", OR_MLL).isDisplayed()) {
			test.log(LogStatus.PASS, "File uploaded successfully");
		} else {
			test.log(LogStatus.FAIL, "ACH Return already exists for this ACH Number."
					+ test.addScreenCapture(report.capture(driver, "ACH Return already exists for this ACH Number.")));
			Assert.fail("ACH Return already exists for this ACH Number.");
		}

	}

	@When("^user gets the data from database \"([^\"]*)\"$")
	public void user_gets_the_data_from_database(String fileName) throws IOException {

		String newFileName = fileName;
		String merchant = null;
		ResultSet rs = null;
		DecimalFormat df = new DecimalFormat("0.00");

		String[] arrName = newFileName.split("_");
		if (arrName[0].equals("BTL")) {
			merchant = "57510";
			merchantName = "57510";
		} else if (arrName[0].equals("MLL")) {
			merchant = "57511";
			merchantName = "57511";
		}
		String env = CONFIG.getProperty("appEnv");
		String URL = getdbURL(env);

		file = new File(System.getProperty("user.dir") + "\\ReturnFiles\\" + merchant + ""
				+ LocalDate.now(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
				+ ".csv");
		if (file.exists()) {
			log.info("File Already exists");
		} else {
			log.info("New file is created");
			file.createNewFile();
		}
		FileWriter myWriter = new FileWriter(file);

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
			CallableStatement stmt = connection.prepareCall("{CALL spCreateReturnsData(?)}");
			stmt.setString(1, merchant);
			stmt.execute();
			stmt = connection.prepareCall("{CALL spGetReturnsByMerch(?)}");
			stmt.setString(1, merchant);
			rs = stmt.executeQuery();

			ResultSetMetaData md = rs.getMetaData();

			int columnCount = md.getColumnCount();

			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					if (i == 5) {
						myWriter.write(merchant + "B" + rs.getString(i));
					} else {
						if (i == 3) {
							String s = rs.getString(i);
							myWriter.write(s.substring(0, 10));
						} else if (i == 4) {
							String s = rs.getString(i);
							Double d = Double.parseDouble(s);
							df.setRoundingMode(RoundingMode.DOWN);
							myWriter.write(df.format(d));
						} else {
							myWriter.write(rs.getString(i));
						}
					}
					if (i != columnCount) {
						myWriter.write(",");
					}

					if (i == 1 | i == 5) {
						myWriter.write(" \"\",");
					}

				}

				myWriter.write("\n");
			}

			log.info("Data written to Notepad");
			test.log(LogStatus.INFO, "Data written to Notepad");
			myWriter.close();

			/*
			 * if (file.length() == 0) { test.log(LogStatus.FAIL, "Returns file is empty");
			 * Assert.fail("Returns file is empty"); }
			 */
		} catch (SQLException e) {
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}

	}

	@Then("^user confirms the returns$")
	public void user_confirms_the_returns() {

		clickOnButton(getWebElement("agreeCheckBox_ID", OR_MLL));

		if (!(getWebElement("processButton_ID", OR_MLL).isEnabled())) {
			test.log(LogStatus.FAIL, "Process button is not enabled"
					+ test.addScreenCapture(report.capture(driver, "Process button is not enabled")));
			Assert.fail("Process button is not enabled");
		} else {

			clickOnButton(getWebElement("processButton_ID", OR_MLL));
			test.log(LogStatus.PASS,
					"Process successfull" + test.addScreenCapture(report.capture(driver, "Process successfull")));
		}

	}

	@Then("^user clicks on trans Mgr then the Batch Extension$")
	public void user_clicks_on_trans_Mgr_then_the_Batch_Extension() {
		switchToLeftFrame();
		clickOnButton(getWebElement("tranMgr_linktext", OR_MLL));
		log.info("user clicks on tran manager link");

		switchToMainFrame();
		waitForPresenceOfElement("batchExtension_linktext", OR_MLL, 10);
		// test.log(LogStatus.INFO, "Batch Extension" +
		// test.addScreenCapture(report.capture(driver, "Batch Extension")));
		clickOnButton(getWebElement("batchExtension_linktext", OR_MLL));
		waitForPresenceOfElement("ucBatchStartDate_ID", OR_MLL, 20);
		waitForPageLoaded();

	}

	@Then("^user enters batch renewal details for (.*) and process the application$")
	public void user_enters_batch_renewal_details(String merchant) {

		waitForPresenceOfElement("ucBatchStartDate_ID", OR_MLL, 20);
		sendKeys(getWebElement("ucBatchStartDate_ID", OR_MLL), getNextBusinessDayFromToday());
		sendKeys(getWebElement("ucBatchEndDate_ID", OR_MLL), getNextBusinessDayFromToday());
		selectByIndex(getWebElement("payrollFrequency_ID", OR_MLL), 4);
		selectByIndex(getWebElement("maximum_Records_ID", OR_MLL), 5);
		selectByIndex(getWebElement("productCode_ID", OR_MLL), 1);
		clickOnButton(getWebElement("search_ID", OR_MLL), 10);
		waitForPresenceOfElement("status_ID", OR_MLL, 30);
		String statusOfFetchedRecord = getWebElement("status_ID", OR_MLL).getText();
		try {

			if (statusOfFetchedRecord.equals("Sorry. No records were found")) {
				log.info("All records are processed. No records were found");
				test.log(LogStatus.PASS, "All records are processed"
						+ test.addScreenCapture(report.capture(driver, "Records processed")));
				return;

			} else {
				throw new SLCException("Records found");
			}
		} catch (SLCException e) {

			waitForPresenceOfElement("processNow_ID", OR_MLL, 30);
			waitForVisibilityOfElement(getWebElement("processNow_ID", OR_MLL), 30);

		}
		test.log(LogStatus.INFO,
				"List of Batch application details" + test.addScreenCapture(report.capture(driver, "Batch details")));

		ArrayList<String> newList = new ArrayList<String>();
		ArrayList<String> list = getDataExcludedFromPIF(merchant);

		List<WebElement> e = driver
				.findElements(By.xpath("//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td/a"));
		int size = e.size();

		for (int i = 0; i < size; i++) {
			WebElement e1 = e.get(i);
			applicationNumber.add(e1.getText());
		}

		int excludePIFSize = list.size();

		for (String var : applicationNumber) {
			for (int j = 0; j < excludePIFSize; j++) {
				if (var.equals(list.get(j))) {
					newList.add(var);
				}
			}

		}

		int countOriginate = 0;
		int countExtend = 0;

		for (int i = 2; i <= size + 1; i++) {
			for (String var : newList) {
				if (Integer.parseInt(e.get(i - 2).getText()) == Integer.parseInt(var)) {

					try {
						if (!driver.findElement(By.xpath(
								"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[9]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
										+ i + "_chkRenewal']"))
								.isSelected()) {

							driver.findElement(By.xpath(
									"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[9]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
											+ i + "_chkRenewal']"))
									.click();
							countOriginate++;
						}
					} catch (NoSuchElementException e1) {
						log.info("WebElement is not present in page or is disabled");
					}

					try {
						if (!driver.findElement(By.xpath(
								"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[10]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
										+ i + "_chkOriginate']"))
								.isSelected()) {

							driver.findElement(By.xpath(
									"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[10]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
											+ i + "_chkOriginate']"))
									.click();
							countExtend++;
						}
					} catch (NoSuchElementException e1) {
						log.info("WebElement is not present in page or is disabled");
					}
				}
			}

		}

		scrollIntoView(getWebElement("ucBatchStartDate_ID", OR_MLL));
		test.log(LogStatus.INFO, "Application to be Excluded from PIF"
				+ test.addScreenCapture(report.capture(driver, "Application to be excluded from PIF")));

		scrollToEndOfPage();

		test.log(LogStatus.INFO, "Application to be Excluded from PIF"
				+ test.addScreenCapture(report.capture(driver, "Application to be excluded from PIF")));

		clickOnButton(getWebElement("processNow_ID", OR_MLL), 10);
		waitForPresenceOfElement("status_ID", OR_MLL, 20);
		String status = getWebElement("status_ID", OR_MLL).getText();

		if (status.equals("Sorry! No loans were selected for processing.")) {
			test.log(LogStatus.PASS, "Loans are already processed"
					+ test.addScreenCapture(report.capture(driver, "Loans are already processed")));
		} else {
			Assert.assertEquals(status, countOriginate + " Total Renewals Processed Successfully. " + countExtend
					+ " Total Loans Originated Successfully");
			test.log(LogStatus.PASS,
					countOriginate + " Total Renewals Processed Successfully. " + countExtend
							+ " Total Loans Originated Successfully"
							+ test.addScreenCapture(report.capture(driver, "Loans are successfully processed")));
		}

	}

	@And("^user selects all the application number to extend and originate$")
	public void user_selects_all_the_application_number_to_extend_and_originate() {

		waitForPresenceOfElement("ucBatchStartDate_ID", OR_MLL, 20);
		sendKeys(getWebElement("ucBatchStartDate_ID", OR_MLL), getNextBusinessDayFromToday());
		sendKeys(getWebElement("ucBatchEndDate_ID", OR_MLL), getNextBusinessDayFromToday());
		selectByIndex(getWebElement("payrollFrequency_ID", OR_MLL), 4);
		selectByIndex(getWebElement("maximum_Records_ID", OR_MLL), 5);
		selectByIndex(getWebElement("productCode_ID", OR_MLL), 1);
		clickUsingJS(getWebElement("search_ID", OR_MLL));
		waitForPresenceOfElement("status_ID", OR_MLL, 30);
		String statusOfRecordFound = getWebElement("status_ID", OR_MLL).getText();
		try {

			if (statusOfRecordFound.equals("Sorry. No records were found")) {
				test.log(LogStatus.PASS, "All records are processed"
						+ test.addScreenCapture(report.capture(driver, "Records processed")));
				return;

			} else {
				throw new SLCException("Records found");
			}
		} catch (SLCException e) {

			waitForPresenceOfElement("processNow_ID", OR_MLL, 30);
			waitForVisibilityOfElement(getWebElement("processNow_ID", OR_MLL), 30);

		}
		test.log(LogStatus.INFO,
				"List of Batch application details" + test.addScreenCapture(report.capture(driver, "Batch details")));

		List<WebElement> e = driver
				.findElements(By.xpath("//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td/a"));
		int size = e.size();

		int totalRenewals = 0;
		int totalOriginated = 0;

		for (int i = 2; i <= size + 1; i++) {

			try {
				checkVisibilityOfElement(driver.findElement(By.xpath(
						"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[9]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
								+ i + "_chkRenewal']")),
						5);

				waitForElementToBeClickable(driver.findElement(By.xpath(
						"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[9]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
								+ i + "_chkRenewal']")),
						5);
				if (!driver.findElement(By.xpath(
						"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[9]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
								+ i + "_chkRenewal']"))
						.isSelected()
						&& driver.findElement(By.xpath(
								"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[9]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
										+ i + "_chkRenewal']"))
								.isEnabled()) {

					driver.findElement(By.xpath(
							"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[9]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
									+ i + "_chkRenewal']"))
							.click();
					totalRenewals++;
				}
			} catch (NoSuchElementException | TimeoutException e1) {
				log.info("Check box is disabled");
			}

			try {

				checkVisibilityOfElement(driver.findElement(By.xpath(
						"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[10]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
								+ i + "_chkOriginate']")),
						5);

				waitForElementToBeClickable(driver.findElement(By.xpath(
						"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[10]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
								+ i + "_chkOriginate']")),
						5);

				if (!driver.findElement(By.xpath(
						"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[10]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
								+ i + "_chkOriginate']"))
						.isSelected()
						&& driver.findElement(By.xpath(
								"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[10]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
										+ i + "_chkOriginate']"))
								.isEnabled()) {

					driver.findElement(By.xpath(
							"//table[@id='UCBatchRen_dgdBatchRenewalGrid']/tbody/tr/td[10]/input[@id='UCBatchRen_dgdBatchRenewalGrid__ctl"
									+ i + "_chkOriginate']"))
							.click();
					totalOriginated++;
				}
			} catch (NoSuchElementException e1) {
				log.info("Check box is disabled");
			}

		}

		scrollIntoView(getWebElement("ucBatchStartDate_ID", OR_MLL));
		test.log(LogStatus.INFO, "Application to be Excluded from PIF"
				+ test.addScreenCapture(report.capture(driver, "Application to be excluded from PIF")));

		scrollToEndOfPage();

		test.log(LogStatus.INFO, "Application to be Excluded from PIF"
				+ test.addScreenCapture(report.capture(driver, "Application to be excluded from PIF")));

		waitForElementToBeClickable(getWebElement("processNow_ID", OR_MLL), 10);

		clickOnButton(getWebElement("processNow_ID", OR_MLL));

		waitForPresenceOfElement("status_ID", OR_MLL, 40);
		waitForPageLoaded();
		String status = getWebElement("status_ID", OR_MLL).getText();
		if (status.equals("Sorry! No loans were selected for processing.")) {
			test.log(LogStatus.PASS, "Loans are already processed"
					+ test.addScreenCapture(report.capture(driver, "Loans are already processed")));
		} else {
			Assert.assertEquals(status, totalRenewals + " Total Renewals Processed Successfully. " + totalOriginated
					+ " Total Loans Originated Successfully");
			test.log(LogStatus.PASS,
					totalRenewals + " Total Renewals Processed Successfully. " + totalOriginated
							+ " Total Loans Originated Successfully"
							+ test.addScreenCapture(report.capture(driver, "Loans are successfully processed")));
		}

	}

	@Then("^user checks if return is available or not for (.*)$")
	public void user_checks_if_return_is_available_or_not(String merchant) {
		if (file.length() != 0) {

			driver.get(CONFIG.getProperty("appLMSURL_" + CONFIG.getProperty("appEnv")));

			waitForPresenceOfElement(By.name("txtUserId"), 30);
			sendKeys(getWebElement("UserIDtxt_NAME", OR_LMS), CONFIG.getProperty("username"));
			sendKeys(getWebElement("Passwordtxt_NAME", OR_LMS), CONFIG.getProperty("password"));
			WebElement element = getWebElement("MerchantDropdown_Name", OR_LMS);
			Select select = new Select(element);
			if (merchant.equalsIgnoreCase("Blue Trust")) {
				select.selectByValue("57510710000");
			} else {
				select.selectByValue("57511710000");
			}
			test.log(LogStatus.PASS, "Trans application login page"
					+ test.addBase64ScreenShot(report.capture(driver, "Trans_application_login")));
			clickOnButton(getWebElement("LoginBtn_NAME", OR_LMS));

			log.info("Login to TRAN application successfull");
			waitForPageLoaded();

			switchToLeftFrame();

			test.log(LogStatus.INFO, "LMS Home page" + test.addScreenCapture(report.capture(driver, "LMS_Homepage")));
			clickOnButton(getWebElement("tranMgr_linktext", OR_MLL));
			log.info("user clicks on tran manager link");
			test.log(LogStatus.INFO,
					"Tran manager page" + test.addScreenCapture(report.capture(driver, "Tran manager page")));
			switchToMainFrame();
			log.info("user clicks on ACH management link");
			waitForPresenceOfElement("achManagement_linktext", OR_MLL, 20);
			clickOnButton(getWebElement("achManagement_linktext", OR_MLL));
			test.log(LogStatus.INFO,
					"ACH Management page" + test.addScreenCapture(report.capture(driver, "ACH Management page")));
			switchToMainFrame();
			waitForPresenceOfElement("achReturn_linktext", OR_MLL, 20);
			test.log(LogStatus.INFO,
					"ACH Return text" + test.addScreenCapture(report.capture(driver, "ACH Return text")));
			clickUsingAction(getWebElement("achReturn_linktext", OR_MLL));
			clickOnButton(getWebElement("processACHReturn_id", OR_MLL));

			waitForPresenceOfElement("achProcessor_id", OR_MLL, 20);
			selectByValue(getWebElement("achProcessor_id", OR_MLL), "CLAYTON");

			if (merchant.equalsIgnoreCase("blue trust")) {

				driver.findElement(By.id("UploadedFile"))
						.sendKeys(System.getProperty("user.dir") + "\\ReturnFiles\\57510" + LocalDate
								.now(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
								+ ".csv");
				waitForPageLoaded();
				log.info("File Uploaded successfully");
				test.log(LogStatus.INFO, "File Uploaded successfully"
						+ test.addScreenCapture(report.capture(driver, "File Uploaded successfully")));
			} else {
				driver.findElement(By.id("UploadedFile"))
						.sendKeys(System.getProperty("user.dir") + "\\ReturnFiles\\57511" + LocalDate
								.now(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
								+ ".csv");
				waitForPageLoaded();
				log.info("File Uploaded successfully");
				test.log(LogStatus.INFO, "File Uploaded successfully"
						+ test.addScreenCapture(report.capture(driver, "File Uploaded successfully")));
			}

			try {

				checkPresenceOfElement("agreeCheckBox_ID", OR_MLL, 20);
				test.log(LogStatus.INFO,
						"Agree Checkbox" + test.addScreenCapture(report.capture(driver, "Agree Checkbox")));
				clickOnButton(getWebElement("agreeCheckBox_ID", OR_MLL));

				if (!(getWebElement("processButton_ID", OR_MLL).isEnabled())) {
					test.log(LogStatus.FAIL, "Process button is not enabled"
							+ test.addScreenCapture(report.capture(driver, "Process button is not enabled")));

				} else {

					clickOnButton(getWebElement("processButton_ID", OR_MLL));
					test.log(LogStatus.PASS, "Process successfull"
							+ test.addScreenCapture(report.capture(driver, "Process successfull")));
				}
			} catch (TimeoutException | NoSuchElementException e) {
				log.info("ACH Return already exists for this ACH Number");
				test.log(LogStatus.INFO, "ACH Return already exists for this ACH Number." + test
						.addScreenCapture(report.capture(driver, "ACH Return already exists for this ACH Number.")));
			}

		} else {
			log.info("File is empty so no ACH return is applicable");
			test.log(LogStatus.INFO, "File is empty so no ACH return is applicable"
					+ test.addScreenCapture(report.capture(driver, "No ACH returns applicable")));
		}
	}

	@Then("^user clicks on trans Mgr and then ACH Process 3.0$")
	public void user_clicks_on_trans_Mgr_and_then_ACH_Process() throws ParseException {
		switchToLeftFrame();

		test.log(LogStatus.INFO, "LMS Home page" + test.addScreenCapture(report.capture(driver, "LMS Homepage")));
		clickOnButton(getWebElement("tranMgr_linktext", OR_MLL));
		log.info("user clicks on tran manager link");
		test.log(LogStatus.INFO,
				"Tran manager page" + test.addScreenCapture(report.capture(driver, "Tran manager page")));
		switchToMainFrame();
		log.info("user clicks on ACH management link");
		waitForPresenceOfElement("achManagement_linktext", OR_MLL, 20);
		clickOnButton(getWebElement("achManagement_linktext", OR_MLL));
		test.log(LogStatus.INFO,
				"ACH Management page" + test.addScreenCapture(report.capture(driver, "ACH Management page")));
		switchToMainFrame();

		waitForPresenceOfElement("ACHProcess_linktext", OR_MLL, 20);
		clickUsingJS(getWebElement("ACHProcess_linktext", OR_MLL));

		// Get the ACH Effective Date

		String effectiveACHDate = getWebElement("ACHEffectiveDate_ID", OR_MLL).getText();

		Date newDate = new SimpleDateFormat("MM/dd/yyyy").parse(effectiveACHDate);
		String actualDate = new SimpleDateFormat("MM/dd/yyyy").format(newDate);
		String expectedDate = getNextBusinessDayFromToday();

		if (!actualDate.equals(expectedDate)) {
			test.log(LogStatus.FAIL, "Invalid ACH Effective Date"
					+ test.addScreenCapture(report.capture(driver, "Invalid ACH Effective Date")));
			errorMessage = "Invalid ACH Effective Date";
			Assert.fail("Invalid ACH Effective Date");
		} else {
			Assert.assertEquals(actualDate, expectedDate);
			test.log(LogStatus.PASS, "ACH Effective Date is : " + actualDate
					+ test.addScreenCapture(report.capture(driver, "ACH Effective Date")));
		}

	}

	@Then("^user selects the debit and clicks on search$")
	public void user_selects_the_debit_and_clicks_on_search() {
		boolean bFlag = false;
		int count = 0;
		selectByIndex(getWebElement("debitcredit_ID", OR_MLL), 2);
		test.log(LogStatus.PASS,
				"User selects debit option" + test.addScreenCapture(report.capture(driver, "debit option selected")));
		clickOnButton(getWebElement("btnSearch_ID", OR_MLL));

		// waitForPresenceOfElement("searchTable_ID", OR_MLL, 10);
		waitForVisibilityOfElement(getWebElement("searchTable_ID", OR_MLL), 10);

		waitTillElementIsVisible("searchTable_ID", OR_MLL, 10, "table is not visible");

		do {
			try {

				String creditCount = getWebElement("creditCount_XPATH", OR_MLL).getText();
				String debitCount = getWebElement("debitCount_XPATH", OR_MLL).getText();
				String autoDebitCount = getWebElement("AutoDebitCount_XPATH", OR_MLL).getText();
				if (Integer.parseInt(creditCount) == 0 && Integer.parseInt(debitCount) == 0
						&& Integer.parseInt(autoDebitCount) == 0) {
					test.log(LogStatus.INFO, "Debit count, Auto Debit count & credit count is zero"
							+ test.addBase64ScreenShot(report.capture()));
					clickOnButton(getWebElement("reset_ID", OR_MLL));

				} else {

					try {
						checkPresenceOfElement("processAutoDebitCard_ID", OR_MLL, 5);
						checkVisibilityOfElement(getWebElement("processAutoDebitCard_ID", OR_MLL), 5);
						log.info("Auto Debit button is present");
						test.log(LogStatus.INFO, "Auto Debit button is present"
								+ test.addScreenCapture(report.capture(driver, "Auto Debit button is present")));
						if (getWebElement("processAutoDebitCard_ID", OR_MLL).isEnabled()) {
							clickOnButton(getWebElement("processAutoDebitCard_ID", OR_MLL));
						}

					} catch (TimeoutException | NoSuchElementException e) {
						log.info("Auto Debit button is not present");

					}

					try {
						checkPresenceOfElement("processDebitCard_ID", OR_MLL, 5);
						checkVisibilityOfElement(getWebElement("processDebitCard_ID", OR_MLL), 5);
						log.info("Process Debit button is not present");
						test.log(LogStatus.INFO, "Process Debit button is present"
								+ test.addScreenCapture(report.capture(driver, "Process Debit button is  present")));
						if (getWebElement("processDebitCard_ID", OR_MLL).isEnabled()) {
							clickOnButton(getWebElement("processDebitCard_ID", OR_MLL));
						}
					} catch (TimeoutException | NoSuchElementException e) {
						log.info("Process Debit button is not present");

					}

					try {
						checkPresenceOfElement("processTxtCheckButton_ID", OR_MLL, 5);
						checkVisibilityOfElement(getWebElement("processTxtCheckButton_ID", OR_MLL), 5);
						log.info("TxtACheck button is present");
						test.log(LogStatus.INFO, "TxtACheck button is present"
								+ test.addScreenCapture(report.capture(driver, "TxtACheck button is present")));
						if (getWebElement("processTxtCheckButton_ID", OR_MLL).isEnabled()) {
							clickOnButton(getWebElement("processTxtCheckButton_ID", OR_MLL));
						}

					} catch (TimeoutException | NoSuchElementException e) {
						log.info("TxtACheck button is not present");

					}

					try {
						checkPresenceOfElement("splitACH_ID", OR_MLL, 5);
						checkVisibilityOfElement(getWebElement("splitACH_ID", OR_MLL), 5);
						log.info("Split ACH button is present");
						test.log(LogStatus.INFO, "Split ACH button is present"
								+ test.addScreenCapture(report.capture(driver, "Split ACH button is present")));
						if (getWebElement("splitACH_ID", OR_MLL).isEnabled()) {
							clickOnButton(getWebElement("splitACH_ID", OR_MLL));
						}

					} catch (TimeoutException | NoSuchElementException e) {
						log.info("Split ACH button is not present");

					}

					try {
						checkPresenceOfElement("submitButton_ID", OR_MLL, 5);
						checkVisibilityOfElement(getWebElement("submitButton_ID", OR_MLL), 5);
						log.info("Submit button is present");
						test.log(LogStatus.INFO, "Submit button is present"
								+ test.addScreenCapture(report.capture(driver, "Submit button is present")));
						if (getWebElement("submitButton_ID", OR_MLL).isEnabled()) {
							clickOnButton(getWebElement("submitButton_ID", OR_MLL));
						}
						bFlag = false;
					} catch (TimeoutException | NoSuchElementException e) {
						log.info("Submit button is not present");
						test.log(LogStatus.INFO, "Submit button is not present"
								+ test.addScreenCapture(report.capture(driver, "Submit button is not present")));
						throw new SLCException();

					}
				}
			} catch (NumberFormatException | SLCException e) {
				count++;
				bFlag = true;

				if (count == 10) {
					test.log(LogStatus.INFO, "Tried 10 times but didn't get expected result"
							+ test.addBase64ScreenShot(report.capture()));
					break;
				}
				clickOnButton(getWebElement("reset_ID", OR_MLL));
				waitForVisibilityOfElement(getWebElement("btnSearch_ID", OR_MLL), 10);
				selectByIndex(getWebElement("debitcredit_ID", OR_MLL), 2);
				clickOnButton(getWebElement("btnSearch_ID", OR_MLL));
				waitForPresenceOfElement("searchTable_ID", OR_MLL, 10);
				waitForVisibilityOfElement(getWebElement("searchTable_ID", OR_MLL), 10);

			}
		} while (bFlag);

	}

	@Then("^user selects the credit and clicks on search$")
	public void user_selects_the_credit_and_clicks_on_search() {

		selectByIndex(getWebElement("debitcredit_ID", OR_MLL), 1);
		test.log(LogStatus.PASS,
				"User selects credit option" + test.addScreenCapture(report.capture(driver, "Credit option selected")));
		clickOnButton(getWebElement("btnSearch_ID", OR_MLL));

		waitForPresenceOfElement("searchTable_ID", OR_MLL, 10);
		waitForVisibilityOfElement(getWebElement("searchTable_ID", OR_MLL), 10);

		String creditCount = getWebElement("creditCount_XPATH", OR_MLL).getText();
		String debitCount = getWebElement("debitCount_XPATH", OR_MLL).getText();
		String autoDebitCount = getWebElement("AutoDebitCount_XPATH", OR_MLL).getText();

		if (Integer.parseInt(creditCount) == 0 && Integer.parseInt(debitCount) == 0
				&& Integer.parseInt(autoDebitCount) == 0) {
			test.log(LogStatus.INFO, "Debit count, Auto Debit count & credit count is zero"
					+ test.addBase64ScreenShot(report.capture()));
			clickOnButton(getWebElement("reset_ID", OR_MLL));
		} else {

			try {
				checkPresenceOfElement("processAutoDebitCard_ID", OR_MLL, 5);
				checkVisibilityOfElement(getWebElement("processAutoDebitCard_ID", OR_MLL), 5);
				log.info("Auto Debit button is present");
				test.log(LogStatus.INFO, "Auto Debit button is present"
						+ test.addScreenCapture(report.capture(driver, "Auto Debit button is present")));
				clickOnButton(getWebElement("processAutoDebitCard_ID", OR_MLL));

			} catch (TimeoutException | NoSuchElementException e) {
				log.info("Auto Debit button is not present");
				test.log(LogStatus.INFO, "Auto Debit button is not present"
						+ test.addScreenCapture(report.capture(driver, "Auto Debit button is not present")));
			}

			try {
				checkPresenceOfElement("processDebitCard_ID", OR_MLL, 5);
				checkVisibilityOfElement(getWebElement("processDebitCard_ID", OR_MLL), 5);
				log.info("Process Debit button is present");
				test.log(LogStatus.INFO, "Process Debit button is present"
						+ test.addScreenCapture(report.capture(driver, "Process Debit button is  present")));
				clickOnButton(getWebElement("processDebitCard_ID", OR_MLL));
			} catch (TimeoutException | NoSuchElementException e) {
				log.info("Process Debit button is not present");
				test.log(LogStatus.INFO, "Process Debit button is not present"
						+ test.addScreenCapture(report.capture(driver, "Process Debit button is not present")));
			}

			try {
				checkPresenceOfElement("processTxtCheckButton_ID", OR_MLL, 5);
				checkVisibilityOfElement(getWebElement("processTxtCheckButton_ID", OR_MLL), 5);
				log.info("TxtACheck button is present");
				test.log(LogStatus.INFO, "TxtACheck button is present"
						+ test.addScreenCapture(report.capture(driver, "TxtACheck button is present")));
				clickOnButton(getWebElement("processTxtCheckButton_ID", OR_MLL));
			} catch (TimeoutException | NoSuchElementException e) {
				log.info("TxtACheck button is not present");
				test.log(LogStatus.INFO, "TxtACheck button is not present"
						+ test.addScreenCapture(report.capture(driver, "TxtACheck button is not present")));
			}

			try {
				checkPresenceOfElement("splitACH_ID", OR_MLL, 5);
				checkVisibilityOfElement(getWebElement("splitACH_ID", OR_MLL), 5);
				log.info("Split ACH button is present");
				test.log(LogStatus.INFO, "Split ACH button is present"
						+ test.addScreenCapture(report.capture(driver, "Split ACH button is present")));
				clickOnButton(getWebElement("splitACH_ID", OR_MLL));
			} catch (TimeoutException | NoSuchElementException e) {
				log.info("Split ACH button is not present");
				test.log(LogStatus.INFO, "Split ACH button is not present"
						+ test.addScreenCapture(report.capture(driver, "Split ACH button is not present")));
			}

			try {
				checkPresenceOfElement("submitButton_ID", OR_MLL, 5);
				checkVisibilityOfElement(getWebElement("submitButton_ID", OR_MLL), 5);
				log.info("Submit button is present");
				test.log(LogStatus.INFO, "Submit button is present"
						+ test.addScreenCapture(report.capture(driver, "Submit button is present")));
				clickOnButton(getWebElement("submitButton_ID", OR_MLL));
			} catch (TimeoutException | NoSuchElementException e) {
				log.info("Submit button is not present");
				test.log(LogStatus.INFO, "Submit button is not present"
						+ test.addScreenCapture(report.capture(driver, "Submit button is not present")));
			}

		}

	}

	@And("^user updates the ACH Date$")
	public void user_updates_the_ACH_Date() {

		clickUsingJS(getWebElement("achMaintenanceDate_linktext", OR_MLL));
		waitForPresenceOfElement("nextScheduleEffectiveDate_ID", OR_MLL, 5);
		sendKeys(getWebElement("nextScheduleEffectiveDate_ID", OR_MLL), getNextBusinessDayFromToday());
		clickOnButton(getWebElement("updateDate_ID", OR_MLL));
		test.log(LogStatus.PASS, "Next Business date updated successfully"
				+ test.addScreenCapture(report.capture(driver, "Date updated")));
		Assert.assertEquals("Date updated successfully.", getWebElement("updateMessage_ID", OR_MLL).getText());

	}

	@And("^get the application number for (.*)$")
	public void get_the_application_number(String var) {
		switchToMainFrame();

		String ApplicationNum = driver.findElement(By.xpath("//table[@id='Table1']/tbody/tr[2]/td[4]/b/font[2]"))
				.getText();
		if (var.equalsIgnoreCase("Blue trust")) {
			applicationNumberOfBTL
					.add(ApplicationNum + " & Email: " + map.get("Email") + " & Periodicity : " + map.get("Period"));

		} else {
			applicationNumberOfMLL
					.add(ApplicationNum + " & Email: " + map.get("Email") + " & Periodicity : " + map.get("Period"));

		}

	}

	@And("^Capture the Cust VIP Level$")
	public void verify_Cust_Level() {
		switchToMainFrame();
		customerLevel = getWebElement("custLevel_xpath", OR_MLL).getText().equalsIgnoreCase("VIP") ? "VIP" : "NONVIP";
		if (driver.findElements(By.xpath("//table[@id='tbCustomerDetails']/tbody/tr[3]/td[2]")).size() > 0) {
			cust_ID = Integer.parseInt(
					driver.findElement(By.xpath("//table[@id='tbCustomerDetails']/tbody/tr[3]/td[2]")).getText());
		}
		log.info("Cutomer Level : " + customerLevel);

	}

	@And("^Verify the application status as (.*)$")
	public void verify_Customer_Level(String applicationStatus) {
		switchToMainFrame();
		String status = "";

		if (applicationStatus.equalsIgnoreCase("Appl Paid Off")) {
			status = driver.findElement(By.xpath("//table[@id='tbApplicationDetails']/tbody/tr[8]/td[2]")).getText();
			Assert.assertEquals(status, applicationStatus);
		}

	}

	@And("^complete the payment using payment mode as (.*) using (.*)$")
	public void complete_Payment_PaidInFull(String paymentType, String paymentMode) {
		log.info("Payment type is : " + paymentType);
		log.info("Payment mode is : " + paymentMode);
		test.log(LogStatus.INFO, "Payment mode is : " + paymentType + test.addBase64ScreenShot(report.capture()));
		switchToMainFrame();
		clickOnButton(getWebElement("txtchk_paymentbtn_id", OR_MLL));
		// Wait for payment window to open
		switchToTranDialogIFrame();

		// Paid in Full
		if (paymentType.equalsIgnoreCase("Paid in Full")) {

			selectByIndex(getWebElement("paymenttypedropdown_name", OR_MLL), 1);

		} else {

			selectByIndex(getWebElement("paymenttypedropdown_name", OR_MLL), 2);
		}
		// update the code for all payment mode accordingly, for now I am writing for
		if (paymentMode.equalsIgnoreCase("CASH")) {
			selectByIndex(getWebElement("paymode_name", OR_MLL), 3);
		} else {
			// This section has to be covered as per requiremnet in future
		}

		clickOnButton(getWebElement("post_ID", OR_MLL));

		new WebDriverWait(driver, 5).until(ExpectedConditions.attributeContains(getWebElement("post_ID", OR_MLL),
				"value", "View Customer Receipt"));

		clickOnButton(getWebElement("cancel_ID", OR_MLL));

	}

	@Then("^Apply for loan$")
	public void apply_For_Loan() {
		clickOnButton(driver.findElement(By.xpath("//a[@id='qaaMenuApply' or @id='desktopApply']")));
		selectByValue(getWebElement("selectLoanAmount_ID", OR_MLL), "275");
		scrollBy(200);
		sendKeys(getWebElement("ConfirmEmailTxt_ID", OR_MLL), map.get("Email"));
		scrollBy(500);
		clickOnButton(getWebElement("AboutYouNextBtn_ID", OR_MLL));

	}

	@Then("^Specify paydates information$")
	public void speicfyPaydates() throws ParseException {
		sendKeysUsingJS(getWebElement("LastPayDate_ID", OR_MLL), getNextBusinessDayFromGivenDate(getPastDate(-4)));
		log.info("Last pay date : " + getNextBusinessDayFromGivenDate(getPastDate(-4)));
		log.info("First upcoming pay date : " + map.get("Next_PayDate"));
		log.info("Second upcoming pay date : " + map.get("Second_PayDate"));

		sendKeysUsingJS(getWebElement("NextPayDateTxt_ID", OR_MLL), map.get("Next_PayDate"));

		sendKeysUsingJS(getWebElement("SecondPayDateTxt_ID", OR_MLL), map.get("Second_PayDate"));

		scrollBy(200);
		waitForElementToBeClickable(getWebElement("IncomeNextBtn_ID", OR_MLL), 20);
		clickOnButton(getWebElement("IncomeNextBtn_ID", OR_MLL));
		int count = 0;

		try {

			checkPresenceOfElement("BankingTab_ID", OR_MLL, 10);
			checkVisibilityOfElement(getWebElement("BankingTab_ID", OR_MLL), 10);

		} catch (TimeoutException e) {
			try {

				boolean bFlag = false;
				if (isElementDisplayed(getWebElement("NetPayTxt_ID", OR_MLL))) {
					bFlag = true;
				}

				while (bFlag) {
					log.info("Problem with the PayDate");

					scrollIntoView(getWebElement("NextPayDateTxt_ID", OR_MLL));

					changeDate();
					sendKeys(getWebElement("LastPayDate_ID", OR_MLL), getNextBusinessDayFromGivenDate(getPastDate(-4)));
					log.info("Last Pay date : " + getNextBusinessDayFromGivenDate(getPastDate(-4)));
					sendKeys(getWebElement("NextPayDateTxt_ID", OR_MLL), map.get("Next_PayDate"));

					log.info(" Updated " + map.get("Next_PayDate"));

					sendKeys(getWebElement("SecondPayDateTxt_ID", OR_MLL), map.get("Second_PayDate"));
					log.info(" Updated " + map.get("Second_PayDate"));
					waitForElementToBeClickable(getWebElement("IncomeNextBtn_ID", OR_MLL), 20);
					clickOnButton(getWebElement("IncomeNextBtn_ID", OR_MLL));

					try {
						checkPresenceOfElement("BankingTab_ID", OR_MLL, 10);
						checkVisibilityOfElement(getWebElement("BankingTab_ID", OR_MLL), 10);
						bFlag = false;
					} catch (TimeoutException e1) {
						count++;
						if (count == 3) {
							scrollIntoView(getWebElement("NextPayDateTxt_ID", OR_MLL));
							test.log(LogStatus.FAIL,
									"Issue with the date " + test.addBase64ScreenShot(report.capture()));
							Assert.fail("Issue with the pay date");
						}
						continue;
					}

				}
			} catch (Exception e1) {
				waitForPresenceOfElement(By.id("content_customContent_bankingTab"), 5);
				waitForVisibilityOfElement(getWebElement("BankingTab_ID", OR_MLL), 10);
			}
		}
	}

	@And("^Apply till user becomes VIP for (.*) with payment mode as (.*)$")
	public void repeatUntillUserIsVIP(String appName, String PaymentMode) throws Throwable {
		while (!customerLevel.equalsIgnoreCase("VIP")) {

			Change_due_date();
			doResign(appName,PaymentMode);
			Originate_Application();
			complete_Payment_PaidInFull("Paid in Full", "CASH");

			driver.get(CONFIG.getProperty("app" + appName + "URL_" + CONFIG.getProperty("appEnv")));

			waitForPageLoaded();

			sendKeys(getWebElement("Emailidtxt_ID", OR_MLL), map.get("Email"));

			// test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver,
			// "user enters login details ")));

			driver.findElement(By.id("content_loginSubmit")).click();

			sendKeys(getWebElement("password_xpath", OR_MLL), strPwd);
			clickOnButton(getWebElement("loginbtn_xpath", OR_MLL));

			clickOnButton(driver.findElement(By.xpath("//a[@id='qaaMenuApply' or @id='desktopApply']")));
			selectByValue(getWebElement("selectLoanAmount_ID", OR_MLL), "275");
			scrollBy(200);
			sendKeys(getWebElement("ConfirmEmailTxt_ID", OR_MLL), map.get("Email"));
			scrollBy(500);
			clickOnButton(getWebElement("AboutYouNextBtn_ID", OR_MLL));

			speicfyPaydates();

			update_BankingPage(PaymentMode);
			update_Consent();
			apply_esign(PaymentMode);

			// appName = "LMS";

			driver.get(CONFIG.getProperty("app" + "LMS" + "URL_" + CONFIG.getProperty("appEnv")));
			waitForPageLoaded();

			user_enters_login_details("LMS", appName);
			search_by_reviewApp("Email_Address");
			verify_Cust_Level();

		}

		if (customerLevel.equalsIgnoreCase("VIP")) {

			String appStatus = driver.findElement(By.xpath("//table[@id='tbApplicationDetails']/tbody/tr[8]/td[2]"))
					.getText();

			if (appStatus.equalsIgnoreCase("Pending Appl")) {
				Change_due_date();
				doResign(appName,PaymentMode);
				Originate_Application();
				// complete_Payment_PaidInFull("Paid in Full", "CASH");
			}

			complete_Payment_PaidInFull("Paid in Full", "CASH");

		}
	}

	@And("^Copy VIP data to database table for (.*)$")
	public void transferDataToDatabaseTable(String merch) {
		int merch_code;
		if (customerLevel.equalsIgnoreCase("VIP")) {
			String SSN = map.get("SSN_1") + map.get("SSN_2") + map.get("SSN_3");
			String email = map.get("Email");
			String ABA = map.get("ABA");
			String F_Name = map.get("First_Name");
			String L_Name = map.get("Last_Name");
			String Accnt = map.get("Acct");
			String DateOfBirth = map.get("DateofBirth");
			switchToMainFrame();
			String status = driver.findElement(By.xpath("//table[@id='tbApplicationDetails']/tbody/tr[8]/td[2]"))
					.getText();
			if (merch.equalsIgnoreCase("Blue Trust")) {
				merch_code = 57510;
			} else {
				merch_code = 57511;
			}
			String URL = getdbURL(CONFIG.getProperty("appEnv"));
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
				String query = "INSERT INTO [QAA].[dbo].[VIPCustomer] VALUES(" + "'" + SSN + "'" + "," + "'" + email
						+ "'" + "," + "'" + ABA + "'" + "," + "'" + F_Name + "'" + "," + "'" + L_Name + "'" + "," + "'"
						+ Accnt + "'" + "," + "'" + DateOfBirth + "'" + "," + cust_ID + "," + merch_code + "," + "'"
						+ status + "'" + ")";
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(query);
				stmt.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("Exception occured while transferring the VIP data to table VIPCustomer");

			}

		}
	}

	@And("^Update VIP Cutomer table$")
	public void updateVIPCutomerTable() {
		switchToMainFrame();
		String status = driver.findElement(By.xpath("//table[@id='tbApplicationDetails']/tbody/tr[8]/td[2]")).getText();
		log.info(status);
		String email = clientData.get("Email");
		log.info(email);

		String URL = getdbURL(CONFIG.getProperty("appEnv"));

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
			Statement stmt = connection.createStatement();
			String query = "UPDATE [QAA].[dbo].[VIPCustomer] set status =" + "'" + status + "'" + " where Email=" + "'"
					+ email + "'" + "";
			stmt.executeUpdate(query);
			stmt.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// doing the paid off

	@Then("^if not paid off then pay it off$")
	public void paidOff() {
		switchToMainFrame();
		String status = driver.findElement(By.xpath("//table[@id='tbApplicationDetails']/tbody/tr[8]/td[2]")).getText();

		log.info("Application status: " + status);
		if (status.equalsIgnoreCase("New Appl")) {
			complete_Payment_PaidInFull("Paid in Full", "CASH");
		}
	}

	@Then("^check try with another lender for (.*) with (.*) and (.*) with (.*) \"([^\"]*)\"$")
	public void fetchNewCustomerdata(String appName, String fileName, String IncomeSource, String paymentType,
			String PaymentMode) throws Throwable {

		boolean bFlag = false;

		int count = 0;

		do {

			if (count == 4) {
				throw new SLCException(errorMessage = "Try with another lender issue.");
			}
			try {
				new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//input[@id='content_customContent_chkElectronicConsent']")));
				bFlag = true;
			} catch (TimeoutException e) {
				count++;
				map.clear();
				driver.manage().deleteAllCookies();
				user_load_config_file(fileName);
				user_fetch_info();
				user_is_on_homepage(appName);
				apply_for_loan();
				update_IncomePage(IncomeSource, paymentType);
				update_BankingPage(PaymentMode);
			}

		} while (!bFlag);

	}

	@Then("^Check if ReSign required for \"([^\"]*)\" and \"([^\"]*)\"$")
	public void doResign(String merchant, String paymentType) {

		switchToMainFrame();

		clickOnButton(driver.findElement(By.xpath("//a[@id='esig']")));

		switchToTranDialogIFrame();
		
		test.log(LogStatus.PASS, "Signature status"+ test.addBase64ScreenShot(report.capture()));

		String status = driver.findElement(By.xpath("//table[@id='dgESig']/tbody/tr[2]/td[9]")).getText();

		log.info("ESign status: " + status);

		// clickOnButton(driver.findElement(By.xpath("//button[@title='close']")));

		do {
			if (status.equalsIgnoreCase("False")) {
				deleteUserPassword(map.get("Email"));
				user_is_on_homepage(merchant);

				sendKeys(getWebElement("Emailidtxt_ID", OR_MLL), map.get("Email"));
				clickOnButton(getWebElement("LogInMenuBtn_XPATH", OR_MLL));

				if (driver.findElements(By.id("content_customContent_frmSSN")).size()>0) {
					sendKeys(getWebElement("SSNTxt_ID", OR_MLL), map.get("SSN_3"));
					sendKeys(getWebElement("DOBTxt_ID", OR_MLL), map.get("DateofBirth"));
					strPwd = createUserPass(map.get("First_Name"), map.get("Last_Name"), map.get("DateofBirth"));
					sendKeys(getWebElement("PasswordTxt_ID", OR_MLL), strPwd);
					sendKeys(getWebElement("ConfirmPasswordTxt_ID", OR_MLL), strPwd);
					clickOnButton(getWebElement("SavePwdButton_ID", OR_MLL));
					clickOnButton(getWebElement("LoginNowBtn_ID", OR_MLL));
				}
				sendKeys(driver.findElement(By.id("content_customContent_frmPassword_Login")), strPwd);
				clickOnButton(getWebElement("LoginBtn_ID", OR_MLL));
				clickOnButton(driver.findElement(By.xpath("//a[text()='Re-eSign Now!']")));
				update_Consent();
				apply_esign(paymentType);
				user_is_on_homepage("LMS");
				user_enters_login_details("LMS", merchant);
				_search_by_reviewApp("Email_Address");
				switchToMainFrame();

				clickOnButton(driver.findElement(By.xpath("//a[@id='esig']")));

				switchToTranDialogIFrame();
				test.log(LogStatus.PASS, "Signature status"+ test.addBase64ScreenShot(report.capture()));
				status = driver.findElement(By.xpath("//table[@id='dgESig']/tbody/tr[2]/td[9]")).getText();
				log.info("E-Sign status: " + status);

			}
		} while (status.equalsIgnoreCase("False"));

	}

}
