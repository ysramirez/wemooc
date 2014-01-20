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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the learning activity try deleted service. This utility wraps {@link LearningActivityTryDeletedPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LearningActivityTryDeletedPersistence
 * @see LearningActivityTryDeletedPersistenceImpl
 * @generated
 */
public class LearningActivityTryDeletedUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(
		LearningActivityTryDeleted learningActivityTryDeleted) {
		getPersistence().clearCache(learningActivityTryDeleted);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LearningActivityTryDeleted> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LearningActivityTryDeleted> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LearningActivityTryDeleted> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static LearningActivityTryDeleted update(
		LearningActivityTryDeleted learningActivityTryDeleted, boolean merge)
		throws SystemException {
		return getPersistence().update(learningActivityTryDeleted, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static LearningActivityTryDeleted update(
		LearningActivityTryDeleted learningActivityTryDeleted, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(learningActivityTryDeleted, merge, serviceContext);
	}

	/**
	* Caches the learning activity try deleted in the entity cache if it is enabled.
	*
	* @param learningActivityTryDeleted the learning activity try deleted
	*/
	public static void cacheResult(
		com.liferay.lmssa.model.LearningActivityTryDeleted learningActivityTryDeleted) {
		getPersistence().cacheResult(learningActivityTryDeleted);
	}

	/**
	* Caches the learning activity try deleteds in the entity cache if it is enabled.
	*
	* @param learningActivityTryDeleteds the learning activity try deleteds
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> learningActivityTryDeleteds) {
		getPersistence().cacheResult(learningActivityTryDeleteds);
	}

	/**
	* Creates a new learning activity try deleted with the primary key. Does not add the learning activity try deleted to the database.
	*
	* @param latDelId the primary key for the new learning activity try deleted
	* @return the new learning activity try deleted
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted create(
		long latDelId) {
		return getPersistence().create(latDelId);
	}

	/**
	* Removes the learning activity try deleted with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param latDelId the primary key of the learning activity try deleted
	* @return the learning activity try deleted that was removed
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a learning activity try deleted with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted remove(
		long latDelId)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(latDelId);
	}

	public static com.liferay.lmssa.model.LearningActivityTryDeleted updateImpl(
		com.liferay.lmssa.model.LearningActivityTryDeleted learningActivityTryDeleted,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(learningActivityTryDeleted, merge);
	}

	/**
	* Returns the learning activity try deleted with the primary key or throws a {@link com.liferay.lmssa.NoSuchLearningActivityTryDeletedException} if it could not be found.
	*
	* @param latDelId the primary key of the learning activity try deleted
	* @return the learning activity try deleted
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a learning activity try deleted with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted findByPrimaryKey(
		long latDelId)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(latDelId);
	}

	/**
	* Returns the learning activity try deleted with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param latDelId the primary key of the learning activity try deleted
	* @return the learning activity try deleted, or <code>null</code> if a learning activity try deleted with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted fetchByPrimaryKey(
		long latDelId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(latDelId);
	}

	/**
	* Returns all the learning activity try deleteds where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

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
	public static java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first learning activity try deleted in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try deleted
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first learning activity try deleted in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try deleted, or <code>null</code> if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last learning activity try deleted in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try deleted
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last learning activity try deleted in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try deleted, or <code>null</code> if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

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
	public static com.liferay.lmssa.model.LearningActivityTryDeleted[] findByUuid_PrevAndNext(
		long latDelId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(latDelId, uuid, orderByComparator);
	}

	/**
	* Returns all the learning activity try deleteds where actManAuditId = &#63;.
	*
	* @param actManAuditId the act man audit ID
	* @return the matching learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findByactDel(
		long actManAuditId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByactDel(actManAuditId);
	}

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
	public static java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findByactDel(
		long actManAuditId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByactDel(actManAuditId, start, end);
	}

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
	public static java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findByactDel(
		long actManAuditId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByactDel(actManAuditId, start, end, orderByComparator);
	}

	/**
	* Returns the first learning activity try deleted in the ordered set where actManAuditId = &#63;.
	*
	* @param actManAuditId the act man audit ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try deleted
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted findByactDel_First(
		long actManAuditId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByactDel_First(actManAuditId, orderByComparator);
	}

	/**
	* Returns the first learning activity try deleted in the ordered set where actManAuditId = &#63;.
	*
	* @param actManAuditId the act man audit ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching learning activity try deleted, or <code>null</code> if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted fetchByactDel_First(
		long actManAuditId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByactDel_First(actManAuditId, orderByComparator);
	}

	/**
	* Returns the last learning activity try deleted in the ordered set where actManAuditId = &#63;.
	*
	* @param actManAuditId the act man audit ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try deleted
	* @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted findByactDel_Last(
		long actManAuditId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByactDel_Last(actManAuditId, orderByComparator);
	}

	/**
	* Returns the last learning activity try deleted in the ordered set where actManAuditId = &#63;.
	*
	* @param actManAuditId the act man audit ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching learning activity try deleted, or <code>null</code> if a matching learning activity try deleted could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted fetchByactDel_Last(
		long actManAuditId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByactDel_Last(actManAuditId, orderByComparator);
	}

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
	public static com.liferay.lmssa.model.LearningActivityTryDeleted[] findByactDel_PrevAndNext(
		long latDelId, long actManAuditId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByactDel_PrevAndNext(latDelId, actManAuditId,
			orderByComparator);
	}

	/**
	* Returns all the learning activity try deleteds.
	*
	* @return the learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the learning activity try deleteds where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes all the learning activity try deleteds where actManAuditId = &#63; from the database.
	*
	* @param actManAuditId the act man audit ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByactDel(long actManAuditId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByactDel(actManAuditId);
	}

	/**
	* Removes all the learning activity try deleteds from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of learning activity try deleteds where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of learning activity try deleteds where actManAuditId = &#63;.
	*
	* @param actManAuditId the act man audit ID
	* @return the number of matching learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public static int countByactDel(long actManAuditId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByactDel(actManAuditId);
	}

	/**
	* Returns the number of learning activity try deleteds.
	*
	* @return the number of learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LearningActivityTryDeletedPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LearningActivityTryDeletedPersistence)PortletBeanLocatorUtil.locate(com.liferay.lmssa.service.ClpSerializer.getServletContextName(),
					LearningActivityTryDeletedPersistence.class.getName());

			ReferenceRegistry.registerReference(LearningActivityTryDeletedUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(
		LearningActivityTryDeletedPersistence persistence) {
	}

	private static LearningActivityTryDeletedPersistence _persistence;
}