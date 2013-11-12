create table LmsEtherpad_group (
	padGroupId LONG not null primary key,
	actId LONG,
	companyId LONG,
	groupId LONG,
	createDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	url VARCHAR(75) null,
	secret VARCHAR(75) null,
	rol VARCHAR(75) null,
	contenType VARCHAR(75) null
);

create table lti_item (
	uuid_ VARCHAR(75) null,
	ltiItemId LONG not null primary key,
	actId LONG,
	companyId LONG,
	groupId LONG,
	createDate DATE null,
	userId LONG,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	url VARCHAR(75) null,
	secret VARCHAR(75) null,
	rol VARCHAR(75) null,
	contenType VARCHAR(75) null,
	iframe BOOLEAN,
	note INTEGER
);