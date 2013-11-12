package com.liferay.lms.learningactivity.calificationtype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.liferay.lms.learningactivity.LearningActivityTypeClp;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PropsUtil;

public class CalificationTypeRegistry {
	Map<Long, CalificationType> types;
	List<CalificationType> typesList;

	public CalificationType getCalificationType(long typeId) {
		return types.get(typeId);
	}

	public java.util.List<CalificationType> getCalificationTypes() {
		return typesList;
	}

	public CalificationTypeRegistry() throws SystemException {
		Properties props = PropsUtil.getProperties("lms.calification.type", true);
		types = new HashMap<Long, CalificationType>();
		typesList = new java.util.ArrayList<CalificationType>();
		for (Object key:props.keySet()) {
			String type=props.getProperty(key.toString());
			try {
				Class<?> c = Class.forName(type);
			
				CalificationType qt = (CalificationType)c.newInstance();
				long typeId=qt.getTypeId();
				types.put(typeId,qt);
				typesList.add(qt);
			} catch (ClassNotFoundException e) {
				try {
					String [] context = ((String) key).split("\\.");
					Class c = Class.forName(type, true, PortletClassLoaderUtil.getClassLoader(context[1]));
					ClassLoaderProxy clp = new ClassLoaderProxy(c.newInstance(), type, PortletClassLoaderUtil.getClassLoader(context[1]));
					CalificationTypeClp ctclp = new CalificationTypeClp(clp);
					long typeId=ctclp.getTypeId();
					types.put(typeId,ctclp);
					typesList.add(ctclp);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
