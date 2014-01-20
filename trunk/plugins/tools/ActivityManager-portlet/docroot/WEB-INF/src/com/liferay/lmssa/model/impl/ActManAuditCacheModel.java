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

import com.liferay.lmssa.model.ActManAudit;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing ActManAudit in entity cache.
 *
 * @author TLS
 * @see ActManAudit
 * @generated
 */
public class ActManAuditCacheModel implements CacheModel<ActManAudit>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", actManAuditId=");
		sb.append(actManAuditId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", courseId=");
		sb.append(courseId);
		sb.append(", className=");
		sb.append(className);
		sb.append(", start=");
		sb.append(start);
		sb.append(", end=");
		sb.append(end);
		sb.append(", state=");
		sb.append(state);
		sb.append(", number=");
		sb.append(number);
		sb.append("}");

		return sb.toString();
	}

	public ActManAudit toEntityModel() {
		ActManAuditImpl actManAuditImpl = new ActManAuditImpl();

		if (uuid == null) {
			actManAuditImpl.setUuid(StringPool.BLANK);
		}
		else {
			actManAuditImpl.setUuid(uuid);
		}

		actManAuditImpl.setActManAuditId(actManAuditId);
		actManAuditImpl.setCompanyId(companyId);
		actManAuditImpl.setGroupId(groupId);
		actManAuditImpl.setUserId(userId);
		actManAuditImpl.setCourseId(courseId);

		if (className == null) {
			actManAuditImpl.setClassName(StringPool.BLANK);
		}
		else {
			actManAuditImpl.setClassName(className);
		}

		if (start == Long.MIN_VALUE) {
			actManAuditImpl.setStart(null);
		}
		else {
			actManAuditImpl.setStart(new Date(start));
		}

		if (end == Long.MIN_VALUE) {
			actManAuditImpl.setEnd(null);
		}
		else {
			actManAuditImpl.setEnd(new Date(end));
		}

		if (state == null) {
			actManAuditImpl.setState(StringPool.BLANK);
		}
		else {
			actManAuditImpl.setState(state);
		}

		actManAuditImpl.setNumber(number);

		actManAuditImpl.resetOriginalValues();

		return actManAuditImpl;
	}

	public String uuid;
	public long actManAuditId;
	public long companyId;
	public long groupId;
	public long userId;
	public long courseId;
	public String className;
	public long start;
	public long end;
	public String state;
	public long number;
}