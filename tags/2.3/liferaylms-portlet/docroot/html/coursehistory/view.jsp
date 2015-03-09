
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.model.CourseResult"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>

<%@ include file="/init.jsp"%>
<%
java.util.List<Group> groups= GroupLocalServiceUtil.getUserGroups(themeDisplay.getUserId());
java.util.List<Course> courses=new ArrayList<Course>();
for(Group groupCourse:groups)
{
	
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(groupCourse.getGroupId());
	if(course!=null&&course.isClosed())
	{
	
		courses.add(course);
				
		
	}
	
}
if(courses.size()>0)
{
%>
<liferay-ui:search-container emptyResultsMessage="there-are-no-courses" delta="10">
	<liferay-ui:search-container-results>
		
		<%
		results = ListUtil.subList(courses, searchContainer.getStart(), searchContainer.getEnd());
		total = courses.size();
		pageContext.setAttribute("results", results);
		pageContext.setAttribute("total", total);
		%>

		</liferay-ui:search-container-results>
			<liferay-ui:search-container-row className="com.liferay.lms.model.Course" keyProperty="courseId" modelVar="course">
	<%
		
		Group groupCourse= GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
		CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), themeDisplay.getUserId());
	
	
	%>
	<liferay-ui:search-container-column-text title="course">
	<%
		if(groupCourse.getPublicLayoutSet().getLogo())
		{
			long logoId = groupCourse.getPublicLayoutSet().getLogoId();
			%>	
				<img src="/image/layout_set_logo?img_id=<%=logoId%>">
				<%=course.getTitle(themeDisplay.getLocale()) %>
			<%
		} 
		else 
		{
			%>
			<%=course.getTitle(themeDisplay.getLocale()) %> 
		<%
		}
		%>
	</liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text align="right">
	<%
	if(courseResult!=null)
	{
	%>
	<%=courseResult.getResult() %>
	 <%
	if(courseResult.isPassed())
	{
		%>
		<liferay-ui:icon image="checked" alt="passed"></liferay-ui:icon>
		<%
	}
		%>
	<%
	}
	else
	{
	%>
	0
	<%
	}
	%>
	
	</liferay-ui:search-container-column-text>
</liferay-ui:search-container-row>
			<liferay-ui:search-iterator />

</liferay-ui:search-container>
<%
}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
%>
