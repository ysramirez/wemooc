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
					"surveyactivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());
	
	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) {
		return new SurveyAssetRenderer(larn);
	}

	@Override
	public String getName() {
		
		return "learningactivity.survey";
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
	
	@Override
	public String getDescription() {
		return "learningactivity.survey.helpmessage";
	}
	
	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

}
