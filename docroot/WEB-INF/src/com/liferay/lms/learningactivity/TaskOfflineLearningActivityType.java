package com.liferay.lms.learningactivity;

import java.util.Locale;

import com.liferay.lms.asset.TaskOfflineAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskOfflineLearningActivityType extends BaseLearningActivityType {

	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) {
		return new TaskOfflineAssetRenderer(larn);
	}

	@Override
	public String getName(Locale locale) {
		
		return "offLine";
	}

	@Override
	public long getTypeId() {
		// TODO Auto-generated method stub
		return 5;
	}

}
