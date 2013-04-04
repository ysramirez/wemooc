package com.liferay.lms.learningactivity;

import java.util.Locale;

import com.liferay.lms.asset.TaskEvaluationAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskEvaluationLearningActivityType extends BaseLearningActivityType {

	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) {
		return new TaskEvaluationAssetRenderer(larn);
	}
	
	@Override
	public boolean hasEditDetails() {
		return false;
	}

	@Override
	public String getName(Locale locale) {
		
		return "evaluation";
	}
	
	
	@Override
	public boolean isScoreConfigurable() {
		return true;
	}

	@Override
	public long getTypeId() {
		return 8;
	}

}
