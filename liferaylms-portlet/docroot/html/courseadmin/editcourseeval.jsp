<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEval"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry"%>
<%@ include file="/init.jsp" %>
<% 
	long courseId = ParamUtil.getLong(renderRequest, "courseId");
	long courseEvalId = ParamUtil.getLong(renderRequest, "courseEvalId");
	CourseEval courseEval = new CourseEvalRegistry().getCourseEval(courseEvalId);

	if(Validator.isNotNull(courseEval.getExpecificContentPage())) { %>
	<liferay-util:include page="<%=courseEval.getExpecificContentPage() %>" servletContext="<%=getServletContext() %>" portletId="<%= courseEval.getPortletId() %>">
		<liferay-util:param name="courseId" value="<%=Long.toString(courseId) %>" />
	</liferay-util:include>	
<% } %>