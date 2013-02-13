package com.liferay.lms.portlet.p2p;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import com.liferay.lms.moduleUpload;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.P2pActivityCorrections;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.impl.P2pActivityCorrectionsImpl;
import com.liferay.lms.model.impl.P2pActivityImpl;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.util.mail.MailEngine;
import com.liferay.util.mail.MailEngineException;



/**
 * Portlet implementation class P2PActivityPortlet
 */
public class P2PActivityPortlet extends MVCPortlet {
	
	
	@ProcessAction(name = "addP2PActivity")
	public void addP2PActivity(ActionRequest request, ActionResponse response) throws Exception
	{
		try{
			
			UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			
			ServiceContext serviceContext = null;
			try {
				serviceContext = ServiceContextFactory.getInstance(request);
			} catch (PortalException e1) {
				e1.printStackTrace();
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
			
			//Obtenemos los campos necesarios.
			User user = UserLocalServiceUtil.getUser(themeDisplay.getUserId());
			Long groupId = themeDisplay.getScopeGroupId();
			
			String description = uploadRequest.getParameter("description");
			Long actId = Long.parseLong(uploadRequest.getParameter("actId"));
			Long p2pActivityId = Long.parseLong(uploadRequest.getParameter("p2pActivityId"));
			
			String fileName = uploadRequest.getFileName("fileName");
			String title = fileName;
			File file = uploadRequest.getFile("fileName");
			
			if((description!=null && !description.equals(""))  && (fileName!=null && !fileName.equals(""))){
			
				//Obtenermos el Id de directorio. Creamos el directorio si no existe.
				long folderId = createDLFolders(user.getUserId(), groupId, serviceContext);
				
				//Subimos el Archivo en la Document Library
			/*	TODODLFileEntry dlDocument = DLFileEntryLocalServiceUtil.addFileEntry(
					user.getUserId(), groupId, folderId, fileName, title, description, "", "", file, serviceContext);
				
				//Damos permisos al archivo para usuarios de comunidad.
				DLFileEntryLocalServiceUtil.addFileEntryResources(dlDocument, true, false);
				*/
				//Registramos la actividad p2p del usuario.
				P2pActivity p2pActivity = new P2pActivityImpl();
				p2pActivity.setActId(actId);
				p2pActivity.setDate(new Date(System.currentTimeMillis()));
				//p2pActivity.setFileEntryId(dlDocument.getFileEntryId());
				p2pActivity.setUserId(user.getUserId());
				p2pActivity.setDescription(description);
				p2pActivity.setP2pActivityId(p2pActivityId);
				P2pActivityLocalServiceUtil.addP2pActivity(p2pActivity);
				
				//Creamos el LearningActivityTry
				LearningActivityTry learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
				learningTry.setStartDate(new java.util.Date(System.currentTimeMillis()));
				learningTry.setUserId(user.getUserId());
				learningTry.setResult(0);
				LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningTry);
				
				/*if(!LearningActivityResultLocalServiceUtil.existsLearningActivityResult(actId, user.getUserId())){
					LearningActivityResultLocalServiceUtil.update(learningTry);
				}*/
				request.setAttribute("latId", learningTry.getLatId());
			}
			else{
				SessionErrors.add(request, "campos-necesarios-vacios");
			}
			
			request.setAttribute("actId", actId);
		}
		catch(Exception e){
			e.printStackTrace();
			if (_log.isErrorEnabled()) {
				_log.error("Error P2pActivityPortlet.addP2PActivity");
				_log.error(e.getMessage());
			}
			SessionErrors.add(request, "error-subir-p2p");
		}

	}
	
	@ProcessAction(name = "saveCorrection")
	public void saveCorrection(ActionRequest request, ActionResponse response) throws Exception {
		
		
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);		
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);		
		PortletConfig portletConfig = this.getPortletConfig();
		
		ServiceContext serviceContext = null;
		
		try {
			serviceContext = ServiceContextFactory.getInstance(request);
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		
		//Obtenemos los campos necesarios.
		User user = UserLocalServiceUtil.getUser(themeDisplay.getUserId());
		Long groupId = themeDisplay.getScopeGroupId();
		
		String description = uploadRequest.getParameter("description");
		Long actId = Long.parseLong(uploadRequest.getParameter("actId"));
		Long latId = Long.parseLong(uploadRequest.getParameter("latId"));
		Long p2pActivityId = Long.parseLong(uploadRequest.getParameter("p2pActivityId"));
		
		String fileName = uploadRequest.getFileName("fileName");
		String title = fileName;
		File file = uploadRequest.getFile("fileName");

		
		if(description!=null && !description.equals("")){
		
			try{
				long fileId = new Long(0);
				
				//Si ha subido un archivo se guarda.
				if(fileName!=null && !fileName.equals("")){
					//Obtenermos el Id de directorio. Creamos el directorio si no existe.
					long folderId = createDLFolders(user.getUserId(), groupId, serviceContext);
					/*TODO
					//Subimos el Archivo en la Document Library
					DLFileEntry dlDocument = DLFileEntryLocalServiceUtil.addFileEntry(
						user.getUserId(), groupId, folderId, fileName, title, description, "", "", file, serviceContext);
					
					//Damos permisos al archivo para usuarios de comunidad.
					DLFileEntryLocalServiceUtil.addFileEntryResources(dlDocument, true, false);
					
					fileId = dlDocument.getFileEntryId();
					*/
				}				
				
				//Registramos la correcion realizada por el usuario
				P2pActivityCorrections p2pActC = new P2pActivityCorrectionsImpl();
				p2pActC.setActId(actId);
				p2pActC.setDescription(description);
				p2pActC.setP2pActivityId(p2pActivityId);
				p2pActC.setDate(new Date(System.currentTimeMillis()));
				p2pActC.setFileEntryId(fileId);
				p2pActC.setUserId(user.getUserId());
				p2pActC = P2pActivityCorrectionsLocalServiceUtil.addorUpdateP2pActivityCorrections(p2pActC);
				
				//actualizamos el count de la P2pActivity.
				P2pActivity p2pActivity = P2pActivityLocalServiceUtil.getP2pActivity(p2pActivityId);
				p2pActivity.setCountCorrections(p2pActivity.getCountCorrections()+1);
				P2pActivityLocalServiceUtil.updateP2pActivity(p2pActivity);
				
				//Si ya se ha llegado al numero de correciones ponemos el learnignActivityTry a completo.
				LearningActivity myActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
				int numTries;
				
				if(myActivity.getExtracontent().equals(""))
					numTries=3;
				else
					numTries= Integer.parseInt(myActivity.getExtracontent());
				
				if(p2pActivity.getCountCorrections()>= numTries){
					List<LearningActivityTry> learningTryList = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUser(actId, user.getUserId());
					if(learningTryList!=null && learningTryList.size()>0){
						updateStateActivity(learningTryList.get(0).getLatId());
					}
					/*if(!LearningActivityResultLocalServiceUtil.existsLearningActivityResult(actId, user.getUserId())){
						LearningActivityResult lar = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, user.getUserId());
						lar.setPassed(true);
						lar.setResult(new Long(100));
						LearningActivityResultLocalServiceUtil.updateLearningActivityResult(lar);
					}*/
				}
				User userPropietaryP2pAct = UserLocalServiceUtil.getUser(p2pActivity.getUserId());
				
				
				//Obtenemso la url del reto de la actividad.
				Group grupo=themeDisplay.getScopeGroup();
				long retoplid=themeDisplay.getPlid();
				for(Layout theLayout:LayoutLocalServiceUtil.getLayouts(grupo.getGroupId(),false))
				{
					if(theLayout.getFriendlyURL().equals("/reto"))
					{
						retoplid=theLayout.getPlid();
					}
				}
				LearningActivity  act = LearningActivityLocalServiceUtil.getLearningActivity(actId);
				Module myModule = ModuleLocalServiceUtil.getModule(act.getModuleId());
				
				/*PortletURL portletURL = PortletURLFactoryUtil.create(
						uploadRequest, "lmsactivitieslist_WAR_liferaylmsportlet", retoplid,
						PortletRequest.RENDER_PHASE);
				portletURL.setParameter("moduleId", Long.toString(myModule.getModuleId()));
				String url = portletURL.toString();*/
				PortletURL portletURL = PortletURLFactoryUtil.create(
				uploadRequest, "p2ptaskactivity_WAR_liferaylmsportlet", retoplid,
				PortletRequest.RENDER_PHASE);
				portletURL.setParameter("moduleId", Long.toString(myModule.getModuleId()));
				portletURL.setParameter("actId", Long.toString(act.getActId()));
				portletURL.setParameter("showRevision", "1");
				String url = portletURL.toString();
				
				sendMailCorrection(userPropietaryP2pAct, actId, p2pActC, themeDisplay, portletConfig, url);
				request.setAttribute("actId", actId);
				request.setAttribute("latId", latId);
				
			}
			catch(Exception e){
				e.printStackTrace();
				if (_log.isErrorEnabled()) {
					_log.error("Error getting P2pActivityPortlet.saveCorrection");
					_log.error(e);
				}
				SessionErrors.add(request, "error-p2ptask-correction");
			}
		}else{
			SessionErrors.add(request, "p2ptask-no-empty-answer");
		}
	}
	
	private long createDLFolders(Long userId,Long groupId,ServiceContext serviceContext) throws PortalException, SystemException{
		//Variables for folder ids
		Long dlMainFolderId = 0L;
		Long dlPortletFolderId = 0L;
		Long dlRecordFolderId = 0L;
		//Search for folder in Document Library
        boolean dlMainFolderFound = false;
        boolean dlPortletFolderFound = false;
        //Get main folder
        try {
        	//Get main folder
        	DLFolder dlFolderMain = DLFolderLocalServiceUtil.getFolder(groupId,0,moduleUpload.DOCUMENTLIBRARY_MAINFOLDER);
        	dlMainFolderId = dlFolderMain.getFolderId();
        	dlMainFolderFound = true;
        	//Get portlet folder
        	DLFolder dlFolderPortlet = DLFolderLocalServiceUtil.getFolder(groupId,dlMainFolderId,moduleUpload.DOCUMENTLIBRARY_PORTLETFOLDER);
        	dlPortletFolderId = dlFolderPortlet.getFolderId();
        	dlPortletFolderFound = true;
        } catch (Exception ex){
        	ex.printStackTrace();//Not found Main Folder
        }
        //Create main folder if not exist
        if(!dlMainFolderFound){
        	/*TODO
        	DLFolder newDocumentMainFolder = DLFolderLocalServiceUtil.addFolder(userId, groupId, 0, moduleUpload.DOCUMENTLIBRARY_MAINFOLDER, moduleUpload.DOCUMENTLIBRARY_MAINFOLDER_DESCRIPTION, serviceContext);
        	DLFolderLocalServiceUtil.addFolderResources(newDocumentMainFolder, true, false);
        	dlMainFolderId = newDocumentMainFolder.getFolderId();
        	dlMainFolderFound = true;
        	*/
        }
        //Create portlet folder if not exist
        if(dlMainFolderFound && !dlPortletFolderFound){
        	/*TODO
        	DLFolder newDocumentPortletFolder = DLFolderLocalServiceUtil.addFolder(userId, groupId, dlMainFolderId , moduleUpload.DOCUMENTLIBRARY_PORTLETFOLDER, moduleUpload.DOCUMENTLIBRARY_PORTLETFOLDER_DESCRIPTION, serviceContext);
        	DLFolderLocalServiceUtil.addFolderResources(newDocumentPortletFolder, true, false);
        	dlPortletFolderFound = true;
            dlPortletFolderId = newDocumentPortletFolder.getFolderId();
            */
        }

        //Create this record folder
        if(dlPortletFolderFound){
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        	Date date = new Date();
        	/*TODO
        	String dlRecordFolderName = dateFormat.format(date)+moduleUpload.SEPARATOR+userId;
        	DLFolder newDocumentRecordFolder = DLFolderLocalServiceUtil.addFolder(userId, groupId, dlPortletFolderId, dlRecordFolderName, dlRecordFolderName, serviceContext);
        	DLFolderLocalServiceUtil.addFolderResources(newDocumentRecordFolder, true, false);
        	dlRecordFolderId = newDocumentRecordFolder.getFolderId();
        	*/
        }
        return dlRecordFolderId;
	}
	
	public void numValidaciones(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		long actId = ParamUtil.getLong(actionRequest, "actId", 0);
		long numValidaciones = ParamUtil.getLong(actionRequest, "numValidaciones", 0);
	
		LearningActivity larn = LearningActivityServiceUtil.getLearningActivity(actId);
		//Lo guardamos en el campo Extracontent
		larn.setExtracontent(Long.toString(numValidaciones));
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivity.class.getName(), actionRequest);
		//LearningActivityServiceUtil.modLearningActivity(larn, serviceContext);
		LearningActivityServiceUtil.modLearningActivity(larn);
	
		SessionMessages.add(actionRequest, "activity-saved-successfully");
	}

	public static void updateStateActivity(Long latId) throws Exception {
		
		LearningActivityTry larntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);
		
		if(larntry.getResult()<100){
			larntry.setResult(new Long(100));
			larntry.setEndDate(new java.util.Date(System.currentTimeMillis()));
			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(larntry);
		}
	}
	
	private static void sendMailCorrection(User user, long actId, 
			P2pActivityCorrections p2pActiCor, ThemeDisplay themeDisplay,PortletConfig portletConfig, String url){
		try
		{
			String firmaPortal  = PrefsPropsUtil.getString(themeDisplay.getCompanyId(),"firma.email.admin");

			LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			
			String subject = new String(LanguageUtil.format(portletConfig, themeDisplay.getLocale(), 
					"correction-subject-mail", new Object[]{activity.getTitle(themeDisplay.getLocale())}).getBytes(), Charset.forName("UTF-8"));
			if(_log.isDebugEnabled()){_log.debug("P2PActivityPortlet::sendMailCorrection::subject:"+subject);}
			
			String body=new String(LanguageUtil.format(portletConfig, themeDisplay.getLocale(), "correction-body-mail", 
					new Object[]{user.getFullName(),url,firmaPortal}).getBytes(), Charset.forName("UTF-8"));
			if(_log.isDebugEnabled()){_log.debug("P2PActivityPortlet::sendMailCorrection::body:"+body);}
			
			String fromUser=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
			InternetAddress from = new InternetAddress(fromUser, "");
			InternetAddress to = new InternetAddress(user.getEmailAddress(), user.getFullName());
			
			//MailEngine.send(from, user.getEmailAddress(), subject, body);
			MailEngine.send(from, new InternetAddress[]{to}, new InternetAddress[]{}, subject, body, true);
			if(_log.isDebugEnabled()){_log.debug("P2PActivityPortlet::sendMailCorrection::Mail Enviado");}
		}
		catch(MailEngineException ex) {
			if(_log.isErrorEnabled()){_log.error(ex);}

		} catch (Exception e) {
			if(_log.isErrorEnabled()){_log.error(e);}
		}
	}
	
	public static void sendMailNoCorrection(User user, long actId, 
			ThemeDisplay themeDisplay,PortletConfig portletConfig, String url){
		try
		{
			String firmaPortal  = PrefsPropsUtil.getString(themeDisplay.getCompanyId(),"firma.email.admin");

			LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
			
			String subject = new String(LanguageUtil.format(portletConfig, themeDisplay.getLocale(), 
					"no-p2p-correction-yet-subject", new Object[]{activity.getTitle(themeDisplay.getLocale())}).getBytes(), Charset.forName("UTF-8"));
			if(_log.isDebugEnabled()){_log.debug("P2PActivityPortlet::sendMailNoCorrection::subject:"+subject);}
			
			String body=new String(LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
					"no-p2p-correction-yet-body", new Object[]{user.getFullName(),firmaPortal}).getBytes(), Charset.forName("UTF-8"));
			if(_log.isDebugEnabled()){_log.debug("P2PActivityPortlet::sendMailNoCorrection::body:"+body);}
			
			/*String from=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
			MailEngine.send(from, user.getEmailAddress(), subject, body);*/
			String fromUser=PrefsPropsUtil.getString(user.getCompanyId(),PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
			InternetAddress from = new InternetAddress(fromUser, "");
			InternetAddress to = new InternetAddress(user.getEmailAddress(), user.getFullName());
			
			//MailEngine.send(from, user.getEmailAddress(), subject, body);
			MailEngine.send(from, new InternetAddress[]{to}, new InternetAddress[]{}, subject, body, true);
			if(_log.isDebugEnabled()){_log.debug("P2PActivityPortlet::sendMailNoCorrection::Mail Enviado");}
		}
		catch(MailEngineException ex) {
			if(_log.isErrorEnabled()){_log.error(ex);}

		} catch (Exception e) {
			if(_log.isErrorEnabled()){_log.error(e);}
		}
	}
	
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
	throws PortletException, IOException {
		// TODO Auto-generated method stub
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
				
				if(typeId==3)
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
	
	private static Log _log = LogFactoryUtil.getLog(P2pActivity.class);
}
