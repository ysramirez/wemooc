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

package com.liferay.lmssa.service.impl;

import java.util.List;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lmssa.model.ActManAudit;
import com.liferay.lmssa.service.persistence.ActManAuditUtil;
import com.liferay.lmssa.service.base.ActManAuditServiceBaseImpl;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The implementation of the act man audit remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lmssa.service.ActManAuditService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lmssa.service.base.ActManAuditServiceBaseImpl
 * @see com.liferay.lmssa.service.ActManAuditServiceUtil
 */
public class ActManAuditServiceImpl extends ActManAuditServiceBaseImpl {
	Log log = LogFactoryUtil.getLog(ActManAuditServiceImpl.class);
	
	
	public ActManAudit addActManAudit (ActManAudit valid) throws SystemException {

		ActManAudit fileobj = ActManAuditUtil.create(CounterLocalServiceUtil.increment(ActManAudit.class.getName()));
		
		fileobj.setCompanyId(valid.getCompanyId());
		fileobj.setGroupId(valid.getGroupId());
		fileobj.setUserId(valid.getUserId());
		fileobj.setUserId(valid.getUserId());
		fileobj.setCourseId(valid.getCourseId());
		fileobj.setUserId(valid.getUserId());
		fileobj.setClassName(valid.getClassName());
		fileobj.setStart(valid.getStart());
		fileobj.setEnd(valid.getEnd());
		fileobj.setState(valid.getState());
		fileobj.setUserTargetId(valid.getUserTargetId());
		fileobj.setActId(valid.getActId());

		ActManAudit returned = ActManAuditUtil.update(fileobj, false);

	    return returned;
	}
	
	public List<ActManAudit> findBycompanyId(long companyId, int start, int end){
		try {
			return ActManAuditUtil.findBycompanyId(companyId, start, end);
		} catch (SystemException e) {
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
			return null;
		}
	}
	
	public int countBycompanyId(long companyId){
	    try {
			return ActManAuditUtil.countBycompanyId(companyId);
		} catch (SystemException e) {
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
			return 0;
		}
	}
}