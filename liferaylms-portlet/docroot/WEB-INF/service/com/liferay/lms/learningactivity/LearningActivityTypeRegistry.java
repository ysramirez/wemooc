package com.liferay.lms.learningactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;

public class LearningActivityTypeRegistry {
	
	private static final String LEARNING_ACTIVITY_TYPES_KEY = LearningActivityTypeRegistry.class.getName()+"_learningActivityTypes";
	private Map<Long,LearningActivityType> _learningActivityTypes;
	
	public LearningActivityTypeRegistry() {
		ThreadLocalCache<Map<Long,LearningActivityType>> threadLocalCache =
				ThreadLocalCacheManager.getThreadLocalCache(
					Lifecycle.REQUEST, LearningActivityType.class.getName());
		_learningActivityTypes = threadLocalCache.get(LEARNING_ACTIVITY_TYPES_KEY);

		if((Validator.isNull(_learningActivityTypes))||
			(!(_learningActivityTypes instanceof Map))||
			(!_learningActivityTypes.isEmpty())||
			(!(_learningActivityTypes.entrySet().iterator().next().getValue() instanceof LearningActivityType))
		) {
			Properties props = PropsUtil.getProperties("lms.learningactivity.type", true);
			_learningActivityTypes = new HashMap<Long, LearningActivityType>();
			for (Object key:props.keySet()) {
				String type=props.getProperty(key.toString());
				try {			
					LearningActivityType lat = (LearningActivityType)Class.forName(type).newInstance();
					long typeId=lat.getTypeId();
					_learningActivityTypes.put(typeId,lat);
				} catch (ClassNotFoundException e) {
					try {
						String [] context = ((String) key).split("\\.");
						ClassLoaderProxy clp = new ClassLoaderProxy(Class.forName(type, true, 
							PortletClassLoaderUtil.getClassLoader(context[1])).newInstance(), type, 
							PortletClassLoaderUtil.getClassLoader(context[1]));
						LearningActivityTypeClp latyclp = new LearningActivityTypeClp(clp);
						long typeId=latyclp.getTypeId();
						_learningActivityTypes.put(typeId,latyclp);
					} catch (Throwable throwable) {
					}
				} catch (Throwable throwable) {
				}
			}
			
			threadLocalCache.put(LEARNING_ACTIVITY_TYPES_KEY, _learningActivityTypes);
		}
	}

	public LearningActivityType getLearningActivityType(long typeId) {
		return _learningActivityTypes.get(typeId);
	}

	public List<LearningActivityType> getLearningActivityTypes() {
		Collection<LearningActivityType> values =  _learningActivityTypes.values();
		if(values instanceof List) {
			return new UnmodifiableList<LearningActivityType>
				((List<LearningActivityType>)values);
		}
		return  new UnmodifiableList<LearningActivityType>
				(new ArrayList<LearningActivityType>(values));
	}
}
