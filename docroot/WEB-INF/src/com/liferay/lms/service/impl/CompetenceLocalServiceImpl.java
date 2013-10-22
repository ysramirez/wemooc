/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.Locale;

import com.liferay.lms.model.Competence;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LmsPrefs;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.base.CompetenceLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivitySettingLocalServiceUtil;

/**
 * The implementation of the competence local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CompetenceLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CompetenceLocalServiceBaseImpl
 * @see com.liferay.lms.service.CompetenceLocalServiceUtil
 */
public class CompetenceLocalServiceImpl extends CompetenceLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CompetenceLocalServiceUtil} to access the competence local service.
	 */
	public Competence addCompetence (String title, String description,ServiceContext serviceContext)
			throws SystemException, PortalException 
			{
		long userId=serviceContext.getUserId();
		System.out.println(1);
		Competence competence = competencePersistence.create(counterLocalService.increment(Competence.class.getName()));	
		System.out.println(2);
			competence.setCompanyId(serviceContext.getCompanyId());
			competence.setGroupId(serviceContext.getScopeGroupId());
			competence.setUserId(userId);
			competence.setDescription(description,serviceContext.getLocale());
			competence.setTitle(title,serviceContext.getLocale());
			competence.setStatus(WorkflowConstants.STATUS_APPROVED);
			competence.setExpandoBridgeAttributes(serviceContext);
			competencePersistence.update(competence, true);
			System.out.println(3);
			try
			{
			resourceLocalService.addResources(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
					userId,Competence.class.getName(), competence.getPrimaryKey(), false,true, true);
			System.out.println(4);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			AssetEntryLocalServiceUtil.updateEntry(userId, competence.getGroupId(), Competence.class.getName(),
					competence.getCompetenceId(), competence.getUuid(),0, serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames(), true, null, null,new java.util.Date(System.currentTimeMillis()), null,
					ContentTypes.TEXT_HTML, competence.getTitle(),
					competence.getDescription(serviceContext.getLocale()), competence.getDescription(serviceContext.getLocale()),
					null, null, 0, 0,null, false);
			System.out.println(5);
			//creating group
		return competence;
		
		
	}
	public Competence modCompetence (Competence competence,
			ServiceContext serviceContext)
			throws SystemException, PortalException {
			competence.setExpandoBridgeAttributes(serviceContext);
			Locale locale=new Locale(serviceContext.getLanguageId());
			competencePersistence.update(competence, true);
			long userId=serviceContext.getUserId();
			AssetEntryLocalServiceUtil.updateEntry(
					userId, competence.getGroupId(), Course.class.getName(),
					competence.getCompetenceId(), competence.getUuid(),0, serviceContext.getAssetCategoryIds(),
					serviceContext.getAssetTagNames(), true, null, null,
					new java.util.Date(System.currentTimeMillis()), null,
					ContentTypes.TEXT_HTML, competence.getTitle(), competence.getDescription(locale), competence.getDescription(locale), null, null, 0, 0,
					null, false);
			return competence;
		
			}
	@Override
	public Competence deleteCompetence (long competenceId) throws SystemException,
	PortalException {
	return this.deleteCompetence(this.getCompetence(competenceId));
	}
	@Override
	public Competence deleteCompetence (Competence competence) throws SystemException 
	{
		try {
			AssetEntryLocalServiceUtil.deleteEntry(Competence.class.getName(), competence.getPrimaryKey());
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return competence;
	}
}