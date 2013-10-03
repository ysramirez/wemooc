package com.liferay.lms.asset;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.BaseAssetRenderer;

public class TaskEvaluationAssetRenderer extends BaseAssetRenderer {
	
	private LearningActivity _learningactivity;
	
	public TaskEvaluationAssetRenderer (LearningActivity learningactivity) {
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

		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			request.setAttribute("learningactivity", _learningactivity);
			return "/html/asset/" + template + ".jsp";
		}

		return "/html/asset/" + template + ".jsp";
	}

	@Override
	public PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception {
		PortletURL portletURL = liferayPortletResponse.createActionURL( "evaluationtaskactivity_WAR_liferaylmsportlet");
		portletURL.setParameter("javax.portlet.action", "edit");
			
		portletURL.setParameter("mvcPath", "/html/evaluationtaskactivity/admin/edit.jsp");
		portletURL.setParameter("jspPage", "/html/evaluationtaskactivity/admin/edit.jsp"); 
	    portletURL.setParameter("actId",Long.toString( _learningactivity.getActId()));
	    
	    return portletURL;
	}
	
	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect) throws Exception {

		PortletURL portletURL = liferayPortletResponse.createRenderURL("evaluationtaskactivity_WAR_liferaylmsportlet");
		portletURL.setParameter("jspPage", "/html/execactivity/test/view.jsp");
		portletURL.setParameter("actId",Long.toString( _learningactivity.getActId()));
	
		return portletURL.toString();
	}

	@Override
	public String getViewInContextMessage() {
		return "Exec test";
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
