package com.liferay.lms.learningactivity;


public abstract class BaseLearningActivityType implements LearningActivityType{

	@Override
	public long getDefaultScore() {		
		return 0;
	}

	@Override
	public long getDefaultTries() {
		return 0;
	}

	@Override
	public String getDefaultFeedbackCorrect() {
		
		return "";
	}

	@Override
	public String getDefaultFeedbackNoCorrect() {
		return "";
	}

	@Override
	public boolean isScoreConfigurable() {
		return false;
	}

	@Override
	public boolean isTriesConfigurable() {
		return false;
	}

	@Override
	public boolean isFeedbackCorrectConfigurable() {
		return false;
	}

	@Override
	public boolean isFeedbackNoCorrectConfigurable() {
		return false;
	}

	

}
