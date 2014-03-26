package com.tls.liferaylms.test.util;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CourseActivityMenu {
	
	public static WebElement findElementActivityMenu(WebDriver driver,String param){
		WebElement desplegable = null;
		
		try{
			desplegable = driver.findElement(By.className("lms-desplegable"));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		List<WebElement> lisc = null;

		try{
			lisc = desplegable.findElements(By.tagName("li"));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		WebElement liActive = null;
		for(WebElement li :lisc){
			String msg = li.getText();
			if(param.length()<msg.length()&&param.equals(msg.substring(0, param.length()))){
				liActive = li;
				break;
			}
		}
		
		return liActive;
	}
	
	public static WebElement findElementActivityMenuTotal(WebDriver driver,String param){
		WebElement desplegable = null;
		
		try{
			desplegable = driver.findElement(By.className("lms-desplegable"));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		List<WebElement> lisc = null;

		try{
			lisc = desplegable.findElements(By.tagName("li"));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		WebElement liActive = null;
		for(WebElement li :lisc){
			String msg = li.getText();
			if(msg.length()>param.length()){
				msg = msg.substring(0,param.length());
			}
			System.out.println(param+"--"+msg+"::"+param.equals(msg));
			if(param.equals(msg)){
				liActive = li;
				break;
			}
		}

		WebElement a = null;
		if(liActive!=null){
			try{
				a = liActive.findElement(By.tagName("a"));
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		return a;
	}
	public static WebElement findElementActivityMenuEdit(WebDriver driver,String param){
		WebElement desplegable = null;
		
		try{
			desplegable = driver.findElement(By.className("lms-desplegable"));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		List<WebElement> lisc = null;

		try{
			lisc = desplegable.findElements(By.tagName("li"));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		WebElement liActive = null;
		for(WebElement li :lisc){
			String msg = li.getText();
			if(param.equals(msg)){
				liActive = li;
				break;
			}
		}

		List<WebElement> as = null;
		if(liActive!=null){
			try{
				as = liActive.findElements(By.tagName("a"));
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		
		if(as!=null&&as.size()>2){
			return as.get(1);
		}else{
			return null;
		}
	}
}
