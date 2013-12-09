package com.tls.liferaylms.test.unit;


import java.util.Date;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;

import com.tls.liferaylms.test.SeleniumTestCase;
import com.tls.liferaylms.test.util.CheckPage;
import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.GetPage;
import com.tls.liferaylms.test.util.InstancePortlet;
import com.tls.liferaylms.test.util.Login;
import com.tls.liferaylms.test.util.Sleep;

public class B_AdminCourse extends SeleniumTestCase {

  @Test
  public void testAdminCourse() throws Exception {
	  if(getLog().isInfoEnabled())getLog().info("init");

		Login login = new Login(driver, "test@liferay.com", "test", baseUrl);
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

						WebElement title = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_title_es_ES"));
						assertNotNull("Not title find!", title);
						Date date = new Date();
						title.sendKeys("Test "+date.getTime());
						WebElement inputVisible = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_visibleCheckbox"));
						assertNotNull("Not visible find!", title);
						inputVisible.click();
						WebElement summary = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_summary"));
						assertNotNull("Not summary", title);
						summary.sendKeys("Test "+date.getTime());
						
						WebElement form = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_fm"));
						
						Sleep.sleep(4000);
						
						if(getLog().isInfoEnabled())getLog().info("Check course");
						GetPage.getPage(driver, "", Context.getTestPage());
						
						WebElement freetext = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_freetext"));
						assertNotNull("Freetext not Find", freetext);
						freetext.sendKeys(String.valueOf(date.getTime()));
						
						//WebElement iframeContent = getElement(By.id("cke_contents__courseadmin_WAR_liferaylmsportlet_description"));
						//assertNotNull("No iframe container",iframeContent);
						/*WebElement iframeCkeditor = getElement(By.xpath("//iframe"));
						assertNotNull("No iframe for CKEditor",iframeCkeditor);
						
						driver.switchTo().frame(iframeCkeditor);
						WebElement body = getElement(By.tagName("body"));
						assertNotNull("No body iframe for CKEditor", body);*/
						//WebElement des = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_description"));
						//if(getLog().isInfoEnabled())getLog().info("_courseadmin_WAR_liferaylmsportlet_description"+des);
						//des.sendKeys("Test "+date.getTime());
						
						
						WebElement formSearch = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_search"));
						assertNotNull("formSearch not Find", formSearch);
						
						WebElement butForm = getElement(formSearch,By.className("aui-button-input-submit")); 
						assertNotNull("formSearch not But Find", butForm);
						butForm.click();
						
						WebElement searchLayout = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_coursesSearchContainerSearchContainer"));
						List<WebElement> as = getElements(searchLayout, By.tagName("a"));
						WebElement course = null;
						for(WebElement a : as){
							if(a.getText().equals("Test "+date.getTime())){
								course = a;
								break;
							}
						}
						
						assertNotNull("Course not Find", course);
						course.click();
						CheckPage.check(driver);
						
						form.submit();
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

  

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }
}
