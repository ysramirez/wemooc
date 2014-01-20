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

package com.liferay.lmssa.model.impl;

import com.liferay.lmssa.model.LearningActivityTryDeleted;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing LearningActivityTryDeleted in entity cache.
 *
 * @author TLS
 * @see LearningActivityTryDeleted
 * @generated
 */
public class LearningActivityTryDeletedCacheModel implements CacheModel<LearningActivityTryDeleted>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", latDelId=");
		sb.append(latDelId);
		sb.append(", actManAuditId=");
		sb.append(actManAuditId);
		sb.append(", latId=");
		sb.append(latId);
		sb.append(", actId=");
		sb.append(actId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", result=");
		sb.append(result);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", tryData=");
		sb.append(tryData);
		sb.append(", tryResultData=");
		sb.append(tryResultData);
		sb.append(", comments=");
		sb.append(comments);
		sb.append("}");

		return sb.toString();
	}

	public LearningActivityTryDeleted toEntityModel() {
		LearningActivityTryDeletedImpl learningActivityTryDeletedImpl = new LearningActivityTryDeletedImpl();

		if (uuid == null) {
			learningActivityTryDeletedImpl.setUuid(StringPool.BLANK);
		}
		else {
			learningActivityTryDeletedImpl.setUuid(uuid);
		}

		learningActivityTryDeletedImpl.setLatDelId(latDelId);
		learningActivityTryDeletedImpl.setActManAuditId(actManAuditId);
		learningActivityTryDeletedImpl.setLatId(latId);
		learningActivityTryDeletedImpl.setActId(actId);
		learningActivityTryDeletedImpl.setUserId(userId);

		if (startDate == Long.MIN_VALUE) {
			learningActivityTryDeletedImpl.setStartDate(null);
		}
		else {
			learningActivityTryDeletedImpl.setStartDate(new Date(startDate));
		}

		learningActivityTryDeletedImpl.setResult(result);

		if (endDate == Long.MIN_VALUE) {
			learningActivityTryDeletedImpl.setEndDate(null);
		}
		else {
			learningActivityTryDeletedImpl.setEndDate(new Date(endDate));
		}

		if (tryData == null) {
			learningActivityTryDeletedImpl.setTryData(StringPool.BLANK);
		}
		else {
			learningActivityTryDeletedImpl.setTryData(tryData);
		}

		if (tryResultData == null) {
			learningActivityTryDeletedImpl.setTryResultData(StringPool.BLANK);
		}
		else {
			learningActivityTryDeletedImpl.setTryResultData(tryResultData);
		}

		if (comments == null) {
			learningActivityTryDeletedImpl.setComments(StringPool.BLANK);
		}
		else {
			learningActivityTryDeletedImpl.setComments(comments);
		}

		learningActivityTryDeletedImpl.resetOriginalValues();

		return learningActivityTryDeletedImpl;
	}

	public String uuid;
	public long latDelId;
	public long actManAuditId;
	public long latId;
	public long actId;
	public long userId;
	public long startDate;
	public long result;
	public long endDate;
	public String tryData;
	public String tryResultData;
	public String comments;
}