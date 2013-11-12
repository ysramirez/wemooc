package com.liferay.lms.lti.asset;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.BaseAssetRenderer;

public class LTIAssetRenderer extends BaseAssetRenderer {
	private static Log log = LogFactoryUtil.getLog(LTIAssetRenderer.class);

	private LearningActivity _learningactivity;
	
	public LTIAssetRenderer (LearningActivity learningactivity) {
		_learningactivity = learningactivity;
	}

	@Override
	public long getClassPK() {
		return _learningactivity.getActId();
	}

	@Override
	public long getGroupId() {
		return _learningactivity.getGroupId();
	}

	public String getSummary() {
		return _learningactivity.getDescription();
	}

	public String getTitle() {
		return _learningactivity.getTitle();
	}

	@Override
	public long getUserId() {
		return _learningactivity.getUserId();
	}

	@Override
	public String getUuid() {
		return _learningactivity.getUuid();
	}

	@Override
	public String render(RenderRequest request, RenderResponse response, String template) throws Exception {
		if(log.isDebugEnabled())log.debug("render");
		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			request.setAttribute("learningactivity", _learningactivity);
			return "/html/asset/" + template + ".jsp";
		}

		return "/html/asset/" + template + ".jsp";
	}

	@Override
	public PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception {
		if(log.isDebugEnabled())log.debug("getURLEdit");
		PortletURL portletURL = liferayPortletResponse.createActionURL( "lti_WAR_ltiportlet");
		portletURL.setParameter("javax.portlet.action", "edit");
		portletURL.setParameter("p_p_id", String.valueOf(_learningactivity.getActId()));
		
		portletURL.setParameter("mvcPath", "/html/lti/admin/edit.jsp");
		portletURL.setParameter("jspPage", "/html/lti/admin/edit.jsp"); 
	    portletURL.setParameter("actId",Long.toString( _learningactivity.getActId()));
	    
	    return portletURL;
	}
	
	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect) throws Exception {
		if(log.isDebugEnabled())log.debug("getURLViewInContext");

		PortletURL portletURL = liferayPortletResponse.createRenderURL("lti_WAR_ltiportlet");
		portletURL.setParameter("jspPage", "/html/lti/view.jsp");
		portletURL.setParameter("actId",Long.toString( _learningactivity.getActId()));
	
		return portletURL.toString();
	}

	@Override
	public String getViewInContextMessage() {
		if(log.isDebugEnabled())log.debug("getViewInContextMessage");
		return "View";
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) throws PortalException, SystemException {
		
		if(permissionChecker.hasPermission(this.getGroupId(), LearningActivity.class.getName(), this.getClassPK(), ActionKeys.UPDATE))
			return true;

		return false;
	}

	public String getSummary(Locale arg0){
		return getSummary();
	}

	public String getTitle(Locale arg0){
		return getTitle();
	}
	
	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}
}
