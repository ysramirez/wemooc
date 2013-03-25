<%@page import="java.util.Map"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>

<%@ include file="/init.jsp"%>
<%
AssetRendererFactory arf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
Map<Long,String> classTypes=arf.getClassTypes(new long[0], themeDisplay.getLocale());
for(Long key:classTypes.keySet())
{
	
%>	
<liferay-portlet:actionURL name="editactivityoptions" var="newactivityURL">
<liferay-portlet:param name="actId" value="0" />
<liferay-portlet:param name="resId" value="0" />
<liferay-portlet:param name="typeId" value="<%=key.toString() %>" />
</liferay-portlet:actionURL>
<%
	String classname=classTypes.get(key);		

%>
	<div class="newitem">
	<liferay-ui:icon image="add" label="<%=true%>" message="<%=classTypes.get(key) %>"
		url="<%=newactivityURL%>" />
		</div>
<%
}
%>

