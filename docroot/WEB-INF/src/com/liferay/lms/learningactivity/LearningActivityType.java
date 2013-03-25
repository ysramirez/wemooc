package com.liferay.lms.learningactivity;

import java.util.Locale;

import com.liferay.lms.model.LearningActivity;
import com.liferay.portlet.asset.model.AssetRenderer;


public interface LearningActivityType
{
	public long getDefaultScore();
	public long getDefaultTries();
	public long getTypeId();
	public String getDefaultFeedbackCorrect();
	public String getDefaultFeedbackNoCorrect();
	public boolean isScoreConfigurable();
	public boolean isTriesConfigurable();
	public boolean isFeedbackCorrectConfigurable();
	public boolean isFeedbackNoCorrectConfigurable();
	public String getName(Locale locale);
	public AssetRenderer getAssetRenderer(LearningActivity larn);
	public String getUrlIcon();
	public String getDescription(Locale locale);

}
