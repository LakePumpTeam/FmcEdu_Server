-- mysql workbench forward engineering

set @old_unique_checks = @@unique_checks, unique_checks = 0;
set @old_foreign_key_checks = @@foreign_key_checks, foreign_key_checks = 0;
set @old_sql_mode = @@sql_mode, sql_mode = 'traditional,allow_invalid_dates';

-- -----------------------------------------------------
-- schema fmc_edu
-- -----------------------------------------------------
drop schema if exists `fmc_edu`;

-- -----------------------------------------------------
-- schema fmc_edu
-- -----------------------------------------------------
create schema if not exists `fmc_edu`
	default character set utf8
	collate utf8_general_ci;
use `fmc_edu`;

-- -----------------------------------------------------
-- table `fmc_edu`.`province`
-- -----------------------------------------------------
drop table if exists `fmc_edu`.`province`;

create table if not exists `fmc_edu`.`province` (
	`id`               int         not null auto_increment,
	`name`             varchar(40) not null,
	`last_update_date` datetime    not null,
	primary key (`id`)
)
	engine = myisam;


-- -----------------------------------------------------
-- table `fmc_edu`.`city`
-- -----------------------------------------------------
drop table if exists `fmc_edu`.`city`;

create table if not exists `fmc_edu`.`city` (
	`id`               int         not null auto_increment,
	`name`             varchar(40) not null,
	`province_id`      int         not null,
	`last_update_date` datetime    not null,
	primary key (`id`)
)
	engine = myisam;

create index `fk_city_province_idx` on `fmc_edu`.`city` (`province_id` asc);


-- -----------------------------------------------------
-- table `fmc_edu`.`address`
-- -----------------------------------------------------
drop table if exists `fmc_edu`.`address`;

create table if not exists `fmc_edu`.`address` (
	`id`               int          not null auto_increment,
	`province_id`      int          not null,
	`city_id`          int          not null,
	`full_address`     varchar(200) null,
	`creation_date`    datetime     null,
	`last_update_date` datetime     not null,
	primary key (`id`)
)
	engine = innodb;

create index `fk_address_city1_idx` on `fmc_edu`.`address` (`city_id` asc);

create index `fk_address_province1_idx` on `fmc_edu`.`address` (`province_id` asc);


-- -----------------------------------------------------
-- table `fmc_edu`.`school`
-- -----------------------------------------------------
drop table if exists `fmc_edu`.`school`;

create table if not exists `fmc_edu`.`school` (
	`id`               int         not null auto_increment,
	`name`             varchar(80) not null,
	`province_id`      int         not null,
	`city_id`          int         not null,
	`address_id`       int         null,
	`last_update_date` datetime    not null,
	primary key (`id`)
)
	engine = innodb;

create index `fk_school_city1_idx` on `fmc_edu`.`school` (`city_id` asc);

create index `fk_school_address1_idx` on `fmc_edu`.`school` (`address_id` asc);

create index `fk_school_province1_idx` on `fmc_edu`.`school` (`province_id` asc);


-- -----------------------------------------------------
-- table `fmc_edu`.`class`
-- -----------------------------------------------------
drop table if exists `fmc_edu`.`class`;

create table if not exists `fmc_edu`.`class` (
	`id`               int         not null auto_increment,
	`school_id`        int         not null,
	`grade`            varchar(50) not null,
	`class`            varchar(50) not null,
	`available`        tinyint(1)  not null default 1,
	`head_teacher_id`  int         null,
	`last_update_date` datetime    not null,
	primary key (`id`)
)
	engine = innodb;

create index `fk_class_school1_idx` on `fmc_edu`.`class` (`school_id` asc);


-- -----------------------------------------------------
-- table `fmc_edu`.`student`
-- -----------------------------------------------------
drop table if exists `fmc_edu`.`student`;

create table if not exists `fmc_edu`.`student` (
	`id`               int         not null auto_increment,
	`name`             varchar(20) not null,
	`class_id`         int         not null,
	`sex`              tinyint(1)  null,
	`birth`            date        null,
	`ring_number`      varchar(45) null,
	`creation_date`    datetime    null,
	`available`        tinyint(1)  not null default 1,
	`last_update_date` datetime    not null,
	primary key (`id`)
)
	engine = innodb;

create index `fk_student_class1_idx` on `fmc_edu`.`student` (`class_id` asc);


-- -----------------------------------------------------
-- table `fmc_edu`.`profile`
-- -----------------------------------------------------
drop table if exists `fmc_edu`.`profile`;

create table if not exists `fmc_edu`.`profile` (
	`id`               int         not null auto_increment,
	`name`             varchar(20) null,
	`phone`            varchar(11) not null,
	`password`         varchar(32) null,
	`app_id`           varchar(20) null,
	`creation_date`    datetime    null,
	`last_login_date`  datetime    null,
	`last_update_date` datetime    not null,
	`available`        tinyint(1)  not null default 1,
	`profile_type`     tinyint(1)  not null default 1,
	primary key (`id`)
)
	engine = innodb;


-- -----------------------------------------------------
-- table `fmc_edu`.`teacher`
-- -----------------------------------------------------
drop table if exists `fmc_edu`.`teacher`;

create table if not exists `fmc_edu`.`teacher` (
	`profile_id`   int        not null,
	`school_id`    int        not null,
	`head_teacher` tinyint(1) not null default 0,
	`initialized`  tinyint(1) not null default 0,
	primary key (`profile_id`)
)
	engine = innodb;

create index `fk_teacher_school1_idx` on `fmc_edu`.`teacher` (`school_id` asc);

create index `fk_teacher_profile1_idx` on `fmc_edu`.`teacher` (`profile_id` asc);


-- -----------------------------------------------------
-- table `fmc_edu`.`parent`
-- -----------------------------------------------------
drop table if exists `fmc_edu`.`parent`;

create table if not exists `fmc_edu`.`parent` (
	`profile_id`          int        not null,
	`address_id`          int        null,
	`paid`                tinyint(1) not null default 0,
	`free_trial`          tinyint(1) not null default 0,
	`free_trial_end_date` datetime   null,
	primary key (`profile_id`)
)
	engine = innodb;

create index `fk_parent_address1_idx` on `fmc_edu`.`parent` (`address_id` asc);

create index `fk_parent_profile1_idx` on `fmc_edu`.`parent` (`profile_id` asc);


-- -----------------------------------------------------
-- table `fmc_edu`.`temp_parent`
-- -----------------------------------------------------
drop table if exists `fmc_edu`.`temp_parent`;

create table if not exists `fmc_edu`.`temp_parent` (
	`profile_id`       int         not null,
	`identifying_code` varchar(20) null,
	`identifying_date` datetime    null,
	primary key (`profile_id`)
)
	engine = innodb;

create index `fk_temp_parent_profile1_idx` on `fmc_edu`.`temp_parent` (`profile_id` asc);


-- -----------------------------------------------------
-- table `fmc_edu`.`parent_student_map`
-- -----------------------------------------------------
drop table if exists `fmc_edu`.`parent_student_map`;

create table if not exists `fmc_edu`.`parent_student_map` (
	`parent_id`     int        not null,
	`student_id`    int        not null,
	`approved`      tinyint(1) not null default 0,
	`approved_date` datetime   null,
	primary key (`parent_id`, `student_id`)
)
	engine = innodb;

create index `fk_parent_has_student_student1_idx` on `fmc_edu`.`parent_student_map` (`student_id` asc);

create index `fk_parent_has_student_parent1_idx` on `fmc_edu`.`parent_student_map` (`parent_id` asc);


-- -----------------------------------------------------
-- table `fmc_edu`.`teacher_class_map`
-- -----------------------------------------------------
drop table if exists `fmc_edu`.`teacher_class_map`;

create table if not exists `fmc_edu`.`teacher_class_map` (
	`teacher_id`       int         not null,
	`class_id`         int         not null,
	`sub_title`        varchar(20) null,
	`head_teacher`     tinyint(1)  not null default 0,
	`creation_date`    datetime    null,
	`last_update_date` datetime    not null,
	primary key (`teacher_id`, `class_id`)
)
	engine = innodb;

create index `fk_teacher_has_class_class1_idx` on `fmc_edu`.`teacher_class_map` (`class_id` asc);

create index `fk_teacher_has_class_teacher1_idx` on `fmc_edu`.`teacher_class_map` (`teacher_id` asc);


set sql_mode = @old_sql_mode;
set foreign_key_checks = @old_foreign_key_checks;
set unique_checks = @old_unique_checks;
