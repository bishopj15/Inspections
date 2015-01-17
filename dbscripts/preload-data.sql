USE `po_inspections`;

-- ADD SYSTEM USERS --
INSERT INTO system_users (userid, password, first_name, last_name, email, last_login)
VALUES ("admin", sha2("admin", 256), "Boss", "Boss", "boss@boss.org", NOW());