package com.liferay.lms.learningactivity;

import java.util.Locale;

import com.liferay.lms.asset.ResourceInternalAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;

public class ResourceInternalLearningActivityType extends BaseLearningActivityType 
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
		
		return "resourceInternal";
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) {
		
		return new ResourceInternalAssetRenderer(learningactivity);
	}


	@Override
	public long getTypeId() {
		return 7;
	}

	
}
