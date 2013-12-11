package com.tls.liferaylms.main;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.tls.liferaylms.test.ClassFinder;
import com.tls.liferaylms.test.util.Context;

public class Main {
	private static Log log = LogFactory.getLog(Main.class);

	public static void main(String[] args) {
		Context.setBaseUrl("http://localhost:8080/");
		Context.setUser("test@liferay.com");
		Context.setPass("test");
		Context.setStudentName("student");
		Context.setStudentPass("student");
		Context.setStudentUser("student@liferay.com");
		Context.setTeacherName("teacher");
		Context.setTeacherPass("teacher");
		Context.setTeacherUser("teacher@liferay.com");
		
		Class[] classes = null;
		try {
			classes = ClassFinder.getClasses("com.tls.liferaylms.test.unit");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(classes!=null){
			for(Class clase:classes){
				if(log.isInfoEnabled())log.info("Exec::"+clase.getName());
				try{	
					Result result=JUnitCore.runClasses(clase);
					List<Failure> fallos=result.getFailures();
					for(Failure fallo:fallos)
					{
						if(log.isInfoEnabled())log.info("Failure::"+fallo.toString());
						//Quit
						System.exit(1);
					}
					
				}catch(Throwable e){
					e.printStackTrace();
				}
			}
		}
	}
}
