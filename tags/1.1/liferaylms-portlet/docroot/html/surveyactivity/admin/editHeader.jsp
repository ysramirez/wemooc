<%@page import="java.util.Map"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<link href='http://fonts.googleapis.com/css?family=Nunito:400,300,700' rel='stylesheet' type='text/css'>
<% 
	if(request.getAttribute("backUrl")!=null) {
%>
<liferay-ui:icon image="back" message="back" url="<%=(String)request.getAttribute(\"backUrl\") %>" label="true"  />
<%
	}
	if(request.getAttribute("activity")!=null) {
		LearningActivity learningActivity=(LearningActivity)request.getAttribute("activity");	
%>
<liferay-ui:header title="<%=AssetRendererFactoryRegistryUtil.
								getAssetRendererFactoryByClassName(LearningActivity.class.getName()).
								getClassTypes(new long[0], themeDisplay.getLocale()).get(Long.valueOf(learningActivity.getTypeId()))%>"></liferay-ui:header>
<liferay-ui:header title="<%=learningActivity.getTitle(themeDisplay.getLocale()) %>"></liferay-ui:header>
<%
	}
%>