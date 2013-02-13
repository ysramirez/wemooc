package com.liferay.lms.hook.events;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.liferay.lms.NoSuchPrefsException;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.messageboards.model.MBCategory;

public class StartupAction extends SimpleAction {

	public Role courseTeacher;
	public Role courseEditor;
	public Role courseCreator;
	public LayoutSetPrototype layoutSetPrototype;
	@Override
	public void run(String[] ids) throws ActionException {
		try {
			System.out.println("initialice Lmsliferay");
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	public void doRun(long companyId) throws Exception 
	{
		LmsPrefs lmsPrefs=null;
		
		try
		{
		lmsPrefs=LmsPrefsLocalServiceUtil.getLmsPrefs(companyId);
		}
		catch(NoSuchPrefsException e)
		{
			lmsPrefs=null;
		}
		if(lmsPrefs==null)
		{
			createDefaultRoles(companyId);
			createDefaultSiteTemplate(companyId);
			createDefaultPreferences(companyId);
		}
		
	}

	public LmsPrefs createDefaultPreferences(long companyId) 
	{
		LmsPrefs lmsPrefs=LmsPrefsLocalServiceUtil.createLmsPrefs(companyId);
		lmsPrefs.setTeacherRole(courseTeacher.getRoleId());
		lmsPrefs.setEditorRole(courseEditor.getRoleId());
		lmsPrefs.setLmsTemplates(Long.toString(layoutSetPrototype.getLayoutSetPrototypeId()));
		return lmsPrefs;
		
	}

	public void createDefaultSiteTemplate(long companyId) throws PortalException, SystemException 
	{
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);
		Map<Locale, String> nameMap = new HashMap<Locale, String>();
		
		nameMap.put(
			LocaleUtil.getDefault(),
			"course");
		layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
				defaultUserId, companyId, nameMap, "course", true,false,null);
		LayoutSet layoutSet = layoutSetPrototype.getLayoutSet();
		ServiceContext serviceContext=new ServiceContext();
		Group group = layoutSet.getGroup();
		InputStream larStream=this.getClass().getClassLoader().getResourceAsStream("/course.lar");
		
		LayoutLocalServiceUtil.importLayouts(defaultUserId,layoutSetPrototype.getGroup().getGroupId() , 
				layoutSetPrototype.getLayoutSet().isPrivateLayout(),
				getLayoutSetPrototypeParameters(), larStream);
		
	}
	private static Map<String, String[]> getLayoutSetPrototypeParameters() 
	{
		Map<String, String[]> parameterMap = new LinkedHashMap<String, String[]>();
        
		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
			//new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			//new String[] {Boolean.FALSE.toString()});
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			//new String[] {Boolean.FALSE.toString()});
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});
		return parameterMap;
	}	
	public void createDefaultRoles(long companyId) throws PortalException, SystemException {
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);
		int scope = ResourceConstants.SCOPE_GROUP_TEMPLATE;
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
		
		descriptionMap.put(
			LocaleUtil.getDefault(),
			"Course creator, can create courses in a community.");
Map<Locale, String> titleMap = new HashMap<Locale, String>();
		
titleMap.put(
			LocaleUtil.getDefault(),
			"Course creator.");
		courseCreator= RoleLocalServiceUtil.addRole(defaultUserId, companyId, "courseCreator"
				,titleMap, descriptionMap, RoleConstants.TYPE_SITE);
		long courseCreatorId=courseCreator.getRoleId();
		java.util.List<ResourceAction>actions= ResourceActionLocalServiceUtil.getResourceActions(Course.class.getName());
		for(ResourceAction raction:actions)
		{
			ResourcePermissionLocalServiceUtil.addResourcePermission(companyId,Course.class.getName(),scope, Long.toString(companyId),courseCreatorId,raction.getActionId());
			
		}
descriptionMap = new HashMap<Locale, String>();
		
titleMap = new HashMap<Locale, String>();

titleMap.put(
			LocaleUtil.getDefault(),
			"Course Editor.");
		descriptionMap.put(
			LocaleUtil.getDefault(),
			"Editors can Edit a course.");
		courseEditor= RoleLocalServiceUtil.addRole(0, companyId, "courseEditor",titleMap, descriptionMap, RoleConstants.TYPE_SITE);
		actions= ResourceActionLocalServiceUtil.getResourceActions(Module.class.getName());
		for(ResourceAction raction:actions)
		{
			ResourcePermissionLocalServiceUtil.addResourcePermission(companyId,Module.class.getName(),scope, Long.toString(companyId),courseEditor.getRoleId(),raction.getActionId());
			
		}
		actions= ResourceActionLocalServiceUtil.getResourceActions(LearningActivity.class.getName());
		for(ResourceAction raction:actions)
		{
			ResourcePermissionLocalServiceUtil.addResourcePermission(companyId,LearningActivity.class.getName(),scope, Long.toString(companyId),courseEditor.getRoleId(),raction.getActionId());
			
		}
		titleMap = new HashMap<Locale, String>();

		titleMap.put(
					LocaleUtil.getDefault(),
					"Course Teacher.");
		descriptionMap = new HashMap<Locale, String>();
		descriptionMap.put(
				LocaleUtil.getDefault(),
				"Teachers.");
		courseTeacher= RoleLocalServiceUtil.addRole(0, companyId, "courseTeacher", titleMap, descriptionMap, RoleConstants.TYPE_SITE);
		actions= ResourceActionLocalServiceUtil.getResourceActions(BlogsEntry.class.getName());
		for(ResourceAction raction:actions)
		{
			ResourcePermissionLocalServiceUtil.addResourcePermission(companyId,BlogsEntry.class.getName(),scope, Long.toString(companyId),courseTeacher.getRoleId(),raction.getActionId());
			
		}
		actions= ResourceActionLocalServiceUtil.getResourceActions(MBCategory.class.getName());
		for(ResourceAction raction:actions)
		{
			ResourcePermissionLocalServiceUtil.addResourcePermission(companyId,MBCategory.class.getName(),scope, Long.toString(companyId),courseTeacher.getRoleId(),raction.getActionId());
			
		}
		ResourcePermissionLocalServiceUtil.addResourcePermission(companyId,LearningActivity.class.getName(),scope, Long.toString(companyId),courseTeacher.getRoleId(),"CORRECT");

	}

}
