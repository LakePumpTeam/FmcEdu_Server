-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema demo_fmc_edu
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `demo_fmc_edu` ;

-- -----------------------------------------------------
-- Schema demo_fmc_edu
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `demo_fmc_edu` DEFAULT CHARACTER SET utf8 ;
USE `demo_fmc_edu` ;

-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`province`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`province` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`province` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) NOT NULL,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`city`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`city` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`city` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) NOT NULL,
  `province_id` INT NOT NULL,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`address` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `province_Id` INT NOT NULL,
  `city_Id` INT NOT NULL,
  `full_address` VARCHAR(200) NULL,
  `creation_date` DATETIME NULL,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`school`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`school` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`school` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `province_id` INT NOT NULL,
  `city_id` INT NOT NULL,
  `address_id` INT NULL,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`class`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`class` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`class` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `school_id` INT NOT NULL,
  `grade` VARCHAR(50) NOT NULL,
  `class` VARCHAR(50) NOT NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 1,
  `head_teacher_id` INT NULL,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`student` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`student` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `class_id` INT NOT NULL,
  `sex` TINYINT(1) NULL,
  `birth` DATE NULL,
  `ring_number` VARCHAR(45) NULL,
  `ring_phone` VARCHAR(11) NULL,
  `creation_date` DATETIME NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 1,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`profile` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`profile` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL,
  `phone` VARCHAR(11) NOT NULL,
  `password` VARCHAR(32) NULL,
  `salt` VARCHAR(32) NULL,
  `app_id` VARCHAR(20) NULL,
  `channel_id` DECIMAL(20) NULL,
  `creation_date` DATETIME NULL,
  `last_login_date` DATETIME NULL,
  `last_update_date` DATETIME NOT NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 1,
  `profile_type` TINYINT(1) NOT NULL DEFAULT 1,
  `last_pc_id` INT NULL DEFAULT 0,
  `last_sdat_id` INT NULL DEFAULT 0,
  `last_sdnf_id` INT NULL DEFAULT 0,
  `last_sdnw_id` INT NULL DEFAULT 0,
  `last_cl_id` INT NULL DEFAULT 0,
  `last_pce_id` INT NULL DEFAULT 0,
  `last_bbs_id` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`teacher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`teacher` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`teacher` (
  `profile_id` INT NOT NULL,
  `school_id` INT NOT NULL,
  `sex` TINYINT(1) NOT NULL DEFAULT 0,
  `head_teacher` TINYINT(1) NOT NULL DEFAULT 0,
  `initialized` TINYINT(1) NOT NULL DEFAULT 0,
  `resume` VARCHAR(400) NULL,
  `course` VARCHAR(50) NULL,
  `birth` DATE NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`parent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`parent` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`parent` (
  `profile_id` INT NOT NULL,
  `address_id` INT NULL,
  `paid` TINYINT(1) NOT NULL DEFAULT 0,
  `free_trial` TINYINT(1) NOT NULL DEFAULT 0,
  `free_trial_end_date` DATETIME NULL,
  PRIMARY KEY (`profile_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`identity_code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`identity_code` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`identity_code` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `phone` VARCHAR(11) NOT NULL,
  `identifying_code` VARCHAR(20) NULL,
  `identifying_end_date` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`parent_student_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`parent_student_map` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`parent_student_map` (
  `parent_id` INT NOT NULL,
  `student_id` INT NOT NULL,
  `relationship` VARCHAR(20) NULL,
  `approved` TINYINT(1) NOT NULL DEFAULT 0,
  `approved_date` DATETIME NULL,
  `creation_date` DATETIME NULL,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`parent_id`, `student_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`teacher_class_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`teacher_class_map` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`teacher_class_map` (
  `teacher_id` INT NOT NULL,
  `class_id` INT NOT NULL,
  `sub_title` VARCHAR(20) NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 1,
  `head_teacher` TINYINT(1) NOT NULL DEFAULT 0,
  `creation_date` DATETIME NULL,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`teacher_id`, `class_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`news`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`news` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`news` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `author` INT NULL,
  `subject` VARCHAR(60) NULL,
  `content` VARCHAR(4000) NOT NULL,
  `news_type` TINYINT(1) NOT NULL DEFAULT 0,
  `ref_id` INT NULL,
  `available` TINYINT(1) NULL DEFAULT 1,
  `approved` TINYINT(1) NULL DEFAULT 0,
  `approved_by` INT NULL,
  `approve_date` DATETIME NULL,
  `like` INT NULL DEFAULT 0,
  `popular` TINYINT(1) NOT NULL DEFAULT 0,
  `publish_date` DATETIME NULL,
  `creation_date` DATETIME NOT NULL,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`task` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`task` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(150) NOT NULL,
  `task` VARCHAR(360) NOT NULL,
  `deadline` DATETIME NOT NULL,
  `creation_date` DATETIME NOT NULL,
  `last_update_date` DATETIME NULL,
  `publish_user_id` INT NOT NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`comments` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`comments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ref_id` INT NOT NULL,
  `profile_id` INT NOT NULL,
  `comment` VARCHAR(500) NOT NULL,
  `creation_date` DATETIME NULL,
  `last_update_date` DATETIME NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`timetable`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`timetable` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`timetable` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `class_id` INT NOT NULL,
  `available` TINYINT(1) NULL DEFAULT 1,
  `creation_date` DATETIME NULL,
  `last_update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`course` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`course` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `timetable_id` INT NOT NULL,
  `course_name` VARCHAR(45) NOT NULL,
  `order` INT NOT NULL,
  `order_name` VARCHAR(45) NULL,
  `start_time` TIME NOT NULL,
  `end_time` TIME NOT NULL,
  `week` INT NULL,
  `available` TINYINT(1) NULL DEFAULT 1,
  `last_update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`image` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `news_id` INT NOT NULL,
  `img_name` VARCHAR(80) NOT NULL,
  `img_path` VARCHAR(100) NOT NULL,
  `creation_date` DATETIME NULL,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`slide`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`slide` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`slide` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `subject` VARCHAR(60) NULL,
  `news_id` INT NOT NULL,
  `profile_id` INT NOT NULL,
  `img_name` VARCHAR(80) NOT NULL,
  `img_path` VARCHAR(100) NOT NULL,
  `order` INT NOT NULL DEFAULT 0,
  `start_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 1,
  `creation_date` DATETIME NULL,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`profile_like_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`profile_like_map` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`profile_like_map` (
  `profile_id` INT NOT NULL,
  `news_id` INT NOT NULL,
  `creation_date` DATETIME NULL)
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`permission` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`permission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `permission` VARCHAR(45) NOT NULL,
  `description` VARCHAR(200) NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 1,
  `last_update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`role` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL,
  `description` VARCHAR(200) NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 1,
  `last_update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`role_permission_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`role_permission_map` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`role_permission_map` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_id` INT NOT NULL,
  `permission_id` INT NOT NULL,
  `last_update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`user_role_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`user_role_map` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`user_role_map` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `profile_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  `last_update_date` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`task_student_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`task_student_map` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`task_student_map` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `task_id` INT NOT NULL,
  `student_id` INT NOT NULL,
  `is_complete` TINYINT(1) NOT NULL DEFAULT 0,
  `creation_date` DATETIME NULL,
  `last_update_date` DATETIME NULL,
  `available` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`selection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`selection` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`selection` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `news_id` INT NOT NULL,
  `selection` VARCHAR(80) NOT NULL,
  `sort_order` TINYINT(1) NOT NULL,
  `creation_date` DATETIME NULL,
  `last_update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `demo_fmc_edu`.`profile_selection_map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `demo_fmc_edu`.`profile_selection_map` ;

CREATE TABLE IF NOT EXISTS `demo_fmc_edu`.`profile_selection_map` (
  `news_id` INT NOT NULL,
  `selection_id` INT NOT NULL,
  `profile_id` INT NOT NULL,
  `creation_date` DATETIME NULL)
ENGINE = MyISAM;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
