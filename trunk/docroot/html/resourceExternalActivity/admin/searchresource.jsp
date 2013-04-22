<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@ include file="/html/resourceExternalActivity/admin/init.jsp" %>
<liferay-portlet:renderURL var="backURL" >
	<liferay-portlet:param name="jspPage" value="/html/resourceExternalActivity/admin/edit.jsp"/>
	<liferay-portlet:param name="resId" value="<%=Long.toString(learnact.getActId()) %>" />
	<liferay-portlet:param name="resModuleId" value="<%=Long.toString(learnact.getModuleId()) %>" />
	<liferay-portlet:param name="actionEditingDetails" value="true"/>
</liferay-portlet:renderURL>
<liferay-ui:icon image="back" message="back" label="true" url="<%=backURL.toString() %>" />

<liferay-ui:header title="<%=learnact.getTitle(themeDisplay.getLocale()) %>"></liferay-ui:header>

<%
List<AssetRendererFactory> factories= AssetRendererFactoryRegistryUtil.getAssetRendererFactories();

%>
<liferay-portlet:renderURL var="selectResource">
<liferay-portlet:param name="jspPage" value="/html/resourceExternalActivity/admin/searchresults.jsp"/>
<liferay-portlet:param name="resId" value="<%=Long.toString(learnact.getActId()) %>"/>
<liferay-portlet:param name="actionEditingDetails" value="true" />
</liferay-portlet:renderURL>
<aui:form name="<portlet:namespace />ressearch" action="<%=selectResource %>" method="POST">
<aui:select name="className" label="asset-type">
<% 
for(AssetRendererFactory arf:factories)
{
	if(!arf.getClassName().equals(LearningActivity.class.getName()))
	{
		String assettypename=LanguageUtil.get(pageContext, "model.resource." + arf.getClassName());
	%>
	<aui:option value="<%=arf.getClassName()%>" label="<%=assettypename%>"></aui:option>
	<%
	}
}
%>
<br>
<aui:input name="keywords" size="20" type="text"/>
</aui:select>
<aui:button type="submit" value="search" />
</aui:form>