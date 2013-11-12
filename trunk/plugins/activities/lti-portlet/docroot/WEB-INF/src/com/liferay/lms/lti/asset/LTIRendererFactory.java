package com.liferay.lms.lti.asset;

import com.liferay.lms.lti.LTILearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

public class LTIRendererFactory extends BaseAssetRendererFactory{
	public static final String CLASS_NAME = LTILearningActivityType.class.getName();

	@Override
	public AssetRenderer getAssetRenderer(long classPK, int type)throws PortalException, SystemException {

		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(classPK);
		
		if (learningActivity != null){
			LTIAssetRenderer lar = new LTIAssetRenderer(learningActivity);
			return lar;
		}else{
			throw new PortalException("La entrada no existe");
		}
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public String getType() {
		return CLASS_NAME;
	}
}