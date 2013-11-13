package com.tls.liferaylms.test.unit;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tls.liferaylms.test.SeleniumTestCase;

public class LoginTest extends SeleniumTestCase  {
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Test
  public void testLogin() throws Exception {
    driver.get(baseUrl + "/");
    
    driver.findElement(By.id("_58_login")).clear();
    driver.findElement(By.id("_58_login")).sendKeys("test@liferay.com");
    driver.findElement(By.id("_58_password")).clear();
    driver.findElement(By.id("_58_password")).sendKeys("test");
    driver.findElement(By.className("aui-button-input-submit")).click();
    WebElement myDynamicElement = (new WebDriverWait(driver, 10))
    		  .until(ExpectedConditions.presenceOfElementLocated(By.id("footer")));
    driver.findElement(By.linkText("Curso privado")).click();
    myDynamicElement = (new WebDriverWait(driver, 10))
  		  .until(ExpectedConditions.presenceOfElementLocated(By.id("footer")));
   
  
    	assertTrue("Acceso a curso",myDynamicElement!=null);
    	assertTrue("estado",false);
    	   driver.get(baseUrl + "/c/portal/logout");
  	  
  }
  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
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
}
