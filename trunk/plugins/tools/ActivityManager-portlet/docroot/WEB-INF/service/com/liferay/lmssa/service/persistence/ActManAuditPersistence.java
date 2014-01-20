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

import com.liferay.lmssa.model.ActManAudit;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the act man audit service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see ActManAuditPersistenceImpl
 * @see ActManAuditUtil
 * @generated
 */
public interface ActManAuditPersistence extends BasePersistence<ActManAudit> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ActManAuditUtil} to access the act man audit persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the act man audit in the entity cache if it is enabled.
	*
	* @param actManAudit the act man audit
	*/
	public void cacheResult(com.liferay.lmssa.model.ActManAudit actManAudit);

	/**
	* Caches the act man audits in the entity cache if it is enabled.
	*
	* @param actManAudits the act man audits
	*/
	public void cacheResult(
		java.util.List<com.liferay.lmssa.model.ActManAudit> actManAudits);

	/**
	* Creates a new act man audit with the primary key. Does not add the act man audit to the database.
	*
	* @param actManAuditId the primary key for the new act man audit
	* @return the new act man audit
	*/
	public com.liferay.lmssa.model.ActManAudit create(long actManAuditId);

	/**
	* Removes the act man audit with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param actManAuditId the primary key of the act man audit
	* @return the act man audit that was removed
	* @throws com.liferay.lmssa.NoSuchActManAuditException if a act man audit with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit remove(long actManAuditId)
		throws com.liferay.lmssa.NoSuchActManAuditException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.lmssa.model.ActManAudit updateImpl(
		com.liferay.lmssa.model.ActManAudit actManAudit, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the act man audit with the primary key or throws a {@link com.liferay.lmssa.NoSuchActManAuditException} if it could not be found.
	*
	* @param actManAuditId the primary key of the act man audit
	* @return the act man audit
	* @throws com.liferay.lmssa.NoSuchActManAuditException if a act man audit with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit findByPrimaryKey(
		long actManAuditId)
		throws com.liferay.lmssa.NoSuchActManAuditException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the act man audit with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param actManAuditId the primary key of the act man audit
	* @return the act man audit, or <code>null</code> if a act man audit with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit fetchByPrimaryKey(
		long actManAuditId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the act man audits where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching act man audits
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.ActManAudit> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the act man audits where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of act man audits
	* @param end the upper bound of the range of act man audits (not inclusive)
	* @return the range of matching act man audits
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.ActManAudit> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the act man audits where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of act man audits
	* @param end the upper bound of the range of act man audits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching act man audits
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.ActManAudit> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first act man audit in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching act man audit
	* @throws com.liferay.lmssa.NoSuchActManAuditException if a matching act man audit could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchActManAuditException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first act man audit in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching act man audit, or <code>null</code> if a matching act man audit could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last act man audit in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching act man audit
	* @throws com.liferay.lmssa.NoSuchActManAuditException if a matching act man audit could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchActManAuditException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last act man audit in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching act man audit, or <code>null</code> if a matching act man audit could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the act man audits before and after the current act man audit in the ordered set where uuid = &#63;.
	*
	* @param actManAuditId the primary key of the current act man audit
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next act man audit
	* @throws com.liferay.lmssa.NoSuchActManAuditException if a act man audit with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit[] findByUuid_PrevAndNext(
		long actManAuditId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchActManAuditException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the act man audit where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lmssa.NoSuchActManAuditException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching act man audit
	* @throws com.liferay.lmssa.NoSuchActManAuditException if a matching act man audit could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lmssa.NoSuchActManAuditException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the act man audit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching act man audit, or <code>null</code> if a matching act man audit could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the act man audit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching act man audit, or <code>null</code> if a matching act man audit could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the act man audits where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching act man audits
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.ActManAudit> findBycompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the act man audits where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of act man audits
	* @param end the upper bound of the range of act man audits (not inclusive)
	* @return the range of matching act man audits
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.ActManAudit> findBycompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the act man audits where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of act man audits
	* @param end the upper bound of the range of act man audits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching act man audits
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.ActManAudit> findBycompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first act man audit in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching act man audit
	* @throws com.liferay.lmssa.NoSuchActManAuditException if a matching act man audit could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit findBycompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchActManAuditException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first act man audit in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching act man audit, or <code>null</code> if a matching act man audit could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit fetchBycompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last act man audit in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching act man audit
	* @throws com.liferay.lmssa.NoSuchActManAuditException if a matching act man audit could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit findBycompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchActManAuditException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last act man audit in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching act man audit, or <code>null</code> if a matching act man audit could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit fetchBycompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the act man audits before and after the current act man audit in the ordered set where companyId = &#63;.
	*
	* @param actManAuditId the primary key of the current act man audit
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next act man audit
	* @throws com.liferay.lmssa.NoSuchActManAuditException if a act man audit with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit[] findBycompanyId_PrevAndNext(
		long actManAuditId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.lmssa.NoSuchActManAuditException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the act man audits.
	*
	* @return the act man audits
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.ActManAudit> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the act man audits.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of act man audits
	* @param end the upper bound of the range of act man audits (not inclusive)
	* @return the range of act man audits
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.ActManAudit> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the act man audits.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of act man audits
	* @param end the upper bound of the range of act man audits (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of act man audits
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.lmssa.model.ActManAudit> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the act man audits where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the act man audit where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the act man audit that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.lmssa.NoSuchActManAuditException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the act man audits where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeBycompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the act man audits from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of act man audits where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching act man audits
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of act man audits where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching act man audits
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of act man audits where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching act man audits
	* @throws SystemException if a system exception occurred
	*/
	public int countBycompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of act man audits.
	*
	* @return the number of act man audits
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}