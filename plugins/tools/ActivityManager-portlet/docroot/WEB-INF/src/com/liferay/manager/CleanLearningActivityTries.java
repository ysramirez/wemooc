package com.liferay.manager;

import java.util.List;
import java.util.Locale;

import com.liferay.lms.activitymanager.ActivityManagerPortlet;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

/**
 * Portlet implementation class ActivityManager
 */
public class CleanLearningActivityTries extends CleanLearningActivity implements MessageListener{
	Log log = LogFactoryUtil.getLog(ActivityManagerPortlet.class);
	private LearningActivity la = null;
	
	public CleanLearningActivityTries(){}
	
	public CleanLearningActivityTries(LearningActivity la) {
		super();
		this.la = la;
	}

	public void run() {
		process();
	}
 
	public void process(){
		if(log.isDebugEnabled())log.debug("start");
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),"portletClassLoader");
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityTry.class,classLoader);
	  	Criterion criterion=PropertyFactoryUtil.forName("actId").eq(la.getActId());
		dq.add(criterion);
		
		try {
			List<LearningActivityTry> tries = LearningActivityTryLocalServiceUtil.dynamicQuery(dq);

			if(log.isDebugEnabled())log.debug("delete tries::"+tries.size());
			for(LearningActivityTry learningActivityTry : tries){
				if(log.isDebugEnabled())log.debug("delete try::"+learningActivityTry.getLatId());
				LearningActivityTryLocalServiceUtil.deleteLearningActivityTry(learningActivityTry);
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		if(log.isDebugEnabled())log.debug("stop");
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		
		try {
			
			this.la = (LearningActivity)message.get("learningActivity");
		
			System.out.println(" LearningActivity: " + la.getTitle(Locale.getDefault()) + " - " + la.getActId());
			
			process();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
