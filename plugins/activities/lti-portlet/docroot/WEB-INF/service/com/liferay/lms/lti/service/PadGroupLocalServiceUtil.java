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

package com.liferay.lms.lti.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * The utility for the pad group local service. This utility wraps {@link com.liferay.lms.lti.service.impl.PadGroupLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Diego Renedo Delgado
 * @see PadGroupLocalService
 * @see com.liferay.lms.lti.service.base.PadGroupLocalServiceBaseImpl
 * @see com.liferay.lms.lti.service.impl.PadGroupLocalServiceImpl
 * @generated
 */
public class PadGroupLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.lms.lti.service.impl.PadGroupLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the pad group to the database. Also notifies the appropriate model listeners.
	*
	* @param padGroup the pad group
	* @return the pad group that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.PadGroup addPadGroup(
		com.liferay.lms.lti.model.PadGroup padGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPadGroup(padGroup);
	}

	/**
	* Creates a new pad group with the primary key. Does not add the pad group to the database.
	*
	* @param padGroupId the primary key for the new pad group
	* @return the new pad group
	*/
	public static com.liferay.lms.lti.model.PadGroup createPadGroup(
		long padGroupId) {
		return getService().createPadGroup(padGroupId);
	}

	/**
	* Deletes the pad group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param padGroupId the primary key of the pad group
	* @return the pad group that was removed
	* @throws PortalException if a pad group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.PadGroup deletePadGroup(
		long padGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePadGroup(padGroupId);
	}

	/**
	* Deletes the pad group from the database. Also notifies the appropriate model listeners.
	*
	* @param padGroup the pad group
	* @return the pad group that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.PadGroup deletePadGroup(
		com.liferay.lms.lti.model.PadGroup padGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePadGroup(padGroup);
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

	public static com.liferay.lms.lti.model.PadGroup fetchPadGroup(
		long padGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchPadGroup(padGroupId);
	}

	/**
	* Returns the pad group with the primary key.
	*
	* @param padGroupId the primary key of the pad group
	* @return the pad group
	* @throws PortalException if a pad group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.PadGroup getPadGroup(
		long padGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPadGroup(padGroupId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the pad groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of pad groups
	* @param end the upper bound of the range of pad groups (not inclusive)
	* @return the range of pad groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.lms.lti.model.PadGroup> getPadGroups(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPadGroups(start, end);
	}

	/**
	* Returns the number of pad groups.
	*
	* @return the number of pad groups
	* @throws SystemException if a system exception occurred
	*/
	public static int getPadGroupsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPadGroupsCount();
	}

	/**
	* Updates the pad group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param padGroup the pad group
	* @return the pad group that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.PadGroup updatePadGroup(
		com.liferay.lms.lti.model.PadGroup padGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePadGroup(padGroup);
	}

	/**
	* Updates the pad group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param padGroup the pad group
	* @param merge whether to merge the pad group with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the pad group that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.lms.lti.model.PadGroup updatePadGroup(
		com.liferay.lms.lti.model.PadGroup padGroup, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePadGroup(padGroup, merge);
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

	public static PadGroupLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					PadGroupLocalService.class.getName());

			if (invokableLocalService instanceof PadGroupLocalService) {
				_service = (PadGroupLocalService)invokableLocalService;
			}
			else {
				_service = new PadGroupLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(PadGroupLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	public void setService(PadGroupLocalService service) {
	}

	private static PadGroupLocalService _service;
}