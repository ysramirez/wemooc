<%@include file="/init.jsp" %>

<liferay-ui:icon-menu showWhenSingleIcon="<%=true%>">

	<portlet:renderURL var="removeRepeatedModuleResultURL">
		<portlet:param name="courseId" value="${param.courseId}" />
		<portlet:param name="jspPage" value="/html/views/deleterepeatedmodule.jsp" />
	</portlet:renderURL>
	<liferay-ui:icon message="actmanager.delete-repeated-module-tries" url="<%=removeRepeatedModuleResultURL.toString() %>" />
	
	<portlet:renderURL var="removeRepeatedModuleResultURL">
		<portlet:param name="courseId" value="${param.courseId}" />
		<portlet:param name="jspPage" value="/html/views/deleterepeatedlearningtries.jsp" />
	</portlet:renderURL>
	<liferay-ui:icon message="actmanager.delete-repeated-learning-tries" url="<%=removeRepeatedModuleResultURL.toString() %>" />
	
</liferay-ui:icon-menu> 