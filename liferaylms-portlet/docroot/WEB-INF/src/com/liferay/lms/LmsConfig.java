package com.liferay.lms;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class LmsConfig
 */
public class LmsConfig extends MVCPortlet {
	public void changeSettings(ActionRequest request , ActionResponse response) throws Exception
	{
		String sitetemplates=StringUtil.merge(request.getParameterMap().get( "lmsTemplatesCheckbox"));
		ThemeDisplay themeDisplay  =(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId());
		prefs.setLmsTemplates(sitetemplates);
		LmsPrefsLocalServiceUtil.updateLmsPrefs(prefs);

	}
}
