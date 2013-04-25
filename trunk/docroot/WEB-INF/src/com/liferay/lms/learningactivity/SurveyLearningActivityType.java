package com.liferay.lms.learningactivity;

import com.liferay.lms.asset.SurveyAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;

public class SurveyLearningActivityType extends BaseLearningActivityType {

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

}
