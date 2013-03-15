package com.liferay.lms.learningactivity;

import java.util.Locale;

import com.liferay.lms.asset.TaskAssetRenderer;
import com.liferay.lms.asset.TestAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskLearningActivityType extends BaseLearningActivityType 
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
		
		return new TaskAssetRenderer(learningactivity);
	}


	@Override
	public String getName(Locale locale) {
		
		return "task";
	}


	@Override
	public long getTypeId() {
		return 1;
	}

	
}
