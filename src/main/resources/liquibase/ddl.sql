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
  `id`               INT         NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(40) NOT NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `fmc_edu`.`city`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`city`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`city` (
  `id`               INT         NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(40) NOT NULL,
  `province_id`      INT         NOT NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `fmc_edu`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`address`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`address` (
  `id`               INT          NOT NULL AUTO_INCREMENT,
  `province_Id`      INT          NOT NULL,
  `city_Id`          INT          NOT NULL,
  `full_address`     VARCHAR(200) NULL,
  `creation_date`    DATETIME     NULL,
  `last_update_date` DATETIME     NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fmc_edu`.`school`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`school`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`school` (
  `id`               INT         NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(80) NOT NULL,
  `province_id`      INT         NOT NULL,
  `city_id`          INT         NOT NULL,
  `address_id`       INT         NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fmc_edu`.`class`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`class`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`class` (
  `id`               INT         NOT NULL AUTO_INCREMENT,
  `school_id`        INT         NOT NULL,
  `grade`            VARCHAR(50) NOT NULL,
  `class`            VARCHAR(50) NOT NULL,
  `available`        TINYINT(1)  NOT NULL DEFAULT 1,
  `head_teacher_id`  INT         NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fmc_edu`.`student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`student`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`student` (
  `id`               INT         NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(20) NOT NULL,
  `class_id`         INT         NOT NULL,
  `sex`              TINYINT(1)  NULL,
  `birth`            DATE        NULL,
  `ring_number`      VARCHAR(45) NULL,
  `ring_phone`       VARCHAR(11) NULL,
  `creation_date`    DATETIME    NULL,
  `available`        TINYINT(1)  NOT NULL DEFAULT 1,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fmc_edu`.`profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`profile`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`profile` (
  `id`               INT         NOT NULL AUTO_INCREMENT,
  `name`             VARCHAR(20) NULL,
  `phone`            VARCHAR(11) NOT NULL,
  `password`         VARCHAR(32) NULL,
  `app_id`           VARCHAR(20) NULL,
  `channel_id`       DECIMAL(20) NULL,
  `creation_date`    DATETIME    NULL,
  `last_login_date`  DATETIME    NULL,
  `last_update_date` DATETIME    NOT NULL,
  `available`        TINYINT(1)  NOT NULL DEFAULT 1,
  `profile_type`     TINYINT(1)  NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC)
)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fmc_edu`.`teacher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`teacher`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`teacher` (
  `profile_id`   INT        NOT NULL,
  `school_id`    INT        NOT NULL,
  `sex`          TINYINT(1) NOT NULL DEFAULT 0,
  `head_teacher` TINYINT(1) NOT NULL DEFAULT 0,
  `initialized`  TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`profile_id`)
)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fmc_edu`.`parent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`parent`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`parent` (
  `profile_id`          INT        NOT NULL,
  `address_id`          INT        NULL,
  `paid`                TINYINT(1) NOT NULL DEFAULT 0,
  `free_trial`          TINYINT(1) NOT NULL DEFAULT 0,
  `free_trial_end_date` DATETIME   NULL,
  PRIMARY KEY (`profile_id`)
)
  ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `fmc_edu`.`identity_code` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `profile_id` INT NOT NULL,
  `identifying_code` VARCHAR(20) NULL,
  `identifying_end_date` DATETIME NULL,
  INDEX `fk_Temp_Parent_Profile1_idx` (`profile_id` ASC),
  PRIMARY KEY (`id`, `profile_id`),
  CONSTRAINT `fk_Temp_Parent_Profile1`
    FOREIGN KEY (`profile_id`)
    REFERENCES `fmc_edu`.`profile` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB

-- -----------------------------------------------------
-- Table `fmc_edu`.`parent_student_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`parent_student_map`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`parent_student_map` (
  `parent_id`        INT         NOT NULL,
  `student_id`       INT         NOT NULL,
  `relationship`     VARCHAR(20) NULL,
  `approved`         TINYINT(1)  NOT NULL DEFAULT 0,
  `approved_date`    DATETIME    NULL,
  `creation_date`    DATETIME    NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`parent_id`, `student_id`)
)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fmc_edu`.`teacher_class_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`teacher_class_map`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`teacher_class_map` (
  `teacher_id`       INT         NOT NULL,
  `class_id`         INT         NOT NULL,
  `sub_title`        VARCHAR(20) NULL,
  `head_teacher`     TINYINT(1)  NOT NULL DEFAULT 0,
  `creation_date`    DATETIME    NULL,
  `last_update_date` DATETIME    NOT NULL,
  PRIMARY KEY (`teacher_id`, `class_id`)
)
  ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
