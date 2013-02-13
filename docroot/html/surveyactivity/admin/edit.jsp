<%@ include file="/html/surveyactivity/admin/init.jsp" %>
<liferay-util:include page="/html/surveyactivity/admin/editquestions.jsp" servletContext="<%=this.getServletContext() %>">
	<liferay-util:param value="<%=Long.toString(learnact.getActId()) %>" name="actId"/>
</liferay-util:include>