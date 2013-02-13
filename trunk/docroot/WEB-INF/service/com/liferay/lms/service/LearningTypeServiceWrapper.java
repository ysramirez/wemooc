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
 * This class is a wrapper for {@link LearningTypeService}.
 * </p>
 *
 * @author    TLS
 * @see       LearningTypeService
 * @generated
 */
public class LearningTypeServiceWrapper implements LearningTypeService,
	ServiceWrapper<LearningTypeService> {
	public LearningTypeServiceWrapper(LearningTypeService learningTypeService) {
		_learningTypeService = learningTypeService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _learningTypeService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_learningTypeService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _learningTypeService.invokeMethod(name, parameterTypes, arguments);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public LearningTypeService getWrappedLearningTypeService() {
		return _learningTypeService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedLearningTypeService(
		LearningTypeService learningTypeService) {
		_learningTypeService = learningTypeService;
	}

	public LearningTypeService getWrappedService() {
		return _learningTypeService;
	}

	public void setWrappedService(LearningTypeService learningTypeService) {
		_learningTypeService = learningTypeService;
	}

	private LearningTypeService _learningTypeService;
}