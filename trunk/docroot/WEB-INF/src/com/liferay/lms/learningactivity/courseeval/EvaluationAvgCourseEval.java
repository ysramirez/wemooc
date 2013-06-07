package com.liferay.lms.learningactivity.courseeval;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;

public class EvaluationAvgCourseEval implements CourseEval {

	@Override
	public void updateCourse(Course course, ModuleResult mresult) throws SystemException 
	{
		
		CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), mresult.getUserId());
				
		if(courseResult==null)
		{
			courseResult=CourseResultLocalServiceUtil.create(course.getCourseId(), mresult.getUserId());
		}
		java.util.List<LearningActivity> evals=LearningActivityLocalServiceUtil.getLearningActivitiesOfGroupAndType(course.getGroupCreatedId(), 8);
		long score=0;
		boolean passed=true;
		for(LearningActivity la:evals)
		{
			
			
			if(LearningActivityResultLocalServiceUtil.existsLearningActivityResult(la.getActId(), mresult.getUserId()))
			{
				LearningActivityResult result=LearningActivityResultLocalServiceUtil.getByActIdAndUserId(la.getActId(), mresult.getUserId());
				score+=result.getResult();
				if(!result.getPassed())
				{
					passed=false;
				}
			}
		}
		if(evals.size()>0)
		{
		long totalScore=score/evals.size();
		courseResult.setResult(totalScore);
		courseResult.setPassed(passed);
		CourseResultLocalServiceUtil.update(courseResult);
		}

	}

	@Override
	public String getName() {
		return "Media de evaluaciones";
	}

	@Override
	public long getTypeId() {
		return 1;
	}

	@Override
	public boolean getNeedPassAllModules() {
		return false;
	}

	@Override
	public boolean getNeedPassPuntuation() {
		return true;
	}

}
