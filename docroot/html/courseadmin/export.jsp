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
		
		<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= true %>" />
		<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>" type="hidden" value="<%= true %>" />
		
		<liferay-ui:message key="what-would-you-like-to-export" />
	
		<aui:input label="pages" 								name="pages" disabled="<%= true %>"   type="checkbox" 										value="<%= true %>" />
		<aui:input label="portlets" 							name="portlets" disabled="<%= true %>"  type="checkbox" 									value="<%= true %>" />
		<aui:input label="setup" 								name="<%= PortletDataHandlerKeys.PORTLET_SETUP %>" type="checkbox" 							value="<%= false %>" />
		<aui:input label="archived-setups" 						name="<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS %>" type="checkbox" 				value="<%= false %>" />
		<aui:input label="user-preferences" 					name="<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES %>" type="checkbox" 				value="<%= false %>" />
		<aui:input label="data"  								name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="checkbox" 							value="<%= false %>" />
		<aui:input label="mirror" checked="<%= true %>" 		name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" 							value="<%= PortletDataHandlerKeys.DATA_STRATEGY_MIRROR %>" />
		<aui:input label="copy-as-new" 							name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" 							value="<%= PortletDataHandlerKeys.DATA_STRATEGY_COPY_AS_NEW %>" />
		<aui:input label="delete-portlet-data-before-importing" name="<%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>" type="checkbox" />
		<aui:input label="permissions" 							name="<%= PortletDataHandlerKeys.PERMISSIONS %>" type="checkbox" />
		<aui:input label="theme" 								name="<%= PortletDataHandlerKeys.THEME %>" type="checkbox" 									value="<%= false %>" />
		<aui:input label="categories" 							name="<%= PortletDataHandlerKeys.CATEGORIES %>" type="checkbox" 							value="<%= false %>" />
		<aui:input label="data" 								name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="checkbox" 							value="<%= false %>" />
		<aui:input label="all" checked="<%= true %>"   			name="range" onClick='<%= renderResponse.getNamespace() + "hideDateRange()" %>' type="radio" value="all" />
		<aui:input label="from-last-publish-date"  				name="range" onClick='<%= renderResponse.getNamespace() + "hideDateRange()" %>' type="radio" value="fromLastPublishDate" />
		<aui:input label="date-range" 							name="range" onClick='<%= renderResponse.getNamespace() + "showDateRange()" %>' type="radio" value="dateRange" />	
		
	</div>
		
	<aui:button-row>
		<aui:button type="submit" value="export" />
	</aui:button-row>
	
</aui:form>