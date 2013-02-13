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
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.base.LearningActivityResultLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;


/**
 * The implementation of the learning activity result local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.LearningActivityResultLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.LearningActivityResultLocalServiceUtil} to access the learning activity result local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.LearningActivityResultLocalServiceBaseImpl
 * @see com.liferay.lms.service.LearningActivityResultLocalServiceUtil
 */
public class LearningActivityResultLocalServiceImpl
	extends LearningActivityResultLocalServiceBaseImpl {
	public LearningActivityResult update(LearningActivityTry learningActivityTry) throws SystemException, PortalException
	{
		LearningActivityResult learningActivityResult=null;
		long actId=learningActivityTry.getActId();
		long userId=learningActivityTry.getUserId();
		LearningActivity learningActivity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		if(!existsLearningActivityResult(actId, userId))
		{	
			learningActivityResult=
				learningActivityResultPersistence.create(counterLocalService.increment(
						LearningActivityResult.class.getName()));
			learningActivityResult.setStartDate(learningActivityTry.getStartDate());
			learningActivityResult.setActId(actId);
			learningActivityResult.setUserId(userId);
			learningActivityResult.setPassed(false);
		}
		else
		{
			learningActivityResult=learningActivityResultPersistence.fetchByact_user(actId, userId);
		}
		if(learningActivityTry.getEndDate()!=null)
		{
			learningActivityResult.setEndDate(learningActivityTry.getEndDate());
			if(learningActivityTry.getResult()>learningActivityResult.getResult())
			{
				
			
				learningActivityResult.setResult(learningActivityTry.getResult());
			}
			if(!learningActivityResult.getPassed())
			{
				if(learningActivityTry.getResult()>=learningActivity.getPasspuntuation())
				{
					learningActivityResult.setPassed(true);
				  
				}
			}
			
			learningActivityResult.setComments(learningActivityTry.getComments());
		}
		learningActivityResultPersistence.update(learningActivityResult, true);
		if(learningActivityResult.getPassed()==true)
		{
			ModuleResultLocalServiceUtil.update(learningActivityResult);
		}
		return learningActivityResult;
		
	}
	public boolean existsLearningActivityResult(long actId,long userId) throws SystemException
	{
		if(learningActivityResultPersistence.countByact_user(actId, userId)>0)
		{
			return true;
		}
		else
		{
		 
			return false;
		}
	}
	public boolean userPassed(long actId,long userId) throws SystemException
	{
		if(!existsLearningActivityResult(actId, userId))
		{
			return false;
		}
		else
		{
			return getByActIdAndUserId(actId, userId).isPassed();
		}
	}
	public long countPassed(long actId) throws SystemException
	{
		return learningActivityResultPersistence.countByap(actId, true);
	}
	public long countNotPassed(long actId) throws SystemException
	{
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityResult.class);
		Criterion criterion=PropertyFactoryUtil.forName("passed").eq(false);
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("actId").eq(actId);
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("endDate").isNotNull();
		dq.add(criterion);
		return learningActivityResultPersistence.countWithDynamicQuery(dq);
	}
	public Double avgResult(long actId) throws SystemException
	{
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityResult.class);
		Criterion criterion=PropertyFactoryUtil.forName("actId").eq(actId);
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("endDate").isNotNull();
		dq.add(criterion);
		dq.setProjection(ProjectionFactoryUtil.avg("result"));
		return (Double)(learningActivityResultPersistence.findWithDynamicQuery(dq).get(0));
	}
	public long countStarted(long actId) throws SystemException
	{
		return learningActivityResultPersistence.countByac(actId);
	}
	public double triesPerUser(long actId) throws SystemException
	{
		long tries=learningActivityTryPersistence.countByact(actId);
		long started=countStarted(actId);
		if(started==0)
		{
			return 0;
		}
		return ((double) tries)/((double) started);
	}
	public LearningActivityResult getByActIdAndUserId(long actId,long userId) throws SystemException
	{
		return learningActivityResultPersistence.fetchByact_user(actId, userId);
	}
}