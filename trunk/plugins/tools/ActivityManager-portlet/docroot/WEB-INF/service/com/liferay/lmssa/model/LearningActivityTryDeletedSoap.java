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
 * This class is used by SOAP remote services, specifically {@link com.liferay.lmssa.service.http.LearningActivityTryDeletedServiceSoap}.
 *
 * @author    TLS
 * @see       com.liferay.lmssa.service.http.LearningActivityTryDeletedServiceSoap
 * @generated
 */
public class LearningActivityTryDeletedSoap implements Serializable {
	public static LearningActivityTryDeletedSoap toSoapModel(
		LearningActivityTryDeleted model) {
		LearningActivityTryDeletedSoap soapModel = new LearningActivityTryDeletedSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setLatDelId(model.getLatDelId());
		soapModel.setActManAuditId(model.getActManAuditId());
		soapModel.setLatId(model.getLatId());
		soapModel.setActId(model.getActId());
		soapModel.setUserId(model.getUserId());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setResult(model.getResult());
		soapModel.setEndDate(model.getEndDate());
		soapModel.setTryData(model.getTryData());
		soapModel.setTryResultData(model.getTryResultData());
		soapModel.setComments(model.getComments());

		return soapModel;
	}

	public static LearningActivityTryDeletedSoap[] toSoapModels(
		LearningActivityTryDeleted[] models) {
		LearningActivityTryDeletedSoap[] soapModels = new LearningActivityTryDeletedSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LearningActivityTryDeletedSoap[][] toSoapModels(
		LearningActivityTryDeleted[][] models) {
		LearningActivityTryDeletedSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LearningActivityTryDeletedSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LearningActivityTryDeletedSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LearningActivityTryDeletedSoap[] toSoapModels(
		List<LearningActivityTryDeleted> models) {
		List<LearningActivityTryDeletedSoap> soapModels = new ArrayList<LearningActivityTryDeletedSoap>(models.size());

		for (LearningActivityTryDeleted model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LearningActivityTryDeletedSoap[soapModels.size()]);
	}

	public LearningActivityTryDeletedSoap() {
	}

	public long getPrimaryKey() {
		return _latDelId;
	}

	public void setPrimaryKey(long pk) {
		setLatDelId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLatDelId() {
		return _latDelId;
	}

	public void setLatDelId(long latDelId) {
		_latDelId = latDelId;
	}

	public long getActManAuditId() {
		return _actManAuditId;
	}

	public void setActManAuditId(long actManAuditId) {
		_actManAuditId = actManAuditId;
	}

	public long getLatId() {
		return _latId;
	}

	public void setLatId(long latId) {
		_latId = latId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_actId = actId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public long getResult() {
		return _result;
	}

	public void setResult(long result) {
		_result = result;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public String getTryData() {
		return _tryData;
	}

	public void setTryData(String tryData) {
		_tryData = tryData;
	}

	public String getTryResultData() {
		return _tryResultData;
	}

	public void setTryResultData(String tryResultData) {
		_tryResultData = tryResultData;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	private String _uuid;
	private long _latDelId;
	private long _actManAuditId;
	private long _latId;
	private long _actId;
	private long _userId;
	private Date _startDate;
	private long _result;
	private Date _endDate;
	private String _tryData;
	private String _tryResultData;
	private String _comments;
}