<%@page import="com.liferay.lms.service.CompetenceServiceUtil"%>
<%@page import="java.util.Enumeration"%>
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
	ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
		 
	Course myCourse = (Course)row.getObject();
	LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
	String name = Course.class.getName();
	String primKey = String.valueOf(myCourse.getCourseId());
	
	long count = 0;
	long countGroup = CompetenceServiceUtil.getCountCompetencesOfGroup(myCourse.getGroupCreatedId());
	long countParentGroup = CompetenceServiceUtil.getCountCompetencesOfGroup(myCourse.getGroupId());
	count = countGroup + countParentGroup;

%>
<liferay-ui:icon-menu>

	<portlet:actionURL name="cleanAllCourses" var="cleanAllCoursesURL">
		<portlet:param name="action" value="cleanAllCourses" />
	</portlet:actionURL>
	<liferay-ui:icon label="manager.module.cleancourses" url="<%=cleanAllCoursesURL.toString() %>" />

	<portlet:actionURL name="cleanModuleAllUsers" var="cleanModuleAllUsersURL">
		<portlet:param name="action" value="cleanAllCourses" />
	</portlet:actionURL>
	<liferay-ui:icon label="manager.module.cleanmoduleallusers" url="<%=cleanModuleAllUsersURL.toString() %>" />

	<portlet:actionURL name="cleanModuleUser" var="cleanModuleUserURL">
		<portlet:param name="action" value="cleanAllCourses" />
	</portlet:actionURL>
	<liferay-ui:icon label="manager.module.cleanmoduleuser" url="<%=cleanModuleUserURL.toString() %>" />

	<portlet:actionURL name="cleanModuleUserAct" var="cleanModuleUserActURL">
		<portlet:param name="action" value="cleanAllCourses" />
	</portlet:actionURL>
	<liferay-ui:icon label="manager.module.cleanmoduleuseractivity" url="<%=cleanModuleUserActURL.toString() %>" />

</liferay-ui:icon-menu> 