package com.liferay.lms.learningactivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsUtil;


public class LearningActivityTypeRegistry 
{
	Map<Long, LearningActivityType> types;
	List<LearningActivityType> typesList;
	public LearningActivityType getLearningActivityType(long typeId)
	{
		
		
		 	return types.get(typeId);
	}
	public java.util.List<LearningActivityType> getLearningActivityTypes()
	{
		
		return typesList;
	}
	public LearningActivityTypeRegistry() throws SystemException 
	{
		Properties props=PropsUtil.getProperties("lms.learningactivity.type", true);
		types=new HashMap<Long, LearningActivityType>();
		typesList=new java.util.ArrayList<LearningActivityType>();
		for(Object key:props.keySet())
		{
			String type=props.getProperty(key.toString());
			
			try {
				Class c;
				c = Class.forName(type);
			
			LearningActivityType lat;
				lat = (LearningActivityType)c.newInstance();
			
			long typeId=lat.getTypeId();
			types.put(typeId,lat);
			typesList.add(lat);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
			}
		}
	}
}
