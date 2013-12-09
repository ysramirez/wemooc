package com.tls.liferaylms.test.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckPage {
	private static Log log = LogFactoryUtil.getLog(CheckPage.class);

	private static Set<String> images = new HashSet<String>();
	
	public static int checkForErrors(WebDriver driver){
		List<WebElement> errors = driver.findElements(By.className("portlet-msg-error"));
		return errors.size();
	}
	
	public static void check(WebDriver driver){
		String source = driver.getPageSource();
		
		Matcher matcher = null;
		Pattern pattern = null;
		List<WebElement> errors = driver.findElements(By.className("portlet-msg-error"));
		if(errors!=null && errors.size()>0){
			for(WebElement we : errors){
				if(we.getText().matches(".*No encontrado.*")){
					if(log.isErrorEnabled())log.error("------------------------------------------------------------------Page not available!");
					return;
				}
			}
		}
		
		List<WebElement> infos = driver.findElements(By.className("portlet-msg-info"));
		if(infos!=null && infos.size()>0){
			for(WebElement we : infos){
				if(we.getText().matches(".*desinstalado.*")){
					if(log.isErrorEnabled())log.error("------------------------------------------------------------------Portlet not available!");
					return;
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
		
	}
}

