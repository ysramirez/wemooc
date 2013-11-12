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

import com.liferay.lms.lti.model.PadGroup;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the pad group service. This utility wraps {@link PadGroupPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Diego Renedo Delgado
 * @see PadGroupPersistence
 * @see PadGroupPersistenceImpl
 * @generated
 */
public class PadGroupUtil {
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
	public static void clearCache(PadGroup padGroup) {
		getPersistence().clearCache(padGroup);
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
	public static List<PadGroup> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PadGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PadGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static PadGroup update(PadGroup padGroup, boolean merge)
		throws SystemException {
		return getPersistence().update(padGroup, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static PadGroup update(PadGroup padGroup, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(padGroup, merge, serviceContext);
	}

	/**
	* Caches the pad group in the entity cache if it is enabled.
	*
	* @param padGroup the pad group
	*/
	public static void cacheResult(com.liferay.lms.lti.model.PadGroup padGroup) {
		getPersistence().cacheResult(padGroup);
	}

	/**
	* Caches the pad groups in the entity cache if it is enabled.
	*
	* @param padGroups the pad groups
	*/
	public static void cacheResult(
		java.util.List<com.liferay.lms.lti.model.PadGroup> padGroups) {
		getPersistence().cacheResult(padGroups);
	}

	/**
	* Creates a new pad group with the primary key. Does not add the pad group to the database.
	*
	* @param padGroupId the primary key for the new pad group
	* @return the new pad group
	*/
	public static com.liferay.lms.lti.model.PadGroup create(long padGroupId) {
		return getPersistence().create(padGroupId);
	}

	/**
	* Removes the pad group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param padGroupId the primary key of the pad group
	* @return the pad group that was removed
	* @throws com.liferay.lms.lti.NoSuchPadGroupException if a pad group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.PadGroup remove(long padGroupId)
		throws com.liferay.lms.lti.NoSuchPadGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(padGroupId);
	}

	public static com.liferay.lms.lti.model.PadGroup updateImpl(
		com.liferay.lms.lti.model.PadGroup padGroup, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(padGroup, merge);
	}

	/**
	* Returns the pad group with the primary key or throws a {@link com.liferay.lms.lti.NoSuchPadGroupException} if it could not be found.
	*
	* @param padGroupId the primary key of the pad group
	* @return the pad group
	* @throws com.liferay.lms.lti.NoSuchPadGroupException if a pad group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.PadGroup findByPrimaryKey(
		long padGroupId)
		throws com.liferay.lms.lti.NoSuchPadGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(padGroupId);
	}

	/**
	* Returns the pad group with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param padGroupId the primary key of the pad group
	* @return the pad group, or <code>null</code> if a pad group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.PadGroup fetchByPrimaryKey(
		long padGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(padGroupId);
	}

	/**
	* Returns all the pad groups.
	*
	* @return the pad groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.lti.model.PadGroup> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the pad groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of pad groups
	* @param end the upper bound of the range of pad groups (not inclusive)
	* @return the range of pad groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.lti.model.PadGroup> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the pad groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of pad groups
	* @param end the upper bound of the range of pad groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of pad groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.lti.model.PadGroup> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the pad groups from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of pad groups.
	*
	* @return the number of pad groups
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PadGroupPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PadGroupPersistence)PortletBeanLocatorUtil.locate(com.liferay.lms.lti.service.ClpSerializer.getServletContextName(),
					PadGroupPersistence.class.getName());

			ReferenceRegistry.registerReference(PadGroupUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated
	 */
	public void setPersistence(PadGroupPersistence persistence) {
	}

	private static PadGroupPersistence _persistence;
}