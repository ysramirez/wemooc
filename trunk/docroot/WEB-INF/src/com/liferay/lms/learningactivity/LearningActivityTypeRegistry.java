package com.liferay.lms.learningactivity;

public class LearningActivityTypeRegistry 
{
	public LearningActivityType getLearningActivityType(long typeId)
	{
		if(typeId==0)
		{
			return new TestLearningActivityType();
		}
		else
		{
			return new BaseLearningActivityType();
		}
	}
}
