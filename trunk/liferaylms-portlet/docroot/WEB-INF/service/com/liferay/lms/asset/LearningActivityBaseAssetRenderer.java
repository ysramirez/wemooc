package com.liferay.lms.asset;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import com.liferay.lms.ActivityViewer;
import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.BaseAssetRenderer;

public abstract class LearningActivityBaseAssetRenderer extends BaseAssetRenderer {
	
	public static final String TEMPLATE_JSP = "template_JSP";
	public static final String TEMPLATE_PORTLET_ID = "template_portlet_id";
	protected static final String LMS_ACTIVITIES_LIST_PORTLET_ID =  PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	protected static final String ACTIVITY_VIEWER_PORTLET_ID =  PortalUtil.getJsSafePortletId("activityViewer"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName());
	
	private LearningActivity _learningactivity;
	private String _nameKey;
	private String _portletId;
	private Layout _layout;
	private boolean _isLmsExternalTemplates = false;
	private boolean _isRuntimePortlet = false;

	public LearningActivityBaseAssetRenderer (LearningActivity learningactivity) throws SystemException,PortalException {
		this(learningactivity, new LearningActivityTypeRegistry().getLearningActivityType(learningactivity.getTypeId()));
	}
	
	public LearningActivityBaseAssetRenderer (LearningActivity learningactivity, LearningActivityType learningActivityType) throws SystemException,PortalException {
		this(learningactivity, new LearningActivityTypeRegistry().getLearningActivityType(learningactivity.getTypeId()),false);
	}
	
	public LearningActivityBaseAssetRenderer (LearningActivity learningactivity, LearningActivityType learningActivityType, boolean isLmsExternalTemplates) throws SystemException,PortalException {
		_learningactivity = learningactivity;
		_nameKey = learningActivityType.getName();
		_portletId = learningActivityType.getPortletId();
		_isLmsExternalTemplates = isLmsExternalTemplates;
		
		@SuppressWarnings("unchecked")
		List<Layout> layouts = LayoutLocalServiceUtil.dynamicQuery(LayoutLocalServiceUtil.dynamicQuery().
				add(PropertyFactoryUtil.forName("privateLayout").eq(false)).
				add(PropertyFactoryUtil.forName("type").eq(LayoutConstants.TYPE_PORTLET)).
				add(PropertyFactoryUtil.forName("companyId").eq(_learningactivity.getCompanyId())).
				add(PropertyFactoryUtil.forName("groupId").eq(_learningactivity.getGroupId())).
				add(PropertyFactoryUtil.forName("friendlyURL").eq("/reto")), 0, 1);

		if(!layouts.isEmpty()) {
			_layout = layouts.get(0);
			if(!((LayoutTypePortlet)_layout.getLayoutType()).getPortletIds().contains(_portletId)){
				_isRuntimePortlet = true;
			}
		}
		else {
			throw new NoSuchLayoutException();
		}
		
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
	public String getUserName() {
		return _learningactivity.getUserName();
	}

	@Override
	public String getUuid() {
		return _learningactivity.getUuid();
	}

	protected String getTemplatePath(String template) {
		return "/html/asset/";
	}

	@Override
	public final String render(RenderRequest request, RenderResponse response, String template) throws Exception {

		if(TEMPLATE_FULL_CONTENT.equals(template)) {
			request.setAttribute("learningactivity", _learningactivity);
		}

	    String templateJSP =  getTemplatePath(template) + template + ".jsp";
	    if(_isLmsExternalTemplates) {
	    	request.setAttribute(TEMPLATE_PORTLET_ID, _portletId);
	    	request.setAttribute(TEMPLATE_JSP, templateJSP);
	    	return "/html/asset/externalTemplate.jsp";
	    }

		return templateJSP;
	}

	@Override
	public PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception {
		PortletURL portletURL = PortletURLFactoryUtil.create(liferayPortletRequest,LMS_ACTIVITIES_LIST_PORTLET_ID,_layout.getPlid(),PortletRequest.ACTION_PHASE);
		portletURL.setParameter("javax.portlet.action", "editactivityoptions");
		portletURL.setParameter("resId",Long.toString( _learningactivity.getActId()));
		portletURL.setParameter("resModuleId",Long.toString( _learningactivity.getModuleId())); 
	    return portletURL;
	}
	
	private void prepareRuntimePortlet(PortletURL portletURL)
			throws SystemException, PortalException {
		if(_isRuntimePortlet){

			portletURL.setParameter("p_o_p_id",ACTIVITY_VIEWER_PORTLET_ID);

			PortletPreferencesFactoryUtil.getLayoutPortletSetup(_layout, _portletId);
			String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(_layout.getPlid(), _portletId);
			String portletName = _portletId;

			int warSeparatorIndex = portletName.indexOf(PortletConstants.WAR_SEPARATOR);
			if (warSeparatorIndex != -1) {
				portletName = portletName.substring(0, warSeparatorIndex);
			}

			if ((ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
					_learningactivity.getCompanyId(), portletName,
					ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey) == 0)&&
				(ResourceActionLocalServiceUtil.fetchResourceAction(portletName, ActivityViewer.ACTION_VIEW)!=null)) {
	        	Role siteMember = RoleLocalServiceUtil.getRole(_learningactivity.getCompanyId(),RoleConstants.SITE_MEMBER);
        		ResourcePermissionServiceUtil.setIndividualResourcePermissions(_learningactivity.getGroupId(), _learningactivity.getCompanyId(), 
        				portletName, resourcePrimKey, siteMember.getRoleId(), new String[]{ActivityViewer.ACTION_VIEW});
			}

		}
	}
	
	@Override
	public PortletURL getURLView(LiferayPortletResponse liferayPortletResponse,
			WindowState windowState) throws Exception {
		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(_layout.getPlid(), _portletId, PortletRequest.RENDER_PHASE);
		portletURL.setWindowState(windowState);
		portletURL.setParameter("actId",Long.toString( _learningactivity.getActId()));
		portletURL.setParameter("moduleId",Long.toString( _learningactivity.getModuleId()));
		portletURL.setParameter("actionEditing",StringPool.FALSE);
		prepareRuntimePortlet(portletURL);
		return portletURL;
	}
	
	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect) throws Exception {
		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(_layout.getPlid(), _portletId, PortletRequest.RENDER_PHASE);
		portletURL.setParameter("actId",Long.toString( _learningactivity.getActId()));
		portletURL.setParameter("moduleId",Long.toString( _learningactivity.getModuleId()));
		portletURL.setParameter("actionEditing",StringPool.FALSE);
		prepareRuntimePortlet(portletURL);
		return portletURL.toString();
	}

	@Override
	public String getViewInContextMessage() {
		return _nameKey;
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) throws PortalException, SystemException {	
		return permissionChecker.
				hasPermission(this.getGroupId(), LearningActivity.class.getName(), this.getClassPK(), ActionKeys.UPDATE);
	}
	
	
	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
			throws PortalException, SystemException {
		return permissionChecker.
				hasPermission(this.getGroupId(), LearningActivity.class.getName(), this.getClassPK(), ActionKeys.VIEW);
	}

	public String getSummary(Locale arg0){
		return getSummary();
	}

	public String getTitle(Locale arg0){
		return getTitle();
	}

}
