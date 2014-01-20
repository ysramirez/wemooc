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

import com.liferay.lmssa.service.ActManAuditLocalServiceUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TLS
 */
public class ActManAuditClp extends BaseModelImpl<ActManAudit>
	implements ActManAudit {
	public ActManAuditClp() {
	}

	public Class<?> getModelClass() {
		return ActManAudit.class;
	}

	public String getModelClassName() {
		return ActManAudit.class.getName();
	}

	public long getPrimaryKey() {
		return _actManAuditId;
	}

	public void setPrimaryKey(long primaryKey) {
		setActManAuditId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_actManAuditId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("actManAuditId", getActManAuditId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("courseId", getCourseId());
		attributes.put("className", getClassName());
		attributes.put("start", getStart());
		attributes.put("end", getEnd());
		attributes.put("state", getState());
		attributes.put("userTargetId", getUserTargetId());
		attributes.put("actId", getActId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long actManAuditId = (Long)attributes.get("actManAuditId");

		if (actManAuditId != null) {
			setActManAuditId(actManAuditId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long courseId = (Long)attributes.get("courseId");

		if (courseId != null) {
			setCourseId(courseId);
		}

		String className = (String)attributes.get("className");

		if (className != null) {
			setClassName(className);
		}

		Date start = (Date)attributes.get("start");

		if (start != null) {
			setStart(start);
		}

		Date end = (Date)attributes.get("end");

		if (end != null) {
			setEnd(end);
		}

		String state = (String)attributes.get("state");

		if (state != null) {
			setState(state);
		}

		Long userTargetId = (Long)attributes.get("userTargetId");

		if (userTargetId != null) {
			setUserTargetId(userTargetId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}
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

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
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

	public long getUserTargetId() {
		return _userTargetId;
	}

	public void setUserTargetId(long userTargetId) {
		_userTargetId = userTargetId;
	}

	public long getActId() {
		return _actId;
	}

	public void setActId(long actId) {
		_actId = actId;
	}

	public BaseModel<?> getActManAuditRemoteModel() {
		return _actManAuditRemoteModel;
	}

	public void setActManAuditRemoteModel(BaseModel<?> actManAuditRemoteModel) {
		_actManAuditRemoteModel = actManAuditRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			ActManAuditLocalServiceUtil.addActManAudit(this);
		}
		else {
			ActManAuditLocalServiceUtil.updateActManAudit(this);
		}
	}

	@Override
	public ActManAudit toEscapedModel() {
		return (ActManAudit)Proxy.newProxyInstance(ActManAudit.class.getClassLoader(),
			new Class[] { ActManAudit.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		ActManAuditClp clone = new ActManAuditClp();

		clone.setUuid(getUuid());
		clone.setActManAuditId(getActManAuditId());
		clone.setCompanyId(getCompanyId());
		clone.setGroupId(getGroupId());
		clone.setUserId(getUserId());
		clone.setCourseId(getCourseId());
		clone.setClassName(getClassName());
		clone.setStart(getStart());
		clone.setEnd(getEnd());
		clone.setState(getState());
		clone.setUserTargetId(getUserTargetId());
		clone.setActId(getActId());

		return clone;
	}

	public int compareTo(ActManAudit actManAudit) {
		long primaryKey = actManAudit.getPrimaryKey();

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

		ActManAuditClp actManAudit = null;

		try {
			actManAudit = (ActManAuditClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = actManAudit.getPrimaryKey();

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
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", actManAuditId=");
		sb.append(getActManAuditId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", courseId=");
		sb.append(getCourseId());
		sb.append(", className=");
		sb.append(getClassName());
		sb.append(", start=");
		sb.append(getStart());
		sb.append(", end=");
		sb.append(getEnd());
		sb.append(", state=");
		sb.append(getState());
		sb.append(", userTargetId=");
		sb.append(getUserTargetId());
		sb.append(", actId=");
		sb.append(getActId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(40);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lmssa.model.ActManAudit");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actManAuditId</column-name><column-value><![CDATA[");
		sb.append(getActManAuditId());
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
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>courseId</column-name><column-value><![CDATA[");
		sb.append(getCourseId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>className</column-name><column-value><![CDATA[");
		sb.append(getClassName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>start</column-name><column-value><![CDATA[");
		sb.append(getStart());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>end</column-name><column-value><![CDATA[");
		sb.append(getEnd());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>state</column-name><column-value><![CDATA[");
		sb.append(getState());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userTargetId</column-name><column-value><![CDATA[");
		sb.append(getUserTargetId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>actId</column-name><column-value><![CDATA[");
		sb.append(getActId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private long _actManAuditId;
	private long _companyId;
	private long _groupId;
	private long _userId;
	private String _userUuid;
	private long _courseId;
	private String _className;
	private Date _start;
	private Date _end;
	private String _state;
	private long _userTargetId;
	private long _actId;
	private BaseModel<?> _actManAuditRemoteModel;
}