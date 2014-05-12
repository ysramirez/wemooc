/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.lms.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseService;
import com.liferay.portal.service.InvokableService;

/**
 * The interface for the course remote service.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author TLS
 * @see CourseServiceUtil
 * @see com.liferay.lms.service.base.CourseServiceBaseImpl
 * @see com.liferay.lms.service.impl.CourseServiceImpl
 * @generated
 */
@JSONWebService
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CourseService extends BaseService, InvokableService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CourseServiceUtil} to access the course remote service. Add custom service methods to {@link com.liferay.lms.service.impl.CourseServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.lms.model.Course> getCoursesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.Course createCourse(java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.String> getCourseStudents(long courseId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.String> getCourseTeachers(long courseId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.String> getCourseEditors(long courseId);

	public void addStudentToCourse(long courseId, java.lang.String login);

	public void addTeacherToCourse(long courseId, java.lang.String login);

	public void addEditorToCourse(long courseId, java.lang.String login);

	public void removeStudentFromCourse(long courseId, java.lang.String login);

	public void removeTeacherFromCourse(long courseId, java.lang.String login);

	public void removeEditorFromCourse(long courseId, java.lang.String login);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getUserResult(long courseId, java.lang.String login);

	public java.util.List<com.liferay.lms.model.Course> myCourses()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addUser(java.lang.String login, java.lang.String firstName,
		java.lang.String lastName, java.lang.String email, boolean isStudent,
		boolean isTeacher, boolean isParent);

	public void updateUser(java.lang.String login, java.lang.String firstName,
		java.lang.String lastName, java.lang.String email, boolean isStudent,
		boolean isTeacher, boolean isParent);

	public void setParent(java.lang.String parentLogin,
		java.lang.String studentLogin);

	public void unsetParent(java.lang.String parentLogin,
		java.lang.String studentLogin);

	public void setTutor(java.lang.String tutorLogin,
		java.lang.String studentLogin);

	public void unsetTutor(java.lang.String parentLogin,
		java.lang.String studentLogin);

	public boolean existsCourseName(java.lang.Long companyId,
		java.lang.Long groupId, java.lang.String groupName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getLogoUrl(java.lang.Long courseId);
}