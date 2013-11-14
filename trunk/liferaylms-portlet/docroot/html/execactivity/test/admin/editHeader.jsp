<%@page import="java.util.Map"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<link href='http://fonts.googleapis.com/css?family=Nunito:400,300,700' rel='stylesheet' type='text/css'>
<%
	if(request.getAttribute("activity")!=null) {
		LearningActivity learningActivity=(LearningActivity)request.getAttribute("activity");	
%>

<liferay-ui:header title="<%=learningActivity.getTitle(themeDisplay.getLocale()) %>" backURL="<%= (request.getAttribute(\"backUrl\")!=null)?(String)request.getAttribute(\"backUrl\"):\"\" %>"></liferay-ui:header>
<%
	}
%>