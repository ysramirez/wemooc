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

package com.liferay.lmssa.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.lmssa.service.http.ActManAuditServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lmssa.service.http.ActManAuditServiceSoap
 * @generated
 */
public class ActManAuditSoap implements Serializable {
	public static ActManAuditSoap toSoapModel(ActManAudit model) {
		ActManAuditSoap soapModel = new ActManAuditSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setActManAuditId(model.getActManAuditId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCourseId(model.getCourseId());
		soapModel.setClassName(model.getClassName());
		soapModel.setStart(model.getStart());
		soapModel.setEnd(model.getEnd());
		soapModel.setState(model.getState());
		soapModel.setNumber(model.getNumber());

		return soapModel;
	}

	public static ActManAuditSoap[] toSoapModels(ActManAudit[] models) {
		ActManAuditSoap[] soapModels = new ActManAuditSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ActManAuditSoap[][] toSoapModels(ActManAudit[][] models) {
		ActManAuditSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ActManAuditSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ActManAuditSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ActManAuditSoap[] toSoapModels(List<ActManAudit> models) {
		List<ActManAuditSoap> soapModels = new ArrayList<ActManAuditSoap>(models.size());

		for (ActManAudit model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ActManAuditSoap[soapModels.size()]);
	}

	public ActManAuditSoap() {
	}

	public long getPrimaryKey() {
		return _actManAuditId;
	}

	public void setPrimaryKey(long pk) {
		setActManAuditId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getActManAuditId() {
		return _actManAuditId;
	}

	public void setActManAuditId(long actManAuditId) {
		_actManAuditId = actManAuditId;
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

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getCourseId() {
		return _courseId;
	}

	public void setCourseId(long courseId) {
		_courseId = courseId;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public Date getStart() {
		return _start;
	}

	public void setStart(Date start) {
		_start = start;
	}

	public Date getEnd() {
		return _end;
	}

	public void setEnd(Date end) {
		_end = end;
	}

	public String getState() {
		return _state;
	}

	public void setState(String state) {
		_state = state;
	}

	public long getNumber() {
		return _number;
	}

	public void setNumber(long number) {
		_number = number;
	}

	private String _uuid;
	private long _actManAuditId;
	private long _companyId;
	private long _groupId;
	private long _userId;
	private long _courseId;
	private String _className;
	private Date _start;
	private Date _end;
	private String _state;
	private long _number;
}