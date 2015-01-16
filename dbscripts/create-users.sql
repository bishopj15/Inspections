DROP SCHEMA IF EXISTS `po_inspections`;
CREATE SCHEMA IF NOT EXISTS `po_inspections` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


-- CREATE INSPECTION DATABASE USER --
grant all privileges on po_inspections.* to 'inspectadmin'@'%' identified by 'password1' with grant option;
grant all privileges on po_inspections.* to 'inspectadmin'@'127.0.0.1' identified by 'password1' with grant option;
grant all privileges on po_inspections.* to 'inspectadmin'@'localhost' identified by 'password1'with grant option;

flush privileges;