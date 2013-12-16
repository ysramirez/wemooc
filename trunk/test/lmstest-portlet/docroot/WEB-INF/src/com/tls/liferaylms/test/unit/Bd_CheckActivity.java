package com.tls.liferaylms.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tls.liferaylms.test.SeleniumTestCase;
import com.tls.liferaylms.test.util.CheckPage;
import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.GetPage;
import com.tls.liferaylms.test.util.Login;


//http://localhost:8080/web/test-1387192978168
public class Bd_CheckActivity extends SeleniumTestCase {

	  @Test
	  public void checkActivities() throws Exception {
		  if(getLog().isInfoEnabled())getLog().info("init");
		  GetPage.getPage(driver, "", Context.getTestPage());
		  
		  Login login = new Login(driver, Context.getStudentUser(), Context.getStudentPass(), Context.getBaseUrl());

		  if(login.isLogin())
			  login.logout();
			
		  boolean studentLogin = login.login();
		  assertTrue("Error not logued",studentLogin);

		  if(studentLogin){
			  for(String id : Context.getActivities().keySet()){
				  if(getLog().isInfoEnabled())getLog().info("Check activity::"+id);
				  
				  String url = Context.getActivities().get(id);
				  
				  GetPage.getPage(driver, url, "");
				  
				  assertEquals("Errors in activity"+id, 0, CheckPage.check(driver));
				  
				  
			  }
		  }
	  }
}
