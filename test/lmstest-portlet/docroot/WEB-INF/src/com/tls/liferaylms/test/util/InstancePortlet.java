package com.tls.liferaylms.test.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

public class InstancePortlet {
	private static Log log = LogFactoryUtil.getLog(InstancePortlet.class);
	
	public static boolean createInstance(WebDriver driver, String text, String identifier){
		if(isInstance(driver, text, identifier)){
			if(log.isInfoEnabled())log.info("Instance true");
			return true;
		}else{
			if(log.isInfoEnabled())log.info("Instance portlet::"+identifier);
			
			WebElement menu = getElement(driver,By.id("_145_addContent"));
			if(log.isInfoEnabled())log.info("_145_addContent::"+menu);
			if(menu==null)
				return false;
			
			menu.click();
			menu.click();
			
			WebElement addApp = getElement(driver,By.id("_145_addApplication"));
			if(log.isInfoEnabled())log.info("_145_addApplication::"+addApp);
			if(addApp==null)
				return false;
			
			addApp.click();

			Sleep.sleep(2000);
			WebElement textContent = getElement(driver,By.id("layout_configuration_content"));
			if(log.isInfoEnabled())log.info("layout_configuration_content::"+textContent);
			if(textContent==null)
				return false;
			
			textContent.sendKeys(text);
			
			WebElement fm = getElement(driver,By.id("fm"));
			if(log.isInfoEnabled())log.info("fm::"+fm);
			if(fm==null)
				return false;
			
			List<WebElement> elements = getElements(fm, By.tagName("div"));
			if(log.isInfoEnabled())log.info("div::"+elements.size());
			if(elements==null)
				return false;
			
			for(WebElement ele : elements){
				if(ele.getAttribute("title").toLowerCase().equals(text)){
					WebElement a = getElement(ele,By.tagName("a"));
					if(log.isInfoEnabled())log.info("a::"+a);
					if(a==null)
						return false;
					
					a.click();
				}
			}
			

			Sleep.sleep(2000);
			WebElement portlet = getElement(driver,By.id(identifier));
			if(portlet!=null){
				return true;
			}else{
				return false;
			}
		}
	}
	
	/*public static boolean destroyInstance(WebDriver driver, String text, String identifier){
		
	}*/
	
	public static boolean isInstance(WebDriver driver, String text, String identifier){
		WebElement portlet = null;
		try{
			portlet = driver.findElement(By.id(identifier));
			if(portlet!=null){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	
	public static WebElement getElement(WebDriver driver,By by){
		try {
			return driver.findElement(by);
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public static WebElement getElement(WebElement we,By by){
		try {
			return we.findElement(by);
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public static List<WebElement> getElements(WebElement we,By by){
		try {
			return we.findElements(by);
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
