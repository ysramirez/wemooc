package com.tls.liferaylms.test.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckPage {
	private static Log log = LogFactoryUtil.getLog(CheckPage.class);

	private static Set<String> images = new HashSet<String>();
	
	public static int checkForErrors(WebDriver driver){
		List<WebElement> errors = driver.findElements(By.className("portlet-msg-error"));
		return errors.size();
	}
	
	public static int check(WebDriver driver){
		int errorNum = 0;
		List<WebElement> errors = driver.findElements(By.className("portlet-msg-error"));
		if(errors!=null && errors.size()>0){
			for(WebElement we : errors){
				if(we.getText().matches(".*No encontrado.*")){
					if(log.isErrorEnabled())log.error("------------------------------------------------------------------Page not available!");
					errorNum++;
				}
			}
		}
		
		List<WebElement> infos = driver.findElements(By.className("portlet-msg-info"));
		if(infos!=null && infos.size()>0){
			for(WebElement we : infos){
				if(we.getText().matches(".*desinstalado.*")){
					if(log.isErrorEnabled())log.error("------------------------------------------------------------------Portlet not available!");
					errorNum++;
			    }
			}
		}

		infos = driver.findElements(By.className("portlet-msg-info"));
		if(infos!=null && infos.size()>0){
			for(WebElement we : infos){
				if(!we.getText().isEmpty())
					if(log.isInfoEnabled())log.info("Info::"+we.getText());
			}
		}
		errors = driver.findElements(By.className("portlet-msg-error"));
		if(errors!=null && errors.size()>0){
			for(WebElement we : errors){
				if(!we.getText().isEmpty())
					if(log.isErrorEnabled())log.error("Error::"+we.getText());
			}
		}
		
		if(log.isInfoEnabled())log.info("------------------------------------------------------------------------------------------------------------");
		return errorNum;
	}
}

