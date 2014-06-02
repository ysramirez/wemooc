package com.liferay.lms.learningactivity.courseeval;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.CourseResult;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

public class MandatoryAvgCourseEval extends BaseCourseEval {
	
	private static DateFormat _dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:sszzz",Locale.US);
		
	
	
	private double calculateMean(double[] values) {
		int i;
		double sumWeight=values.length;
		
		
		double mean=0;
		for (i = 0; i < values.length; i++) {
			mean+=values[i];
		}
		mean/=sumWeight;
		
		//Correction factor
		
		
		return mean;
	}
	
	

	@Override
	public void updateCourse(Course course, ModuleResult mresult) throws SystemException 
	{				
		updateCourse(course, mresult.getUserId());				
	}
	
	@Override
	public boolean updateCourse(Course course, long userId) throws SystemException {
		
			CourseResult courseResult=CourseResultLocalServiceUtil.getByUserAndCourse(course.getCourseId(), userId);
			//.fetchByuc(mresult.getUserId(), course.getCourseId());
			if(courseResult==null)
			{
				courseResult=CourseResultLocalServiceUtil.create(course.getCourseId(), userId);
			
				//auditing
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupId(), CourseResult.class.getName(), courseResult.getPrimaryKey(), userId, AuditConstants.CREATE, null);
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
			List<LearningActivity> learningActivities=LearningActivityLocalServiceUtil.getMandatoryLearningActivitiesOfGroup(course.getGroupCreatedId());
			for(LearningActivity activity:learningActivities)
			{
				if(LearningActivityResultLocalServiceUtil.existsLearningActivityResult(activity.getActId(), userId))
				{
					LearningActivityResult learningActivityResult=LearningActivityResultLocalServiceUtil.getByActIdAndUserId(activity.getActId(), userId);
					result+=learningActivityResult.getResult();
				}
			}
			if(learningActivities.size()>0)
			{
				result=result/learningActivities.size();
			}
				courseResult.setResult(result);
				courseResult.setPassed(passed);
				if(passed && courseResult.getPassedDate()==null)courseResult.setPassedDate(new Date());
				CourseResultLocalServiceUtil.update(courseResult);
				return true;
	}

	@Override
	public boolean updateCourse(Course course) throws SystemException {
		try {
			if((course.getCourseExtraData()==null)&&(course.getCourseExtraData().trim().length()==0)) {
				return false;
			}
			
			Document document=SAXReaderUtil.read(course.getCourseExtraData());
			Element rootElement =document.getRootElement();
			
			
			
			for(User userOfCourse:UserLocalServiceUtil.getGroupUsers(course.getGroupCreatedId())){
				if(!PermissionCheckerFactoryUtil.create(userOfCourse).hasPermission(course.getGroupCreatedId(), "com.liferay.lms.model",course.getGroupCreatedId(), "VIEW_RESULTS")){
					updateCourse(course,  userOfCourse.getUserId());	
				}				
			}

			return true;
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public String getName() {
		return "mandatoryavg."+getTypeId()+".name";
	}
	
	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, "mandatoryavg."+getTypeId()+".name");
	}

	@Override
	public long getTypeId() {
		return 2;
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
