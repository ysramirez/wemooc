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
			
			WebElement portlet = driver.findElement(By.id("p_p_id_changeEditingMode_WAR_liferaylmsportlet_"));
			WebElement configMenu = portlet.findElement(By.className("portlet-title-default"));
			
			configMenu.click();
			try{
				configMenu.click();
			}catch(Exception e){}
			
			Sleep.sleep(2000);
			
			WebElement configBut1 = driver.findElement(By.id("_changeEditingMode_WAR_liferaylmsportlet_hjzj_menuButton"));
			configBut1.click();
	
			WebElement configBut2 = driver.findElement(By.id("_changeEditingMode_WAR_liferaylmsportlet_hjzj_menu_configuration"));
			configBut2.click();
			
			Sleep.sleep(2000);
			
			driver.switchTo().frame(0);
	
			WebElement action = driver.findElement(By.id("courseteacher_ACTION_VIEW"));
			action.click();
			
			WebElement submit = driver.findElement(By.className("aui-button-input-submit"));
			submit.click();
			
			CheckPage.check(driver);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
