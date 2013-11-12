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

import com.liferay.lms.lti.service.PadGroupLocalServiceUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Diego Renedo Delgado
 */
public class PadGroupClp extends BaseModelImpl<PadGroup> implements PadGroup {
	public PadGroupClp() {
	}

	public Class<?> getModelClass() {
		return PadGroup.class;
	}

	public String getModelClassName() {
		return PadGroup.class.getName();
	}

	public long getPrimaryKey() {
		return _padGroupId;
	}

	public void setPrimaryKey(long primaryKey) {
		setPadGroupId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_padGroupId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
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

	@Override
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

	public long getPadGroupId() {
		return _padGroupId;
	}

	public void setPadGroupId(long padGroupId) {
		_padGroupId = padGroupId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_actId = actId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public String getSecret() {
		return _secret;
	}

	public void setSecret(String secret) {
		_secret = secret;
	}

	public String getRol() {
		return _rol;
	}

	public void setRol(String rol) {
		_rol = rol;
	}

	public String getContenType() {
		return _contenType;
	}

	public void setContenType(String contenType) {
		_contenType = contenType;
	}

	public BaseModel<?> getPadGroupRemoteModel() {
		return _padGroupRemoteModel;
	}

	public void setPadGroupRemoteModel(BaseModel<?> padGroupRemoteModel) {
		_padGroupRemoteModel = padGroupRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			PadGroupLocalServiceUtil.addPadGroup(this);
		}
		else {
			PadGroupLocalServiceUtil.updatePadGroup(this);
		}
	}

	@Override
	public PadGroup toEscapedModel() {
		return (PadGroup)Proxy.newProxyInstance(PadGroup.class.getClassLoader(),
			new Class[] { PadGroup.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		PadGroupClp clone = new PadGroupClp();

		clone.setPadGroupId(getPadGroupId());
		clone.setActId(getActId());
		clone.setCompanyId(getCompanyId());
		clone.setGroupId(getGroupId());
		clone.setCreateDate(getCreateDate());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setUrl(getUrl());
		clone.setSecret(getSecret());
		clone.setRol(getRol());
		clone.setContenType(getContenType());

		return clone;
	}

	public int compareTo(PadGroup padGroup) {
		long primaryKey = padGroup.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		PadGroupClp padGroup = null;

		try {
			padGroup = (PadGroupClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = padGroup.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{padGroupId=");
		sb.append(getPadGroupId());
		sb.append(", actId=");
		sb.append(getActId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", url=");
		sb.append(getUrl());
		sb.append(", secret=");
		sb.append(getSecret());
		sb.append(", rol=");
		sb.append(getRol());
		sb.append(", contenType=");
		sb.append(getContenType());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(37);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.lti.model.PadGroup");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>padGroupId</column-name><column-value><![CDATA[");
		sb.append(getPadGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actId</column-name><column-value><![CDATA[");
		sb.append(getActId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>url</column-name><column-value><![CDATA[");
		sb.append(getUrl());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>secret</column-name><column-value><![CDATA[");
		sb.append(getSecret());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>rol</column-name><column-value><![CDATA[");
		sb.append(getRol());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>contenType</column-name><column-value><![CDATA[");
		sb.append(getContenType());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _padGroupId;
	private long _actId;
	private long _companyId;
	private long _groupId;
	private Date _createDate;
	private String _name;
	private String _description;
	private String _url;
	private String _secret;
	private String _rol;
	private String _contenType;
	private BaseModel<?> _padGroupRemoteModel;
}