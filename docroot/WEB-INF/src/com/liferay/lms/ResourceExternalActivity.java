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
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
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
	
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String jspPage = ParamUtil.getString(actionRequest, "jspPage");
		long actId = ParamUtil.getLong(actionRequest, "actId", 0);
		long entryId = ParamUtil.getLong(actionRequest, "entryId", 0);
		ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);
	
		LearningActivity larn = LearningActivityServiceUtil.getLearningActivity(actId);
		larn.setExtracontent(Long.toString(entryId));
		LearningActivityServiceUtil.modLearningActivity(larn, serviceContext);
		SessionMessages.add(actionRequest, "activity-saved-successfully");
		actionResponse.setRenderParameter("jspPage", jspPage);
		actionResponse.setRenderParameter("actId", Long.toString(actId));
	}
	
	public void addfiles(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {

	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
	UploadPortletRequest request = PortalUtil.getUploadPortletRequest(actionRequest);
	
	String jspPage = ParamUtil.getString(actionRequest, "jspPage");
	long actId = ParamUtil.getLong(actionRequest, "actId", 0);
	long entryId = ParamUtil.getLong(actionRequest, "entryId", 0);
	ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);
	String fileName = request.getFileName("fileName");
	String fileName2 = request.getFileName("fileName2");
	boolean deleteVideo=ParamUtil.getBoolean(request, "deletevideo",false);
	boolean deleteComplementary=ParamUtil.getBoolean(request, "deletecomplementary",false);
	String description = request.getParameter("description");
	String youtubecode=ParamUtil.getString(request,"youtubecode","");
	String title = fileName;// + " uploaded by " + user.getFullName();
	AssetEntry entry=null;
	AssetEntry entry2=null;
	LearningActivity larn = LearningActivityServiceUtil.getLearningActivity(actId);
	String extraContent=larn.getExtracontent();
	Document document = SAXReaderUtil.createDocument();
	Element rootElement = document.addElement("multimediaentry");
	if(extraContent!=null &&!"".equals(extraContent)&&!Validator.isNumber(extraContent))
	{
		document=SAXReaderUtil.read(extraContent);
		rootElement =document.getRootElement();
	}
	if(deleteVideo)
	{
		Element video=rootElement.element("video");
		if(!video.attributeValue("id","").equals(""))
		{
			AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(video.attributeValue("id")));
			FileEntry videofile=DLAppLocalServiceUtil.getFileEntry(videoAsset.getClassPK());
			//TODO DLFileVersion videofileVersion = videofile.getFileVersion();
			DLAppLocalServiceUtil.deleteFileEntry(videofile.getFileEntryId());
		}
		video.detach();
		rootElement.remove(video);
		
		
	}
	if(deleteComplementary)
	{
		Element documento=rootElement.element("document");
		if(!documento.attributeValue("id","").equals(""))
		{
			AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(documento.attributeValue("id")));
			FileEntry videofile=DLAppLocalServiceUtil.getFileEntry(videoAsset.getClassPK());
			//TODO DLFileVersion videofileVersion = videofile.getFileVersion();
			DLAppLocalServiceUtil.deleteFileEntry(videofile.getFileEntryId());
		}
		documento.detach();
		rootElement.remove(documento);
		
	}
	
	if(fileName!=null && !fileName.equals(""))
	{
		File file = request.getFile("fileName");
		long filesize=	request.getSize("fileName");
		
		Element video=rootElement.element("video");
		if(video!=null &&!video.attributeValue("id","").equals(""))
		{
			AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(video.attributeValue("id")));
			DLFileEntry videofile=DLFileEntryLocalServiceUtil.getDLFileEntry(videoAsset.getClassPK());
			DLFileVersion videofileVersion = videofile.getFileVersion();
			DLFileEntryLocalServiceUtil.deleteFileEntry(videofile.getFileEntryId());
		}
		long folderId=createDLFolders(actionRequest, themeDisplay.getUserId(),themeDisplay.getScopeGroupId(), serviceContext);
		InputStream istrem=new FileInputStream(file);
		String mimeType=MimeTypesUtil.getContentType(file.getName());
		DLFileEntry dlDocument = DLFileEntryLocalServiceUtil.addFileEntry(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), themeDisplay.getScopeGroupId(), folderId, fileName,mimeType,
				title, title,"",0,new HashMap<String,Fields>(), file,
				istrem,filesize, serviceContext);
			
			//Damos permisos al archivo para usuarios de comunidad.
		DLFileEntryLocalServiceUtil.addFileEntryResources(dlDocument, true, false);
		entry=AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), dlDocument.getPrimaryKey())	;
		}

	if(fileName2!=null && !fileName2.equals(""))
	{
		File file = request.getFile("fileName2");
		long filesize=	request.getSize("fileName2");
		Element documento=rootElement.element("document");
		if(documento!=null&&!documento.attributeValue("id","").equals(""))
		{
			AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(documento.attributeValue("id")));
			DLFileEntry videofile=DLFileEntryLocalServiceUtil.getDLFileEntry(videoAsset.getClassPK());
			DLFileVersion videofileVersion = videofile.getFileVersion();
			DLFileEntryLocalServiceUtil.deleteFileEntry(videofile.getFileEntryId());
		}
		long folderId=createDLFolders(actionRequest, themeDisplay.getUserId(),themeDisplay.getScopeGroupId(), serviceContext);
		InputStream istrem=new FileInputStream(file);
		String mimeType=MimeTypesUtil.getContentType(file.getName());
		
		DLFileEntry dlDocument = DLFileEntryLocalServiceUtil.addFileEntry(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), themeDisplay.getScopeGroupId(), folderId, fileName,mimeType,
				title, title,"",0,new HashMap<String,Fields>(), file,
				istrem,filesize, serviceContext);
			
			//Damos permisos al archivo para usuarios de comunidad.
		DLFileEntryLocalServiceUtil.addFileEntryResources(dlDocument, true, false);
		entry2=AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), dlDocument.getPrimaryKey())	;
		if(entry2!=null)
		{
			if(documento!=null)
			{
				documento.detach();
				rootElement.remove(documento);
			}
			documento=SAXReaderUtil.createElement("document");
			documento.addAttribute("id", Long.toString(entry2.getEntryId()));
			rootElement.add(documento);
		}
	}
	
	if(entry!=null || !"".equals(youtubecode))
	{
		Element video=rootElement.element("video");
		if(video!=null)
		{
			video.detach();
			rootElement.remove(video);
		}
		video = SAXReaderUtil.createElement("video");
		if(entry!=null)
		{
			video.addAttribute("id", Long.toString(entry.getEntryId()));
			video.setText("");
		}
		else
		{
			
			video.setText(youtubecode);
		}
		
		rootElement.add(video);
		
		
		
	}
	larn.setExtracontent(document.formattedString());
	larn.setDescription( description,themeDisplay.getLocale());
	//LearningActivityServiceUtil.modLearningActivity(larn, serviceContext);
	LearningActivityServiceUtil.modLearningActivity(larn);
	SessionMessages.add(actionRequest, "activity-saved-successfully");
	actionResponse.setRenderParameter("jspPage", jspPage);
	actionResponse.setRenderParameter("actId", Long.toString(actId));
}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
	throws PortletException, IOException {

if(ParamUtil.getLong(renderRequest, "actId", 0)==0)// TODO Auto-generated method stub
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{
		LearningActivity activity;
		try {
			activity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(renderRequest, "actId", 0));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
}
}
	private long createDLFolders(ActionRequest request,Long userId,Long groupId,ServiceContext serviceContext) throws PortalException, SystemException{
		//Variables for folder ids
		Long dlMainFolderId = 0L;
		//Search for folder in Document Library
        boolean dlMainFolderFound = false;
        //Get main folder
        try {
        	//Get main folder
        	Folder dlFolderMain = DLAppLocalServiceUtil.getFolder(groupId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,DOCUMENTLIBRARY_MAINFOLDER);
        	dlMainFolderId = dlFolderMain.getFolderId();
        	dlMainFolderFound = true;
        	//Get portlet folder
        } catch (Exception ex){
        	ex.printStackTrace();//Not found Main Folder
        }
        //Create main folder if not exist
        if(!dlMainFolderFound){
        	Folder newDocumentMainFolder = DLAppLocalServiceUtil.addFolder(userId, groupId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, DOCUMENTLIBRARY_MAINFOLDER, DOCUMENTLIBRARY_MAINFOLDER, serviceContext);
        	dlMainFolderId = newDocumentMainFolder.getFolderId();
        	dlMainFolderFound = true;
        }
        //Create portlet folder if not exist
     
  
        return dlMainFolderId;
	}
}
