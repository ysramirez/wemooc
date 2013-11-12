<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp"%>
	
<%
long actId = ParamUtil.getLong(request,"actId",0);
String res = ParamUtil.getString(request,"resultados","");

if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),LearningActivity.class.getName() , actId, "CORRECT")){
%>	

<style>
	.action{border: 1px solid #000;margin:10px;padding:10px;}
</style>

<div class="actions">

	<div class="action">
		<portlet:actionURL name="asignP2pActivity" var="asignP2pActivityURL" />
		<liferay-ui:icon image="assign" label="<%=true %>" message="p2ptaskactivity.edit.asignp2p" url='<%= asignP2pActivityURL %>'/>
	</div>
	
	<div class="action">
		<portlet:actionURL name="updateResultP2PActivities" var="updateResultP2PActivitiesURL" />
		<liferay-ui:icon image="recent_changes" label="<%=true %>" message="p2ptaskactivity.updateResult" url='<%= updateResultP2PActivitiesURL %>'/>
		<div>
			<a onClick="openLogs('/custom_logs/updateResultP2PActivities.txt')" style="Cursor:pointer;">updateResultP2PActivities.txt</a>
		</div>
	</div>
	
	<portlet:actionURL name="updateExtraContentMultimediaActivities" var="updateExtraContentMultimediaActivitiesURL" />
	<div class="action">
		<h4><liferay-ui:message key="portaladmin.multimedia.updateextracontent" /></h4>
		<aui:form action="<%=updateExtraContentMultimediaActivitiesURL %>" method="POST" name="form_mail">
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="send" label="portaladmin.multimedia.updatebd" class="submit" ></aui:button>
			</aui:button-row>
		</aui:form>
	</div>
	
	<portlet:actionURL name="updateExtraContentScormActivities" var="updateExtraContentScormActivitiesURL" />
	<div class="action">
		<h4><liferay-ui:message key="portaladmin.scorm.updateextracontent" /></h4>
		<aui:form action="<%=updateExtraContentScormActivitiesURL %>" method="POST" name="form_mail">
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="send" label="portaladmin.multimedia.updatebd" class="submit" ></aui:button>
			</aui:button-row>
		</aui:form>
	</div>
	
	<portlet:actionURL name="deleteRepeatedModuleResult" var="deleteRepeatedModuleResultURL" />
	<div class="action">
		<h4><liferay-ui:message key="portaladmin.multimedia.deleteRepeatedModuleResult" /></h4>
		<aui:form action="<%=deleteRepeatedModuleResultURL %>" method="POST" name="form_mail">
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="update" class="submit" ></aui:button>
			</aui:button-row>
		</aui:form>
		<div>
			<a onClick="openLogs('/custom_logs/deleteRepeatedModuleResult.txt')" style="Cursor:pointer;">deleteRepeatedModuleResult.txt</a>
		</div>
	</div>

	<div class="resultado">
		<%=res %>
	</div>

</div>
<%
}
%>