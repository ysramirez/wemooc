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
 * This class is a wrapper for {@link SurveyResultService}.
 * </p>
 *
 * @author    TLS
 * @see       SurveyResultService
 * @generated
 */
public class SurveyResultServiceWrapper implements SurveyResultService,
	ServiceWrapper<SurveyResultService> {
	public SurveyResultServiceWrapper(SurveyResultService surveyResultService) {
		_surveyResultService = surveyResultService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _surveyResultService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_surveyResultService.setBeanIdentifier(beanIdentifier);
	}

	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _surveyResultService.invokeMethod(name, parameterTypes, arguments);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SurveyResultService getWrappedSurveyResultService() {
		return _surveyResultService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSurveyResultService(
		SurveyResultService surveyResultService) {
		_surveyResultService = surveyResultService;
	}

	public SurveyResultService getWrappedService() {
		return _surveyResultService;
	}

	public void setWrappedService(SurveyResultService surveyResultService) {
		_surveyResultService = surveyResultService;
	}

	private SurveyResultService _surveyResultService;
}