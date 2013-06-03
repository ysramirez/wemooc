package com.liferay.lms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
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
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CourseAdmin
 */
public class CourseAdmin extends MVCPortlet {
	
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

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Course.class.getName(), actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		String redirect = ParamUtil.getString(actionRequest, "redirect");

		User user = themeDisplay.getUser();
		Enumeration<String> parNam = actionRequest.getParameterNames();
		String title = "";
		while (parNam.hasMoreElements()) {
			String paramName = parNam.nextElement();
			if (paramName.startsWith("title_") && paramName.length() > 6) {
				if (title.equals("")) {
					title = actionRequest.getParameter(paramName);
				}

			}
		}

		String description = actionRequest.getParameter("description");
		long courseId = ParamUtil.getLong(actionRequest, "courseId", 0);
		long courseTemplateId=ParamUtil.getLong(actionRequest,"courseTemplate",0);
		String friendlyURL = ParamUtil.getString(actionRequest, "friendlyURL",
				"");
		int startMonth = ParamUtil.getInteger(actionRequest, "startMon");
		int startYear = ParamUtil.getInteger(actionRequest, "startYear");
		int startDay = ParamUtil.getInteger(actionRequest, "startDay");
		int startHour = ParamUtil.getInteger(actionRequest, "startHour");
		int startMinute = ParamUtil.getInteger(actionRequest, "startMin");
		int startAMPM = ParamUtil.getInteger(actionRequest, "startAMPM");
		String summary = ParamUtil.getString(actionRequest, "summary", "");
		boolean visible = ParamUtil.getBoolean(actionRequest, "visible", false);
		long courseEvalId=ParamUtil.getLong(actionRequest, "courseEvalId", 0);

		if (friendlyURL.equals("") && !title.equals("")) {
			friendlyURL = StringPool.BLANK;
		}

		if (startAMPM > 0) {
			startHour += 12;
		}
		Date startDate = PortalUtil.getDate(startMonth, startDay, startYear,
				startHour, startMinute, user.getTimeZone(),
				new EntryDisplayDateException());

		int stopMonth = ParamUtil.getInteger(actionRequest, "stopMon");
		int stopYear = ParamUtil.getInteger(actionRequest, "stopYear");
		int stopDay = ParamUtil.getInteger(actionRequest, "stopDay");
		int stopHour = ParamUtil.getInteger(actionRequest, "stopHour");
		int stopMinute = ParamUtil.getInteger(actionRequest, "stopMin");
		int stopAMPM = ParamUtil.getInteger(actionRequest, "stopAMPM");
		if (stopAMPM > 0) {
			stopHour += 12;
		}
		Date stopDate = PortalUtil.getDate(stopMonth, stopDay, stopYear,
				stopHour, stopMinute, user.getTimeZone(),
				new EntryDisplayDateException());

		java.util.Date ahora = new java.util.Date(System.currentTimeMillis());
		// Validations
		boolean noTitle = true;
		Enumeration<String> parNames = actionRequest.getParameterNames();
		while (parNames.hasMoreElements()) {
			String paramName = parNames.nextElement();
			if (paramName.startsWith("title_")
					&& paramName.length() > 6
					&& ParamUtil.getString(actionRequest, paramName, "")
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

		Course course = null;
		if (courseId == 0) {
			course = com.liferay.lms.service.CourseLocalServiceUtil.addCourse(
					title, description, summary, friendlyURL,
					themeDisplay.getLocale(), ahora, startDate, stopDate,courseTemplateId,
					serviceContext);
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
		// Estamos editando un curso existente.
		else {

			course = CourseLocalServiceUtil.getCourse(courseId);
			course.setDescription( description,themeDisplay.getLocale());
			course.setStartDate(startDate);
			course.setEndDate(stopDate);
			com.liferay.lms.service.CourseLocalServiceUtil.modCourse(course,
					summary, serviceContext);
		}
		
		//Cambiar la imagen de la comunidad
		UploadPortletRequest request = PortalUtil.getUploadPortletRequest(actionRequest);
		String fileName = request.getFileName("fileName");
		if(fileName!=null && !fileName.equals(""))
		{
			File file = request.getFile("fileName");
			System.out.println(" file: "+file);
			LayoutSetLocalServiceUtil.updateLogo(course.getGroupId(), true, true, file);
		}

		boolean oneTitleNotEmpty = false;
		parNames = actionRequest.getParameterNames();
		while (parNames.hasMoreElements()) {
			String paramName = parNames.nextElement();
			if (paramName.startsWith("title_") && paramName.length() > 6) {
				String language = paramName.substring(6);
				Locale locale = LocaleUtil.fromLanguageId(language);
				course.setTitle(actionRequest.getParameter(paramName),locale);

				if (!actionRequest.getParameter(paramName).equals("")) {
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
		com.liferay.lms.service.CourseLocalServiceUtil.modCourse(course,
				summary, serviceContext);
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
	
	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {
		
		String action = ParamUtil.getString(resourceRequest, "action");
	
		if(action.equals("export")){
			
			try {	
				
				long groupId = ParamUtil.getLong(resourceRequest, "groupId");
				boolean privateLayout = ParamUtil.getBoolean(resourceRequest, "privateLayout");
				String fileName = ParamUtil.getString(resourceRequest, "exportFileName");
				
				Date now = new Date();
				Date startDate = new Date(now.getTime() - Time.DAY); //new Date(now.getTime() - (rangeLast * Time.HOUR));
				Date endDate = now;
							
				File file = LayoutServiceUtil.exportLayoutsAsFile(groupId, privateLayout, null, resourceRequest.getParameterMap(), null, null);
			
				HttpServletRequest request = PortalUtil.getHttpServletRequest(resourceRequest);
				HttpServletResponse response = PortalUtil.getHttpServletResponse(resourceResponse);
	
				InputStream is = new FileInputStream(file);

				String contentType = MimeTypesUtil.getContentType(file);

				ServletResponseUtil.sendFile(request, response, fileName, is, contentType);
				
			}catch(Exception e){
				
				System.out.println(" Error: "+e.getMessage());
				e.printStackTrace();
				
			}
			
		} else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}
	
	public void importCourse(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		try {
			UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

			long groupId = ParamUtil.getLong(uploadRequest, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(uploadRequest, "privateLayout");
			File file = uploadRequest.getFile("importFileName");
			
			System.out.println("groupId: "+groupId+", privateLayout:"+privateLayout+", file: "+file.getName());
			
			System.out.println("actionRequest.getParameterMap().size: "+actionRequest.getParameterMap().size());

			if (!file.exists()) {
				System.out.println("Import file does not exist");
				throw new LARFileException("Import file does not exist");
			}
			System.out.println("importLayouts 1");
			LayoutServiceUtil.importLayouts(groupId, privateLayout, uploadRequest.getParameterMap(), file);
			System.out.println("importLayouts 2");

			addSuccessMessage(actionRequest, actionResponse);
			
		}
		catch (Exception e) {
			System.out.println("Error importando lar.");
			
			if ((e instanceof LARFileException) || (e instanceof LARTypeException)) {

				SessionErrors.add(actionRequest, e.getClass().getName());
				
			}
			else {
				_log.error(e, e);
				SessionErrors.add(actionRequest, LayoutImportException.class.getName());
			}
		}
	}
}
