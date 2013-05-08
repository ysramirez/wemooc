<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<%
List<AssetRendererFactory> factories= AssetRendererFactoryRegistryUtil.getAssetRendererFactories();
%>

<liferay-portlet:renderURL var="selectResource">
<liferay-portlet:param name="jspPage" value="/html/scormactivity/admin/searchresults.jsp"/>
</liferay-portlet:renderURL>
<aui:form name="<portlet:namespace />ressearch" action="<%=selectResource %>" method="POST">
<aui:select name="className" label="asset-type">
<% 

String assetTypes=PropsUtil.get("lms.scorm.assettypes");
String[] allowedAssetTypes=assetTypes.split(",");

for(String className:allowedAssetTypes)
{	
	String assettypename=LanguageUtil.get(pageContext, "model.resource." + className);	
	%>
	<aui:option value="<%=className%>" label="<%=assettypename%>"></aui:option>
	<%
	
}
%>
</aui:select>
<aui:input name="keywords" size="20" type="text"/>
<aui:select name="groupId" label="ambito">

<aui:option value="<%=themeDisplay.getScopeGroupId() %>" label="course"></aui:option>
<aui:option value="<%=themeDisplay.getCompanyGroupId() %>" label="global-contents"></aui:option>
<aui:option value="<%=themeDisplay.getUser().getGroupId() %>" label="personal-contents"></aui:option>
</aui:select>

<aui:button type="submit" value="search" />
</aui:form>