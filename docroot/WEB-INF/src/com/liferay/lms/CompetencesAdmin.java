package com.liferay.lms;

import java.util.Enumeration;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.lms.model.Competence;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CompetencesAdmin
 */
public class CompetencesAdmin extends MVCPortlet 
{
	public void saveCompetence(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Competence.class.getName(), actionRequest);
        System.out.println("kkk");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		String redirect = ParamUtil.getString(actionRequest, "redirect");
		User user = themeDisplay.getUser();
		Enumeration<String> parNam = actionRequest.getParameterNames();
		String title = "";
	    boolean noTitle = true;
		while (parNam.hasMoreElements()) {
			
			String paramName = parNam.nextElement();
			System.out.println(paramName+":"+actionRequest.getParameter(paramName));
			if (paramName.startsWith("title_") && paramName.length() > 6) {
				if (title.equals("")) {
					title = actionRequest.getParameter(paramName);
					noTitle = false;
				}

			}
		}
		if (noTitle) {
			  System.out.println("notitle");
				
			SessionErrors.add(actionRequest, "title-required");
			actionResponse.setRenderParameter("jspPage",
					"/html/competencesadmin/editcompetence.jsp");
			return;
		}
		String description = actionRequest.getParameter("description");
		long competenceId = ParamUtil.getLong(actionRequest, "competenceId", 0);
		Competence competence=null;
		if(competenceId==0)
		{
			System.out.println("anado");
			 competence=CompetenceLocalServiceUtil.addCompetence(title, description, serviceContext);
			 System.out.println("anadido");
		}
		else
		{
			competence=CompetenceLocalServiceUtil.getCompetence(competenceId);
		}
		
		boolean oneTitleNotEmpty = false;
		Enumeration<String> parNames = actionRequest.getParameterNames();
			while (parNames.hasMoreElements()) {
			String paramName = parNames.nextElement();
			if (paramName.startsWith("title_") && paramName.length() > 6) {
				String language = paramName.substring(6);
				Locale locale = LocaleUtil.fromLanguageId(language);
				competence.setTitle(actionRequest.getParameter(paramName),locale);

				if (!actionRequest.getParameter(paramName).equals("")) {
					oneTitleNotEmpty = true;
				}
			}
		}

		if (!oneTitleNotEmpty) {
			System.out.println("notitle");
			SessionErrors.add(actionRequest, "title-empty");
			actionResponse.setRenderParameter("jspPage",
					"/html/competencesadmin/editcompetence.jsp");
			return;
		}
		 System.out.println("abajo");
			
		CompetenceLocalServiceUtil.modCompetence(competence, serviceContext);
	}
}
