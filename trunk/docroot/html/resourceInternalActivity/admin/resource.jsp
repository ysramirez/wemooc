
<%@ include file="/html/resourceInternalActivity/admin/init.jsp" %>

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
<liferay-portlet:param name="jspPage" value="/html/resourceInternalActivity/admin/searchresource.jsp"/>
 <liferay-portlet:param value="<%=Long.toString(learnact.getActId()) %>" name="actId"/>

</liferay-portlet:renderURL>
<a href="<%=searchResource %>"><liferay-ui:message key="selectResource" /></a>

