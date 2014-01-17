package com.liferay.manager;

import java.util.Date;

import com.liferay.lms.model.ActManAudit;
import com.liferay.lms.model.impl.ActManAuditImpl;
import com.liferay.lms.service.ActManAuditLocalServiceUtil;
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
		createInstance();
	}
	
	public ActManAudit createInstance(){
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		actManAudit = new ActManAuditImpl();
		actManAudit.setClassName(this.getClass().getName());
		if(serviceContext!=null)
			actManAudit.setCompanyId(serviceContext.getCompanyId());
		actManAudit.setStart(new Date());
		actManAudit.setState("run");
		
		try {
			actManAudit = ActManAuditLocalServiceUtil.addActManAudit(actManAudit);
		} catch (SystemException e) {
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
			return null;
		} catch (BeanLocatorException e) {
			
		}
		return actManAudit;
	}

	public static void setRunning(boolean isRunning) {
		CleanLearningActivity.running = isRunning;
	}

	public static boolean isRunning() {
		return running;
	}
}
