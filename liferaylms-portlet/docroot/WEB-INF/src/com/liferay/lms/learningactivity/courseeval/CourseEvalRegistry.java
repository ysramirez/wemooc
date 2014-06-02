package com.liferay.lms.learningactivity.courseeval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.liferay.lms.learningactivity.LearningActivityTypeClp;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PropsUtil;


public class CourseEvalRegistry 
{
	Map<Long, CourseEval> types;
	List<CourseEval> typesList;
	public java.util.List<CourseEval> getCourseEvals()
	{
		
		return typesList;
	}
	public CourseEval getCourseEval(long typeId)
	{
		
		
		 	return types.get(typeId);
	}
	
	@SuppressWarnings("unchecked")
	public CourseEvalRegistry() 
	{
	
		Properties props=PropsUtil.getProperties("lms.course.courseeval", true);
		types=new HashMap<Long, CourseEval>();
		typesList=new java.util.ArrayList<CourseEval>();
		for(Object key:props.keySet())
		{
			String type=props.getProperty(key.toString());
			
			try {
				Class<CourseEval> c = (Class<CourseEval>) Class.forName(type);
				CourseEval lat = c.newInstance();
				long typeId=lat.getTypeId();
				types.put(typeId,lat);
				typesList.add(lat);
			} catch (ClassNotFoundException e) {
				try {
					String [] context = ((String) key).split("\\.");
					Class<?> c = Class.forName(type, true, PortletClassLoaderUtil.getClassLoader(context[1]));
					ClassLoaderProxy clp = new ClassLoaderProxy(c.newInstance(), type, PortletClassLoaderUtil.getClassLoader(context[1]));
					CourseEvalClp courseEvalClp = new CourseEvalClp(clp);
					long typeId=courseEvalClp.getTypeId();
					types.put(typeId,courseEvalClp);
					typesList.add(courseEvalClp);
				} catch (ClassNotFoundException e1) {
				} catch (InstantiationException e1) {
				} catch (IllegalAccessException e1) {
				} 
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}
	}
}
