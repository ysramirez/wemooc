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
 * This class is a wrapper for {@link ActManAuditService}.
 * </p>
 *
 * @author    TLS
 * @see       ActManAuditService
 * @generated
 */
public class ActManAuditServiceWrapper implements ActManAuditService,
	ServiceWrapper<ActManAuditService> {
	public ActManAuditServiceWrapper(ActManAuditService actManAuditService) {
		_actManAuditService = actManAuditService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _actManAuditService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_actManAuditService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _actManAuditService.invokeMethod(name, parameterTypes, arguments);
	}

	public com.liferay.lmssa.model.ActManAudit addActManAudit(
		com.liferay.lmssa.model.ActManAudit valid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _actManAuditService.addActManAudit(valid);
	}

	public java.util.List<com.liferay.lmssa.model.ActManAudit> findBycompanyId(
		long companyId, int start, int end) {
		return _actManAuditService.findBycompanyId(companyId, start, end);
	}

	public int countBycompanyId(long companyId) {
		return _actManAuditService.countBycompanyId(companyId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ActManAuditService getWrappedActManAuditService() {
		return _actManAuditService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedActManAuditService(
		ActManAuditService actManAuditService) {
		_actManAuditService = actManAuditService;
	}

	public ActManAuditService getWrappedService() {
		return _actManAuditService;
	}

	public void setWrappedService(ActManAuditService actManAuditService) {
		_actManAuditService = actManAuditService;
	}

	private ActManAuditService _actManAuditService;
}