package com.liferay.lms.learningactivity;

import java.util.Locale;


public abstract class BaseLearningActivityType implements LearningActivityType{

	@Override
	public String getUrlIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription(Locale locale) {
		// TODO Auto-generated method stub
		return "";
	}

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
