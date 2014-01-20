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

package com.liferay.lmssa.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link ActManAuditLocalService}.
 * </p>
 *
 * @author    TLS
 * @see       ActManAuditLocalService
 * @generated
 */
public class ActManAuditLocalServiceWrapper implements ActManAuditLocalService,
	ServiceWrapper<ActManAuditLocalService> {
	public ActManAuditLocalServiceWrapper(
		ActManAuditLocalService actManAuditLocalService) {
		_actManAuditLocalService = actManAuditLocalService;
	}

	/**
	* Adds the act man audit to the database. Also notifies the appropriate model listeners.
	*
	* @param actManAudit the act man audit
	* @return the act man audit that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit addActManAudit(
		com.liferay.lmssa.model.ActManAudit actManAudit)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.addActManAudit(actManAudit);
	}

	/**
	* Creates a new act man audit with the primary key. Does not add the act man audit to the database.
	*
	* @param actManAuditId the primary key for the new act man audit
	* @return the new act man audit
	*/
	public com.liferay.lmssa.model.ActManAudit createActManAudit(
		long actManAuditId) {
		return _actManAuditLocalService.createActManAudit(actManAuditId);
	}

	/**
	* Deletes the act man audit with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param actManAuditId the primary key of the act man audit
	* @return the act man audit that was removed
	* @throws PortalException if a act man audit with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit deleteActManAudit(
		long actManAuditId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.deleteActManAudit(actManAuditId);
	}

	/**
	* Deletes the act man audit from the database. Also notifies the appropriate model listeners.
	*
	* @param actManAudit the act man audit
	* @return the act man audit that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit deleteActManAudit(
		com.liferay.lmssa.model.ActManAudit actManAudit)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.deleteActManAudit(actManAudit);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _actManAuditLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.lmssa.model.ActManAudit fetchActManAudit(
		long actManAuditId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.fetchActManAudit(actManAuditId);
	}

	/**
	* Returns the act man audit with the primary key.
	*
	* @param actManAuditId the primary key of the act man audit
	* @return the act man audit
	* @throws PortalException if a act man audit with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit getActManAudit(
		long actManAuditId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.getActManAudit(actManAuditId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the act man audit with the UUID in the group.
	*
	* @param uuid the UUID of act man audit
	* @param groupId the group id of the act man audit
	* @return the act man audit
	* @throws PortalException if a act man audit with the UUID in the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit getActManAuditByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.getActManAuditByUuidAndGroupId(uuid,
			groupId);
	}

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
	public java.util.List<com.liferay.lmssa.model.ActManAudit> getActManAudits(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.getActManAudits(start, end);
	}

	/**
	* Returns the number of act man audits.
	*
	* @return the number of act man audits
	* @throws SystemException if a system exception occurred
	*/
	public int getActManAuditsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.getActManAuditsCount();
	}

	/**
	* Updates the act man audit in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param actManAudit the act man audit
	* @return the act man audit that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit updateActManAudit(
		com.liferay.lmssa.model.ActManAudit actManAudit)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.updateActManAudit(actManAudit);
	}

	/**
	* Updates the act man audit in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param actManAudit the act man audit
	* @param merge whether to merge the act man audit with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the act man audit that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.lmssa.model.ActManAudit updateActManAudit(
		com.liferay.lmssa.model.ActManAudit actManAudit, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditLocalService.updateActManAudit(actManAudit, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _actManAuditLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_actManAuditLocalService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _actManAuditLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	public java.util.List<com.liferay.lmssa.model.ActManAudit> findBycompanyId(
		long companyId, int start, int end) {
		return _actManAuditLocalService.findBycompanyId(companyId, start, end);
	}

	public int countBycompanyId(long companyId) {
		return _actManAuditLocalService.countBycompanyId(companyId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ActManAuditLocalService getWrappedActManAuditLocalService() {
		return _actManAuditLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedActManAuditLocalService(
		ActManAuditLocalService actManAuditLocalService) {
		_actManAuditLocalService = actManAuditLocalService;
	}

	public ActManAuditLocalService getWrappedService() {
		return _actManAuditLocalService;
	}

	public void setWrappedService(
		ActManAuditLocalService actManAuditLocalService) {
		_actManAuditLocalService = actManAuditLocalService;
	}

	private ActManAuditLocalService _actManAuditLocalService;
}