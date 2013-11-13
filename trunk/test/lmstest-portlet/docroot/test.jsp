<%
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<%@page import="com.tls.liferaylms.test.SeleniumDriverUtil"%>
<%@page import="org.junit.runner.notification.Failure"%>
<%@page import="org.junit.runner.Result"%>
<%@page import="java.util.List"%>
<%@page import="com.tls.liferaylms.test.ClassFinder"%>
<%@page import="java.util.Enumeration"%>
<%@page import="junit.framework.TestFailure"%>
<%@page import="junit.framework.TestResult"%>
<%@page import="com.tls.liferaylms.test.unit.LoginTest"%>
<%@page import="junit.framework.JUnit4TestAdapter"%>
<%@page import="org.junit.runner.JUnitCore"%>
<%@page import="org.testng.junit.JUnit4TestRunner"%>
<table>
<%

Class[] classes=ClassFinder.getClasses("com.tls.liferaylms.test.unit");
for(Class clase:classes)
{

	%>
	<tr><td><%=clase.getName() %></td>
	<%

try
{
	
Result result=JUnitCore.runClasses(clase);

if(result.getFailureCount()>0)
{
	%>
	<td style="background-color:red"><%=result.getFailureCount() %></td>
	<%
}
else
{
	%>
	<td style="background-color:green"><%=result.getFailureCount() %></td>
	<%
}
%>
<td>
<%
List<Failure> fallos=result.getFailures();
for(Failure fallo:fallos)
{
	%>
	<br />
	<%=fallo.toString() %>:	
	<%
}
%>
</td></tr>
<%
}
catch(Throwable e)
{
	
}
}
SeleniumDriverUtil.closeDriver();
%>
</table>
