package com.liferay.lms.learningactivity;

import com.liferay.lms.asset.TestAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TestLearningActivityType extends BaseLearningActivityType 
{

	@Override
	public long getDefaultScore() {
		return 50;
	}


	@Override
	public boolean isScoreConfigurable() {
		return true;
	}

	@Override
	public boolean isTriesConfigurable() {
		return true;
	}

	@Override
	public boolean isFeedbackCorrectConfigurable() {
		return true;
	}

	@Override
	public boolean isFeedbackNoCorrectConfigurable() {
		return true;
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) {
		
		return new TestAssetRenderer(learningactivity);
	}


	@Override
	public String getName() {
		return "test";
	}


	@Override
	public long getTypeId() {
		return 0;
	}

	
}
