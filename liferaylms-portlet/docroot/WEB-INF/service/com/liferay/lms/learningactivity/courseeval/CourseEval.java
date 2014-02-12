package com.liferay.lms.learningactivity.courseeval;

import java.io.IOException;
import java.util.Locale;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.ModuleResult;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.xml.DocumentException;

public interface CourseEval 
{
	public void updateCourse(Course course, ModuleResult moduleResult) throws SystemException;
	public boolean updateCourse(Course course, long userId) throws SystemException;
	public boolean updateCourse(Course course) throws SystemException;
	public String getName();
	public String getName(Locale locale);
	public long getTypeId();
	public boolean getNeedPassAllModules();
	public boolean getNeedPassPuntuation();
	public long getPassPuntuation(Course course) throws DocumentException;

	public JSONObject getEvaluationModel(Course course)
			throws PortalException, SystemException, DocumentException,
			IOException;

	public void setEvaluationModel(Course course, JSONObject model)
				throws PortalException, SystemException, DocumentException,
				IOException;


}
