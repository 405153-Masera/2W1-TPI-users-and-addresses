--CREATE DATABASE IF NOT EXISTS contacts;

--CREATE USER IF NOT EXISTS 'contact_user'@'%' IDENTIFIED BY 'contact_password';
--GRANT ALL PRIVILEGES ON contacts.* TO 'contact_user'@'%';
--FLUSH PRIVILEGES;

--USE contacts;

-- Tabla PersonType
CREATE TABLE person_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255),
    created_datetime DATETIME,
    created_user INT,
    last_updated_datetime DATETIME,
    last_updated_user INT
    );

-- Tabla ContactType
CREATE TABLE contact_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255),
    created_datetime DATETIME,
    created_user INT,
    last_updated_datetime DATETIME,
    last_updated_user INT
    );

-- Tabla Contact
CREATE TABLE contact (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    person_type_id INT,
    contact_type_id INT,
    contact_value VARCHAR(255),
    created_datetime DATETIME,
    created_user INT,
    last_updated_datetime DATETIME,
    last_updated_user INT,
    FOREIGN KEY (person_type_id) REFERENCES person_type(id),
    FOREIGN KEY (contact_type_id) REFERENCES contact_type(id)
    );

-- Tablas de auditoría
CREATE TABLE person_type_audit (
    id VARCHAR(255) PRIMARY KEY,
    person_type_id INT,
    version INT,
    type VARCHAR(255),
    created_datetime DATETIME,
    created_user INT,
    last_updated_datetime DATETIME,
    last_updated_user INT
    );

CREATE TABLE contact_type_audit (
    id VARCHAR(255) PRIMARY KEY,
    contact_type_id INT,
    version INT,
    type VARCHAR(255),
    created_datetime DATETIME,
    created_user INT,
    last_updated_datetime DATETIME,
    last_updated_user INT
    );

CREATE TABLE contact_audit (
    id VARCHAR(255) PRIMARY KEY,
    contact_id INT,
    version INT,
    user_id INT,
    person_type_id INT,
    contact_type_id INT,
    contact_value VARCHAR(255),
    created_datetime DATETIME,
    created_user INT,
    last_updated_datetime DATETIME,
    last_updated_user INT
    );

-- Trigger para insertar en la auditoría de PersonType
DELIMITER $$
CREATE TRIGGER trg_person_type_insert
    AFTER INSERT ON person_type
    FOR EACH ROW
BEGIN
    INSERT INTO person_type_audit (id, person_type_id, version, type, created_datetime, created_user, last_updated_datetime, last_updated_user)
    VALUES (NEW.id, NEW.id, 0, NEW.type, NEW.created_datetime, NEW.created_user, NEW.last_updated_datetime, NEW.last_updated_user);
END $$
DELIMITER ;

-- Trigger para actualizar en la auditoría de PersonType
DELIMITER $$
CREATE TRIGGER trg_person_type_update
    AFTER UPDATE ON person_type
    FOR EACH ROW
BEGIN
    DECLARE latest_version INT;
    SELECT MAX(version) INTO latest_version FROM person_type_audit WHERE id = NEW.id;
    SET latest_version = IFNULL(latest_version, 0) + 1;

    INSERT INTO person_type_audit (id, person_type_id, version, type, created_datetime, created_user, last_updated_datetime, last_updated_user)
    VALUES (CONCAT(NEW.id, '.', latest_version), NEW.id, latest_version, NEW.type, NEW.created_datetime, NEW.created_user, NEW.last_updated_datetime, NEW.last_updated_user);
END $$
DELIMITER ;

-- Trigger para insertar en la auditoría de ContactType
DELIMITER $$
CREATE TRIGGER trg_contact_type_insert
    AFTER INSERT ON contact_type
    FOR EACH ROW
BEGIN
    INSERT INTO contact_type_audit (id, contact_type_id, version, type, created_datetime, created_user, last_updated_datetime, last_updated_user)
    VALUES (NEW.id, NEW.id, 0, NEW.type, NEW.created_datetime, NEW.created_user, NEW.last_updated_datetime, NEW.last_updated_user);
END $$
DELIMITER ;

-- Trigger para actualizar en la auditoría de ContactType
DELIMITER $$
CREATE TRIGGER trg_contact_type_update
    AFTER UPDATE ON contact_type
    FOR EACH ROW
BEGIN
    DECLARE latest_version INT;
    SELECT MAX(version) INTO latest_version FROM contact_type_audit WHERE id = NEW.id;
    SET latest_version = IFNULL(latest_version, 0) + 1;

    INSERT INTO contact_type_audit (id, contact_type_id, version, type, created_datetime, created_user, last_updated_datetime, last_updated_user)
    VALUES (CONCAT(NEW.id, '.', latest_version), NEW.id, latest_version, NEW.type, NEW.created_datetime, NEW.created_user, NEW.last_updated_datetime, NEW.last_updated_user);
END $$
DELIMITER ;

-- Trigger para insertar en la auditoría de Contact
DELIMITER $$
CREATE TRIGGER trg_contact_insert
    AFTER INSERT ON contact
    FOR EACH ROW
BEGIN
    INSERT INTO contact_audit (id, contact_id, version, user_id, person_type_id, contact_type_id, contact_value, created_datetime, created_user, last_updated_datetime, last_updated_user)
    VALUES (NEW.id, NEW.id, 0, NEW.user_id, NEW.person_type_id, NEW.contact_type_id, NEW.contact_value, NEW.created_datetime, NEW.created_user, NEW.last_updated_datetime, NEW.last_updated_user);
END $$
DELIMITER ;

-- Trigger para actualizar en la auditoría de Contact
DELIMITER $$
CREATE TRIGGER trg_contact_update
    AFTER UPDATE ON contact
    FOR EACH ROW
BEGIN
    DECLARE latest_version INT;
    SELECT MAX(version) INTO latest_version FROM contact_audit WHERE id = NEW.id;
    SET latest_version = IFNULL(latest_version, 0) + 1;

    INSERT INTO contact_audit (id, contact_id, version, user_id, person_type_id, contact_type_id, contact_value, created_datetime, created_user, last_updated_datetime, last_updated_user)
    VALUES (CONCAT(NEW.id, '.', latest_version), NEW.id, latest_version, NEW.user_id, NEW.person_type_id, NEW.contact_type_id, NEW.contact_value, NEW.created_datetime, NEW.created_user, NEW.last_updated_datetime, NEW.last_updated_user);
END $$
DELIMITER ;

INSERT INTO contact_type (type, created_datetime, created_user, last_updated_datetime, last_updated_user)
VALUES ('Correo Electrónico', NOW(), 1, NOW(), 1),
       ('Teléfono Móvil', NOW(), 1, NOW(), 1);

INSERT INTO person_type (type, created_datetime, created_user, last_updated_datetime, last_updated_user)
VALUES ('Empleado', NOW(), 1, NOW(), 1),
       ('Proveedor', NOW(), 1, NOW(), 1),
       ('Usuario', NOW(), 1, NOW(), 1);