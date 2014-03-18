package com.liferay.lms.asset;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.BaseAssetRenderer;

public class CourseAssetRenderer extends BaseAssetRenderer {
	
	protected static final String COURSE_ADMIN_PORTLET_ID =  PortalUtil.getJsSafePortletId("courseadmin"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	
	private Course _course;

	
	public CourseAssetRenderer (Course course) throws SystemException,PortalException {
		_course = course;		
	}
	
	@Override
	public long getClassPK() {
		return _course.getCourseId();
	}

	@Override
	public long getGroupId() {
		return _course.getGroupId();
	}

	@Override
	public String getSummary(Locale locale){
		return _course.getDescription(locale);
	}

	@Override
	public String getTitle(Locale locale){
		return _course.getTitle(locale);
	}

	@Override
	public long getUserId() {
		return _course.getUserId();
	}
	
	@Override
	public String getUserName() {
		try {
			return UserLocalServiceUtil.getUser(_course.getUserId()).
					getFullName();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getUuid() {
		return _course.getUuid();
	}

	@Override
	public final String render(RenderRequest request, RenderResponse response, String template) throws Exception {
		request.setAttribute("course", _course);
		return "/html/asset/course/" + template + ".jsp";

	}

	@Override
	public final PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);
  	  	PortletURL portletURL = PortletURLFactoryUtil.create(liferayPortletRequest,COURSE_ADMIN_PORTLET_ID,getControlPanelPlid(themeDisplay),PortletRequest.RENDER_PHASE);
  	  	portletURL.setParameter("mvcPath", "/html/courseadmin/editcourse.jsp");
  	  	portletURL.setParameter("courseId",Long.toString(_course.getCourseId()));
		return portletURL;
	}
	
	@Override
	public final PortletURL getURLView(LiferayPortletResponse liferayPortletResponse,
			WindowState windowState) throws Exception {
		Group courseGroup= GroupLocalServiceUtil.getGroup(_course.getGroupCreatedId());
		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(courseGroup.getDefaultPrivatePlid(), StringPool.BLANK, PortletRequest.RENDER_PHASE);
		return portletURL;
	}
	
	@Override
	public final String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Group courseGroup= GroupLocalServiceUtil.getGroup(_course.getGroupCreatedId());
		return PortalUtil.getGroupFriendlyURL(courseGroup, false, themeDisplay);
	}

	@Override
	public String getViewInContextMessage() {
		return "show-detail-course";
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) throws PortalException, SystemException {	
		return ((!_course.isClosed())&&
				(permissionChecker.hasPermission(_course.getGroupId(),  Course.class.getName(),_course.getCourseId(),ActionKeys.UPDATE)));

	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
			throws PortalException, SystemException {
		return ((!_course.isClosed())&&
				(UserLocalServiceUtil.hasGroupUser(_course.getGroupCreatedId(), permissionChecker.getUserId())));
	}

}
