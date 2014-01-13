package com.tls.liferaylms.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.swing.event.ChangeEvent;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.tls.liferaylms.test.SeleniumTestCase;
import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.CourseActivityMenu;
import com.tls.liferaylms.test.util.GetPage;
import com.tls.liferaylms.test.util.Login;
import com.tls.liferaylms.test.util.Sleep;
import com.tls.liferaylms.test.util.TestProperties;

/**
 * @author Diego Renedo Delgado
 */
public class Bf_CheckActivityRestrictions extends SeleniumTestCase {
	String test = TestProperties.get("act.test");
	String scorm = TestProperties.get("act.scorm");

	@Test
	public void CheckRestrictions(){
		Login login = new Login(driver, Context.getTeacherUser(), Context.getTeacherPass(), Context.getBaseUrl());
		
		if(login.isLogin())
			login.logout();
		
		boolean teacherLogin = login.login();
		assertTrue("Error login teacher",teacherLogin);

		if(teacherLogin){
			GetPage.getPage(driver, Context.getCoursePage(), "/reto");
			
			changeEditMode();
			
			Sleep.sleep(2000);
			for(String id : Context.getActivities().keySet()){
				if(id.length()>test.length()&&id.substring(0, test.length()).equals(test)){
					  WebElement a = CourseActivityMenu.findElementActivityMenuEdit(driver,id);
					  assertNotNull("Edit link for Test not found", a);

					  a.click();
					  Sleep.sleep(2000);
						
					  driver.switchTo().frame(0);
					  
					  openColapsables();
					  
					  WebElement checkStart = getElement(By.id("_lmsactivitieslist_WAR_liferaylmsportlet_startdate-enabledCheckbox"));
					  											
					  checkStart.click();
					  
					  WebElement startYear = getElement(By.id("_lmsactivitieslist_WAR_liferaylmsportlet_startYear"));
					  startYear.sendKeys("2019");
					  
					  WebElement submit = getElement(By.className("aui-button-input-submit"));
					  assertNotNull("Not submit found", submit);
					  //Doubleclick
					  try{
						  submit.click();
						  submit.click();
					  }catch(Exception e){}
				}
			}
			GetPage.getPage(driver, Context.getCoursePage(), "/reto");

			changeEditMode();

			Sleep.sleep(2000);
			
			WebElement newactivity = getElement(By.className("newactivity"));
			assertNotNull("Not newactivity button", newactivity);

			WebElement aNew = getElement(newactivity,By.tagName("a"));
			assertNotNull("Not aNewnewactivity button", aNew);
			aNew.click();

			Sleep.sleep(2000);
			
			driver.switchTo().frame(0);
			

			WebElement activityList = getElement(By.className("activity-list"));
			assertNotNull("Not Activity list find", activityList);

			List<WebElement> lis = getElements(activityList, By.tagName("li"));
			
			WebElement a = getElement(lis.get(1),By.tagName("a"));
			a.click();

			Sleep.sleep(2000);
			
			WebElement titleAct = getElement(By.id("_lmsactivitieslist_WAR_liferaylmsportlet_title_es_ES"));
			assertNotNull("Title activity not find", titleAct);
			
			titleAct.sendKeys(TestProperties.get("act.test.pre")+" "+Context.getCourseId());
			sendCkEditorJS(driver,"act.test.pre");
			
			WebElement form = getElement(By.id("_lmsactivitieslist_WAR_liferaylmsportlet_fm"));
			assertNotNull("Not form activity found", form);
			form.submit();
			
			GetPage.getPage(driver, Context.getCoursePage(), "/reto");
			
			changeEditMode();

			for(String id : Context.getActivities().keySet()){
				if(id.length()>scorm.length()&&id.substring(0, scorm.length()).equals(scorm)){
					a = CourseActivityMenu.findElementActivityMenuEdit(driver,id);
					assertNotNull("Edit link for Test not found", a);

					a.click();
					Sleep.sleep(2000);
						
					driver.switchTo().frame(0);
					  
					openColapsables();
					
					WebElement precedence = getElement(By.id("_lmsactivitieslist_WAR_liferaylmsportlet_precedence"));
					assertNotNull("Not precedence combo found", precedence);
					
					precedence.sendKeys(TestProperties.get("act.test.pre"));

					WebElement submit = getElement(By.className("aui-button-input-submit"));
					assertNotNull("Not submit found", submit);
					//Doubleclick
					try{
						submit.click();
						submit.click();
					}catch(Exception e){}
					
					break;
				}
			}
		}
		
		GetPage.getPage(driver, Context.getCoursePage(), "/reto");
		
		Sleep.sleep(2000);
		
		login = new Login(driver, Context.getStudentUser(), Context.getStudentPass(), Context.getBaseUrl());
		
		if(login.isLogin()){
			System.out.println("logout");
			login.logout();
		}
		
		boolean studentLogin = login.login();
		assertTrue("Error login student",studentLogin);

		if(studentLogin){
			GetPage.getPage(driver, Context.getCoursePage(), "/reto");
			

			for(String id : Context.getActivities().keySet()){
				if(id.length()>scorm.length()&&id.substring(0, scorm.length()).equals(scorm)){
					WebElement a = CourseActivityMenu.findElementActivityMenuTotal(driver,id);
					assertNull("SCORM Activity should not be available", a);
				}else if(id.length()>test.length()&&id.substring(0, test.length()).equals(test)){
					WebElement a = CourseActivityMenu.findElementActivityMenuTotal(driver,id);
					assertNull("Test Activity should not be available", a);
				}
			}
			
			WebElement a = CourseActivityMenu.findElementActivityMenuTotal(driver,TestProperties.get("act.test.pre"));
			assertNotNull("Pre Activity not found", a);
			a.click();

			Sleep.sleep(2000);
			
			GetPage.getPage(driver, Context.getCoursePage(), "/reto");
			

			for(String id : Context.getActivities().keySet()){
				if(id.length()>scorm.length()&&id.substring(0, scorm.length()).equals(scorm)){
					a = CourseActivityMenu.findElementActivityMenuTotal(driver,id);
					assertNotNull("SCORM Activity should be available", a);
				}
			}
		}
	}
	
	private void changeEditMode(){
		WebElement editPortlet = getElement(By.id("p_p_id_changeEditingMode_WAR_liferaylmsportlet_"));
		assertNotNull("Not Edit portlet found", editPortlet);
		
		List<WebElement> inputs = getElements(editPortlet,By.tagName("input"));
		assertEquals("Not Edit portlet found", 1,inputs.size());
		inputs.get(0).click();
	}
	
	private void openColapsables(){
		List<WebElement> pcontainer = getElements(By.className("lfr-panel-titlebar"));
		assertNotNull("Not found pcontainer", pcontainer);
		
		for(WebElement we : pcontainer){
			if(getLog().isInfoEnabled())getLog().info("Click::"+we.getText());
			try{
				we.click();
			}catch(Exception e){}
			List<WebElement> spans = getElements(we,By.tagName("span"));
			if(spans!=null){
				for(WebElement span : spans){
					try{
						span.click();
					}catch(Exception e){}
				}
			}
			List<WebElement> divs = getElements(we,By.tagName("div"));
			if(spans!=null){
				for(WebElement div : divs){
					try{
						div.click();
					}catch(Exception e){}
				}
			}
			Sleep.sleep(1000);
		}
	}

	private void sendCkEditorJS(WebDriver driver,String prop){
		if (driver instanceof JavascriptExecutor) {
			StringBuilder sb = new StringBuilder("javascript:CKEDITOR.instances['_lmsactivitieslist_WAR_liferaylmsportlet_description'].setData('<p>");
			sb.append(TestProperties.get(prop));
			sb.append(" ");
			sb.append(Context.getCourseId());
			sb.append("</p>');");
		    ((JavascriptExecutor)driver).executeScript(sb.toString());
		}
	}
}
