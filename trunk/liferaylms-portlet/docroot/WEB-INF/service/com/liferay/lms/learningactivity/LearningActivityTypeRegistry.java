package com.liferay.lms.learningactivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

public class LearningActivityTypeRegistry {
	Map<Long, LearningActivityType> types;
	List<LearningActivityType> typesList;

	public LearningActivityType getLearningActivityType(long typeId) {
		return types.get(typeId);
	}

	public java.util.List<LearningActivityType> getLearningActivityTypes() {
		return typesList;
	}

	public LearningActivityTypeRegistry() throws SystemException {
		Properties props = PropsUtil.getProperties("lms.learningactivity.type", true);
		types = new HashMap<Long, LearningActivityType>();
		typesList = new java.util.ArrayList<LearningActivityType>();
		for (Object key:props.keySet()) {
			String type=props.getProperty(key.toString());
			try {
				Class<?> c = Class.forName(type);
			
				LearningActivityType lat = (LearningActivityType)c.newInstance();
				long typeId=lat.getTypeId();
				types.put(typeId,lat);
				typesList.add(lat);
			} catch (ClassNotFoundException e) {
				try {
					String [] context = ((String) key).split("\\.");
					
					Class c = null;
					ClassLoaderProxy clp = null;
					if (Validator.isNumber(context[1])) {
						c = Class.forName(
								type,
								true,
								PortletBeanLocatorUtil.getBeanLocator(
										ClpSerializer.getServletContextName())
										.getClassLoader());
						clp = new ClassLoaderProxy(c.newInstance(), type,
								PortletBeanLocatorUtil.getBeanLocator(
										ClpSerializer.getServletContextName())
										.getClassLoader());
					} else {
						c = Class.forName(type, true, PortletClassLoaderUtil
								.getClassLoader(context[1]));
						clp = new ClassLoaderProxy(c.newInstance(), type,
								PortletClassLoaderUtil
										.getClassLoader(context[1]));
					}
					
					LearningActivityTypeClp latyclp = new LearningActivityTypeClp(clp);
					long typeId=latyclp.getTypeId();
					types.put(typeId,latyclp);
					typesList.add(latyclp);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
			}
		}
	}
}
