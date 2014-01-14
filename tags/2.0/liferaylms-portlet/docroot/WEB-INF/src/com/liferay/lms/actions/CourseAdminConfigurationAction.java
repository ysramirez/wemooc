package com.liferay.lms.actions;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class CourseAdminConfigurationAction implements ConfigurationAction {
	public static final String JSP = "/html/courseadmin/config/edit.jsp";

	public String render(PortletConfig config, RenderRequest renderRequest, RenderResponse renderResponse) throws Exception 
	{
		return JSP; 
	}
	
	public void processAction( 
			PortletConfig portletConfig, ActionRequest actionRequest, 
			ActionResponse actionResponse) 
		throws Exception { 
		
		if (!Constants.UPDATE.equals(actionRequest.getParameter(Constants.CMD))) {
			return;
		} 
		
		PortletPreferences portletPreferences =
		PortletPreferencesFactoryUtil.getPortletSetup( 
				actionRequest, ParamUtil.getString(actionRequest, "portletResource")); 
		
		portletPreferences.setValue("showInscriptionDate",Boolean.toString(ParamUtil.getBoolean(actionRequest, "inscriptionDate",true)));
		portletPreferences.setValue("categories",Boolean.toString(ParamUtil.getBoolean(actionRequest, "categories",true)));
		portletPreferences.setValue("courseTemplates",	StringUtil.merge(actionRequest.getParameterMap().get( "courseTemplates")));
		
		
		portletPreferences.store();
		SessionMessages.add( 
				actionRequest, portletConfig.getPortletName() + ".doConfigure"); 

		
	} 
}
