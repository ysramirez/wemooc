
package com.liferay.lms.asset;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.BaseAssetRenderer;

public class TaskP2PAssetRenderer extends BaseAssetRenderer {

	private LearningActivity _learningactivity;
	private static Log _log = LogFactoryUtil.getLog(TaskP2PAssetRenderer.class);
	

	public TaskP2PAssetRenderer(LearningActivity learningactivity) {

		_learningactivity = learningactivity;
	}

	public long getClassPK() {

		return _learningactivity.getActId();
	}

	public long getGroupId() {

		return _learningactivity.getGroupId();
	}

	public String getSummary() {

		return _learningactivity.getDescription();
	}

	public String getTitle() {

		return _learningactivity.getTitle();
	}

	public long getUserId() {

		return _learningactivity.getUserId();
	}

	public String getUuid() {

		return _learningactivity.getUuid();
	}

	public String render(RenderRequest request, RenderResponse response, String template)
		throws Exception {

		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			request.setAttribute("learningactivity", _learningactivity);

			return "/html/p2p/" + template + ".jsp";
		}
		else {
			return "/html/p2p/" + template + ".jsp";
		}

	}

	public PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest, LiferayPortletResponse liferayPortletResponse)
		throws Exception {
		
		_log.debug("getURLEdit: entrada");
		
		HttpServletRequest request = liferayPortletRequest.getHttpServletRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		PortletURL portletURL = liferayPortletResponse.createRenderURL("p2ptaskactivity_WAR_liferaylmsportlet");

		portletURL.setParameter("mvcPath", "/html/p2ptaskactivity/admin/edit.jsp");
		portletURL.setParameter("jspPage", "/html/p2ptaskactivity/admin/edit.jsp");

		portletURL.setParameter("actId", Long.toString(_learningactivity.getActId()));
		
		_log.debug("getURLEdit: "+portletURL.toString());
		return portletURL;
	}

	@Override
	public String getURLViewInContext(LiferayPortletRequest liferayPortletRequest, LiferayPortletResponse liferayPortletResponse, String noSuchEntryRedirect)
		throws Exception {

		PortletURL viewPortletURL = liferayPortletResponse.createRenderURL("p2ptaskactivity_WAR_liferaylmsportlet");
		viewPortletURL.setWindowState(LiferayWindowState.NORMAL);
		viewPortletURL.setParameter("jspPage", "/html/p2ptaskactivity/view.jsp");
		viewPortletURL.setParameter("actId",Long.toString( _learningactivity.getActId()));


	return viewPortletURL.toString();
	}

	@Override
	public String getViewInContextMessage() {

		// TODO Auto-generated method stub

		return "Exec P2P Task";
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		// TODO Auto-generated method stub
		if (permissionChecker.hasPermission(this.getGroupId(), LearningActivity.class.getName(), this.getClassPK(), ActionKeys.UPDATE)) {
			return true;
		}
		else {
			return false;
		}
	}

	// @Override
	public String getSummary(Locale arg0) {

		return getSummary();
	}

	// @Override
	public String getTitle(Locale arg0) {

		// TODO Auto-generated method stub
		return getTitle();
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}

}
