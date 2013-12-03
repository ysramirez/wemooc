create table lmsmail_AuditSendMails (
	uuid_ VARCHAR(75) null,
	auditSendMailsId LONG not null primary key,
	userId LONG,
	templateId LONG,
	groupId LONG,
	sendDate DATE null
);

create table lmsmail_MailTemplate (
	uuid_ VARCHAR(75) null,
	idTemplate LONG not null primary key,
	companyId LONG,
	groupId LONG,
	userId LONG,
	subject VARCHAR(120) null,
	body VARCHAR(3000) null
);