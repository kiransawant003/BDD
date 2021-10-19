package com.TestAutomation.Reporting;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import com.relevantcodes.extentreports.ExtentReports;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import com.TestAutomation.StepDef.*;

public class ExtentManager extends TestBase {
	public static ExtentReports extent;
	//Date date ;

	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			// Set HTML reporting file location
			String workingDir = System.getProperty("user.dir");
			extent = new ExtentReports(workingDir + "//target//ExtentReportResults.html", true);

			// To add system or environment info by using the addSystemInfo method.
			extent.addSystemInfo("OS", System.getProperty("os.name"));
			extent.addSystemInfo("Browser", CONFIG.getProperty("browser"));
			extent.addSystemInfo("Environment", CONFIG.getProperty("appEnv"));

			extent.loadConfig(new File(workingDir + "//src//com//TestAutomation//Reporting//extent-config.xml"));

		}
		return extent;
	}
	/*
	 * public static ExtentHtmlReporter htmlReporter = new
	 * ExtentHtmlReporter(System.getProperty("user.dir")+
	 * "\\target\\ExtentReportResults.html"); public static ExtentReports extent;
	 * 
	 * 
	 * public void initialize() { extent = new ExtentReports();
	 * extent.attachReporter(htmlReporter);
	 * 
	 * //To add system or environment info by using the setSystemInfo method.
	 * extent.setSystemInfo("OS", System.getProperty("os.name"));
	 * extent.setSystemInfo("Browser", TestBase.CONFIG.getProperty("browser"));
	 * extent.setSystemInfo("Environment", TestBase.CONFIG.getProperty("appEnv"));
	 * 
	 * //configuration items to change the look and feel //add content, manage tests
	 * etc htmlReporter.config().setChartVisibilityOnOpen(true);
	 * htmlReporter.config().setDocumentTitle("SLC Automation Report");
	 * htmlReporter.config().setReportName("SLC Automation Report");
	 * htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
	 * htmlReporter.config().setTheme(Theme.STANDARD); htmlReporter.config().
	 * setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
	 * 
	 * 
	 * }
	 */

	// Old Code

	public String capture_old(WebDriver driver, String screenShotName) throws IOException {
		/**
		 * TakesScreenshot ts = (TakesScreenshot)driver; File source =
		 * ts.getScreenshotAs(OutputType.FILE); String dest =
		 * System.getProperty("user.dir")
		 * +"\\target\\ErrorScreenshots\\"+screenShotName+".png"; File destination = new
		 * File(dest); FileUtils.copyFile(source, destination); return dest;
		 */
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		String time = Long.toString(timestamp.getTime());
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// File Dest = new
		// File(System.getProperty("user.dir")+"//target//Screenshots1//"+screenShotName.concat(time)+".png");
		// File Dest = new
		// File(".//target//Screenshots//"+screenShotName.concat(time)+".png");

		File Dest = new File(
				System.getProperty("user.dir") + "//target//Screenshots//" + screenShotName.concat(time) + ".png");
		// C:\QA_BDD_Automation\May
		// Latest\SLC_BDDAutomationFramework\target\Screenshots\screenshotvoidloanBTL

		FileUtils.copyFile(scrFile, Dest);
		// ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
		// BufferedImage img=ImageIO.read(Dest);
		// ImageIO.write(img, "png", baos);

		// byte[] base64String=Base64.encodeBase64(baos.toByteArray());
		// System.out.println(base64String.toString());
		// baos.flush();
		// baos.close();
		// return base64String.toString();
		// String errflpath = Dest.getCanonicalPath();
		// FileUtils.copyFile(scrFile, Dest);
		return Dest.getCanonicalPath();
	}

	// Created : Shivam Sharma

	public synchronized String capture(WebDriver driver, String screenShotName) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String time = Long.toString(timestamp.getTime());
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File("././Screenshots/" + screenShotName.concat(time) + ".png");
		try {
			FileUtils.copyFile(scrFile, Dest);
			FileUtils.copyDirectory(new File("./Screenshots"), new File("./target/Screenshots/"));
		} catch (IOException e) {
			log.info(e.getMessage());
		}

		return Dest.toPath().toString();
	}

	public synchronized String capture() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String time = Long.toString(timestamp.getTime());
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File("././Screenshots/" + time + ".png");
		try {
			FileUtils.copyFile(scrFile, Dest);
			FileUtils.copyDirectory(new File("./Screenshots"), new File("./target/Screenshots/"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Dest.toPath().toString();
	}

	/*
	 * public String captureFullScreenShot(WebDriver driver, String screenShotName)
	 * {
	 * 
	 * String canPath = ""; Calendar calendar = Calendar.getInstance(); String date
	 * = calendar.getTime().toString(); String formattedDate = date.replace(" ",
	 * "_").replace(":", "_"); Screenshot screenshot = new
	 * AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
	 * .takeScreenshot(driver);
	 * 
	 * File dest = new File(System.getProperty("user.dir") +
	 * "\\target\\Screenshots1\\" + screenShotName.concat("_" + formattedDate) +
	 * ".png"); try { ImageIO.write(screenshot.getImage(), "PNG", dest); } catch
	 * (IOException e) { }
	 * 
	 * try { canPath = dest.getCanonicalPath(); } catch (IOException e) { } return
	 * canPath; }
	 */

	public String captureFullScreenShot(WebDriver driver, String screenShotName) {

		String canPath = "";
		Calendar calendar = Calendar.getInstance();
		String date = calendar.getTime().toString();
		String formattedDate = date.replace(" ", "_").replace(":", "_");
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(driver);

		File dest = new File("././Screenshots/" + screenShotName.concat("_" + formattedDate) + ".png");
		try {
			ImageIO.write(screenshot.getImage(), "PNG", dest);
			FileUtils.copyDirectory(new File("./Screenshots"), new File("./target/Screenshots/"));
		} catch (IOException e) {
		}

		canPath = dest.getPath().toString();

		return canPath;
	}

}
