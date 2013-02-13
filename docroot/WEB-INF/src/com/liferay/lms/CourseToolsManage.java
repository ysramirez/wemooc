package com.liferay.lms;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
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
			ellayout.setHidden(!ellayout.getHidden());
			LayoutLocalServiceUtil.updateLayout(ellayout);
			//response.setRenderParameters(request.getParameterMap());			
			response.setRenderParameter("jspPage", "/html/coursetoolsmanage/reload.jsp");		
	} 

}
