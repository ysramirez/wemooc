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

package com.liferay.lmssa.service.persistence;

import com.liferay.lmssa.model.LearningActivityTryDeleted;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the learning activity try deleted service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LearningActivityTryDeletedPersistenceImpl
 * @see LearningActivityTryDeletedUtil
 * @generated
 */
public interface LearningActivityTryDeletedPersistence extends BasePersistence<LearningActivityTryDeleted> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LearningActivityTryDeletedUtil} to access the learning activity try deleted persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the learning activity try deleted in the entity cache if it is enabled.
	*
	* @param learningActivityTryDeleted the learning activity try deleted
	*/
	public void cacheResult(
		com.liferay.lmssa.model.LearningActivityTryDeleted learningActivityTryDeleted);

	/**
	* Caches the learning activity try deleteds in the entity cache if it is enabled.
	*
	* @param learningActivityTryDeleteds the learning activity try deleteds
	*/
	public void cacheResult(
		java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> learningActivityTryDeleteds);

	/**
	* Creates a new learning activity try deleted with the primary key. Does not add the learning activity try deleted to the database.
	*
	* @param latDelId the primary key for the new learning activity try deleted
	* @return the new learning activity try deleted
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted create(
		long latDelId);

	/**
	* Removes the learning activity try deleted with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param latDelId the primary key of the learning activity try deleted
	* @return the learning activity try deleted that was removed
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a learning activity try deleted with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted remove(
		long latDelId)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lmssa.model.LearningActivityTryDeleted updateImpl(
		com.liferay.lmssa.model.LearningActivityTryDeleted learningActivityTryDeleted,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity try deleted with the primary key or throws a {@link com.liferay.lmssa.NoSuchLearningActivityTryDeletedException} if it could not be found.
	*
	* @param latDelId the primary key of the learning activity try deleted
	* @return the learning activity try deleted
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a learning activity try deleted with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted findByPrimaryKey(
		long latDelId)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity try deleted with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param latDelId the primary key of the learning activity try deleted
	* @return the learning activity try deleted, or <code>null</code> if a learning activity try deleted with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted fetchByPrimaryKey(
		long latDelId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity try deleteds where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the learning activity try deleteds where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of learning activity try deleteds
	* @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	* @return the range of matching learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity try deleteds where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of learning activity try deleteds
	* @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity try deleted in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try deleted
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity try deleted in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try deleted, or <code>null</code> if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity try deleted in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try deleted
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity try deleted in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try deleted, or <code>null</code> if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity try deleteds before and after the current learning activity try deleted in the ordered set where uuid = &#63;.
	*
	* @param latDelId the primary key of the current learning activity try deleted
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity try deleted
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a learning activity try deleted with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted[] findByUuid_PrevAndNext(
		long latDelId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity try deleteds where actManAuditId = &#63;.
	*
	* @param actManAuditId the act man audit ID
	* @return the matching learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findByactDel(
		long actManAuditId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the learning activity try deleteds where actManAuditId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actManAuditId the act man audit ID
	* @param start the lower bound of the range of learning activity try deleteds
	* @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	* @return the range of matching learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findByactDel(
		long actManAuditId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity try deleteds where actManAuditId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param actManAuditId the act man audit ID
	* @param start the lower bound of the range of learning activity try deleteds
	* @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findByactDel(
		long actManAuditId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity try deleted in the ordered set where actManAuditId = &#63;.
	*
	* @param actManAuditId the act man audit ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try deleted
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted findByactDel_First(
		long actManAuditId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first learning activity try deleted in the ordered set where actManAuditId = &#63;.
	*
	* @param actManAuditId the act man audit ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try deleted, or <code>null</code> if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted fetchByactDel_First(
		long actManAuditId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity try deleted in the ordered set where actManAuditId = &#63;.
	*
	* @param actManAuditId the act man audit ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try deleted
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted findByactDel_Last(
		long actManAuditId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last learning activity try deleted in the ordered set where actManAuditId = &#63;.
	*
	* @param actManAuditId the act man audit ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try deleted, or <code>null</code> if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted fetchByactDel_Last(
		long actManAuditId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the learning activity try deleteds before and after the current learning activity try deleted in the ordered set where actManAuditId = &#63;.
	*
	* @param latDelId the primary key of the current learning activity try deleted
	* @param actManAuditId the act man audit ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next learning activity try deleted
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a learning activity try deleted with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.LearningActivityTryDeleted[] findByactDel_PrevAndNext(
		long latDelId, long actManAuditId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the learning activity try deleteds.
	*
	* @return the learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the learning activity try deleteds.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of learning activity try deleteds
	* @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	* @return the range of learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the learning activity try deleteds.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of learning activity try deleteds
	* @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity try deleteds where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity try deleteds where actManAuditId = &#63; from the database.
	*
	* @param actManAuditId the act man audit ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByactDel(long actManAuditId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the learning activity try deleteds from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity try deleteds where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity try deleteds where actManAuditId = &#63;.
	*
	* @param actManAuditId the act man audit ID
	* @return the number of matching learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public int countByactDel(long actManAuditId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of learning activity try deleteds.
	*
	* @return the number of learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}