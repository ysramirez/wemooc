package com.tls.liferaylms.test;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.tls.liferaylms.test.util.Context;

public class SeleniumTestCase {
	protected WebDriver driver;
	protected String baseUrl;
	private Log log = null;
	private boolean acceptNextAlert = true;

	@Before
	public void setUp() throws Exception {
		driver = SeleniumDriverUtil.getDriver();
		baseUrl = Context.getBaseUrl();
		
		
	}

	@After
	public void tearDown() throws Exception {

	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public WebElement getElement(By by){
		try {
			return driver.findElement(by);
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	public WebElement getElement(WebElement we,By by){
		try {
			return we.findElement(by);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
	
	public Log getLog(){
		if(log==null){
			log = LogFactoryUtil.getLog(this.getClass());
		}
		return log;
	}
}
