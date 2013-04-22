package com.liferay.lms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ResourceActivity
 */
public class ResourceExternalActivity extends MVCPortlet {
	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads";

	public void selectResource(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String jspPage = ParamUtil.getString(actionRequest, "jspPage");
		long actId = ParamUtil.getLong(actionRequest, "resId", 0);
		long entryId = ParamUtil.getLong(actionRequest, "entryId", 0);
		ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);
	
		LearningActivity larn = LearningActivityServiceUtil.getLearningActivity(actId);
		larn.setExtracontent(Long.toString(entryId));
		LearningActivityServiceUtil.modLearningActivity(larn, serviceContext);
		SessionMessages.add(actionRequest, "activity-saved-successfully");
		actionResponse.setRenderParameter("jspPage", jspPage);
		actionResponse.setRenderParameter("resId", Long.toString(actId));
	}
	
	public void addfiles(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {

	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
	UploadPortletRequest request = PortalUtil.getUploadPortletRequest(actionRequest);
	
	String jspPage = ParamUtil.getString(actionRequest, "jspPage");
	long actId = ParamUtil.getLong(actionRequest, "resId", 0);
	String description = request.getParameter("description");
	String youtubecode=ParamUtil.getString(request,"youtubecode","");
	LearningActivity larn = LearningActivityServiceUtil.getLearningActivity(actId);
	String extraContent=larn.getExtracontent();
	Document document = SAXReaderUtil.createDocument();
	Element rootElement = document.addElement("multimediaentry");
	if(extraContent!=null &&!"".equals(extraContent)&&!Validator.isNumber(extraContent))
	{
		document=SAXReaderUtil.read(extraContent);
		rootElement =document.getRootElement();
	}
	
	if(!"".equals(youtubecode))
	{
		Element video=rootElement.element("video");
		if(video!=null)
		{
			video.detach();
			rootElement.remove(video);
		}
		video = SAXReaderUtil.createElement("video");
		video.setText(youtubecode);		
		rootElement.add(video);
	}
	larn.setExtracontent(document.formattedString());
	larn.setDescription( description,themeDisplay.getLocale());
	//LearningActivityServiceUtil.modLearningActivity(larn, serviceContext);
	LearningActivityServiceUtil.modLearningActivity(larn);
	SessionMessages.add(actionRequest, "activity-saved-successfully");
	actionResponse.setRenderParameter("jspPage", jspPage);
	actionResponse.setRenderParameter("actionEditingDetails", "true");	
	actionResponse.setRenderParameter("resId", Long.toString(actId));
}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
	throws PortletException, IOException {

	long actId=0;
	
	if(ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false)){
		
		actId=ParamUtil.getLong(renderRequest, "resId", 0);
		renderResponse.setProperty("clear-request-parameters",Boolean.TRUE.toString());
	}
	else{
		actId=ParamUtil.getLong(renderRequest, "actId", 0);
	}
		
		
if(actId==0)// TODO Auto-generated method stub
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{
		LearningActivity activity;
		try {
			activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			long typeId=activity.getTypeId();
			
			if(typeId==2)
			{
				super.render(renderRequest, renderResponse);
			}
			else
			{
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
			}
		} catch (PortalException e) {
		} catch (SystemException e) {
		}			
}
}

}
