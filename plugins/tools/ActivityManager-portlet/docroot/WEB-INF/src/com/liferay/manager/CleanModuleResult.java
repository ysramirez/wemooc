package com.liferay.manager;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

public class CleanModuleResult extends CleanLearningActivity implements MessageListener{

	@Override
	public void receive(Message message) throws MessageListenerException {

		long groupId  = message.getLong("groupId");
		long moduleId = message.getLong("moduleId");
		long userId   = message.getLong("userId");
		
		String action = message.getString("action");
		
		if("cleanAllCourses".equals(action)){
			
			cleanAllCourses();
			
		} else if("cleanModuleAllUsers".equals(action)){
			
			cleanModuleAllUsers(groupId, moduleId);
			
		} else if("cleanModuleUser".equals(action)){
			
			cleanModuleUser(moduleId, userId);
			
		}
	}
	
	public void cleanAllCourses(){
		
		try {
			
			ModuleResultLocalServiceUtil.updateAllCoursesAllModulesAllUsers();
			
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void cleanModuleAllUsers(long groupId, long moduleId){
		
		try {

			ModuleResultLocalServiceUtil.updateAllUsers(groupId, moduleId);
			
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void cleanModuleUser(long moduleId, long userId){
		
		try {

			ModuleResultLocalServiceUtil.update(moduleId, userId);
			
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	
}
