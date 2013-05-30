package com.liferay.lms.learningactivity;

import com.liferay.lms.asset.TaskOfflineAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskOfflineLearningActivityType extends BaseLearningActivityType {

	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"offlinetaskactivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());
	
	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) {
		return new TaskOfflineAssetRenderer(larn);
	}

	@Override
	public String getName() {
		
		return "offLine";
	}

	@Override
	public boolean isScoreConfigurable() {
		return true;
	}
	
	@Override
	public long getTypeId() {
		return 5;
	}
	
	@Override
	public boolean hasEditDetails() {
		return false;
	}
	
	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

}
