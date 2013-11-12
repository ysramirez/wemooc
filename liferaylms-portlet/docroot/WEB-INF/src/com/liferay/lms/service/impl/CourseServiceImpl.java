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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.lms.service.base.CourseServiceBaseImpl;
import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.social.model.SocialRelationConstants;
import com.liferay.portlet.social.service.SocialRelationLocalServiceUtil;

/**
 * The implementation of the course remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseServiceUtil} to access the course remote service.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CourseServiceBaseImpl
 * @see com.liferay.lms.service.CourseServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class CourseServiceImpl extends CourseServiceBaseImpl {
	public java.util.List<Course> getCoursesOfGroup(long groupId) throws SystemException
	{
		return coursePersistence.filterFindByGroupId(groupId);
		
	
	}
	
	@JSONWebService
	public Course createCourse(String name)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		Course course = null;
		
		try {
			if (CourseLocalServiceUtil.existsCourseName(serviceContext.getCompanyId(), null, name)) {
				throw new DuplicateGroupException(LanguageUtil.get(LocaleUtil.getDefault(), "title-repeated"));
			}
			
			String[] lspist=LmsPrefsLocalServiceUtil.getLmsPrefsIni(serviceContext.getCompanyId()).getLmsTemplates().split(",");
			Date now = new Date();
			long guestId = GroupLocalServiceUtil.getGroup(serviceContext.getCompanyId(), GroupConstants.GUEST).getGroupId();
			List<Long> assetCategoryIds = new ArrayList<Long>();
									
			User user = getUser();
			serviceContext.setUserId(user.getUserId());
			serviceContext.setScopeGroupId(user.getGroupId());
			
			serviceContext.setLanguageId(LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
			
			serviceContext.setAssetCategoryIds(ArrayUtil.toArray(assetCategoryIds.toArray(new Long[assetCategoryIds.size()])));
			
			
			course = CourseLocalServiceUtil.addCourse(
					name, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
					LocaleUtil.getDefault(), now, now, now,Long.parseLong(lspist[0]),GroupConstants.TYPE_SITE_PRIVATE,
					serviceContext, Long.parseLong("0"),0);
			course.setCourseEvalId(1); // EvaluationAvgCourseEval.getTypeId();
			com.liferay.lms.service.CourseLocalServiceUtil.modCourse(course,
					serviceContext);
			
			course.setGroupId(guestId);
			CourseLocalServiceUtil.updateCourse(course);
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId());
			assetEntry.setGroupId(guestId);

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.ADD, null);
			
			AssetEntryLocalServiceUtil.updateAssetEntry(assetEntry);
		} catch (NestableException e) {
		}
		
		return course;
	}
	@JSONWebService
	public java.util.List<String> getCourseStudents(long courseId)
	{
		return null;
	}
	@JSONWebService
	public java.util.List<String> getCourseTeachers(long courseId)
	{
		return null;
	}
	@JSONWebService
	public java.util.List<String> getCourseEditors(long courseId)
	{
		return null;
	}
	@JSONWebService
	public void addStudentToCourse(long courseId,String login)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		try {
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
				GroupLocalServiceUtil.addUserGroups(user.getUserId(), new long[] { course.getGroupCreatedId() });
			}
			
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), RoleLocalServiceUtil.getRole(serviceContext.getCompanyId(), RoleConstants.SITE_MEMBER).getRoleId());

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
			
		} catch (NestableException e) {
		} 
	}
	@JSONWebService
	public void addTeacherToCourse(long courseId,String login)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		try {
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
				GroupLocalServiceUtil.addUserGroups(user.getUserId(), new long[] { course.getGroupCreatedId() });
			}
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), LmsPrefsLocalServiceUtil.getLmsPrefs(serviceContext.getCompanyId()).getTeacherRole());

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
			
		} catch (NestableException e) {
		} 	
	}
	@JSONWebService
	public void addEditorToCourse(long courseId,String login)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		try {
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
				GroupLocalServiceUtil.addUserGroups(user.getUserId(), new long[] { course.getGroupCreatedId() });
			}
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), LmsPrefsLocalServiceUtil.getLmsPrefs(serviceContext.getCompanyId()).getEditorRole());

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
			
		} catch (NestableException e) {
		}
	}
	@JSONWebService
	public void removeStudentFromCourse(long courseId,String login)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		try {
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			GroupLocalServiceUtil.unsetUserGroups(user.getUserId(),new long[] { course.getGroupCreatedId() });

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
			
		} catch (NestableException e) {
		}
	}
	@JSONWebService
	public void removeTeacherFromCourse(long courseId,String login)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		try {
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			UserGroupRoleLocalServiceUtil.deleteUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), LmsPrefsLocalServiceUtil.getLmsPrefs(serviceContext.getCompanyId()).getTeacherRole());

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
			
		} catch (NestableException e) {
		}
	}
	@JSONWebService
	public void removeEditorFromCourse(long courseId,String login)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		try {
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			UserGroupRoleLocalServiceUtil.deleteUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), LmsPrefsLocalServiceUtil.getLmsPrefs(serviceContext.getCompanyId()).getEditorRole());

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), Course.class.getName(), course.getCourseId(), serviceContext.getUserId(), AuditConstants.UPDATE, null);
			
		} catch (NestableException e) {
		} 
	}
	@JSONWebService
	public long getUserResult(long courseId,String login)
	{
		return 0;
	}
	@JSONWebService
	public java.util.List<Course> myCourses() throws PortalException, SystemException
	{
		User usuario= this.getUser();
		java.util.List<Group> groups= GroupLocalServiceUtil.getUserGroups(usuario.getUserId());
		java.util.List<Course> results=new java.util.ArrayList<Course>();
		
		for(Group groupCourse:groups)
		{
			
			
			Course course=courseLocalService.fetchByGroupCreatedId(groupCourse.getGroupId());
			if(course!=null)
			{
				results.add(course);
			}
		}
		return results;

	}
	
	
	private long [] getGruposFromExpando(Long companyId, String [] userGroupNames) throws PortalException, SystemException {
		List<Long> userGroupIds = new ArrayList<Long>();
		if (userGroupNames != null) {
			for (String ugn : userGroupNames) {
				UserGroup ug = UserGroupLocalServiceUtil.getUserGroup(companyId, ugn);
				userGroupIds.add(ug.getUserGroupId());
			}
		}
		return ArrayUtil.toArray(userGroupIds.toArray(new Long[0]));
	}
	
	@JSONWebService
	public void addUser(String login, String firstName,String lastName,String email,boolean isStudent, boolean isTeacher,boolean isParent)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		int rolesItr=0;
		long[] roles = new long[((isStudent)?1:0)+((isTeacher)?1:0)+((isParent)?1:0)];
		
		try {
			if(isStudent){
				roles[rolesItr++]=UserGroupLocalServiceUtil.getUserGroup(serviceContext.getCompanyId(), PropsUtil.get("weclass.grupo.estudiante")).getUserGroupId();
			}
			
			if(isTeacher){
				roles[rolesItr++]=UserGroupLocalServiceUtil.getUserGroup(serviceContext.getCompanyId(), PropsUtil.get("weclass.grupo.profesor")).getUserGroupId();
			}
			
			if(isParent){
				roles[rolesItr]=UserGroupLocalServiceUtil.getUserGroup(serviceContext.getCompanyId(), PropsUtil.get("weclass.grupo.padre")).getUserGroupId();
			}
			
			Date birthday = new Date(0);
					
			User creatorUser=getUser();
			

			UserLocalServiceUtil.addUser(creatorUser.getUserId(), serviceContext.getCompanyId(), false, /*password1*/login, /*password2*/login, false, login, 
					email, new Long(0), "", creatorUser.getLocale(), firstName, StringPool.BLANK, lastName, -1, -1, 
					creatorUser.isMale(), birthday.getMonth(), birthday.getDay(), birthday.getYear()+1900 , StringPool.BLANK, null, null, null, roles, 
					false, ServiceContextThreadLocal.getServiceContext());
		} catch (PortalException e) {
		} catch (SystemException e) {
		}
	}
	@JSONWebService
	public void updateUser(String login, String firstName,String lastName,String email,boolean isStudent, boolean isTeacher,boolean isParent)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		long[] rolesToRemove = new long[((isStudent)?0:1)+((isTeacher)?0:1)+((isParent)?0:1)];
		List<Long> rolesToAdd = new ArrayList<Long>(3-rolesToRemove.length);
		int rolesAddItr=0,rolesRemoveItr=0;
		
		try {
			if (isStudent) {
				rolesToAdd.add(UserGroupLocalServiceUtil.getUserGroup(
						serviceContext.getCompanyId(),
						PropsUtil.get("weclass.grupo.estudiante"))
						.getUserGroupId());
			} else {
				rolesToRemove[rolesRemoveItr++] = UserGroupLocalServiceUtil
						.getUserGroup(serviceContext.getCompanyId(),
								PropsUtil.get("weclass.grupo.estudiante"))
						.getUserGroupId();
			}
			if (isTeacher) {
				rolesToAdd.add(UserGroupLocalServiceUtil.getUserGroup(
						serviceContext.getCompanyId(),
						PropsUtil.get("weclass.grupo.profesor"))
						.getUserGroupId());
			} else {
				rolesToRemove[rolesRemoveItr++] = UserGroupLocalServiceUtil
						.getUserGroup(serviceContext.getCompanyId(),
								PropsUtil.get("weclass.grupo.profesor"))
						.getUserGroupId();
			}
			if (isParent) {
				rolesToAdd.add(UserGroupLocalServiceUtil.getUserGroup(
						serviceContext.getCompanyId(),
						PropsUtil.get("weclass.grupo.padre")).getUserGroupId());
			} else {
				rolesToRemove[rolesRemoveItr] = UserGroupLocalServiceUtil
						.getUserGroup(serviceContext.getCompanyId(),
								PropsUtil.get("weclass.grupo.padre"))
						.getUserGroupId();
			}
			User user = UserLocalServiceUtil.getUserByScreenName(
					serviceContext.getCompanyId(), login);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmailAddress(email);
			List<UserGroup> userGroups = user.getUserGroups();
			long[] userArray = new long[] { user.getUserId() };
			for (long roleToRemove : rolesToRemove) {
				for (UserGroup userGroup : userGroups) {
					if (userGroup.getUserGroupId() == roleToRemove) {
						UserLocalServiceUtil.unsetUserGroupUsers(
								userGroup.getUserGroupId(), userArray);
					}
				}
			}
			for (Iterator<Long> iterator = rolesToAdd.iterator(); iterator
					.hasNext();) {
				long roleToAdd = iterator.next();
				for (UserGroup userGroup : userGroups) {
					if (userGroup.getUserGroupId() == roleToAdd) {
						iterator.remove();
					}
				}
			}
			for (long roleToAdd : rolesToAdd) {
				UserLocalServiceUtil.setUserGroupUsers(roleToAdd, userArray);
			}
			UserLocalServiceUtil.updateUser(user);
		} catch (Exception e) {
		}
	
	}
	@JSONWebService
	public void setParent(String parentLogin,String studentLogin)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		try {
			User parent = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), parentLogin);
			User student = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), studentLogin);				
			SocialRelationLocalServiceUtil.addRelation(parent.getUserId(), student.getUserId(), SocialRelationConstants.TYPE_UNI_PARENT);
			SocialRelationLocalServiceUtil.addRelation(student.getUserId(), parent.getUserId(), SocialRelationConstants.TYPE_UNI_CHILD);
		} catch (PortalException e) {
		} catch (SystemException e) {
		}
	}
	@JSONWebService
	public void unsetParent(String parentLogin,String studentLogin)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		try {
			User parent = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), parentLogin);
			User student = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), studentLogin);
			SocialRelationLocalServiceUtil.deleteRelation(parent.getUserId(), student.getUserId(), SocialRelationConstants.TYPE_UNI_PARENT);
			SocialRelationLocalServiceUtil.deleteRelation(student.getUserId(), parent.getUserId(), SocialRelationConstants.TYPE_UNI_CHILD);
		} catch (Exception e) {
		}		
	}
	@JSONWebService
	public void setTutor(String tutorLogin,String studentLogin)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		try {
			User tutor = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), tutorLogin);
			User student = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), studentLogin);	
			SocialRelationLocalServiceUtil.addRelation(tutor.getUserId(), student.getUserId(), SocialRelationConstants.TYPE_UNI_SUPERVISOR);
			SocialRelationLocalServiceUtil.addRelation(student.getUserId(), tutor.getUserId(), SocialRelationConstants.TYPE_UNI_SUBORDINATE);
		} catch (PortalException e) {
		} catch (SystemException e) {
		}
	}
	@JSONWebService
	public void unsetTutor(String parentLogin,String studentLogin)
	{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		try {
			User tutor = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), parentLogin);
			User student = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), studentLogin);
			SocialRelationLocalServiceUtil.deleteRelation(tutor.getUserId(), student.getUserId(), SocialRelationConstants.TYPE_UNI_SUPERVISOR);
			SocialRelationLocalServiceUtil.deleteRelation(student.getUserId(), tutor.getUserId(), SocialRelationConstants.TYPE_UNI_SUBORDINATE);
		} catch (Exception e) {
		}		
	}
	@JSONWebService
	public boolean existsCourseName(Long companyId, Long groupId, String groupName) {
		
		try {
			return CourseLocalServiceUtil.existsCourseName(companyId, groupId, groupName);
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@JSONWebService
	public String getLogoUrl(Long courseId) {
		Course course = null;
		Group generatedGroup = null;
		try {
			course = courseLocalService.getCourse(courseId);
			generatedGroup = GroupLocalServiceUtil.fetchGroup(course.getGroupCreatedId());
		} catch (SystemException e) {
			return "";
		} catch (PortalException e) {
			return "";
		}
		if (Validator.isNotNull(course.getIcon())) {
			try {
				FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(course.getIcon());
				return DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);
			} catch (SystemException e) {
				return "";
			} catch (PortalException e) {
				return "";
			}

		} else if(generatedGroup.getPublicLayoutSet().getLogo())
		{
			long logoId = generatedGroup.getPublicLayoutSet().getLogoId();
			return "/image/layout_set_logo?img_id="+logoId;
		}
		return "";
	}
}