package com.liferay.lms.asset;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpServletRequest;

import com.liferay.lms.model.Course;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class CourseAssetRenderer extends BaseAssetRenderer {

	private Course _course;
	AssetEntry assetEntry;
	public CourseAssetRenderer (Course course) {
		_course = course;
		try {
			assetEntry=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId());
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		}
		public long getClassPK() {
		return _course.getCourseId();
		}
		public long getGroupId() {
		return _course.getGroupId();
		}
		public String getSummary() { 
		return assetEntry.getDescription();
		} 
		public String getTitle() { 
			
		return assetEntry.getTitle();
		} 
		public long getUserId() {
		return _course.getUserId();
		}
		public String getUuid() {
		return _course.getUuid();
		}
		public String render(RenderRequest request, RenderResponse response,
				String template)
				throws Exception {
			request.setAttribute("course", _course);
					if (template.equals(TEMPLATE_FULL_CONTENT)) {
						request.setAttribute("course", _course);
						
						return "/html/asset/course/" + template + ".jsp";
						}
						else {
							return "/html/asset/course/" + template + ".jsp";
						}
						
						
					}
			public PortletURL getURLEdit(
					LiferayPortletRequest liferayPortletRequest,
					LiferayPortletResponse liferayPortletResponse) throws Exception 
			{
				 HttpServletRequest request =
			            liferayPortletRequest.getHttpServletRequest();

			 ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			            WebKeys.THEME_DISPLAY);
		
			return null;
			}
			@Override
			public String getURLViewInContext(
					LiferayPortletRequest liferayPortletRequest,
					LiferayPortletResponse liferayPortletResponse,
					String noSuchEntryRedirect) throws Exception {
				// TODO Auto-generated method stub
				
				Group objectiveGroup=GroupLocalServiceUtil.getGroup(_course.getGroupCreatedId());
				return "/web"+objectiveGroup.getFriendlyURL();
			}
			@Override
			public String getViewInContextMessage() {
				
				return "Ver ficha del curso";
			}
			@Override
			public boolean hasEditPermission(PermissionChecker permissionChecker)
					throws PortalException, SystemException {
				return false;
			}
			
			public String getSummary(Locale locale) {
				
				return _course.getDescription(locale);
			}
			
			public String getTitle(Locale locale) {
				return _course.getTitle(locale);
			}
			@Override
			public String getUserName() {
				return null;
			}
   
	

}
