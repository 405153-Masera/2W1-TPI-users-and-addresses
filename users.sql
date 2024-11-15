
-- Tabla dni_type
CREATE TABLE dni_type (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          description VARCHAR(255),
                          created_datetime DATETIME,
                          last_updated_datetime DATETIME,
                          created_user INT,
                          last_updated_user INT
);


CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255),
                       lastname VARCHAR(255),
                       username VARCHAR(255),
                       password VARCHAR(255),
                       dni VARCHAR(255),
                       dni_type_id INT,
                       active TINYINT(1),
                       avatar_url VARCHAR(255),
                       birth_date DATE,
                       telegram_id INT,
                       created_datetime DATETIME,
                       last_updated_datetime DATETIME,
                       created_user INT,
                       last_updated_user INT,
                       FOREIGN KEY (dni_type_id) REFERENCES dni_type(id)
);

-- Tabla plotusers
CREATE TABLE plotusers (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           plot_id INT,
                           user_id INT,
                           created_datetime DATETIME,
                           last_updated_datetime DATETIME,
                           created_user INT,
                           last_updated_user INT,
                           FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Tabla roles
CREATE TABLE roles (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       description VARCHAR(255),
                       created_datetime DATETIME,
                       last_updated_datetime DATETIME,
                       created_user INT,
                       last_updated_user INT
);

-- Tabla userroles
CREATE TABLE userroles (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           role_id INT,
                           user_id INT,
                           created_datetime DATETIME,
                           last_updated_datetime DATETIME,
                           created_user INT,
                           last_updated_user INT,
                           FOREIGN KEY (role_id) REFERENCES roles(id),
                           FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Tablas de auditoría
CREATE TABLE users_audit (
                             version_id INT AUTO_INCREMENT PRIMARY KEY,
                             id INT,
                             version INT,
                             name VARCHAR(255),
                             lastname VARCHAR(255),
                             username VARCHAR(255),
                             password VARCHAR(255),
                             dni VARCHAR(255),
                             dni_type_id INT,
                             active TINYINT(1),
                             avatar_url VARCHAR(255),
                             birth_date DATE,
                             telegram_id INT,
                             created_datetime DATETIME,
                             last_updated_datetime DATETIME,
                             created_user INT,
                             last_updated_user INT
);

CREATE TABLE plotusers_audit (
                                 version_id INT AUTO_INCREMENT PRIMARY KEY,
                                 id INT,
                                 version INT,
                                 plot_id INT,
                                 user_id INT,
                                 created_datetime DATETIME,
                                 last_updated_datetime DATETIME,
                                 created_user INT,
                                 last_updated_user INT
);

CREATE TABLE roles_audit (
                             version_id INT AUTO_INCREMENT PRIMARY KEY,
                             id INT,
                             version INT,
                             description VARCHAR(255),
                             created_datetime DATETIME,
                             last_updated_datetime DATETIME,
                             created_user INT,
                             last_updated_user INT
);

CREATE TABLE userroles_audit (
                                 version_id INT AUTO_INCREMENT PRIMARY KEY,
                                 id INT,
                                 version INT,
                                 role_id INT,
                                 user_id INT,
                                 created_datetime DATETIME,
                                 last_updated_datetime DATETIME,
                                 created_user INT,
                                 last_updated_user INT
);


CREATE TABLE dni_type_audit (
                                version_id INT AUTO_INCREMENT PRIMARY KEY,
                                id INT,
                                version INT,
                                description VARCHAR(255),
                                created_datetime DATETIME,
                                last_updated_datetime DATETIME,
                                created_user INT,
                                last_updated_user INT
);

-- Triggers de auditoría
DELIMITER $$

-- Trigger para insertar en la auditoría de Users
CREATE TRIGGER trg_users_insert
    AFTER INSERT ON users
    FOR EACH ROW
BEGIN
    INSERT INTO users_audit (id, version, name, lastname, username, password, dni_type_id, dni, active, avatar_url, birth_date, telegram_id, created_datetime, last_updated_datetime, created_user, last_updated_user)
    VALUES (NEW.id, 1, NEW.name, NEW.lastname, NEW.username, NEW.password, NEW.dni_type_id,NEW.dni , NEW.active, NEW.avatar_url, NEW.birth_date, NEW.telegram_id, NEW.created_datetime, NEW.last_updated_datetime, NEW.created_user, NEW.last_updated_user);
END $$

-- Trigger para actualizar en la auditoría de Users
CREATE TRIGGER trg_users_update
    AFTER UPDATE ON users
    FOR EACH ROW
BEGIN
    DECLARE latest_version INT;
    SELECT MAX(version) INTO latest_version FROM users_audit WHERE id = NEW.id;
    SET latest_version = IFNULL(latest_version, 0) + 1;

    INSERT INTO users_audit (id, version, name, lastname, username, password, dni_type_id, dni, active, avatar_url, birth_date, telegram_id, created_datetime, last_updated_datetime, created_user, last_updated_user)
    VALUES (NEW.id, latest_version, NEW.name, NEW.lastname, NEW.username, NEW.password, NEW.dni_type_id, NEW.dni, NEW.active, NEW.avatar_url, NEW.birth_date, NEW.telegram_id, NEW.created_datetime, NEW.last_updated_datetime, NEW.created_user, NEW.last_updated_user);
END $$

-- Trigger para insertar en la auditoría de PlotUsers
CREATE TRIGGER trg_plotusers_insert
    AFTER INSERT ON plotusers
    FOR EACH ROW
BEGIN
    INSERT INTO plotusers_audit (id, version, plot_id, user_id, created_datetime, last_updated_datetime, created_user, last_updated_user)
    VALUES (NEW.id, 1, NEW.plot_id, NEW.user_id, NEW.created_datetime, NEW.last_updated_datetime, NEW.created_user, NEW.last_updated_user);
END $$

-- Trigger para actualizar en la auditoría de PlotUsers
CREATE TRIGGER trg_plotusers_update
    AFTER UPDATE ON plotusers
    FOR EACH ROW
BEGIN
    DECLARE latest_version INT;
    SELECT MAX(version) INTO latest_version FROM plotusers_audit WHERE id = NEW.id;
    SET latest_version = IFNULL(latest_version, 0) + 1;

    INSERT INTO plotusers_audit (id, version, plot_id, user_id, created_datetime, last_updated_datetime, created_user, last_updated_user)
    VALUES (NEW.id, latest_version, NEW.plot_id, NEW.user_id, NEW.created_datetime, NEW.last_updated_datetime, NEW.created_user, NEW.last_updated_user);
END $$

-- Trigger para insertar en la auditoría de Roles
CREATE TRIGGER trg_roles_insert
    AFTER INSERT ON roles
    FOR EACH ROW
BEGIN
    INSERT INTO roles_audit (id, version, description, created_datetime, last_updated_datetime, created_user, last_updated_user)
    VALUES (NEW.id, 1, NEW.description, NEW.created_datetime, NEW.last_updated_datetime, NEW.created_user, NEW.last_updated_user);
END $$

-- Trigger para actualizar en la auditoría de Roles
CREATE TRIGGER trg_roles_update
    AFTER UPDATE ON roles
    FOR EACH ROW
BEGIN
    DECLARE latest_version INT;
    SELECT MAX(version) INTO latest_version FROM roles_audit WHERE id = NEW.id;
    SET latest_version = IFNULL(latest_version, 0) + 1;

    INSERT INTO roles_audit (id, version, description, created_datetime, last_updated_datetime, created_user, last_updated_user)
    VALUES (NEW.id, latest_version, NEW.description, NEW.created_datetime, NEW.last_updated_datetime, NEW.created_user, NEW.last_updated_user);
END $$

-- Trigger para insertar en la auditoría de UserRoles
CREATE TRIGGER trg_userroles_insert
    AFTER INSERT ON userroles
    FOR EACH ROW
BEGIN
    INSERT INTO userroles_audit (id, version, role_id, user_id, created_datetime, last_updated_datetime, created_user, last_updated_user)
    VALUES (NEW.id, 1, NEW.role_id, NEW.user_id, NEW.created_datetime, NEW.last_updated_datetime, NEW.created_user, NEW.last_updated_user);
END $$

-- Trigger para actualizar en la auditoría de UserRoles
CREATE TRIGGER trg_userroles_update
    AFTER UPDATE ON userroles
    FOR EACH ROW
BEGIN
    DECLARE latest_version INT;
    SELECT MAX(version) INTO latest_version FROM userroles_audit WHERE id = NEW.id;
    SET latest_version = IFNULL(latest_version, 0) + 1;

    INSERT INTO userroles_audit (id, version, role_id, user_id, created_datetime, last_updated_datetime, created_user, last_updated_user)
    VALUES (NEW.id, latest_version, NEW.role_id, NEW.user_id, NEW.created_datetime, NEW.last_updated_datetime, NEW.created_user, NEW.last_updated_user);
END $$

DELIMITER ;

DELIMITER $$

-- Trigger para insertar en la auditoría de dni_type
CREATE TRIGGER trg_dni_type_insert
    AFTER INSERT ON dni_type
    FOR EACH ROW
BEGIN
    INSERT INTO dni_type_audit (id, version, description, created_datetime, last_updated_datetime, created_user, last_updated_user)
    VALUES (NEW.id, 1, NEW.description, NEW.created_datetime, NEW.last_updated_datetime, NEW.created_user, NEW.last_updated_user);
END $$

-- Trigger para actualizar en la auditoría de dni_type
CREATE TRIGGER trg_dni_type_update
    AFTER UPDATE ON dni_type
    FOR EACH ROW
BEGIN
    DECLARE latest_version INT;
    SELECT MAX(version) INTO latest_version FROM dni_type_audit WHERE id = NEW.id;
    SET latest_version = IFNULL(latest_version, 0) + 1;

    INSERT INTO dni_type_audit (id, version, description, created_datetime, last_updated_datetime, created_user, last_updated_user)
    VALUES (NEW.id, latest_version, NEW.description, NEW.created_datetime, NEW.last_updated_datetime, NEW.created_user, NEW.last_updated_user);
END $$

DELIMITER ;

-- Insertar roles
INSERT INTO roles (description, created_datetime, last_updated_datetime, created_user, last_updated_user)
VALUES
    ('SuperAdmin', NOW(), NOW(), 1, 1),
    ('Gerente', NOW(), NOW(), 1, 1),
    ('Propietario', NOW(), NOW(), 1, 1),
    ('Familiar mayor', NOW(), NOW(), 1, 1),
    ('Familiar menor', NOW(), NOW(), 1, 1),
    ('Inquilino', NOW(), NOW(), 1, 1),
    ('Gerente Finanzas', NOW(), NOW(), 1, 1),
    ('Contador', NOW(), NOW(), 1, 1),
    ('Seguridad', NOW(), NOW(), 1, 1),
    ('Gerente Inventario', NOW(), NOW(), 1, 1),
    ('Gerente Empleados', NOW(), NOW(), 1, 1),
    ('Gerente Multas', NOW(), NOW(), 1, 1);
    

-- Insertar tipos de DNI
INSERT INTO dni_type (description, created_datetime, last_updated_datetime, created_user, last_updated_user)
VALUES
    ('DNI', NOW(), NOW(), 1, 1),
    ('Pasaporte', NOW(), NOW(), 1, 1),
    ('CUIT/CUIL', NOW(), NOW(), 1, 1);

INSERT INTO users (name, lastname, dni_type_id, dni, birth_date, active, created_datetime, created_user, last_updated_datetime, last_updated_user, username, password, telegram_id)
VALUES
('Ulises', 'Lara', 1, '42512123', '2000-09-15', TRUE, NOW(), 1, NOW(), 1, 'uliseslara', '12345678', 12345),
('Carlos', 'Pérez', 1, '41234567', '1985-04-12', TRUE, NOW(), 1, NOW(), 1, 'carlosperez', '12345678', 67890),
('María', 'González', 1, '39876543', '1990-08-25', TRUE, NOW(), 1, NOW(), 1, 'mariagonzalez', '12345678', 23456),
('Juan', 'López', 1, '41238945', '1978-03-15', TRUE, NOW(), 1, NOW(), 1, 'juanlopez', '12345678', 78901),
('Ana', 'Martínez', 1, '44556677', '1995-07-19', TRUE, NOW(), 1, NOW(), 1, 'anamartinez', '12345678', 34567),
('Pedro', 'Ramírez', 1, '40785621', '1982-01-05', TRUE, NOW(), 1, NOW(), 1, 'pedroramirez', '12345678', 89012),
('Sofía', 'Hernández', 1, '43890123', '1993-11-11', TRUE, NOW(), 1, NOW(), 1, 'sofiahernandez', '12345678', 45678),
('Luis', 'García', 1, '42987654', '1988-09-30', TRUE, NOW(), 1, NOW(), 1, 'luisgarcia', '12345678', 90123),
('Laura', 'Rojas', 1, '40654321', '2000-02-20', TRUE, NOW(), 1, NOW(), 1, 'laurarojas', '12345678', 56789),
('José', 'Torres', 1, '43210987', '1975-05-22', TRUE, NOW(), 1, NOW(), 1, 'josetorres', '12345678', 12367),
('Carmen', 'Díaz', 3, '23455948169', '1987-12-10', TRUE, NOW(), 1, NOW(), 1, 'carmentorres', '12345678', 23478),
('Ana', 'Pérez', 1, '41234568', '1990-04-12', TRUE, NOW(), 1, NOW(), 1, 'anaperez', '12345678', 67834),
('Luis', 'Pérez', 1, '41234569', '2010-05-25', TRUE, NOW(), 1, NOW(), 1, 'luisperez', '12345678', 23499),
('Martín', 'González', 1, '39876544', '1992-08-25', TRUE, NOW(), 1, NOW(), 1, 'martingonzalez', '12345678', 34534),
('Clara', 'González', 1, '39876545', '2011-02-20', TRUE, NOW(), 1, NOW(), 1, 'claragonzalez', '12345678', 56750),
('Raúl', 'López', 1, '41238946', '1981-01-15', TRUE, NOW(), 1, NOW(), 1, 'raullopez', '12345678', 67899),
('María', 'López', 1, '41238947', '2012-03-10', TRUE, NOW(), 1, NOW(), 1, 'marialopez', '12345678', 89001),
('Isabel', 'Martínez', 1, '44556678', '1993-07-01', TRUE, NOW(), 1, NOW(), 1, 'isabelmartinez', '12345678', 34523),
('Santiago', 'Martínez', 1, '44556679', '2010-06-01', TRUE, NOW(), 1, NOW(), 1, 'santiagomartinez', '12345678', 56712),
('Roberto', 'Ramírez', 1, '40785622', '1984-05-05', TRUE, NOW(), 1, NOW(), 1, 'robertoramirez', '12345678', 12389),
('Lucía', 'Ramírez', 1, '40785623', '2010-10-11', TRUE, NOW(), 1, NOW(), 1, 'luciaramirez', '12345678', 45601),
('Juliana', 'Hernández', 1, '43890124', '1995-11-12', TRUE, NOW(), 1, NOW(), 1, 'julianahernandez', '12345678', 78945),
('Pedro', 'Hernández', 1, '43890125', '2012-02-21', TRUE, NOW(), 1, NOW(), 1, 'pedrohernandez', '12345678', 23456),
('Antonio', 'García', 1, '42987655', '1989-10-30', TRUE, NOW(), 1, NOW(), 1, 'antoniogarcia', '12345678', 90145),
('Eva', 'García', 2, '1242987656', '2013-05-20', TRUE, NOW(), 1, NOW(), 1, 'evagarcia', '12345678', 12342),
('Carlos', 'Rojas', 1, '40654322', '1998-02-19', TRUE, NOW(), 1, NOW(), 1, 'carlosrojas', '12345678', 67823),
('Marta', 'Rojas', 1, '40654323', '2015-06-18', TRUE, NOW(), 1, NOW(), 1, 'martarojas', '12345678', 23412),
('Félix', 'Torres', 1, '43210988', '1980-05-25', TRUE, NOW(), 1, NOW(), 1, 'felixtorres', '12345678', 78934),
('Beatriz', 'Torres', 1, '43210989', '2012-07-30', TRUE, NOW(), 1, NOW(), 1, 'beatriztorres', '12345678', 56745),
('Martín', 'Díaz', 3, '23455948170', '1992-12-10', TRUE, NOW(), 1, NOW(), 1, 'martindiaz', '12345678', 90156);

INSERT INTO userroles (role_id, user_id, created_datetime, last_updated_datetime, created_user, last_updated_user)
VALUES 
(1, 1, NOW(), NOW(), 1, 1),
(3, 2, NOW(), NOW(), 1, 1),
(3, 3, NOW(), NOW(), 1, 1),
(3, 4, NOW(), NOW(), 1, 1),
(3, 5, NOW(), NOW(), 1, 1),
(3, 6, NOW(), NOW(), 1, 1),
(3, 7, NOW(), NOW(), 1, 1),
(3, 8, NOW(), NOW(), 1, 1),
(3, 9, NOW(), NOW(), 1, 1),
(3, 10, NOW(), NOW(), 1, 1),
(3, 11, NOW(), NOW(), 1, 1),
(4, 12, NOW(), NOW(), 1, 1), -- Familiar de Carlos Pérez
(5, 13, NOW(), NOW(), 1, 1), -- Familiar de Carlos Pérez
(4, 14, NOW(), NOW(), 1, 1), -- Familiar de María González
(5, 15, NOW(), NOW(), 1, 1), -- Familiar de María González
(4, 16, NOW(), NOW(), 1, 1), -- Familiar de Juan López
(5, 17, NOW(), NOW(), 1, 1), -- Familiar de Juan López
(4, 18, NOW(), NOW(), 1, 1), -- Familiar de Ana Martínez
(5, 19, NOW(), NOW(), 1, 1), -- Familiar de Ana Martínez
(4, 20, NOW(), NOW(), 1, 1), -- Familiar de Pedro Ramírez
(5, 21, NOW(), NOW(), 1, 1), -- Familiar de Pedro Ramírez
(4, 22, NOW(), NOW(), 1, 1), -- Familiar de Sofía Hernández
(5, 23, NOW(), NOW(), 1, 1), -- Familiar de Sofía Hernández
(4, 24, NOW(), NOW(), 1, 1), -- Familiar de Luis García
(5, 25, NOW(), NOW(), 1, 1), -- Familiar de Luis García
(4, 26, NOW(), NOW(), 1, 1), -- Familiar de Laura Rojas
(5, 27, NOW(), NOW(), 1, 1), -- Familiar de Laura Rojas
(4, 28, NOW(), NOW(), 1, 1), -- Familiar de José Torres
(5, 29, NOW(), NOW(), 1, 1), -- Familiar de José Torres
(4, 30, NOW(), NOW(), 1, 1); -- Familiar de Carmen Díaz

INSERT INTO plotusers (plot_id, user_id, created_datetime, last_updated_datetime, created_user, last_updated_user)
VALUES
(1, 2, NOW(), NOW(), 1, 1),
(2, 2, NOW(), NOW(), 1, 1),
(3, 3, NOW(), NOW(), 1, 1),
(5, 4, NOW(), NOW(), 1, 1),
(7, 5, NOW(), NOW(), 1, 1),
(10, 6, NOW(), NOW(), 1, 1),
(11, 7, NOW(), NOW(), 1, 1),
(14, 8, NOW(), NOW(), 1, 1),
(16, 9, NOW(), NOW(), 1, 1),
(17, 10, NOW(), NOW(), 1, 1),
(19, 11, NOW(), NOW(), 1, 1),
(20, 11, NOW(), NOW(), 1, 1),
(1, 12, NOW(), NOW(), 1, 1), -- Familiar de Carlos Pérez
(1, 13, NOW(), NOW(), 1, 1), -- Familiar de Carlos Pérez
(3, 14, NOW(), NOW(), 1, 1), -- Familiar de María González
(3, 15, NOW(), NOW(), 1, 1), -- Familiar de María González
(5, 16, NOW(), NOW(), 1, 1), -- Familiar de Juan López
(5, 17, NOW(), NOW(), 1, 1), -- Familiar de Juan López
(7, 18, NOW(), NOW(), 1, 1), -- Familiar de Ana Martínez
(7, 19, NOW(), NOW(), 1, 1), -- Familiar de Ana Martínez
(10, 20, NOW(), NOW(), 1, 1), -- Familiar de Pedro Ramírez
(10, 21, NOW(), NOW(), 1, 1), -- Familiar de Pedro Ramírez
(11, 22, NOW(), NOW(), 1, 1), -- Familiar de Sofía Hernández
(11, 23, NOW(), NOW(), 1, 1), -- Familiar de Sofía Hernández
(14, 24, NOW(), NOW(), 1, 1), -- Familiar de Luis García
(14, 25, NOW(), NOW(), 1, 1), -- Familiar de Luis García
(16, 26, NOW(), NOW(), 1, 1), -- Familiar de Laura Rojas
(16, 27, NOW(), NOW(), 1, 1), -- Familiar de Laura Rojas
(17, 28, NOW(), NOW(), 1, 1), -- Familiar de José Torres
(17, 29, NOW(), NOW(), 1, 1), -- Familiar de José Torres
(19, 30, NOW(), NOW(), 1, 1); -- Familiar de Carmen Díaz
