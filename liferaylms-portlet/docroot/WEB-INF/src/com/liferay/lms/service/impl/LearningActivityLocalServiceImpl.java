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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.base.LearningActivityLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

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
		Course course=courseLocalService.getCourseByGroupCreatedId(larn.getGroupId());
		if(course.isClosed())
		{
			return true;
		}
		if(larn.getModuleId()>0&&moduleLocalService.isLocked(larn.getModuleId(), userId))
		{
			return true;
		}
		if((larn.getEnddate()!=null&&larn.getEnddate().before(now)) ||(larn.getStartdate()!=null&&larn.getStartdate().after(now)))
		{
			return true;
		}

		if(larn.getPrecedence()!=0)
		{
			return !LearningActivityResultLocalServiceUtil.userPassed(larn.getPrecedence(), userId);
		}
		return false;
	}
	@Override
	public LearningActivity addLearningActivity(LearningActivity learningActivity,ServiceContext serviceContext) throws SystemException, PortalException {

		LearningActivity retorno=this.addLearningActivity(learningActivity.getTitle(),
				learningActivity.getDescription(), learningActivity.getCreateDate(),
				learningActivity.getStartdate(), learningActivity.getEnddate(), learningActivity.getTypeId(),
				learningActivity.getTries(), learningActivity.getPasspuntuation(), learningActivity.getModuleId(), learningActivity.getExtracontent(),
				learningActivity.getFeedbackCorrect(), learningActivity.getFeedbackNoCorrect(), serviceContext);
		//retorno.setPrecedence(learningActivity.setPrecedence(precedence))
		retorno.setPriority(learningActivity.getPriority());
		retorno.setWeightinmodule(learningActivity.getWeightinmodule());
		learningActivityPersistence.update(retorno, true);

		//auditing
		AuditingLogFactory.audit(retorno.getCompanyId(), retorno.getGroupId(), LearningActivity.class.getName(), retorno.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.ADD, null);

		return retorno;
	}
	public LearningActivity addLearningActivity (String title, String description, 
			java.util.Date createDate,java.util.Date startDate,java.util.Date endDate,
			int typeId,long tries,int passpuntuation,long moduleId, String extracontent,
			String feedbackCorrect, String feedbackNoCorrect,ServiceContext serviceContext)
					throws SystemException, 
					PortalException {

		long userId=serviceContext.getUserId();
		LearningActivity larn = learningActivityPersistence.create(counterLocalService.increment(LearningActivity.class.getName()));
		larn.setCompanyId(serviceContext.getCompanyId());
		larn.setGroupId(serviceContext.getScopeGroupId());
		larn.setUserId(userId);

		larn.setUserName(userLocalService.getUser(userId).getFullName());
		larn.setGroupId(serviceContext.getScopeGroupId());
		larn.setDescription(description);
		larn.setTypeId(typeId);
		larn.setTitle(title);
		larn.setStartdate(startDate);
		larn.setCreateDate(new java.util.Date(System.currentTimeMillis()));
		larn.setModifiedDate(new java.util.Date(System.currentTimeMillis()));
		larn.setEnddate(endDate);
		larn.setTries(tries);
		larn.setPasspuntuation(passpuntuation);
		larn.setStatus(WorkflowConstants.STATUS_APPROVED);
		larn.setModuleId(moduleId);
		larn.setExtracontent(extracontent);
		larn.setPriority(larn.getActId());
		larn.setFeedbackCorrect(feedbackCorrect);
		larn.setFeedbackNoCorrect(feedbackNoCorrect);
		learningActivityPersistence.update(larn, true);

		resourceLocalService.addModelResources(larn, serviceContext);

		assetEntryLocalService.updateEntry(
				userId, larn.getGroupId(), LearningActivity.class.getName(),
				larn.getActId(), larn.getUuid(),typeId, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(), true, null, null,
				new java.util.Date(System.currentTimeMillis()), null,
				ContentTypes.TEXT_HTML, 
				larn.getTitle().length()<255 ? larn.getTitle():larn.getTitle(Locale.getDefault()),
						null, larn.getDescription(serviceContext.getLocale()),null, null, 0, 0,
						null, false);

		socialActivityLocalService.addUniqueActivity(
				larn.getUserId(), larn.getGroupId(),
				LearningActivity.class.getName(), larn.getActId(),
				0, StringPool.BLANK, 0);
		//auditing
		AuditingLogFactory.audit(larn.getCompanyId(), larn.getGroupId(), LearningActivity.class.getName(), larn.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.ADD, null);

		return larn;

	}

	public LearningActivity modLearningActivity (long actId,String title, String description, java.util.Date createDate,java.util.Date startDate,java.util.Date endDate, int typeId,long tries,int passpuntuation,long moduleId,
			String extracontent, String feedbackCorrect, String feedbackNoCorrect,ServiceContext serviceContext)
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
		larn.setExtracontent(extracontent);
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
					ContentTypes.TEXT_HTML, larn.getTitle(), null, larn.getDescription(serviceContext.getLocale()),null, null, 0, 0,
					null, false);
			SocialActivityLocalServiceUtil.addActivity(
					larn.getUserId(), larn.getGroupId(),
					LearningActivity.class.getName(), larn.getActId(),
					1, StringPool.BLANK, 0);
		}
		catch(Exception e)
		{
		}

		//auditing
		AuditingLogFactory.audit(larn.getCompanyId(), larn.getGroupId(), LearningActivity.class.getName(), larn.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.UPDATE, null);

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
		SocialActivityLocalServiceUtil.addActivity(
				larn.getUserId(), larn.getGroupId(),
				LearningActivity.class.getName(), larn.getActId(),
				1, StringPool.BLANK, 0);

		//auditing
		AuditingLogFactory.audit(larn.getCompanyId(), larn.getGroupId(), LearningActivity.class.getName(), larn.getPrimaryKey(), serviceContext.getUserId(), AuditConstants.UPDATE, null);

		return larn;

	}

	public LearningActivity modLearningActivity (LearningActivity larn) throws SystemException, PortalException {

		learningActivityPersistence.update(larn, false);

		return larn;
	}

	public java.util.List<LearningActivity> getLearningActivitiesOfGroup(long groupId) throws SystemException
	{
		return learningActivityPersistence.findByg(groupId);
	}
	public long countLearningActivitiesOfGroup(long groupId) throws SystemException
	{
		return learningActivityPersistence.countByg(groupId);
	}
	public java.util.List<LearningActivity> getLearningActivitiesOfGroupAndType(long groupId,int typeId) throws SystemException
	{
		return learningActivityPersistence.findByg_t(groupId, typeId);
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
		SocialActivityLocalServiceUtil.addActivity(
				lernact.getUserId(), lernact.getGroupId(),
				LearningActivity.class.getName(), lernact.getActId(),
				2, StringPool.BLANK, 0);
	}

	public LearningActivity getPreviusLearningActivity(long actId) throws SystemException
	{
		LearningActivity larn=learningActivityPersistence.fetchByPrimaryKey(actId);
		return getPreviusLearningActivity(larn);


	}
	public LearningActivity getPreviusLearningActivity(LearningActivity larn) throws SystemException {
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader");  
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivity.class, classLoader);
		Criterion criterion=PropertyFactoryUtil.forName("priority").lt(larn.getPriority());
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("moduleId").eq(larn.getModuleId());
		dq.add(criterion);
		Order createOrder=OrderFactoryUtil.getOrderFactory().desc("priority");
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

	public void moveActivity(long actId, long previusAct, long nextAct) throws SystemException {
		LearningActivity actualAct = (actId>0)?learningActivityPersistence.fetchByPrimaryKey(actId):null;
		LearningActivity finalPrevAct = (previusAct>0)?learningActivityPersistence.fetchByPrimaryKey(previusAct):null;
		LearningActivity finalNextAct = (nextAct>0)?learningActivityPersistence.fetchByPrimaryKey(nextAct):null;

		//Elemento subido
		if(finalNextAct!=null && actualAct.getPriority() > finalNextAct.getPriority()){
			LearningActivity prevAct = getPreviusLearningActivity(actualAct);
			while(prevAct != null && actualAct.getPriority() > finalNextAct.getPriority()){
				goUpLearningActivity(actId);
				actualAct = learningActivityPersistence.fetchByPrimaryKey(actId);
				prevAct = getPreviusLearningActivity(actualAct);
			}
		}else{//Elemento bajado
			LearningActivity nexAct = getNextLearningActivity(actualAct);
			while (nexAct != null && actualAct.getPriority() < finalPrevAct.getPriority()){
				goDownLearningActivity(actId);
				actualAct = learningActivityPersistence.fetchByPrimaryKey(actId);
				nexAct = getNextLearningActivity(actualAct);
			}
		}

	}

	public LearningActivity getNextLearningActivity(long actId ) throws SystemException
	{
		LearningActivity larn=learningActivityPersistence.fetchByPrimaryKey(actId);
		return getNextLearningActivity(larn);

	}
	public LearningActivity getNextLearningActivity(LearningActivity larn) throws SystemException {
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), "portletClassLoader"); 
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivity.class, classLoader);
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

	public String getExtraContentValue(long actId, String key) throws SystemException{

		try {
			LearningActivity activity = learningActivityPersistence.fetchByPrimaryKey(actId);

			HashMap<String, String> hashMap = new HashMap<String, String>();

			if(activity != null){

				hashMap = convertXMLExtraContentToHashMap(actId);
				//Para evitar que retorne null si no existe la clave.
				if(hashMap.containsKey(key)){
					return hashMap.get(key);
				}
				else{
					return "";
				}
			}
		} catch (Exception e) {
		}

		return "";
	}


	public void setExtraContentValue(long actId, String name, String val) throws SystemException{

		try {
			LearningActivity activity = learningActivityPersistence.fetchByPrimaryKey(actId);

			HashMap<String, String> hashMap = new HashMap<String, String>();

			if(activity != null){
				hashMap = convertXMLExtraContentToHashMap(actId);
				hashMap.put(name, val);
			}

			saveHashMapToXMLExtraContent(actId, hashMap);

		} catch (PortalException e) {
		}
	}

	public HashMap<String, String> convertXMLExtraContentToHashMap(long actId) throws SystemException, PortalException 
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String xml ="";

		try {			
			LearningActivity activity = learningActivityPersistence.fetchByPrimaryKey(actId);

			if(activity != null  && !activity.getExtracontent().equals("")){
				xml = activity.getExtracontent();
			}
			else{
				return hashMap;
			}
			Document document;

			document = SAXReaderUtil.read(xml);
			Element rootElement = document.getRootElement();

			for(Element key:rootElement.elements()){

				if(key.getName().equals("document")){
					hashMap.put(key.getName(), key.attributeValue("id") );
				}else{
					hashMap.put(key.getName(), key.getText());
				}

			}

		} catch (DocumentException e) {
		}

		return hashMap;
	}

	@SuppressWarnings("rawtypes")
	public void saveHashMapToXMLExtraContent(long actId, HashMap<String, String> map) throws SystemException, PortalException 
	{
		try {
			LearningActivity activity = learningActivityPersistence.fetchByPrimaryKey(actId);

			if(activity != null  && !map.isEmpty()){

				//Element resultadosXML=SAXReaderUtil.createElement("p2p");
				Element resultadosXML=SAXReaderUtil.createElement(getNameLearningActivity(activity.getTypeId()));
				Document resultadosXMLDoc=SAXReaderUtil.createDocument(resultadosXML);

				Iterator it = map.entrySet().iterator();

				while (it.hasNext()) {
					Map.Entry e = (Map.Entry)it.next();
					Element eleXML=SAXReaderUtil.createElement(String.valueOf(e.getKey()));
					if(e.getKey().equals("document")){
						eleXML.addAttribute("id", String.valueOf(e.getValue()));
					}else{
						eleXML.addText(String.valueOf(e.getValue()));
					}
					resultadosXML.add(eleXML);
				}
				activity.setExtracontent(resultadosXMLDoc.formattedString());
				learningActivityPersistence.update(activity, true);
			}

		} catch (Exception e) {
		}
	}

	public boolean isLearningActivityDeleteTries(long typeId){
		LearningActivityTypeRegistry learningActivityTypeRegistry = null;
		try {
			learningActivityTypeRegistry = new LearningActivityTypeRegistry();
		} catch (SystemException e) {
			e.printStackTrace();
		}

		if(learningActivityTypeRegistry!=null){
			if(learningActivityTypeRegistry.getLearningActivityType(typeId)!=null){
				return learningActivityTypeRegistry.getLearningActivityType(typeId).hasDeleteTries();
			}
		}
		return false;
	}

	private String getNameLearningActivity(int type) throws SystemException{
		String res = "other";

		switch(type){
		//test
		case 0: res = "test"; break;
		//activity undefined
		case 1: res = "activity"; break;
		//resource
		case 2: res = "multimediaentry"; break;
		//taskp2p
		case 3: res = "p2p"; break;
		//survey
		case 4: res = "survey"; break;
		//offline
		case 5: res = "offline"; break;
		//online
		case 6: res = "online"; break;
		//resource internal
		case 7: res = "resourceInternal"; break;
		}

		return res;
	}

}