package com.liferay.lms.learningactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.CompanyThreadLocal;

public class LearningActivityTypeRegistry {
	
	private static final String LEARNING_ACTIVITY_TYPES_KEY = LearningActivityTypeRegistry.class.getName()+"_learningActivityTypes";
	private Map<Long,LearningActivityType> _learningActivityTypes;
	
	private static Map<Long,LearningActivityType> _getLearningActivityTypes(){
		Map<Long,LearningActivityType> learningActivityTypes = new HashMap<Long, LearningActivityType>();
		Properties properties = PropsUtil.getProperties("lms.learningactivity.type", true);
		for (Object key:properties.keySet()) {
			String type=properties.getProperty(key.toString());
			try {			
				LearningActivityType learningActivityType = (LearningActivityType)Class.forName(type).newInstance();
				long typeId=learningActivityType.getTypeId();
				learningActivityTypes.put(typeId,learningActivityType);
			} catch (ClassNotFoundException e) {
				try {
					String [] context = ((String) key).split("\\.");
					ClassLoaderProxy clp = new ClassLoaderProxy(Class.forName(type, true, 
						PortletClassLoaderUtil.getClassLoader(context[1])).newInstance(), type, 
						PortletClassLoaderUtil.getClassLoader(context[1]));
					LearningActivityTypeClp latyclp = new LearningActivityTypeClp(clp);
					long typeId=latyclp.getTypeId();
					learningActivityTypes.put(typeId,latyclp);
				} catch (Throwable throwable) {
				}
			} catch (Throwable throwable) {
			}
		}
		return learningActivityTypes;
	}
	
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
			Map<Long,LearningActivityType> learningActivityTypes = _getLearningActivityTypes();
			try {
				_learningActivityTypes = new LinkedHashMap<Long, LearningActivityType>(learningActivityTypes.size());
				for(long typeId:StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(CompanyThreadLocal.getCompanyId()).getActivities(), StringPool.COMMA, -1L)){
					LearningActivityType learningActivityType = learningActivityTypes.get(typeId);
					if(Validator.isNotNull(learningActivityType)){
						_learningActivityTypes.put(typeId, learningActivityType);
						learningActivityTypes.remove(typeId);
					}
				}
				for (Entry<Long, LearningActivityType> learningActivityType : (Set<Entry<Long, LearningActivityType>>)learningActivityTypes.entrySet()) {
					_learningActivityTypes.put(learningActivityType.getKey(), learningActivityType.getValue());
				}
			} catch (NestableException e) {
				_learningActivityTypes = learningActivityTypes;
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
