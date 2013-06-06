package com.liferay.lms.learningactivity.courseeval;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.ModuleResult;
import com.liferay.portal.kernel.exception.SystemException;

public interface CourseEval 
{
	public void updateCourse(Course course, ModuleResult moduleResult) throws SystemException;
	public String getName();
	public long getTypeId();

}
