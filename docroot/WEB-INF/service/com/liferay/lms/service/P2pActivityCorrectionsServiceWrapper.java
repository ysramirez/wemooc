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
 * This class is a wrapper for {@link P2pActivityCorrectionsService}.
 * </p>
 *
 * @author    TLS
 * @see       P2pActivityCorrectionsService
 * @generated
 */
public class P2pActivityCorrectionsServiceWrapper
	implements P2pActivityCorrectionsService,
		ServiceWrapper<P2pActivityCorrectionsService> {
	public P2pActivityCorrectionsServiceWrapper(
		P2pActivityCorrectionsService p2pActivityCorrectionsService) {
		_p2pActivityCorrectionsService = p2pActivityCorrectionsService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _p2pActivityCorrectionsService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_p2pActivityCorrectionsService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _p2pActivityCorrectionsService.invokeMethod(name,
			parameterTypes, arguments);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public P2pActivityCorrectionsService getWrappedP2pActivityCorrectionsService() {
		return _p2pActivityCorrectionsService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedP2pActivityCorrectionsService(
		P2pActivityCorrectionsService p2pActivityCorrectionsService) {
		_p2pActivityCorrectionsService = p2pActivityCorrectionsService;
	}

	public P2pActivityCorrectionsService getWrappedService() {
		return _p2pActivityCorrectionsService;
	}

	public void setWrappedService(
		P2pActivityCorrectionsService p2pActivityCorrectionsService) {
		_p2pActivityCorrectionsService = p2pActivityCorrectionsService;
	}

	private P2pActivityCorrectionsService _p2pActivityCorrectionsService;
}