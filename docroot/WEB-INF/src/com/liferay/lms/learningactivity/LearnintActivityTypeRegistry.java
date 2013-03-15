package com.liferay.lms.learningactivity;

public class LearnintActivityTypeRegistry 
{
	LearningActivityType getLearningActivityType(long typeId)
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
