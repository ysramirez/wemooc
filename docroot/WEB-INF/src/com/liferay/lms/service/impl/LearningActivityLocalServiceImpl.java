/**
 * 2012 TELEFONICA LEARNING SERVICES. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lms.service.impl;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.base.LearningActivityLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.service.ServiceContext;

/**
 * The implementation of the learning activity local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.LearningActivityLocalService} interface.
 * </p>
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.LearningActivityLocalServiceUtil} to access the learning activity local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.LearningActivityLocalServiceBaseImpl
 * @see com.liferay.lms.service.LearningActivityLocalServiceUtil
 */
public class LearningActivityLocalServiceImpl
	extends LearningActivityLocalServiceBaseImpl {
	public boolean islocked(long actId, long userId) throws Exception
	{
		LearningActivity larn =
			learningActivityPersistence.fetchByPrimaryKey(actId);
		java.util.Date now=new java.util.Date(System.currentTimeMillis());
		Module theModule=moduleLocalService.getModule(larn.getModuleId());
		if(moduleLocalService.isLocked(larn.getModuleId(), userId))
		{
			return true;
		}
		if(larn.getEnddate().before(now) ||larn.getStartdate().after(now))
		{
			return true;
		}
		
		if(larn.getPrecedence()!=0)
		{
			return !LearningActivityResultLocalServiceUtil.userPassed(larn.getPrecedence(), userId);
		}
		return false;
	}
	
	public LearningActivity addLearningActivity(
			LearningActivity learningActivity,ServiceContext serviceContext) throws SystemException, PortalException {
	
		LearningActivity retorno=this.addLearningActivity(learningActivity.getTitle(),
				learningActivity.getDescription(), learningActivity.getCreateDate(),
				learningActivity.getStartdate(), learningActivity.getEnddate(), learningActivity.getTypeId(),
				learningActivity.getTries(), learningActivity.getPasspuntuation(), learningActivity.getModuleId(), 
				learningActivity.getFeedbackCorrect(), learningActivity.getFeedbackNoCorrect(), serviceContext);
		//retorno.setPrecedence(learningActivity.setPrecedence(precedence))
		retorno.setPriority(learningActivity.getPriority());
		retorno.setWeightinmodule(learningActivity.getWeightinmodule());
		learningActivityPersistence.update(retorno, true);
		return retorno;
	}
	public LearningActivity addLearningActivity (String title, String description, 
			java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,
			int typeId,long tries,int passpuntuation,long moduleId,
			 String feedbackCorrect, String feedbackNoCorrect,ServiceContext serviceContext)
			throws SystemException, 
			PortalException {
		
		long userId=serviceContext.getUserId();
		LearningActivity larn =
				learningActivityPersistence.create(counterLocalService.increment(
						LearningActivity.class.getName()));
			larn.setCompanyId(serviceContext.getCompanyId());
			larn.setGroupId(serviceContext.getScopeGroupId());
			larn.setUserId(userId);
			larn.setCreateDate(serviceContext.getCreateDate());
			larn.setDescription(description);
			larn.setTypeId(typeId);
			larn.setTitle(title);
			larn.setStartdate(startDate);
			larn.setEnddate(endDate);
			larn.setTries(tries);
			larn.setPasspuntuation(passpuntuation);
			larn.setStatus(WorkflowConstants.STATUS_APPROVED);
			larn.setModuleId(moduleId);
			larn.setPriority(larn.getActId());
			larn.setFeedbackCorrect(feedbackCorrect);
			larn.setFeedbackNoCorrect(feedbackNoCorrect);
			learningActivityPersistence.update(larn, true);
			try
			{
			resourceLocalService.addResources(
					serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), userId,
			LearningActivity.class.getName(), larn.getPrimaryKey(), false,
			true, true);
			assetEntryLocalService.updateEntry(
					userId, larn.getGroupId(), LearningActivity.class.getName(),
					larn.getActId(), larn.getUuid(),typeId, serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames(), true, null, null,
					new java.util.Date(System.currentTimeMillis()), null,
					ContentTypes.TEXT_HTML, larn.getTitle(), null, larn.getDescription(),null, null, 0, 0,
					null, false);
			
					
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return larn;
			}
	public LearningActivity modLearningActivity (long actId,String title, String description, java.util.Date createDate,java.util.Date startDate,java.util.Date endDate, int typeId,long tries,int passpuntuation,long moduleId,
			 String feedbackCorrect, String feedbackNoCorrect,ServiceContext serviceContext)
			throws SystemException, 
			PortalException {
		
		long userId=serviceContext.getUserId();
		LearningActivity larn =this.getLearningActivity(actId);
			larn.setCompanyId(serviceContext.getCompanyId());
			larn.setGroupId(serviceContext.getScopeGroupId());
			larn.setUserId(userId);
			larn.setCreateDate(serviceContext.getCreateDate());
			larn.setDescription(description);
			larn.setTitle(title);
			larn.setStartdate(startDate);
			larn.setEnddate(endDate);
			larn.setTries(tries);
			larn.setPasspuntuation(passpuntuation);
			larn.setStatus(WorkflowConstants.STATUS_APPROVED);
			larn.setModuleId(moduleId);
			larn.setFeedbackCorrect(feedbackCorrect);
			larn.setFeedbackNoCorrect(feedbackNoCorrect);
			learningActivityPersistence.update(larn, true);
			try
			{
				
				assetEntryLocalService.updateEntry(
						userId, larn.getGroupId(), LearningActivity.class.getName(),
						larn.getActId(), larn.getUuid(),larn.getTypeId(), serviceContext.getAssetCategoryIds(),
						serviceContext.getAssetTagNames(), true, null, null,
						new java.util.Date(System.currentTimeMillis()), null,
						ContentTypes.TEXT_HTML, larn.getTitle(), null, larn.getDescription(),null, null, 0, 0,
						null, false);
			
					
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return larn;
			}
	public LearningActivity modLearningActivity (LearningActivity larn, 
			ServiceContext serviceContext)
			throws SystemException, PortalException {
			
			learningActivityPersistence.update(larn, false);
			long userId=serviceContext.getUserId();
	
			assetEntryLocalService.updateEntry(
					userId, larn.getGroupId(), LearningActivity.class.getName(),
					larn.getActId(), larn.getUuid(),larn.getTypeId(), serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames(), true, null, null, 
					new java.util.Date(System.currentTimeMillis()), null,
					ContentTypes.TEXT_HTML, larn.getTitle(), null, larn.getDescription(),null, null, 0, 0,
					null, false);
	
					
			return larn;
		
			}
	
	public LearningActivity modLearningActivity (LearningActivity larn) throws SystemException, PortalException {

		learningActivityPersistence.update(larn, false);

		return larn;
	}
	
	public java.util.List<LearningActivity> getLearningActivitiesOfGroup(long groupId) throws SystemException
	{
		return learningActivityPersistence.findByg(groupId, 0, 1000);
	}
	
	public java.util.List<LearningActivity> getLearningActivitiesOfModule(long moduleId) throws SystemException
	{
		return learningActivityPersistence.findBym(moduleId, 0, 1000);
	}
	public java.util.List<Long> getLearningActivityIdsOfModule(long moduleId) throws SystemException
	{
		java.util.List<LearningActivity>larnacts= learningActivityPersistence.findBym(moduleId, 0, 1000);
		java.util.List<Long> result=new java.util.ArrayList<Long>();
		for(LearningActivity larn:larnacts)
		{
			result.add(larn.getActId());
		}
		return result;
	}
	public void deleteLearningactivity (LearningActivity lernact) throws SystemException,
	PortalException {
	long companyId = lernact.getCompanyId();
	assetEntryLocalService.deleteEntry(LearningActivity.class.getName(),lernact.getActId());
	resourceLocalService.deleteResource(
	companyId, LearningActivity.class.getName(),
	ResourceConstants.SCOPE_INDIVIDUAL, lernact.getPrimaryKey());
	assetEntryLocalService.deleteEntry(
			LearningActivity.class.getName(), lernact.getActId());
	learningActivityPersistence.remove(lernact);
	}
	
	public LearningActivity getPreviusLearningActivity(long actId) throws SystemException
	{
		LearningActivity larn=learningActivityPersistence.fetchByPrimaryKey(actId);
		return getPreviusLearningActivity(larn);
		
		
	}
	public LearningActivity getPreviusLearningActivity(LearningActivity larn) throws SystemException {
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivity.class);
		Criterion criterion=PropertyFactoryUtil.forName("priority").lt(larn.getPriority());
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("moduleId").eq(larn.getModuleId());
		dq.add(criterion);
		Order createOrder=OrderFactoryUtil.getOrderFactory().desc("priority");
		dq.addOrder(createOrder);

		java.util.List<LearningActivity> larnsp=(java.util.List<LearningActivity>)learningActivityLocalService.dynamicQuery(dq,0,1);
		if(larnsp!=null&& larnsp.size()>0)
		{
			return larnsp.get(0);
		}
		else
		{
			return null;
		}
	}
	public void goUpLearningActivity(long actId ) throws SystemException
	{
		LearningActivity previusActivity=getPreviusLearningActivity(actId);
		if(previusActivity!=null)
		{
			
			LearningActivity larn=learningActivityPersistence.fetchByPrimaryKey(actId);
			long priority=larn.getPriority();
			larn.setPriority(previusActivity.getPriority());
			previusActivity.setPriority(priority);
			learningActivityPersistence.update(larn, true);
			learningActivityPersistence.update(previusActivity, true);
		}
		
	}
	public void goDownLearningActivity(long actId ) throws SystemException
	{
		LearningActivity previusActivity=getNextLearningActivity(actId);
		if(previusActivity!=null)
		{
			
			LearningActivity larn=learningActivityPersistence.fetchByPrimaryKey(actId);
			long priority=larn.getPriority();
			larn.setPriority(previusActivity.getPriority());
			previusActivity.setPriority(priority);
			learningActivityPersistence.update(larn, true);
			learningActivityPersistence.update(previusActivity, true);
		}
		
	}
	public LearningActivity getNextLearningActivity(long actId ) throws SystemException
	{
		LearningActivity larn=learningActivityPersistence.fetchByPrimaryKey(actId);
		return getNextLearningActivity(larn);
		
	}
	public LearningActivity getNextLearningActivity(LearningActivity larn) throws SystemException {
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivity.class);
		Criterion criterion=PropertyFactoryUtil.forName("priority").gt(larn.getPriority());
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("moduleId").eq(larn.getModuleId());
		dq.add(criterion);
		Order createOrder=OrderFactoryUtil.getOrderFactory().asc("priority");
		dq.addOrder(createOrder);

		@SuppressWarnings("unchecked")
		java.util.List<LearningActivity> larnsp=(java.util.List<LearningActivity>)learningActivityLocalService.dynamicQuery(dq,0,1);
		if(larnsp!=null&& larnsp.size()>0)
		{
			return larnsp.get(0);
		}
		else
		{
			return null;
		}
	}
	public void deleteLearningactivity (long actId) throws SystemException,
	PortalException {
	this.deleteLearningactivity(LearningActivityLocalServiceUtil.getLearningActivity(actId));
	}
	

}