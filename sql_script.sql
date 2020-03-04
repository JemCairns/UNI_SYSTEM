create table hibernate_sequence
(
    next_val bigint null
);

create table module
(
    ID                varchar(10)     not null,
    name              varchar(35)     not null,
    coordinator_ID    varchar(10)     not null,
    max_num_students  int default 200 not null,
    status            varchar(15)     null,
    description       varchar(255)    null,
    curr_num_students int             not null,
    constraint module_ID_uindex
        unique (ID),
    constraint module_name_uindex
        unique (name)
);

alter table module
    add primary key (ID);

create table module_registration
(
    id           int auto_increment
        primary key,
    student_ID   varchar(10)  not null,
    module_ID    varchar(10)  not null,
    percentage   decimal      not null,
    letter_grade varchar(255) null
);

create table staff
(
    ID            varchar(10) not null,
    password      varchar(35) not null,
    prefix        varchar(35) null,
    first_name    varchar(35) not null,
    last_name     varchar(35) not null,
    date_of_birth date        not null,
    gender        varchar(35) not null,
    constraint staff_ID_uindex
        unique (ID)
);

alter table staff
    add primary key (ID);

create table student
(
    ID            varchar(10)   not null,
    first_name    varchar(35)   not null,
    last_name     varchar(35)   not null,
    address       varchar(60)   not null,
    phone_number  varchar(15)   null,
    email         varchar(60)   not null,
    password      varchar(60)   not null,
    fees_due      int default 0 not null,
    fees_paid     int default 0 not null,
    gender        varchar(35)   not null,
    date_of_birth date          not null,
    stage         varchar(35)   not null,
    constraint USER_ID_uindex
        unique (ID)
);

alter table student
    add primary key (ID);

create table topic
(
    topic_ID    int auto_increment
        primary key,
    title       varchar(60) not null,
    description varchar(60) null
);

create table topic_registration
(
    reg_ID    int auto_increment
        primary key,
    module_ID varchar(10) not null,
    topic_ID  int         not null
);









INSERT INTO uni_schema.module (ID, name, coordinator_ID, max_num_students, status, description, curr_num_students) VALUES ('COMP00001', 'Intro to CompSci', '0000001STA', 150, 'terminated', 'This is an introductory module for computer science.', 2);
INSERT INTO uni_schema.module (ID, name, coordinator_ID, max_num_students, status, description, curr_num_students) VALUES ('COMP00002', 'Android Programming', '0000001STA', 125, 'available', 'In this module you will develop an android app about the weather.', 1);
INSERT INTO uni_schema.module (ID, name, coordinator_ID, max_num_students, status, description, curr_num_students) VALUES ('COMP00003', 'Advanced Computer Forensics', '0000002STA', 40, 'available', 'This module will cover advanced computer forensics.', 2);
INSERT INTO uni_schema.module (ID, name, coordinator_ID, max_num_students, status, description, curr_num_students) VALUES ('COMP00004', 'Intro to Python Programming', '0000003STA', 200, 'available', 'Starter Python programming module.', 2);

INSERT INTO uni_schema.module_registration (id, student_ID, module_ID, percentage, letter_grade) VALUES (14, '0000001STU', 'COMP00004', 68, 'C+');
INSERT INTO uni_schema.module_registration (id, student_ID, module_ID, percentage, letter_grade) VALUES (15, '0000001STU', 'COMP00001', 93, 'A');
INSERT INTO uni_schema.module_registration (id, student_ID, module_ID, percentage, letter_grade) VALUES (16, '0000003STU', 'COMP00003', 0, 'NA');
INSERT INTO uni_schema.module_registration (id, student_ID, module_ID, percentage, letter_grade) VALUES (17, '0000003STU', 'COMP00002', 87, 'A-');
INSERT INTO uni_schema.module_registration (id, student_ID, module_ID, percentage, letter_grade) VALUES (18, '0000004STU', 'COMP00004', 72, 'B-');
INSERT INTO uni_schema.module_registration (id, student_ID, module_ID, percentage, letter_grade) VALUES (19, '0000008STU', 'COMP00001', 82, 'B+');
INSERT INTO uni_schema.module_registration (id, student_ID, module_ID, percentage, letter_grade) VALUES (20, '0000008STU', 'COMP00003', 0, 'NA');

INSERT INTO uni_schema.staff (ID, password, prefix, first_name, last_name, date_of_birth, gender) VALUES ('0000001STA', 'IAmNumber1', 'Mr', 'Tom', 'Clarke', '1963-08-23', 'Male');
INSERT INTO uni_schema.staff (ID, password, prefix, first_name, last_name, date_of_birth, gender) VALUES ('0000002STA', 'IAmNumber2', 'Mrs', 'Michelle', 'Brophy', '1975-01-14', 'Female');
INSERT INTO uni_schema.staff (ID, password, prefix, first_name, last_name, date_of_birth, gender) VALUES ('0000003STA', 'IAmNumber3', 'Ms', 'Emma', 'Stone', '1980-10-03', 'Female');
INSERT INTO uni_schema.staff (ID, password, prefix, first_name, last_name, date_of_birth, gender) VALUES ('0000004STA', 'IAmNumber4', 'Mr', 'Fred', 'Michaels', '1991-07-30', 'Male');

INSERT INTO uni_schema.student (ID, first_name, last_name, address, phone_number, email, password, fees_due, fees_paid, gender, date_of_birth, stage) VALUES ('0000001STU', 'Jack', 'Byrne', '4 Lynch Road, Calafornia', '0870000001', 'jack@springfield.com', 'password1', 0, 3000, 'male', '1996-08-07', 'Stage 3');
INSERT INTO uni_schema.student (ID, first_name, last_name, address, phone_number, email, password, fees_due, fees_paid, gender, date_of_birth, stage) VALUES ('0000002STU', 'Joe', 'Partridge', '92 Stellar Ave, Springfield', '0871496385', 'joe@springfield.com', 'password2', 500, 2500, 'male', '2000-09-20', 'Stage 2');
INSERT INTO uni_schema.student (ID, first_name, last_name, address, phone_number, email, password, fees_due, fees_paid, gender, date_of_birth, stage) VALUES ('0000003STU', 'Mary', 'Foley', 'Apartment 42, Springfield Campus', '0865341295', 'maryf@springfield.com', 'password3', 0, 3000, 'female', '1995-10-08', 'Masters');
INSERT INTO uni_schema.student (ID, first_name, last_name, address, phone_number, email, password, fees_due, fees_paid, gender, date_of_birth, stage) VALUES ('0000004STU', 'Patrick', 'Stewart', '12 Fortune Street, Enterprise', '0895649537', 'patrick@springfield.com', 'password4', 0, 3000, 'male', '1980-12-31', 'PHD');
INSERT INTO uni_schema.student (ID, first_name, last_name, address, phone_number, email, password, fees_due, fees_paid, gender, date_of_birth, stage) VALUES ('0000006STU', 'Michael', 'Walls', '12 Wall Street, Springfield', '0895563395', 'michaelw@sprinfield.com', 'password6', 3000, 0, 'male', '2001-01-13', 'Stage 3');
INSERT INTO uni_schema.student (ID, first_name, last_name, address, phone_number, email, password, fees_due, fees_paid, gender, date_of_birth, stage) VALUES ('0000008STU', 'Beyonce', 'NA', '1 Beyonce Street, Beyonce City', '0861296689', 'beyonce@springfield.com', 'password8', 0, 3000, 'female', '1981-11-06', 'Stage 1');
INSERT INTO uni_schema.student (ID, first_name, last_name, address, phone_number, email, password, fees_due, fees_paid, gender, date_of_birth, stage) VALUES ('0000009STU', 'Harry', 'Brady', '9 Tree Town, Springfield', '0861596735', 'harryb@springfield.com', 'password9', 3000, 0, 'male', '1998-09-25', 'Stage 4');

INSERT INTO uni_schema.topic (topic_ID, title, description) VALUES (5, 'Java', 'This topic uses Java.');
INSERT INTO uni_schema.topic (topic_ID, title, description) VALUES (6, 'Python', 'This topic uses Python.');
INSERT INTO uni_schema.topic (topic_ID, title, description) VALUES (7, 'C', 'This topic uses C.');
INSERT INTO uni_schema.topic (topic_ID, title, description) VALUES (8, 'Forensics', 'This topic uses forensics.');
INSERT INTO uni_schema.topic (topic_ID, title, description) VALUES (9, 'App Development', 'You will develop an android app in this module.');
INSERT INTO uni_schema.topic (topic_ID, title, description) VALUES (10, 'Spring-Boot', 'You will use Spring-Boot in this module.');

INSERT INTO uni_schema.topic_registration (reg_ID, module_ID, topic_ID) VALUES (1, 'COMP00001', 7);
INSERT INTO uni_schema.topic_registration (reg_ID, module_ID, topic_ID) VALUES (7, 'COMP00002', 5);
INSERT INTO uni_schema.topic_registration (reg_ID, module_ID, topic_ID) VALUES (8, 'COMP00002', 9);
INSERT INTO uni_schema.topic_registration (reg_ID, module_ID, topic_ID) VALUES (9, 'COMP00003', 5);
INSERT INTO uni_schema.topic_registration (reg_ID, module_ID, topic_ID) VALUES (10, 'COMP00003', 8);
INSERT INTO uni_schema.topic_registration (reg_ID, module_ID, topic_ID) VALUES (12, 'COMP00004', 6);































