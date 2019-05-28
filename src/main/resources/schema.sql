create sequence global_seq start with 1 increment by 1;

create table models (
  id      integer not null primary key,
  version integer not null,
  name    varchar(32)
);