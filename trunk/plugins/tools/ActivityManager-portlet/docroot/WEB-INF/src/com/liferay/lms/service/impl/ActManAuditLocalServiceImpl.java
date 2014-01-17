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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.model.ActManAudit;
import com.liferay.lms.service.base.ActManAuditLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.ActManAuditUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.tls.npa.lsb.tls.badge.model.Badge;
import com.tls.npa.lsb.tls.badge.service.persistence.BadgeUtil;

/**
 * The implementation of the act man audit local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.ActManAuditLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lms.service.base.ActManAuditLocalServiceBaseImpl
 * @see com.liferay.lms.service.ActManAuditLocalServiceUtil
 */
public class ActManAuditLocalServiceImpl extends ActManAuditLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.lms.service.ActManAuditLocalServiceUtil} to access the act man audit local service.
	 */

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
}