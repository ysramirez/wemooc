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

package com.liferay.lms.lti.model.impl;

import com.liferay.lms.lti.model.LtiItem;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing LtiItem in entity cache.
 *
 * @author Diego Renedo Delgado
 * @see LtiItem
 * @generated
 */
public class LtiItemCacheModel implements CacheModel<LtiItem>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", ltiItemId=");
		sb.append(ltiItemId);
		sb.append(", actId=");
		sb.append(actId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", url=");
		sb.append(url);
		sb.append(", secret=");
		sb.append(secret);
		sb.append(", rol=");
		sb.append(rol);
		sb.append(", contenType=");
		sb.append(contenType);
		sb.append(", iframe=");
		sb.append(iframe);
		sb.append(", note=");
		sb.append(note);
		sb.append("}");

		return sb.toString();
	}

	public LtiItem toEntityModel() {
		LtiItemImpl ltiItemImpl = new LtiItemImpl();

		if (uuid == null) {
			ltiItemImpl.setUuid(StringPool.BLANK);
		}
		else {
			ltiItemImpl.setUuid(uuid);
		}

		ltiItemImpl.setLtiItemId(ltiItemId);
		ltiItemImpl.setActId(actId);
		ltiItemImpl.setCompanyId(companyId);
		ltiItemImpl.setGroupId(groupId);

		if (createDate == Long.MIN_VALUE) {
			ltiItemImpl.setCreateDate(null);
		}
		else {
			ltiItemImpl.setCreateDate(new Date(createDate));
		}

		ltiItemImpl.setUserId(userId);

		if (name == null) {
			ltiItemImpl.setName(StringPool.BLANK);
		}
		else {
			ltiItemImpl.setName(name);
		}

		if (description == null) {
			ltiItemImpl.setDescription(StringPool.BLANK);
		}
		else {
			ltiItemImpl.setDescription(description);
		}

		if (url == null) {
			ltiItemImpl.setUrl(StringPool.BLANK);
		}
		else {
			ltiItemImpl.setUrl(url);
		}

		if (secret == null) {
			ltiItemImpl.setSecret(StringPool.BLANK);
		}
		else {
			ltiItemImpl.setSecret(secret);
		}

		if (rol == null) {
			ltiItemImpl.setRol(StringPool.BLANK);
		}
		else {
			ltiItemImpl.setRol(rol);
		}

		if (contenType == null) {
			ltiItemImpl.setContenType(StringPool.BLANK);
		}
		else {
			ltiItemImpl.setContenType(contenType);
		}

		ltiItemImpl.setIframe(iframe);
		ltiItemImpl.setNote(note);

		ltiItemImpl.resetOriginalValues();

		return ltiItemImpl;
	}

	public String uuid;
	public long ltiItemId;
	public long actId;
	public long companyId;
	public long groupId;
	public long createDate;
	public long userId;
	public String name;
	public String description;
	public String url;
	public String secret;
	public String rol;
	public String contenType;
	public boolean iframe;
	public int note;
}