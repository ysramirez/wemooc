

<%@include file="/init.jsp" %>
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


<%
long courseId=ParamUtil.getLong(request, "courseId",0);
long roleId=ParamUtil.getLong(request, "roleId",0);
Role role=RoleLocalServiceUtil.getRole(roleId);
Course course=CourseLocalServiceUtil.getCourse(courseId);
String firstName = request.getParameter("firstName");
String lastName = request.getParameter("lastName");
String screenName = request.getParameter("screenName");	
String emailAddress = request.getParameter("emailAddress");
String andSearchStr = request.getParameter("andSearch");

if (firstName == null) firstName = "";	
if (lastName == null) lastName = "";
if (screenName == null) screenName = "";
if (emailAddress == null) emailAddress = "";	
if (andSearchStr == null) andSearchStr = "true";


PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/courseadmin/usersresults.jsp");
//portletURL.setParameter("paginator","true");	
portletURL.setParameter("firstName", firstName); 
portletURL.setParameter("lastName", lastName);
portletURL.setParameter("screenName", screenName);
portletURL.setParameter("emailAddress", emailAddress);
portletURL.setParameter("andSearch", andSearchStr);
portletURL.setParameter("courseId",Long.toString(courseId));
portletURL.setParameter("roleId",Long.toString(roleId));

%>


<liferay-portlet:renderURL var="backURL" >
<portlet:param name="courseId" value="<%=Long.toString(courseId) %>" />
<portlet:param name="roleId" value="<%=Long.toString(roleId) %>" />
<portlet:param name="jspPage" value="/html/courseadmin/rolemembers.jsp" />
</liferay-portlet:renderURL>
<liferay-ui:header title="<%=course.getTitle(themeDisplay.getLocale()) %>" backURL="<%=backURL %>"></liferay-ui:header>
<h2><%=role.getTitle(themeDisplay.getLocale()) %></h2>
<jsp:include page="/html/courseadmin/search_form.jsp" />
<liferay-ui:search-container iteratorURL="<%=portletURL%>"
   		emptyResultsMessage="there-are-no-results"
   		delta="10">

   	<liferay-ui:search-container-results>
   	
   	
<%
	String middleName = null;

	if (firstName != null && firstName.equals("")) firstName = null;
	firstName = addWildcards(firstName);
	
	if (lastName != null && lastName.equals("")) lastName = null;
	lastName = addWildcards(lastName);
	
	if (screenName != null && screenName.equals("")) screenName = null;
	screenName = addWildcards(screenName);
	
	if (emailAddress != null && emailAddress.equals("")) emailAddress = null;
	emailAddress = addWildcards(emailAddress);
	
	if (andSearchStr == null) andSearchStr = "true";
	boolean andSearch = Boolean.parseBoolean(andSearchStr);	

	
	LinkedHashMap<String,Object> params=null;			
	

	OrderByComparator obc = null;

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
<liferay-portlet:actionURL name="addUserRole" var="addUserRoleURL">
<liferay-portlet:param name="jspPage" value="/html/courseadmin/rolemembers.jsp"></liferay-portlet:param>
<liferay-portlet:param name="courseId" value="<%=Long.toString(courseId) %>"></liferay-portlet:param>
<liferay-portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"></liferay-portlet:param>
<liferay-portlet:param name="roleId" value="<%=Long.toString(roleId) %>"></liferay-portlet:param>

</liferay-portlet:actionURL>
<liferay-ui:icon image="add" url="<%=addUserRoleURL %>" label="add" message="add"> label="add" message="add"><liferay-ui:message key="assign-member" /></liferay-ui:icon>
</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>
 	<liferay-ui:search-iterator />
</liferay-ui:search-container>
	


<liferay-portlet:renderURL var="volverURL" windowState="NORMAL">
</liferay-portlet:renderURL>

<aui:button name="volverButton" value="back" onClick="<%= volverURL %>"></aui:button>

