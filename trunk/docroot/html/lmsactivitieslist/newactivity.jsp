<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
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
LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
for(Long key:classTypes.keySet())
{
	String classname=classTypes.get(key);
%>	
	<liferay-portlet:actionURL name="editactivityoptions" var="newactivityURL">
		<liferay-portlet:param name="resId" value="0" />
		<liferay-portlet:param name="resModuleId" value="<%=ParamUtil.getString(renderRequest, \"resModuleId\") %>" />
		<liferay-portlet:param name="typeId" value="<%=key.toString() %>" />
	</liferay-portlet:actionURL>

	<li class="activity_<%=key%>">
		<liferay-ui:icon image="add" label="<%=true%>" message="<%=classTypes.get(key) %>" url="<%=newactivityURL%>" cssClass="activity-icon" imageHover="<%=learningActivityTypeRegistry.getLearningActivityType(key).getDescription() %>" />
		<span class="activity-help">
			<liferay-ui:icon-help message="<%=learningActivityTypeRegistry.getLearningActivityType(key).getDescription() %>"  />
		</span>
	</li>
<%
}
%>
</ul>
