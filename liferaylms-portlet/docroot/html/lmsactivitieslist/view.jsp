<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@ include file="/init.jsp"%>

<%
	long moduleId = ParamUtil.getLong(request, "moduleId", 0);

	PortletPreferences preferences = null;
	String portletResource = ParamUtil.getString(request, "portletResource");
	if (Validator.isNotNull(portletResource)) 
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	else
		preferences = renderRequest.getPreferences();
	String viewMode = preferences.getValue("viewMode", "0");
	
	boolean completeMode = viewMode.compareTo("0") == 0;
	boolean actualModuleMode = viewMode.compareTo("1") == 0;
	
	System.out.println("completeMode: "+completeMode);
	System.out.println("actualModuleMode: "+actualModuleMode);
	System.out.println("moduleId: "+moduleId);
	
	if(actualModuleMode && moduleId>0){
%>
		<div class="lms-desplegable" style="overflow: hidden;" >
			<jsp:include page="/html/lmsactivitieslist/viewactivities.jsp"></jsp:include>
		</div>
<%
	}else{
%>
		<jsp:include page="/html/lmsactivitieslist/viewComplete.jsp"></jsp:include>
<%	} %>
	
