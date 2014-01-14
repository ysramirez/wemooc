<%@page import="com.liferay.lms.model.LearningActivity"%>

<%

LearningActivity myActivity = activity;

String name = LearningActivity.class.getName();
String primKey = String.valueOf(myActivity.getActId());
%>

<c:if test="<%= permissionChecker.hasPermission(myActivity.getGroupId(), LearningActivity.class.getName(), myActivity.getActId(),
ActionKeys.UPDATE)||permissionChecker.hasPermission(myActivity.getGroupId(), LearningActivity.class.getName(), myActivity.getActId(),
		ActionKeys.DELETE)||permissionChecker.hasPermission(myActivity.getGroupId(), LearningActivity.class.getName(), myActivity.getActId(),
				ActionKeys.PERMISSIONS)%>">
				
<c:if test="<%= permissionChecker.hasPermission(myActivity.getGroupId(),LearningActivity.class.getName(),myActivity.getActId(),ActionKeys.UPDATE) %>">
	<liferay-portlet:actionURL portletName="editactivity_WAR_liferaylmsportlet" name="editactivityoptions" var="editoptionsURL">
		<liferay-portlet:param name="resId" value="<%=primKey %>" />
		<portlet:param name="actId" value="0" />
	</liferay-portlet:actionURL>
	<liferay-ui:icon image="edit" message="edit" url="<%=editoptionsURL.toString() %>" />
</c:if>


<c:if test="<%= permissionChecker.hasPermission(myActivity.getGroupId(), LearningActivity.class.getName(), myActivity.getActId(),
ActionKeys.DELETE) %>">
<portlet:actionURL name="deleteactivity" var="deleteURL">
<portlet:param name="resId" value="<%= primKey %>" />
<portlet:param name="actId" value="0" />

</portlet:actionURL>
<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />
</c:if>
<c:if test="<%= permissionChecker.hasPermission(myActivity.getGroupId(), LearningActivity.class.getName(), myActivity.getActId(),
ActionKeys.UPDATE) %>">
<portlet:actionURL name="downactivity" var="downURL">
<portlet:param name="actId" value="<%=primKey %>" />
<portlet:param name="resId" value="<%=primKey %>" />
</portlet:actionURL>


<liferay-ui:icon image="bottom"  url="<%=downURL.toString() %>" />
<portlet:actionURL name="upactivity" var="upURL">
<portlet:param name="actId" value="<%=primKey %>" />
<portlet:param name="resId" value="<%=primKey %>" />
</portlet:actionURL>


<liferay-ui:icon image="top"  url="<%=upURL.toString() %>" />

</c:if>

<c:if test="<%= permissionChecker.hasPermission(myActivity.getGroupId(), LearningActivity.class.getName(), myActivity.getActId(),
ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="<%=LearningActivity.class.getName() %>"
					modelResourceDescription="<%= myActivity.getTitle(themeDisplay.getLocale()) %>"
					resourcePrimKey="<%= String.valueOf(myActivity.getActId()) %>"
					var="permissionsURL"
				/>
				<liferay-ui:icon image="permissions" message="permissions" url="<%=permissionsURL %>" />			
			</c:if>
</c:if>