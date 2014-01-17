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

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ActManAudit}.
 * </p>
 *
 * @author    TLS
 * @see       ActManAudit
 * @generated
 */
public class ActManAuditWrapper implements ActManAudit,
	ModelWrapper<ActManAudit> {
	public ActManAuditWrapper(ActManAudit actManAudit) {
		_actManAudit = actManAudit;
	}

	public Class<?> getModelClass() {
		return ActManAudit.class;
	}

	public String getModelClassName() {
		return ActManAudit.class.getName();
	}

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

	/**
	* Returns the primary key of this act man audit.
	*
	* @return the primary key of this act man audit
	*/
	public long getPrimaryKey() {
		return _actManAudit.getPrimaryKey();
	}

	/**
	* Sets the primary key of this act man audit.
	*
	* @param primaryKey the primary key of this act man audit
	*/
	public void setPrimaryKey(long primaryKey) {
		_actManAudit.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this act man audit.
	*
	* @return the uuid of this act man audit
	*/
	public java.lang.String getUuid() {
		return _actManAudit.getUuid();
	}

	/**
	* Sets the uuid of this act man audit.
	*
	* @param uuid the uuid of this act man audit
	*/
	public void setUuid(java.lang.String uuid) {
		_actManAudit.setUuid(uuid);
	}

	/**
	* Returns the act man audit ID of this act man audit.
	*
	* @return the act man audit ID of this act man audit
	*/
	public long getActManAuditId() {
		return _actManAudit.getActManAuditId();
	}

	/**
	* Sets the act man audit ID of this act man audit.
	*
	* @param actManAuditId the act man audit ID of this act man audit
	*/
	public void setActManAuditId(long actManAuditId) {
		_actManAudit.setActManAuditId(actManAuditId);
	}

	/**
	* Returns the company ID of this act man audit.
	*
	* @return the company ID of this act man audit
	*/
	public long getCompanyId() {
		return _actManAudit.getCompanyId();
	}

	/**
	* Sets the company ID of this act man audit.
	*
	* @param companyId the company ID of this act man audit
	*/
	public void setCompanyId(long companyId) {
		_actManAudit.setCompanyId(companyId);
	}

	/**
	* Returns the group ID of this act man audit.
	*
	* @return the group ID of this act man audit
	*/
	public long getGroupId() {
		return _actManAudit.getGroupId();
	}

	/**
	* Sets the group ID of this act man audit.
	*
	* @param groupId the group ID of this act man audit
	*/
	public void setGroupId(long groupId) {
		_actManAudit.setGroupId(groupId);
	}

	/**
	* Returns the user ID of this act man audit.
	*
	* @return the user ID of this act man audit
	*/
	public long getUserId() {
		return _actManAudit.getUserId();
	}

	/**
	* Sets the user ID of this act man audit.
	*
	* @param userId the user ID of this act man audit
	*/
	public void setUserId(long userId) {
		_actManAudit.setUserId(userId);
	}

	/**
	* Returns the user uuid of this act man audit.
	*
	* @return the user uuid of this act man audit
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAudit.getUserUuid();
	}

	/**
	* Sets the user uuid of this act man audit.
	*
	* @param userUuid the user uuid of this act man audit
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_actManAudit.setUserUuid(userUuid);
	}

	/**
	* Returns the course ID of this act man audit.
	*
	* @return the course ID of this act man audit
	*/
	public long getCourseId() {
		return _actManAudit.getCourseId();
	}

	/**
	* Sets the course ID of this act man audit.
	*
	* @param courseId the course ID of this act man audit
	*/
	public void setCourseId(long courseId) {
		_actManAudit.setCourseId(courseId);
	}

	/**
	* Returns the class name of this act man audit.
	*
	* @return the class name of this act man audit
	*/
	public java.lang.String getClassName() {
		return _actManAudit.getClassName();
	}

	/**
	* Sets the class name of this act man audit.
	*
	* @param className the class name of this act man audit
	*/
	public void setClassName(java.lang.String className) {
		_actManAudit.setClassName(className);
	}

	/**
	* Returns the start of this act man audit.
	*
	* @return the start of this act man audit
	*/
	public java.util.Date getStart() {
		return _actManAudit.getStart();
	}

	/**
	* Sets the start of this act man audit.
	*
	* @param start the start of this act man audit
	*/
	public void setStart(java.util.Date start) {
		_actManAudit.setStart(start);
	}

	/**
	* Returns the end of this act man audit.
	*
	* @return the end of this act man audit
	*/
	public java.util.Date getEnd() {
		return _actManAudit.getEnd();
	}

	/**
	* Sets the end of this act man audit.
	*
	* @param end the end of this act man audit
	*/
	public void setEnd(java.util.Date end) {
		_actManAudit.setEnd(end);
	}

	/**
	* Returns the state of this act man audit.
	*
	* @return the state of this act man audit
	*/
	public java.lang.String getState() {
		return _actManAudit.getState();
	}

	/**
	* Sets the state of this act man audit.
	*
	* @param state the state of this act man audit
	*/
	public void setState(java.lang.String state) {
		_actManAudit.setState(state);
	}

	/**
	* Returns the user target ID of this act man audit.
	*
	* @return the user target ID of this act man audit
	*/
	public long getUserTargetId() {
		return _actManAudit.getUserTargetId();
	}

	/**
	* Sets the user target ID of this act man audit.
	*
	* @param userTargetId the user target ID of this act man audit
	*/
	public void setUserTargetId(long userTargetId) {
		_actManAudit.setUserTargetId(userTargetId);
	}

	/**
	* Returns the act ID of this act man audit.
	*
	* @return the act ID of this act man audit
	*/
	public long getActId() {
		return _actManAudit.getActId();
	}

	/**
	* Sets the act ID of this act man audit.
	*
	* @param actId the act ID of this act man audit
	*/
	public void setActId(long actId) {
		_actManAudit.setActId(actId);
	}

	public boolean isNew() {
		return _actManAudit.isNew();
	}

	public void setNew(boolean n) {
		_actManAudit.setNew(n);
	}

	public boolean isCachedModel() {
		return _actManAudit.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_actManAudit.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _actManAudit.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _actManAudit.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_actManAudit.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _actManAudit.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_actManAudit.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ActManAuditWrapper((ActManAudit)_actManAudit.clone());
	}

	public int compareTo(com.liferay.lms.model.ActManAudit actManAudit) {
		return _actManAudit.compareTo(actManAudit);
	}

	@Override
	public int hashCode() {
		return _actManAudit.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lms.model.ActManAudit> toCacheModel() {
		return _actManAudit.toCacheModel();
	}

	public com.liferay.lms.model.ActManAudit toEscapedModel() {
		return new ActManAuditWrapper(_actManAudit.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _actManAudit.toString();
	}

	public java.lang.String toXmlString() {
		return _actManAudit.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_actManAudit.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public ActManAudit getWrappedActManAudit() {
		return _actManAudit;
	}

	public ActManAudit getWrappedModel() {
		return _actManAudit;
	}

	public void resetOriginalValues() {
		_actManAudit.resetOriginalValues();
	}

	private ActManAudit _actManAudit;
}