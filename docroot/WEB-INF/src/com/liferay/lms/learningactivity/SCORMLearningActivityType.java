package com.liferay.lms.learningactivity;

import com.liferay.lms.asset.SCORMAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;

public class SCORMLearningActivityType extends BaseLearningActivityType 
{

	@Override
	public boolean gradebook() {
		return false;
	}


	@Override
	public long getDefaultScore() {
		return 50;
	}
	@Override
	public boolean isScoreConfigurable() {
		return true;
	}

	@Override
	public String getName() {
		
		return "SCORM";
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) {
		
		return new SCORMAssetRenderer(learningactivity);
	}


	@Override
	public long getTypeId() {
		return 9;
	}

	
}
