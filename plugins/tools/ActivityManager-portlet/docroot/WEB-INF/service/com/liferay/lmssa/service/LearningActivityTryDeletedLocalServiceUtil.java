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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * The utility for the learning activity try deleted local service. This utility wraps {@link com.liferay.lmssa.service.impl.LearningActivityTryDeletedLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author TLS
 * @see LearningActivityTryDeletedLocalService
 * @see com.liferay.lmssa.service.base.LearningActivityTryDeletedLocalServiceBaseImpl
 * @see com.liferay.lmssa.service.impl.LearningActivityTryDeletedLocalServiceImpl
 * @generated
 */
public class LearningActivityTryDeletedLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lmssa.service.impl.LearningActivityTryDeletedLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the learning activity try deleted to the database. Also notifies the appropriate model listeners.
	*
	* @param learningActivityTryDeleted the learning activity try deleted
	* @return the learning activity try deleted that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted addLearningActivityTryDeleted(
		com.liferay.lmssa.model.LearningActivityTryDeleted learningActivityTryDeleted)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addLearningActivityTryDeleted(learningActivityTryDeleted);
	}

	/**
	* Creates a new learning activity try deleted with the primary key. Does not add the learning activity try deleted to the database.
	*
	* @param latDelId the primary key for the new learning activity try deleted
	* @return the new learning activity try deleted
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted createLearningActivityTryDeleted(
		long latDelId) {
		return getService().createLearningActivityTryDeleted(latDelId);
	}

	/**
	* Deletes the learning activity try deleted with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param latDelId the primary key of the learning activity try deleted
	* @return the learning activity try deleted that was removed
	* @throws PortalException if a learning activity try deleted with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted deleteLearningActivityTryDeleted(
		long latDelId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteLearningActivityTryDeleted(latDelId);
	}

	/**
	* Deletes the learning activity try deleted from the database. Also notifies the appropriate model listeners.
	*
	* @param learningActivityTryDeleted the learning activity try deleted
	* @return the learning activity try deleted that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted deleteLearningActivityTryDeleted(
		com.liferay.lmssa.model.LearningActivityTryDeleted learningActivityTryDeleted)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteLearningActivityTryDeleted(learningActivityTryDeleted);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.lmssa.model.LearningActivityTryDeleted fetchLearningActivityTryDeleted(
		long latDelId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchLearningActivityTryDeleted(latDelId);
	}

	/**
	* Returns the learning activity try deleted with the primary key.
	*
	* @param latDelId the primary key of the learning activity try deleted
	* @return the learning activity try deleted
	* @throws PortalException if a learning activity try deleted with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted getLearningActivityTryDeleted(
		long latDelId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLearningActivityTryDeleted(latDelId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the learning activity try deleteds.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of learning activity try deleteds
	* @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	* @return the range of learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lmssa.model.LearningActivityTryDeleted> getLearningActivityTryDeleteds(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLearningActivityTryDeleteds(start, end);
	}

	/**
	* Returns the number of learning activity try deleteds.
	*
	* @return the number of learning activity try deleteds
	* @throws SystemException if a system exception occurred
	*/
	public static int getLearningActivityTryDeletedsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLearningActivityTryDeletedsCount();
	}

	/**
	* Updates the learning activity try deleted in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param learningActivityTryDeleted the learning activity try deleted
	* @return the learning activity try deleted that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted updateLearningActivityTryDeleted(
		com.liferay.lmssa.model.LearningActivityTryDeleted learningActivityTryDeleted)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLearningActivityTryDeleted(learningActivityTryDeleted);
	}

	/**
	* Updates the learning activity try deleted in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param learningActivityTryDeleted the learning activity try deleted
	* @param merge whether to merge the learning activity try deleted with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the learning activity try deleted that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lmssa.model.LearningActivityTryDeleted updateLearningActivityTryDeleted(
		com.liferay.lmssa.model.LearningActivityTryDeleted learningActivityTryDeleted,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLearningActivityTryDeleted(learningActivityTryDeleted,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static void clearService() {
		_service = null;
	}

	public static LearningActivityTryDeletedLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					LearningActivityTryDeletedLocalService.class.getName());

			if (invokableLocalService instanceof LearningActivityTryDeletedLocalService) {
				_service = (LearningActivityTryDeletedLocalService)invokableLocalService;
			}
			else {
				_service = new LearningActivityTryDeletedLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(LearningActivityTryDeletedLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(LearningActivityTryDeletedLocalService service) {
	}

	private static LearningActivityTryDeletedLocalService _service;
}