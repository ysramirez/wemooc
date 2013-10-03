<%@page import="java.util.Locale"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@ page import="com.liferay.portal.LARFileException" %>
<%@ page import="com.liferay.portal.LARTypeException" %>
<%@ page import="com.liferay.portal.LayoutImportException" %>

<%@ page import="com.liferay.portal.kernel.lar.PortletDataException" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandler" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerBoolean" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerChoice" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerControl" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerKeys" %>
<%@ page import="com.liferay.portal.kernel.lar.UserIdStrategy" %>
<%@ page import="com.liferay.portal.kernel.util.Time" %>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>

<%@ include file="/init.jsp" %>	

<%
	String groupId = request.getParameter("groupId");

	String name = groupId;
	
	Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(Long.valueOf(groupId));
	
	try{
		if(course != null){
			name = course.getTitle(Locale.getDefault());
		}
	}catch(Exception e){}
%>


<liferay-portlet:resourceURL var="exportURL" >
	<portlet:param name="action" value="export"/>
	<portlet:param name="groupId" value="<%=groupId %>"/>
</liferay-portlet:resourceURL>

	
<aui:form name="form" action="<%=exportURL%>" method="post" >

	<aui:input label="export-the-selected-data-to-the-given-lar-file-name" name="exportFileName" size="50" value='<%= HtmlUtil.escape(StringUtil.replace(name, " ", "_")) + "-" + Time.getShortTimestamp() + ".lar" %>' />
	
	<div class="options" style="display:none;">
		
		<liferay-ui:message key="what-would-you-like-to-export" />
	
		<%
		String rootPortletId = themeDisplay.getPortletDisplay().getRootPortletId();
		String taglibOnChange = renderResponse.getNamespace() + "toggleChildren(this, '" + renderResponse.getNamespace() + PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + rootPortletId + "Controls');";
		%>

		<aui:input label="data"			name="<%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + rootPortletId %>" type="checkbox" value="<%= true %>" onchange="<%= taglibOnChange %>" />
		<aui:input label="categories" 	name="<%= PortletDataHandlerKeys.CATEGORIES %>" 				type="checkbox" value="<%= false %>" />
		<aui:input label="permissions" 	name="<%= PortletDataHandlerKeys.PERMISSIONS %>" 				type="checkbox" value="<%= false %>" />
		<aui:input label="setup" 		name="<%= PortletDataHandlerKeys.PORTLET_SETUP %>" 				type="checkbox" value="<%= false %>" />
		<aui:input label="preferences" 	name="<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES %>" 	type="checkbox" value="<%= false %>" />
		<aui:input label="permissions" 	name="<%= PortletDataHandlerKeys.USER_PERMISSIONS %>" 			type="checkbox" value="<%= false %>" />
				
	</div>
		
	<aui:button-row>
		<aui:button type="submit" value="export" />
	</aui:button-row>
	
</aui:form>