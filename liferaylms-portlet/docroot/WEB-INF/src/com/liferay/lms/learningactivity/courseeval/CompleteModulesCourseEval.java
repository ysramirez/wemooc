package com.liferay.lms.learningactivity.courseeval;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

public class CompleteModulesCourseEval implements CourseEval {

	@Override
	@SuppressWarnings("unchecked")
	public void updateCourse(Course course, ModuleResult mresult) throws SystemException 
	{
		
		CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), mresult.getUserId());
				//.fetchByuc(mresult.getUserId(), course.getCourseId());
		if(courseResult==null)
		{
			courseResult=CourseResultLocalServiceUtil.create(course.getCourseId(), mresult.getUserId());

			//auditing
			AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), CourseResult.class.getName(), courseResult.getPrimaryKey(), mresult.getUserId(), AuditConstants.CREATE, null);
		}
		if(mresult.isPassed())
		{
			List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId());
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
	@SuppressWarnings("unchecked")
	public boolean updateCourse(Course course, long userId) throws SystemException {
		
		CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), userId);
		if(courseResult==null)
		{
			courseResult=CourseResultLocalServiceUtil.create(course.getCourseId(), userId);
		}

		List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId());
		boolean passed=true;
		long cuantospasados=0;
		for(Module thmodule:modules)
		{
			if(!ModuleLocalServiceUtil.isUserPassed(thmodule.getModuleId(), userId))
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
			return true;	
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean updateCourse(Course course) throws SystemException {
		
		List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId());
		try {
		
			for(User userOfCourse:UserLocalServiceUtil.getGroupUsers(course.getGroupCreatedId())){
				if(!PermissionCheckerFactoryUtil.create(userOfCourse).hasPermission(course.getGroupCreatedId(), "com.liferay.lms.model",course.getGroupCreatedId(), "VIEW_RESULTS")){
			
				CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), userOfCourse.getUserId());
				if(courseResult==null)
				{
					courseResult=CourseResultLocalServiceUtil.create(course.getCourseId(), userOfCourse.getUserId());
				}
				
	
				
				boolean passed=true;
				long cuantospasados=0;
				for(Module thmodule:modules)
				{
					if(!ModuleLocalServiceUtil.isUserPassed(thmodule.getModuleId(), userOfCourse.getUserId()))
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
		} catch (Exception e) {
			throw new SystemException(e);
		}
		return true;	
	}
	
	@Override
	public String getName() {
		return getName(Locale.getDefault());
	}
	
	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(Locale.getDefault(), "courseeval."+getTypeId()+".name");
	}

	@Override
	public long getTypeId() {
		return 0;
	}

	@Override
	public boolean getNeedPassAllModules() {
		return true;
	}

	@Override
	public boolean getNeedPassPuntuation() {
		return false;
	}

	@Override
	public long getPassPuntuation(Course course) throws DocumentException {
		throw new RuntimeException();
	}
	
	@Override
	public JSONObject getEvaluationModel(Course course) throws PortalException,
			SystemException, DocumentException, IOException {
		return JSONFactoryUtil.createJSONObject();
	}

	@Override
	public void setEvaluationModel(Course course, JSONObject model)
			throws PortalException, SystemException, DocumentException,
			IOException {	
		Document document = SAXReaderUtil.createDocument();
		Element rootElement = document.addElement("eval");
		rootElement.addElement("courseEval").setText(CompleteModulesCourseEval.class.getName());		
		course.setCourseExtraData(document.formattedString());
	}

}
