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

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LearningActivityTryDeleted}.
 * </p>
 *
 * @author    TLS
 * @see       LearningActivityTryDeleted
 * @generated
 */
public class LearningActivityTryDeletedWrapper
	implements LearningActivityTryDeleted,
		ModelWrapper<LearningActivityTryDeleted> {
	public LearningActivityTryDeletedWrapper(
		LearningActivityTryDeleted learningActivityTryDeleted) {
		_learningActivityTryDeleted = learningActivityTryDeleted;
	}

	public Class<?> getModelClass() {
		return LearningActivityTryDeleted.class;
	}

	public String getModelClassName() {
		return LearningActivityTryDeleted.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("latDelId", getLatDelId());
		attributes.put("actManAuditId", getActManAuditId());
		attributes.put("latId", getLatId());
		attributes.put("actId", getActId());
		attributes.put("userId", getUserId());
		attributes.put("startDate", getStartDate());
		attributes.put("result", getResult());
		attributes.put("endDate", getEndDate());
		attributes.put("tryData", getTryData());
		attributes.put("tryResultData", getTryResultData());
		attributes.put("comments", getComments());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long latDelId = (Long)attributes.get("latDelId");

		if (latDelId != null) {
			setLatDelId(latDelId);
		}

		Long actManAuditId = (Long)attributes.get("actManAuditId");

		if (actManAuditId != null) {
			setActManAuditId(actManAuditId);
		}

		Long latId = (Long)attributes.get("latId");

		if (latId != null) {
			setLatId(latId);
		}

		Long actId = (Long)attributes.get("actId");

		if (actId != null) {
			setActId(actId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Long result = (Long)attributes.get("result");

		if (result != null) {
			setResult(result);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		String tryData = (String)attributes.get("tryData");

		if (tryData != null) {
			setTryData(tryData);
		}

		String tryResultData = (String)attributes.get("tryResultData");

		if (tryResultData != null) {
			setTryResultData(tryResultData);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}
	}

	/**
	* Returns the primary key of this learning activity try deleted.
	*
	* @return the primary key of this learning activity try deleted
	*/
	public long getPrimaryKey() {
		return _learningActivityTryDeleted.getPrimaryKey();
	}

	/**
	* Sets the primary key of this learning activity try deleted.
	*
	* @param primaryKey the primary key of this learning activity try deleted
	*/
	public void setPrimaryKey(long primaryKey) {
		_learningActivityTryDeleted.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this learning activity try deleted.
	*
	* @return the uuid of this learning activity try deleted
	*/
	public java.lang.String getUuid() {
		return _learningActivityTryDeleted.getUuid();
	}

	/**
	* Sets the uuid of this learning activity try deleted.
	*
	* @param uuid the uuid of this learning activity try deleted
	*/
	public void setUuid(java.lang.String uuid) {
		_learningActivityTryDeleted.setUuid(uuid);
	}

	/**
	* Returns the lat del ID of this learning activity try deleted.
	*
	* @return the lat del ID of this learning activity try deleted
	*/
	public long getLatDelId() {
		return _learningActivityTryDeleted.getLatDelId();
	}

	/**
	* Sets the lat del ID of this learning activity try deleted.
	*
	* @param latDelId the lat del ID of this learning activity try deleted
	*/
	public void setLatDelId(long latDelId) {
		_learningActivityTryDeleted.setLatDelId(latDelId);
	}

	/**
	* Returns the act man audit ID of this learning activity try deleted.
	*
	* @return the act man audit ID of this learning activity try deleted
	*/
	public long getActManAuditId() {
		return _learningActivityTryDeleted.getActManAuditId();
	}

	/**
	* Sets the act man audit ID of this learning activity try deleted.
	*
	* @param actManAuditId the act man audit ID of this learning activity try deleted
	*/
	public void setActManAuditId(long actManAuditId) {
		_learningActivityTryDeleted.setActManAuditId(actManAuditId);
	}

	/**
	* Returns the lat ID of this learning activity try deleted.
	*
	* @return the lat ID of this learning activity try deleted
	*/
	public long getLatId() {
		return _learningActivityTryDeleted.getLatId();
	}

	/**
	* Sets the lat ID of this learning activity try deleted.
	*
	* @param latId the lat ID of this learning activity try deleted
	*/
	public void setLatId(long latId) {
		_learningActivityTryDeleted.setLatId(latId);
	}

	/**
	* Returns the act ID of this learning activity try deleted.
	*
	* @return the act ID of this learning activity try deleted
	*/
	public long getActId() {
		return _learningActivityTryDeleted.getActId();
	}

	/**
	* Sets the act ID of this learning activity try deleted.
	*
	* @param actId the act ID of this learning activity try deleted
	*/
	public void setActId(long actId) {
		_learningActivityTryDeleted.setActId(actId);
	}

	/**
	* Returns the user ID of this learning activity try deleted.
	*
	* @return the user ID of this learning activity try deleted
	*/
	public long getUserId() {
		return _learningActivityTryDeleted.getUserId();
	}

	/**
	* Sets the user ID of this learning activity try deleted.
	*
	* @param userId the user ID of this learning activity try deleted
	*/
	public void setUserId(long userId) {
		_learningActivityTryDeleted.setUserId(userId);
	}

	/**
	* Returns the user uuid of this learning activity try deleted.
	*
	* @return the user uuid of this learning activity try deleted
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _learningActivityTryDeleted.getUserUuid();
	}

	/**
	* Sets the user uuid of this learning activity try deleted.
	*
	* @param userUuid the user uuid of this learning activity try deleted
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_learningActivityTryDeleted.setUserUuid(userUuid);
	}

	/**
	* Returns the start date of this learning activity try deleted.
	*
	* @return the start date of this learning activity try deleted
	*/
	public java.util.Date getStartDate() {
		return _learningActivityTryDeleted.getStartDate();
	}

	/**
	* Sets the start date of this learning activity try deleted.
	*
	* @param startDate the start date of this learning activity try deleted
	*/
	public void setStartDate(java.util.Date startDate) {
		_learningActivityTryDeleted.setStartDate(startDate);
	}

	/**
	* Returns the result of this learning activity try deleted.
	*
	* @return the result of this learning activity try deleted
	*/
	public long getResult() {
		return _learningActivityTryDeleted.getResult();
	}

	/**
	* Sets the result of this learning activity try deleted.
	*
	* @param result the result of this learning activity try deleted
	*/
	public void setResult(long result) {
		_learningActivityTryDeleted.setResult(result);
	}

	/**
	* Returns the end date of this learning activity try deleted.
	*
	* @return the end date of this learning activity try deleted
	*/
	public java.util.Date getEndDate() {
		return _learningActivityTryDeleted.getEndDate();
	}

	/**
	* Sets the end date of this learning activity try deleted.
	*
	* @param endDate the end date of this learning activity try deleted
	*/
	public void setEndDate(java.util.Date endDate) {
		_learningActivityTryDeleted.setEndDate(endDate);
	}

	/**
	* Returns the try data of this learning activity try deleted.
	*
	* @return the try data of this learning activity try deleted
	*/
	public java.lang.String getTryData() {
		return _learningActivityTryDeleted.getTryData();
	}

	/**
	* Sets the try data of this learning activity try deleted.
	*
	* @param tryData the try data of this learning activity try deleted
	*/
	public void setTryData(java.lang.String tryData) {
		_learningActivityTryDeleted.setTryData(tryData);
	}

	/**
	* Returns the try result data of this learning activity try deleted.
	*
	* @return the try result data of this learning activity try deleted
	*/
	public java.lang.String getTryResultData() {
		return _learningActivityTryDeleted.getTryResultData();
	}

	/**
	* Sets the try result data of this learning activity try deleted.
	*
	* @param tryResultData the try result data of this learning activity try deleted
	*/
	public void setTryResultData(java.lang.String tryResultData) {
		_learningActivityTryDeleted.setTryResultData(tryResultData);
	}

	/**
	* Returns the comments of this learning activity try deleted.
	*
	* @return the comments of this learning activity try deleted
	*/
	public java.lang.String getComments() {
		return _learningActivityTryDeleted.getComments();
	}

	/**
	* Sets the comments of this learning activity try deleted.
	*
	* @param comments the comments of this learning activity try deleted
	*/
	public void setComments(java.lang.String comments) {
		_learningActivityTryDeleted.setComments(comments);
	}

	public boolean isNew() {
		return _learningActivityTryDeleted.isNew();
	}

	public void setNew(boolean n) {
		_learningActivityTryDeleted.setNew(n);
	}

	public boolean isCachedModel() {
		return _learningActivityTryDeleted.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_learningActivityTryDeleted.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _learningActivityTryDeleted.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _learningActivityTryDeleted.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_learningActivityTryDeleted.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _learningActivityTryDeleted.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_learningActivityTryDeleted.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new LearningActivityTryDeletedWrapper((LearningActivityTryDeleted)_learningActivityTryDeleted.clone());
	}

	public int compareTo(
		com.liferay.lmssa.model.LearningActivityTryDeleted learningActivityTryDeleted) {
		return _learningActivityTryDeleted.compareTo(learningActivityTryDeleted);
	}

	@Override
	public int hashCode() {
		return _learningActivityTryDeleted.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.lmssa.model.LearningActivityTryDeleted> toCacheModel() {
		return _learningActivityTryDeleted.toCacheModel();
	}

	public com.liferay.lmssa.model.LearningActivityTryDeleted toEscapedModel() {
		return new LearningActivityTryDeletedWrapper(_learningActivityTryDeleted.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _learningActivityTryDeleted.toString();
	}

	public java.lang.String toXmlString() {
		return _learningActivityTryDeleted.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_learningActivityTryDeleted.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public LearningActivityTryDeleted getWrappedLearningActivityTryDeleted() {
		return _learningActivityTryDeleted;
	}

	public LearningActivityTryDeleted getWrappedModel() {
		return _learningActivityTryDeleted;
	}

	public void resetOriginalValues() {
		_learningActivityTryDeleted.resetOriginalValues();
	}

	private LearningActivityTryDeleted _learningActivityTryDeleted;
}