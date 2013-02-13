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
 * This class is a wrapper for {@link P2pActivityService}.
 * </p>
 *
 * @author    TLS
 * @see       P2pActivityService
 * @generated
 */
public class P2pActivityServiceWrapper implements P2pActivityService,
	ServiceWrapper<P2pActivityService> {
	public P2pActivityServiceWrapper(P2pActivityService p2pActivityService) {
		_p2pActivityService = p2pActivityService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _p2pActivityService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_p2pActivityService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _p2pActivityService.invokeMethod(name, parameterTypes, arguments);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public P2pActivityService getWrappedP2pActivityService() {
		return _p2pActivityService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedP2pActivityService(
		P2pActivityService p2pActivityService) {
		_p2pActivityService = p2pActivityService;
	}

	public P2pActivityService getWrappedService() {
		return _p2pActivityService;
	}

	public void setWrappedService(P2pActivityService p2pActivityService) {
		_p2pActivityService = p2pActivityService;
	}

	private P2pActivityService _p2pActivityService;
}