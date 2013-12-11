package com.tls.liferaylms.test.util;

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
	private static String teacherUser = "";
	private static String teacherName = "";
	private static String teacherPass = "";
		
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
	
}
