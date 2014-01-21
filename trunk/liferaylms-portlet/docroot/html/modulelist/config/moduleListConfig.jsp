<%@page import="com.liferay.portal.kernel.servlet.SessionMessages"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@include file="/init.jsp" %>
<%

	PortletPreferences preferences = null;
	String portletResource = ParamUtil.getString(request, "portletResource");
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}else{
		preferences = renderRequest.getPreferences();
	}
	String showLockedModulesIcon = preferences.getValue("showLockedModulesIcon", "0");
	String showModuleIcon = preferences.getValue("showModuleIcon", "1");
	String numerateModules = preferences.getValue("numerateModules", "0");
	String moduleTitleLinkable = preferences.getValue("moduleTitleLinkable", "0");
	String showPercentDone = preferences.getValue("showPercentDone", "1");
	String showModuleStartDate = preferences.getValue("showModuleStartDate", "1");
	String showModuleEndDate = preferences.getValue("showModuleEndDate", "1");
	String allowEditionMode = preferences.getValue("allowEditionMode", "0");
	String allowAccessWhenFinishedButNotClosed = preferences.getValue("allowAccessWhenFinishedButNotClosed", "0");
%>

<liferay-portlet:actionURL var="saveConfigurationURL"  portletConfiguration="true"/>
<aui:form action="<%=saveConfigurationURL %>" >
	<aui:input type="hidden" name="<%=Constants.CMD %>" value="<%=Constants.UPDATE %>" />
	<aui:select label="modulelist.showLockedModulesIcon"  name="showLockedModulesIcon" inlineLabel="true">
		<% boolean selected = showLockedModulesIcon.compareTo("0") == 0; %>
		<aui:option value="0" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "no") %></aui:option>
		<% selected = showLockedModulesIcon.compareTo("1") == 0; %>
		<aui:option value="1" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "yes") %></aui:option>
	</aui:select>
	<aui:select label="modulelist.showModuleIcon"  name="showModuleIcon" inlineLabel="true">
		<% boolean selected = showModuleIcon.compareTo("0") == 0; %>
		<aui:option value="0" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "no") %></aui:option>
		<% selected = showModuleIcon.compareTo("1") == 0; %>
		<aui:option value="1" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "yes") %></aui:option>
	</aui:select>
	<aui:select label="modulelist.numerateModules"  name="numerateModules" inlineLabel="true">
		<% boolean selected = numerateModules.compareTo("0") == 0; %>
		<aui:option value="0" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "no") %></aui:option>
		<% selected = numerateModules.compareTo("1") == 0; %>
		<aui:option value="1" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "yes") %></aui:option>
	</aui:select>
	<aui:select label="modulelist.moduleTitleLinkable"  name="moduleTitleLinkable" inlineLabel="true">
		<% boolean selected = moduleTitleLinkable.compareTo("0") == 0; %>
		<aui:option value="0" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "no") %></aui:option>
		<% selected = moduleTitleLinkable.compareTo("1") == 0; %>
		<aui:option value="1" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "yes") %></aui:option>
	</aui:select>
	<aui:select label="modulelist.showPercentDone"  name="showPercentDone" inlineLabel="true">
		<% boolean selected = showPercentDone.compareTo("0") == 0; %>
		<aui:option value="0" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "no") %></aui:option>
		<% selected = showPercentDone.compareTo("1") == 0; %>
		<aui:option value="1" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "yes") %></aui:option>
	</aui:select>
	<aui:select label="modulelist.showModuleStartDate"  name="showModuleStartDate" inlineLabel="true">
		<% boolean selected = showModuleStartDate.compareTo("0") == 0; %>
		<aui:option value="0" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "no") %></aui:option>
		<% selected = showModuleStartDate.compareTo("1") == 0; %>
		<aui:option value="1" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "yes") %></aui:option>
	</aui:select>
	<aui:select label="modulelist.showModuleEndDate"  name="showModuleEndDate" inlineLabel="true">
		<% boolean selected = showModuleEndDate.compareTo("0") == 0; %>
		<aui:option value="0" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "no") %></aui:option>
		<% selected = showModuleEndDate.compareTo("1") == 0; %>
		<aui:option value="1" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "yes") %></aui:option>
	</aui:select>
	<aui:select label="modulelist.allowEditionMode"  name="allowEditionMode" inlineLabel="true">
		<% boolean selected = allowEditionMode.compareTo("0") == 0; %>
		<aui:option value="0" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "no") %></aui:option>
		<% selected = allowEditionMode.compareTo("1") == 0; %>
		<aui:option value="1" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "yes") %></aui:option>
	</aui:select>
	<aui:select label="modulelist.allowAccessWhenFinishedButNotClosed"  name="allowAccessWhenFinishedButNotClosed" inlineLabel="true">
		<% boolean selected = allowAccessWhenFinishedButNotClosed.compareTo("0") == 0; %>
		<aui:option value="0" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "no") %></aui:option>
		<% selected = allowAccessWhenFinishedButNotClosed.compareTo("1") == 0; %>
		<aui:option value="1" selected="<%= selected %>"><%= LanguageUtil.get(pageContext, "yes") %></aui:option>
	</aui:select>
	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
</aui:form>
