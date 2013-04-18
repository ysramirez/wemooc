<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.SCORMContentLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.SCORMContentServiceUtil"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<liferay-ui:icon-menu>
<%
ResultRow row =
	(ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	 
SCORMContent myScorm = (SCORMContent)row.getObject();
LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
String name = SCORMContent.class.getName();
String primKey = String.valueOf(myScorm.getScormId());

%>
<portlet:actionURL name="deleteSCORM" var="deleteURL">
<portlet:param name="scormId" value="<%= primKey %>" />
</portlet:actionURL>
<%
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  SCORMContent.class.getName(),primKey,ActionKeys.DELETE))
{
%>
<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />
<%
}
%>

<c:if test="<%= permissionChecker.hasPermission(myScorm.getGroupId(), SCORMContent.class.getName(), myScorm.getScormId(),
ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="<%=SCORMContent.class.getName() %>"
					modelResourceDescription="<%= myScorm.getTitle() %>"
					resourcePrimKey="<%= String.valueOf(myScorm.getScormId()) %>"
					var="permissionsURL"
				/>
				<liferay-ui:icon image="permissions" message="courseadmin.adminactions.permissions" url="<%=permissionsURL %>" />			
			</c:if>

</liferay-ui:icon-menu>
