package com.liferay.lms.learningactivity;

import com.liferay.lms.asset.SurveyAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;

public class SurveyLearningActivityType extends BaseLearningActivityType {

	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"surveyActivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());
	
	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) {
		return new SurveyAssetRenderer(larn);
	}

	@Override
	public String getName() {
		
		return "survey";
	}

	@Override
	public long getTypeId() {
		// TODO Auto-generated method stub
		return 4;
	}
	
	
	@Override
	public String getMesageEditDetails() {
		return "surveyactivity.editquestions";
	}
	
	public String portletId() {
		return PORTLET_ID;
	}

}
