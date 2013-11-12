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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableService;

/**
 * The utility for the course remote service. This utility wraps {@link com.liferay.lms.service.impl.CourseServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author TLS
 * @see CourseService
 * @see com.liferay.lms.service.base.CourseServiceBaseImpl
 * @see com.liferay.lms.service.impl.CourseServiceImpl
 * @generated
 */
public class CourseServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.service.impl.CourseServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static java.util.List<com.liferay.lms.model.Course> getCoursesOfGroup(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCoursesOfGroup(groupId);
	}

	public static com.liferay.lms.model.Course createCourse(
		java.lang.String name) {
		return getService().createCourse(name);
	}

	public static java.util.List<java.lang.String> getCourseStudents(
		long courseId) {
		return getService().getCourseStudents(courseId);
	}

	public static java.util.List<java.lang.String> getCourseTeachers(
		long courseId) {
		return getService().getCourseTeachers(courseId);
	}

	public static java.util.List<java.lang.String> getCourseEditors(
		long courseId) {
		return getService().getCourseEditors(courseId);
	}

	public static void addStudentToCourse(long courseId, java.lang.String login) {
		getService().addStudentToCourse(courseId, login);
	}

	public static void addTeacherToCourse(long courseId, java.lang.String login) {
		getService().addTeacherToCourse(courseId, login);
	}

	public static void addEditorToCourse(long courseId, java.lang.String login) {
		getService().addEditorToCourse(courseId, login);
	}

	public static void removeStudentFromCourse(long courseId,
		java.lang.String login) {
		getService().removeStudentFromCourse(courseId, login);
	}

	public static void removeTeacherFromCourse(long courseId,
		java.lang.String login) {
		getService().removeTeacherFromCourse(courseId, login);
	}

	public static void removeEditorFromCourse(long courseId,
		java.lang.String login) {
		getService().removeEditorFromCourse(courseId, login);
	}

	public static long getUserResult(long courseId, java.lang.String login) {
		return getService().getUserResult(courseId, login);
	}

	public static java.util.List<com.liferay.lms.model.Course> myCourses()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().myCourses();
	}

	public static void addUser(java.lang.String login,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String email, boolean isStudent, boolean isTeacher,
		boolean isParent) {
		getService()
			.addUser(login, firstName, lastName, email, isStudent, isTeacher,
			isParent);
	}

	public static void updateUser(java.lang.String login,
		java.lang.String firstName, java.lang.String lastName,
		java.lang.String email, boolean isStudent, boolean isTeacher,
		boolean isParent) {
		getService()
			.updateUser(login, firstName, lastName, email, isStudent,
			isTeacher, isParent);
	}

	public static void setParent(java.lang.String parentLogin,
		java.lang.String studentLogin) {
		getService().setParent(parentLogin, studentLogin);
	}

	public static void unsetParent(java.lang.String parentLogin,
		java.lang.String studentLogin) {
		getService().unsetParent(parentLogin, studentLogin);
	}

	public static void setTutor(java.lang.String tutorLogin,
		java.lang.String studentLogin) {
		getService().setTutor(tutorLogin, studentLogin);
	}

	public static void unsetTutor(java.lang.String parentLogin,
		java.lang.String studentLogin) {
		getService().unsetTutor(parentLogin, studentLogin);
	}

	public static void clearService() {
		_service = null;
	}

	public static CourseService getService() {
		if (_service == null) {
			InvokableService invokableService = (InvokableService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					CourseService.class.getName());

			if (invokableService instanceof CourseService) {
				_service = (CourseService)invokableService;
			}
			else {
				_service = new CourseServiceClp(invokableService);
			}

			ReferenceRegistry.registerReference(CourseServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(CourseService service) {
	}

	private static CourseService _service;
}