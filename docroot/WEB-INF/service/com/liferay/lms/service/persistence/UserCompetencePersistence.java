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

package com.liferay.lms.service.persistence;

import com.liferay.lms.model.UserCompetence;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the user competence service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see UserCompetencePersistenceImpl
 * @see UserCompetenceUtil
 * @generated
 */
public interface UserCompetencePersistence extends BasePersistence<UserCompetence> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserCompetenceUtil} to access the user competence persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the user competence in the entity cache if it is enabled.
	*
	* @param userCompetence the user competence
	*/
	public void cacheResult(com.liferay.lms.model.UserCompetence userCompetence);

	/**
	* Caches the user competences in the entity cache if it is enabled.
	*
	* @param userCompetences the user competences
	*/
	public void cacheResult(
		java.util.List<com.liferay.lms.model.UserCompetence> userCompetences);

	/**
	* Creates a new user competence with the primary key. Does not add the user competence to the database.
	*
	* @param usercompId the primary key for the new user competence
	* @return the new user competence
	*/
	public com.liferay.lms.model.UserCompetence create(long usercompId);

	/**
	* Removes the user competence with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param usercompId the primary key of the user competence
	* @return the user competence that was removed
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence remove(long usercompId)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lms.model.UserCompetence updateImpl(
		com.liferay.lms.model.UserCompetence userCompetence, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user competence with the primary key or throws a {@link com.liferay.lms.NoSuchUserCompetenceException} if it could not be found.
	*
	* @param usercompId the primary key of the user competence
	* @return the user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence findByPrimaryKey(
		long usercompId)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user competence with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param usercompId the primary key of the user competence
	* @return the user competence, or <code>null</code> if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence fetchByPrimaryKey(
		long usercompId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the user competences where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.UserCompetence> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the user competences where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @return the range of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.UserCompetence> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the user competences where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.UserCompetence> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first user competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first user competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user competence, or <code>null</code> if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last user competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last user competence in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user competence, or <code>null</code> if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user competences before and after the current user competence in the ordered set where uuid = &#63;.
	*
	* @param usercompId the primary key of the current user competence
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence[] findByUuid_PrevAndNext(
		long usercompId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the user competences where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.UserCompetence> findByuserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the user competences where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @return the range of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.UserCompetence> findByuserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the user competences where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.UserCompetence> findByuserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first user competence in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence findByuserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first user competence in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user competence, or <code>null</code> if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence fetchByuserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last user competence in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence findByuserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last user competence in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user competence, or <code>null</code> if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence fetchByuserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user competences before and after the current user competence in the ordered set where userId = &#63;.
	*
	* @param usercompId the primary key of the current user competence
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence[] findByuserId_PrevAndNext(
		long usercompId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the user competences where competenceId = &#63;.
	*
	* @param competenceId the competence ID
	* @return the matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.UserCompetence> findBycompetenceId(
		long competenceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the user competences where competenceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param competenceId the competence ID
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @return the range of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.UserCompetence> findBycompetenceId(
		long competenceId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the user competences where competenceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param competenceId the competence ID
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.UserCompetence> findBycompetenceId(
		long competenceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first user competence in the ordered set where competenceId = &#63;.
	*
	* @param competenceId the competence ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence findBycompetenceId_First(
		long competenceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first user competence in the ordered set where competenceId = &#63;.
	*
	* @param competenceId the competence ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user competence, or <code>null</code> if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence fetchBycompetenceId_First(
		long competenceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last user competence in the ordered set where competenceId = &#63;.
	*
	* @param competenceId the competence ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence findBycompetenceId_Last(
		long competenceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last user competence in the ordered set where competenceId = &#63;.
	*
	* @param competenceId the competence ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user competence, or <code>null</code> if a matching user competence could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence fetchBycompetenceId_Last(
		long competenceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the user competences before and after the current user competence in the ordered set where competenceId = &#63;.
	*
	* @param usercompId the primary key of the current user competence
	* @param competenceId the competence ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user competence
	* @throws com.liferay.lms.NoSuchUserCompetenceException if a user competence with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lms.model.UserCompetence[] findBycompetenceId_PrevAndNext(
		long usercompId, long competenceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lms.NoSuchUserCompetenceException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the user competences.
	*
	* @return the user competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.UserCompetence> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the user competences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @return the range of user competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.UserCompetence> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the user competences.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of user competences
	* @param end the upper bound of the range of user competences (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user competences
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lms.model.UserCompetence> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user competences where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user competences where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByuserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user competences where competenceId = &#63; from the database.
	*
	* @param competenceId the competence ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeBycompetenceId(long competenceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the user competences from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of user competences where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of user competences where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public int countByuserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of user competences where competenceId = &#63;.
	*
	* @param competenceId the competence ID
	* @return the number of matching user competences
	* @throws SystemException if a system exception occurred
	*/
	public int countBycompetenceId(long competenceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of user competences.
	*
	* @return the number of user competences
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}