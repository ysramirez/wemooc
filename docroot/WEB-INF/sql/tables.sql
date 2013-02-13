create table Lms_CheckP2pMailing (
	checkP2pId LONG not null primary key,
	actId LONG,
	date_ DATE null
);

create table Lms_Course (
	uuid_ VARCHAR(75) null,
	courseId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	userId LONG,
	groupCreatedId LONG,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	title STRING null,
	description STRING null,
	friendlyURL VARCHAR(75) null,
	startDate DATE null,
	endDate DATE null,
	icon LONG
);

create table Lms_CourseResult (
	crId LONG not null primary key,
	courseId LONG,
	result LONG,
	comments VARCHAR(75) null,
	userId LONG,
	passed BOOLEAN
);

create table Lms_LearningActivity (
	uuid_ VARCHAR(75) null,
	actId LONG not null primary key,
	companyId LONG,
	userId LONG,
	groupId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	title STRING null,
	description STRING null,
	typeId INTEGER,
	startdate DATE null,
	enddate DATE null,
	precedence LONG,
	tries LONG,
	passpuntuation INTEGER,
	priority LONG,
	moduleId LONG,
	extracontent VARCHAR(75) null,
	feedbackCorrect VARCHAR(75) null,
	feedbackNoCorrect VARCHAR(75) null,
	weightinmodule LONG
);

create table Lms_LearningActivityResult (
	uuid_ VARCHAR(75) null,
	larId LONG not null primary key,
	actId LONG,
	userId LONG,
	result LONG,
	startDate DATE null,
	endDate DATE null,
	latId LONG,
	comments VARCHAR(75) null,
	passed BOOLEAN
);

create table Lms_LearningActivityTry (
	uuid_ VARCHAR(75) null,
	latId LONG not null primary key,
	actId LONG,
	userId LONG,
	startDate DATE null,
	result LONG,
	endDate DATE null,
	tryData VARCHAR(75) null,
	tryResultData VARCHAR(75) null,
	comments VARCHAR(75) null
);

create table Lms_LearningType (
	uuid_ VARCHAR(75) null,
	typeId LONG not null primary key,
	name VARCHAR(75) null,
	className VARCHAR(75) null
);

create table Lms_LmsPrefs (
	companyId LONG not null primary key,
	teacherRole LONG,
	editorRole LONG,
	lmsTemplates VARCHAR(75) null
);

create table Lms_Module (
	uuid_ VARCHAR(75) null,
	moduleId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	userId LONG,
	title STRING null,
	description STRING null,
	ordern LONG,
	startDate DATE null,
	endDate DATE null,
	icon LONG,
	precedence LONG
);

create table Lms_ModuleResult (
	moduleId LONG,
	result LONG,
	comments VARCHAR(75) null,
	userId LONG,
	passed BOOLEAN,
	mrId LONG not null primary key
);

create table Lms_P2pActivity (
	uuid_ VARCHAR(75) null,
	p2pActivityId LONG not null primary key,
	actId LONG,
	userId LONG,
	fileEntryId LONG,
	countCorrections LONG,
	description VARCHAR(75) null,
	date_ DATE null
);

create table Lms_P2pActivityCorrections (
	uuid_ VARCHAR(75) null,
	p2pActivityCorrectionsId LONG not null primary key,
	p2pActivityId LONG,
	userId LONG,
	actId LONG,
	description VARCHAR(75) null,
	date_ DATE null,
	fileEntryId LONG
);

create table Lms_SurveyResult (
	uuid_ VARCHAR(75) null,
	surveyResultId LONG not null primary key,
	actId LONG,
	latId LONG,
	questionId LONG,
	answerId LONG,
	userId LONG
);

create table Lms_TestAnswer (
	uuid_ VARCHAR(75) null,
	answerId LONG not null primary key,
	questionId LONG,
	actId LONG,
	precedence LONG,
	answer VARCHAR(75) null,
	isCorrect BOOLEAN,
	feedbackCorrect VARCHAR(75) null,
	feedbacknocorrect VARCHAR(75) null
);

create table Lms_TestQuestion (
	uuid_ VARCHAR(75) null,
	questionId LONG not null primary key,
	actId LONG,
	text_ VARCHAR(75) null,
	questionType LONG
);