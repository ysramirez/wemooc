
<%@include file="../init.jsp" %>

<%@ page import="com.liferay.lms.model.Module" %>
<%@ page import="com.liferay.portlet.PortalPreferences" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>


<jsp:useBean id="addmoduleURL" class="java.lang.String" scope="request" />
<jsp:useBean id="moduleFilterURL" class="java.lang.String" scope="request" />
<jsp:useBean id="moduleFilter" class="java.lang.String" scope="request" />

<link rel="stylesheet" type="text/css" href="/Lms-portlet/css/Portlet_module.css" />

<liferay-ui:success key="module-prefs-success" message="module-prefs-success" />
<liferay-ui:success key="module-added-successfully" message="module-added-successfully" />
<liferay-ui:success key="module-deleted-successfully" message="module-deleted-successfully" />
<liferay-ui:success key="module-updated-successfully" message="module-updated-successfully" />
<liferay-ui:error key="module-error-deleting" message="module-error-deleting" />
<liferay-ui:error key="dependent-rows-exist-error-deleting" message="dependent-rows-exist-error-deleting" />

<%
long moduleId=ParamUtil.getLong(request,"moduleId",0);
boolean actionEditing=ParamUtil.getBoolean(request,"actionEditing",false);

if((Boolean)request.getAttribute("hasAddPermission")&&actionEditing)
{
	
%>
<div class="newitem">
<liferay-ui:icon image="add" label="true" message="new" url="<%=addmoduleURL.toString() %>" />
</div>

<%
}
	String iconChecked = "checked";
	String iconUnchecked = "unchecked";
	int rows_per_page = new Integer(prefs.getValue("module-rows-per-page", "5"));
	if (Validator.isNotNull(moduleFilter) || !moduleFilter.equalsIgnoreCase("")) {
		rows_per_page = 100;
	}
	
	SimpleDateFormat dateFormat = new SimpleDateFormat(prefs.getValue("module-date-format", "yyyy/MM/dd"));
	SimpleDateFormat dateTimeFormat = new SimpleDateFormat(prefs.getValue("module-datetime-format","yyyy/MM/dd HH:mm"));

	PortalPreferences portalPrefs = PortletPreferencesFactoryUtil.getPortalPreferences(request);

	String orderByCol = ParamUtil.getString(request, "orderByCol");
	String orderByType = ParamUtil.getString(request, "orderByType");

	if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
		portalPrefs.setValue("module_order", "module-order-by-col", orderByCol);
		portalPrefs.setValue("module_order", "module-order-by-type", orderByType);
	} else {
		orderByCol = portalPrefs.getValue("module_order", "module-order-by-col", "moduleId");
		orderByType = portalPrefs.getValue("module_order", "module-order-by-type", "asc");
	}
%>
<liferay-ui:search-container  delta='<%= rows_per_page %>' emptyResultsMessage="module-empty-results-message" orderByCol="<%= orderByCol%>" orderByType="<%= orderByType%>">
	<liferay-ui:search-container-results>

		<%
		int containerStart;
		int containerEnd;
		try {
			containerStart = ParamUtil.getInteger(request, "containerStart");
			containerEnd = ParamUtil.getInteger(request, "containerEnd");
		} catch (Exception e) {
			containerStart = searchContainer.getStart();
			containerEnd = searchContainer.getEnd();
		}
		if (containerStart <=0) {
			containerStart = searchContainer.getStart();
			containerEnd = searchContainer.getEnd();
		}
		
		List<Module> tempResults = (List<Module>)request.getAttribute("tempResults");
		results = ListUtil.subList(tempResults, containerStart, containerEnd);
		total = tempResults.size();

		pageContext.setAttribute("results", results);
		pageContext.setAttribute("total", total);

		request.setAttribute("containerStart",String.valueOf(containerStart));
		request.setAttribute("containerEnd",String.valueOf(containerEnd));
		%>

	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.lms.model.module"
		keyProperty="moduleId"
		modelVar="module"
	>

<portlet:renderURL var="selectModuleURL">
	<portlet:param name="moduleId" value="<%=Long.toString(module.getModuleId())%>" />
	<portlet:param name="actId" value="0" />
</portlet:renderURL>

<portlet:renderURL var="cancelModuleURL">
	<portlet:param name="moduleId" value="0" />
	<portlet:param name="actId" value="0" />
</portlet:renderURL>


		<liferay-ui:search-container-column-text
			name="title" align="left">
			<%if(module.getModuleId()!=moduleId)
			{
			%>
			<a href="<%=selectModuleURL.toString() %>"><%=module.getTitle(themeDisplay.getLocale()) %></a>
			<%
			}
			else
			{
				%>
			<b></b><a href="<%=cancelModuleURL.toString() %>">>><%=module.getTitle(themeDisplay.getLocale()) %></a></b>
			<%
			}
			%>
		</liferay-ui:search-container-column-text>
		<%if(actionEditing)
			{
			%>
		<liferay-ui:search-container-column-jsp
			align="right"
			path="/JSPs/module/edit_actions.jsp"
		/>
		<%
			}
		%>

	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />

</liferay-ui:search-container>
