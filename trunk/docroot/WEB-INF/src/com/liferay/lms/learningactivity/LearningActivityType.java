package com.liferay.lms.learningactivity;


public interface LearningActivityType{
	public long getDefaultScore();
	public long getDefaultTries();
	
	public String getDefaultFeedbackCorrect();
	
	public String getDefaultFeedbackNoCorrect();
	
	public boolean isScoreConfigurable();
	
	public boolean isTriesConfigurable();
	
	public boolean isFeedbackCorrectConfigurable();
	
	public boolean isFeedbackNoCorrectConfigurable();
	

}
