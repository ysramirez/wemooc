package com.liferay.lms;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class CourseToolsManage extends MVCPortlet 
{
	public void changeLayout(ActionRequest request, ActionResponse response) throws Exception
	{

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);
			if (!themeDisplay.isSignedIn()) {
				return;
			}
			String layoutid=ParamUtil.getString(request, "layoutid");
			Layout ellayout=LayoutLocalServiceUtil.getLayout(Long.parseLong(layoutid));
			if(ellayout.getHidden()) {
				ellayout.setHidden(true);
				ResourcePermissionServiceUtil.removeResourcePermission(ellayout.getGroupId(), themeDisplay.getCompanyId(), Layout.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL, layoutid, RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER).getRoleId(), ActionKeys.VIEW);
			}
			else {
				ellayout.setHidden(false);
				ResourcePermissionServiceUtil.addResourcePermission(ellayout.getGroupId(), themeDisplay.getCompanyId(), Layout.class.getName(), 
						ResourceConstants.SCOPE_INDIVIDUAL, layoutid, RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER).getRoleId(), ActionKeys.VIEW);	
			}
			
			ellayout.setHidden(!ellayout.getHidden());
			LayoutLocalServiceUtil.updateLayout(ellayout);
			//response.setRenderParameters(request.getParameterMap());			
			response.setRenderParameter("jspPage", "/html/coursetoolsmanage/reload.jsp");		
	} 

}
