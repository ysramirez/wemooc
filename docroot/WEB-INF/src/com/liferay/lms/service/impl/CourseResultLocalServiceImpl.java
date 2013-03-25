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

import com.liferay.lms.learningactivity.courseeval.CourseEval;
import com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.base.CourseResultLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the course result local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CourseResultLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CourseResultLocalServiceUtil} to access the course result local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CourseResultLocalServiceBaseImpl
 * @see com.liferay.lms.service.CourseResultLocalServiceUtil
 */
public class CourseResultLocalServiceImpl
	extends CourseResultLocalServiceBaseImpl {
	public CourseResult getByUserAndCourse(long courseId,long userId) throws SystemException
	{
		return courseResultPersistence.fetchByuc(userId, courseId);
	}

	public long countByCourseId(long courseId, boolean passed) throws SystemException
	{
		return courseResultPersistence.countByc(courseId, passed);
	}
	public CourseResult create(long courseId, long userId) throws SystemException
	{
		CourseResult courseResult=courseResultPersistence.create(counterLocalService.increment(CourseResult.class.getName()));
		courseResult.setUserId(userId);
		courseResult.setCourseId(courseId);
		courseResult.setResult(0);
		courseResult.setPassed(false);
		courseResultPersistence.update(courseResult, false);
		return courseResult;
	}
	public void update(CourseResult cresult) throws SystemException
	{
		courseResultPersistence.update(cresult, false);
	}
	public void update(ModuleResult mresult) throws PortalException, SystemException
	{
		Module theModule=moduleLocalService.getModule(mresult.getModuleId());
		Course course=coursePersistence.fetchByGroupCreatedId(theModule.getGroupId());
		if(course!=null)
		{
			CourseEvalRegistry cer=new CourseEvalRegistry();
			long courseEvalTypeId=course.getCourseEvalId();
			CourseEval ceval=cer.getCourseEval(courseEvalTypeId);
			ceval.updateCourse(course, mresult);
		}
	}
}