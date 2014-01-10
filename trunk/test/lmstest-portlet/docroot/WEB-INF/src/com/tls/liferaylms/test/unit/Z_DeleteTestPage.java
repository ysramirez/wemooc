package com.tls.liferaylms.test.unit;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tls.liferaylms.test.SeleniumTestCase;
import com.tls.liferaylms.test.util.CheckPage;
import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.GetPage;
import com.tls.liferaylms.test.util.Login;
import com.tls.liferaylms.test.util.Sleep;

/**
 * @author Diego Renedo Delgado
 */
public class Z_DeleteTestPage extends SeleniumTestCase {

	  @Test
	  public void testDeletePage() throws Exception {
		  try{
			if(getLog().isInfoEnabled())getLog().info("init");
			Login login = new Login(driver, Context.getUser(), Context.getPass(), Context.getBaseUrl());
			boolean loged = login.isLogin();
			assertTrue("Error not logued",loged);
			if (loged) {
				GetPage.getPage(driver, "", Context.getTestPage());
				Sleep.sleep(1000);
				
				if(getLog().isInfoEnabled())getLog().info("Delete page...");
				WebElement menu = getElement(By.id("_145_manageContent"));
				assertNotNull("Not Admin Menu", menu);

				menu.click();
				menu.click();
				WebElement managePage = getElement(By.id("_145_manageContentContainer"));
				assertNotNull("Not manage page Menu", managePage);
				WebElement aHref = getElement(managePage, By.tagName("a"));
				aHref.click();

				Sleep.sleep(3000);

				if(getLog().isInfoEnabled())getLog().info("Switch frame");
				driver.switchTo().frame(0);

				if(getLog().isInfoEnabled())getLog().info("Try delete");
				WebElement iconDelete = getElement(By.className("aui-icon-delete"));
				assertNotNull("Not delete page Menu", iconDelete);
				iconDelete.click();
				
				Alert confirm =driver.switchTo().alert();
				confirm.accept();
				
				GetPage.getPage(driver, "", Context.getTestPage());
				assertNotEquals("Errors in page",0,CheckPage.checkForErrors(driver));
			}
		  }catch(Exception e){
			  e.printStackTrace();
		  }
			
			
			if(getLog().isInfoEnabled())getLog().info("end ok");
	  }
}
