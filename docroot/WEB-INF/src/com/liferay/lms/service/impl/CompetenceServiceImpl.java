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

import com.liferay.lms.model.Competence;

import com.liferay.lms.service.base.CompetenceServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the competence remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.CompetenceService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.CompetenceServiceBaseImpl
 * @see com.liferay.lms.service.CompetenceServiceUtil
 */
public class CompetenceServiceImpl extends CompetenceServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.CompetenceServiceUtil} to access the competence remote service.
	 */
	public java.util.List<Competence> getCompetencesOfGroup(long groupId) throws SystemException
	{
		return competencePersistence.filterFindByGroupId(groupId);
		
	
	}
	public java.util.List<Competence> getCompetencesOfGroup(long groupId, int start, int end) throws SystemException
	{
		return competencePersistence.filterFindByGroupId(groupId,start,end);
		
	
	}
	public int getCountCompetencesOfGroup(long groupId) throws SystemException
	{
		return competencePersistence.filterCountByGroupId(groupId);
		
	
	}
}