package com.tls.liferaylms.test.unit;


import java.util.Date;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;

import com.tls.liferaylms.test.SeleniumTestCase;
import com.tls.liferaylms.test.util.CheckCourse;
import com.tls.liferaylms.test.util.CheckPage;
import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.GetPage;
import com.tls.liferaylms.test.util.InstancePortlet;
import com.tls.liferaylms.test.util.Login;
import com.tls.liferaylms.test.util.Sleep;
import com.tls.liferaylms.test.util.TestProperties;

/**
 * @author Diego Renedo Delgado
 */
public class Ba_AdminCourse extends SeleniumTestCase {

  @Test
  public void testAdminCourse() throws Exception {
	  if(getLog().isInfoEnabled())getLog().info("init");

		Login login = new Login(driver, Context.getUser(), Context.getPass(), Context.getBaseUrl());
		boolean loged = login.isLogin();
		assertTrue("Error not logued",loged);
		if (loged) {
			if(Context.isTest()){
				try{					
					boolean instance = InstancePortlet.createInstance(driver, "administraci\u00f3n de cursos", "portlet_courseadmin_WAR_liferaylmsportlet");
					assertTrue("Error instance portlet portlet_courseadmin_WAR_liferaylmsportlet", instance);
					if(instance){
						WebElement addCourse = getElement(By.className("newitem2"));
						assertNotNull("Not addCourse find!", addCourse);
						WebElement butAddCourse = getElement(addCourse,By.tagName("a"));
						assertNotNull("Not button for addCourse find!", butAddCourse);
						butAddCourse.click();

						Sleep.sleep(1000);
						
						WebElement title = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_title_es_ES"));
						assertNotNull("Not title find!", title);
						Date date = new Date();
						title.sendKeys(TestProperties.get("course-name")+" "+date.getTime());
						WebElement inputVisible = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_visibleCheckbox"));
						assertNotNull("Not visible find!", title);
						inputVisible.click();
						WebElement summary = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_summary"));
						assertNotNull("Not summary", title);
						summary.sendKeys(TestProperties.get("course-name")+" "+date.getTime());
						
						if (driver instanceof JavascriptExecutor) {
						    ((JavascriptExecutor)driver).executeScript("javascript:CKEDITOR.instances['_courseadmin_WAR_liferaylmsportlet_description'].setData('<p>Test "+date.getTime()+"</p>');");
						}
						
						WebElement butForm1 = getElement(By.className("aui-button-input-submit")); 
						assertNotNull("formSearch not Find", butForm1);
						butForm1.click();

						Sleep.sleep(4000);
						
						WebElement freetext = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_freetext"));
						assertNotNull("Freetext not Find", freetext);
						freetext.sendKeys(String.valueOf(date.getTime()));
												
						WebElement formSearch = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_search"));
						assertNotNull("formSearch not Find", formSearch);
						
						WebElement butForm = getElement(formSearch,By.className("aui-button-input-submit")); 
						assertNotNull("formSearch But not Find", butForm);
						butForm.click();
						
						Sleep.sleep(2000);
						
						WebElement searchLayout = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_coursesSearchContainerSearchContainer"));
						assertNotNull("Not form Search", searchLayout);
						
						List<WebElement> as = getElements(searchLayout, By.tagName("a"));
						WebElement course = null;
						for(WebElement a : as){
							if(a.getText().equals(TestProperties.get("course-name")+" "+date.getTime())){
								course = a;
								break;
							}
						}
						
						assertNotNull("Course not Find", course);
						
						Context.setCoursePage(course.getAttribute("href"));
						Context.setCourseId(String.valueOf(date.getTime()));
						Context.setCourseName(TestProperties.get("course-name")+" "+date.getTime());
						
						if(getLog().isInfoEnabled())getLog().info("Course URL::"+Context.getCoursePage());
						
						course.click();
						
						Sleep.sleep(1000);
						
						assertTrue("Check course not good! ",CheckCourse.checkCourse(driver,driver.getCurrentUrl()));
												
						
						GetPage.getPage(driver, "", Context.getTestPage());
						
						
					}
					
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				assertTrue("Error not test",Context.isTest());
			}
		}
		if(getLog().isInfoEnabled())getLog().info("end ok");
  }

}
