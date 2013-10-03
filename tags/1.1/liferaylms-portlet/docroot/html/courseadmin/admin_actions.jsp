<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<%
ResultRow row =
	(ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	 
Course myCourse = (Course)row.getObject();
LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
String name = Course.class.getName();
String primKey = String.valueOf(myCourse.getCourseId());
%>
<liferay-ui:icon-menu>
<portlet:renderURL var="editURL">
<portlet:param name="courseId" value="<%=primKey %>" />
<portlet:param name="jspPage" value="/html/courseadmin/editcourse.jsp" />
</portlet:renderURL>
<%
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.UPDATE))
{
%>
<liferay-ui:icon image="edit" message="edit" url="<%=editURL.toString() %>" />

<%
}
%>

<portlet:actionURL name="deleteCourse" var="deleteURL">
<portlet:param name="courseId" value="<%= primKey %>" />
</portlet:actionURL>
<%
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,ActionKeys.DELETE))
{
%>
<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />
<%
}
%>
<portlet:renderURL var="teachersURL">
<portlet:param name="courseId" value="<%=primKey %>" />
<portlet:param name="roleId" value="<%=Long.toString(prefs.getTeacherRole()) %>" />
<portlet:param name="jspPage" value="/html/courseadmin/rolemembers.jsp" />
</portlet:renderURL>
<%
if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,"ASSIGN_MEMBERS"))
{
	String teacherName=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getTitle(locale);
%>

<liferay-ui:icon image="group" message="<%=teacherName %>" url="<%=teachersURL.toString() %>" />

<%
}
%>
<portlet:renderURL var="editorsURL">
<portlet:param name="courseId" value="<%=primKey %>" />
<portlet:param name="roleId" value="<%=Long.toString(prefs.getEditorRole()) %>" />
<portlet:param name="jspPage" value="/html/courseadmin/rolemembers.jsp" />
</portlet:renderURL>
<%
if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,"ASSIGN_MEMBERS"))
{
String editorName=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getTitle(locale);
%>
<liferay-ui:icon image="group" message="<%=editorName %>" url="<%=editorsURL.toString() %>" />

<%
}
Role commmanager=RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER) ;
%>
<portlet:renderURL var="studentsURL">
<portlet:param name="courseId" value="<%=primKey %>" />
<portlet:param name="roleId" value="<%=Long.toString(commmanager.getRoleId()) %>" />
<portlet:param name="jspPage" value="/html/courseadmin/rolemembers.jsp" />
</portlet:renderURL>
<%
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),primKey,"ASSIGN_MEMBERS"))
{
%>
<liferay-ui:icon image="group" message="courseadmin.adminactions.students" url="<%=studentsURL.toString() %>" />

<%
}
%>
<c:if test="<%= permissionChecker.hasPermission(myCourse.getGroupId(), Course.class.getName(), myCourse.getCourseId(), ActionKeys.PERMISSIONS) %>">
	<liferay-security:permissionsURL
		modelResource="<%=Course.class.getName() %>"
		modelResourceDescription="<%= myCourse.getTitle(themeDisplay.getLocale()) %>"
		resourcePrimKey="<%= String.valueOf(myCourse.getCourseId()) %>"
		var="permissionsURL"
	/>
	<liferay-ui:icon image="permissions" message="courseadmin.adminactions.permissions" url="<%=permissionsURL %>" />			

	<portlet:renderURL var="exportURL">
		<portlet:param name="groupId" value="<%=String.valueOf(myCourse.getGroupCreatedId()) %>" />
		<portlet:param name="jspPage" value="/html/courseadmin/export.jsp" />
	</portlet:renderURL>
	<liferay-ui:icon image="download" message="courseadmin.adminactions.export" url="<%=exportURL %>" />			

	<portlet:renderURL var="importURL">
		<portlet:param name="groupId" value="<%=String.valueOf(myCourse.getGroupCreatedId()) %>" />
		<portlet:param name="jspPage" value="/html/courseadmin/import.jsp" />
	</portlet:renderURL>
	<liferay-ui:icon image="post" message="courseadmin.adminactions.import" url="<%=importURL %>" />			

	<portlet:renderURL var="cloneURL">
		<portlet:param name="groupId" value="<%=String.valueOf(myCourse.getGroupCreatedId()) %>" />
		<portlet:param name="jspPage" value="/html/courseadmin/clone.jsp" />
	</portlet:renderURL>
	<% //<liferay-ui:icon image="copy" message="courseadmin.adminactions.clone" url="<%=cloneURL " /> %>			
</c:if>

</liferay-ui:icon-menu>