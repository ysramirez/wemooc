package com.tls.liferaylms.test;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTestCase {
	 protected WebDriver driver;
	 protected String baseUrl;
	 
	@Before
	  public void setUp() throws Exception {
	    driver = SeleniumDriverUtil.getDriver();
	    baseUrl = "http://localhost:8080/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }
	@After
	  public void tearDown() throws Exception {
	  }

}
