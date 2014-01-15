package com.liferay.manager;

public class CleanLearningActivity {

	public static boolean running = false ;

	public static void setRunning(boolean isRunning) {
		CleanLearningActivity.running = isRunning;
	}

	public static boolean isRunning() {
		return running;
	}
}
