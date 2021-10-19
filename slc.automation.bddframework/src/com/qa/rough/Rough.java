package com.qa.rough;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

public class Rough  {

	public static void main(String[] args)   {
		
		
		SoftAssert a = new SoftAssert();
		
		
		a.assertEquals(true, false);
		a.assertEquals(53, 5);
		a.assertAll();
		
		System.out.println("Hello");
		
		
		
		
		
	}
}
