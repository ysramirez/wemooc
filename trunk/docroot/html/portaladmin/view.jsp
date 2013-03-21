<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp"%>
	
<%
long actId = ParamUtil.getLong(request,"actId",0);

if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),LearningActivity.class.getName() , actId, "CORRECT")){
%>	

<div class="actions">

	<div class="action">
		<portlet:actionURL name="asignP2pActivity" var="asignP2pActivityURL" />
		<liferay-ui:icon image="assign" label="<%=true %>" message="p2ptaskactivity.edit.asignp2p" url='<%= asignP2pActivityURL %>'/>
	</div>
	
	<div class="action">
		<portlet:actionURL name="updateResultP2PActivities" var="updateResultP2PActivitiesURL" />
		<liferay-ui:icon image="recent_changes" label="<%=true %>" message="p2ptaskactivity.updateResult" url='<%= updateResultP2PActivitiesURL %>'/>
	</div>
	
	<div class="action">
		<portlet:actionURL name="updateExtraContentMultimediaActivities" var="updateExtraContentMultimediaActivitiesURL" />
		<liferay-ui:icon image="recent_changes" label="<%=true %>" message="p2ptaskactivity.updateExtraContent" url='<%=updateExtraContentMultimediaActivitiesURL %>'/>
	</div>
	
</div>
<%
}
%>