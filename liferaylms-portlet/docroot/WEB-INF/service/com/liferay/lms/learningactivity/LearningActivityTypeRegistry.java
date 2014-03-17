package com.liferay.lms.learningactivity;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.kernel.audit.AuditRequestThreadLocal;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.CompanyThreadLocal;

public class LearningActivityTypeRegistry {

	private static LearningActivityType[] _getLearningActivityTypes(){
		Properties properties = PropsUtil.getProperties("lms.learningactivity.type", true);
		LearningActivityType[] learningActivityTypes = new LearningActivityType[properties.size()];
		int currentLearningActivityType = 0;
		for (Object key:properties.keySet()) {
			String type=properties.getProperty(key.toString());
			try {			
				LearningActivityType learningActivityType = (LearningActivityType)Class.forName(type).newInstance();
				learningActivityTypes[currentLearningActivityType++]=learningActivityType;
			} catch (ClassNotFoundException e) {
				try {
					String [] context = ((String) key).split("\\.");
					ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(Class.forName(type, true, 
						PortletClassLoaderUtil.getClassLoader(context[1])).newInstance(), type, 
						PortletClassLoaderUtil.getClassLoader(context[1]));
					learningActivityTypes[currentLearningActivityType++]=new LearningActivityTypeClp(classLoaderProxy);
				} catch (Throwable throwable) {
				}
			} catch (Throwable throwable) {
			}
		}
		
		if(properties.size()==currentLearningActivityType) {
			return learningActivityTypes;
		}
		else {
			return Arrays.copyOf(learningActivityTypes,currentLearningActivityType);
		}
	}
	
	public LearningActivityTypeRegistry() {
		_learningActivityTypes =  _learningActivityTypeThreadLocal.get();
		if((Validator.isNull(_learningActivityTypes))||
			(_learningActivityTypes.isEmpty())||
			(!(_learningActivityTypes.get(0) instanceof LearningActivityType))) {
				LearningActivityType[] learningActivityTypes = _getLearningActivityTypes();
				try{
					long[] orderedIds = StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(CompanyThreadLocal.getCompanyId()).getActivities(), StringPool.COMMA, -1L);
					for (int currentPosition = 0; currentPosition < orderedIds.length; currentPosition++) {
						if(orderedIds[currentPosition]>=0){
							for(int currentLearningActivityType=currentPosition+1;currentLearningActivityType<learningActivityTypes.length;currentLearningActivityType++){
								if(learningActivityTypes[currentLearningActivityType].getTypeId()==orderedIds[currentPosition]){
									LearningActivityType learningActivityType=learningActivityTypes[currentLearningActivityType];
									learningActivityTypes[currentLearningActivityType]=learningActivityTypes[currentPosition];
									learningActivityTypes[currentPosition]=learningActivityType;
								}
							}
						}
					}
				} catch(NestableException e){}
			_learningActivityTypes=new UnmodifiableList<LearningActivityType>(Arrays.asList(learningActivityTypes));
			_learningActivityTypeThreadLocal.set(_learningActivityTypes);
		}
	}
		
	public LearningActivityType getLearningActivityType(long typeId) {
		for (LearningActivityType learningActivityType : _learningActivityTypes) {
			if(learningActivityType.getTypeId()==typeId){
				return learningActivityType;
			}
		}	
		return null;
	}

	public List<LearningActivityType> getLearningActivityTypes() {
		return _learningActivityTypes;
	}
	
	private List<LearningActivityType> _learningActivityTypes;
	
	private static ThreadLocal<List<LearningActivityType>>
		_learningActivityTypeThreadLocal =
			new AutoResetThreadLocal<List<LearningActivityType>>(
				AuditRequestThreadLocal.class + "._learningActivityTypeThreadLocal");
}
