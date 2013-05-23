
package com.liferay.lms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.portlet.WindowState;
import javax.xml.namespace.QName;

import com.liferay.lms.asset.LearningActivityAssetRendererFactory;
import com.liferay.lms.events.ThemeIdEvent;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.P2pActivityCorrections;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletQNameUtil;
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
		UploadRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);
		
		List<Long> assetCategoryIdsList = new ArrayList<Long>();
		boolean updateAssetCategoryIds = false;
		
		for (String name:Collections.list((Enumeration<String>)uploadRequest.getParameterNames())){
			if (name.startsWith("assetCategoryIds")) {
				updateAssetCategoryIds = true;
				for (long assetCategoryId : StringUtil.split(
						ParamUtil.getString(uploadRequest, name), 0L)) {
					assetCategoryIdsList.add(assetCategoryId);
				}
			}
		}
		
		if (updateAssetCategoryIds) {
			serviceContext.setAssetCategoryIds(ArrayUtil.toArray(
					assetCategoryIdsList.toArray(
							new Long[assetCategoryIdsList.size()])));
		}
	
		String assetTagNames = uploadRequest.getParameter("assetTagNames");

		if (assetTagNames != null) {
			serviceContext.setAssetTagNames(StringUtil.split(assetTagNames));
		}
		
		ThemeDisplay themeDisplay = (ThemeDisplay) uploadRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PermissionChecker permissionChecker=themeDisplay.getPermissionChecker();
		String redirect = ParamUtil.getString(uploadRequest, "redirect");
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		uploadRequest.setAttribute("editing", "true");
		User user = themeDisplay.getUser();
		long actId = ParamUtil.getLong(uploadRequest, "resId", 0);
		long moduleId = ParamUtil.getLong(uploadRequest, "resModuleId", 0);
		long weightinmodule=ParamUtil.getLong(uploadRequest, "weightinmodule", 0);
		long precedence=ParamUtil.getLong(uploadRequest, "precedence", 0);
		//String title = actionRequest.getParameter("title");
		
		String description = uploadRequest.getParameter("description");
		int type = ParamUtil.getInteger(uploadRequest, "type", -1);
		int startMonth = ParamUtil.getInteger(uploadRequest, "startMon");
		int startYear = ParamUtil.getInteger(uploadRequest, "startYear");
		int startDay = ParamUtil.getInteger(uploadRequest, "startDay");
		int startHour = ParamUtil.getInteger(uploadRequest, "startHour");
		int startMinute = ParamUtil.getInteger(uploadRequest, "startMin");
		int startAMPM = ParamUtil.getInteger(uploadRequest, "startAMPM");
		String feedbackCorrect = ParamUtil.getString(uploadRequest, "feedbackCorrect", "");
		String feedbackNoCorrect = ParamUtil.getString(uploadRequest, "feedbackNoCorrect", "");

		if (startAMPM > 0) {
			startHour += 12;
		}
		Date startDate = PortalUtil.getDate(startMonth, startDay, startYear, startHour, startMinute, user.getTimeZone(), new EntryDisplayDateException());

		int stopMonth = ParamUtil.getInteger(uploadRequest, "stopMon");
		int stopYear = ParamUtil.getInteger(uploadRequest, "stopYear");
		int stopDay = ParamUtil.getInteger(uploadRequest, "stopDay");
		int stopHour = ParamUtil.getInteger(uploadRequest, "stopHour");
		int stopMinute = ParamUtil.getInteger(uploadRequest, "stopMin");
		int stopAMPM = ParamUtil.getInteger(uploadRequest, "stopAMPM");
		if (stopAMPM > 0) {
			stopHour += 12;
		}
		Date stopDate = PortalUtil.getDate(stopMonth, stopDay, stopYear, stopHour, stopMinute, user.getTimeZone(), new EntryDisplayDateException());

		long tries = ParamUtil.getLong(uploadRequest, "tries", 0);
		int passpuntuation = ParamUtil.getInteger(uploadRequest, "passpuntuation", 0);
		java.util.Date ahora = new java.util.Date(System.currentTimeMillis());
		
		//validating
		Enumeration<String> parNams= uploadRequest.getParameterNames();
		boolean hasTitle=false;
		while(parNams.hasMoreElements())
		{
		  String paramName=parNams.nextElement();
		  if(paramName.startsWith("title_")&&paramName.length()>6)
		  {
			  if(uploadRequest.getParameter(paramName)!=null && uploadRequest.getParameter(paramName).length()>0){
				  String title = uploadRequest.getParameter(paramName);
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
		
		LearningActivity larn=null;
		LearningActivityType learningActivityType=null;
		
		if(actId==0){
			//Type validation
			if (type==-1){
				SessionErrors.add(actionRequest, "activity-type-not-selected");
				return;
			}
						
			learningActivityType=new LearningActivityTypeRegistry().getLearningActivityType(type);
		}
		else
		{		
			learningActivityType=new LearningActivityTypeRegistry().
					getLearningActivityType(LearningActivityLocalServiceUtil.getLearningActivity(actId).getTypeId());
		}
			
		//Type especific validations
		if(!learningActivityType.especificValidations(uploadRequest, actionResponse)){
			return;
		}
		
		
		if (actId == 0) {

			if(permissionChecker.hasPermission(
					themeDisplay.getScopeGroupId(),
					Module.class.getName(), moduleId,
					"ADD_LACT"))
			{
			larn =LearningActivityLocalServiceUtil.addLearningActivity(
				"", "", ahora, startDate, stopDate, type, tries, passpuntuation, moduleId, feedbackCorrect, feedbackNoCorrect, serviceContext);
			}
		}
		else {
			if(permissionChecker.hasPermission(
					themeDisplay.getScopeGroupId(),
					LearningActivity.class.getName(), actId,
					ActionKeys.UPDATE))
			{
			larn=LearningActivityLocalServiceUtil.modLearningActivity(
				actId, "", "", ahora, startDate, stopDate, type, tries, passpuntuation, moduleId, feedbackCorrect, feedbackNoCorrect, serviceContext);
			}
			
		}
		larn.setDescription( description,themeDisplay.getLocale());
		larn.setWeightinmodule(weightinmodule);
		larn.setPrecedence(precedence);
		Enumeration<String> parNames= uploadRequest.getParameterNames();
		
		while(parNames.hasMoreElements())
		{
		  String paramName=parNames.nextElement();
		  if(paramName.startsWith("title_")&&paramName.length()>6)
		  {
			  String language=paramName.substring(6);
			  Locale locale=LocaleUtil.fromLanguageId(language);
			  larn.setTitle( uploadRequest.getParameter(paramName),locale);
		  }
		}
		
		learningActivityType.setExtraContent(uploadRequest,actionResponse,larn);

		if(permissionChecker.hasPermission(
				themeDisplay.getScopeGroupId(),
				LearningActivity.class.getName(), larn.getActId(),
				ActionKeys.UPDATE))
		{
			LearningActivityLocalServiceUtil.updateLearningActivity(larn);
			learningActivityType.afterInsertOrUpdate(uploadRequest,actionResponse,larn);
		}
		SessionMessages.add(uploadRequest, "activity-saved-successfully");
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
		uploadRequest.setAttribute("activity", larn);

	}
	
	public void goToModule(ActionRequest actionRequest, ActionResponse actionResponse)throws Exception {
		ThemeIdEvent themeIdEvent = new ThemeIdEvent();
		themeIdEvent.setModuleId(ParamUtil.getLong(actionRequest, "moduleId",0));
		themeIdEvent.setThemeId(ParamUtil.getLong(actionRequest, "themeId",1));		
		actionResponse.setEvent(new QName("http://www.wemooc.com/" , "themeId"), themeIdEvent);
	}
	
	public void deletemodule(ActionRequest actionRequest, ActionResponse actionResponse)throws Exception {

		long moduleId = ParamUtil.getLong(actionRequest, "resId",0);
		long renderModule = ParamUtil.getLong(actionRequest, "moduleId",0);

		if(moduleId>0)
		{
			if(LearningActivityLocalServiceUtil.dynamicQueryCount(DynamicQueryFactoryUtil.forClass(LearningActivity.class).
			add(PropertyFactoryUtil.forName("moduleId").eq(moduleId)))==0) {
				ModuleLocalServiceUtil.deleteModule(moduleId);
				if(moduleId==renderModule) {
					actionResponse.removePublicRenderParameter("moduleId");
					actionResponse.removePublicRenderParameter("actId");	
				}	
			}
			else{
				SessionErrors.add(actionRequest, "activities-in-module");
			}
		}
	}
	
	public void upmodule(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {

		long moduleId = ParamUtil.getLong(actionRequest, "resId",0);
		
		if(moduleId>0)
		{
			ModuleLocalServiceUtil.goUpModule(moduleId);
		}
		
	}
	
	public void downmodule(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {

		long moduleId = ParamUtil.getLong(actionRequest, "resId",0);

		if(moduleId>0)
		{
			ModuleLocalServiceUtil.goDownModule(moduleId);
		}
		
	}
	
	public void deleteactivity(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long actId = ParamUtil.getLong(actionRequest, "resId");
		long renderActId = ParamUtil.getLong(actionRequest, "actId",0); 
		
		if(actId>0)
		{
			LearningActivityServiceUtil.deleteLearningactivity(actId);
			if(actId==renderActId) {
				actionResponse.removePublicRenderParameter("actId");				
			}
		}
	}
	
	public void upactivity(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {

		long actId = ParamUtil.getLong(actionRequest, "resId",0);
		
		if(actId>0)
		{
			LearningActivityLocalServiceUtil.goUpLearningActivity(actId);
		}
	}
	
	public void downactivity(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {

		long actId = ParamUtil.getLong(actionRequest, "resId",0);
	
		if(actId>0)
		{
			LearningActivityLocalServiceUtil.goDownLearningActivity(actId);
		}
	}
	
	public void editactivity(ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException, SystemException, Exception {
		long actId = ParamUtil.getInteger(actionRequest, "resId");
	
		// LearningActivity learnact =
		// com.liferay.lms.service.LearningActivityServiceUtil.getLearningActivity(actId);
		LearningActivityAssetRendererFactory laf = new LearningActivityAssetRendererFactory();
		if (laf != null) {
			AssetRenderer assetRenderer = laf.getAssetRenderer(actId, 0);
			String urlEdit = assetRenderer.getURLEdit((LiferayPortletRequest) actionRequest, (LiferayPortletResponse) actionResponse).toString();			
			Portlet urlEditPortlet =PortletLocalServiceUtil.getPortletById(HttpUtil.getParameter(urlEdit, "p_p_id",false));
			if(urlEditPortlet!=null) {
				PublicRenderParameter actIdPublicParameter = urlEditPortlet.getPublicRenderParameter("actId");
				if(actIdPublicParameter!=null) {
					urlEdit=HttpUtil.removeParameter(urlEdit,PortletQNameUtil.getPublicRenderParameterName(actIdPublicParameter.getQName()));
				}
				urlEdit=HttpUtil.addParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resId", actId);
				urlEdit=HttpUtil.removeParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"actionEditingDetails");
				urlEdit=HttpUtil.addParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"actionEditingDetails", true);
			}
		
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
	
	public void changeVisibility(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long actId=ParamUtil.getLong(actionRequest, "resId", 0);
		Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
		boolean visible = ResourcePermissionLocalServiceUtil.hasResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
				ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),siteMemberRole.getRoleId(), ActionKeys.VIEW);
		
		if(visible) {
			ResourcePermissionLocalServiceUtil.removeResourcePermission(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
					ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),siteMemberRole.getRoleId(), ActionKeys.VIEW);	
		}else {
			String[] actIds = {ActionKeys.VIEW};
			ResourcePermissionLocalServiceUtil.setResourcePermissions(siteMemberRole.getCompanyId(), LearningActivity.class.getName(), 
					ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(actId),siteMemberRole.getRoleId(), actIds);
		}	
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
