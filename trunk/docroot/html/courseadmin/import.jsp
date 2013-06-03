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

<%@ include file="/init.jsp" %>	

<%
	String groupId = request.getParameter("groupId");

%>

<portlet:actionURL name="importCourse" var="importURL">
	<portlet:param name="groupId" value="<%= groupId %>" />
</portlet:actionURL>
	
<aui:form name="form" action="<%=importURL%>" method="post" enctype="multipart/form-data">
	
	<liferay-ui:error exception="<%= LARFileException.class %>" message="please-specify-a-lar-file-to-import" />
	<liferay-ui:error exception="<%= LARTypeException.class %>" message="please-import-a-lar-file-of-the-correct-type" />
	<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />
	
	<aui:input label="import-a-lar-file-to-overwrite-the-selected-data" name="importFileName" size="50" type="file" />
	
	<div class="options" style="display:none;">
	
		<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= true %>" />
	
		<liferay-ui:message key="what-would-you-like-to-export" />
		
		<aui:input label="pages"								name="pages" disabled="<%= true %>"  type="checkbox" 							value="<%=true %>" />
		<aui:input label="delete-missing-layouts" 				name="<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>" type="checkbox" 	value="<%= false %>" />
		<aui:input label="portlets" 							name="portlets" disabled="<%= true %>"  type="checkbox" 						value="<%=true %>" />
		<aui:input label="setup" 								name="<%= PortletDataHandlerKeys.PORTLET_SETUP %>" type="checkbox" 				value="<%= false %>" />
		<aui:input label="archived-setups" 						name="<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS %>" type="checkbox" 	value="<%= false %>" />
		<aui:input label="user-preferences" 					name="<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES %>" type="checkbox" 	value="<%= false %>" />
		<aui:input label="delete-portlet-data-before-importing" name="<%= PortletDataHandlerKeys.DELETE_PORTLET_DATA %>" type="checkbox" />
		<aui:input label="data"  								name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="checkbox" 				value="<%= true %>" />
		<aui:input label="mirror" 								name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" 				value="<%= PortletDataHandlerKeys.DATA_STRATEGY_MIRROR %>" />
		<aui:input label="copy-as-new" checked="<%= true %>" 	name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" 				value="<%= PortletDataHandlerKeys.DATA_STRATEGY_COPY_AS_NEW %>" />
		<aui:input label="if-a-user-id-does-not-exist,-then-use-my-user-id" name="<%= PortletDataHandlerKeys.USER_ID_STRATEGY %>" type="radio" 	value="<%= UserIdStrategy.CURRENT_USER_ID %>" />
		<aui:input label="always-use-my-user-id" 				name="<%= PortletDataHandlerKeys.USER_ID_STRATEGY %>" checked="<%= true %>"  type="radio" value="<%= UserIdStrategy.ALWAYS_CURRENT_USER_ID %>" />
		<aui:input label="permissions" 							name="<%= PortletDataHandlerKeys.PERMISSIONS %>" type="checkbox" 				value="<%= false %>" />
		<aui:input label="theme" 								name="<%= PortletDataHandlerKeys.THEME %>" type="checkbox" 						value="<%= false %>" />
		<aui:input label="categories" 							name="<%= PortletDataHandlerKeys.CATEGORIES %>" type="checkbox" 				value="<%= false %>" />
		
	</div>	
		
	<aui:button-row>
		<aui:button type="submit" value="import" />
	</aui:button-row>
</aui:form>