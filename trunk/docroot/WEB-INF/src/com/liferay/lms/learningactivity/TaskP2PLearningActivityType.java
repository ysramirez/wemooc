package com.liferay.lms.learningactivity;

import com.liferay.lms.asset.TaskP2PAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskP2PLearningActivityType extends BaseLearningActivityType {

	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) {
		// TODO Auto-generated method stub
		return new TaskP2PAssetRenderer(larn);
	}
	@Override
	public long getDefaultScore() {		
		return 50;
	}

	@Override
	public String getName() {
		return "taskp2p";
	}

	@Override
	public long getTypeId() {
		return 3;
	}

}
