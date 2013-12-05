package com.tls.liferaylms.test.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.liferay.portal.kernel.log.Log;
import com.tls.liferaylms.test.SeleniumTestCase;
import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.Login;

public class Aa_LoginTest extends SeleniumTestCase {
	private Log log = null;

	@Test
	public void testLogin() throws Exception {
		if(getLog().isInfoEnabled())getLog().info("init");

		Login login = new Login(driver, Context.getUser(), Context.getPass(), Context.getBaseUrl());

		if (login.isLogin()) {
			if(log.isInfoEnabled())log.info("isLogin::true");
			assertTrue("Error logout",login.logout());
		}else{
			assertTrue("Error login",login.login());
			assertTrue("Error validate login",login.isLogin());
			assertTrue("Error logout",login.logout());
		}
	}
}
