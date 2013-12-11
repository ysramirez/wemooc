package com.tls.liferaylms.test.unit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.tls.liferaylms.test.SeleniumTestCase;
import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.GetPage;
import com.tls.liferaylms.test.util.Login;
import com.tls.liferaylms.test.util.Sleep;

public class Bc_CreateActivity extends SeleniumTestCase {

	@Test
	public void createActivity() throws Exception {
		Login login = new Login(driver, Context.getTeacherUser(), Context.getTeacherPass(), Context.getBaseUrl());
		
		if(login.isLogin())
			login.logout();
		
		boolean teacherLogin = login.login();
		assertTrue("Error login teacher",teacherLogin);
		
		if(teacherLogin){
			try{
				GetPage.getPage(driver, Context.getCoursePage(), "/reto");
				
				WebElement editPortlet = getElement(By.id("p_p_id_changeEditingMode_WAR_liferaylmsportlet_"));
				assertNotNull("Not Edit portlet found", editPortlet);
				
				List<WebElement> inputs = getElements(editPortlet,By.tagName("input"));
				assertEquals("Not Edit portlet found", 1,inputs.size());
				inputs.get(0).click();
				
				Sleep.sleep(2000);
	
				WebElement activityPortlet = getElement(By.id("p_p_id_moduleportlet_WAR_liferaylmsportlet_"));
				assertNotNull("Not Activity portlet found", activityPortlet);
				
				if (driver instanceof JavascriptExecutor) {
				    ((JavascriptExecutor)driver).executeScript("javascript:_moduleportlet_WAR_liferaylmsportlet_openPopup();");
				}
	
				driver.switchTo().frame(0);
				
				WebElement title = getElement(By.id("_moduleportlet_WAR_liferaylmsportlet_title_es_ES"));
				assertNotNull("Not Activity title found", activityPortlet);
				title.sendKeys("Module "+Context.getCourseId());
	
				WebElement form = getElement(By.id("_moduleportlet_WAR_liferaylmsportlet_addmodule"));
				assertNotNull("Not form activity found", form);
				form.submit();

				Sleep.sleep(2000);

				driver.switchTo().activeElement();

				
				WebElement closethick = getElement(By.className("aui-button-input-cancel"));
				assertNotNull("Not close popoup", closethick);
				closethick.click();

				GetPage.getPage(driver, Context.getCoursePage(), "/reto");
				
				editPortlet = getElement(By.id("p_p_id_changeEditingMode_WAR_liferaylmsportlet_"));
				assertNotNull("Not Edit portlet found", editPortlet);
				
				inputs = getElements(editPortlet,By.tagName("input"));
				assertEquals("Not Edit portlet found", 1,inputs.size());
				inputs.get(0).click();
				
				Sleep.sleep(2000);

				System.out.println(driver.getPageSource());
				
				//Add activities
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
				
				assertTrue("Poor activities... ",lis.size()>6);
				
				GetPage.getPage(driver, Context.getCoursePage(), "/reto");
				
				editPortlet = getElement(By.id("p_p_id_changeEditingMode_WAR_liferaylmsportlet_"));
				assertNotNull("Not Edit portlet found", editPortlet);
				
				inputs = getElements(editPortlet,By.tagName("input"));
				assertEquals("Not Edit portlet found", 1,inputs.size());
				inputs.get(0).click();

				Sleep.sleep(2000);
				
				for(int i=0;i<lis.size();i++){
					newactivity = getElement(By.className("newactivity"));
					assertNotNull("Not newactivity button", newactivity);

					aNew = getElement(newactivity,By.tagName("a"));
					assertNotNull("Not aNewnewactivity button", aNew);
					aNew.click();

					Sleep.sleep(2000);
					
					driver.switchTo().frame(0);

					activityList = getElement(By.className("activity-list"));
					assertNotNull("Not Activity list find", activityList);
					
					lis = getElements(activityList, By.tagName("li"));
					
					assertTrue("Poor activities... ",lis.size()>6);

					WebElement a = getElement(lis.get(i),By.tagName("a"));
					a.click();

					Sleep.sleep(2000);
					
					WebElement titleAct = getElement(By.id("_lmsactivitieslist_WAR_liferaylmsportlet_title_es_ES"));
					assertNotNull("Title activity not find", titleAct);
					switch(i){
						case 0:
							titleAct.sendKeys("Test "+Context.getCourseId());
							if (driver instanceof JavascriptExecutor) {
							    ((JavascriptExecutor)driver).executeScript("javascript:CKEDITOR.instances['_lmsactivitieslist_WAR_liferaylmsportlet_description'].setData('<p>Test "+Context.getCourseId()+"</p>');");
							}
							break;
						case 1:
							titleAct.sendKeys("R externo "+Context.getCourseId());
							if (driver instanceof JavascriptExecutor) {
							    ((JavascriptExecutor)driver).executeScript("javascript:CKEDITOR.instances['_lmsactivitieslist_WAR_liferaylmsportlet_description'].setData('<p>R externo "+Context.getCourseId()+"</p>');");
							}
							break;
						case 2:
							titleAct.sendKeys("P2P "+Context.getCourseId());
							if (driver instanceof JavascriptExecutor) {
							    ((JavascriptExecutor)driver).executeScript("javascript:CKEDITOR.instances['_lmsactivitieslist_WAR_liferaylmsportlet_description'].setData('<p>P2P "+Context.getCourseId()+"</p>');");
							}
							break;
						case 3:
							titleAct.sendKeys("Encuesta "+Context.getCourseId());
							if (driver instanceof JavascriptExecutor) {
							    ((JavascriptExecutor)driver).executeScript("javascript:CKEDITOR.instances['_lmsactivitieslist_WAR_liferaylmsportlet_description'].setData('<p>P2P "+Context.getCourseId()+"</p>');");
							}
							break;
						case 4:
							titleAct.sendKeys("T Present "+Context.getCourseId());
							if (driver instanceof JavascriptExecutor) {
							    ((JavascriptExecutor)driver).executeScript("javascript:CKEDITOR.instances['_lmsactivitieslist_WAR_liferaylmsportlet_description'].setData('<p>T Present "+Context.getCourseId()+"</p>');");
							}
							break;
						case 5:
							titleAct.sendKeys("Act desa "+Context.getCourseId());
							if (driver instanceof JavascriptExecutor) {
							    ((JavascriptExecutor)driver).executeScript("javascript:CKEDITOR.instances['_lmsactivitieslist_WAR_liferaylmsportlet_description'].setData('<p>Act desa "+Context.getCourseId()+"</p>');");
							}
							break;
						case 6:
							titleAct.sendKeys("R media "+Context.getCourseId());
							if (driver instanceof JavascriptExecutor) {
							    ((JavascriptExecutor)driver).executeScript("javascript:CKEDITOR.instances['_lmsactivitieslist_WAR_liferaylmsportlet_description'].setData('<p>R media "+Context.getCourseId()+"</p>');");
							}
							break;
						case 7:
							titleAct.sendKeys("Eval "+Context.getCourseId());
							if (driver instanceof JavascriptExecutor) {
							    ((JavascriptExecutor)driver).executeScript("javascript:CKEDITOR.instances['_lmsactivitieslist_WAR_liferaylmsportlet_description'].setData('<p>Eval "+Context.getCourseId()+"</p>');");
							}
							break;
						case 8:
							titleAct.sendKeys("SCORM "+Context.getCourseId());
							if (driver instanceof JavascriptExecutor) {
							    ((JavascriptExecutor)driver).executeScript("javascript:CKEDITOR.instances['_lmsactivitieslist_WAR_liferaylmsportlet_description'].setData('<p>SCORM "+Context.getCourseId()+"</p>');");
							}
							break;
					}
					
					form = getElement(By.id("_lmsactivitieslist_WAR_liferaylmsportlet_fm"));
					assertNotNull("Not form activity found", form);
					form.submit();

					Sleep.sleep(2000);

					/*driver.switchTo().frame(0);
					
					closethick = getElement(By.className("aui-button-input-cancel"));
					assertNotNull("Not close popoup", closethick);
					closethick.click();*/
					

					GetPage.getPage(driver, Context.getCoursePage(), "/reto");
					
					editPortlet = getElement(By.id("p_p_id_changeEditingMode_WAR_liferaylmsportlet_"));
					assertNotNull("Not Edit portlet found", editPortlet);
					
					inputs = getElements(editPortlet,By.tagName("input"));
					assertEquals("Not Edit portlet found", 1,inputs.size());
					inputs.get(0).click();
						
				}
				
				
				
				
				Sleep.sleep(30000);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
