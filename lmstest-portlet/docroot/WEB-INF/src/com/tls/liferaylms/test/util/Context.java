package com.tls.liferaylms.test.util;

import java.util.HashMap;

public class Context {
	private static String baseUrl = "";
	private static String user = "";
	private static String pass = "";
	private static String page = "";
	private static String testPage = "";
	private static boolean test = false;
	private static String courseName = "";
	private static String courseId = "";
	private static String coursePage = "";
	private static String studentUser = "";
	private static String studentName = "";
	private static String studentPass = "";
	private static String studentUser2 = "";
	private static String studentName2 = "";
	private static String studentPass2 = "";
	public static String getStudentUser2() {
		return studentUser2;
	}

	public static void setStudentUser2(String studentUser2) {
		Context.studentUser2 = studentUser2;
	}

	public static String getStudentName2() {
		return studentName2;
	}

	public static void setStudentName2(String studentName2) {
		Context.studentName2 = studentName2;
	}

	public static String getStudentPass2() {
		return studentPass2;
	}

	public static void setStudentPass2(String studentPass2) {
		Context.studentPass2 = studentPass2;
	}

	private static String teacherUser = "";
	private static String teacherName = "";
	private static String teacherPass = "";
	private static HashMap<String, String> activities = new HashMap<String, String>();
		
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
	
	public static String getTestPage() {
		return testPage;
	}
	
	public static void setTestPage(String testPage) {
		Context.testPage = testPage;
	}
	
	public static boolean isTest() {
		return test;
	}
	
	public static void setTest(boolean test) {
		Context.test = test;
	}

	public static String getCourseName() {
		return courseName;
	}

	public static void setCourseName(String courseName) {
		Context.courseName = courseName;
	}

	public static String getCoursePage() {
		return coursePage;
	}

	public static void setCoursePage(String coursePage) {
		Context.coursePage = coursePage;
	}

	public static String getCourseId() {
		return courseId;
	}

	public static void setCourseId(String courseId) {
		Context.courseId = courseId;
	}

	public static String getStudentUser() {
		return studentUser;
	}

	public static void setStudentUser(String studentUser) {
		Context.studentUser = studentUser;
	}

	public static String getStudentName() {
		return studentName;
	}

	public static void setStudentName(String studentName) {
		Context.studentName = studentName;
	}

	public static String getStudentPass() {
		return studentPass;
	}

	public static void setStudentPass(String studentPass) {
		Context.studentPass = studentPass;
	}

	public static String getTeacherUser() {
		return teacherUser;
	}

	public static void setTeacherUser(String teacherUser) {
		Context.teacherUser = teacherUser;
	}

	public static String getTeacherName() {
		return teacherName;
	}

	public static void setTeacherName(String teacherName) {
		Context.teacherName = teacherName;
	}

	public static String getTeacherPass() {
		return teacherPass;
	}

	public static void setTeacherPass(String teacherPass) {
		Context.teacherPass = teacherPass;
	}

	public static HashMap<String, String> getActivities() {
		return activities;
	}

	public static void setActivities(HashMap<String, String> activities) {
		Context.activities = activities;
	}
	
}
