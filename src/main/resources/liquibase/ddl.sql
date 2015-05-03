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
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;
USE `fmc_edu`;

-- -----------------------------------------------------
-- Table `fmc_edu`.`Province`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`Province`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`Province` (
  `Id`               INT      NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(40) NOT NULL,
  `Last_Update_Date` DATETIME NOT NULL,
  PRIMARY KEY (`Id`)
)
  ENGINE = MyISAM;


-- -----------------------------------------------------
-- Table `fmc_edu`.`City`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`City`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`City` (
  `Id`               INT         NOT NULL AUTO_INCREMENT,
  `Name`             VARCHAR(40) NOT NULL,
  `Province_Id`      INT         NOT NULL,
  `Last_Update_Date` DATETIME    NOT NULL,
  PRIMARY KEY (`Id`)
)
  ENGINE = MyISAM;

CREATE INDEX `fk_City_Province_idx` ON `fmc_edu`.`City` (`Province_Id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`Address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`Address`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`Address` (
  `Id`               INT      NOT NULL AUTO_INCREMENT,
  `Full_Address` VARCHAR(200) NULL,
  `Province_Id`      INT      NOT NULL,
  `City_Id`          INT      NOT NULL,
  `Last_Update_Date` DATETIME NOT NULL,
  PRIMARY KEY (`Id`)
)
  ENGINE = MyISAM;

CREATE INDEX `fk_Address_Province1_idx` ON `fmc_edu`.`Address` (`Province_Id` ASC);

CREATE INDEX `fk_Address_City1_idx` ON `fmc_edu`.`Address` (`City_Id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`School`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`School`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`School` (
  `Id`               INT         NOT NULL AUTO_INCREMENT,
  `Name`             VARCHAR(80) NULL,
  `Province_Id`      INT         NOT NULL,
  `City_Id`          INT         NOT NULL,
  `Address_Id`       INT         NULL,
  `Last_Update_Date` DATETIME    NOT NULL,
  PRIMARY KEY (`Id`)
)
  ENGINE = MyISAM;

CREATE INDEX `fk_School_City1_idx` ON `fmc_edu`.`School` (`City_Id` ASC);

CREATE INDEX `fk_School_Address1_idx` ON `fmc_edu`.`School` (`Address_Id` ASC);

CREATE INDEX `fk_School_Province1_idx` ON `fmc_edu`.`School` (`Province_Id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`Teacher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`Teacher`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`Teacher` (
  `Id`               INT         NOT NULL AUTO_INCREMENT,
  `Name`             VARCHAR(20) NOT NULL,
  `Phone`            VARCHAR(11) NOT NULL,
  `Password`         VARCHAR(32) NOT NULL,
  `School_Id`        INT         NOT NULL,
  `Head_Teacher`     TINYINT(1)  NOT NULL,
  `App_Id`           VARCHAR(20) NULL,
  `Available`        TINYINT(1)  NULL     DEFAULT 1,
  `Last_Update_Date` DATETIME    NOT NULL,
  PRIMARY KEY (`Id`)
)
  ENGINE = MyISAM;

CREATE INDEX `fk_Teacher_School1_idx` ON `fmc_edu`.`Teacher` (`School_Id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`Class`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`Class`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`Class` (
  `Id`               INT         NOT NULL AUTO_INCREMENT,
  `School_id`        INT         NOT NULL,
  `Grade`            VARCHAR(50) NOT NULL,
  `Class`            VARCHAR(50) NOT NULL,
  `Available`        TINYINT(1)  NOT NULL DEFAULT 1,
  `Head_Teacher_Id`  INT         NULL,
  `Last_Update_Date` DATETIME    NOT NULL,
  PRIMARY KEY (`Id`)
)
  ENGINE = MyISAM;

CREATE INDEX `fk_Class_School1_idx` ON `fmc_edu`.`Class` (`School_id` ASC);

CREATE INDEX `fk_Class_Teacher1_idx` ON `fmc_edu`.`Class` (`Head_Teacher_Id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`Student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`Student`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`Student` (
  `Id`               INT         NOT NULL AUTO_INCREMENT,
  `Name`             VARCHAR(20) NOT NULL,
  `Class_id`         INT         NOT NULL,
  `Sex`              TINYINT(1)  NULL,
  `Birth`            DATE        NULL,
  `Ring_Number`      VARCHAR(45) NULL,
  `Creation_Date`    DATETIME    NULL,
  `Available`        TINYINT(1)  NULL     DEFAULT 1,
  `Last_Update_Date` DATETIME    NOT NULL,
  PRIMARY KEY (`Id`)
)
  ENGINE = MyISAM;

CREATE INDEX `fk_Student_Class1_idx` ON `fmc_edu`.`Student` (`Class_id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`Parent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`Parent`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`Parent` (
  `Id`               INT         NOT NULL AUTO_INCREMENT,
  `Name`             VARCHAR(20) NOT NULL,
  `Phone`            VARCHAR(11) NOT NULL,
  `Password`         VARCHAR(32) NOT NULL,
  `Student_Id`       INT         NOT NULL,
  `Address_Id`       INT         NULL,
  `Approved`         TINYINT(1)  NULL     DEFAULT 0,
  `Approved_Date`    DATETIME    NULL,
  `App_Id`           VARCHAR(19) NULL,
  `Creation_Date`    DATETIME    NULL,
  `Available`        TINYINT(1)  NULL     DEFAULT 1,
  `Last_Login_Date`  DATETIME    NULL,
  `Last_Update_Date` DATETIME    NOT NULL,
  PRIMARY KEY (`Id`)
)
  ENGINE = MyISAM;

CREATE INDEX `fk_Parent_Student1_idx` ON `fmc_edu`.`Parent` (`Student_Id` ASC);

CREATE INDEX `fk_Parent_Address1_idx` ON `fmc_edu`.`Parent` (`Address_Id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`Teacher_Class_Map`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`Teacher_Class_Map`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`Teacher_Class_Map` (
  `Teacher_Id`       INT         NOT NULL,
  `Class_Id`         INT         NOT NULL,
  `Sub_Title`        VARCHAR(20) NOT NULL,
  `Head_Tearcher`    TINYINT(1)  NULL DEFAULT 0,
  `Available`        TINYINT(1)  NULL DEFAULT 1,
  `Last_Update_Date` DATETIME    NOT NULL,
  PRIMARY KEY (`Teacher_Id`, `Class_Id`)
)
  ENGINE = MyISAM;

CREATE INDEX `fk_Teacher_has_Class_Class1_idx` ON `fmc_edu`.`Teacher_Class_Map` (`Class_Id` ASC);

CREATE INDEX `fk_Teacher_has_Class_Teacher1_idx` ON `fmc_edu`.`Teacher_Class_Map` (`Teacher_Id` ASC);


-- -----------------------------------------------------
-- Table `fmc_edu`.`Temp_Parent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fmc_edu`.`Temp_Parent`;

CREATE TABLE IF NOT EXISTS `fmc_edu`.`Temp_Parent` (
  `Id`               INT         NOT NULL AUTO_INCREMENT,
  `Phone`            VARCHAR(11) NOT NULL,
  `Identifying_Code` VARCHAR(20) NULL,
  `Identifying_Date` DATETIME    NULL,
  `Creation_Date`    DATETIME    NOT NULL,
  `Last_Update_Date` DATETIME    NULL,
  PRIMARY KEY (`Id`)
)
  ENGINE = MyISAM;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
