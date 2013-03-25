package com.liferay.lms.learningactivity.courseeval;

import java.util.Locale;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;

public class CompleteModulesCourseEval implements CourseEval {

	@Override
	public void updateCourse(Course course, ModuleResult mresult) throws SystemException 
	{
		
		CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), mresult.getUserId());
				//.fetchByuc(mresult.getUserId(), course.getCourseId());
		if(courseResult==null)
		{
			courseResult=CourseResultLocalServiceUtil.create(course.getCourseId(), mresult.getUserId());
		}
		if(mresult.isPassed())
		{
			java.util.List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId());
			boolean passed=true;
			long cuantospasados=0;
			for(Module thmodule:modules)
			{
				if(!ModuleLocalServiceUtil.isUserPassed(thmodule.getModuleId(), mresult.getUserId()))
				{
					passed=false;
					
				}
				else
				{
					cuantospasados++;
				}
			}
			long result=0;
			if(modules.size()>0)
			{
				result=100*cuantospasados/modules.size();
			}
			
			
				courseResult.setResult(result);
				courseResult.setPassed(passed);
				CourseResultLocalServiceUtil.update(courseResult);
			
		}

	}

	@Override
	public String getName(Locale locale) {
		// TODO Auto-generated method stub
		return "Completar Modulos";
	}

	@Override
	public long getTypeId() {
		return 0;
	}

}
