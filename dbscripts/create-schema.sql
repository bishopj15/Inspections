-- SETUP SCHEMA --
DROP SCHEMA IF EXISTS `po_inspections`;
CREATE SCHEMA IF NOT EXISTS `po_inspections` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE po_inspections;

DROP TABLE IF EXISTS `system_users`;
CREATE TABLE IF NOT EXISTS `system_users` (
	`pkey` INT NOT NULL AUTO_INCREMENT,
	`userid` VARCHAR(30) NOT NULL,
	`password` VARCHAR(80) NULL,
	`first_name` VARCHAR(45) NULL,
  	`last_name` VARCHAR(45) NULL,
  	`email` VARCHAR(45) NULL,
  	`last_login` DATETIME NULL,
  	PRIMARY KEY (`pkey`),
  	UNIQUE INDEX `pkey_UNIQUE` (`pkey` ASC),
  	UNIQUE INDEX `name_UNIQUE` (`userid` ASC)
	)
	ENGINE = InnoDB;