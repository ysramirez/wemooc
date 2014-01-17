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