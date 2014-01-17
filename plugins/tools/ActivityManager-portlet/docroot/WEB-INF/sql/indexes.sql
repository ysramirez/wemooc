create index IX_6EEBDADC on lms_ActManAudit (companyId);
create index IX_A3957A68 on lms_ActManAudit (uuid_);
create unique index IX_744F2F42 on lms_ActManAudit (uuid_, groupId);