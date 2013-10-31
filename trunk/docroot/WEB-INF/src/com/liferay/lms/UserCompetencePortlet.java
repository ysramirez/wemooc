package com.liferay.lms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.inscriptionadmin.InscriptionAdminPortlet;
import com.liferay.lms.model.Competence;
import com.liferay.lms.model.UserCompetence;
import com.liferay.lms.service.CompetenceLocalServiceUtil;
import com.liferay.lms.service.UserCompetenceLocalServiceUtil;
import com.liferay.lms.views.CompetenceView;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;


public class UserCompetencePortlet extends MVCPortlet {
	private static Log log = LogFactoryUtil.getLog(InscriptionAdminPortlet.class);
 
	private String viewJSP; 
	
	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}
	
	public void doView(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		int page = 0;
		int delta = 10;
		try{
			page = Integer.valueOf(renderRequest.getParameter("act"));
			if(page>0){
				page = page -1;
			}
		}catch(NumberFormatException nfe){
			if(log.isDebugEnabled())nfe.printStackTrace();
		}
		
		try{

			delta = Integer.valueOf(renderRequest.getParameter("delta"));
		}catch(NumberFormatException nfe){
			if(log.isDebugEnabled())nfe.printStackTrace();
		}
		
		try{
			if(log.isDebugEnabled())log.debug("deltaact"+renderRequest.getParameter("deltaact"));
			if(renderRequest.getParameter("deltaact")!=null){
				if(log.isDebugEnabled())log.debug("deltaact"+renderRequest.getParameter("deltaact"));
				delta = Integer.valueOf(renderRequest.getParameter("deltaact"));
			}
		}catch(NumberFormatException nfe){
			if(log.isDebugEnabled())nfe.printStackTrace();
		}
		
		if(log.isDebugEnabled())log.debug("page:"+page+" delta:"+delta+"  --  "+(page*delta)+"::"+((page*delta)+delta));
		
		List<UserCompetence> ucs = UserCompetenceLocalServiceUtil.findBuUserId(themeDisplay.getUserId(),page*delta,(page*delta)+delta);
		int totale = UserCompetenceLocalServiceUtil.countByUserId(themeDisplay.getUserId());
		
		List<CompetenceView> competences = new ArrayList<CompetenceView>();
		for(UserCompetence uc : ucs){
			try {
				Competence competence = CompetenceLocalServiceUtil.getCompetence(uc.getCompetenceId());
				if(competence!=null){
					
					competences.add(new CompetenceView(competence, uc));
				}
			} catch (PortalException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			} catch (SystemException e) {
				if(log.isDebugEnabled())e.printStackTrace();
			}
		}

		renderRequest.setAttribute("competences", competences);
		renderRequest.setAttribute("totale", String.valueOf(totale));
		renderRequest.setAttribute("delta", String.valueOf(delta));
		include(viewJSP, renderRequest, renderResponse);
	}
 

	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}
}
