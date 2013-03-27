package com.liferay.lms.learningactivity;

import java.util.Locale;


public abstract class BaseLearningActivityType implements LearningActivityType{

	@Override
	public String getMesageEditDetails(Locale locale) {
		// TODO Auto-generated method stub
		return "Edit activity details";
	}

	@Override
	public boolean hasEditDetails() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUrlIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean gradebook() {
		// TODO Auto-generated method stub
		return true;
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
