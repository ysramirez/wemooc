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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Diego Renedo Delgado
 * @generated
 */
public class LtiItemSoap implements Serializable {
	public static LtiItemSoap toSoapModel(LtiItem model) {
		LtiItemSoap soapModel = new LtiItemSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setLtiItemId(model.getLtiItemId());
		soapModel.setActId(model.getActId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setUserId(model.getUserId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setUrl(model.getUrl());
		soapModel.setSecret(model.getSecret());
		soapModel.setRol(model.getRol());
		soapModel.setContenType(model.getContenType());
		soapModel.setIframe(model.getIframe());
		soapModel.setNote(model.getNote());

		return soapModel;
	}

	public static LtiItemSoap[] toSoapModels(LtiItem[] models) {
		LtiItemSoap[] soapModels = new LtiItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LtiItemSoap[][] toSoapModels(LtiItem[][] models) {
		LtiItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LtiItemSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LtiItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LtiItemSoap[] toSoapModels(List<LtiItem> models) {
		List<LtiItemSoap> soapModels = new ArrayList<LtiItemSoap>(models.size());

		for (LtiItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LtiItemSoap[soapModels.size()]);
	}

	public LtiItemSoap() {
	}

	public long getPrimaryKey() {
		return _ltiItemId;
	}

	public void setPrimaryKey(long pk) {
		setLtiItemId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLtiItemId() {
		return _ltiItemId;
	}

	public void setLtiItemId(long ltiItemId) {
		_ltiItemId = ltiItemId;
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

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
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

	public boolean getIframe() {
		return _iframe;
	}

	public boolean isIframe() {
		return _iframe;
	}

	public void setIframe(boolean iframe) {
		_iframe = iframe;
	}

	public int getNote() {
		return _note;
	}

	public void setNote(int note) {
		_note = note;
	}

	private String _uuid;
	private long _ltiItemId;
	private long _actId;
	private long _companyId;
	private long _groupId;
	private Date _createDate;
	private long _userId;
	private String _name;
	private String _description;
	private String _url;
	private String _secret;
	private String _rol;
	private String _contenType;
	private boolean _iframe;
	private int _note;
}