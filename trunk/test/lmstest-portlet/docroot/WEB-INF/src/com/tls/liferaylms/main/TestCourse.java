package com.tls.liferaylms.main;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.TestProperties;

public class TestCourse {
	private static Log log = LogFactory.getLog(TestCourse.class);

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
		
		http://localhost:8080/web/test-1388736431576
		Context.setCoursePage("http://localhost:8080/web//test-1388748856313");

		HashMap<String, String> activities = new HashMap<String, String>();
		activities.put("Actividad de test 1388748856313","");
		activities.put("Actividad r externo 1388748856313","");
		activities.put("P2P 1388748856313","");
		activities.put("Encuesta 1388748856313","");
		activities.put("T Present 1388748856313","");
		activities.put("Act desa 1388748856313","");
		activities.put("R media 1388748856313","");
		activities.put("Eval 1388748856313","");
		activities.put("SCORM 1388748856313","");
		Context.setActivities(activities);
		
		
		Class classInstance = null;
		
		try {
			classInstance = Class.forName("com.tls.liferaylms.test.unit.Bd_CheckActivity");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(classInstance!=null){
			Result result=JUnitCore.runClasses(classInstance);

			List<Failure> fallos=result.getFailures();
			for(Failure fallo:fallos)
			{
				if(log.isInfoEnabled())log.info("Failure::"+fallo.toString());
				//Quit
				System.exit(1);
			}
		}
	}
}
