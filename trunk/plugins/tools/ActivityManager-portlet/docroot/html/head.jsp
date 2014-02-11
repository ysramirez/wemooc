<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ include file="/html/init.jsp" %>

<liferay-ui:error message="you-do-not-have-the-required-permissions-to-access-this-application" key="error1"/>
<liferay-ui:error message="error-type" key="ko"/>

<liferay-ui:success message="your-request-completed-successfully" key="ok" />

<c:if test="${active}">
	<span><liferay-ui:message key="actmanager.otherprocess" /></span>
</c:if>
 
<portlet:renderURL var="portletURL" />
<h3>
	<a href="${portletURL}"><%=themeDisplay.getPortletDisplay().getPortletName() %></a> 
	<c:if test="${not empty course}">
		<portlet:actionURL var="viewCourseURL" name="viewCourse">
	    	<portlet:param name="id" value="${course.primaryKey}" />
		</portlet:actionURL>
			> <a href="${viewCourseURL}">${course.getTitle(themeDisplay.locale)}</a>
	 		<c:if test="${not empty la}">
	 			> ${la.getTitle(themeDisplay.locale)}
	 		</c:if>
	</c:if> 
</h3>

<liferay-ui:icon-menu id="options" showWhenSingleIcon="<%=true %>">
	<portlet:renderURL var="viewPortletTitlesURL" >   
	    <portlet:param name="jspPage" value="/html/views/portlettitles.jsp" />           
	</portlet:renderURL>
	<liferay-ui:icon image="assign" message="actmanager.viewportlettitles"  url="<%=viewPortletTitlesURL.toString() %>"  />
</liferay-ui:icon-menu>