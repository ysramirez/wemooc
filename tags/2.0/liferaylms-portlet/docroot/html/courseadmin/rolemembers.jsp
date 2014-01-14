<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.util.comparator.UserFirstNameComparator"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.util.JS"%>
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
LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
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
	java.util.List<User> userst=UserLocalServiceUtil.getGroupUsers(createdGroupId);
	
	for(User usert:userst)
	{
		List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(usert.getUserId(),createdGroupId);
		boolean remove =false;
		for(UserGroupRole ugr:userGroupRoles){
			if(ugr.getRoleId()==prefs.getEditorRole()||ugr.getRoleId()==prefs.getTeacherRole()){
				remove = true;
				break;
			}
		}
		if(!remove){
			users.add(usert);
		}
	}
}
%>
<liferay-portlet:renderURL var="backURL"></liferay-portlet:renderURL>

<liferay-util:buffer var="toolbar">
	<jsp:include page="toolbar.jsp"/>
</liferay-util:buffer>


<% if(!toolbar.trim().isEmpty()){ %>
	<div class="container-toolbar" >
		<%=toolbar %>
	</div>
<% } %>
<%
	String teacherName=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getTitle(locale);
	String editorName=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getTitle(locale);
	String tab="";
	if(roleId==commmanager.getRoleId()){
		tab =  LanguageUtil.get(pageContext,"courseadmin.adminactions.students");
	}else if(roleId==prefs.getEditorRole()){
		tab = editorName;
	}else{
		tab = teacherName;
	}
	
PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/courseadmin/rolememberstab.jsp");
portletURL.setParameter("courseId",Long.toString(courseId));
portletURL.setParameter("roleId",Long.toString(roleId));
portletURL.setParameter("tabs1",tab);
%>

<liferay-ui:search-container emptyResultsMessage="there-are-no-users"
 delta="10" deltaConfigurable="true" iteratorURL="<%=portletURL%>">
<liferay-ui:search-container-results>
<%

List<User> orderedUsers = new ArrayList<User>();
orderedUsers.addAll(users);
Collections.sort(orderedUsers, new Comparator<User>() {
    @Override
    public int compare(final User object1, final User object2) {
        return object1.getFullName().toLowerCase().compareTo(object2.getFullName().toLowerCase());
    }
} );

results = ListUtil.subList(orderedUsers, searchContainer.getStart(),
searchContainer.getEnd());
if(results.size()<=0){
	results = ListUtil.subList(orderedUsers,0,searchContainer.getDelta());
}
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
<liferay-portlet:param name="jspPage" value="/html/courseadmin/rolememberstab.jsp"></liferay-portlet:param>
<liferay-portlet:param name="courseId" value="<%=Long.toString(courseId) %>"></liferay-portlet:param>
<liferay-portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"></liferay-portlet:param>
<liferay-portlet:param name="roleId" value="<%=Long.toString(roleId) %>"></liferay-portlet:param>
<liferay-portlet:param name="tabs1" value="<%=tab %>"></liferay-portlet:param>

</liferay-portlet:actionURL>
<liferay-ui:icon-delete url="<%=removeUserRoleURL+\"&cur=\"+searchContainer.getCur() %>" label="delete"></liferay-ui:icon-delete>
</liferay-ui:search-container-column-text>
</liferay-ui:search-container-row>
<liferay-ui:search-iterator />

</liferay-ui:search-container>