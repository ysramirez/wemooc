Liferay.Service.register("Liferay.Service.Lms", "com.liferay.lms.service", "liferaylms-portlet");

Liferay.Service.registerClass(
	Liferay.Service.Lms, "Course",
	{
		getCoursesOfGroup: true,
		createCourse: true,
		getCourseStudents: true,
		getCourseTeachers: true,
		getCourseEditors: true,
		addStudentToCourse: true,
		addTeacherToCourse: true,
		addEditorToCourse: true,
		removeStudentFromCourse: true,
		removeTeacherFromCourse: true,
		removeEditorFromCourse: true,
		getUserResult: true,
		myCourses: true,
		addUser: true,
		updateUser: true,
		setParent: true,
		unsetParent: true,
		setTutor: true,
		unsetTutor: true,
		existsCourseName: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "LearningActivity",
	{
		getLearningActivitiesOfGroup: true,
		getLearningActivitiesOfModule: true,
		deleteLearningactivity: true,
		getLearningActivity: true,
		addLearningActivity: true,
		modLearningActivity: true,
		isLocked: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "LearningActivityResult",
	{
		getByActId: true,
		getByActIdAndUser: true,
		userPassed: true,
		userLoginPassed: true,
		update: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "LearningActivityTry",
	{
		createLearningActivityTry: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "Module",
	{
		findAllInGroup: true,
		isLocked: true,
		isUserPassed: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "SCORMContent",
	{
		getSCORMContentOfGroup: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "TestAnswer",
	{
		getTestAnswersByQuestionId: true,
		getTestAnswer: true,
		modTestAnswer: true,
		addTestAnswer: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.Lms, "TestQuestion",
	{
		addQuestion: true,
		getQuestions: true,
		getQuestion: true
	}
);