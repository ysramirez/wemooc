package com.liferay.lms.learningactivity;

import java.util.Locale;

import com.liferay.lms.asset.TaskOnlineAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskOnlineLearningActivityType extends BaseLearningActivityType {

	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) {
		return new TaskOnlineAssetRenderer(larn);
	}

	@Override
	public String getName(Locale locale) {
		
		return "onLine";
	}

	@Override
	public long getTypeId() {
		// TODO Auto-generated method stub
		return 6;
	}

}
