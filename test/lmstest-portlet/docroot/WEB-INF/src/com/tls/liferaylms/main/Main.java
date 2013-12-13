package com.tls.liferaylms.main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.tls.liferaylms.test.ClassFinder;
import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.TestProperties;

public class Main {
	private static Log log = LogFactory.getLog(Main.class);

	public static void main(String[] args) {
		Context.setBaseUrl(TestProperties.get("server")); //"http://localhost:8080/"
		Context.setUser(TestProperties.get("user")); //"test@liferay.com"
		Context.setPass(TestProperties.get("user-pass")); //"test"
		Context.setStudentName(TestProperties.get("student-name")); //student
		Context.setStudentPass(TestProperties.get("student-pass")); //student
		Context.setStudentUser(TestProperties.get("student-email")); //student@liferay.com
		Context.setTeacherName(TestProperties.get("teacher-name")); //teacher
		Context.setTeacherPass(TestProperties.get("teacher-pass")); //teacher
		Context.setTeacherUser(TestProperties.get("teacher-email")); //teacher@liferay.com
				
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
