<%@page import="com.liferay.portal.kernel.servlet.SessionMessages"%>
<%@page import="com.liferay.portal.kernel.portlet.PortletBagPool"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@include file="/init.jsp" %>
<%
	PortletPreferences preferences = null;
	
	String portletResource = ParamUtil.getString(request, "portletResource");
	
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}
	else{
		preferences = renderRequest.getPreferences();
	}
	
%>

<liferay-portlet:actionURL var="saveConfigurationURL"  portletConfiguration="true"/>
<aui:form action="<%=saveConfigurationURL %>" >
	<aui:input type="hidden" name="<%=Constants.CMD %>" value="<%=Constants.UPDATE %>" />
	<aui:input type="checkbox" label="inscription-date" name="inscriptionDate" value="<%=preferences.getValue(\"showInscriptionDate\", StringPool.TRUE) %>" ignoreRequestValue="true"/>

	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
	
</aui:form>
