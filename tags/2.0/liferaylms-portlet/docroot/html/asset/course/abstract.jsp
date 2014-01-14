<%@page import="com.liferay.lms.model.Course"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@ include file="/init.jsp"%>
<%
String abstractLength = renderRequest.getPreferences().getValue("abstractLength", "200");

Course course=(Course)request.getAttribute("course");
String summary = course.getDescription(themeDisplay.getLocale());

summary = StringUtil.shorten(summary, Integer.valueOf(abstractLength), "...");

%>
<p><%= summary %></p>