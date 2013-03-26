package com.liferay.lms.learningactivity;

import java.util.Locale;

import com.liferay.lms.asset.ResourceExternalAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;

public class ResourceExternalLearningActivityType extends BaseLearningActivityType 
{

	@Override
	public boolean gradebook() {
		return false;
	}


	@Override
	public long getDefaultScore() {
		return 0;
	}


	@Override
	public String getName(Locale locale) {
		
		return "resourceExternal";
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) {
		
		return new ResourceExternalAssetRenderer(learningactivity);
	}


	@Override
	public long getTypeId() {
		return 2;
	}

	
}
