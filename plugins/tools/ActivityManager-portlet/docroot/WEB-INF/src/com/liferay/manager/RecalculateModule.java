package com.liferay.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.model.User;

public class RecalculateModule extends CleanLearningActivity implements MessageListener{
	Log log = LogFactoryUtil.getLog(RecalculateModule.class);
	Module module = null;

	public RecalculateModule(){
		super();
	}

	public void process() throws Exception{
		
		ModuleResultLocalServiceUtil.updateAllUsers(module.getGroupId(), module.getModuleId());
		
		/*List<LearningActivity> las = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(module.getModuleId());
		Set<Long> users = new HashSet<Long>();
		
		if(las!=null&&las.size()>0){
			for(LearningActivity la : las){
				if(log.isDebugEnabled())log.debug("Recalculate la:"+la.getActId());
				List<LearningActivityResult> lars = LearningActivityResultLocalServiceUtil.getByActId(la.getActId());
				if(lars!=null){
					for(LearningActivityResult lar : lars){
						if(log.isDebugEnabled())log.debug("Recalculate lar:"+lar.getLarId());
						if(!users.contains(lar.getUserId())){
							if(log.isDebugEnabled())log.debug("Recalculate user:"+lar.getUserId());
							users.add(lar.getUserId());
							try{
								ModuleResultLocalServiceUtil.update(lar);
								
							}catch(Exception e){
								if(log.isInfoEnabled())log.info(e.getMessage());
								if(log.isDebugEnabled())e.printStackTrace();
							}
						}
					}
				}
			}
		}*/
	}
	
	@Override
	public void receive(Message message) throws MessageListenerException {
		try{
			this.module = (Module)message.get("module");
			User userc = (User)message.get("userc");

			createInstance(module.getCompanyId(),module.getGroupId(),userc.getUserId());
			
			if(log.isDebugEnabled())log.debug(" Module: " + module.getTitle(Locale.getDefault()));
			
			process();
		}catch(Exception e){
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
		} finally {
			endInstance();
		}
	}
}
