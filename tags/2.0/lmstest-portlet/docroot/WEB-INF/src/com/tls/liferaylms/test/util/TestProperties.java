package com.tls.liferaylms.test.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	private static Properties prop = null; 
	
	public static String get(String key){
		if(prop==null)
			init();
		return prop.getProperty(key);
	}
	
	private static void init(){
		prop = new Properties();
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			prop.load(classLoader.getResourceAsStream("test.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
