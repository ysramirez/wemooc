package com.liferay.manager;

import java.util.Date;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lmssa.model.ActManAudit;
import com.liferay.lmssa.model.LearningActivityTryDeleted;
import com.liferay.lmssa.model.impl.ActManAuditImpl;
import com.liferay.lmssa.model.impl.LearningActivityTryDeletedImpl;
import com.liferay.lmssa.service.ActManAuditLocalServiceUtil;
import com.liferay.lmssa.service.LearningActivityTryDeletedLocalServiceUtil;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.exception.PortalException;
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
		
		actManAudit = new ActManAuditImpl();
		actManAudit.setClassName(this.getClass().getName());
		
		actManAudit.setCompanyId(companyId);
		actManAudit.setGroupId(groupId);
		actManAudit.setUserId(userId);
		
		actManAudit.setStart(new Date());
		actManAudit.setState("actmanager.run");
		actManAudit.setNumber(0);
		
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
	
	public boolean processTry(LearningActivityTry lat){
		
		LearningActivityTryDeleted latd = new LearningActivityTryDeletedImpl();
		
		latd.setActId(lat.getActId());
		latd.setActManAuditId(actManAudit.getActManAuditId());
		latd.setEndDate(lat.getEndDate());
		latd.setLatId(lat.getLatId());
		latd.setUserId(lat.getUserId());
		latd.setStartDate(lat.getStartDate());
		latd.setResult(lat.getResult());
		latd.setTryData(lat.getTryData());
		latd.setTryResultData(lat.getTryResultData());
		latd.setComments(lat.getComments());
		
		try {
			latd = LearningActivityTryDeletedLocalServiceUtil.addLearningActivityTryDeleted(latd);
			LearningActivityTryLocalServiceUtil.deleteLearningActivityTry(lat);
			actManAudit.setNumber(actManAudit.getNumber()+1);
			actManAudit = ActManAuditLocalServiceUtil.updateActManAudit(actManAudit);
			
		} catch (SystemException e) {
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
		}
		
		LearningActivityResult res= null;
		try {
			res = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(lat.getActId(), lat.getUserId());
		} catch (SystemException e) {
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
		}
		

		LearningActivity larn = null;
		try {
			larn = LearningActivityLocalServiceUtil.getLearningActivity(lat.getActId());
		} catch (PortalException e) {
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
		} catch (SystemException e) {
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
		}
		
		if(res!=null){
			res.setResult(0);
			res.setPassed(false);
			res.setEndDate(null);
			try {
				LearningActivityResultLocalServiceUtil.updateLearningActivityResult(res);
			} catch (SystemException e) {
				if(log.isInfoEnabled())log.info(e.getMessage());
				if(log.isDebugEnabled())e.printStackTrace();
			}
		}
		
		if(larn!=null&&larn.getWeightinmodule()>0){
			ModuleResult mr = null;
			try {
				mr = ModuleResultLocalServiceUtil.getByModuleAndUser(larn.getModuleId(), lat.getUserId());
			} catch (SystemException e) {
				if(log.isInfoEnabled())log.info(e.getMessage());
				if(log.isDebugEnabled())e.printStackTrace();
			}
			if(mr!=null){
				mr.setPassed(false);
				try {
					ModuleResultLocalServiceUtil.updateModuleResult(mr);
				} catch (SystemException e) {
					if(log.isInfoEnabled())log.info(e.getMessage());
					if(log.isDebugEnabled())e.printStackTrace();
				}
			}
		}
		
		return true;
	}
}
