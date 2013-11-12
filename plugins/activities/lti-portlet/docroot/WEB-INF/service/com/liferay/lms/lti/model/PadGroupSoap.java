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
public class PadGroupSoap implements Serializable {
	public static PadGroupSoap toSoapModel(PadGroup model) {
		PadGroupSoap soapModel = new PadGroupSoap();

		soapModel.setPadGroupId(model.getPadGroupId());
		soapModel.setActId(model.getActId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setUrl(model.getUrl());
		soapModel.setSecret(model.getSecret());
		soapModel.setRol(model.getRol());
		soapModel.setContenType(model.getContenType());

		return soapModel;
	}

	public static PadGroupSoap[] toSoapModels(PadGroup[] models) {
		PadGroupSoap[] soapModels = new PadGroupSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PadGroupSoap[][] toSoapModels(PadGroup[][] models) {
		PadGroupSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PadGroupSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PadGroupSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PadGroupSoap[] toSoapModels(List<PadGroup> models) {
		List<PadGroupSoap> soapModels = new ArrayList<PadGroupSoap>(models.size());

		for (PadGroup model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PadGroupSoap[soapModels.size()]);
	}

	public PadGroupSoap() {
	}

	public long getPrimaryKey() {
		return _padGroupId;
	}

	public void setPrimaryKey(long pk) {
		setPadGroupId(pk);
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
}