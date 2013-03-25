package com.liferay.lms.learningactivity.courseeval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
	public CourseEvalRegistry() 
	{
	
		Properties props=PropsUtil.getProperties("lms.course.courseeval", true);
		types=new HashMap<Long, CourseEval>();
		typesList=new java.util.ArrayList<CourseEval>();
		for(Object key:props.keySet())
		{
			String type=props.getProperty(key.toString());
			
			try {
				Class c;
				c = Class.forName(type);
			
				CourseEval lat;
				lat = (CourseEval)c.newInstance();
			
			long typeId=lat.getTypeId();
			types.put(typeId,lat);
			typesList.add(lat);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
