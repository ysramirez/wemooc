/**
 * 2012 TELEFONICA LEARNING SERVICES. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lms.service.impl;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.base.CourseLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivitySettingLocalServiceUtil;



/**
 * The implementation of the course local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseLocalServiceUtil} to access the course local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CourseLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseLocalServiceUtil
 */
public class CourseLocalServiceImpl extends CourseLocalServiceBaseImpl {
	public java.util.List<Course> getCoursesOfGroup(long groupId) throws SystemException
	{
		return coursePersistence.findByGroupId(groupId);
	}
	public java.util.List<Course> getCourses(long companyId) throws SystemException
	{
		return coursePersistence.findByCompanyId(companyId);
	}
	public long countByGroupId(long groupId) throws SystemException
	{
		return coursePersistence.countByGroupId(groupId);
	}
	public Course fetchByGroupCreatedId(long groupId) throws SystemException
	{
		return coursePersistence.fetchByGroupCreatedId(groupId);
	}
	public Course addCourse (String title, String description,String summary,String friendlyURL, Locale locale,
			java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,long layoutSetPrototypeId,
		ServiceContext serviceContext)
			throws SystemException, 
			PortalException {
		LmsPrefs lmsPrefs=lmsPrefsLocalService.getLmsPrefsIni(serviceContext.getCompanyId());
		long userId=serviceContext.getUserId();
		Course course =
				coursePersistence.create(counterLocalService.increment(
						Course.class.getName()));
			course.setCompanyId(serviceContext.getCompanyId());
			course.setGroupId(serviceContext.getScopeGroupId());
			course.setUserId(userId);
			course.setFriendlyURL(friendlyURL);
			course.setDescription(description,locale);
			course.setTitle(title,locale);
			course.setStartDate(startDate);
			course.setEndDate(endDate);
			course.setStatus(WorkflowConstants.STATUS_APPROVED);
			course.setExpandoBridgeAttributes(serviceContext);
			coursePersistence.update(course, true);
			try
			{
			resourceLocalService.addResources(
					serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), userId,
			Course.class.getName(), course.getPrimaryKey(), false,
			true, true);
			assetEntryLocalService.updateEntry(
					userId, course.getGroupId(), Course.class.getName(),
					course.getCourseId(), course.getUuid(),0, serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames(), true, null, null,
					new java.util.Date(System.currentTimeMillis()), null,
					ContentTypes.TEXT_HTML, course.getTitle(),null,  course.getDescription(locale),null, null, 0, 0,
					null, false);
					
			}
			catch(Exception e)
			{
			}
			//creating group
			Group group = GroupLocalServiceUtil.addGroup(userId,null, 0,
					title,summary,GroupConstants.TYPE_SITE_PRIVATE,friendlyURL,true,true,serviceContext);
			
			//Añadimos el rol Teacher al usuario que crea el blog
			//System.out.println("Add TO ROL::UserId "+userId);
			long[] usuarios = new long[1];
			usuarios[0] = userId;
			try
			{
			UserLocalServiceUtil.addRoleUsers(lmsPrefs.getTeacherRole(), usuarios);
			}
			catch (Exception e)
			{
				
			}
			try
			{
			UserLocalServiceUtil.addRoleUsers(lmsPrefs.getEditorRole(), usuarios);
			}
			catch (Exception e)
			{
			}
			course.setGroupCreatedId(group.getGroupId());
			GroupLocalServiceUtil.addUserGroups(userId, new long[] { group.getGroupId() });
			course.setFriendlyURL(group.getFriendlyURL());
			coursePersistence.update(course, true);
			LayoutSetPrototype lsProto=LayoutSetPrototypeServiceUtil.getLayoutSetPrototype(layoutSetPrototypeId);
			importLayouts(getAdministratorUser(serviceContext.getCompanyId()).getUserId(), group, lsProto);
			/* activamos social activity para la comunidad creada */ 		
			SocialActivitySettingLocalServiceUtil.updateActivitySetting(group.getGroupId(), Group.class.getName(), true);			
			return course;
		
	}
	public Course addCourse (String title, String description,String summary,String friendlyURL, Locale locale,
			java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,
		ServiceContext serviceContext)
			throws SystemException, 
			PortalException {
		LmsPrefs lmsPrefs=lmsPrefsLocalService.getLmsPrefsIni(serviceContext.getCompanyId());
		long layoutSetPrototypeId=Long.valueOf(lmsPrefs.getLmsTemplates());
		return addCourse (title, description,summary,friendlyURL, locale,
				createDate,startDate,endDate,layoutSetPrototypeId,
				 serviceContext);
	}
	public Course addCourse (String title, String description,String friendlyURL, Locale locale,
			java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,
		ServiceContext serviceContext)
			throws SystemException, 
			PortalException {
		
				return this.addCourse(title, description, description, friendlyURL, locale, createDate, startDate, endDate, serviceContext);
			}
	
	private static User getAdministratorUser(long companyId) throws PortalException, SystemException
	{
		// El nombre del rol "Administrator" no puede cambiar a través del UI, es un caso excepcional de sólo lectura
		// Sin embargo pueden haber varios administradores (con el rol "Administrator"),
		// hacemos lo siguiente: devolvemos el que tenga userName "test" y así tenderemos
		// a devolver siempre el mismo, si no hay un administrador "test" (pues puede cambiarse) devolvemos el primero.
		long adminRoleId = RoleLocalServiceUtil.getRole(companyId, "Administrator").getRoleId();
		List<User> adminList = UserLocalServiceUtil.getRoleUsers(adminRoleId);
		for(User user : adminList)
		{
			if (user.getScreenName().equals("test"))
				return user;
		}
		return adminList.get(0); // Devolvemos el primero.
	}	
	private static LayoutSetPrototype fetchLayoutSetPrototypeByDescription(String description,long companyId) throws SystemException,PortalException
	{
		LayoutSetPrototype lspRes = null;
		for(LayoutSetPrototype lsp:LayoutSetPrototypeLocalServiceUtil.search(companyId, true,0,Integer.MAX_VALUE, null))	
		{
			if(description.equals(lsp.getDescription()))
			{
				lspRes = lsp;
				break;
			}
		}
		return lspRes;
	}	
	
	private static void importLayouts(long userId,Group grupo,LayoutSetPrototype lsProto) throws PortalException, SystemException
	{
			LayoutSet ls = lsProto.getLayoutSet();	
			
		File fileIni= LayoutLocalServiceUtil.exportLayoutsAsFile(ls.getGroupId(), true,
				null,getLayoutSetPrototypeParameters(), null, null);
	
		try
		{
		LayoutLocalServiceUtil.importLayouts(userId, grupo.getPublicLayoutSet().getGroupId(), 
			grupo.getPublicLayoutSet().isPrivateLayout(),
			getLayoutSetPrototypeParameters(), fileIni);
		}
		catch(Exception e)
		{
		}
		FileUtil.delete(fileIni);
		
	}
	private static Map<String, String[]> getLayoutSetPrototypeParameters() 
	{
		Map<String, String[]> parameterMap = new LinkedHashMap<String, String[]>();
        
		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
			//new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
				PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
				new String[] {PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE});
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
	public void setVisible(long courseId,boolean visible) throws PortalException, SystemException
	{
	    assetEntryLocalService.updateVisible(Course.class.getName(), courseId, visible);
	}
	public Course modCourse (Course course,String summary,
			ServiceContext serviceContext)
			throws SystemException, PortalException {
			course.setExpandoBridgeAttributes(serviceContext);
			Locale locale=new Locale(serviceContext.getLanguageId());
			coursePersistence.update(course, true);
			long userId=serviceContext.getUserId();
			Group theGroup=GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
			theGroup.setName(course.getTitle(locale, true));
			theGroup.setDescription(summary);
			GroupLocalServiceUtil.updateGroup(theGroup);
			AssetEntry asset=null;
			assetEntryLocalService.updateEntry(
					userId, course.getGroupId(), Course.class.getName(),
					course.getCourseId(), course.getUuid(),0, serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames(), true, null, null,
					new java.util.Date(System.currentTimeMillis()), null,
					ContentTypes.TEXT_HTML, course.getTitle(),null,  course.getDescription(locale),null, null, 0, 0,
					null, false);
			return course;
		
			}
	public Course modCourse (Course course, 
			ServiceContext serviceContext)
			throws SystemException, PortalException {
			course.setExpandoBridgeAttributes(serviceContext);
		
			return this.modCourse(course, "", serviceContext);
		
			}
	public Course deleteCourse(Course course) throws SystemException,
	PortalException {
	long companyId = course.getCompanyId();
	
	//Cambia el titulo y la friendlyurl y desactiva el grupo
	Group theGroup=GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
	theGroup.setName("desactivado("+course.getGroupCreatedId()+")");
	theGroup.setFriendlyURL(course.getFriendlyURL()+"_desac");
	theGroup.setActive(false);
	GroupLocalServiceUtil.updateGroup(theGroup);
	
	assetEntryLocalService.deleteEntry(Course.class.getName(),course.getCourseId());
	resourceLocalService.deleteResource(
	companyId, Course.class.getName(),
	ResourceConstants.SCOPE_INDIVIDUAL, course.getPrimaryKey());
	assetEntryLocalService.deleteEntry(
			LearningActivity.class.getName(), course.getCourseId());
	coursePersistence.remove(course);
	return null;
	}
	
	public Course deleteCourse (long courseId) throws SystemException,
	PortalException {
	this.deleteCourse(CourseLocalServiceUtil.getCourse(courseId));
	return null;
	}
	
	//Siguiendo el indice IX_5DE0BE11 de la tabla group_
	public boolean existsCourseName(long companyId, long classNameId, long liveGroupId, String name){
		boolean res = false;
		
		try {
			Group group = GroupLocalServiceUtil.getGroup(companyId, name);
			if(group != null && group.getClassNameId() == classNameId && group.getLiveGroupId() == liveGroupId ){
				res = true;
			}
			
		} catch (Exception e) {
			res = false;
		}

		return res;
	}
	
	@SuppressWarnings("unchecked")
	public Course getCourseByGroupCreatedId(long groupCreatedId) throws SystemException{
		
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(Course.class)
				.add(PropertyFactoryUtil.forName("groupCreatedId").eq(groupCreatedId));
	
		List<Course> list = (List<Course>)coursePersistence.findWithDynamicQuery(consulta);
		
		if(!list.isEmpty() && list.size()>0){
			return list.get(0);
		}

		return null;
	}
}