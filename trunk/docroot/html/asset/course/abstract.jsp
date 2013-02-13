<%@page import="com.liferay.lms.model.Course"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%
Course course=(Course)request.getAttribute("course");
%>

<p><%=HtmlUtil.unescape(course.getDescription()) %></p>