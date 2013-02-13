/**
 * Copyright (c)2013 Telefonica Learning Services. All rights reserved.
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

package com.liferay.lms.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link CheckP2pMailingService}.
 * </p>
 *
 * @author    TLS
 * @see       CheckP2pMailingService
 * @generated
 */
public class CheckP2pMailingServiceWrapper implements CheckP2pMailingService,
	ServiceWrapper<CheckP2pMailingService> {
	public CheckP2pMailingServiceWrapper(
		CheckP2pMailingService checkP2pMailingService) {
		_checkP2pMailingService = checkP2pMailingService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _checkP2pMailingService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_checkP2pMailingService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _checkP2pMailingService.invokeMethod(name, parameterTypes,
			arguments);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public CheckP2pMailingService getWrappedCheckP2pMailingService() {
		return _checkP2pMailingService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedCheckP2pMailingService(
		CheckP2pMailingService checkP2pMailingService) {
		_checkP2pMailingService = checkP2pMailingService;
	}

	public CheckP2pMailingService getWrappedService() {
		return _checkP2pMailingService;
	}

	public void setWrappedService(CheckP2pMailingService checkP2pMailingService) {
		_checkP2pMailingService = checkP2pMailingService;
	}

	private CheckP2pMailingService _checkP2pMailingService;
}