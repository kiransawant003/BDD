package com.qa.rough;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.TestAutomation.StepDef.BrowserDriverManager;

public class Rough2 {

	
	  public static void main(String[] args) {
		  

	  
	  System.setProperty("webdriver.chrome.driver",
	  "C:\\Users\\ssharma9\\Documents\\SLC\\Automation\\slc.automation.bddframework\\slc.automation.bddframework\\driver\\Chrome_v85\\chromedriver.exe"
	  );
	  
	  WebDriver driver = new ChromeDriver(); driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  
	  driver.get("https://www.google.com");
	  
	  WebElement e = driver.findElement(By.linkText("Sign in"));
	  
	  System.out.println("==" + e.getSize());
	  
	  int l1 = e.getSize().getHeight(); int l2 = e.getSize().getWidth();
	  
	  Actions a = new Actions(driver);
	  
	  
	  Point p = e.getLocation();
	  
	  a.moveToElement(e, p.x, p.y).click().build().perform();
	  
	  System.out.println("Clicked successfully");
	  
	  }
	 
	
	

}
