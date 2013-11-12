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

package com.liferay.lms.lti.service.persistence;

import com.liferay.lms.lti.model.LtiItem;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the lti item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Diego Renedo Delgado
 * @see LtiItemPersistenceImpl
 * @see LtiItemUtil
 * @generated
 */
public interface LtiItemPersistence extends BasePersistence<LtiItem> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LtiItemUtil} to access the lti item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the lti item in the entity cache if it is enabled.
	*
	* @param ltiItem the lti item
	*/
	public void cacheResult(com.liferay.lms.lti.model.LtiItem ltiItem);

	/**
	* Caches the lti items in the entity cache if it is enabled.
	*
	* @param ltiItems the lti items
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.lti.model.LtiItem> ltiItems);

	/**
	* Creates a new lti item with the primary key. Does not add the lti item to the database.
	*
	* @param ltiItemId the primary key for the new lti item
	* @return the new lti item
	*/
	public com.liferay.lms.lti.model.LtiItem create(long ltiItemId);

	/**
	* Removes the lti item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ltiItemId the primary key of the lti item
	* @return the lti item that was removed
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a lti item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem remove(long ltiItemId)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.lti.model.LtiItem updateImpl(
		com.liferay.lms.lti.model.LtiItem ltiItem, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the lti item with the primary key or throws a {@link com.liferay.lms.lti.NoSuchLtiItemException} if it could not be found.
	*
	* @param ltiItemId the primary key of the lti item
	* @return the lti item
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a lti item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem findByPrimaryKey(long ltiItemId)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the lti item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ltiItemId the primary key of the lti item
	* @return the lti item, or <code>null</code> if a lti item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem fetchByPrimaryKey(long ltiItemId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the lti items where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching lti items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.lti.model.LtiItem> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the lti items where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of lti items
	* @param end the upper bound of the range of lti items (not inclusive)
	* @return the range of matching lti items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.lti.model.LtiItem> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the lti items where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of lti items
	* @param end the upper bound of the range of lti items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching lti items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.lti.model.LtiItem> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first lti item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lti item
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first lti item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lti item, or <code>null</code> if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last lti item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lti item
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last lti item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lti item, or <code>null</code> if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the lti items before and after the current lti item in the ordered set where uuid = &#63;.
	*
	* @param ltiItemId the primary key of the current lti item
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next lti item
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a lti item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem[] findByUuid_PrevAndNext(
		long ltiItemId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the lti item where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lms.lti.NoSuchLtiItemException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching lti item
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the lti item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching lti item, or <code>null</code> if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the lti item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching lti item, or <code>null</code> if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the lti item where actId = &#63; or throws a {@link com.liferay.lms.lti.NoSuchLtiItemException} if it could not be found.
	*
	* @param actId the act ID
	* @return the matching lti item
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem findByactId(long actId)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the lti item where actId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param actId the act ID
	* @return the matching lti item, or <code>null</code> if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem fetchByactId(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the lti item where actId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param actId the act ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching lti item, or <code>null</code> if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem fetchByactId(long actId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the lti items.
	*
	* @return the lti items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.lti.model.LtiItem> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the lti items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of lti items
	* @param end the upper bound of the range of lti items (not inclusive)
	* @return the range of lti items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.lti.model.LtiItem> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the lti items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of lti items
	* @param end the upper bound of the range of lti items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of lti items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.lti.model.LtiItem> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the lti items where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the lti item where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the lti item that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the lti item where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @return the lti item that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.LtiItem removeByactId(long actId)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the lti items from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of lti items where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching lti items
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of lti items where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching lti items
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of lti items where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching lti items
	* @throws SystemException if a system exception occurred
	*/
	public int countByactId(long actId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of lti items.
	*
	* @return the number of lti items
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}