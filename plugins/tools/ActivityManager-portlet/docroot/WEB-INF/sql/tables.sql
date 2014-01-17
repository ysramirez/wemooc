create table lms_ActManAudit (
	uuid_ VARCHAR(75) null,
	actManAuditId LONG not null primary key,
	companyId LONG,
	groupId LONG,
	userId LONG,
	courseId LONG,
	className VARCHAR(75) null,
	start_ DATE null,
	end_ DATE null,
	state_ VARCHAR(75) null,
	userTargetId LONG,
	actId LONG
);

create table lms_LearningActivityTryDeleted (
	uuid_ VARCHAR(75) null,
	latDelId LONG not null primary key,
	actManAuditId LONG,
	latId LONG,
	actId LONG,
	userId LONG,
	startDate DATE null,
	result LONG,
	endDate DATE null,
	tryData VARCHAR(75) null,
	tryResultData VARCHAR(75) null,
	comments VARCHAR(75) null
);