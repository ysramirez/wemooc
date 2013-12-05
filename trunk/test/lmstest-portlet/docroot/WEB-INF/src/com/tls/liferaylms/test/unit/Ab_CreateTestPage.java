package com.tls.liferaylms.test.unit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.tls.liferaylms.test.SeleniumTestCase;
import com.tls.liferaylms.test.util.CheckPage;
import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.GetPage;
import com.tls.liferaylms.test.util.Login;
import com.tls.liferaylms.test.util.Sleep;

public class Ab_CreateTestPage extends SeleniumTestCase {

	@Test
	public void testCreatePage() throws Exception {
		if(getLog().isInfoEnabled())getLog().info("init");

		Login login = new Login(driver, Context.getUser(), Context.getPass(), Context.getBaseUrl());
		boolean loged = login.isLogin();
		assertTrue("Error not logued",loged);
		if (loged) {
			if(getLog().isInfoEnabled())getLog().info("Add page...");
			WebElement menu = getElement(By.id("_145_addContent"));
			assertNotNull("Not Admin Menu", menu);
			
			menu.click();
			WebElement addPage = getElement(By.id("addPage"));
			
			if(getLog().isInfoEnabled())getLog().info("addPage::"+addPage);
			assertNotNull("Not Add page Menu"+addPage, addPage);
			addPage.click();
			
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
}
