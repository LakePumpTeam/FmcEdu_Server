-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema fmc_edu
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `fmc_edu`;

-- -----------------------------------------------------
-- Schema fmc_edu
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `fmc_edu`
  DEFAULT CHARACTER SET utf8;
USE `fmc_edu`;

-- -----------------------------------------------------
-- Table `fmc_edu`.`province`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`province`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`province` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(40) NOT NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = MyISAM
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fmc_edu`.`city`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`city`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`city` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(40) NOT NULL,
  `province_id`      INT(11)     NOT NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = MyISAM
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_City_Province_idx` ON `fmc_edu`.`city` (`province_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`address`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`address` (
  `id`               INT(11)      NOT NULL AUTO_INCREMENT,
  `province_id`      INT(11)      NOT NULL,
  `city_id`          INT(11)      NOT NULL,
  `full_address`     VARCHAR(200) NULL     DEFAULT NULL,
  `creation_date`    DATETIME     NULL     DEFAULT NULL,
  `last_update_date` DATETIME     NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Address_City1_idx` ON `fmc_edu`.`address` (`city_Id` ASC);

CREATE INDEX `fk_Address_Province1_idx` ON `fmc_edu`.`address` (`province_Id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`school`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`school`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`school` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(80) NOT NULL,
  `province_id`      INT(11)     NOT NULL,
  `city_id`          INT(11)     NOT NULL,
  `address_id`       INT(11)     NULL     DEFAULT NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_School_City1_idx` ON `fmc_edu`.`school` (`city_id` ASC);

CREATE INDEX `fk_School_Address1_idx` ON `fmc_edu`.`school` (`address_id` ASC);

CREATE INDEX `fk_School_Province1_idx` ON `fmc_edu`.`school` (`province_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`class`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`class`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`class` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `school_id`        INT(11)     NOT NULL,
  `grade`            VARCHAR(50) NOT NULL,
  `class`            VARCHAR(50) NOT NULL,
  `available`        TINYINT(1)  NOT NULL DEFAULT '1',
  `head_teacher_id`  INT(11)     NULL     DEFAULT NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Class_School1_idx` ON `fmc_edu`.`class` (`school_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`student`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`student` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(20) NOT NULL,
  `class_id`         INT(11)     NOT NULL,
  `sex`              TINYINT(1)  NULL     DEFAULT NULL,
  `birth`            DATE        NULL     DEFAULT NULL,
  `ring_number`      VARCHAR(45) NULL     DEFAULT NULL,
  `creation_date`    DATETIME    NULL     DEFAULT NULL,
  `available`        TINYINT(1)  NOT NULL DEFAULT '1',
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Student_Class1_idx` ON `fmc_edu`.`student` (`class_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`profile`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`profile` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(20) NULL     DEFAULT NULL,
  `phone`            VARCHAR(15) NOT NULL,
  `password`         VARCHAR(32) NULL     DEFAULT NULL,
  `app_id`           VARCHAR(20) NULL     DEFAULT NULL,
  `creation_date`    DATETIME    NULL     DEFAULT NULL,
  `last_login_date`  DATETIME    NULL     DEFAULT NULL,
  `last_update_date` DATETIME    NOT NULL,
  `available`        TINYINT(1)  NOT NULL DEFAULT '1',
  `profile_type`     TINYINT(1)  NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `phone_UNIQUE` ON `fmc_edu`.`profile` (`phone` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`teacher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`teacher`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`teacher` (
  `profile_id`  INT(11)    NOT NULL,
  `school_id`   INT(11)    NOT NULL,
  `head_teacher` TINYINT(1) NOT NULL DEFAULT '0',
  `initialized` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`profile_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Teacher_School1_idx` ON `fmc_edu`.`teacher` (`school_id` ASC);

CREATE INDEX `fk_Teacher_Profile1_idx` ON `fmc_edu`.`teacher` (`profile_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`parent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`parent`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`parent` (
  `profile_id`          INT(11)    NOT NULL,
  `address_id`          INT(11)    NULL     DEFAULT NULL,
  `paid`                TINYINT(1) NOT NULL DEFAULT '0',
  `free_trial`          TINYINT(1) NOT NULL DEFAULT '0',
  `free_trial_end_date` DATETIME   NULL     DEFAULT NULL,
  PRIMARY KEY (`profile_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Parent_Address1_idx` ON `fmc_edu`.`parent` (`address_id` ASC);

CREATE INDEX `fk_Parent_Profile1_idx` ON `fmc_edu`.`parent` (`profile_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`temp_parent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`temp_parent`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`temp_parent` (
  `profile_id`       INT(11)  NOT NULL,
  `identifying_code` VARCHAR(20) NULL DEFAULT NULL,
  `identifying_date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`profile_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Temp_Parent_Profile1_idx` ON `fmc_edu`.`temp_parent` (`profile_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`parent_student_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`parent_student_map`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`parent_student_map` (
  `parent_id`     INT(11)    NOT NULL,
  `student_id`    INT(11)    NOT NULL,
  `approved`      TINYINT(1) NOT NULL DEFAULT '0',
  `approved_date` DATETIME   NULL     DEFAULT NULL,
  PRIMARY KEY (`parent_id`, `student_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Parent_has_Student_Student1_idx` ON `fmc_edu`.`parent_student_map` (`student_id` ASC);

CREATE INDEX `fk_Parent_has_Student_Parent1_idx` ON `fmc_edu`.`parent_student_map` (`parent_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`teacher_class_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`teacher_class_map`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`teacher_class_map` (
  `teacher_id`       INT(11)     NOT NULL,
  `class_id`         INT(11)     NOT NULL,
  `sub_title`        VARCHAR(20) NULL     DEFAULT NULL,
  `head_teacher`     TINYINT(1)  NOT NULL DEFAULT '0',
  `creation_date`    DATETIME    NULL     DEFAULT NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`teacher_id`, `class_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Teacher_has_Class_Class1_idx` ON `fmc_edu`.`teacher_class_map` (`class_id` ASC);

CREATE INDEX `fk_Teacher_has_Class_Teacher1_idx` ON `fmc_edu`.`teacher_class_map` (`teacher_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`address`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`address` (
  `id`               INT(11)      NOT NULL AUTO_INCREMENT,
  `province_id`      INT(11)      NOT NULL,
  `city_id`          INT(11)      NOT NULL,
  `full_address`     VARCHAR(200) NULL     DEFAULT NULL,
  `creation_date`    DATETIME     NULL     DEFAULT NULL,
  `last_update_date` DATETIME     NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_address_city1_idx` ON `fmc_edu`.`address` (`city_id` ASC);

CREATE INDEX `fk_address_province1_idx` ON `fmc_edu`.`address` (`province_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`city`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`city`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`city` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(40) NOT NULL,
  `province_id`      INT(11)     NOT NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = MyISAM
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_city_province_idx` ON `fmc_edu`.`city` (`province_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`class`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`class`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`class` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `school_id`        INT(11)     NOT NULL,
  `grade`            VARCHAR(50) NOT NULL,
  `class`            VARCHAR(50) NOT NULL,
  `available`        TINYINT(1)  NOT NULL DEFAULT '1',
  `head_teacher_id`  INT(11)     NULL     DEFAULT NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_class_school1_idx` ON `fmc_edu`.`class` (`school_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`parent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`parent`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`parent` (
  `profile_id`          INT(11)    NOT NULL,
  `address_id`          INT(11)    NULL     DEFAULT NULL,
  `paid`                TINYINT(1) NOT NULL DEFAULT '0',
  `free_trial`          TINYINT(1) NOT NULL DEFAULT '0',
  `free_trial_end_date` DATETIME   NULL     DEFAULT NULL,
  PRIMARY KEY (`profile_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_parent_address1_idx` ON `fmc_edu`.`parent` (`address_id` ASC);

CREATE INDEX `fk_parent_profile1_idx` ON `fmc_edu`.`parent` (`profile_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`parent_student_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`parent_student_map`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`parent_student_map` (
  `parent_id`     INT(11)    NOT NULL,
  `student_id`    INT(11)    NOT NULL,
  `approved`      TINYINT(1) NOT NULL DEFAULT '0',
  `approved_date` DATETIME   NULL     DEFAULT NULL,
  PRIMARY KEY (`parent_id`, `student_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_parent_has_student_student1_idx` ON `fmc_edu`.`parent_student_map` (`student_id` ASC);

CREATE INDEX `fk_parent_has_student_parent1_idx` ON `fmc_edu`.`parent_student_map` (`parent_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`profile`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`profile` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(20) NULL     DEFAULT NULL,
  `phone`            VARCHAR(15) NOT NULL,
  `password`         VARCHAR(32) NULL     DEFAULT NULL,
  `app_id`           VARCHAR(20) NULL     DEFAULT NULL,
  `creation_date`    DATETIME    NULL     DEFAULT NULL,
  `last_login_date`  DATETIME    NULL     DEFAULT NULL,
  `last_update_date` DATETIME    NOT NULL,
  `available`        TINYINT(1)  NOT NULL DEFAULT '1',
  `profile_type`     TINYINT(1)  NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fmc_edu`.`province`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`province`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`province` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(40) NOT NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = MyISAM
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `fmc_edu`.`school`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`school`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`school` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(80) NOT NULL,
  `province_id`      INT(11)     NOT NULL,
  `city_id`          INT(11)     NOT NULL,
  `address_id`       INT(11)     NULL     DEFAULT NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_school_city1_idx` ON `fmc_edu`.`school` (`city_id` ASC);

CREATE INDEX `fk_school_address1_idx` ON `fmc_edu`.`school` (`address_id` ASC);

CREATE INDEX `fk_school_province1_idx` ON `fmc_edu`.`school` (`province_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`student`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`student` (
  `id`               INT(11)     NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(20) NOT NULL,
  `class_id`         INT(11)     NOT NULL,
  `sex`              TINYINT(1)  NULL     DEFAULT NULL,
  `birth`            DATE        NULL     DEFAULT NULL,
  `ring_number`      VARCHAR(45) NULL     DEFAULT NULL,
  `creation_date`    DATETIME    NULL     DEFAULT NULL,
  `available`        TINYINT(1)  NOT NULL DEFAULT '1',
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_student_class1_idx` ON `fmc_edu`.`student` (`class_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`teacher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`teacher`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`teacher` (
  `profile_id`  INT(11)    NOT NULL,
  `school_id`   INT(11)    NOT NULL,
  `head_teacher` TINYINT(1) NOT NULL DEFAULT '0',
  `initialized` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`profile_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_teacher_school1_idx` ON `fmc_edu`.`teacher` (`school_id` ASC);

CREATE INDEX `fk_teacher_profile1_idx` ON `fmc_edu`.`teacher` (`profile_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`teacher_class_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`teacher_class_map`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`teacher_class_map` (
  `teacher_id`       INT(11)     NOT NULL,
  `class_id`         INT(11)     NOT NULL,
  `sub_title`        VARCHAR(20) NULL     DEFAULT NULL,
  `head_teacher`     TINYINT(1)  NOT NULL DEFAULT '0',
  `creation_date`    DATETIME    NULL     DEFAULT NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`teacher_id`, `class_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_teacher_has_class_class1_idx` ON `fmc_edu`.`teacher_class_map` (`class_id` ASC);

CREATE INDEX `fk_teacher_has_class_teacher1_idx` ON `fmc_edu`.`teacher_class_map` (`teacher_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`temp_parent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`temp_parent`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`temp_parent` (
  `profile_id`       INT(11)  NOT NULL,
  `identifying_code` VARCHAR(20) NULL DEFAULT NULL,
  `identifying_date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`profile_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_temp_parent_profile1_idx` ON `fmc_edu`.`temp_parent` (`profile_id` ASC);


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
