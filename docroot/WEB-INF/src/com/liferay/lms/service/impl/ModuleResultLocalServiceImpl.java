/**
 * 2012 TELEFONICA LEARNING SERVICES. All rights reserved.
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

package com.liferay.lms.service.impl;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.base.ModuleResultLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the module result local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.ModuleResultLocalService} interface.
 * </p>
 * 
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.ModuleResultLocalServiceUtil} to access the module result local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.ModuleResultLocalServiceBaseImpl
 * @see com.liferay.lms.service.ModuleResultLocalServiceUtil
 */
public class ModuleResultLocalServiceImpl extends ModuleResultLocalServiceBaseImpl {

	public ModuleResult getByModuleAndUser(long moduleId, long userId)
		throws SystemException {

		ModuleResult moduleResult = moduleResultPersistence.fetchBymu(userId, moduleId);
		return moduleResult;	
	}

	public long countByModule(long moduleId)
		throws SystemException {

		return moduleResultPersistence.countBym(moduleId);
	}

	public long countByModulePassed(long moduleId, boolean passed)
		throws SystemException {

		return moduleResultPersistence.countBymp(moduleId, passed);
	}

	public void update(LearningActivityResult lactr)
		throws PortalException, SystemException {

		ModuleResult moduleResult = null;
		long actId = lactr.getActId();
		long userId = lactr.getUserId();
		LearningActivity learningActivity = learningActivityLocalService.getLearningActivity(actId);
		// Si el Weight es mayor que cero (obligatoria) entonces calcula, sino
		// no.
		// Se elimina la restricción de calcular solo en las obligatorias, se
		// calcula ent todas las que se aprueben.
		if (learningActivity.getModuleId() > 0 && /*
												 * learningActivity.
												 * getWeightinmodule()>0 &&
												 */lactr.getPassed()) {
			long moduleId = learningActivity.getModuleId();
			if (moduleResultPersistence.countBymu(userId, moduleId) > 0) {
				moduleResult = moduleResultPersistence.fetchBymu(userId, moduleId, false);
				if (moduleResult.getPassed()) {
					return;
				}
			}
			else {
				moduleResult = moduleResultPersistence.create(counterLocalService.increment(ModuleResult.class.getName()));
				moduleResult.setModuleId(moduleId);
				moduleResult.setPassed(false);
				moduleResult.setUserId(userId);
				moduleResult.setResult(0);

			}
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LearningActivity.class);
			Criterion crit;
			crit = PropertyFactoryUtil.forName("weightinmodule").gt(new Long(0));
			dynamicQuery.add(crit);
			Criterion crit2;
			crit2 = PropertyFactoryUtil.forName("moduleId").eq(moduleId);
			dynamicQuery.add(crit2);
			java.util.List<LearningActivity> activities = learningActivityLocalService.dynamicQuery(dynamicQuery);
			long passedNumber = 0;
			for (LearningActivity activity : activities) {
				if (learningActivityResultLocalService.existsLearningActivityResult(activity.getActId(), userId)) {
					if (learningActivityResultLocalService.getByActIdAndUserId(activity.getActId(), userId).getPassed()) {
						passedNumber++;
					}
				}
			}
			if (activities.size() > 0) {
				moduleResult.setResult(100 * passedNumber / activities.size());
			}
			if (passedNumber == activities.size()) {
				moduleResult.setResult(100);
				moduleResult.setPassed(true);

			}
			moduleResultPersistence.update(moduleResult, true);
			courseResultLocalService.update(moduleResult);
		}
	}
}
