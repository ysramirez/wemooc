package com.liferay.lms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVReader;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.thread.CloneCourseThread;
import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CourseAdmin
 */
public class CourseAdmin extends MVCPortlet {

	public static String DOCUMENTLIBRARY_MAINFOLDER = "ResourceUploads";
	
	public static String IMAGEGALLERY_MAINFOLDER = "icons";
	public static String IMAGEGALLERY_PORTLETFOLDER = "course";
	public static String IMAGEGALLERY_MAINFOLDER_DESCRIPTION = "Course Image Uploads";
	public static String IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION = "";
	
	private static Log _log = LogFactoryUtil.getLog(CourseAdmin.class);

	public void deleteCourse(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Course.class.getName(), actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		String redirect = ParamUtil.getString(actionRequest, "redirect");

		User user = themeDisplay.getUser();
		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		if (courseId > 0) {
			CourseLocalServiceUtil.deleteCourse(courseId);
		}
	}

	public void saveCourse(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), uploadRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		String redirect = ParamUtil.getString(uploadRequest, "redirect");

		User user = themeDisplay.getUser();
		Enumeration<String> parNam = uploadRequest.getParameterNames();
		String title = "";
		while (parNam.hasMoreElements()) {
			String paramName = parNam.nextElement();
			if (paramName.startsWith("title_") && paramName.length() > 6) {
				if (title.equals("")) {
					title = uploadRequest.getParameter(paramName);
				}

			}
		}

		String description = uploadRequest.getParameter("description");
		long icon = ParamUtil.getLong(uploadRequest, "icon", 0);
		//Cambiar la imagen de la comunidad
		
		String fileName = uploadRequest.getFileName("fileName");
		long courseId = ParamUtil.getLong(uploadRequest, "courseId", 0);
		long courseTemplateId=ParamUtil.getLong(uploadRequest,"courseTemplate",0);
		String friendlyURL = ParamUtil.getString(uploadRequest, "friendlyURL",
				"");
		int startMonth = ParamUtil.getInteger(uploadRequest, "startMon");
		int startYear = ParamUtil.getInteger(uploadRequest, "startYear");
		int startDay = ParamUtil.getInteger(uploadRequest, "startDay");
		int startHour = ParamUtil.getInteger(uploadRequest, "startHour");
		int startMinute = ParamUtil.getInteger(uploadRequest, "startMin");
		int startAMPM = ParamUtil.getInteger(uploadRequest, "startAMPM");
		String summary = ParamUtil.getString(uploadRequest, "summary", "");
		boolean visible = ParamUtil.getBoolean(uploadRequest, "visible", false);
		
		long courseEvalId=ParamUtil.getLong(uploadRequest, "courseEvalId", 0);

		if (friendlyURL.equals("") && !title.equals("")) {
			friendlyURL = StringPool.BLANK;
		}

		if (startAMPM > 0) {
			startHour += 12;
		}
		Date startDate = PortalUtil.getDate(startMonth, startDay, startYear,
					startHour, startMinute, user.getTimeZone(),
					new EntryDisplayDateException());

		int stopMonth = ParamUtil.getInteger(uploadRequest, "stopMon");
		int stopYear = ParamUtil.getInteger(uploadRequest, "stopYear");
		int stopDay = ParamUtil.getInteger(uploadRequest, "stopDay");
		int stopHour = ParamUtil.getInteger(uploadRequest, "stopHour");
		int stopMinute = ParamUtil.getInteger(uploadRequest, "stopMin");
		int stopAMPM = ParamUtil.getInteger(uploadRequest, "stopAMPM");
		if (stopAMPM > 0) {
			stopHour += 12;
		}
		Date stopDate = new Date();
		try {
			stopDate = PortalUtil.getDate(stopMonth, stopDay, stopYear,
					stopHour, stopMinute, user.getTimeZone(),
					new EntryDisplayDateException());
		} catch (PortalException e1) {
			e1.printStackTrace();
		}

		java.util.Date ahora = new java.util.Date(System.currentTimeMillis());
		// Validations
		boolean noTitle = true;
		Enumeration<String> parNames = uploadRequest.getParameterNames();
		while (parNames.hasMoreElements()) {
			String paramName = parNames.nextElement();
			if (paramName.startsWith("title_")
					&& paramName.length() > 6
					&& ParamUtil.getString(uploadRequest, paramName, "")
					.length() > 0) {
				noTitle = false;
			}
		}
		if (noTitle) {
			SessionErrors.add(actionRequest, "title-required");
			actionResponse.setRenderParameter("jspPage",
					"/html/courseadmin/editcourse.jsp");
			return;
		}
		
		boolean requiredCourseIcon = GetterUtil.getBoolean(PropsUtil.get("lms.course.icon.required"), false);

		if (requiredCourseIcon) {
			if (Validator.isNull(icon) && Validator.isNull(fileName)) {
				SessionErrors.add(actionRequest, "course-icon-required");
				actionResponse.setRenderParameter("jspPage",
						"/html/courseadmin/editcourse.jsp");
				return;
			}
		}
		
		//File size validation
		if (Validator.isNotNull(fileName) && !validateFileSize(uploadRequest.getFile("fileName"))){
			SessionErrors.add(actionRequest, "error-file-size");
			actionResponse.setRenderParameter("jspPage",
					"/html/courseadmin/editcourse.jsp");
			return;
		}
		
		try{
			AssetEntryLocalServiceUtil.validate(serviceContext.getScopeGroupId(), Course.class.getName(), serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames());
		}catch(Exception e){
			actionResponse.setRenderParameters(getParametersMapWithoutNulls(uploadRequest));
			List<String> errors = new ArrayList<String>();
			if (e instanceof AssetCategoryException) {
				AssetCategoryException ace = (AssetCategoryException)e;
				AssetVocabulary assetVocabulary = ace.getVocabulary();
				String vocabularyTitle = StringPool.BLANK;
				if (assetVocabulary != null) 
					vocabularyTitle = assetVocabulary.getTitle(themeDisplay.getLocale());

				if (ace.getType() == AssetCategoryException.AT_LEAST_ONE_CATEGORY) 
					errors.add(LanguageUtil.format(themeDisplay.getLocale(),"please-select-at-least-one-category-for-x", vocabularyTitle));
				else if (ace.getType() ==AssetCategoryException.TOO_MANY_CATEGORIES) 
					errors.add(LanguageUtil.format(themeDisplay.getLocale(), "you-cannot-select-more-than-one-category-for-x", vocabularyTitle));
			}else 
				errors.add(LanguageUtil.get(themeDisplay.getLocale(), "an-unexpected-error-occurred-while-saving"));

			SessionErrors.add(actionRequest, "newCourseErrors", errors);
			actionResponse.setRenderParameter("jspPage", "/html/courseadmin/editcourse.jsp");
			return;
		}


		Course course = null;
		if (courseId == 0) {
			course = com.liferay.lms.service.CourseLocalServiceUtil.addCourse(
					title, description, summary, friendlyURL,
					themeDisplay.getLocale(), ahora, startDate, stopDate,courseTemplateId,
					serviceContext);
			if(course != null){
				long[] groupIds = new long[1];
				groupIds[0] = course.getGroupCreatedId();
				GroupLocalServiceUtil.addUserGroups(user.getUserId(), groupIds);
				long[] roles = new long[2];
				LmsPrefs prefs = LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay
						.getCompanyId());
				roles[0] = prefs.getEditorRole();
				roles[1] = prefs.getTeacherRole();
				UserGroupRoleLocalServiceUtil.addUserGroupRoles(user.getUserId(),
						course.getGroupCreatedId(), roles);

				/* activamos el social equity */
				AssetEntry aEntryCourse = AssetEntryLocalServiceUtil.getEntry(
						Course.class.getName(), course.getCourseId());
			}

		}
		// Estamos editando un curso existente.
		else {

			course = CourseLocalServiceUtil.getCourse(courseId);
			course.setDescription( description,themeDisplay.getLocale());
			course.setStartDate(startDate); 
			course.setEndDate(stopDate);
			com.liferay.lms.service.CourseLocalServiceUtil.modCourse(course,
					summary, serviceContext);
		}

		if(course!= null){
			//Cambiar la imagen de la comunidad
			if(fileName!=null && !fileName.equals(""))
			{
				File file = uploadRequest.getFile("fileName");
				//LayoutSetLocalServiceUtil.updateLogo(course.getGroupId(), true, true, file);
				//Saving image
				long fileMaxSize = 5 * 1024 * 1024;
				try {
					fileMaxSize = Long.parseLong(PrefsPropsUtil.getString(PropsKeys.DL_FILE_MAX_SIZE));
					//System.out.println("---\n fileMaxSize 0 : " + fileMaxSize+", "+ file.length());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if(file.length() <= fileMaxSize){
					String contentType = uploadRequest.getContentType("fileName");
					long repositoryId = DLFolderConstants.getDataRepositoryId(course.getGroupCreatedId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
					long igFolderId=createIGFolders(actionRequest, themeDisplay.getUserId(),repositoryId);
								
					//Subimos el Archivo en la Document Library
					ServiceContext serviceContextImg = new ServiceContext();
					serviceContextImg.setScopeGroupId(course.getGroupCreatedId());
					//Damos permisos al archivo para usuarios de comunidad.
					serviceContextImg.setAddGroupPermissions(true);
					serviceContextImg.setAddGuestPermissions(true);
					FileEntry image = DLAppLocalServiceUtil.addFileEntry(
						                      themeDisplay.getUserId(), repositoryId , igFolderId , fileName, contentType, fileName, StringPool.BLANK, StringPool.BLANK, file , serviceContextImg ) ;
							
					course.setIcon(image.getFileEntryId());
				} else if(file.length() > fileMaxSize){
					course.setIcon(0);
				}
			} else if (ParamUtil.getBoolean(uploadRequest, "discardLogo", false) && !requiredCourseIcon) {
				course.setIcon(0);
			}

			boolean oneTitleNotEmpty = false;
			parNames = uploadRequest.getParameterNames();
			while (parNames.hasMoreElements()) {
				String paramName = parNames.nextElement();
				if (paramName.startsWith("title_") && paramName.length() > 6) {
					String language = paramName.substring(6);
					Locale locale = LocaleUtil.fromLanguageId(language);
					course.setTitle(uploadRequest.getParameter(paramName),locale);

					if (!uploadRequest.getParameter(paramName).equals("")) {
						oneTitleNotEmpty = true;
					}
				}
			}

			if (!oneTitleNotEmpty) {
				SessionErrors.add(actionRequest, "title-empty");
				actionResponse.setRenderParameter("jspPage",
						"/html/courseadmin/editcourse.jsp");
				return;
			}
			course.setCourseEvalId(courseEvalId);
			CourseLocalServiceUtil.modCourse(course,summary,serviceContext);
				
			PermissionChecker permissionChecker = PermissionCheckerFactoryUtil
					.getPermissionCheckerFactory().create(user);

			if (permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),
					Course.class.getName(), 0, "PUBLISH")) {

				com.liferay.lms.service.CourseLocalServiceUtil.setVisible(
						course.getCourseId(), visible);
			}
			
			SessionMessages.add(actionRequest, "course-saved-successfully");
			WindowState windowState = actionRequest.getWindowState();
			if (redirect != null && !"".equals(redirect)) {
				if (!windowState.equals(LiferayWindowState.POP_UP)) {
					actionResponse.sendRedirect(redirect);
				} else {
					redirect = PortalUtil.escapeRedirect(redirect);

					if (Validator.isNotNull(redirect)) {
						actionResponse.sendRedirect(redirect);
					}
				}
			}
			
		}

	}
	
	private Map<String, String[]> getParametersMapWithoutNulls(UploadPortletRequest portletRequest) {
		Map<String, String[]> parametersMapWithoutNulls = portletRequest.getParameterMap();
		List<String> keysToRemove = new ArrayList<String>();
		
		java.util.Iterator<String> it = parametersMapWithoutNulls.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if (parametersMapWithoutNulls.get(key) == null) {
				keysToRemove.add(key);
			}
		}
		for (int i = 0; i < keysToRemove.size(); i++) {
			parametersMapWithoutNulls.remove(keysToRemove.get(i));
		}
		return parametersMapWithoutNulls;
	}

	private boolean validateFileSize(File file) {
		boolean valid = true;

		//Comprobar que el tamano del fichero no supere los 5mb
		long size = 5 * 1024 * 1024;
				
		if(file.length() > size){
			valid = false;
		}
		
		return valid;
	}

	public void removeUserRole(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Course.class.getName(), actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		Role commmanager = RoleLocalServiceUtil.getRole(
				themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);

		User user = themeDisplay.getUser();
		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		long roleId = ParamUtil.getLong(actionRequest, "roleId", 0);
		long userId = ParamUtil.getLong(actionRequest, "userId", 0);
		Course course = CourseLocalServiceUtil.getCourse(courseId);
		if (roleId != commmanager.getRoleId()) {
			UserGroupRoleLocalServiceUtil.deleteUserGroupRoles(
					new long[] { userId }, course.getGroupCreatedId(), roleId);
		} else {

			GroupLocalServiceUtil.unsetUserGroups(userId,
					new long[] { course.getGroupCreatedId() });
		}
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
	}

	public void addUserRole(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Course.class.getName(), actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();
		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		long roleId = ParamUtil.getLong(actionRequest, "roleId", 0);
		long userId = ParamUtil.getLong(actionRequest, "userId", 0);
		Course course = CourseLocalServiceUtil.getCourse(courseId);
		if (!GroupLocalServiceUtil.hasUserGroup(userId,
				course.getGroupCreatedId())) {
			GroupLocalServiceUtil.addUserGroups(userId,
					new long[] { course.getGroupCreatedId() });
		}
		UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { userId },
				course.getGroupCreatedId(), roleId);
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
	}


	public void importUserRole(PortletRequest portletRequest,
			PortletResponse portletResponse) throws NestableException, IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		UploadPortletRequest request = PortalUtil.getUploadPortletRequest(portletRequest);
		long courseId = ParamUtil.getLong(portletRequest, "courseId", 0);
		long roleId = ParamUtil.getLong(portletRequest, "roleId", 0);
		String fileName = request.getFileName("fileName");
		Course course = CourseLocalServiceUtil.getCourse(courseId);

		List<String> errors = new ArrayList<String>();
		List<Long> users = new ArrayList<Long>();

		if(fileName==null || StringPool.BLANK.equals(fileName)){
			SessionErrors.add(portletRequest, "courseadmin.importuserrole.csv.fileRequired");
		}
		else{ 
			String contentType = request.getContentType("fileName");	
			System.out.println(" contentType : " + contentType );
			System.out.println(" fileName : " + fileName );
			if (!fileName.endsWith(".csv")) {
				SessionErrors.add(portletRequest, "courseadmin.importuserrole.csv.badFormat");	
			}
			else {
				CSVReader reader = null;
				try {
					File file = request.getFile("fileName");
					System.out.println("----------------------------\n  Import users ");
					reader = new CSVReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"), ';');

					String[] currLine;

					while ((currLine = reader.readNext()) != null) {

						for(String userIdStr:currLine){
						
							if (!userIdStr.equals("")){
	
								long userId=0;
								System.out.println("    userId : " + userIdStr.trim() );
								try {
									
									userId = Long.parseLong(userIdStr.trim());
									
									User user = UserLocalServiceUtil.getUser(userId);
									
									if(user != null){
										System.out.println("      User Name : " + user.getFullName() );
										if(!GroupLocalServiceUtil.hasUserGroup(userId, course.getGroupCreatedId())){
											GroupLocalServiceUtil.addUserGroups(userId, new long[] { course.getGroupCreatedId() });
										}
	
										users.add(userId);
									}else{
										System.out.println("      User not exits (userId:"+userId+").");
									}
									
									
								} catch (NumberFormatException e) {
									errors.add(LanguageUtil.format(getPortletConfig(),themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.user-id-bad-format", new Object[] { userId }, false));
								} catch (PortalException e) {
									errors.add(LanguageUtil.format(getPortletConfig(),themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.user-id-not-found",	new Object[] { userId,userId }, false));
								} catch (Exception e){
									errors.add(LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(),"courseadmin.importuserrole.csvError"));
								}
							}
						}
					}

				} catch (FileNotFoundException e) {
					errors.add(LanguageUtil.get(getPortletConfig(), themeDisplay.getLocale(),"courseadmin.importuserrole.csvError.empty-file"));
				}catch(Exception e){
					e.printStackTrace();
				} finally {
					if (reader != null) {
						reader.close();
					}
				}

				if(errors.isEmpty()){
					for (Long user : users) {
						UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user }, course.getGroupCreatedId(), roleId);
					}
					SessionMessages.add(portletRequest, "courseadmin.importuserrole.csv.saved");
				}
				else {
					SessionErrors.add(portletRequest, "courseadmin.importuserrole.csvErrors",errors);
				}	
			}	
		}
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {

		String action = ParamUtil.getString(resourceRequest, "action");

		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);

		if(action.equals("export")){

			try {	

				long groupId = ParamUtil.getLong(resourceRequest, "groupId");
				String fileName = ParamUtil.getString(resourceRequest, "exportFileName");

				File file = LayoutServiceUtil.exportPortletInfoAsFile(themeDisplay.getLayout().getPlid(), groupId, themeDisplay.getPortletDisplay().getId(), resourceRequest.getParameterMap(), null, null);

				HttpServletRequest request = PortalUtil.getHttpServletRequest(resourceRequest);
				HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponse);

				InputStream is = new FileInputStream(file);

				String contentType = MimeTypesUtil.getContentType(file);

				ServletResponseUtil.sendFile(request, response, fileName, is, contentType);

			}catch(Exception e){

				//System.out.println(" Error: "+e.getMessage());
				e.printStackTrace();

			}

		} else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	public void importCourse(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		try {
			UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(uploadRequest, "groupId");
			File file = uploadRequest.getFile("importFileName");

			//System.out.println("groupId: "+groupId+", privateLayout:"+privateLayout+", file: "+file.getName());

			//System.out.println("actionRequest.getParameterMap().size: "+actionRequest.getParameterMap().size());

			if (!file.exists()) {
				//	System.out.println("Import file does not exist");
				throw new LARFileException("Import file does not exist");
			}
			//System.out.println("importLayouts 1, portletId"+themeDisplay.getPortletDisplay().getId());

			String portletId = (String) actionRequest.getAttribute(WebKeys.PORTLET_ID);
			LayoutServiceUtil.importPortletInfo(themeDisplay.getLayout().getPlid(), groupId,portletId, uploadRequest.getParameterMap(), file);

			//System.out.println("importLayouts 2");

			addSuccessMessage(actionRequest, actionResponse);

		}
		catch (Exception e) {
			//System.out.println("Error importando lar.");

			if ((e instanceof LARFileException) || (e instanceof LARTypeException)) {

				SessionErrors.add(actionRequest, e.getClass().getName());

			}
			else {
				_log.error(e, e);
				SessionErrors.add(actionRequest, LayoutImportException.class.getName());
			}
		}
	}


	@Override
	protected void doDispatch(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		if("importUserRole".equals(ParamUtil.getString(renderRequest, "ajaxAction"))){
			try {
				importUserRole(renderRequest,renderResponse);
			} catch (NestableException e) {
				throw new PortletException(e);
			}
		}
		super.doDispatch(renderRequest, renderResponse);
	}

	public void cloneCourse(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);	
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Course.class.getName(), actionRequest);
		
		long groupId  = ParamUtil.getLong(actionRequest, "groupId", 0);
	
		String newCourseName  = ParamUtil.getString(actionRequest, "newCourseName", "New course cloned");
		
		int startMonth = 	ParamUtil.getInteger(actionRequest, "startMon");
		int startYear = 	ParamUtil.getInteger(actionRequest, "startYear");
		int startDay = 		ParamUtil.getInteger(actionRequest, "startDay");
		int startHour = 	ParamUtil.getInteger(actionRequest, "startHour");
		int startMinute = 	ParamUtil.getInteger(actionRequest, "startMin");
		int startAMPM = 	ParamUtil.getInteger(actionRequest, "startAMPM");
		if (startAMPM > 0) {
			startHour += 12;
		}
		Date startDate = PortalUtil.getDate(startMonth, startDay, startYear, startHour, startMinute, themeDisplay.getTimeZone(), new EntryDisplayDateException());
		
		int stopMonth = 	ParamUtil.getInteger(actionRequest, "stopMon");
		int stopYear = 		ParamUtil.getInteger(actionRequest, "stopYear");
		int stopDay = 		ParamUtil.getInteger(actionRequest, "stopDay");
		int stopHour = 		ParamUtil.getInteger(actionRequest, "stopHour");
		int stopMinute = 	ParamUtil.getInteger(actionRequest, "stopMin");
		int stopAMPM = 		ParamUtil.getInteger(actionRequest, "stopAMPM");
		if (stopAMPM > 0) {
			stopHour += 12;
		}
		Date endDate = PortalUtil.getDate(stopMonth, stopDay, stopYear, stopHour, stopMinute, themeDisplay.getTimeZone(), new EntryDisplayDateException());
		
		CloneCourseThread cloneThread = new CloneCourseThread(groupId, newCourseName, themeDisplay, startDate, endDate, serviceContext);
		
		Thread thread = new Thread(cloneThread);
		
		thread.start();
		
		SessionMessages.add(actionRequest, "courseadmin.clone.confirmation.success");

	}
	
	private long createIGFolders(PortletRequest request,long userId,long repositoryId) throws PortalException, SystemException{
		//Variables for folder ids
		Long igMainFolderId = 0L;
		Long igPortletFolderId = 0L;
		Long igRecordFolderId = 0L;
	    //Search for folders
	    boolean igMainFolderFound = false;
	    boolean igPortletFolderFound = false;
	    try {
	    	//Get the main folder
	    	Folder igMainFolder = DLAppLocalServiceUtil.getFolder(repositoryId,DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,IMAGEGALLERY_MAINFOLDER);
	    	igMainFolderId = igMainFolder.getFolderId();
	    	igMainFolderFound = true;
	    	//Get the portlet folder
	    	DLFolder igPortletFolder = DLFolderLocalServiceUtil.getFolder(repositoryId,igMainFolderId,IMAGEGALLERY_PORTLETFOLDER);
	    	igPortletFolderId = igPortletFolder.getFolderId();
	    	igPortletFolderFound = true;
	    } catch (Exception ex) {
	    }
	    
		ServiceContext serviceContext= ServiceContextFactory.getInstance( DLFolder.class.getName(), request);
		//Damos permisos al archivo para usuarios de comunidad.
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
	    //Create main folder if not exist
	    if(!igMainFolderFound) {
	    	Folder newImageMainFolder=DLAppLocalServiceUtil.addFolder(userId, repositoryId, 0, IMAGEGALLERY_MAINFOLDER, IMAGEGALLERY_MAINFOLDER_DESCRIPTION, serviceContext);
	    	igMainFolderId = newImageMainFolder.getFolderId();
	    	igMainFolderFound = true;
	    }
	    //Create portlet folder if not exist
	    if(igMainFolderFound && !igPortletFolderFound){
	    	Folder newImagePortletFolder = DLAppLocalServiceUtil.addFolder(userId, repositoryId, igMainFolderId, IMAGEGALLERY_PORTLETFOLDER, IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION, serviceContext);	    	
	    	igPortletFolderFound = true;
	    	igPortletFolderId = newImagePortletFolder.getFolderId();
	    }
	    //Create this record folder
	    if(igPortletFolderFound){
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	    	Date date = new Date();
	    	String igRecordFolderName=dateFormat.format(date)+StringPool.UNDERLINE+userId;
	    	Folder newImageRecordFolder = DLAppLocalServiceUtil.addFolder(userId,repositoryId, igPortletFolderId,igRecordFolderName, igRecordFolderName, serviceContext);
	    	igRecordFolderId = newImageRecordFolder.getFolderId();
	    }
	    return igRecordFolderId;
	  }
	
}
