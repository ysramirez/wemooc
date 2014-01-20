create index IX_6EEBDADC on lms_ActManAudit (companyId);
create index IX_A3957A68 on lms_ActManAudit (uuid_);
create unique index IX_744F2F42 on lms_ActManAudit (uuid_, groupId);

create index IX_E8F29E00 on lms_LearningActivityTryDeleted (actManAuditId);
create index IX_D95DFD19 on lms_LearningActivityTryDeleted (latDelId);
create index IX_7A3472B6 on lms_LearningActivityTryDeleted (uuid_);

create index IX_1B1E4CCE on lmssa_ActManAudit (companyId);
create index IX_DFA2B55A on lmssa_ActManAudit (uuid_);
create unique index IX_7F02490 on lmssa_ActManAudit (uuid_, groupId);

create index IX_21D32CE on lmssa_LearningActivityTryDeleted (actManAuditId);
create index IX_4AAFB584 on lmssa_LearningActivityTryDeleted (uuid_);