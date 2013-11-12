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

package com.liferay.lms.lti.service.impl;

import com.liferay.lms.lti.model.LtiItem;
import com.liferay.lms.lti.service.base.LtiItemLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The implementation of the lti item local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.lti.service.LtiItemLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Diego Renedo Delgado
 * @see com.liferay.lms.lti.service.base.LtiItemLocalServiceBaseImpl
 * @see com.liferay.lms.lti.service.LtiItemLocalServiceUtil
 */
public class LtiItemLocalServiceImpl extends LtiItemLocalServiceBaseImpl {
	private static Log log = LogFactoryUtil.getLog(LtiItemLocalServiceImpl.class);
	
	public LtiItem create(long actId,String url,String secret){
		LtiItem ltiItem = null;
		
		try {
			ltiItem = ltiItemPersistence.create(counterLocalService.increment(LtiItem.class.getName()));
		} catch (SystemException e) {
			if(log.isDebugEnabled())e.printStackTrace();
			if(log.isErrorEnabled())log.error(e.getMessage());
			return null;
		}
		
		ltiItem.setActId(actId);
		ltiItem.setUrl(url);
		ltiItem.setSecret(secret);
		
		try {
			return ltiItemPersistence.update(ltiItem, true);
		} catch (SystemException e) {
			if(log.isDebugEnabled())e.printStackTrace();
			if(log.isErrorEnabled())log.error(e.getMessage());
			return null;
		}
	}
	
	public LtiItem fetchByactId(long actId){
		try {
			return ltiItemPersistence.fetchByactId(actId);
		} catch (SystemException e) {
			return null;
		}
	}
}