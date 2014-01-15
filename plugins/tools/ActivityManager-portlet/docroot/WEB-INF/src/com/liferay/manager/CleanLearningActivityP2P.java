package com.liferay.manager;

import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

public class CleanLearningActivityP2P extends CleanLearningActivity implements MessageListener {
	
	@Override
	public void receive(Message message) throws MessageListenerException {

		long groupId  = message.getLong("groupId");
		long moduleId = message.getLong("moduleId");
		long userId   = message.getLong("userId");
		long actId    = message.getLong("actId");
		
		String action = message.getString("action");
		
		if("deleteUserUpload".equals(action)){
			
			deleteUserUpload(actId, userId);
			
		} else if("deleteUserUpload".equals(action)){
			
			deleteUserUpload(groupId, moduleId);
			
		} 
		
	}
	
	private void deleteUserUpload(long actId, long userId){
		
		try {
			
			P2pActivity p2p = P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, userId);
			
			//Las P2P sólo generan un try cuando se hace la entrega, con obtener la última nos es suficiente.
			LearningActivityTry learnTry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, userId);
			LearningActivityResult learnResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
			
			//Borrar los registros de la base de datos.
			LearningActivityResultLocalServiceUtil.deleteLearningActivityResult(learnResult);
			LearningActivityTryLocalServiceUtil.deleteLearningActivityTry(learnTry);
			P2pActivityLocalServiceUtil.deleteP2pActivity(p2p);
			
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
