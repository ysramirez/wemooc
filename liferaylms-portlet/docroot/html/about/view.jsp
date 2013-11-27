<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@page import="java.io.IOException"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.util.Properties"%>

<%@ include file="/init.jsp"%>

<%

	Properties prop = new Properties();
	long buildNumber = 0;
	Date date = new Date(0);
	try {
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		prop.load(classLoader.getResourceAsStream("service.properties"));
	
		buildNumber = Long.valueOf(prop.getProperty("build.date",""));
		
		date = new Date(buildNumber);
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

%>
<h2 class="description-title"><%=LanguageUtil.get(pageContext,"about")%></h2>
<span class="label"><%= LanguageUtil.get(pageContext,"build-date")	+": "+ date.toString() %></span>
<span class="label"><%= LanguageUtil.get(pageContext,"build-number")+": "+ prop.getProperty("build.number","") %></span>
<span class="label"><%= LanguageUtil.get(pageContext,"auto-upgrade")+": "+ prop.getProperty("build.auto.upgrade","") %></span>
