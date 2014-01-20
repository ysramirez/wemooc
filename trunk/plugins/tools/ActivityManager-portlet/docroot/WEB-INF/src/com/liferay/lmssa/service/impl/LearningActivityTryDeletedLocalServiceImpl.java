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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lmssa.model.LearningActivityTryDeleted;
import com.liferay.lmssa.service.base.LearningActivityTryDeletedLocalServiceBaseImpl;
import com.liferay.lmssa.service.persistence.LearningActivityTryDeletedUtil;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the learning activity try deleted local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lmssa.service.LearningActivityTryDeletedLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see com.liferay.lmssa.service.base.LearningActivityTryDeletedLocalServiceBaseImpl
 * @see com.liferay.lmssa.service.LearningActivityTryDeletedLocalServiceUtil
 */
public class LearningActivityTryDeletedLocalServiceImpl	extends LearningActivityTryDeletedLocalServiceBaseImpl {
	
	public LearningActivityTryDeleted addLearningActivityTryDeleted (LearningActivityTryDeleted valid) throws SystemException {

		LearningActivityTryDeleted fileobj = LearningActivityTryDeletedUtil.create(CounterLocalServiceUtil.increment(LearningActivityTryDeleted.class.getName()));

		fileobj.setActId(valid.getActId());
		fileobj.setActManAuditId(valid.getActManAuditId());
		fileobj.setEndDate(valid.getEndDate());
		fileobj.setLatId(valid.getLatId());
		fileobj.setUserId(valid.getUserId());
		fileobj.setStartDate(valid.getStartDate());
		fileobj.setResult(valid.getResult());
		fileobj.setTryData(valid.getTryData());
		fileobj.setTryResultData(valid.getTryResultData());
		fileobj.setComments(valid.getComments());

		LearningActivityTryDeleted returned = LearningActivityTryDeletedUtil.update(fileobj, false);

	    return returned;
	}
}