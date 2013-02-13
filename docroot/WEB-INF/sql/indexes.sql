create index IX_A9A7E97F on Lms_CheckP2pMailing (actId);

create index IX_AABFFF7A on Lms_Course (companyId);
create index IX_B852F6C6 on Lms_Course (groupCreatedId);
create index IX_E1561C7C on Lms_Course (groupId);
create index IX_D92F6E28 on Lms_Course (userId);
create index IX_684B7382 on Lms_Course (userId, groupId);
create index IX_97009E06 on Lms_Course (uuid_);
create unique index IX_F90F3D64 on Lms_Course (uuid_, groupId);

create index IX_E5606419 on Lms_CourseResult (courseId, passed);
create index IX_3F29EDEF on Lms_CourseResult (userId, courseId);

create index IX_A674914A on Lms_LearningActivity (groupId);
create index IX_A26B5373 on Lms_LearningActivity (groupId, typeId);
create index IX_A907C93B on Lms_LearningActivity (moduleId);
create index IX_A331EE54 on Lms_LearningActivity (uuid_);
create unique index IX_75B864D6 on Lms_LearningActivity (uuid_, groupId);

create index IX_4AEF2D40 on Lms_LearningActivityResult (actId);
create index IX_86B3E524 on Lms_LearningActivityResult (actId, passed);
create index IX_9F67B375 on Lms_LearningActivityResult (actId, passed, endDate);
create index IX_2E4B457A on Lms_LearningActivityResult (actId, userId);
create index IX_678F5817 on Lms_LearningActivityResult (userId);
create index IX_8B143A37 on Lms_LearningActivityResult (uuid_);

create index IX_6C40B616 on Lms_LearningActivityTry (actId);
create index IX_BAC99850 on Lms_LearningActivityTry (actId, userId);
create index IX_AC65C30D on Lms_LearningActivityTry (uuid_);

create index IX_3596C509 on Lms_LearningType (uuid_);

create index IX_306AE98B on Lms_Module (groupId);
create index IX_3ED54339 on Lms_Module (userId);
create index IX_E045C191 on Lms_Module (userId, groupId);
create index IX_47B362D5 on Lms_Module (uuid_);
create unique index IX_B29B7175 on Lms_Module (uuid_, groupId);

create index IX_7D47DB17 on Lms_ModuleResult (moduleId);
create index IX_A588CBFB on Lms_ModuleResult (moduleId, passed);
create index IX_A378B6D1 on Lms_ModuleResult (userId, moduleId);

create index IX_A70D5A07 on Lms_P2pActivity (actId);
create index IX_90AFBB41 on Lms_P2pActivity (actId, userId);
create index IX_E73266FE on Lms_P2pActivity (uuid_);

create index IX_70AAED8C on Lms_P2pActivityCorrections (actId, userId);
create index IX_599E425D on Lms_P2pActivityCorrections (p2pActivityId);
create index IX_86D5ED97 on Lms_P2pActivityCorrections (p2pActivityId, userId);
create index IX_331E1449 on Lms_P2pActivityCorrections (uuid_);

create index IX_2DB6CF4A on Lms_SurveyResult (uuid_);

create index IX_31C145A on Lms_TestAnswer (actId);
create index IX_558E7458 on Lms_TestAnswer (questionId);
create index IX_43412151 on Lms_TestAnswer (uuid_);

create index IX_8D95C0F2 on Lms_TestQuestion (actId);
create index IX_CDBACDE9 on Lms_TestQuestion (uuid_);