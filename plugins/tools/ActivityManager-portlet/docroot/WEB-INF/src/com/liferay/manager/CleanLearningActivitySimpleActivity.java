package com.liferay.manager;

import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

public class CleanLearningActivitySimpleActivity extends CleanLearningActivity
		implements MessageListener {

	@Override
	public void receive(Message message) throws MessageListenerException {
		
		long groupId  = message.getLong("groupId");
		long moduleId = message.getLong("moduleId");
		long userId   = message.getLong("userId");
		long actId    = message.getLong("actId");
		
		String action = message.getString("action");
		
		if("deleteUserUpload".equals(action)){
			
			deleteUserUpload(actId, userId);
			
		}
	}
	
	private void deleteUserUpload(long actId, long userId){
		
		try {
			
			//Las encuestas sólo se pueden hacer una vez, sólo tendremos una.
			LearningActivityTry learnTry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, userId);
			LearningActivityResult learnResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
			
			//Borrar los registros de la base de datos.
			LearningActivityResultLocalServiceUtil.deleteLearningActivityResult(learnResult);
			LearningActivityTryLocalServiceUtil.deleteLearningActivityTry(learnTry);

		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
