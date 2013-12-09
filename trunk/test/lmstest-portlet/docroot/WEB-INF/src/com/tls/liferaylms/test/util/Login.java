package com.tls.liferaylms.test.util;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

public class Login {
	private static Log log = LogFactoryUtil.getLog(Login.class);
	private static String b = Keys.BACK_SPACE.toString();
	
	WebDriver driver = null;
	String user = null;
	String pass = null;
	String path = null;

	public Login(WebDriver driver,String user,String pass,String path) {
		super();
		this.driver = driver;
		this.user = user;
		this.pass = pass;
		this.path = path;
	}

	public boolean login(){
		try{
			GetPage.getPage(driver,path,"");
			WebElement singin =getElement(By.id("sign-in"));
			if(singin!=null) {
				singin.click();
			}else{
				GetPage.getPage(driver,path,"/c/portal/login");
			}
			Sleep.sleep(1000);
			WebElement login = getElement(By.id("_58_login"));
			if(login==null){
				return false;
			}else{
				login.sendKeys(Keys.END);
				for(int i=0;i<40;i++){
					login.sendKeys(Keys.BACK_SPACE);
				}
				login.sendKeys(user);
				driver.findElement(By.id("_58_password")).sendKeys(pass);
				driver.findElement(By.id("_58_fm")).submit();
				Sleep.sleep(2000);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		WebElement webElement = null;
		try{
			webElement = driver.findElement(By.className("sign-out"));
		}catch(Exception e){
			
		}
		if(webElement==null){
			if(log.isInfoEnabled())log.info("login:false");
			return false;
		}else{
			if(log.isInfoEnabled())log.info("login:true");
			return true;
		}
	}
	
	public boolean isLogin(){
		WebElement span = getElement(By.id("p_145"));
		if(span==null){
			return false;
		}else{
			return true;
		}
			
	}

	public boolean logout(){
		WebElement we = getElement(By.className("sign-out"));
		if(we!=null){
			we.click();
			if(isLogin()){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}
	
	private WebElement getElement(By by){
		try {
			return driver.findElement(by);
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
