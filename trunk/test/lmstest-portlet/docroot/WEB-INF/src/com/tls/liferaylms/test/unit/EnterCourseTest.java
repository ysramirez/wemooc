package com.tls.liferaylms.test.unit;

import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

import com.tls.liferaylms.test.SeleniumTestCase;
import com.tls.liferaylms.test.util.CheckPage;
import com.tls.liferaylms.test.util.GetPage;
import com.tls.liferaylms.test.util.Login;
import com.tls.liferaylms.test.util.Sleep;

public class EnterCourseTest extends SeleniumTestCase {
 private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Test
  public void testLogin() throws Exception {
	  if(getLog().isInfoEnabled())getLog().info("init");

		Login login = new Login(driver, "test@liferay.com", "test", baseUrl);
		boolean loged = login.isLogin();
		assertTrue("Error not logued",loged);
		if (loged) {
			if(getLog().isInfoEnabled())getLog().info("menu-button");
			WebElement menu = getElement(By.className("menu-button"));
			assertNotNull("Not Admin Menu", menu);
			
			menu.click();
			WebElement addApp = getElement(By.id("add-application"));
			assertNotNull("Not Add page Menu", addApp);
			addApp.click();
			
			WebElement navigation = getElement(By.id("navigation"));
			assertNotNull("Not Found navigation", navigation);
			
			WebElement addPageTextLayout = getElement(navigation,By.className("add-page"));
			assertNotNull("Not Found addPageText", addPageTextLayout);
			
			WebElement addPageText = getElement(addPageTextLayout,By.tagName("input"));
			
			addPageText.sendKeys("test");
			addPageText.sendKeys(Keys.RETURN);
			Sleep.sleep(3000);
			
			GetPage.getPage(driver, baseUrl, "/test");
			
			assertEquals("Errors in page",0,CheckPage.checkForErrors(driver));
		}
		if(getLog().isInfoEnabled())getLog().info("end ok");
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
