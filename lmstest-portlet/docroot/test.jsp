<%@page import="com.tls.liferaylms.test.util.Context"%>
<%@page import="com.liferay.portal.kernel.log.Log"%>
<%@page import="com.liferay.portal.kernel.log.LogFactoryUtil"%>
<%@page buffer="5kb" autoFlush="false" %>

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
<%@page import="com.tls.liferaylms.test.unit.Aa_LoginTest"%>
<%@page import="junit.framework.JUnit4TestAdapter"%>
<%@page import="org.junit.runner.JUnitCore"%>
<%@page import="org.testng.junit.JUnit4TestRunner"%>
<table>
<%

Log log = LogFactoryUtil.getLog("com.tls.liferaylms.test.jsp");

Context.setBaseUrl("http://localhost:8080/");
Context.setUser("test@liferay.com");
Context.setPass("test");

Class[] classes=ClassFinder.getClasses("com.tls.liferaylms.test.unit");
for(Class clase:classes)
	{
	if(log.isInfoEnabled())log.info("Exec::"+clase.getName());
	%>
	<tr><td><%=clase.getName() %></td>
	<%

try{
	
	Result result=JUnitCore.runClasses(clase);

	if(result.getFailureCount()>0){
	%>
	<td style="background-color:red"><%=result.getFailureCount() %></td>
	<%
	}else{
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
catch(Throwable e){
	e.printStackTrace();
}
}
//SeleniumDriverUtil.closeDriver();
%>
</table>
