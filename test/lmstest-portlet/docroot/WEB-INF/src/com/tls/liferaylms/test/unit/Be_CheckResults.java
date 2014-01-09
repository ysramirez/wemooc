package com.tls.liferaylms.test.unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tls.liferaylms.test.SeleniumTestCase;
import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.GetPage;
import com.tls.liferaylms.test.util.Login;
import com.tls.liferaylms.test.util.Sleep;

/**
 * @author Diego Renedo Delgado
 */
public class Be_CheckResults extends SeleniumTestCase {

	@Test
	public void checkResults() throws Exception {
		try{
			if(getLog().isInfoEnabled())getLog().info("init");
		  
			Login login = new Login(driver, Context.getUser(), Context.getPass(), Context.getBaseUrl());

			if(login.isLogin())
				login.logout();
		  
			boolean testLogin = login.login();
			assertTrue("Error not logued test",testLogin);

			if(testLogin){
				GetPage.getPage(driver, Context.getCoursePage(),"/admin");
			  
				WebElement menu = getElement(By.className("lfr-content-category"));
				assertNotNull("Not activity found:", menu);
			  
				List<WebElement> divs = getElements(menu,By.tagName("div"));
				assertEquals("Administrator menu not has options expected",3, divs.size());
				List<WebElement> a = getElements(divs.get(0),By.tagName("a"));
				assertEquals("No link to admin students found",3, divs.size());
				a.get(0).click();
			  
				Sleep.sleep(1000);
			  
				WebElement portlet = getElement(By.id("_studentmanage_WAR_liferaylmsportlet_usersSearchContainer"));
				assertNotNull("Not Admin students portlet found", portlet);
			  
				List<WebElement> trs = getElements(portlet,By.className("results-row"));
				assertNotNull("Not results for students", trs);
			  
				for(int i=0;i<trs.size();i++){
					if(i>0){
						portlet = getElement(By.id("_studentmanage_WAR_liferaylmsportlet_usersSearchContainer"));
						assertNotNull("Not Admin students portlet found", portlet);
						
						trs = getElements(portlet,By.className("results-row"));
						assertNotNull("Not results for students", trs);
					}
					assertNotNull("Not students", trs);
					WebElement tr = trs.get(i);
					String text = null;
					try{
						text = tr.getText().toLowerCase().split(" ")[0];
					}catch(Exception e){}
					if(text!=null&&!text.equals("")){
						if(text.equals(Context.getStudentName())){
							assertEquals("Result for student not match","10010085100100100100-0", goTolink(tr));
						}else if(text.equals(Context.getStudentName2())){
							assertEquals("Result for student2 not match","010085100100100100-0", goTolink(tr));
						}
					} 
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		if(getLog().isInfoEnabled())getLog().info("end!");
	}
	
	private String goTolink(WebElement tr){
		List<WebElement> tds = getElements(tr,By.tagName("td"));
		assertNotNull("Not students", tds);
		assertEquals("Size of tr not correct", 2, tds.size());
		WebElement td = tds.get(1);
		assertNotNull("Not td for results", td);
		WebElement a = getElement(td,By.tagName("a"));
		assertNotNull("Not link for results", a);
		
		a.click();
		
		Sleep.sleep(1000);
				
		WebElement select = getElement(By.className("aui-helper-unselectable"));
		WebElement rollOut = getElement(select,By.tagName("a"));
		rollOut.click();
		

		WebElement portlet = getElement(By.id("_studentmanage_WAR_liferaylmsportlet_learningActivitiesSearchContainerSearchContainer"));
		assertNotNull("Not td for results", portlet);
		
		List<WebElement> trs = getElements(portlet,By.tagName("tr"));
		
		StringBuilder sb = new StringBuilder();
		
		for(WebElement ter : trs){
			List<WebElement> teds = getElements(ter,By.tagName("td"));
			if(teds.size()>1){
				WebElement ted = teds.get(1);
				sb.append(ted.getText());
			}
		}
		
		WebElement breturn = getElement(By.id("_studentmanage_WAR_liferaylmsportlet_TabsBack"));
		assertNotNull("Not return button", breturn);
		breturn.click();
		
		Sleep.sleep(2000);
		
		return sb.toString();
	}
}
