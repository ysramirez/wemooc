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

package com.liferay.lms.model.impl;

import com.liferay.lms.model.CheckP2pMailing;
import com.liferay.lms.model.CheckP2pMailingModel;
import com.liferay.lms.model.CheckP2pMailingSoap;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the CheckP2pMailing service. Represents a row in the &quot;Lms_CheckP2pMailing&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.lms.model.CheckP2pMailingModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CheckP2pMailingImpl}.
 * </p>
 *
 * @author TLS
 * @see CheckP2pMailingImpl
 * @see com.liferay.lms.model.CheckP2pMailing
 * @see com.liferay.lms.model.CheckP2pMailingModel
 * @generated
 */
public class CheckP2pMailingModelImpl extends BaseModelImpl<CheckP2pMailing>
	implements CheckP2pMailingModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a check p2p mailing model instance should use the {@link com.liferay.lms.model.CheckP2pMailing} interface instead.
	 */
	public static final String TABLE_NAME = "Lms_CheckP2pMailing";
	public static final Object[][] TABLE_COLUMNS = {
			{ "checkP2pId", Types.BIGINT },
			{ "actId", Types.BIGINT },
			{ "date_", Types.TIMESTAMP }
		};
	public static final String TABLE_SQL_CREATE = "create table Lms_CheckP2pMailing (checkP2pId LONG not null primary key,actId LONG,date_ DATE null)";
	public static final String TABLE_SQL_DROP = "drop table Lms_CheckP2pMailing";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.lms.model.CheckP2pMailing"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.lms.model.CheckP2pMailing"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.lms.model.CheckP2pMailing"),
			true);
	public static long ACTID_COLUMN_BITMASK = 1L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CheckP2pMailing toModel(CheckP2pMailingSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		CheckP2pMailing model = new CheckP2pMailingImpl();

		model.setCheckP2pId(soapModel.getCheckP2pId());
		model.setActId(soapModel.getActId());
		model.setDate(soapModel.getDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<CheckP2pMailing> toModels(
		CheckP2pMailingSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<CheckP2pMailing> models = new ArrayList<CheckP2pMailing>(soapModels.length);

		for (CheckP2pMailingSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.com.liferay.lms.model.CheckP2pMailing"));

	public CheckP2pMailingModelImpl() {
	}

	public long getPrimaryKey() {
		return _checkP2pId;
	}

	public void setPrimaryKey(long primaryKey) {
		setCheckP2pId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_checkP2pId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public Class<?> getModelClass() {
		return CheckP2pMailing.class;
	}

	public String getModelClassName() {
		return CheckP2pMailing.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("checkP2pId", getCheckP2pId());
		attributes.put("actId", getActId());
		attributes.put("date", getDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long checkP2pId = (Long)attributes.get("checkP2pId");

		if (checkP2pId != null) {
			setCheckP2pId(checkP2pId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Date date = (Date)attributes.get("date");

		if (date != null) {
			setDate(date);
		}
	}

	public long getCheckP2pId() {
		return _checkP2pId;
	}

	public void setCheckP2pId(long checkP2pId) {
		_checkP2pId = checkP2pId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_columnBitmask |= ACTID_COLUMN_BITMASK;

		if (!_setOriginalActId) {
			_setOriginalActId = true;

			_originalActId = _actId;
		}

		_actId = actId;
	}

	public long getOriginalActId() {
		return _originalActId;
	}

	public Date getDate() {
		return _date;
	}

	public void setDate(Date date) {
		_date = date;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
			CheckP2pMailing.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CheckP2pMailing toEscapedModel() {
		if (_escapedModelProxy == null) {
			_escapedModelProxy = (CheckP2pMailing)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelProxyInterfaces,
					new AutoEscapeBeanHandler(this));
		}

		return _escapedModelProxy;
	}

	@Override
	public Object clone() {
		CheckP2pMailingImpl checkP2pMailingImpl = new CheckP2pMailingImpl();

		checkP2pMailingImpl.setCheckP2pId(getCheckP2pId());
		checkP2pMailingImpl.setActId(getActId());
		checkP2pMailingImpl.setDate(getDate());

		checkP2pMailingImpl.resetOriginalValues();

		return checkP2pMailingImpl;
	}

	public int compareTo(CheckP2pMailing checkP2pMailing) {
		long primaryKey = checkP2pMailing.getPrimaryKey();

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

		CheckP2pMailing checkP2pMailing = null;

		try {
			checkP2pMailing = (CheckP2pMailing)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = checkP2pMailing.getPrimaryKey();

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
	public void resetOriginalValues() {
		CheckP2pMailingModelImpl checkP2pMailingModelImpl = this;

		checkP2pMailingModelImpl._originalActId = checkP2pMailingModelImpl._actId;

		checkP2pMailingModelImpl._setOriginalActId = false;

		checkP2pMailingModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CheckP2pMailing> toCacheModel() {
		CheckP2pMailingCacheModel checkP2pMailingCacheModel = new CheckP2pMailingCacheModel();

		checkP2pMailingCacheModel.checkP2pId = getCheckP2pId();

		checkP2pMailingCacheModel.actId = getActId();

		Date date = getDate();

		if (date != null) {
			checkP2pMailingCacheModel.date = date.getTime();
		}
		else {
			checkP2pMailingCacheModel.date = Long.MIN_VALUE;
		}

		return checkP2pMailingCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{checkP2pId=");
		sb.append(getCheckP2pId());
		sb.append(", actId=");
		sb.append(getActId());
		sb.append(", date=");
		sb.append(getDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.CheckP2pMailing");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>checkP2pId</column-name><column-value><![CDATA[");
		sb.append(getCheckP2pId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actId</column-name><column-value><![CDATA[");
		sb.append(getActId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>date</column-name><column-value><![CDATA[");
		sb.append(getDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = CheckP2pMailing.class.getClassLoader();
	private static Class<?>[] _escapedModelProxyInterfaces = new Class[] {
			CheckP2pMailing.class
		};
	private long _checkP2pId;
	private long _actId;
	private long _originalActId;
	private boolean _setOriginalActId;
	private Date _date;
	private long _columnBitmask;
	private CheckP2pMailing _escapedModelProxy;
}