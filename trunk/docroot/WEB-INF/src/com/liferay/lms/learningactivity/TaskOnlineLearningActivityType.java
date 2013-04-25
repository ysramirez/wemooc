package com.liferay.lms.learningactivity;

import com.liferay.lms.asset.TaskOnlineAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskOnlineLearningActivityType extends BaseLearningActivityType {

	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) {
		return new TaskOnlineAssetRenderer(larn);
	}

	@Override
	public String getName() {
		
		return "onLine";
	}
	
	@Override
	public boolean isScoreConfigurable() {
		return true;
	}

	@Override
	public long getTypeId() {
		// TODO Auto-generated method stub
		return 6;
	}

}
