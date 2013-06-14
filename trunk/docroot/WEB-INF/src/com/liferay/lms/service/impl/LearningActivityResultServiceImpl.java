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

import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.base.LearningActivityResultServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.model.User;

/**
 * The implementation of the learning activity result remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.LearningActivityResultService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.LearningActivityResultServiceUtil} to access the learning activity result remote service.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.LearningActivityResultServiceBaseImpl
 * @see com.liferay.lms.service.LearningActivityResultServiceUtil
 */
@JSONWebService(mode = JSONWebServiceMode.MANUAL)
public class LearningActivityResultServiceImpl
	extends LearningActivityResultServiceBaseImpl 
	{
	@JSONWebService
	public LearningActivityResult getByActId(long actId) throws PortalException, SystemException
	{
		User user=this.getUser();
		return learningActivityResultLocalService.getByActIdAndUserId(actId, user.getUserId());
	}
	@JSONWebService
	public LearningActivityResult getByActIdAndUser(long actId,String login) throws PortalException, SystemException
	{
		User user=this.getUser();
		return learningActivityResultLocalService.getByActIdAndUserId(actId, user.getUserId());
	}
	@JSONWebService
	public boolean userPassed(long actId) throws PortalException, SystemException
	{
		User user=this.getUser();
		return learningActivityResultLocalService.userPassed(actId, user.getUserId());
	}
	@JSONWebService
	public boolean userLoginPassed(long actId,String login) throws PortalException, SystemException
	{
		User user=this.getUser();
		return learningActivityResultLocalService.userPassed(actId, user.getUserId());
	}
	@JSONWebService
	public LearningActivityResult update(long latId, long result, String tryResultData) throws PortalException, SystemException
	{
		User user=this.getUser();
		return learningActivityResultLocalService.update(latId, result, tryResultData, user.getUserId());
	}
}