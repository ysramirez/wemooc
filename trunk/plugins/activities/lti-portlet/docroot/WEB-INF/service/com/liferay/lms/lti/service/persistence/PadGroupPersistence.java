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

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the pad group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Diego Renedo Delgado
 * @see PadGroupPersistenceImpl
 * @see PadGroupUtil
 * @generated
 */
public interface PadGroupPersistence extends BasePersistence<PadGroup> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PadGroupUtil} to access the pad group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the pad group in the entity cache if it is enabled.
	*
	* @param padGroup the pad group
	*/
	public void cacheResult(com.liferay.lms.lti.model.PadGroup padGroup);

	/**
	* Caches the pad groups in the entity cache if it is enabled.
	*
	* @param padGroups the pad groups
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.lti.model.PadGroup> padGroups);

	/**
	* Creates a new pad group with the primary key. Does not add the pad group to the database.
	*
	* @param padGroupId the primary key for the new pad group
	* @return the new pad group
	*/
	public com.liferay.lms.lti.model.PadGroup create(long padGroupId);

	/**
	* Removes the pad group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param padGroupId the primary key of the pad group
	* @return the pad group that was removed
	* @throws com.liferay.lms.lti.NoSuchPadGroupException if a pad group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.PadGroup remove(long padGroupId)
		throws com.liferay.lms.lti.NoSuchPadGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.lti.model.PadGroup updateImpl(
		com.liferay.lms.lti.model.PadGroup padGroup, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the pad group with the primary key or throws a {@link com.liferay.lms.lti.NoSuchPadGroupException} if it could not be found.
	*
	* @param padGroupId the primary key of the pad group
	* @return the pad group
	* @throws com.liferay.lms.lti.NoSuchPadGroupException if a pad group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.PadGroup findByPrimaryKey(long padGroupId)
		throws com.liferay.lms.lti.NoSuchPadGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the pad group with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param padGroupId the primary key of the pad group
	* @return the pad group, or <code>null</code> if a pad group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.lti.model.PadGroup fetchByPrimaryKey(long padGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the pad groups.
	*
	* @return the pad groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.lti.model.PadGroup> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.lti.model.PadGroup> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.lms.lti.model.PadGroup> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the pad groups from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of pad groups.
	*
	* @return the number of pad groups
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}