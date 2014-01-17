package com.liferay.manager;

import java.util.Date;

import com.liferay.lms.model.ActManAudit;
import com.liferay.lms.model.impl.ActManAuditImpl;
import com.liferay.lms.service.ActManAuditLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;

public class CleanLearningActivity {

	public static boolean running = false ;
	
	private ActManAudit actManAudit = null;
	
	public ActManAudit createInstance(){
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		actManAudit = new ActManAuditImpl();
		actManAudit.setClassName(this.getClass().getName());
		actManAudit.setCompanyId(serviceContext.getCompanyId());
		actManAudit.setStart(new Date());
		actManAudit.setState("run");
		
		try {
			actManAudit = ActManAuditLocalServiceUtil.addActManAudit(actManAudit);
		} catch (SystemException e) {
			e.printStackTrace();
			return null;
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
