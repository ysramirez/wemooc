<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@ include file="/html/resourceInternalActivity/admin/init.jsp" %>

<%
List<AssetRendererFactory> factories= AssetRendererFactoryRegistryUtil.getAssetRendererFactories();

%>
<liferay-portlet:renderURL var="selectResource">
<liferay-portlet:param name="jspPage" value="/html/resourceInternalActivity/admin/searchresults.jsp"/>
 <liferay-portlet:param value="<%=Long.toString(learnact.getActId()) %>" name="actId"/>

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