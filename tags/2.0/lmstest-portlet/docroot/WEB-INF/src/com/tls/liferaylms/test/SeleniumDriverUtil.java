package com.tls.liferaylms.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumDriverUtil 
{
	 private static WebDriver driver;
	 public static WebDriver  getDriver() throws Exception 
	 {
		 if(driver==null)
		 {
		    driver = new FirefoxDriver();
		 }
		 return driver;
	 }
	 public static void closeDriver()
	 {
		 driver.quit();
		 driver=null;
	 }
}
