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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.lms.service.base.LearningActivityTryLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * The implementation of the learning activity try local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.LearningActivityTryLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.LearningActivityTryLocalServiceUtil} to access the learning activity try local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.LearningActivityTryLocalServiceBaseImpl
 * @see com.liferay.lms.service.LearningActivityTryLocalServiceUtil
 */
public class LearningActivityTryLocalServiceImpl
	extends LearningActivityTryLocalServiceBaseImpl {
	@Override
	public LearningActivityTry updateLearningActivityTry(
			LearningActivityTry learningActivityTry) throws SystemException {
		
		return updateLearningActivityTry(learningActivityTry,true);
	}
	public long getLearningActivityTryByActUserCount(long actId,long userId) throws SystemException
	{
		return learningActivityTryPersistence.countByact_u(actId, userId);
	}
	public void deleteUserTries(long actId,long userId) throws SystemException, PortalException
	{
		java.util.List<LearningActivityTry> userTries=learningActivityTryPersistence.findByact_u(actId, userId);
		LearningActivity larn=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		for(LearningActivityTry userTry:userTries)
		{
			this.deleteLearningActivityTry(userTry.getLatId());
		}
		if(learningActivityResultLocalService.existsLearningActivityResult(actId, userId))
		{
			LearningActivityResult res=learningActivityResultLocalService.getByActIdAndUserId(actId, userId);
			res.setResult(0);
			res.setPassed(false);
			res.setEndDate(null);
			learningActivityResultLocalService.updateLearningActivityResult(res);
			if(larn.getWeightinmodule()>0)
			{
				ModuleResult mr=ModuleResultLocalServiceUtil.getByModuleAndUser(larn.getModuleId(), userId);
				if(mr!=null)
				{
					mr.setPassed(false);
					ModuleResultLocalServiceUtil.updateModuleResult(mr);
				}
			}
		}
		
	}
	
	public java.util.List<LearningActivityTry> getLearningActivityTryByActUser(long actId,long userId) throws SystemException
	{
		return learningActivityTryPersistence.findByact_u(actId, userId);
	}
	@Override
	public LearningActivityTry updateLearningActivityTry(
			LearningActivityTry learningActivityTry, boolean merge)
			throws SystemException{
		try
		{
		if(learningActivityTry.getEndDate()!=null)
		{
			LearningActivityResultLocalServiceUtil.update(learningActivityTry)	;
		}
		return super.updateLearningActivityTry(learningActivityTry, merge);
	}
		catch(PortalException e)
		{
			throw new SystemException(e);
		}
	}

	public LearningActivityTry createLearningActivityTry(long actId,ServiceContext serviceContext) throws SystemException, PortalException
	{
		LearningActivityTry larnt =
			learningActivityTryPersistence.create(counterLocalService.increment(
					LearningActivityTry.class.getName()));
		larnt.setUserId(serviceContext.getUserId());
		larnt.setActId(actId);
		larnt.setStartDate(new java.util.Date(System.currentTimeMillis()));
		learningActivityTryPersistence.update(larnt, true);
		LearningActivityResultLocalServiceUtil.update(larnt);
		return larnt;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUsersByLearningActivity(long actId) throws SystemException, PortalException
	{ 			
		List<User> users = new ArrayList<User>();
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(LearningActivityTry.class)
					.add(PropertyFactoryUtil.forName("actId").eq(new Long(actId)));
					
		List<LearningActivityTry> activities = (List<LearningActivityTry>)learningActivityTryPersistence.findWithDynamicQuery(consulta);

		for(LearningActivityTry activity:activities){
			Long uId = activity.getUserId();
			User u = UserLocalServiceUtil.getUserById(uId.longValue());
			if(u!=null && !users.contains(u))
				users.add(u);
		}
		return users;		
	}
	
	@SuppressWarnings("unchecked")
	public LearningActivityTry getLastLearningActivityTryByActivityAndUser(long actId,long userId) throws SystemException, PortalException
	{ 			
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(LearningActivityTry.class)
					.add(PropertyFactoryUtil.forName("actId").eq(new Long(actId)))
					.add(PropertyFactoryUtil.forName("userId").eq(new Long(userId)))
					.addOrder(PropertyFactoryUtil.forName("endDate").desc());
					
		List<LearningActivityTry> activities = (List<LearningActivityTry>)learningActivityTryPersistence.findWithDynamicQuery(consulta);

		for(LearningActivityTry activity:activities){
			//Necesitamos la primera, que está ordenada por la última realizada.
			return activity;
		}
		return null;		
	}
	
	@SuppressWarnings("unchecked")
	public LearningActivityTry getLearningActivityTryNotFinishedByActUser(long actId,long userId) throws SystemException, PortalException
	{ 			
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(LearningActivityTry.class)
					.add(PropertyFactoryUtil.forName("actId").eq(new Long(actId)))
					.add(PropertyFactoryUtil.forName("userId").eq(new Long(userId)))
					.add(PropertyFactoryUtil.forName("endDate").isNull())
					.addOrder(PropertyFactoryUtil.forName("startDate").desc());
					
		List<LearningActivityTry> activities = (List<LearningActivityTry>)learningActivityTryPersistence.findWithDynamicQuery(consulta);

		for(LearningActivityTry activity:activities){
			//Necesitamos la primera, que está ordenada por la última realizada.
			return activity;
		}
		return null;		
	}
	
	public int getTriesCountByActivityAndUser(long actId,long userId) throws SystemException, PortalException
	{ 			
		return learningActivityTryPersistence.countByact_u(actId, userId);	
	}
	
	public HashMap<Long, Long> getMapTryResultData(long actId,long userId) throws SystemException, PortalException 
	{
		HashMap<Long, Long> answersMap = new HashMap<Long, Long>();
		LearningActivityTry actTry = getLastLearningActivityTryByActivityAndUser(actId, userId);
		String xml = actTry.getTryResultData();
		
		if(xml.equals(""))
			return answersMap;
			
		Document document;
		try {
			document = SAXReaderUtil.read(xml);
			Element rootElement = document.getRootElement();
			
			for(Element question:rootElement.elements("question")){
				for(Element answer:question.elements("answer")){
	    			answersMap.put(Long.valueOf(question.attributeValue("id")), Long.valueOf(answer.attributeValue("id"))) ;
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return answersMap;
	}
	
	@SuppressWarnings("unchecked")
	public boolean canUserDoANewTry(long actId,long userId) throws Exception{
		
		//Si ya ha pasado el test, no puede hacer más intentos.
		if(LearningActivityResultLocalServiceUtil.userPassed(actId, userId))
		{
			return false;
		}
		if(LearningActivityLocalServiceUtil.islocked(actId, userId))
		{
			return false;
		}
		
		//Mirar si los intentos que tiene son menores de los intentos posibles
		int userTries = getTriesCountByActivityAndUser(actId,userId);
		LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		Long activityTries=activity.getTries();
		if(activityTries==0)
		{
			return true;
		}
		
		//Mirar que alguno de sus intentos no se terminase.
		DynamicQuery consulta = DynamicQueryFactoryUtil.forClass(LearningActivityTry.class)
				.add(PropertyFactoryUtil.forName("actId").eq(new Long(actId)))
				.add(PropertyFactoryUtil.forName("userId").eq(new Long(userId)))
				.add(PropertyFactoryUtil.forName("endDate").isNull());
				
		long opened = learningActivityTryPersistence.countWithDynamicQuery(consulta);
			
		//Si tiene menos intentos de los que se puede hacer.
		return Long.valueOf(userTries-opened) < activityTries;
	}
}