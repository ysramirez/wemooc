package com.liferay.manager;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.persistence.LearningActivityResultUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.model.User;

public class CleanLearningActivityTriesNotPassed extends CleanLearningActivity implements MessageListener{
	Log log = LogFactoryUtil.getLog(CleanLearningActivityTriesUser.class);
	private LearningActivity la = null;
	private User user = null;
	
	public CleanLearningActivityTriesNotPassed(){
	}

	public void process() throws Exception{
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),"portletClassLoader");
		
		//Los resultados que tengan fecha y no estén aprobados. 
		DynamicQuery dq = DynamicQueryFactoryUtil.forClass(LearningActivityResult.class,classLoader)
				.add(PropertyFactoryUtil.forName("actId").eq(la.getActId()))
				.add(PropertyFactoryUtil.forName("passed").ne(true))
				.add(PropertyFactoryUtil.forName("endDate").isNotNull());

		//List<LearningActivityResult> results = LearningActivityResultUtil.findWithDynamicQuery(dq);
		List<LearningActivityResult> results = LearningActivityResultLocalServiceUtil.dynamicQuery(dq);
		
		for(LearningActivityResult result:results){
			System.out.println(" result : " + result.getActId()+", result: "+result.getUserId() +", passed: "+result.getPassed() );

			List<LearningActivityTry> tries = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUser(result.getActId(), result.getUserId());
			
			for(LearningActivityTry ltry:tries){
				System.out.println("   try : " + ltry.getLatId()+" - "+ltry.getResult());
				
				LearningActivityTryLocalServiceUtil.deleteLearningActivityTry(ltry);
			}
			
			LearningActivityResultLocalServiceUtil.deleteLearningActivityResult(result);
			
		}
		
		
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		setRunning(true);
		
		try{
			this.la = (LearningActivity)message.get("learningActivity");
			this.user = (User)message.get("user");
			
			if(log.isDebugEnabled())log.debug(" LearningActivity: " + la.getTitle(Locale.getDefault()) + " - " + la.getActId() + " - " +user.getFullName());
			
			process();
		}catch(Exception e){
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
			
			e.printStackTrace();
		} finally {
			setRunning(false);
		}
		
		
	}

}
