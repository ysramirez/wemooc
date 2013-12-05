package com.tls.liferaylms.test.util;

public class Context {
	private static String baseUrl = "";
	private static String user = "";
	private static String pass = "";
	private static String page = "";
		
	public static String getBaseUrl() {
		return baseUrl;
	}
	public static void setBaseUrl(String baseUrl) {
		Context.baseUrl = baseUrl;
	}
	public static String getUser() {
		return user;
	}
	public static void setUser(String user) {
		Context.user = user;
	}
	public static String getPass() {
		return pass;
	}
	public static void setPass(String pass) {
		Context.pass = pass;
	}
	public static String getPage() {
		return page;
	}
	public static void setPage(String page) {
		Context.page = page;
	}
	
	
}
