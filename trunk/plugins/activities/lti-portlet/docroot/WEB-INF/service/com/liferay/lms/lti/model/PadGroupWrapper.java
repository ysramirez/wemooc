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
 * This class is a wrapper for {@link PadGroup}.
 * </p>
 *
 * @author    Diego Renedo Delgado
 * @see       PadGroup
 * @generated
 */
public class PadGroupWrapper implements PadGroup, ModelWrapper<PadGroup> {
	public PadGroupWrapper(PadGroup padGroup) {
		_padGroup = padGroup;
	}

	public Class<?> getModelClass() {
		return PadGroup.class;
	}

	public String getModelClassName() {
		return PadGroup.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("padGroupId", getPadGroupId());
		attributes.put("actId", getActId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("createDate", getCreateDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("url", getUrl());
		attributes.put("secret", getSecret());
		attributes.put("rol", getRol());
		attributes.put("contenType", getContenType());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long padGroupId = (Long)attributes.get("padGroupId");

		if (padGroupId != null) {
			setPadGroupId(padGroupId);
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
	}

	/**
	* Returns the primary key of this pad group.
	*
	* @return the primary key of this pad group
	*/
	public long getPrimaryKey() {
		return _padGroup.getPrimaryKey();
	}

	/**
	* Sets the primary key of this pad group.
	*
	* @param primaryKey the primary key of this pad group
	*/
	public void setPrimaryKey(long primaryKey) {
		_padGroup.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the pad group ID of this pad group.
	*
	* @return the pad group ID of this pad group
	*/
	public long getPadGroupId() {
		return _padGroup.getPadGroupId();
	}

	/**
	* Sets the pad group ID of this pad group.
	*
	* @param padGroupId the pad group ID of this pad group
	*/
	public void setPadGroupId(long padGroupId) {
		_padGroup.setPadGroupId(padGroupId);
	}

	/**
	* Returns the act ID of this pad group.
	*
	* @return the act ID of this pad group
	*/
	public long getActId() {
		return _padGroup.getActId();
	}

	/**
	* Sets the act ID of this pad group.
	*
	* @param actId the act ID of this pad group
	*/
	public void setActId(long actId) {
		_padGroup.setActId(actId);
	}

	/**
	* Returns the company ID of this pad group.
	*
	* @return the company ID of this pad group
	*/
	public long getCompanyId() {
		return _padGroup.getCompanyId();
	}

	/**
	* Sets the company ID of this pad group.
	*
	* @param companyId the company ID of this pad group
	*/
	public void setCompanyId(long companyId) {
		_padGroup.setCompanyId(companyId);
	}

	/**
	* Returns the group ID of this pad group.
	*
	* @return the group ID of this pad group
	*/
	public long getGroupId() {
		return _padGroup.getGroupId();
	}

	/**
	* Sets the group ID of this pad group.
	*
	* @param groupId the group ID of this pad group
	*/
	public void setGroupId(long groupId) {
		_padGroup.setGroupId(groupId);
	}

	/**
	* Returns the create date of this pad group.
	*
	* @return the create date of this pad group
	*/
	public java.util.Date getCreateDate() {
		return _padGroup.getCreateDate();
	}

	/**
	* Sets the create date of this pad group.
	*
	* @param createDate the create date of this pad group
	*/
	public void setCreateDate(java.util.Date createDate) {
		_padGroup.setCreateDate(createDate);
	}

	/**
	* Returns the name of this pad group.
	*
	* @return the name of this pad group
	*/
	public java.lang.String getName() {
		return _padGroup.getName();
	}

	/**
	* Sets the name of this pad group.
	*
	* @param name the name of this pad group
	*/
	public void setName(java.lang.String name) {
		_padGroup.setName(name);
	}

	/**
	* Returns the description of this pad group.
	*
	* @return the description of this pad group
	*/
	public java.lang.String getDescription() {
		return _padGroup.getDescription();
	}

	/**
	* Sets the description of this pad group.
	*
	* @param description the description of this pad group
	*/
	public void setDescription(java.lang.String description) {
		_padGroup.setDescription(description);
	}

	/**
	* Returns the url of this pad group.
	*
	* @return the url of this pad group
	*/
	public java.lang.String getUrl() {
		return _padGroup.getUrl();
	}

	/**
	* Sets the url of this pad group.
	*
	* @param url the url of this pad group
	*/
	public void setUrl(java.lang.String url) {
		_padGroup.setUrl(url);
	}

	/**
	* Returns the secret of this pad group.
	*
	* @return the secret of this pad group
	*/
	public java.lang.String getSecret() {
		return _padGroup.getSecret();
	}

	/**
	* Sets the secret of this pad group.
	*
	* @param secret the secret of this pad group
	*/
	public void setSecret(java.lang.String secret) {
		_padGroup.setSecret(secret);
	}

	/**
	* Returns the rol of this pad group.
	*
	* @return the rol of this pad group
	*/
	public java.lang.String getRol() {
		return _padGroup.getRol();
	}

	/**
	* Sets the rol of this pad group.
	*
	* @param rol the rol of this pad group
	*/
	public void setRol(java.lang.String rol) {
		_padGroup.setRol(rol);
	}

	/**
	* Returns the conten type of this pad group.
	*
	* @return the conten type of this pad group
	*/
	public java.lang.String getContenType() {
		return _padGroup.getContenType();
	}

	/**
	* Sets the conten type of this pad group.
	*
	* @param contenType the conten type of this pad group
	*/
	public void setContenType(java.lang.String contenType) {
		_padGroup.setContenType(contenType);
	}

	public boolean isNew() {
		return _padGroup.isNew();
	}

	public void setNew(boolean n) {
		_padGroup.setNew(n);
	}

	public boolean isCachedModel() {
		return _padGroup.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_padGroup.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _padGroup.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _padGroup.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_padGroup.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _padGroup.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_padGroup.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PadGroupWrapper((PadGroup)_padGroup.clone());
	}

	public int compareTo(PadGroup padGroup) {
		return _padGroup.compareTo(padGroup);
	}

	@Override
	public int hashCode() {
		return _padGroup.hashCode();
	}

	public com.liferay.portal.model.CacheModel<PadGroup> toCacheModel() {
		return _padGroup.toCacheModel();
	}

	public PadGroup toEscapedModel() {
		return new PadGroupWrapper(_padGroup.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _padGroup.toString();
	}

	public java.lang.String toXmlString() {
		return _padGroup.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_padGroup.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public PadGroup getWrappedPadGroup() {
		return _padGroup;
	}

	public PadGroup getWrappedModel() {
		return _padGroup;
	}

	public void resetOriginalValues() {
		_padGroup.resetOriginalValues();
	}

	private PadGroup _padGroup;
}