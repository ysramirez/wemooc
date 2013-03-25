package com.liferay.lms.learningactivity.courseeval;

import java.util.Locale;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.ModuleResult;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * 
 * @author je03042
 *
 */
public interface CourseEval 
{
	public void updateCourse(Course course, ModuleResult moduleResult) throws SystemException;
	public String getName(Locale locale);
	public long getTypeId();

}
