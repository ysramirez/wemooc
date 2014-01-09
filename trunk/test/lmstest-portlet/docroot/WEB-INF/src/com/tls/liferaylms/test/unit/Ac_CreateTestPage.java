package com.tls.liferaylms.test.unit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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

/**
 * @author Diego Renedo Delgado
 */
public class Ac_CreateTestPage extends SeleniumTestCase {

	@Test
	public void testCreatePage() throws Exception {
		if(getLog().isInfoEnabled())getLog().info("init");
		try{
			Sleep.sleep(2000);
			GetPage.getPage(driver, Context.getBaseUrl(), "/");
			
			WebElement navigationPre = getElement(By.id("navigation"));
			List<WebElement> ahrefsPre = getElements(navigationPre, By.tagName("a"));
			for(WebElement a : ahrefsPre){
				if(getLog().isInfoEnabled())getLog().info("Check::"+a.getText());
				if(a.getText().equals("test")){
					Context.setTestPage(a.getAttribute("href"));
					break;
				}
			}
			if(!Context.getTestPage().isEmpty()){
				GetPage.getPage(driver, "", Context.getTestPage());
				assertEquals("Errors in page",0,CheckPage.checkForErrors(driver));
				Context.setTest(true);
				return;
			}
			
			Login login = new Login(driver, Context.getUser(), Context.getPass(), Context.getBaseUrl());
			boolean loged = login.isLogin();
			assertTrue("Error not logued",loged);
			if (loged) {
				if(getLog().isInfoEnabled())getLog().info("Add page...");
				WebElement menu = getElement(By.id("_145_addContent"));
				assertNotNull("Not Admin Menu", menu);

				menu.click();
				menu.click();
				WebElement addPage = getElement(By.id("addPage"));
				
				if(getLog().isInfoEnabled())getLog().info("addPage::"+addPage);
				assertNotNull("Not Add page Menu"+addPage, addPage);
				addPage.click();
				
				WebElement navigation = getElement(By.id("navigation"));
				assertNotNull("Not Found navigation", navigation);
				
				WebElement addPageTextLayout = getElement(navigation,By.className("add-page"));
				
				if(addPageTextLayout==null){
					List<WebElement> ahrefs = getElements(navigation, By.tagName("a"));
					List<String> aNames = new ArrayList<String>();
					for(WebElement a : ahrefs){
						if(getLog().isInfoEnabled())getLog().info("href::"+a.getAttribute("href"));
						aNames.add(a.getAttribute("href"));
					}
					for(String a : aNames){
						GetPage.getPage(driver, "", a);
						Sleep.sleep(3000);
						addPageTextLayout = getAddPageElement();
						if(addPageTextLayout!=null){
							break;
						}
					}
				}
				
				assertNotNull("Not Found addPageText", addPageTextLayout);
				
				WebElement addPageText = getElement(addPageTextLayout,By.tagName("input"));
				
				addPageText.sendKeys("test");
				addPageText.sendKeys(Keys.RETURN);
	
				Sleep.sleep(2000);
				
				GetPage.getPage(driver, "", Context.getPage());
				
				navigation = getElement(By.id("navigation"));
				List<WebElement> ahrefs = getElements(navigation, By.tagName("a"));
				for(WebElement a : ahrefs){
					if(getLog().isInfoEnabled())getLog().info("Check::"+a.getText());
					if(a.getText().equals("test")){
						Context.setTestPage(a.getAttribute("href"));
						break;
					}
				}
				
				GetPage.getPage(driver, "", Context.getTestPage());
				
				assertEquals("Errors in page",0,CheckPage.checkForErrors(driver));
				
				Context.setTest(true);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(getLog().isInfoEnabled())getLog().info("end ok");
	}
	
	private WebElement getAddPageElement(){
		WebElement menu = getElement(By.id("_145_addContent"));
		assertNotNull("Not Admin Menu", menu);
		
		menu.click();
		menu.click();
		WebElement addPage = getElement(By.id("addPage"));
		
		if(getLog().isInfoEnabled())getLog().info("addPage::"+addPage);
		assertNotNull("Not Add page Menu"+addPage, addPage);
		if(getLog().isInfoEnabled())getLog().info("clicking::");
		addPage.click();
		if(getLog().isInfoEnabled())getLog().info("clicked::");
		
		WebElement navigation = getElement(By.id("navigation"));
		assertNotNull("Not Found navigation", navigation);
		
		WebElement addPageTextLayout = getElement(navigation,By.className("add-page"));
		
		if(getLog().isInfoEnabled())getLog().info("returnPage::"+addPageTextLayout);
		return addPageTextLayout;
	}
}
