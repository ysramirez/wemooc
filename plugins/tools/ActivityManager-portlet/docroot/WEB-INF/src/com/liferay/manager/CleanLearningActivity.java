package com.liferay.manager;

import java.util.Date;

import com.liferay.lmssa.model.ActManAudit;
import com.liferay.lmssa.model.impl.ActManAuditImpl;
import com.liferay.lmssa.service.ActManAuditLocalServiceUtil;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;

public class CleanLearningActivity {
	Log log = LogFactoryUtil.getLog(CleanLearningActivity.class);
	
	public static boolean running = false ;
	
	private ActManAudit actManAudit = null;
	
	public CleanLearningActivity(){
	}
	
	public ActManAudit createInstance(long companyId,long groupId,long userId){
		if(log.isDebugEnabled())log.debug("createInstance");
		setRunning(true);
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		actManAudit = new ActManAuditImpl();
		actManAudit.setClassName(this.getClass().getName());
		
		actManAudit.setCompanyId(companyId);
		actManAudit.setGroupId(groupId);
		actManAudit.setUserId(userId);
		
		actManAudit.setStart(new Date());
		actManAudit.setState("actmanager.run");
		
		try {
			actManAudit = ActManAuditLocalServiceUtil.addActManAudit(actManAudit);
		} catch (SystemException e) {
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
			e.printStackTrace();
			return null;
		} catch (BeanLocatorException e) {
			
		}
		return actManAudit;
	}

	public ActManAudit endInstance(){
		if(log.isDebugEnabled())log.debug("endInstance");
		actManAudit.setEnd(new Date());
		actManAudit.setState("actmanager.stop");
		try {
			actManAudit = ActManAuditLocalServiceUtil.updateActManAudit(actManAudit);
		} catch (SystemException e) {
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
		}
		setRunning(false);
		
		return actManAudit;
	}

	public static void setRunning(boolean isRunning) {
		CleanLearningActivity.running = isRunning;
	}

	public static boolean isRunning() {
		return running;
	}
}
