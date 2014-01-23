<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
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
	LmsPrefs lmsPrefs=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId());
	String[] layusprsel=new String[0];
	if(preferences.getValue("courseTemplates","")!=null&&preferences.getValue("courseTemplates","").length()>0)
	{	
		layusprsel=preferences.getValue("courseTemplates", "").split(",");
	}
%>

<liferay-portlet:actionURL var="saveConfigurationURL"  portletConfiguration="true"/>
<aui:form action="<%=saveConfigurationURL %>" >
	<aui:input type="hidden" name="<%=Constants.CMD %>" value="<%=Constants.UPDATE %>" />
	<aui:input type="checkbox" label="inscription-date" name="inscriptionDate" value="<%=preferences.getValue(\"showInscriptionDate\", StringPool.TRUE) %>" ignoreRequestValue="true"/>
<%
String[] lspist=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getLmsTemplates().split(",");
		%>
		<aui:field-wrapper  label="allowed-site-templates" >
		<%
		for(String lspis:lspist)
		{
			String checked="";
			if(ArrayUtils.contains(layusprsel, lspis)||layusprsel.length==0)
			{
				checked="checked=\"true\"";
			}
			LayoutSetPrototype layoutsetproto=LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.parseLong(lspis));
			%>
				<input type="checkbox" name="<portlet:namespace/>courseTemplates" 
	<%=checked %> value="<%=layoutsetproto.getLayoutSetPrototypeId()%>"/> <label><%=layoutsetproto.getName(themeDisplay.getLocale())%></label><br />
			
			<%
		}
		%>
		</aui:field-wrapper>
		<%
		%>
	<aui:input type="checkbox" label="available-categories" name="categories" value="<%=preferences.getValue(\"categories\", StringPool.TRUE) %>" ignoreRequestValue="true"/>
	<aui:button-row>
		<aui:button type="submit" value="save" />
	</aui:button-row>
	
</aui:form>
