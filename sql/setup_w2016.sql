/*
 *  File name:  setup.sql
 *  Function:   to create the initial database schema for the CMPUT 391 project: An Online Image Sharing System
 *              Winter, 2016
 *  Author:     Prof. Li-Yan Yuan
 */

DROP SEQUENCE images_seq;
DROP SEQUENCE groups_seq;
DROP TRIGGER images_ins;
DROP TRIGGER groups_ins;

DROP TABLE images;
DROP TABLE group_lists;
DROP TABLE groups;
DROP TABLE persons;
DROP TABLE users;


CREATE TABLE users (
   user_name varchar(24),
   password  varchar(24),
   date_registered date,
   primary key(user_name)
);

CREATE TABLE persons (
   user_name  varchar(24),
   first_name varchar(24),
   last_name  varchar(24),
   address    varchar(128),
   email      varchar(128),
   phone      char(10),
   PRIMARY KEY(user_name),
   UNIQUE (email),
   FOREIGN KEY (user_name) REFERENCES users
);


CREATE TABLE groups (
   group_id   int,
   user_name  varchar(24),
   group_name varchar(24),
   date_created date,
   PRIMARY KEY (group_id),
   UNIQUE (user_name, group_name),
   FOREIGN KEY(user_name) REFERENCES users
);

CREATE SEQUENCE groups_seq
  START WITH 1
  INCREMENT BY 1
  CACHE 100;

CREATE OR REPLACE TRIGGER groups_ins 
BEFORE INSERT ON groups 
FOR EACH ROW
BEGIN
  SELECT groups_seq.NEXTVAL
  INTO   :new.group_id
  FROM   dual;
END;

/

INSERT INTO groups values(0,null,'public', sysdate);
INSERT INTO groups values(0,null,'private',sysdate);

CREATE TABLE group_lists (
   group_id    int,
   friend_id   varchar(24),
   date_added  date,
   notice      varchar(1024),
   PRIMARY KEY(group_id, friend_id),
   FOREIGN KEY(group_id) REFERENCES groups,
   FOREIGN KEY(friend_id) REFERENCES users
);

CREATE TABLE images (
   photo_id    int,
   owner_name  varchar(24),
   permitted   int,
   subject     varchar(128),
   place       varchar(128),
   timing      date,
   description varchar(2048),
   thumbnail   blob,
   photo       blob,
   PRIMARY KEY(photo_id),
   FOREIGN KEY(owner_name) REFERENCES users,
   FOREIGN KEY(permitted) REFERENCES groups
);

CREATE SEQUENCE images_seq
  START WITH 1
  INCREMENT BY 1
  CACHE 100;

CREATE OR REPLACE TRIGGER images_seq 
BEFORE INSERT ON images 
FOR EACH ROW
BEGIN
  SELECT images_seq.NEXTVAL
  INTO   :new.photo_id
  FROM   dual;
END;

/
