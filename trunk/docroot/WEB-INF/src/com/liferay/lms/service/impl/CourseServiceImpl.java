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

import com.liferay.lms.model.Course;
import com.liferay.lms.service.base.CourseServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;

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
		return null;
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
		
	}
	@JSONWebService
	public void addTeacherToCourse(long courseId,String login)
	{
		
	}
	@JSONWebService
	public void addEditorToCourse(long courseId,String login)
	{
		
	}
	@JSONWebService
	public void removeStudentFromCourse(long courseId,String login)
	{
		
	}
	@JSONWebService
	public void removeTeacherFromCourse(long courseId,String login)
	{
		
	}
	@JSONWebService
	public void removeEditorFromCourse(long courseId,String login)
	{
		
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
	@JSONWebService
	public void addUser(String login, String firstName,String lastName,String email,boolean isStudent, boolean isTeacher,boolean isParent)
	{
		return ;
	}
	@JSONWebService
	public void updateUser(String login, String firstName,String lastName,String email,boolean isStudent, boolean isTeacher,boolean isParent)
	{
		return ;
	}
	@JSONWebService
	public void setParent(String parentLogin,String studentLogin)
	{
		
	}
	@JSONWebService
	public void unsetParent(String parentLogin,String studentLogin)
	{
		
	}
	@JSONWebService
	public void setTutor(String parentLogin,String studentLogin)
	{
		
	}
	@JSONWebService
	public void unsetTutor(String parentLogin,String studentLogin)
	{
		
	}
}