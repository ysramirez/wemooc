<%@page import="com.liferay.lms.model.SCORMContent"%>
<%
SCORMContent scorm=(SCORMContent)request.getAttribute("scorm");
%>
<%=scorm.getDescription()%>