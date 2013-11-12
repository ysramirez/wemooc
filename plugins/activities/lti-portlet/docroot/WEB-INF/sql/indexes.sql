create index IX_7A3950A6 on lti_item (actId);
create index IX_BA5E5D9D on lti_item (uuid_);
create unique index IX_310A17AD on lti_item (uuid_, groupId);