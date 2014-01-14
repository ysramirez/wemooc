package com.liferay.lms;

import java.util.Enumeration;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ProcessAction;

import com.liferay.lms.model.Competence;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.lms.service.impl.CompetenceLocalServiceImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CompetencesAdmin
 */
public class CompetencesAdmin extends MVCPortlet{
	private static Log log = LogFactoryUtil.getLog(CompetencesAdmin.class);
	
	
	@ProcessAction(name="saveCompetence")
	public void saveCompetence(ActionRequest actionRequest,ActionResponse actionResponse) {

		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(Competence.class.getName(), actionRequest);
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Enumeration<String> parNam = actionRequest.getParameterNames();
		String title = "";
	    boolean noTitle = true;
		while (parNam.hasMoreElements()) {
			
			String paramName = parNam.nextElement();
			if (paramName.startsWith("title_") && paramName.length() > 6) {
				if (title.equals("")) {
					title = actionRequest.getParameter(paramName);
					noTitle = false;
				}

			}
		}
		if (noTitle) {
				
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
			 try {
				competence=CompetenceLocalServiceUtil.addCompetence(title, description, serviceContext);
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				competence=CompetenceLocalServiceUtil.getCompetence(competenceId);
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
			competence.setTitle(title);
			competence.setDescription(description);
			try {
				CompetenceLocalServiceUtil.updateCompetence(competence, serviceContext);
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
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
			SessionErrors.add(actionRequest, "title-empty");
			actionResponse.setRenderParameter("jspPage",
					"/html/competencesadmin/editcompetence.jsp");
			return;
		}
			
		try{
			CompetenceLocalServiceUtil.modCompetence(competence, serviceContext);
		}catch(Exception e){
			if(log.isDebugEnabled())e.printStackTrace();
		}
		if(log.isDebugEnabled())log.debug("End right");
	}
	
	@ProcessAction(name="deleteCompetence")
	public void deleteCompetence(ActionRequest request, ActionResponse response)throws Exception {
		
		long id = ParamUtil.getLong(request, "competenceId");
		if (Validator.isNotNull(id)) {
			if(log.isDebugEnabled())log.debug("deleteCompetence");
			CompetenceLocalServiceUtil.deleteCompetence(id);
		}
	}
}
