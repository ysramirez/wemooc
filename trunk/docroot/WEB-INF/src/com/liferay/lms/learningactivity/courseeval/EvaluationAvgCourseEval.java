package com.liferay.lms.learningactivity.courseeval;

import java.util.Locale;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.impl.LearningActivityLocalServiceImpl;
import com.liferay.portal.kernel.exception.SystemException;

public class EvaluationAvgCourseEval implements CourseEval {

	@Override
	public void updateCourse(Course course, ModuleResult mresult) throws SystemException 
	{
		
		CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), mresult.getUserId());
				//.fetchByuc(mresult.getUserId(), course.getCourseId());
		if(courseResult==null)
		{
			courseResult=CourseResultLocalServiceUtil.create(course.getCourseId(), mresult.getUserId());
		}
		java.util.List<LearningActivity> evals=LearningActivityLocalServiceUtil.getLearningActivitiesOfGroupAndType(course.getGroupCreatedId(), 8);
	

	}

	@Override
	public String getName(Locale locale) {
		// TODO Auto-generated method stub
		return "Media de evaluaciones";
	}

	@Override
	public long getTypeId() {
		return 0;
	}

}
