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

package com.liferay.lms.lti.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LtiItem}.
 * </p>
 *
 * @author    Diego Renedo Delgado
 * @see       LtiItem
 * @generated
 */
public class LtiItemWrapper implements LtiItem, ModelWrapper<LtiItem> {
	public LtiItemWrapper(LtiItem ltiItem) {
		_ltiItem = ltiItem;
	}

	public Class<?> getModelClass() {
		return LtiItem.class;
	}

	public String getModelClassName() {
		return LtiItem.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("ltiItemId", getLtiItemId());
		attributes.put("actId", getActId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("createDate", getCreateDate());
		attributes.put("userId", getUserId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("url", getUrl());
		attributes.put("secret", getSecret());
		attributes.put("rol", getRol());
		attributes.put("contenType", getContenType());
		attributes.put("iframe", getIframe());
		attributes.put("note", getNote());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long ltiItemId = (Long)attributes.get("ltiItemId");

		if (ltiItemId != null) {
			setLtiItemId(ltiItemId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		String secret = (String)attributes.get("secret");

		if (secret != null) {
			setSecret(secret);
		}

		String rol = (String)attributes.get("rol");

		if (rol != null) {
			setRol(rol);
		}

		String contenType = (String)attributes.get("contenType");

		if (contenType != null) {
			setContenType(contenType);
		}

		Boolean iframe = (Boolean)attributes.get("iframe");

		if (iframe != null) {
			setIframe(iframe);
		}

		Integer note = (Integer)attributes.get("note");

		if (note != null) {
			setNote(note);
		}
	}

	/**
	* Returns the primary key of this lti item.
	*
	* @return the primary key of this lti item
	*/
	public long getPrimaryKey() {
		return _ltiItem.getPrimaryKey();
	}

	/**
	* Sets the primary key of this lti item.
	*
	* @param primaryKey the primary key of this lti item
	*/
	public void setPrimaryKey(long primaryKey) {
		_ltiItem.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this lti item.
	*
	* @return the uuid of this lti item
	*/
	public java.lang.String getUuid() {
		return _ltiItem.getUuid();
	}

	/**
	* Sets the uuid of this lti item.
	*
	* @param uuid the uuid of this lti item
	*/
	public void setUuid(java.lang.String uuid) {
		_ltiItem.setUuid(uuid);
	}

	/**
	* Returns the lti item ID of this lti item.
	*
	* @return the lti item ID of this lti item
	*/
	public long getLtiItemId() {
		return _ltiItem.getLtiItemId();
	}

	/**
	* Sets the lti item ID of this lti item.
	*
	* @param ltiItemId the lti item ID of this lti item
	*/
	public void setLtiItemId(long ltiItemId) {
		_ltiItem.setLtiItemId(ltiItemId);
	}

	/**
	* Returns the act ID of this lti item.
	*
	* @return the act ID of this lti item
	*/
	public long getActId() {
		return _ltiItem.getActId();
	}

	/**
	* Sets the act ID of this lti item.
	*
	* @param actId the act ID of this lti item
	*/
	public void setActId(long actId) {
		_ltiItem.setActId(actId);
	}

	/**
	* Returns the company ID of this lti item.
	*
	* @return the company ID of this lti item
	*/
	public long getCompanyId() {
		return _ltiItem.getCompanyId();
	}

	/**
	* Sets the company ID of this lti item.
	*
	* @param companyId the company ID of this lti item
	*/
	public void setCompanyId(long companyId) {
		_ltiItem.setCompanyId(companyId);
	}

	/**
	* Returns the group ID of this lti item.
	*
	* @return the group ID of this lti item
	*/
	public long getGroupId() {
		return _ltiItem.getGroupId();
	}

	/**
	* Sets the group ID of this lti item.
	*
	* @param groupId the group ID of this lti item
	*/
	public void setGroupId(long groupId) {
		_ltiItem.setGroupId(groupId);
	}

	/**
	* Returns the create date of this lti item.
	*
	* @return the create date of this lti item
	*/
	public java.util.Date getCreateDate() {
		return _ltiItem.getCreateDate();
	}

	/**
	* Sets the create date of this lti item.
	*
	* @param createDate the create date of this lti item
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ltiItem.setCreateDate(createDate);
	}

	/**
	* Returns the user ID of this lti item.
	*
	* @return the user ID of this lti item
	*/
	public long getUserId() {
		return _ltiItem.getUserId();
	}

	/**
	* Sets the user ID of this lti item.
	*
	* @param userId the user ID of this lti item
	*/
	public void setUserId(long userId) {
		_ltiItem.setUserId(userId);
	}

	/**
	* Returns the user uuid of this lti item.
	*
	* @return the user uuid of this lti item
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ltiItem.getUserUuid();
	}

	/**
	* Sets the user uuid of this lti item.
	*
	* @param userUuid the user uuid of this lti item
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_ltiItem.setUserUuid(userUuid);
	}

	/**
	* Returns the name of this lti item.
	*
	* @return the name of this lti item
	*/
	public java.lang.String getName() {
		return _ltiItem.getName();
	}

	/**
	* Sets the name of this lti item.
	*
	* @param name the name of this lti item
	*/
	public void setName(java.lang.String name) {
		_ltiItem.setName(name);
	}

	/**
	* Returns the description of this lti item.
	*
	* @return the description of this lti item
	*/
	public java.lang.String getDescription() {
		return _ltiItem.getDescription();
	}

	/**
	* Sets the description of this lti item.
	*
	* @param description the description of this lti item
	*/
	public void setDescription(java.lang.String description) {
		_ltiItem.setDescription(description);
	}

	/**
	* Returns the url of this lti item.
	*
	* @return the url of this lti item
	*/
	public java.lang.String getUrl() {
		return _ltiItem.getUrl();
	}

	/**
	* Sets the url of this lti item.
	*
	* @param url the url of this lti item
	*/
	public void setUrl(java.lang.String url) {
		_ltiItem.setUrl(url);
	}

	/**
	* Returns the secret of this lti item.
	*
	* @return the secret of this lti item
	*/
	public java.lang.String getSecret() {
		return _ltiItem.getSecret();
	}

	/**
	* Sets the secret of this lti item.
	*
	* @param secret the secret of this lti item
	*/
	public void setSecret(java.lang.String secret) {
		_ltiItem.setSecret(secret);
	}

	/**
	* Returns the rol of this lti item.
	*
	* @return the rol of this lti item
	*/
	public java.lang.String getRol() {
		return _ltiItem.getRol();
	}

	/**
	* Sets the rol of this lti item.
	*
	* @param rol the rol of this lti item
	*/
	public void setRol(java.lang.String rol) {
		_ltiItem.setRol(rol);
	}

	/**
	* Returns the conten type of this lti item.
	*
	* @return the conten type of this lti item
	*/
	public java.lang.String getContenType() {
		return _ltiItem.getContenType();
	}

	/**
	* Sets the conten type of this lti item.
	*
	* @param contenType the conten type of this lti item
	*/
	public void setContenType(java.lang.String contenType) {
		_ltiItem.setContenType(contenType);
	}

	/**
	* Returns the iframe of this lti item.
	*
	* @return the iframe of this lti item
	*/
	public boolean getIframe() {
		return _ltiItem.getIframe();
	}

	/**
	* Returns <code>true</code> if this lti item is iframe.
	*
	* @return <code>true</code> if this lti item is iframe; <code>false</code> otherwise
	*/
	public boolean isIframe() {
		return _ltiItem.isIframe();
	}

	/**
	* Sets whether this lti item is iframe.
	*
	* @param iframe the iframe of this lti item
	*/
	public void setIframe(boolean iframe) {
		_ltiItem.setIframe(iframe);
	}

	/**
	* Returns the note of this lti item.
	*
	* @return the note of this lti item
	*/
	public int getNote() {
		return _ltiItem.getNote();
	}

	/**
	* Sets the note of this lti item.
	*
	* @param note the note of this lti item
	*/
	public void setNote(int note) {
		_ltiItem.setNote(note);
	}

	public boolean isNew() {
		return _ltiItem.isNew();
	}

	public void setNew(boolean n) {
		_ltiItem.setNew(n);
	}

	public boolean isCachedModel() {
		return _ltiItem.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ltiItem.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ltiItem.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ltiItem.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ltiItem.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ltiItem.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ltiItem.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new LtiItemWrapper((LtiItem)_ltiItem.clone());
	}

	public int compareTo(com.liferay.lms.lti.model.LtiItem ltiItem) {
		return _ltiItem.compareTo(ltiItem);
	}

	@Override
	public int hashCode() {
		return _ltiItem.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.lti.model.LtiItem> toCacheModel() {
		return _ltiItem.toCacheModel();
	}

	public com.liferay.lms.lti.model.LtiItem toEscapedModel() {
		return new LtiItemWrapper(_ltiItem.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ltiItem.toString();
	}

	public java.lang.String toXmlString() {
		return _ltiItem.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_ltiItem.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public LtiItem getWrappedLtiItem() {
		return _ltiItem;
	}

	public LtiItem getWrappedModel() {
		return _ltiItem;
	}

	public void resetOriginalValues() {
		_ltiItem.resetOriginalValues();
	}

	private LtiItem _ltiItem;
}