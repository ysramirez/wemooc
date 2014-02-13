package com.tls.liferaylms.test.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckCourse {
	
	public static boolean checkCourse(WebDriver driver,String url){
		GetPage.getPage(driver, url, "");
		CheckPage.check(driver);
		
		try{
			GetPage.getPage(driver, url, "/reto");
			
			Sleep.sleep(3000);
			
			WebElement portlet = driver.findElement(By.id("p_p_id_changeEditingMode_WAR_liferaylmsportlet_"));
			WebElement configMenu = portlet.findElement(By.className("portlet-title-default"));
			
			try{
				configMenu.click();
				try{
					configMenu.click();
				}catch(Exception e){}
			}catch(Exception e){
				WebElement menuEdit = driver.findElement(By.id("_145_toggleControls"));
				WebElement a = menuEdit.findElement(By.tagName("a"));
				a.click();
				if(!configMenu.isDisplayed()){
					a.click();
				}
				configMenu.click();
				try{
					configMenu.click();
				}catch(Exception e1){}
			}
			
			Sleep.sleep(1000);
			
			WebElement config = driver.findElement(By.id("_changeEditingMode_WAR_liferaylmsportlet_hjzj_menu"));
			config.click();
			
			WebElement configBut1 = driver.findElement(By.id("_changeEditingMode_WAR_liferaylmsportlet_hjzj_menuButton"));
			configBut1.click();
	
			WebElement configBut2 = driver.findElement(By.id("_changeEditingMode_WAR_liferaylmsportlet_hjzj_menu_configuration"));
			configBut2.click();
			
			Sleep.sleep(2000);
			
			driver.switchTo().frame(0);
	
			WebElement action = driver.findElement(By.id("courseteacher_ACTION_VIEW"));
			action.click();

			action = driver.findElement(By.id("site-member_ACTION_VIEW"));
			action.click();
			
			WebElement submit = driver.findElement(By.className("aui-button-input-submit"));
			submit.click();
 
			GetPage.getPage(driver, url, "/reto");
			
			CheckPage.check(driver);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
