<%@include file="/init.jsp" %>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.util.comparator.UserFirstNameComparator"%>
<%@page import="com.liferay.portal.kernel.dao.orm.CustomSQLParam"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseServiceUtil"%>
<%@page import="com.liferay.portal.model.UserGroupRole"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.UserGroupRoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%

long courseId=ParamUtil.getLong(request, "courseId",0);
long roleId=ParamUtil.getLong(request, "roleId",0);
Role role=RoleLocalServiceUtil.getRole(roleId);
Course course=CourseLocalServiceUtil.getCourse(courseId);
boolean backToEdit = ParamUtil.getBoolean(request, "backToEdit");
String redirectOfEdit = ParamUtil.getString(request, "redirectOfEdit");
String firstName = ParamUtil.getString(request,"firstName");
String lastName = ParamUtil.getString(request,"lastName");
String screenName = ParamUtil.getString(request,"screenName");	
String emailAddress = ParamUtil.getString(request,"emailAddress");
boolean andSearch = ParamUtil.getBoolean(request,"andSearch",true);

LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
Role commmanager=RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER) ;
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
portletURL.setParameter("jspPage","/html/courseadmin/usersresults.jsp");
//portletURL.setParameter("paginator","true");	
portletURL.setParameter("firstName", firstName); 
portletURL.setParameter("lastName", lastName);
portletURL.setParameter("screenName", screenName);
portletURL.setParameter("emailAddress", emailAddress);
portletURL.setParameter("andSearch",Boolean.toString(andSearch));
portletURL.setParameter("courseId",Long.toString(courseId));
portletURL.setParameter("roleId",Long.toString(roleId));
portletURL.setParameter("backToEdit",Boolean.toString(backToEdit));
if(backToEdit) {
	portletURL.setParameter("backToEdit",redirectOfEdit);
}

%>


<liferay-portlet:renderURL var="backURL" >
	<portlet:param name="jspPage" value="/html/courseadmin/rolememberstab.jsp" />
	<portlet:param name="courseId" value="<%=Long.toString(courseId) %>" />
	<portlet:param name="roleId" value="<%=Long.toString(roleId) %>" />
	<portlet:param name="tabs1" value="<%=tab %>" />
	<portlet:param name="backToEdit" value="<%=Boolean.toString(backToEdit) %>" />
	<c:if test="<%=backToEdit %>">
		<portlet:param name="redirectOfEdit" value='<%=redirectOfEdit %>'/>
	</c:if>
</liferay-portlet:renderURL>
<liferay-ui:header title="<%=course.getTitle(themeDisplay.getLocale()) %>" backURL="<%=backURL %>"></liferay-ui:header>
<h2><%=role.getTitle(themeDisplay.getLocale()) %></h2>
<jsp:include page="/html/courseadmin/search_form.jsp" />
<liferay-ui:search-container iteratorURL="<%=portletURL%>"
   		emptyResultsMessage="there-are-no-results"
   		delta="10" deltaConfigurable="true" >

   	<liferay-ui:search-container-results>
   	
   	
<%
	String middleName = null;

	if (Validator.isNull(firstName)) {
		firstName = null;
	}
	else {
		firstName = addWildcards(firstName);
	}
	
	if (Validator.isNull(lastName)) {
		lastName = null;
	}
	else {
		lastName = addWildcards(lastName);
	}
	
	if (Validator.isNull(screenName)) {
		screenName = null;
	}
	else {
		screenName = addWildcards(screenName);
	}
	
	if (Validator.isNull(emailAddress)) {
		emailAddress = null;
	}
	else {
		emailAddress = addWildcards(emailAddress);
	}
	
	LinkedHashMap<String,Object> params=new LinkedHashMap<String,Object>();			
	

	OrderByComparator obc = new UserFirstNameComparator(true);
	
	params.put("notInCourseRole",new CustomSQLParam("WHERE User_.userId NOT IN "+
	                                                " (SELECT UserGroupRole.userId "+
	                                                "  FROM UserGroupRole "+
	                                                "  WHERE  (UserGroupRole.groupId = ?) AND (UserGroupRole.roleId = ?))",new Long[]{course.getGroupCreatedId(),roleId}));
	/*
	if (new Long(roleId).equals(prefs.getTeacherRole()) || new Long(roleId).equals(prefs.getEditorRole())) {
		params.put("inRole", new CustomSQLParam("WHERE User_.userId IN (SELECT UserGroupRole.userId "+
            "  FROM UserGroupRole "+
            "  WHERE UserGroupRole.roleId = ?)", new Long[]{roleId}));
	}
	*/
	List<User> userListPage = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), firstName, middleName, lastName, screenName, emailAddress, 0, params, andSearch, searchContainer.getStart(), searchContainer.getEnd(), obc);
	int userCount =  UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), firstName, middleName, lastName, screenName, emailAddress, 0, params, andSearch);

	pageContext.setAttribute("results", userListPage);
    pageContext.setAttribute("total", userCount);

%>
	</liferay-ui:search-container-results>
	
	<liferay-ui:search-container-row className="com.liferay.portal.model.User"
     		keyProperty="userId"
     		modelVar="user">
<liferay-ui:search-container-column-text>
<liferay-ui:user-display userId="<%=user.getUserId() %>"></liferay-ui:user-display>
</liferay-ui:search-container-column-text> 
<liferay-ui:search-container-column-text>

<%
%>


<liferay-portlet:actionURL name="addUserRole" var="addUserRoleURL">
	<liferay-portlet:param name="jspPage" value="/html/courseadmin/rolememberstab.jsp"/>
	<liferay-portlet:param name="courseId" value="<%=Long.toString(courseId) %>"/>
	<liferay-portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"/>
	<liferay-portlet:param name="roleId" value="<%=Long.toString(roleId) %>"/>
	<liferay-portlet:param name="tabs1" value="<%=tab %>"/>
	<liferay-portlet:param name="backToEdit" value="<%=Boolean.toString(backToEdit) %>" />
	<c:if test="<%=backToEdit %>">
		<liferay-portlet:param name="redirectOfEdit" value='<%=redirectOfEdit %>'/>
	</c:if>
</liferay-portlet:actionURL>
<a class="newitem2" href="<%=addUserRoleURL %>" ><liferay-ui:message key="assign-member" /></a>
</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>
 	<liferay-ui:search-iterator />
</liferay-ui:search-container>
<%!
	public static String addWildcards(String value)
	{
		if (value == null) return null;
		if (value.length() == 1) return "%" + value + "%";
		if (value.charAt(0) != '%') value = "%" + value;
		if (value.charAt(value.length() - 1) != '%') value = value + "%";
		return value;
	}
%>
