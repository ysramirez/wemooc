package com.liferay.manager;

import java.util.List;
import java.util.Locale;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.model.User;

public class CleanLearningActivityTriesUser extends CleanLearningActivity implements MessageListener{
	Log log = LogFactoryUtil.getLog(CleanLearningActivityTriesUser.class);
	private LearningActivity la = null;
	private User user = null;
	private User userc = null;
	
	public CleanLearningActivityTriesUser(){
		super();
	}

	public void process() throws Exception{
		List<LearningActivityTry> lats = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUser(la.getActId(), user.getUserId());
		for(LearningActivityTry lat : lats){
			LearningActivityTryLocalServiceUtil.deleteLearningActivityTry(lat.getLatId());
		}
		
		LearningActivityResult lar = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(la.getActId(), user.getUserId());
		if(lar!=null){
			LearningActivityResultLocalServiceUtil.deleteLearningActivityResult(lar);
		}
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		
		try{
			this.la = (LearningActivity)message.get("learningActivity");
			this.user = (User)message.get("user");
			User userc = (User)message.get("userc");

			createInstance(la.getCompanyId(),la.getGroupId(),user.getUserId());
			
			if(log.isDebugEnabled())log.debug(" LearningActivity: " + la.getTitle(Locale.getDefault()) + " - " + la.getActId() + " - " +user.getFullName());
			
			process();
		}catch(Exception e){
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
		} finally {
			endInstance();
		}
		
		
	}

}
