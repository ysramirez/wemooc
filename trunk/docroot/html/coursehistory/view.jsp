
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>

<%@ include file="/init.jsp"%>
<%
java.util.List<Group> groups= GroupLocalServiceUtil.getUserGroups(themeDisplay.getUserId());
	
for(Group groupCourse:groups)
{
	
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(groupCourse.getGroupId());
	CourseLocalServiceUtil.
	if(course!=null&&course.isClosed())
	{
	%>
	
	<ul>
		<%
		if(groupCourse.getPublicLayoutSet().getLogo())
		{
			long logoId = groupCourse.getPublicLayoutSet().getLogoId();
			%>
			
			<li class="course-title">
				<img src="/image/layout_set_logo?img_id=<%=logoId%>">
				<%=course.getTitle(themeDisplay.getLocale()) %>
			</li>
			<%
		} 
		else 
		{
			%>
			<li class="course-no-image" ><%=course.getTitle(themeDisplay.getLocale()) %></li>
		<%
		}
				
		
	}
	%>
	</ul>
	<%
}
%>