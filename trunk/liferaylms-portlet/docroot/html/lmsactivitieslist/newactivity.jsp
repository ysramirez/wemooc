<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>

<%@ include file="/init.jsp"%>

<link href='http://fonts.googleapis.com/css?family=Nunito:400,300,700' rel='stylesheet' type='text/css'>

<script type="text/javascript">
<!--
AUI().ready(
    function(A) {
		A.all('img[onblur*=hide]').each(function(img){
			img.after(['blur','mouseout'],function(event){ 
				Liferay.Portal.ToolTip._cached.destroy();
				Liferay.Portal.ToolTip._cached=null;
			 });
		});
    }
);
//-->
</script>

<ul class="activity-list">
<%
	LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
	Map<Long,String> classTypes=AssetRendererFactoryRegistryUtil.
		getAssetRendererFactoryByClassName(LearningActivity.class.getName()).
		getClassTypes(new long[]{themeDisplay.getScopeGroupId()}, themeDisplay.getLocale());
	for(Long key:StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getActivities(), 
		StringPool.COMMA, GetterUtil.DEFAULT_LONG))
	{
		String classname=classTypes.get(key);
		if(Validator.isNotNull(classname)) {
%>	
	<liferay-portlet:renderURL var="newactivityURL">
		<liferay-portlet:param name="editing" value="<%=StringPool.TRUE %>" />
		<liferay-portlet:param name="resId" value="0" />
		<liferay-portlet:param name="resModuleId" value="<%=ParamUtil.getString(renderRequest, \"resModuleId\") %>" />
		<liferay-portlet:param name="type" value="<%=key.toString() %>" />
	</liferay-portlet:renderURL>
	
	<liferay-util:buffer var="activityMessage">
	    <%=LanguageUtil.get(themeDisplay.getLocale(), classTypes.get(key)) %>
	    <span class="activity-help">
			<liferay-ui:icon-help message="<%=learningActivityTypeRegistry.getLearningActivityType(key).getDescription() %>"  />
		</span>
	</liferay-util:buffer>

	<li class="activity_<%=key%>">
		<liferay-ui:icon image="add" label="<%=true%>" message="<%=activityMessage %>" url="<%=newactivityURL%>" cssClass="activity-icon" />
	</li>
<%
		}
	}
%>
</ul>
