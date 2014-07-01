package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class GeneralStats
 */
public class GeneralStats extends MVCPortlet {
	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		String action = ParamUtil.getString(resourceRequest, "action");
		long[] courseIds=ParamUtil.getLongValues(resourceRequest, "courseIds");
		ThemeDisplay themeDisplay  =(ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		if(action.equals("export")){
			try
			{
			resourceResponse.setCharacterEncoding("ISO-8859-1");
			resourceResponse.setContentType("text/csv;charset=ISO-8859-1");
			resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=generalstats."+Long.toString(System.currentTimeMillis())+".csv");
	        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
	        resourceResponse.getPortletOutputStream().write(b);   
	        CSVWriter writer = new CSVWriter(new OutputStreamWriter(resourceResponse.getPortletOutputStream(),"ISO-8859-1"),';');
	        String[] linea=new String[8];
	        linea[0]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.name");
	        linea[1]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.registered");
	        linea[2]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.starts.course");
	        linea[3]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.ends.course");
	        linea[4]=LanguageUtil.get(themeDisplay.getLocale(),"closed");
	        linea[5]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulestats.marks.average");
	        linea[6]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.modulecounter");
	        linea[7]=LanguageUtil.get(themeDisplay.getLocale(),"coursestats.activitiescounter");
	        writer.writeNext(linea);
	        for(long courseId:courseIds)
	        {
	        	Course course=CourseLocalServiceUtil.getCourse(courseId);
	        linea=new String[8];
	        linea[0]=course.getTitle(themeDisplay.getLocale());
	        Group groupsel= GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
			long registered=UserLocalServiceUtil.getGroupUsersCount(course.getGroupCreatedId(),0);
			
			
			long finalizados = CourseResultLocalServiceUtil.countByCourseId(course.getCourseId(), true);
			long iniciados = CourseResultLocalServiceUtil.countByCourseId(course.getCourseId(), false) + finalizados;
			double avgResult=0;
			if(finalizados>0)
			{
				avgResult=CourseResultLocalServiceUtil.avgResult(course.getCourseId());
			}
			long activitiesCount=LearningActivityLocalServiceUtil.countLearningActivitiesOfGroup(course.getGroupCreatedId());
			long modulesCount=ModuleLocalServiceUtil.countByGroupId(course.getGroupCreatedId());
			String closed=course.getClosed()?LanguageUtil.get(themeDisplay.getLocale(),"yes"):LanguageUtil.get(themeDisplay.getLocale(),"no");
			linea[1]=Long.toString(registered);
			linea[2]=Long.toString(iniciados);
			linea[3]=Long.toString(finalizados);
			DecimalFormat df = new DecimalFormat("#.#");
			linea[4]=closed;
			linea[5]=df.format(avgResult);
			linea[6]=Long.toString(modulesCount);
			linea[7]=Long.toString(activitiesCount);
	        writer.writeNext(linea);
	        resourceResponse.getPortletOutputStream().write(b);
	        }
	        writer.flush();
			writer.close();
			resourceResponse.getPortletOutputStream().flush();
			resourceResponse.getPortletOutputStream().close();
			
			}
			catch(Exception e)
			{
				
			}
		}
	}

}
