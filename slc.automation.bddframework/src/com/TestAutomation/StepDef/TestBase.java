package com.TestAutomation.StepDef;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.mail.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.TestAutomation.CustomException.SLCException;
import com.TestAutomation.Reporting.ExtentManager;
import com.TestAutomation.Utility.PropertiesFileReader;
import com.TestAutomation.StepDef.BrowserDriverManager;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	public static WebDriver driver = null;
	public static Properties CONFIG, OR_BTL, OR_MLL, OR_LMS;
	public PropertiesFileReader obj = new PropertiesFileReader();
	public static Statement stmt = null;
	public static HashMap<String, String> map = new HashMap<String, String>();
	public static HashMap<String, String> clientData = new HashMap<String, String>();
	public static ArrayList<String> listFromDatabase = new ArrayList<String>();
	public String LinkTXT = "";

	public static Logger log = Logger.getLogger("Code executed at : ");
	// For Jenkins
	public static String browser = "";
	public static String appEnv = "";
	public static String payFrequency = "";
	public static ExtentTest test;
	public static ExtentManager report = new ExtentManager();
	public static ArrayList<String> applicationNumberOfBTL = new ArrayList<String>();
	public static ArrayList<String> applicationNumberOfMLL = new ArrayList<String>();
	protected static String browerName = "";
	protected String errorMessage = "";
	public static String strPwd;
	

	public void initialize() throws MalformedURLException {
		driver = null;
		if (driver == null) {
			// load property file
			try {
				CONFIG = obj.getProperties(System.getProperty("user.dir") + "//resources//browser-config.properties");
				OR_BTL = obj.getProperties(System.getProperty("user.dir") + "//resources//OR_BTL.properties");
				OR_MLL = obj.getProperties(System.getProperty("user.dir") + "//resources//OR_MLL.properties");
				OR_LMS = obj.getProperties(System.getProperty("user.dir") + "//resources//OR_LMS.properties");
			} catch (IOException e) {
				Assert.fail("Issue in initialization of properties file");
				errorMessage = "Issue in initialization of properties file";
			}

			clientData.clear();
			map.clear();

			if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
				browser = System.getenv("browser");
			} else {
				browser = CONFIG.getProperty("browser");
			}
			CONFIG.setProperty("browser", browser);

			if (System.getenv("Environment") != null && !System.getenv("Environment").isEmpty()) {
				appEnv = System.getenv("Environment");
			} else {
				appEnv = CONFIG.getProperty("appEnv");
			}
			CONFIG.setProperty("appEnv", appEnv);

			if (System.getenv("payFrequency") != null && !System.getenv("payFrequency").isEmpty()) {
				payFrequency = System.getenv("payFrequency");
			} else {
				payFrequency = CONFIG.getProperty("payFrequency");
			}

			CONFIG.setProperty("payFrequency", payFrequency);
			if (CONFIG.getProperty("browser").equals("firefox")) {
				// System.setProperty("webdriver.gecko.driver",
				// "resources//drivers//geckodriver.exe");
				// System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla
				// Firefox\\firefox.exe");
				driver = new FirefoxDriver();
			} else if (CONFIG.getProperty("browser").equals("ie")) {
				System.setProperty("webdriver.ie.driver", "resources//drivers//IEDriverServer.exe");

				// WebDriverManager.getDriverInstance().setDriver(new InternetExplorerDriver());
				driver = com.TestAutomation.StepDef.BrowserDriverManager.getDriverInstance().getDriver();

			} else if (CONFIG.getProperty("browser").equalsIgnoreCase("chrome")) {

				/*
				 * System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
				 * + "\\driver\\Chrome_v90\\chromedriver.exe");
				 */

				WebDriverManager.chromedriver().setup();
			
				ChromeOptions options = new ChromeOptions();
				if (CONFIG.getProperty("headless").equalsIgnoreCase("yes")) {
					options.addArguments("headless");
				}
				options.addArguments("enable-automation");
				options.addArguments("--disable-infobars");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--disable-browser-side-navigation");
				options.addArguments("--disable-gpu");
				options.addArguments("--dns-prefetch-disable");
				options.addArguments("--disable-extensions");
				options.addArguments("enable-automation");
				options.setAcceptInsecureCerts(true);

				BrowserDriverManager.getDriverInstance().setDriver(new ChromeDriver(options));
				driver = BrowserDriverManager.getDriverInstance().getDriver();
				log.info("Launching Chrome browser");
			}
			driver.manage().deleteAllCookies();
			driver.manage().window().setSize(new Dimension(1400, 800));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		} else {
			Assert.fail("Unable to initialize chrome browser");
		}
	}

	public void waitForPageLoaded() {

		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 80);
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
							.equalsIgnoreCase("complete");
				}
			});
		} catch (Throwable error) {
			System.out.println(error.getMessage());
			errorMessage = "Timeout waiting for Page Load Request to complete.";
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}

	}

	public static void waitForPageToLoad(By locator, int time) {

		try {
			boolean bFlag = false;
			do {
				String status = ((JavascriptExecutor) driver).executeScript("return document.readyState").toString();
				if (status.equalsIgnoreCase("complete")) {
					bFlag = true;
					break;
				}
			} while (!bFlag);

			try {
			} catch (TimeoutException e) {
				Assert.fail("Web page is loaded but the element is not present in DOM or it is not visible");
			}
		} catch (Exception e) {
			Assert.fail("Could not load the web page. Cause of failure : " + e.getMessage());
		}
	}

	public static WebElement getWebElement(String locator, Properties prop) {

		String[] tokens = locator.split("_");
		String locatorType = tokens[tokens.length - 1];
		String strlocator = prop.getProperty(locator).trim();
		WebElement webElement = null;

		if (getWebElementSize(locator, prop) == 1) {
			if (locatorType.equalsIgnoreCase("XPATH")) {

				webElement = driver.findElement(By.xpath(strlocator));

			} else if (locatorType.equalsIgnoreCase("ID")) {

				webElement = driver.findElement(By.id(strlocator));

			} else if (locatorType.equalsIgnoreCase("NAME")) {

				webElement = driver.findElement(By.name(strlocator));

			} else if (locatorType.equalsIgnoreCase("CSS")) {

				webElement = driver.findElement(By.cssSelector(strlocator));

			} else if (locatorType.equalsIgnoreCase("LINKTEXT")) {

				webElement = driver.findElement(By.linkText(strlocator));
			}

			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(webElement));
		} else if (getWebElementSize(locator, prop) > 1) {

			throw new SLCException("Multuiple elements present with the locator");
		} else {

			throw new SLCException("No such element. Check the path of WebElement");
		}

		/*
		 * try {
		 * 
		 * if (locatorType.equalsIgnoreCase("XPATH")) {
		 * 
		 * webElement = driver.findElement(By.xpath(strlocator));
		 * 
		 * } else if (locatorType.equalsIgnoreCase("ID")) {
		 * 
		 * webElement = driver.findElement(By.id(strlocator));
		 * 
		 * } else if (locatorType.equalsIgnoreCase("NAME")) {
		 * 
		 * webElement = driver.findElement(By.name(strlocator));
		 * 
		 * } else if (locatorType.equalsIgnoreCase("CSS")) {
		 * 
		 * webElement = driver.findElement(By.cssSelector(strlocator));
		 * 
		 * } else if (locatorType.equalsIgnoreCase("LINKTEXT")) {
		 * 
		 * webElement = driver.findElement(By.linkText(strlocator)); }
		 * 
		 * } catch (NoSuchElementException e) { Assert.fail(strlocator +
		 * " Element not found : NoSuchElementException"); } catch(Throwable e1) {
		 * log.info(e1.getMessage()); }
		 */
		// new WebDriverWait(driver,
		// 20).until(ExpectedConditions.visibilityOf(webElement));

		return webElement;

	}

	public static int getWebElementSize(String locator, Properties prop) {

		String[] tokens = locator.split("_");
		String locatorType = tokens[tokens.length - 1];
		String strlocator = prop.getProperty(locator).trim();
		List<WebElement> webElement = null;

		if (locatorType.equalsIgnoreCase("XPATH")) {

			webElement = driver.findElements(By.xpath(strlocator));

		} else if (locatorType.equalsIgnoreCase("ID")) {

			webElement = driver.findElements(By.id(strlocator));

		} else if (locatorType.equalsIgnoreCase("NAME")) {

			webElement = driver.findElements(By.name(strlocator));

		} else if (locatorType.equalsIgnoreCase("CSS")) {

			webElement = driver.findElements(By.cssSelector(strlocator));

		} else if (locatorType.equalsIgnoreCase("LINKTEXT")) {

			webElement = driver.findElements(By.linkText(strlocator));
		}

		return webElement.size();

	}

	public static void sendMail() throws MessagingException, EmailException {

		String to = CONFIG.getProperty("ToEmailGroup");
		String from = CONFIG.getProperty("FromEmailGroup");
		String host = CONFIG.getProperty("MailHost");
		String subject = CONFIG.getProperty("Subject");

		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();
		EmailAttachment attachment1 = new EmailAttachment();
		// attachment.setPath(System.getProperty("user.dir") +
		// "//target//ExtentReportResults.html");
		attachment.setPath(System.getProperty("user.dir") + "//CustomerData//Blue_Trust.txt");
		attachment1.setPath(System.getProperty("user.dir") + "//CustomerData//Max_Lend.txt");
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment1.setDisposition(EmailAttachment.ATTACHMENT);
		// attachment.setDescription("SLC Automation Report");
		// attachment.setName("SLC");

		// Create the email message
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(host);
		String[] recipientList = to.split(",");
		email.addTo(recipientList);
		email.setFrom(from);
		email.setSubject(subject);
		email.setMsg("----------This is an automated email------------------");

		// add the attachment
		email.attach(attachment);
		email.attach(attachment1);
		// send the email
		email.send();

		System.out.println("-------Email Sent Successfully--------");

//	   Properties props = new Properties();
//       props.put("mail.smtp.host", "qtssmtp.slc.local");
//       props.put("mail.smtp.auth", "true");
//      
//       Session session = Session.getDefaultInstance(props,    		   
//				new javax.mail.Authenticator() {
//					protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication("Gautam.Salian@slchq.com", "Welcome1.87");
//					}
//				});
//	  // 	Properties props = System.getProperties();
//	  // 	props.setProperty("mail.smtp.host",host);
//	  // Session session = Session.getDefaultInstance(props);
//		MimeMessage message = new MimeMessage(session);
//		   
//		   String [] recipientList = to.split(",");
//		   InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
//		   int counter=0;
//		   for(String recipient:recipientList) {
//			   recipientAddress[counter]=new InternetAddress(recipient.trim());
//			   counter++;
//		   }
//		   
//		   message.addRecipients(Message.RecipientType.TO, recipientAddress);
//		   message.setSubject("SLC Test Automation Result");
//		   message.setSentDate(new Date());
//		   String htmlData = "<h1>*****This is an Automated Mail*******</h1>";
//		   message.setContent(htmlData, "text/html");
//		   
//		   MimeBodyPart messageBodyPart = new MimeBodyPart();
//		   messageBodyPart.setContent(htmlData,"text/html");
//		   
//		   Multipart multipart = new MimeMultipart();
//		   multipart.addBodyPart(messageBodyPart);
//		   
//		   MimeBodyPart attachPart = new MimeBodyPart();
//		   try {
//			   attachPart.attachFile(System.getProperty("user.dir")+"//target//ExtentReportResults.html");		   
//		   }catch(IOException ex) {
//			   ex.printStackTrace();
//		   }
//		   multipart.addBodyPart(attachPart);
//		   
//		   message.setContent(multipart);
//		   Transport.send(message);
//		   System.out.println("message sent successfully.....");
		// }catch(MessagingException mex) {
		// mex.printStackTrace();
		// }
	}

	public boolean receiveMail(String UserName, String Password, String FolderName) {
		boolean flag = false;
		try {
			String FName[] = FolderName.split("_");
			Properties properties = new Properties();
			properties.setProperty("mail.store.protocol", "imaps");
			// Session emailSession=Session.getDefaultInstance(properties,null);
			Session emailSession = Session.getInstance(properties);
			Store emailStore = emailSession.getStore("imaps");
			emailStore.connect("imap.privateemail.com", UserName, Password);
			Folder emailFolder = emailStore.getFolder(FName[0]).getFolder(FName[1]);
			emailFolder.open(Folder.READ_WRITE);
			Message messages[] = emailFolder.getMessages();

			// Below new code added email come in spam folder
			// Thread.sleep(2000);
			// Folder emailFolder1 = emailStore.getFolder("Spam");

			/////////// ********* Folder emailFolder1 = emailStore.getFolder(FolderName);
			// emailFolder1.open(Folder.READ_ONLY);
			// Thread.sleep(2000);
			//// Message messages1[]=emailFolder1.getMessages();
			// emailFolder.copyMessages(messages1, emailFolder);

			// Above new code added
			String textBody = "";
			// below line added 8 july

			// for (int i = messages.length - 5; i < messages.length; i++) {

			for (int i = messages.length; i > messages.length - 5; i--) {
				Address[] emailList = messages[i - 1].getAllRecipients();

				if (emailList[0].toString().toLowerCase().contains(map.get("Email").toLowerCase())) {
					log.info(map.get("Email").toLowerCase() + " is present in Private Email ");
					textBody = messages[i - 1].getContent().toString();
					// LinkTXT = textBody.substring(textBody.indexOf("='") + 2,
					// textBody.indexOf("'>"));

					if (textBody.contains("Your check payment has been submitted for processing.")) {
						log.info("Your check payment has been submitted for processing." + map.get("Email"));
						flag = true;
						break;
					}

					LinkTXT = "";
					LinkTXT = textBody.substring(textBody.indexOf("href=\"") + 6, textBody.indexOf("\">Please"));

					log.info("link=" + LinkTXT);
					flag = true;
					break;
				}

			}
			emailFolder.close(false);
			emailStore.close();
			properties.clear();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			log.info("Please check the Inbox in Private Email");
			test.log(LogStatus.FAIL, "Please check the Inbox in Private Email");
			Assert.fail("Please check the Inbox in Private Email");
		}
		return flag;
	}

	public boolean receiveMailForgotPass(String UserName, String Password, String FolderName) {
		boolean flag = false;
		try {
			String FName[] = FolderName.split("_");
			Properties properties = new Properties();
			properties.setProperty("mail.store.protocol", "imaps");
			Session emailSession = Session.getInstance(properties);
			Store emailStore = emailSession.getStore("imaps");
			emailStore.connect("imap.privateemail.com", UserName, Password);
			Folder emailFolder = emailStore.getFolder(FName[0]).getFolder(FName[1]);
			emailFolder.open(Folder.READ_WRITE);
			Message messages[] = emailFolder.getMessages();
			for (int i = messages.length; i > messages.length - 5; i--) {
				Address[] emailList = messages[i - 1].getAllRecipients();
				log.info(messages[i - 1].getSubject());
				log.info(map.get("Email"));
				log.info(messages[i - 1].getContentType());
				log.info(messages[i - 1].getFlags());

				if (emailList[0].toString().toLowerCase().contains(map.get("Email").toLowerCase())
						&& messages[i - 1].getSubject().equals("Reset your password")) {
					log.info(map.get("Email").toLowerCase() + " is present in Private Email ");
					ArrayList<String> li = new ArrayList<String>();
					li.add(messages[i - 1].getContent().toString());
					String blueTrust = "http://bluetrustloans.qa.slc.local/resetpassword.aspx?";
					String maxLend = "http://maxlend.qa.slc.local/resetpassword.aspx?";
					log.info(li.get(0).indexOf("token="));
					log.info("");
					String token = li.get(0).substring(li.get(0).indexOf("token="), li.get(0).indexOf("token=") + 50)
							.split("\"")[0];
					LinkTXT = "";
					if (li.get(0).contains("maxlend")) {
						LinkTXT = maxLend + token;
					} else {
						LinkTXT = blueTrust + token;
					}
					log.info("link=" + LinkTXT);
					flag = true;
					break;
				}

			}
			emailFolder.close(false);
			emailStore.close();
			properties.clear();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			log.info("Please check the Inbox in Private Email");
			test.log(LogStatus.FAIL, "Please check the Inbox in Private Email");
			Assert.fail("Please check the Inbox in Private Email");
		}
		return flag;
	}

	// for spam only
	public boolean receiveMailforspam(String UserName, String Password, String FolderName) throws InterruptedException {
		boolean flag = false;
		try {
			Properties properties = new Properties();
			properties.setProperty("mail.store.protocol", "imaps");
			// Session emailSession=Session.getDefaultInstance(properties,null);
			Session emailSession = Session.getInstance(properties);
			Store emailStore = emailSession.getStore("imaps");
			emailStore.connect("imap.privateemail.com", UserName, Password);

			Thread.sleep(2000);
			Folder emailFolder1 = emailStore.getFolder("Spam");

			/////////// ********* Folder emailFolder1 = emailStore.getFolder(FolderName);
			emailFolder1.open(Folder.READ_ONLY);
			Thread.sleep(2000);
			Message messages1[] = emailFolder1.getMessages();
			// emailFolder.copyMessages(messages1, emailFolder);

			// Above new code added
			String textBody = "";

			for (int i = messages1.length - 5; i < messages1.length; i++) {
				Address[] emailList = messages1[i].getAllRecipients();
				if (map.get("Email").toLowerCase().equals(emailList[0].toString().toLowerCase())) {
					textBody = messages1[i].getContent().toString();
					// Thread.sleep(2000);
					LinkTXT = "";
					LinkTXT = textBody.substring(textBody.indexOf("='") + 2, textBody.indexOf("'>"));
					flag = true;
					break;
				}

			}
			emailFolder1.close(false);
			emailStore.close();
			properties.clear();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean receiveLatestMail(String UserName, String Password, String FolderName) throws InterruptedException {
		boolean flag = false;
		try {

			String FName[] = FolderName.split("_");
			Properties properties = new Properties();
			properties.setProperty("mail.store.protocol", "imaps");
			// Session emailSession=Session.getDefaultInstance(properties,null);
			Session emailSession = Session.getInstance(properties);
			Store emailStore = emailSession.getStore("imaps");
			emailStore.connect("imap.privateemail.com", UserName, Password);
			Folder emailFolder = emailStore.getFolder(FName[0]).getFolder(FName[1]);
			emailFolder.open(Folder.READ_ONLY);
			Message messages[] = emailFolder.getMessages();
			String textBody = "";
			Thread.sleep(20000);
			for (int i = messages.length - 5; i < messages.length; i++) {
				Address[] emailList = messages[i].getAllRecipients();
				// if
				// (map.get("Email").toLowerCase().equals(emailList[0].toString().toLowerCase()))
				// {
				if (emailList[0].toString().toLowerCase().contains(map.get("Email").toLowerCase())) {
					textBody = messages[i].getContent().toString();
					if (textBody.contains("Please click here to complete your Payment")) {
						String LinkTXT2 = textBody.substring(textBody.indexOf("href=\"") + 6,
								textBody.indexOf("\">Please"));
						if (!LinkTXT.equals(LinkTXT2)) {
							LinkTXT = "";
							LinkTXT = LinkTXT2;
							log.info(LinkTXT);
							flag = true;
							break;
						}
					}

				}

			}
			emailFolder.close(false);
			emailStore.close();
			properties.clear();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	public boolean validate_LMSInterface(HashMap<String, String> clientData, String dbName, String user, String pass) {

		String dbURL = null;
		boolean bFlag = false;
		if (CONFIG.getProperty("appEnv").equals("QA")) {
			dbURL = "jdbc:sqlserver://2012QASQLPROD01.slc.local:1433;databasename=" + dbName
					+ ";integratedSecurity=false";
		} else if (CONFIG.getProperty("appEnv").equals("DEV")) {
			dbURL = "jdbc:sqlserver://QTSDEVSQL01.slc.local:1433;databasename=" + dbName + ";integratedSecurity=false";
		}
		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(dbURL, user, pass);
			stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT COUNT(*) as C1 FROM [LMS_interface].[dbo].[UserTable] WHERE Cust_ID IN (select Cust_ID FROM [BankA].[dbo].[tbl_Customer] where Email ='"
							+ clientData.get("Email") + "');");

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();

			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
					if (rs.getString(i).equals("0")) {
						bFlag = false;
						break;
					} else if (rs.getString(i).equals("1")) {
						log.info("User is registered, deleting from database to re-register the user");
						connection.createStatement().executeUpdate(
								"DELETE FROM [LMS_interface].[dbo].[UserTable] where Cust_ID IN (select Cust_ID FROM [BankA].[dbo].[tbl_Customer] where Email ='"
										+ clientData.get("Email") + "');");

						log.info("User is successfully deleted from DataBase");
						bFlag = false;
						break;
					} else {
						bFlag = true;
						break;
					}

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException exp) {
			exp.printStackTrace();
		}
		return bFlag;
	}

	public boolean validate_LMSInterface_PPC(HashMap<String, String> clientData, String dbName, String user,
			String pass) {

		String dbURL = null;
		boolean bFlag = false;
		if (CONFIG.getProperty("appEnv").equals("QA")) {
			dbURL = "jdbc:sqlserver://2012QASQLPROD01.slc.local:1433;databasename=" + dbName
					+ ";integratedSecurity=false";
		} else if (CONFIG.getProperty("appEnv").equals("DEV")) {
			dbURL = "jdbc:sqlserver://QTSDEVSQL01.slc.local:1433;databasename=" + dbName + ";integratedSecurity=false";
		}
		try {

			// Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(dbURL, user, pass);
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT COUNT(*) as C1 FROM [LMS_interface].[dbo].[UserTable] WHERE Cust_ID IN (select Cust_ID FROM [BankA].[dbo].[tbl_Customer] where Email ='"
							+ clientData.get("Email") + "');");

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();

			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
					if (rs.getString(i).equals("0")) {
						bFlag = false;
						break;
					} else if (rs.getString(i).equals("1")) {
						log.info("User is registered, deleting from database to re-register the user");
						// connection.createStatement().executeUpdate(
						// "DELETE FROM [LMS_interface].[dbo].[AccountUserMapping] WHERE UserID in
						// (select UserId from [LMS_interface].[dbo].[UserTable] where Cust_ID= (select
						// Cust_ID FROM [BankA].[dbo].[tbl_Customer] where Email
						// ='"+clientData.get("Email")+"'));");
						connection.createStatement().executeUpdate(
								"DELETE FROM [LMS_interface].[dbo].[UserTable] where Cust_ID IN (select Cust_ID FROM [BankA].[dbo].[tbl_Customer] where Email ='"
										+ clientData.get("Email") + "');");

						log.info("User is successfully deleted from DataBase");
						bFlag = false;
						break;
					} else {
						bFlag = true;
						break;
					}

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException exp) {
			exp.printStackTrace();
		}
		return bFlag;
	}
	
	
	//check if user has password in LMS Interface
	
	public static boolean isPasswordReset(String email) {
		String dbName = "LMS_interface";
		String user= "lms_user"; 
		String pass= "ST20JP*otty";

		String dbURL = null;
		boolean bFlag = false;
		if (CONFIG.getProperty("appEnv").equals("QA")) {
			dbURL = "jdbc:sqlserver://2012QASQLPROD01.slc.local:1433;databasename=" + dbName
					+ ";integratedSecurity=false";
		} else if (CONFIG.getProperty("appEnv").equals("DEV")) {
			dbURL = "jdbc:sqlserver://QTSDEVSQL01.slc.local:1433;databasename=" + dbName + ";integratedSecurity=false";
		}
		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(dbURL, user, pass);
			stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT COUNT(*) as C1 FROM [LMS_interface].[dbo].[UserTable] WHERE Cust_ID IN (select Cust_ID FROM [BankA].[dbo].[tbl_Customer] where Email ='"
							+ clientData.get("Email") + "');");

			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();

			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
					if (rs.getString(i).equals("0")) {
						bFlag = false;
						break;
					} else if (rs.getString(i).equals("1")) {
						log.info("User is registered, deleting from database to re-register the user");
						connection.createStatement().executeUpdate(
								"DELETE FROM [LMS_interface].[dbo].[UserTable] where Cust_ID IN (select Cust_ID FROM [BankA].[dbo].[tbl_Customer] where Email ='"
										+ clientData.get("Email") + "');");

						log.info("User is successfully deleted from DataBase");
						bFlag = false;
						break;
					} else {
						bFlag = true;
						break;
					}

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException exp) {
			exp.printStackTrace();
		}
		return bFlag;
	}
	
	
	
	
	
	
	

	public static String getdbURL(String env) {
		String dbURL = null;
		if (env.equals("QA")) {
			dbURL = "jdbc:sqlserver://2012QASQLPROD01.slc.local:1433;databasename=QAA;integratedSecurity=false";
		} else if (env.equals("DEV")) {
			dbURL = "jdbc:sqlserver://QTSDEVSQL01.slc.local:1433;databasename=QAA;integratedSecurity=false";
		}
		return dbURL;
	}

	public String getdbURL(String env, String dbName) {
		String dbURL = null;
		if (env.equals("QA")) {
			dbURL = "jdbc:sqlserver://2012QASQLPROD01.slc.local:1433;databasename=" + dbName
					+ ";integratedSecurity=false";
		} else if (env.equals("DEV")) {
			dbURL = "jdbc:sqlserver://QTSDEVSQL01.slc.local:1433;databasename=" + dbName + ";integratedSecurity=false";
		}
		return dbURL;
	}

	public static List<String> resultSetToArrayList(ResultSet rs, List<String> clientData) throws SQLException {

		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= columns; i++) {
				clientData.add(rs.getObject(i).toString().trim());
			}
		}
		return clientData;
	}

	public HashMap<String, String> resultSetToHashMap(ResultSet rs) throws SQLException {

		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		HashMap<String, String> row = new HashMap<String, String>();
		while (rs.next()) {
			for (int i = 1; i <= columns; i++) {
				row.put(md.getColumnName(i), rs.getString(i));
			}
		}
		return row;
	}

	public static void writeToExcel(int counterOfExecutions, List<String> clientData)
			throws IOException, InvalidFormatException {

		File file = new File("./src/output/clientData_Output.xlsx");
		if (file.exists() && !file.isDirectory()) {
			file.delete();
		}
		File file1 = new File("./resources/testdata_config/clientData_template.xlsx");
		File file2 = new File("./src/output/clientData_Output.xlsx");
		FileUtils.copyFile(file1, file2);

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("Sheet1");

		Row row;
		Cell cell;
		int add = 0;

		String[] HeaderColumn = { "First_Name", "Last_Name", "Email", "SSN_1", "SSN_2", "SSN_3", "DateofBirth",
				"Street", "City", "State", "Zip", "Employer_Job", "Fax", "Home_Ph_1", "Home_Ph_2", "Home_Ph_3",
				"Cell_Ph_1", "Cell_Ph_2", "Cell_Ph_3", "Employer_Name", "Employer_Street", "Employer_City",
				"Employer_State", "Employer_Zip", "ABA", "Bank_Name", "Bank_Street", "Bank_City", "Bank_State",
				"Bank_Zip", "Acct", "Active", "Period", "NetPay", "Next_PayDate", "Second_PayDate" };

		for (int i = 0; i < 1; i++) {
			row = sheet.createRow(i);
			for (int j = 0; j < HeaderColumn.length; j++) {
				cell = row.createCell(j);
				cell.setCellValue(HeaderColumn[j]);
			}
		}

		for (int i = 1; i < counterOfExecutions + 1; i++) {
			row = sheet.createRow(i);

			for (int j = 0; j < 50; j++) {
				cell = row.createCell(j);
				if (clientData.get(add).equals("Next")) {
					add++;
					break;
				} else {
					cell.setCellType(CellType.STRING);
					cell.setCellValue(clientData.get(add));
					add++;
				}

			}

			// add+=5;
		}

		FileOutputStream fileOutputSecond = new FileOutputStream(file2);
		wb.write(fileOutputSecond);
		fileOutputSecond.close();

		wb.close();
	}

	public static List<String> getPayrollData(List<String> clientData) throws ParseException {
		String Periodicity = null;
		int factor = 0;
		int num;
		// int num =getRandomDoubleBetweenRange(0,3);

		String payFreq = CONFIG.getProperty("payFrequency");

		if (payFreq.equalsIgnoreCase("weekly")) {
			num = 0;
		} else if (payFreq.equalsIgnoreCase("biweekly")) {
			num = 1;
		} else if (payFreq.equalsIgnoreCase("semi-monthly")) {
			num = 2;
		} else if (payFreq.equalsIgnoreCase("monthly")) {
			num = 3;
		} else {
			num = getRandomDoubleBetweenRange(0, 3);
		}

		// int num = 3;
		switch (num) {
		case 0:
			Periodicity = "W";
			factor = 52;
			log.info("Pay frequency selected is Weekly");
			break;
		case 1:
			Periodicity = "B";
			factor = 26;
			log.info("Pay frequency selected is Biweekly");
			break;
		case 2:
			Periodicity = "S";
			factor = 24;
			log.info("Pay frequency selected is Semi-monthly");
			break;
		case 3:
			Periodicity = "M";
			factor = 12;
			log.info("Pay frequency selected is Monthly");
			break;

		}

		String NetPay = Long.toString(Math.round((Math.random() * (30000) + 30000) / factor));
		int duration = 365 / factor;
		int offset =  5; // Four day minimum loan duration
		// int offset = 6;
/*		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		dateFormat.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, offset + 1);
		if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) { // or sunday
			c.add(Calendar.DAY_OF_MONTH, 2);
		}
		if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			c.add(Calendar.DAY_OF_MONTH, 1);
		}*/
	//	String FirstPayDate = dateFormat.format(c.getTime());
		
		String FirstPayDate = getNextBusinessDayFromGivenDate(getPastDate(offset));
/*
		c.add(Calendar.DAY_OF_MONTH, duration);
		if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) { // or sunday
			c.add(Calendar.DAY_OF_MONTH, 2);
		} else if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			c.add(Calendar.DAY_OF_MONTH, 1);
		}*/
	//	String SecondPayDate = dateFormat.format(c.getTime());
		
		String SecondPayDate = getNextBusinessDayFromGivenDate(getPastDate(offset+duration));

		// System.out.println(temp );

		clientData.add(Periodicity);
		clientData.add(NetPay);
		clientData.add(getNextBusinessDayFromGivenDate(FirstPayDate));
		clientData.add(getNextBusinessDayFromGivenDate(SecondPayDate));
		return clientData;
	}

	public static HashMap<String, String> getPayrollData_VIP(HashMap<String, String> clientData2)
			throws ParseException {
		String Periodicity = null;
		int factor = 0;
		// int num =getRandomDoubleBetweenRange(0,3);
		int num;
		String payFreq = CONFIG.getProperty("payFrequency");

		if (payFreq.equalsIgnoreCase("weekly")) {
			num = 0;
		} else if (payFreq.equalsIgnoreCase("biweekly")) {
			num = 1;
		} else if (payFreq.equalsIgnoreCase("semi-monthly")) {
			num = 2;
		} else if (payFreq.equalsIgnoreCase("monthly")) {
			num = 3;
		} else {
			num = 3;
		}
		switch (num) {
		case 0:
			Periodicity = "W";
			factor = 52;
		case 1:
			Periodicity = "B";
			factor = 26;
			break;
		case 2:
			Periodicity = "S";
			factor = 24;
			break;
		case 3:
			Periodicity = "M";
			factor = 12;
			break;
		}
		String NetPay = Long.toString(Math.round((Math.random() * (30000) + 30000) / factor));
		int duration = 365 / factor;
		int offset = getRandomDoubleBetweenRange(0, duration) + 4; // Four day minimum loan duration
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		dateFormat.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, offset + 1);
		if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) { // or sunday
			c.add(Calendar.DAY_OF_MONTH, 2);
		}
		if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		String FirstPayDate = dateFormat.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, duration);
		if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) { // or sunday
			c.add(Calendar.DAY_OF_MONTH, 2);
		} else if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		String SecondPayDate = dateFormat.format(c.getTime());

		clientData2.put("Periodicity", Periodicity);
		clientData2.put("NetPay", NetPay);
		clientData2.put("FirstPayDate", getNextBusinessDayFromGivenDate(FirstPayDate));
		clientData2.put("SecondPayDate", getNextBusinessDayFromGivenDate(SecondPayDate));

		return clientData2;
	}

	public static String getDayBeforeYesterday() {
		Calendar c = Calendar.getInstance();

		Date d = c.getTime();

		c.setTime(d);

		c.add(Calendar.DAY_OF_YEAR, -2);

		Date previousDate = c.getTime();

		return new SimpleDateFormat("MM/dd/yyyy").format(previousDate);
	}

	public static int getRandomDoubleBetweenRange(int min, int max) {
		int x = (int) (Math.random() * ((max - min) + 1)) + min;
		return x;
	}

	public void ExcelToHashMap(String filePath) throws IOException {

		File file = new File(filePath);
		FileInputStream fp = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fp);
		XSSFSheet sheet = wb.getSheetAt(0);
		Row rowHeader;
		Row rowValue;
		Cell cell;
		ArrayList<String> arrRowHeader = new ArrayList<String>();
		ArrayList<String> arrRowValue = new ArrayList<String>();
		String Headers, HeaderValue;

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			rowHeader = sheet.getRow(0);
			int colNum = rowHeader.getLastCellNum();
			for (int j = 0; j < colNum; j++) {
				arrRowHeader.add(rowHeader.getCell(j).getStringCellValue().toString());
//		    		Headers=rowHeader.getCell(j).getStringCellValue().toString();
//		    		String arrRowHeader[j]=Headers;
			}

		}
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			rowValue = sheet.getRow(1);
			int colNum = rowValue.getLastCellNum();
			for (int j = 0; j < colNum; j++) {
				arrRowValue.add(rowValue.getCell(j).getStringCellValue().toString());
//		    		arrRowValue[j]=HeaderValue;
			}
		}
		for (int i = 0; i < arrRowHeader.size(); i++) {
			map.put(arrRowHeader.get(i), arrRowValue.get(i));
		}
		log.info("File has been written to excel sheet");
	}

	public String generateRandomString(int bound) {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			if (i == 0)
				buffer.append((char) Character.toUpperCase(randomLimitedInt));
			else
				buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		return generatedString;
	}

	public char generateRandomSpecial(int bound) {
		char[] arrSpecialCharacter = new char[8];
		arrSpecialCharacter[0] = '!';
		arrSpecialCharacter[1] = '@';
		arrSpecialCharacter[2] = '#';
		arrSpecialCharacter[3] = '$';
		arrSpecialCharacter[4] = '%';
		arrSpecialCharacter[5] = '^';
		arrSpecialCharacter[6] = '&';
		arrSpecialCharacter[7] = '*';
		int i = new Random().nextInt(7);
		return arrSpecialCharacter[i];
	}

	public int generateRandomInteger(int bound) {
		int c = (int) (new Random().nextInt(bound));
		return c;
	}

	public boolean digiESign_EmailChk() {
		boolean bFlag = false;

		return bFlag;
	}

	public void closeBrowser() {
		if (driver != null) {
			driver.quit();
			log.info("Successfully quit the driver");
		}
		driver = null;
	}

	// Below new code added
	public static void waitForElement(ExpectedCondition<?> expectedCondition) {

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(expectedCondition);

	}

	public void sendKeys(WebElement element, String var) {

		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));

		Assert.assertTrue(element.isDisplayed(), errorMessage = "Text box is not displayed");
		Assert.assertTrue(element.isEnabled(), errorMessage = "Text box is not Enabled");

		// log.info(element.getAttribute("value"));

		if (!element.getAttribute("value").isEmpty()) {
			element.click();
			element.clear();
			element.sendKeys(var);
		} else {
			element.click();
			element.sendKeys(var);
		}

		if (element.getAttribute("value").isEmpty()) {
			throw new SLCException(errorMessage = "Unable to enter values in Text Box");
		}

	}

	public void sendKeys(By by, String var) {
		/*
		 * waitForPresenceOfElement(by, 40);
		 * waitForVisibilityOfElement(driver.findElement(by), 40);
		 */

		if (driver.findElements(by).size() == 1) {
			waitForVisibilityOfElement(driver.findElement(by), 10);

			Assert.assertTrue(driver.findElement(by).isDisplayed(), errorMessage = "Text box is not displayed");
			Assert.assertTrue(driver.findElement(by).isEnabled(), errorMessage = "Text box is not Enabled");
			driver.findElement(by).clear();
			driver.findElement(by).click();
			driver.findElement(by).sendKeys(var);
		} else if (driver.findElements(by).size() > 1) {
			throw new SLCException(errorMessage = "More than one element is present. Put the unique locator");
		} else {
			throw new SLCException(errorMessage = "No such element");
		}

		/*
		 * if (driver.findElement(by).isEnabled() &&
		 * driver.findElement(by).isDisplayed()) { driver.findElement(by).clear();
		 * driver.findElement(by).click(); driver.findElement(by).sendKeys(var); }
		 */
	}

	public void clickOnButton(WebElement element) {

		waitForElementToBeClickable(element, 10);

		if (isElementEnabled(element) && isElementDisplayed(element)) {
			element.click();
		} else {
			Assert.fail("WebElement is disabled or not enabled");
		}

	}

	public void clickOnButton(WebElement element, int time) {

		waitForVisibilityOfElement(element, time);
		waitForElementToBeClickable(element, time);
		if (element.isEnabled() && element.isDisplayed()) {
			element.click();
		} else {
			test.log(LogStatus.FAIL, "Element is disabled" + test.addBase64ScreenShot(report.capture()));
			Assert.fail("Webelement is disabled");
		}

	}

	public boolean isAlertPresent() {
		boolean foundAlert = false;
		WebDriverWait wait = new WebDriverWait(driver, 2 /* timeout in seconds */);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
		} catch (TimeoutException eTO) {
			foundAlert = false;
		}
		return foundAlert;
	}

	/*
	 * public void changeDate() { int factor = 0;
	 * 
	 * String payFreq = CONFIG.getProperty("payFrequency");
	 * 
	 * if (payFreq.equalsIgnoreCase("weekly")) { factor = 52; } else if
	 * (payFreq.equalsIgnoreCase("every2weeks")) { factor = 26; } else if
	 * (payFreq.equalsIgnoreCase("twicemonthly")) { factor = 24; } else if
	 * (payFreq.equalsIgnoreCase("monthly")) { factor = 12; } else { int num =
	 * getRandomDoubleBetweenRange(0, 3); // int num = 3; switch (num) { case 0:
	 * factor = 52; break; case 1: factor = 26; break; case 2: factor = 24; break;
	 * case 3: factor = 12; break; } }
	 * 
	 * int duration = 365 / factor; int offset = getRandomDoubleBetweenRange(0,
	 * duration) + 4;
	 * 
	 * DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); Calendar c =
	 * Calendar.getInstance(); dateFormat.format(c.getTime());
	 * c.add(Calendar.DAY_OF_MONTH, offset + 1); if ((c.get(Calendar.DAY_OF_WEEK) ==
	 * Calendar.SATURDAY)) { // or sunday c.add(Calendar.DAY_OF_MONTH, 2); } if
	 * ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
	 * c.add(Calendar.DAY_OF_MONTH, 1); } String FirstPayDate =
	 * dateFormat.format(c.getTime()); c.add(Calendar.DAY_OF_MONTH, duration); if
	 * ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) { // or sunday
	 * c.add(Calendar.DAY_OF_MONTH, 2); } else if ((c.get(Calendar.DAY_OF_WEEK) ==
	 * Calendar.SUNDAY)) { c.add(Calendar.DAY_OF_MONTH, 1); } String SecondPayDate =
	 * dateFormat.format(c.getTime());
	 * 
	 * log.info("Old Date FirstPayDate : " + map.get("Next_PayDate") +
	 * " & SecondPayDate : " + map.get("Second_PayDate"));
	 * 
	 * map.replace("Next_PayDate", FirstPayDate); map.replace("Second_PayDate",
	 * SecondPayDate);
	 * 
	 * log.info("New Date FirstPayDate : " + map.get("Next_PayDate") +
	 * " & SecondPayDate : " + map.get("Second_PayDate"));
	 * 
	 * }
	 */

	public void changeDate() throws ParseException {

		String FirstPayDate = getNextBusinessDayFromGivenDate(map.get("Next_PayDate"));

		String SecondPayDate = getNextBusinessDayFromGivenDate(map.get("Second_PayDate"));

		log.info("Old FirstPayDate : " + map.get("Next_PayDate"));
		log.info("Old SecondPayDate: " + map.get("Second_PayDate"));

		map.replace("Next_PayDate", FirstPayDate);
		map.replace("Second_PayDate", SecondPayDate);

		log.info("New FirstPayDate : " + map.get("Next_PayDate"));
		log.info("New SecondPayDate: " + map.get("Second_PayDate"));

	}

	public void changeDate_VIP() throws ParseException {

		String FirstPayDate = getNextBusinessDayFromGivenDate(clientData.get("Next_PayDate"));

		String SecondPayDate = getNextBusinessDayFromGivenDate(clientData.get("Second_PayDate"));

		log.info("Old FirstPayDate : " + clientData.get("Next_PayDate"));
		log.info("Old SecondPayDate: " + clientData.get("Second_PayDate"));

		map.replace("Next_PayDate", FirstPayDate);
		map.replace("Second_PayDate", SecondPayDate);

		log.info("New FirstPayDate : " + clientData.get("Next_PayDate"));
		log.info("New SecondPayDate: " + clientData.get("Second_PayDate"));

	}

// Churn Test cases

	public void waitForVisibilityOfElement(WebElement element, int time) {

		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.visibilityOf(element));
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			log.info("Time out exception occured. Element was not visible within the time frame");
			test.log(LogStatus.FAIL, "Time out exception occured. Element was not visible within the time frame"
					+ test.addBase64ScreenShot(report.capture()));
			Assert.fail("Time out exception occured. Element was not visible within the time frame" + " Webelement : "
					+ element);
		}

	}

	public void checkVisibilityOfElement(WebElement element, int time) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOf(element));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void waitForVisibilityOfElement(WebElement element, int time, String message) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOf(element));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void waitForElementToBeClickable(WebElement element, int time) {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			log.info(errorMessage = "Time out exception occured. Element was clickable within the time frame");
			test.log(LogStatus.FAIL, "Time out exception occured. Element was clickable within the time frame"
					+ test.addBase64ScreenShot(report.capture()));
			Assert.fail("Time out exception occured. Element was not clickable within the time frame");
		}

	}

	public void waitForPresenceOfElement(By by, int time) {

		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture()));
			errorMessage = "Webelement is not present in DOM, searched for " + time + " . Element : " + by;
			Assert.fail("Webelement is not present in DOM, searched for " + time + " . Element : " + by);
		}

	}

	public void presenceOfElement(By by, int time) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void waitForPresenceOfElement(By by, int time, String message) {

		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			errorMessage = message;
			test.log(LogStatus.FAIL, message + test.addBase64ScreenShot(report.capture()));
			log.info(message);
			Assert.fail(message);
		}

	}

	public static void waitForPresenceOfElement(String locator, Properties prop, int time) {
		String[] tokens = locator.split("_");
		String locatorType = tokens[tokens.length - 1];
		String strlocator = prop.getProperty(locator).trim();
		WebDriverWait wait = new WebDriverWait(driver, time);

		try {
			if (locatorType.equalsIgnoreCase("xpath")) {
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(strlocator)));
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			} else if (locatorType.equalsIgnoreCase("id")) {
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(strlocator)));
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			} else if (locatorType.equalsIgnoreCase("name")) {
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.name(strlocator)));
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			} else if (locatorType.equalsIgnoreCase("tagname")) {
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(strlocator)));
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			} else if (locatorType.equalsIgnoreCase("linkText")) {
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(strlocator)));
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			} else if (locatorType.equalsIgnoreCase("partialLinkText")) {
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(strlocator)));
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			} else {
				log.info("Invalid locators");
			}
		} catch (TimeoutException e) {
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture()));
			Assert.fail("Webelement is not present in DOM, searched for " + time + " . Element : " + locator);
		}

	}

	public static void checkPresenceOfElement(String locator, Properties prop, int time) {
		String[] tokens = locator.split("_");
		String locatorType = tokens[tokens.length - 1];
		String strlocator = prop.getProperty(locator).trim();
		WebDriverWait wait = new WebDriverWait(driver, time);

		if (locatorType.equalsIgnoreCase("xpath")) {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(strlocator)));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else if (locatorType.equalsIgnoreCase("id")) {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(strlocator)));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else if (locatorType.equalsIgnoreCase("name")) {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.name(strlocator)));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else if (locatorType.equalsIgnoreCase("tagname")) {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(strlocator)));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else if (locatorType.equalsIgnoreCase("linkText")) {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(strlocator)));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else if (locatorType.equalsIgnoreCase("partialLinkText")) {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(strlocator)));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else {
			log.info("Invalid locators");
		}

	}

	public void waitForFrameToBeAvailableAndSwitchToIt(String frameLocator, int time) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void waitForFrameToBeAvailableAndSwitchToIt(WebElement frameLocator, int time) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void sendKeysUsingJS(WebElement element, String value) {
		waitForVisibilityOfElement(element, 40);
		waitForElementToBeClickable(element, 40);
		element.clear();
///		element.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + value + "';", element);

	}

	public void clickUsingJS(WebElement element) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public void clickUsingAction(WebElement element) {
		waitForVisibilityOfElement(element, 40);
		waitForElementToBeClickable(element, 40);
		Actions action = new Actions(driver);
		// action.moveToElement(element, element.getSize().getHeight(),
		// element.getSize().getWidth()).click(element)
		action.moveToElement(element).click().perform();
		// action.clickAndHold(element).release().build().perform();
	}

	public void doubleClickUsingAction(WebElement element) {
		waitForVisibilityOfElement(element, 40);
		waitForElementToBeClickable(element, 40);
		Actions action = new Actions(driver);
		action.doubleClick(element).build().perform();
	}

	public void scrollIntoView(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void scrollBy(int pixel) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + pixel + ")");
	}

	public static void scrollToEndOfPage() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void selectByIndex(WebElement element, int index) {
		Select select = new Select(element);
		select.selectByIndex(index);

	}

	public void selectByValue(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);

	}

	public void selectByVisibleText(WebElement element, String text) {
		Select select = new Select(element);
		select.selectByVisibleText(text);

	}

	public static String getSelectedOptionFromDropdown(WebElement element) {

		Select s = new Select(element);
		return s.getFirstSelectedOption().getText();

	}

	public void switchToMainFrame() {
		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
	}

	public void switchToLeftFrame() {
		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Left", 30);
	}

	public void switchToTranDialogIFrame() {

		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 30);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 30);
		waitForFrameToBeAvailableAndSwitchToIt("TRANdialogiframe", 30);
	}

	public void switchToPaymentFrame() {

		driver.switchTo().defaultContent();
		waitForFrameToBeAvailableAndSwitchToIt(driver.findElement(By.tagName("iframe")), 10);
		waitForFrameToBeAvailableAndSwitchToIt("Main", 10);
		waitForFrameToBeAvailableAndSwitchToIt("PaymentMethodFrame", 10);
	}
//Added for try with another lender

	public void getNewUserData_VIPUser(String fileName) throws Throwable {
		log.info("Get data for VIP user");
		boolean bFlag = false;
		String merchant = null;
		boolean bNewRegister = true;
		// HashMap<String,String> clientData= new HashMap<String,String>();
		List<String> myData = new ArrayList<String>();
		String[] arrName = fileName.split("_");
		if (arrName[0].equals("BTL")) {
			merchant = "57510";
		} else if (arrName[0].equals("MLL")) {
			merchant = "57511";
		}
		String env = CONFIG.getProperty("appEnv");
		String URL = getdbURL(env);
		while (bNewRegister) {
			try {
				// Class.forName("net.sourceforge.jtds.jdbc.Driver");
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
				CallableStatement stmt = connection.prepareCall("{CALL spGetAddress(?)}");
				stmt.setString(1, merchant);
				ResultSet rs = stmt.executeQuery();
				myData = resultSetToArrayList(rs, myData);
				stmt = connection.prepareCall("{CALL spTest_GetVIPsingle(?,?,?)}");
				stmt.setString(1, merchant);
				stmt.setString(2, "0");
				stmt.setString(3, myData.get(2));
				rs = stmt.executeQuery();
				clientData = resultSetToHashMap(rs);

				Set<String> data = clientData.keySet();

				for (String vData : data) {
					log.info("Key:Value = " + vData + " : " + clientData.get(vData));
				}
				bNewRegister = validate_LMSInterface(clientData, "LMS_interface", "lms_user", "ST20JP*otty");
				clientData = getPayrollData_VIP(clientData);
				myData.clear();
				if (bNewRegister == false)
					break;
			} catch (SQLException e) {
				e.getMessage();
			} catch (ClassNotFoundException e) {
				e.getMessage();
			}
		}

	}

	public void getNewUserData_PPCUser(String fileName) throws Throwable {
		log.info("Get data for PPC user");
		boolean bFlag = false;
		String merchant = null;
		boolean bNewRegister = true;
		// HashMap<String,String> clientData= new HashMap<String,String>();
		List<String> myData = new ArrayList<String>();
		String[] arrName = fileName.split("_");
		if (arrName[0].contains("BTL")) {
			merchant = "57510";
		} else if (arrName[0].contains("MLL")) {
			merchant = "57511";
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
				stmt = connection.prepareCall("{CALL spTest_NewGetOrganicVIP(?,?,?)}");
				stmt.setString(1, merchant);
				stmt.setString(2, "0");
				stmt.setString(3, myData.get(2));
				rs = stmt.executeQuery();
				clientData = resultSetToHashMap(rs);

				Set<String> data = clientData.keySet();

				for (String vData : data) {
					log.info("Key:Value = " + vData + " : " + clientData.get(vData));
				}

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

	public void getNewUserData_NormalUser(String fileName) {

		log.info("Get data for normal user");
		String newFileName = fileName;
		String[] listOfDisabledCountries = { "HI", "GA", "CT", "AR", "MA", "MN", "NY", "PA", "VT", "VA", "WA", "WV",
				"WI", "CA" };
		String[] mechant_57510 = { "AZ", "DC", "MD", "NC", "NJ" };
		String[] mechant_57511 = { "ND" };
		boolean bFlag = false;
		String merchant = null;
		List<String> clientData = new ArrayList<String>();
		String[] arrName = fileName.split("_");
		if (arrName[0].equals("BTL")) {
			merchant = "57510";
		} else if (arrName[0].equals("MLL")) {
			merchant = "57511";
		}
		String env = arrName[1];// args[1];
		int noofExecutions = Integer.parseInt(arrName[3]);
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
					log.info("Disabled States found ");
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

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			// tlog.info("Using recursion as IndexOutOfBoundsException exception occured ");
			getNewUserData_NormalUser(newFileName);
		} catch (NoSuchFieldException e) {
			// log.info("Using recursion as NoSuchFieldException exception occured ");
			getNewUserData_NormalUser(newFileName);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void fetchUserInfo() throws Throwable {
		ExcelToHashMap("./src/output/clientData_Output.xlsx");
	}

	public void applyForLoan() {

		log.info("Specify information on AboutYou Page");
		Set<String> data = map.keySet();

		log.info("New Test Data to be used for testing");
		for (String cData : data) {
			log.info(cData + " : " + map.get(cData));
		}

		waitForPresenceOfElement(By.id("content_customContent_frmFirstName"), 40);
		sendKeys(getWebElement("FirstNameTxt_ID", OR_MLL), map.get("First_Name"));
		sendKeys(getWebElement("LastNameTxt_ID", OR_MLL), map.get("Last_Name"));
		sendKeysUsingJS(getWebElement("DOBTxt_ID", OR_MLL), "02/02/1973");
		sendKeysUsingJS(getWebElement("SSNTxt_ID", OR_MLL), map.get("SSN_1") + map.get("SSN_2") + map.get("SSN_3"));
		log.info("SSN NO=" + map.get("SSN_1") + map.get("SSN_2") + map.get("SSN_3"));
		sendKeys(getWebElement("EmailTxt_ID", OR_MLL), map.get("Email"));
		sendKeys(getWebElement("ConfirmEmailTxt_ID", OR_MLL), map.get("Email"));
		sendKeys(getWebElement("StreetAddressTxt_ID", OR_MLL), map.get("Street"));
		sendKeys(getWebElement("CityTxt_ID", OR_MLL), map.get("City"));
		WebElement eleState = getWebElement("StateDrpdwn_ID", OR_MLL);
		eleState.click();
		selectByValue(getWebElement("StateDrpdwn_ID", OR_MLL), map.get("State"));
		sendKeysUsingJS(getWebElement("ZipTxt_ID", OR_MLL), map.get("Zip"));
		sendKeys(getWebElement("HomePhoneTxt_ID", OR_MLL),
				map.get("Home_Ph_1") + map.get("Home_Ph_2") + map.get("Home_Ph_3"));
		if (getWebElement("CellPhoneChkbox_ID", OR_MLL).isSelected()) {
			clickOnButton(getWebElement("CellPhoneChkbox_ID", OR_MLL));
			waitForPresenceOfElement(By.id("content_customContent_frmCellPhone"), 10);
			sendKeys(getWebElement("CellPhoneTxt_ID", OR_MLL),
					map.get("Cell_Ph_1") + map.get("Cell_Ph_2") + map.get("Cell_Ph_3"));
		}
		scrollBy(100);

		clickOnButton(getWebElement("AboutYouNextBtn_ID", OR_MLL));

		waitForPresenceOfElement(By.id("content_customContent_incomeTab"), 40);
		waitForVisibilityOfElement(getWebElement("IncomeTab_ID", OR_MLL), 40);
		waitForPageLoaded();

		WebElement eleIncomeTab = getWebElement("IncomeTab_ID", OR_MLL);

	}

	public void updateIncomePage(String IncomeSource, String paymentType) {

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

			WebElement eleSource = getWebElement("IncomeSourceDrpDwn_ID", OR_MLL);
			eleSource.click();
			Select state = new Select(eleSource);
			if (IncomeSource.equals("Employed")) {
				state.selectByValue("P");
				getWebElement("EmployerNameTxt_ID", OR_MLL).sendKeys(map.get("Employer_Name"));
				getWebElement("WorkPhoneTxt_ID", OR_MLL).click();
				String WorkPhone = String.valueOf(959);
				String WorkPhone1 = String.valueOf(rm.nextInt((999 - 850) + 1) + 850);
				String Workphone2 = String.valueOf(rm.nextInt(9999));
				String Workphone3 = String.valueOf(rm.nextInt(9999));
				// getWebElement("WorkPhoneTxt_ID", OR_MLL)
				// .sendKeys(WorkPhone + "" + WorkPhone1 + "" + Workphone2 + "" + Workphone3);

				getWebElement("WorkPhoneTxt_ID", OR_MLL).sendKeys(959 + "" + 994 + "" + 9407 + "" + 4662);

			} else {
				state.selectByValue(strIncomeSource);
				String strMainIncomeSource = driver
						.findElement(By.xpath("//select[@id='content_customContent_frmIncomeSource']/option[@value='"
								+ strIncomeSource + "']"))
						.getText().trim();
			}
			WebElement element = getWebElement("DirectDepositChkbox_ID", OR_MLL);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			if (paymentType.equals("DirectDeposit"))
				getWebElement("DirectDepositChkbox_ID", OR_MLL).click();
			else
				getWebElement("PaperChkChkbox_ID", OR_MLL).click();
			if (map.get("Period").equals("B")) {
				getWebElement("Every2WeeksRadioBtn_ID", OR_MLL).click();
			} else if (map.get("Period").equals("S")) {
				getWebElement("TwiceMonthlyRadioBtn_ID", OR_MLL).click();
			} else if (map.get("Period").equals("M")) {
				getWebElement("MonthlyRadioBtn_ID", OR_MLL).click();
			} else if (map.get("Period").equals("W")) {
				getWebElement("WeeklyRadioBtn_ID", OR_MLL).click();
			}
			waitForPresenceOfElement(By.id("content_customContent_frmNetPay"), 30);
			sendKeys(getWebElement("NetPayTxt_ID", OR_MLL), "16000");
			String date = LocalDate.now(ZoneId.of("America/New_York"))
					.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
			sendKeysUsingJS(getWebElement("LastPayDate_ID", OR_MLL), date);
			sendKeysUsingJS(getWebElement("NextPayDateTxt_ID", OR_MLL), map.get("Next_PayDate"));
			sendKeysUsingJS(getWebElement("SecondPayDateTxt_ID", OR_MLL), map.get("Second_PayDate"));
			waitForPresenceOfElement(By.id("content_customContent_incomeNext"), 30);
			clickOnButton(getWebElement("IncomeNextBtn_ID", OR_MLL));
			int count = 0;

			try {
				waitForPresenceOfElement(By.id("content_customContent_bankingTab"), 20);
				waitForVisibilityOfElement(getWebElement("BankingTab_ID", OR_MLL), 10);

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
						changeDate();
						sendKeysUsingJS(getWebElement("NextPayDateTxt_ID", OR_MLL), map.get("Next_PayDate"));
						log.info(" Updated " + map.get("Next_PayDate"));

						sendKeysUsingJS(getWebElement("SecondPayDateTxt_ID", OR_MLL), map.get("Second_PayDate"));
						log.info(" Updated " + map.get("Second_PayDate"));
						clickUsingJS(getWebElement("IncomeNextBtn_ID", OR_MLL));
						count++;
						try {
							waitForPresenceOfElement(By.id("content_customContent_bankingTab"), 5);
							waitForVisibilityOfElement(getWebElement("BankingTab_ID", OR_MLL), 10);
							bFlag = false;
						} catch (NoSuchElementException | TimeoutException e1) {
							if (count == 3) {
								break;
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

	public void UpdateBankingPage(String PaymentMode) {

		boolean flag = false;
		WebElement eleBankingTab = getWebElement("BankingTab_ID", OR_MLL);
		if (eleBankingTab.isDisplayed()) {

			waitForPresenceOfElement("ABARoutingNoTxt_ID", OR_MLL, 40);
			sendKeysUsingJS(getWebElement("ABARoutingNoTxt_ID", OR_MLL), map.get("ABA"));

			waitForPresenceOfElement("BankAcctNo_ID", OR_MLL, 20);
			sendKeysUsingJS(getWebElement("BankAcctNo_ID", OR_MLL), map.get("Acct"));

			WebElement element = getWebElement("EFTFundRadio_ID", OR_MLL);
			scrollBy(450);
			if (PaymentMode.equals("Electronic Fund Transfer"))
				getWebElement("EFTFundRadio_ID", OR_MLL).click();
			else if ((PaymentMode.equals("Credit Card")))
				getWebElement("CreditCardRadio_ID", OR_MLL).click();
			else
				getWebElement("TextChkRadio_ID", OR_MLL).click();

			if (getWebElement("EFTFundRadio_ID", OR_MLL).isSelected()) {
				log.info("Electronic Fund Transfer selected from banking page");
			} else {
				log.info("TextChkRadio selected from banking page");
			}
			clickUsingJS(getWebElement("ChkApprovalBtn_ID", OR_MLL));
			String strFirstName = map.get("First_Name").substring(0, 1).toUpperCase()
					+ map.get("First_Name").substring(1).toLowerCase();
			int iChkApproval = driver
					.findElements(By.xpath("//div[@id='headerBlock1']//p[contains(text(),'" + strFirstName + "')]"))
					.size();
			if (iChkApproval > 0) {
				element = driver
						.findElement(By.xpath("//div[@id='headerBlock1']//p[contains(text(),'" + strFirstName + "')]"));
				if (element.isDisplayed()) {
					flag = true;

				}
			}

		}

	}

	public static void acceptLocation() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.navigator.geolocation.getCurrentPosition=function(success){"
				+ "var position = {\"coords\" : {\"latitude\": \"555\",\"longitude\": \"999\"}};"
				+ "success(position);}");
	}

	public static String getNextBusinessDayFromGivenDate(String date) throws ParseException {

		String env = CONFIG.getProperty("appEnv");
		String date4 = "";
		ResultSet rs = null;
		// String date =
		// LocalDate.now(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//mm/dd/yyyy
		String date1 = date;

		Date d = new SimpleDateFormat("MM/dd/yyyy").parse(date1);

		String newDate = new SimpleDateFormat("yyyy-MM-dd").format(d);

		String URL = getdbURL(env);

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
			CallableStatement stmt = connection.prepareCall("{CALL spGetNextBusinessDay(?)}");

			stmt.setString(1, newDate);

			rs = stmt.executeQuery();

			while (rs.next()) {
				String businessDate = rs.getString(1);
				Date businessDay = new SimpleDateFormat("yyyy-MM-dd").parse(businessDate);
				date4 = new SimpleDateFormat("MM/dd/yyyy").format(businessDay);

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return date4;
	}

	public String getNextBusinessDayFromToday() {

		String env = CONFIG.getProperty("appEnv");
		String getNextBusinessDay = "";
		ResultSet rs = null;
		String date = LocalDate.now(ZoneId.of("America/New_York")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		String URL = getdbURL(env);
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
			CallableStatement stmt = connection.prepareCall("{CALL spGetNextBusinessDay(?)}");

			stmt.setString(1, date);

			rs = stmt.executeQuery();

			while (rs.next()) {
				String date1 = rs.getString(1);

				Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
				getNextBusinessDay = new SimpleDateFormat("MM/dd/yyyy").format(d);
			}

		} catch (ClassNotFoundException e) {
			e.getMessage();
		} catch (SQLException e) {
			e.getMessage();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getNextBusinessDay;
	}

	public static ArrayList<String> getDataExcludedFromPIF(String mechant_ID) {

		String env = CONFIG.getProperty("appEnv");

		ResultSet rs = null;

		String URL = getdbURL(env);

		String merchant = "";

		if (mechant_ID.equalsIgnoreCase("blue trust")) {
			merchant = "57510";
		} else {
			merchant = "57511";
		}

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
			CallableStatement stmt = connection.prepareCall("{CALL spTest_GetForExcludeFromPIF(?)}");
			stmt.setString(1, merchant);
			rs = stmt.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			int column = md.getColumnCount();

			while (rs.next()) {

				for (int i = 1; i <= column; i++) {
					listFromDatabase.add(rs.getString(i));
				}

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listFromDatabase;

	}

	// in case element is not clickable at point(x,y) exception

	public void clickUsingJSForClickableExceptionUsingYCordinate(WebElement elementToClick) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + elementToClick.getLocation().y + ")");
		elementToClick.click();

	}

	public void clickUsingJSForClickableExceptionUsingXCordinate(WebElement elementToClick) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + elementToClick.getLocation().x + ")");
		elementToClick.click();

	}

	public static String getFutureDate(int noOfDays) throws ParseException {
		Calendar c = Calendar.getInstance();

		Date d = c.getTime();

		c.setTime(d);

		c.add(Calendar.DAY_OF_YEAR, noOfDays);

		Date previousDate = c.getTime();

		return new SimpleDateFormat("MM/dd/yyyy").format(previousDate);
	}

	public static String getPastDate(int noOfDays) {
		Calendar c = Calendar.getInstance();

		Date d = c.getTime();

		c.setTime(d);

		c.add(Calendar.DAY_OF_YEAR, noOfDays);

		Date previousDate = c.getTime();

		return new SimpleDateFormat("MM/dd/yyyy").format(previousDate);
	}

	public static void writeToFile(ArrayList<String> ar, String merchant) throws IOException {

		ArrayList<String> data = ar;

		File file = new File(System.getProperty("user.dir") + "\\CustomerData\\" + merchant.replace(" ", "_") + ".txt");

		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter wr = new FileWriter(file);

		wr.write("**** " + merchant + " Application Number ****");
		wr.write("\n");

		for (String s : data) {
			wr.write("Application Number :" + s);

			wr.write("\n");
		}

		wr.close();

	}

	public static byte[] getScreenshot() {
		TakesScreenshot ts = (TakesScreenshot) driver;
		byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
		return screenshot;

	}

	public String addCheckNo() throws InterruptedException, IOException {

		try {
			switchToPaymentFrame();
			if (!getWebElement("Txtacheck_xpath", OR_MLL).isSelected()) {
				getWebElement("Txtacheck_xpath", OR_MLL).click();
			}
		} catch (Exception e) {

		}
		Random rs = new Random();
		String CheckNo = String.valueOf(rs.nextInt(9999));
		switchToMainFrame();
		scrollIntoView(getWebElement("TxtACheck_ID", OR_MLL));
		waitForVisibilityOfElement(getWebElement("TxtACheck_ID", OR_MLL), 10);
		sendKeys(getWebElement("TxtACheck_ID", OR_MLL), CheckNo);
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "Screen shot")));
		getWebElement("SendScheduleBtn_XPATH", OR_LMS).click();

		return CheckNo;
	}

	public void doTxtACheckSignature() {

		log.info("perform TextaCheck Process");

		driver.get(LinkTXT);
		waitForPageToLoad(By.xpath("//input[@id='txtAccountNumber']"), 40);
		sendKeys(driver.findElement(By.xpath("//input[@id='txtAccountNumber']")), map.get("Acct"));
		test.log(LogStatus.PASS, test.addBase64ScreenShot(report.capture(driver, "TextaCheck_Bank Account Number")));
		waitForPresenceOfElement(By.xpath("//a[@id='btnSubmit']"), 30);

		waitForElementToBeClickable(getWebElement("SubmitBtn_XPATH", OR_MLL), 20);
		clickOnButton(getWebElement("SubmitBtn_XPATH", OR_MLL));
		acceptLocation();
		log.info("Accepted GeoLocation");
		waitForPageToLoad(By.xpath("//img[@id='imgCheck']"), 120);

		int ielement = driver.findElements(By.xpath("//img[@id='imgCheck']")).size();
		if (ielement > 0) {
			test.log(LogStatus.PASS, "Check Image" + test.addBase64ScreenShot(report.capture(driver, "Check Image")));
		} else {
			test.log(LogStatus.FAIL, "Unable to print the Check");
			test.log(LogStatus.FAIL, test.addBase64ScreenShot(report.capture(driver, "Check Image")));
		}
		scrollIntoView(getWebElement("SubmitBtn_XPATH", OR_MLL));
		clickOnButton(getWebElement("SubmitBtn_XPATH", OR_MLL));
		waitForPageToLoad(By.id("MyCanvas"), 60);
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
		waitForPageToLoad(By.id("imgCheckFront"), 80);
		clickOnButton(getWebElement("Disclaimer_ID", OR_MLL));
		waitForPresenceOfElement(By.id("imgCheckFront"), 60);
		waitForVisibilityOfElement(driver.findElement(By.id("imgCheckFront")), 120);
		waitForPresenceOfElement(By.xpath("//button[@id='btnSubmit']"), 30);
		clickOnButton(getWebElement("TxtAChkSubmitBtn_XPATH", OR_MLL));

	}

	public void doTxtACheckProcess() {
		log.info("schedule TxtACheck and Send Mail");
		boolean flag = false;
		// waitForPageLoaded();
		// String CheckNo = addCheckNo();
		int time_init = LocalTime.now().getMinute();

		log.info("Starting time : " + time_init);
		int time_final = time_init + 2;

		if (time_final >= 60) {
			time_final = time_final - 60;
		}
		log.info("Ending time : " + time_final);
		boolean emailChk = false;
		do {
			emailChk = receiveMail(CONFIG.getProperty("UserName_" + CONFIG.getProperty("appEnv")),
					CONFIG.getProperty("Password_" + CONFIG.getProperty("appEnv")), "Inbox_TxtACheck");
			// time_init = LocalTime.now().getMinute();
			if (time_init == time_final && emailChk == true) {
				log.info("TxtACheck e-mail is not present in private email");
				test.log(LogStatus.FAIL, "TxtACheck e-mail not received in private email");
				Assert.fail("TxtACheck e-mail is not received in private email");

			}

		} while (!emailChk);

		if (emailChk) {
			test.log(LogStatus.PASS, "TxtACheck Request Email Sent Successfully to User " + map.get("First_Name") + " "
					+ map.get("Last_Name"));
		}

	}

	public void closeAnyOpenBrowser() {
		try {
			Runtime.getRuntime().exec("taskkill /im chromedriver* /f /t");
			Runtime.getRuntime().exec("taskkill /im geckodriver* /f /t");
		} catch (IOException e) {
			log.info(e.getMessage());
		}

	}

	public static boolean isElementDisplayed(WebElement element) {
		boolean status = false;
		try {
			status = element.isDisplayed();

		} catch (NoSuchElementException | TimeoutException e) {
			Assert.fail("Please check the locator");
		}

		return status;
	}

	public static boolean isElementEnabled(WebElement element) {

		return element.isEnabled();
	}

	public static boolean isElementSelected(WebElement element) {

		return element.isSelected();
	}

	public static void verifyStringInTextBox(String actual, WebElement element) {
		Assert.assertEquals(actual, element.getAttribute("value").trim());
	}

	public void writeStatusToDatabase(String featureFileName, String scenario, String status, String startTime,
			String endTime, long duration, String browserName, String merchantID, String errorMessage, int SSN,
			int ApplicationNumber) {
		String URL = getdbURL(CONFIG.getProperty("appEnv"));

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(URL, "QAAUser", "Slc$$0qa");
			Statement stmt = connection.createStatement();
			String query = "INSERT INTO [QAA].[dbo].[DailyStatusReport] values(" + "'" + featureFileName + "'" + ","
					+ "'" + scenario + "'" + "," + "'" + status + "'" + "," + "'" + startTime + "'" + ", " + "'"
					+ endTime + "'" + "," + "'" + duration + "'" + "," + "'" + browserName + "'" + "," + "'"
					+ merchantID + "'" + "," + "'" + errorMessage + "'" + "," + "'" + SSN + "'" + "," + "'"
					+ ApplicationNumber + "'" + ")";
			stmt.executeUpdate(query);
			stmt.close();
			connection.close();
		} catch (Exception e) {
			log.info(e.getMessage());
		}

	}


	
	public static void deleteUserPassword(String email) {

		String dbURL = null;
		boolean bFlag = false;
		String dbName = "LMS_interface";
		String user =  "lms_user";
		String password = "ST20JP*otty";
		if (CONFIG.getProperty("appEnv").equals("QA")) {
			dbURL = "jdbc:sqlserver://2012QASQLPROD01.slc.local:1433;databasename=" + dbName
					+ ";integratedSecurity=false";
		} else if (CONFIG.getProperty("appEnv").equals("DEV")) {
			dbURL = "jdbc:sqlserver://QTSDEVSQL01.slc.local:1433;databasename=" + dbName + ";integratedSecurity=false";
		}
		try {

			// Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(dbURL, user, password);
			stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT COUNT(*) as C1 FROM [LMS_interface].[dbo].[UserTable] WHERE Cust_ID IN (select Cust_ID FROM [BankA].[dbo].[tbl_Customer] where Email ='"
							+ email + "');");

			ResultSetMetaData md = rs.getMetaData();
		//	int columns = md.getColumnCount();

			
			while (rs.next()) {
					if (rs.getString(1).equals("0")) {
						log.info("User has not created the password");
						break;
					} else if (rs.getString(1).equals("1")) {
						log.info("User is registered, deleting from database to re-register the user");
						connection.createStatement().executeUpdate(
								"DELETE FROM [LMS_interface].[dbo].[UserTable] where Cust_ID IN (select Cust_ID FROM [BankA].[dbo].[tbl_Customer] where Email ='"
										+ clientData.get("Email") + "');");

						log.info("User is successfully deleted from DataBase");
						bFlag = false;
						break;
					} else {
						bFlag = true;
						log.info("More than 1 row found while deleting the user password");
						break;
					}

				}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException exp) {
			exp.printStackTrace();
		}
		//return bFlag;
	}


	public static String createUserPass(String FName,String LName,String DateOfBirth)
	{
		
		strPwd = FName.substring(0, 1).toUpperCase()+ FName.substring(1, 2).toLowerCase() + LName.substring(0, 2).toLowerCase()+ DateOfBirth.substring(DateOfBirth.length()-4, DateOfBirth.length())+"$";
		log.info("User pass created : " + strPwd);
		return strPwd;
	}
}
