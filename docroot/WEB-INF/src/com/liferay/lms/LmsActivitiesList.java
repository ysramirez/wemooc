
package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.asset.LearningActivityAssetRendererFactory;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.P2pActivityCorrections;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class LmsActivitiesList
 */

public class LmsActivitiesList extends MVCPortlet {

	public void deleteMyTries(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {
		long actId = ParamUtil.getLong(actionRequest, "resId", 0);
		String redirect = ParamUtil.getString(actionRequest, "redirect");
		if(actId>0)
		{
		ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);
		LearningActivity larn=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		actionRequest.setAttribute("editing", "true");
		LearningActivityTryLocalServiceUtil.deleteUserTries(actId, themeDisplay.getUserId());
		if(P2pActivityLocalServiceUtil.existP2pAct(actId, themeDisplay.getUserId()))
		{
			P2pActivity p2pact=P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, themeDisplay.getUserId());
			P2pActivityLocalServiceUtil.deleteP2pActivity(p2pact.getP2pActivityId());
			
			java.util.List<P2pActivityCorrections> p2pactcorrcs=P2pActivityCorrectionsLocalServiceUtil.findByP2pActivityId(p2pact.getP2pActivityId());
			for(P2pActivityCorrections p2pactcorr:p2pactcorrcs)
			{
				P2pActivityCorrectionsLocalServiceUtil.deleteP2pActivityCorrections(p2pactcorr);
			}
			
		}
		
		actionRequest.setAttribute("activity", larn);
		}
		WindowState windowState = actionRequest.getWindowState();
		if (redirect != null && !"".equals(redirect)) {
			if (!windowState.equals(LiferayWindowState.POP_UP)) {
				actionResponse.sendRedirect(redirect);
			}
			else {
				redirect = PortalUtil.escapeRedirect(redirect);

				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
		}
		
		
	}
	public void saveActivity(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		String redirect = ParamUtil.getString(actionRequest, "redirect");
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		actionRequest.setAttribute("editing", "true");
		User user = themeDisplay.getUser();
		long actId = ParamUtil.getLong(actionRequest, "actId", 0);
		long moduleId = ParamUtil.getLong(actionRequest, "moduleId", 0);
		long weightinmodule=ParamUtil.getLong(actionRequest, "weightinmodule", 0);
		long precedence=ParamUtil.getLong(actionRequest, "precedence", 0);
		//String title = actionRequest.getParameter("title");
		
		String description = actionRequest.getParameter("description");
		int type = ParamUtil.getInteger(actionRequest, "type", 0);
		int startMonth = ParamUtil.getInteger(actionRequest, "startMon");
		int startYear = ParamUtil.getInteger(actionRequest, "startYear");
		int startDay = ParamUtil.getInteger(actionRequest, "startDay");
		int startHour = ParamUtil.getInteger(actionRequest, "startHour");
		int startMinute = ParamUtil.getInteger(actionRequest, "startMin");
		int startAMPM = ParamUtil.getInteger(actionRequest, "startAMPM");
		String feedbackCorrect = ParamUtil.getString(actionRequest, "feedbackCorrect", "");
		String feedbackNoCorrect = ParamUtil.getString(actionRequest, "feedbackNoCorrect", "");

		if (startAMPM > 0) {
			startHour += 12;
		}
		Date startDate = PortalUtil.getDate(startMonth, startDay, startYear, startHour, startMinute, user.getTimeZone(), new EntryDisplayDateException());

		int stopMonth = ParamUtil.getInteger(actionRequest, "stopMon");
		int stopYear = ParamUtil.getInteger(actionRequest, "stopYear");
		int stopDay = ParamUtil.getInteger(actionRequest, "stopDay");
		int stopHour = ParamUtil.getInteger(actionRequest, "stopHour");
		int stopMinute = ParamUtil.getInteger(actionRequest, "stopMin");
		int stopAMPM = ParamUtil.getInteger(actionRequest, "stopAMPM");
		if (stopAMPM > 0) {
			stopHour += 12;
		}
		Date stopDate = PortalUtil.getDate(stopMonth, stopDay, stopYear, stopHour, stopMinute, user.getTimeZone(), new EntryDisplayDateException());

		long tries = ParamUtil.getLong(actionRequest, "tries", 0);
		int passpuntuation = ParamUtil.getInteger(actionRequest, "passpuntuation", 0);
		java.util.Date ahora = new java.util.Date(System.currentTimeMillis());
		
		//validating
		Enumeration<String> parNams= actionRequest.getParameterNames();
		boolean hasTitle=false;
		while(parNams.hasMoreElements())
		{
		  String paramName=parNams.nextElement();
		  if(paramName.startsWith("title_")&&paramName.length()>6)
		  {
			  if(actionRequest.getParameter(paramName)!=null && actionRequest.getParameter(paramName).length()>0){
				  String title = actionRequest.getParameter(paramName);
				  StringTokenizer tokens = new StringTokenizer(title);
				  if(tokens.countTokens() > 0 ){
					  hasTitle=true;
				  }
			  }
		  }
		}
		if(!hasTitle)
		{
			SessionErrors.add(actionRequest, "title-required");
			return;
		}
		if(Validator.isNull(description))
		{
			SessionErrors.add(actionRequest, "description-required");
		}
		if(Validator.equals(moduleId, 0))
		{
			SessionErrors.add(actionRequest, "module-required");
		}
		
		//Date validation
		if (startDate.after(stopDate)){
			SessionErrors.add(actionRequest, "activity-startDate-before-endDate");
		}
		
		//Type validation
		if (type == -1){
			SessionErrors.add(actionRequest, "activity-type-not-selected");
			return;
		}
		
		LearningActivity larn=null;
		if (actId == 0) {

			if(permissionChecker.hasPermission(
					themeDisplay.getScopeGroupId(),
					Module.class.getName(), moduleId,
					"ADD_LACT"))
			{
			larn =com.liferay.lms.service.LearningActivityLocalServiceUtil.addLearningActivity(
				"", "", ahora, startDate, stopDate, type, tries, passpuntuation, moduleId, feedbackCorrect, feedbackNoCorrect, serviceContext);
			}
		}
		else {
			if(permissionChecker.hasPermission(
					themeDisplay.getScopeGroupId(),
					LearningActivity.class.getName(), actId,
					ActionKeys.UPDATE))
			{
			larn=com.liferay.lms.service.LearningActivityLocalServiceUtil.modLearningActivity(
				actId, "", "", ahora, startDate, stopDate, type, tries, passpuntuation, moduleId, feedbackCorrect, feedbackNoCorrect, serviceContext);
			}
			
		}
		larn.setDescription( description,themeDisplay.getLocale());
		larn.setWeightinmodule(weightinmodule);
		larn.setPrecedence(precedence);
		Enumeration<String> parNames= actionRequest.getParameterNames();
		
		while(parNames.hasMoreElements())
		{
		  String paramName=parNames.nextElement();
		  if(paramName.startsWith("title_")&&paramName.length()>6)
		  {
			  String language=paramName.substring(6);
			  Locale locale=LocaleUtil.fromLanguageId(language);
			  larn.setTitle( actionRequest.getParameter(paramName),locale);
		  }
		}
		if(permissionChecker.hasPermission(
				themeDisplay.getScopeGroupId(),
				LearningActivity.class.getName(), larn.getActId(),
				ActionKeys.UPDATE))
		{
			com.liferay.lms.service.LearningActivityLocalServiceUtil.updateLearningActivity(larn);
		}
		SessionMessages.add(actionRequest, "activity-saved-successfully");
		WindowState windowState = actionRequest.getWindowState();
		if (redirect != null && !"".equals(redirect)) {
			if (!windowState.equals(LiferayWindowState.POP_UP)) {
				actionResponse.sendRedirect(redirect);
			}
			else {
				redirect = PortalUtil.escapeRedirect(redirect);

				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
		}
		actionRequest.setAttribute("activity", larn);

	}


	public void deleteactivity(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String portletId = PortalUtil.getPortletId(actionRequest);
		long actId = ParamUtil.getLong(actionRequest, "resId");
		com.liferay.lms.service.LearningActivityServiceUtil.deleteLearningactivity(actId);
		SessionMessages.add(actionRequest, "activity-deleted-successfully");
	}
	
	public void upactivity(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {

	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
	String portletId = PortalUtil.getPortletId(actionRequest);
	long actId = ParamUtil.getLong(actionRequest, "resId",0);
	actionResponse.setRenderParameters(actionRequest.getParameterMap());
	
	if(actId>0)
	{
		com.liferay.lms.service.LearningActivityLocalServiceUtil.goUpLearningActivity(actId);
	}
}
	public void downactivity(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {

	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
	String portletId = PortalUtil.getPortletId(actionRequest);
	long actId = ParamUtil.getLong(actionRequest, "resId",0);
	actionResponse.setRenderParameters(actionRequest.getParameterMap());
	
	if(actId>0)
	{
		com.liferay.lms.service.LearningActivityLocalServiceUtil.goDownLearningActivity(actId);
	}
}
	public void editactivity(ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException, SystemException, Exception {
		long actId = ParamUtil.getInteger(actionRequest, "actId");
	
		// LearningActivity learnact =
		// com.liferay.lms.service.LearningActivityServiceUtil.getLearningActivity(actId);
		LearningActivityAssetRendererFactory laf = new LearningActivityAssetRendererFactory();
		if (laf != null) {
			AssetRenderer assetRenderer = laf.getAssetRenderer(actId, 0);
			String urlEdit = assetRenderer.getURLEdit((LiferayPortletRequest) actionRequest, (LiferayPortletResponse) actionResponse).toString();			
			actionResponse.sendRedirect(urlEdit);
		}
		SessionMessages.add(actionRequest, "asset-renderer-not-defined");
	}
	public void editactivityoptions(ActionRequest actionRequest, ActionResponse actionResponse)
	throws PortalException, SystemException, Exception {
		long actId=ParamUtil.getLong(actionRequest, "resId",0);
		if(actId>0)
		{
		LearningActivity larn=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		actionRequest.setAttribute("activity", larn);
		}
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		actionRequest.setAttribute("editing", "true");
}

	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		// TODO Auto-generated method stub
		if(renderRequest.getAttribute("editing")!=null &&renderRequest.getAttribute("editing").equals("true"))
		{
			include("/html/editactivity/editactivity.jsp",renderRequest,renderResponse);
		}
		else
		{
			super.doView(renderRequest, renderResponse);
		}
	}



	public void viewactivity(ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException, SystemException, Exception {

		long actId = ParamUtil.getInteger(actionRequest, "actId");
		LearningActivity learnact = com.liferay.lms.service.LearningActivityServiceUtil.getLearningActivity(actId);
		LearningActivityAssetRendererFactory laf = new LearningActivityAssetRendererFactory();
		if (laf != null) {
			AssetRenderer assetRenderer = laf.getAssetRenderer(actId, 0);
			String urlEdit = assetRenderer.getURLViewInContext((LiferayPortletRequest) actionRequest, (LiferayPortletResponse) actionResponse, "").toString();
			actionResponse.sendRedirect(urlEdit);
		}
		SessionMessages.add(actionRequest, "asset-renderer-not-defined");

	}

	public void modactivity(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		String portletId = PortalUtil.getPortletId(actionRequest);

		String title = actionRequest.getParameter("title");
		String description = actionRequest.getParameter("description");
		int typeId = ParamUtil.getInteger(actionRequest, "type", 0);
		java.util.Date ahora = new java.util.Date(System.currentTimeMillis());
		LearningActivity learningActivity = LearningActivityServiceUtil.getLearningActivity(ParamUtil.getLong(actionRequest, "actId"));
		learningActivity.setTitle(title);
		learningActivity.setDescription(description);
		learningActivity.setTypeId(typeId);
		LearningActivityLocalServiceUtil.updateLearningActivity(learningActivity, false);
		editactivity(actionRequest, actionResponse);
		SessionMessages.add(actionRequest, "activity-modified-successfully");
	}
	
	
	
}
