
<%@ include file="/html/resourceExternalActivity/admin/init.jsp" %>
<liferay-portlet:renderURL var="backURL" >
	<liferay-portlet:param name="jspPage" value="/html/resourceExternalActivity/admin/edit.jsp"/>
	<liferay-portlet:param name="resId" value="<%=Long.toString(learnact.getActId()) %>" />
	<liferay-portlet:param name="resModuleId" value="<%=Long.toString(learnact.getModuleId()) %>" />
	<liferay-portlet:param name="actionEditingDetails" value="true"/>
</liferay-portlet:renderURL>
<liferay-ui:icon image="back" message="back" label="true" url="<%=backURL.toString() %>" />

<liferay-ui:header title="<%=learnact.getTitle(themeDisplay.getLocale()) %>"></liferay-ui:header>

<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%

if(learnact.getExtracontent()!=null &&!learnact.getExtracontent().trim().equals("") )
{
	
	long entryId=Long.valueOf(learnact.getExtracontent());
	AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(entryId);
	AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());

	AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
	String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT);

%>
	<liferay-ui:header title="<%=assetRenderer.getTitle(themeDisplay.getLocale())%>"></liferay-ui:header>
	<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>" />
	<%
}
else
{
	%>
	<p><liferay-ui:message key="selectResourceMsg" /></p>
	<%
}

%>
<liferay-portlet:renderURL var="searchResource">
<liferay-portlet:param name="jspPage" value="/html/resourceExternalActivity/admin/searchresource.jsp"/>
 <liferay-portlet:param value="<%=Long.toString(learnact.getActId()) %>" name="resId"/>

</liferay-portlet:renderURL>
<a href="<%=searchResource %>"><liferay-ui:message key="selectResource" /></a>

