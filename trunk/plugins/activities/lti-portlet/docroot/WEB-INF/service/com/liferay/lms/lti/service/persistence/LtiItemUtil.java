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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the lti item service. This utility wraps {@link LtiItemPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Diego Renedo Delgado
 * @see LtiItemPersistence
 * @see LtiItemPersistenceImpl
 * @generated
 */
public class LtiItemUtil {
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
	public static void clearCache(LtiItem ltiItem) {
		getPersistence().clearCache(ltiItem);
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
	public static List<LtiItem> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LtiItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LtiItem> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static LtiItem update(LtiItem ltiItem, boolean merge)
		throws SystemException {
		return getPersistence().update(ltiItem, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static LtiItem update(LtiItem ltiItem, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(ltiItem, merge, serviceContext);
	}

	/**
	* Caches the lti item in the entity cache if it is enabled.
	*
	* @param ltiItem the lti item
	*/
	public static void cacheResult(com.liferay.lms.lti.model.LtiItem ltiItem) {
		getPersistence().cacheResult(ltiItem);
	}

	/**
	* Caches the lti items in the entity cache if it is enabled.
	*
	* @param ltiItems the lti items
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.lti.model.LtiItem> ltiItems) {
		getPersistence().cacheResult(ltiItems);
	}

	/**
	* Creates a new lti item with the primary key. Does not add the lti item to the database.
	*
	* @param ltiItemId the primary key for the new lti item
	* @return the new lti item
	*/
	public static com.liferay.lms.lti.model.LtiItem create(long ltiItemId) {
		return getPersistence().create(ltiItemId);
	}

	/**
	* Removes the lti item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ltiItemId the primary key of the lti item
	* @return the lti item that was removed
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a lti item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem remove(long ltiItemId)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(ltiItemId);
	}

	public static com.liferay.lms.lti.model.LtiItem updateImpl(
		com.liferay.lms.lti.model.LtiItem ltiItem, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(ltiItem, merge);
	}

	/**
	* Returns the lti item with the primary key or throws a {@link com.liferay.lms.lti.NoSuchLtiItemException} if it could not be found.
	*
	* @param ltiItemId the primary key of the lti item
	* @return the lti item
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a lti item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem findByPrimaryKey(
		long ltiItemId)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(ltiItemId);
	}

	/**
	* Returns the lti item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ltiItemId the primary key of the lti item
	* @return the lti item, or <code>null</code> if a lti item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem fetchByPrimaryKey(
		long ltiItemId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(ltiItemId);
	}

	/**
	* Returns all the lti items where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching lti items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.lti.model.LtiItem> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

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
	public static java.util.List<com.liferay.lms.lti.model.LtiItem> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

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
	public static java.util.List<com.liferay.lms.lti.model.LtiItem> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first lti item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lti item
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first lti item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching lti item, or <code>null</code> if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last lti item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lti item
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last lti item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching lti item, or <code>null</code> if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

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
	public static com.liferay.lms.lti.model.LtiItem[] findByUuid_PrevAndNext(
		long ltiItemId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_PrevAndNext(ltiItemId, uuid, orderByComparator);
	}

	/**
	* Returns the lti item where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lms.lti.NoSuchLtiItemException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching lti item
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the lti item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching lti item, or <code>null</code> if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the lti item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching lti item, or <code>null</code> if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Returns the lti item where actId = &#63; or throws a {@link com.liferay.lms.lti.NoSuchLtiItemException} if it could not be found.
	*
	* @param actId the act ID
	* @return the matching lti item
	* @throws com.liferay.lms.lti.NoSuchLtiItemException if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem findByactId(long actId)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByactId(actId);
	}

	/**
	* Returns the lti item where actId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param actId the act ID
	* @return the matching lti item, or <code>null</code> if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem fetchByactId(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByactId(actId);
	}

	/**
	* Returns the lti item where actId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param actId the act ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching lti item, or <code>null</code> if a matching lti item could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem fetchByactId(long actId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByactId(actId, retrieveFromCache);
	}

	/**
	* Returns all the lti items.
	*
	* @return the lti items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.lti.model.LtiItem> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.lms.lti.model.LtiItem> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.lms.lti.model.LtiItem> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the lti items where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the lti item where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the lti item that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes the lti item where actId = &#63; from the database.
	*
	* @param actId the act ID
	* @return the lti item that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.LtiItem removeByactId(long actId)
		throws com.liferay.lms.lti.NoSuchLtiItemException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByactId(actId);
	}

	/**
	* Removes all the lti items from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of lti items where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching lti items
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the number of lti items where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching lti items
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of lti items where actId = &#63;.
	*
	* @param actId the act ID
	* @return the number of matching lti items
	* @throws SystemException if a system exception occurred
	*/
	public static int countByactId(long actId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByactId(actId);
	}

	/**
	* Returns the number of lti items.
	*
	* @return the number of lti items
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LtiItemPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LtiItemPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.lti.service.ClpSerializer.getServletContextName(),
					LtiItemPersistence.class.getName());

			ReferenceRegistry.registerReference(LtiItemUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(LtiItemPersistence persistence) {
	}

	private static LtiItemPersistence _persistence;
}