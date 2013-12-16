package com.tls.liferaylms.test.unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.tls.liferaylms.test.SeleniumTestCase;
import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.GetPage;
import com.tls.liferaylms.test.util.InstancePortlet;
import com.tls.liferaylms.test.util.Login;
import com.tls.liferaylms.test.util.Sleep;

public class Bb_CheckUsers extends SeleniumTestCase {

	  @Test
	  public void testUsers() throws Exception {
		  if(getLog().isInfoEnabled())getLog().info("init");
			GetPage.getPage(driver, "", Context.getTestPage());

			Login login = new Login(driver, Context.getUser(), Context.getPass(), Context.getBaseUrl());
			boolean loged = login.isLogin();
			assertTrue("Error not logued",loged);
			
			assertNotEquals("Error not course created","",Context.getTestPage().equals(""));
			
			boolean instance = InstancePortlet.createInstance(driver, "administraci\u00f3n de cursos", "portlet_courseadmin_WAR_liferaylmsportlet");
			assertTrue("Error instance portlet portlet_courseadmin_WAR_liferaylmsportlet", instance);
			if(instance){
				try{
					
					//Add Teacher in course
					if(getLog().isInfoEnabled())getLog().info("Starting test");
	
					Sleep.sleep(4000);
					
					WebElement form = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_fm"));
					
					WebElement freetext = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_freetext"));
					assertNotNull("Freetext not Find", freetext);
					if(!freetext.getText().equals(Context.getCourseId())){
						for(int i=0;i<40;i++){
							freetext.sendKeys(Keys.BACK_SPACE);
						}
						if(getLog().isInfoEnabled())getLog().info("Put course::"+Context.getCourseId());
						freetext.sendKeys(String.valueOf(Context.getCourseId()));	
					}
					
					WebElement formSearch = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_search"));
					assertNotNull("formSearch not Find", formSearch);
					
					WebElement butForm = getElement(formSearch,By.className("aui-button-input-submit")); 
					assertNotNull("submit Search not Find", butForm);
					butForm.click();

					Sleep.sleep(2000);
					
					WebElement searchLayout = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_coursesSearchContainerSearchContainer"));
					List<WebElement> as = getElements(searchLayout, By.tagName("tr"));
	
					if(getLog().isInfoEnabled())getLog().info("Course Size::"+as.size());
					
					assertEquals("Course not find", as.size(),3);
					
					WebElement menuButton = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_coursesSearchContainer_1_menuButton"));
					assertNotNull("Course Admin not Find menuButton", menuButton);
					menuButton.click();
					Sleep.sleep(2000);
	
					WebElement courseMembers = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_coursesSearchContainer_1_menu_assign-member"));
					assertNotNull("Course Admin not Find memebers Button", courseMembers);
					
					courseMembers.click();
					Sleep.sleep(2000);
					
					WebElement tab = getElement(By.className("aui-tabview-list"));
					assertNotNull("Course Users tab not found", tab);
					List<WebElement> lis = getElements(tab,By.tagName("li")); 
					assertEquals("formSearch not But not Find", lis.size(),3);
					
					if(getLog().isInfoEnabled())getLog().info("Tab Size::"+lis.size());
					if(lis.size()==3){
						if(getLog().isInfoEnabled())getLog().info("Move to menu");
						WebElement btTab = getElement(lis.get(2),By.tagName("a"));
						btTab.click();
						
						Sleep.sleep(1000);
						
						WebElement btNewUser = getElement(By.className("bt_newuser"));
						assertNotNull("User Course Button menu not find", btNewUser);
						WebElement aAddUser = getElement(btNewUser,By.tagName("a")); 
						assertNotNull("User Course Button not find", aAddUser);
						aAddUser.click();
						
						Sleep.sleep(2000);
						
						WebElement emailAddress = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_emailAddress"));
						assertNotNull("User Course emailAddress not find", emailAddress);
						emailAddress.sendKeys(Context.getTeacherUser());
						
						WebElement button = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_searchUsers"));
						assertNotNull("User search course button not find", button);
						button.click();
						
						Sleep.sleep(1000);

						WebElement container = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_usersSearchContainerSearchContainer"));
						assertNotNull("User Course search container not find", container);
						
						List<WebElement> las = getElements(container,By.className("newitem2"));
						assertNotNull("No users in te result",las);
						assertEquals("Many users in te result",1, las.size());
						
						las.get(0).click();
					}

					Sleep.sleep(2000);

					//Add Student in course
					tab = getElement(By.className("aui-tabview-list"));
					assertNotNull("Course Users tab not found", tab);
					lis = getElements(tab,By.tagName("li")); 
					assertEquals("formSearch not But not Find", lis.size(),3);

					if(getLog().isInfoEnabled())getLog().info("Tab Size::"+lis.size());
					if(lis.size()==3){
						if(getLog().isInfoEnabled())getLog().info("Move to menu");
						WebElement btTab = getElement(lis.get(0),By.tagName("a"));
						btTab.click();

						Sleep.sleep(1000);
						
						WebElement btNewUser = getElement(By.className("bt_newuser"));
						assertNotNull("User Course Button menu not find", btNewUser);
						WebElement aAddUser = getElement(btNewUser,By.tagName("a")); 
						assertNotNull("User Course Button not find", aAddUser);
						aAddUser.click();

						Sleep.sleep(2000);
						
						WebElement emailAddress = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_emailAddress"));
						assertNotNull("User Course emailAddress not find", emailAddress);
						emailAddress.sendKeys(Context.getStudentUser());
						
						WebElement button = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_searchUsers"));
						assertNotNull("User search course button not find", button);
						button.click();
						
						Sleep.sleep(1000);

						WebElement container = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_usersSearchContainerSearchContainer"));
						assertNotNull("User Course search container not find", container);

						List<WebElement> las = getElements(container,By.className("newitem2"));
						assertNotNull("No users in te result",las);
						assertEquals("Many users in te result",1, las.size());

						las.get(0).click();
						
					}

					GetPage.getPage(driver, "", Context.getTestPage());
					Sleep.sleep(2000);
					
					menuButton = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_coursesSearchContainer_1_menuButton"));
					assertNotNull("Course Admin not Find menuButton", menuButton);
					menuButton.click();
					Sleep.sleep(1000);
	
					courseMembers = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_coursesSearchContainer_1_menu_courseadmin.adminactions.permissions"));
					assertNotNull("Course Admin not Find memebers Button", courseMembers);
					courseMembers.click();
					Sleep.sleep(2000);
					
					WebElement input = getElement(By.id("user_ACTION_ACCESS"));
					assertNotNull("No input found for permissions", input);
					input.click();
					
					WebElement submit = getElement(By.className("aui-button-input-submit"));
					assertNotNull("Not submit found", submit);

					try{
						submit.click();
						submit.click();
					}catch(Exception e){}
					

					GetPage.getPage(driver, "", Context.getTestPage());
					
					form = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_fm"));
					
					freetext = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_freetext"));
					assertNotNull("Freetext not Find", freetext);
					if(freetext.getText().equals(Context.getCourseId())){
						for(int i=0;i<40;i++){
							freetext.sendKeys(Keys.BACK_SPACE);
						}
						freetext.sendKeys(String.valueOf(Context.getCourseId()));
					}
					
					formSearch = getElement(By.id("_courseadmin_WAR_liferaylmsportlet_search"));
					assertNotNull("formSearch not Find", formSearch);
					
					butForm = getElement(formSearch,By.className("aui-button-input-submit"));
					assertNotNull("formSearch not But Find", butForm);
					butForm.click();

					Sleep.sleep(2000);
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			if(getLog().isInfoEnabled())getLog().info("End");
	  }
}
