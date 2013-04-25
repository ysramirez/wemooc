package com.liferay.lms.learningactivity;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.lms.asset.ResourceExternalAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

public class ResourceExternalLearningActivityType extends BaseLearningActivityType 
{
	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads";
	
	@Override
	public boolean gradebook() {
		return false;
	}


	@Override
	public long getDefaultScore() {
		return 0;
	}


	@Override
	public String getName() {
		
		return "resourceExternal";
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) {
		
		return new ResourceExternalAssetRenderer(learningactivity);
	}


	@Override
	public long getTypeId() {
		return 2;
	}
	
	@Override
	public String getExpecificContentPage() {
		return "/html/resourceExternalActivity/admin/edit.jsp";
	}
	
	@Override
	public boolean hasEditDetails() {
		return false;
	}

	
	@Override
	public void setExtraContent(UploadRequest uploadRequest,
			PortletResponse portletResponse, LearningActivity learningActivity)
			throws PortalException, SystemException, DocumentException,IOException {
		PortletRequest portletRequest = (PortletRequest)uploadRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		String youtubecode=ParamUtil.getString(uploadRequest,"youtubecode");
		String additionalFile = uploadRequest.getFileName("additionalFile");
		boolean deleteVideo=ParamUtil.getBoolean(uploadRequest, "deleteAdditionalFile",false);
		
		if((deleteVideo)||
		   (!StringPool.BLANK.equals(youtubecode.trim()))||
		   ((additionalFile!=null)&&(!StringPool.BLANK.equals(additionalFile.trim())))){
			Document document = null;
			Element rootElement = null;
			if((learningActivity.getExtracontent()==null)||(learningActivity.getExtracontent().trim().length()==0)){
				document = SAXReaderUtil.createDocument();
				rootElement = document.addElement("multimediaentry");
			}
			else
			{
				document=SAXReaderUtil.read(learningActivity.getExtracontent());
				rootElement =document.getRootElement();
			}
			
			if(!StringPool.BLANK.equals(youtubecode.trim())){
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
			

			if((deleteVideo)||
			   ((additionalFile!=null)&&(!StringPool.BLANK.equals(additionalFile.trim())))){

				Element additionalDocumentElement=rootElement.element("document");
				if((additionalDocumentElement!=null)&&(additionalDocumentElement.attributeValue("id","").length()!=0))
				{
					AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(additionalDocumentElement.attributeValue("id")));
					FileEntry videofile=DLAppLocalServiceUtil.getFileEntry(videoAsset.getClassPK());
					DLAppLocalServiceUtil.deleteFileEntry(videofile.getFileEntryId());
					additionalDocumentElement.detach();
					rootElement.remove(additionalDocumentElement);
				}
			}
			
			if((additionalFile!=null)&&(!StringPool.BLANK.equals(additionalFile.trim()))){
				
				ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
				long repositoryId = DLFolderConstants.getDataRepositoryId(themeDisplay.getScopeGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
				long folderId=createDLFolders(themeDisplay.getUserId(),repositoryId, portletRequest);
				ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFileEntry.class.getName(), portletRequest);
				
				//Damos permisos al archivo para usuarios de comunidad.
				serviceContext.setAddGroupPermissions(true);

				FileEntry dlDocument = DLAppLocalServiceUtil.addFileEntry(
					                      themeDisplay.getUserId(), repositoryId , folderId , additionalFile, uploadRequest.getContentType("additionalFile"), 
					                      additionalFile, StringPool.BLANK, StringPool.BLANK, uploadRequest.getFile("additionalFile") , serviceContext ) ;

				Element additionalDocumentElement=SAXReaderUtil.createElement("document");
				additionalDocumentElement.addAttribute("id", Long.toString(AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), dlDocument.getPrimaryKey()).getEntryId()));
				rootElement.add(additionalDocumentElement);	
			}
			
			
			learningActivity.setExtracontent(document.formattedString());	
		}
		
	}
	
	private long createDLFolders(Long userId,Long repositoryId,PortletRequest portletRequest) throws PortalException, SystemException{
		//Variables for folder ids
		Long dlMainFolderId = 0L;
		//Search for folder in Document Library
        boolean dlMainFolderFound = false;
        //Get main folder
        try {
        	//Get main folder
        	Folder dlFolderMain = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,DOCUMENTLIBRARY_MAINFOLDER);
        	dlMainFolderId = dlFolderMain.getFolderId();
        	dlMainFolderFound = true;
        	//Get portlet folder
        } catch (Exception ex){
        }
        
		ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFolder.class.getName(), portletRequest);
		//Damos permisos al archivo para usuarios de comunidad.
		serviceContext.setAddGroupPermissions(true);
        
        //Create main folder if not exist
        if(!dlMainFolderFound){
        	Folder newDocumentMainFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, DOCUMENTLIBRARY_MAINFOLDER, DOCUMENTLIBRARY_MAINFOLDER, serviceContext);
        	dlMainFolderFound = true;
        	dlMainFolderId = newDocumentMainFolder.getFolderId();
        }
        //Create portlet folder if not exist
     
  
        return dlMainFolderId;
	}
		
}
