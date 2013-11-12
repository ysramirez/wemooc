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

package com.liferay.lms.model;

import com.liferay.lms.service.CourseResultLocalServiceUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TLS
 */
public class CourseResultClp extends BaseModelImpl<CourseResult>
	implements CourseResult {
	public CourseResultClp() {
	}

	public Class<?> getModelClass() {
		return CourseResult.class;
	}

	public String getModelClassName() {
		return CourseResult.class.getName();
	}

	public long getPrimaryKey() {
		return _crId;
	}

	public void setPrimaryKey(long primaryKey) {
		setCrId(primaryKey);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_crId);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("crId", getCrId());
		attributes.put("courseId", getCourseId());
		attributes.put("result", getResult());
		attributes.put("comments", getComments());
		attributes.put("userId", getUserId());
		attributes.put("passed", getPassed());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long crId = (Long)attributes.get("crId");

		if (crId != null) {
			setCrId(crId);
		}

		Long courseId = (Long)attributes.get("courseId");

		if (courseId != null) {
			setCourseId(courseId);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Boolean passed = (Boolean)attributes.get("passed");

		if (passed != null) {
			setPassed(passed);
		}
	}

	public long getCrId() {
		return _crId;
	}

	public void setCrId(long crId) {
		_crId = crId;
	}

	public long getCourseId() {
		return _courseId;
	}

	public void setCourseId(long courseId) {
		_courseId = courseId;
	}

	public long getResult() {
		return _result;
	}

	public void setResult(long result) {
		_result = result;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
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

	public boolean getPassed() {
		return _passed;
	}

	public boolean isPassed() {
		return _passed;
	}

	public void setPassed(boolean passed) {
		_passed = passed;
	}

	public BaseModel<?> getCourseResultRemoteModel() {
		return _courseResultRemoteModel;
	}

	public void setCourseResultRemoteModel(BaseModel<?> courseResultRemoteModel) {
		_courseResultRemoteModel = courseResultRemoteModel;
	}

	public void persist() throws SystemException {
		if (this.isNew()) {
			CourseResultLocalServiceUtil.addCourseResult(this);
		}
		else {
			CourseResultLocalServiceUtil.updateCourseResult(this);
		}
	}

	@Override
	public CourseResult toEscapedModel() {
		return (CourseResult)Proxy.newProxyInstance(CourseResult.class.getClassLoader(),
			new Class[] { CourseResult.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		CourseResultClp clone = new CourseResultClp();

		clone.setCrId(getCrId());
		clone.setCourseId(getCourseId());
		clone.setResult(getResult());
		clone.setComments(getComments());
		clone.setUserId(getUserId());
		clone.setPassed(getPassed());

		return clone;
	}

	public int compareTo(CourseResult courseResult) {
		long primaryKey = courseResult.getPrimaryKey();

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

		CourseResultClp courseResult = null;

		try {
			courseResult = (CourseResultClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long primaryKey = courseResult.getPrimaryKey();

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
		StringBundler sb = new StringBundler(13);

		sb.append("{crId=");
		sb.append(getCrId());
		sb.append(", courseId=");
		sb.append(getCourseId());
		sb.append(", result=");
		sb.append(getResult());
		sb.append(", comments=");
		sb.append(getComments());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", passed=");
		sb.append(getPassed());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.liferay.lms.model.CourseResult");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>crId</column-name><column-value><![CDATA[");
		sb.append(getCrId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>courseId</column-name><column-value><![CDATA[");
		sb.append(getCourseId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>result</column-name><column-value><![CDATA[");
		sb.append(getResult());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>comments</column-name><column-value><![CDATA[");
		sb.append(getComments());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>passed</column-name><column-value><![CDATA[");
		sb.append(getPassed());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _crId;
	private long _courseId;
	private long _result;
	private String _comments;
	private long _userId;
	private String _userUuid;
	private boolean _passed;
	private BaseModel<?> _courseResultRemoteModel;
}