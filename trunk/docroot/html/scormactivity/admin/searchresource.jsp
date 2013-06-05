<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<%
List<AssetRendererFactory> factories= AssetRendererFactoryRegistryUtil.getAssetRendererFactories();Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
long searchGroupId=themeDisplay.getScopeGroupId();
if(course!=null)
{
	searchGroupId=course.getGroupId();
}
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
<aui:input name="groupId" type="hidden" value="<%=Long.toString(searchGroupId) %>" />

<%@ include file="/html/resourceInternalActivity/admin/catselector.jspf" %>

<aui:button type="submit" value="search" />
</aui:form>