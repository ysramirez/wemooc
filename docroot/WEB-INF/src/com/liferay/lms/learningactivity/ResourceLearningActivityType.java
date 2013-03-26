package com.liferay.lms.learningactivity;

import java.util.Locale;

import com.liferay.lms.asset.ResourceAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;

public class ResourceLearningActivityType extends BaseLearningActivityType 
{

	@Override
	public boolean gradebook() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public long getDefaultScore() {
		return 0;
	}


	@Override
	public String getName(Locale locale) {
		
		return "resource";
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) {
		
		return new ResourceAssetRenderer(learningactivity);
	}


	@Override
	public long getTypeId() {
		return 2;
	}

	
}
