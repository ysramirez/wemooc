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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" autoFlush="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Testing</title>
</head>
<body>
<table>
<%
out.flush();
Class[] classes=ClassFinder.getClasses("com.tls.liferaylms.test.unit");
for(Class clase:classes)
{

	%>
	<tr><td><%=clase.getName() %></td>
	<%
	out.flush();
try
{
	
Result result=JUnitCore.runClasses(clase);

out.println("<td>"+result.getRunCount()+"</td>");
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
</body>
</html>