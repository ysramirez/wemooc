<%@ include file="/init.jsp" %>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.model.UserGroupRole"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.UserGroupRoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%
long courseId=ParamUtil.getLong(request, "courseId",0);
long roleId=ParamUtil.getLong(request, "roleId",0);
Course course=CourseLocalServiceUtil.getCourse(courseId);
long createdGroupId=course.getGroupCreatedId();
Role role=RoleLocalServiceUtil.getRole(roleId);
Role commmanager=RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER) ;
java.util.List<User> users=new java.util.ArrayList<User>();
if(roleId!=commmanager.getRoleId())
{
	java.util.List<UserGroupRole> ugrs=UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroupAndRole(createdGroupId, roleId);

users=new java.util.ArrayList<User>();
for(UserGroupRole ugr:ugrs)
{
	users.add(ugr.getUser());
}
}
else
{
	users=UserLocalServiceUtil.getGroupUsers(createdGroupId);
}
%>
<liferay-portlet:renderURL var="backURL"></liferay-portlet:renderURL>

<liferay-ui:header title="<%=course.getTitle(themeDisplay.getLocale()) %>" backURL="<%=backURL %>"></liferay-ui:header>
<h2><%=role.getTitle(themeDisplay.getLocale()) %></h2>
<portlet:renderURL var="adduserURL">
<portlet:param name="jspPage" value="/html/courseadmin/usersresults.jsp">
<liferay-portlet:param name="courseId" value="<%=Long.toString(courseId) %>"></liferay-portlet:param>
<liferay-portlet:param name="roleId" value="<%=Long.toString(roleId) %>"></liferay-portlet:param>

</portlet:param>
</portlet:renderURL>

<liferay-ui:icon
image="add"
label="<%= true %>"
message="add"
url='<%= adduserURL %>'
/>
<%
PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/courseadmin/rolemembers.jsp");
portletURL.setParameter("courseId",Long.toString(courseId));
portletURL.setParameter("roleId",Long.toString(roleId));
%>

<liferay-ui:search-container emptyResultsMessage="there-are-no-users"
 delta="10" iteratorURL="<%=portletURL%>">
<liferay-ui:search-container-results>
<%
results = ListUtil.subList(users, searchContainer.getStart(),
searchContainer.getEnd());
total = users.size();
pageContext.setAttribute("results", results);
pageContext.setAttribute("total", total);
%>
</liferay-ui:search-container-results>
<liferay-ui:search-container-row
className="com.liferay.portal.model.User"
keyProperty="userId"
modelVar="user">
<liferay-ui:search-container-column-text>
<liferay-ui:user-display userId="<%=user.getUserId() %>"></liferay-ui:user-display>
</liferay-ui:search-container-column-text>
<liferay-ui:search-container-column-text>
<liferay-portlet:actionURL name="removeUserRole" var="removeUserRoleURL">
<liferay-portlet:param name="jspPage" value="/html/courseadmin/rolemembers.jsp"></liferay-portlet:param>
<liferay-portlet:param name="courseId" value="<%=Long.toString(courseId) %>"></liferay-portlet:param>
<liferay-portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"></liferay-portlet:param>
<liferay-portlet:param name="roleId" value="<%=Long.toString(roleId) %>"></liferay-portlet:param>

</liferay-portlet:actionURL>
<liferay-ui:icon-delete url="<%=removeUserRoleURL %>" label="delete"></liferay-ui:icon-delete>
</liferay-ui:search-container-column-text>
</liferay-ui:search-container-row>
<liferay-ui:search-iterator />

</liferay-ui:search-container>