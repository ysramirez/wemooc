<%@page import="java.util.Map"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>

<%@ include file="/init.jsp"%>
<ul class="activity-list">
<%
AssetRendererFactory arf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
Map<Long,String> classTypes=arf.getClassTypes(new long[0], themeDisplay.getLocale());
for(Long key:classTypes.keySet())
{
	
%>	
<liferay-portlet:actionURL name="editactivityoptions" var="newactivityURL">
<liferay-portlet:param name="resId" value="0" />
<liferay-portlet:param name="resModuleId" value="<%=ParamUtil.getString(renderRequest, \"resModuleId\") %>" />
<liferay-portlet:param name="typeId" value="<%=key.toString() %>" />
</liferay-portlet:actionURL>
<%
	String classname=classTypes.get(key);
%>
	<li class="activity_<%=key%>">
		<liferay-ui:icon image="add" label="<%=true%>" message="activity.creation" url="<%=newactivityURL%>" />
	</li>
<%
}
%>
</ul>
