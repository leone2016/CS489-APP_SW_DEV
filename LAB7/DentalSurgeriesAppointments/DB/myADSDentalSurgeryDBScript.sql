-- ==============================================================================
-- Script Name: myADSDentalSurgeryDBScript.sql
-- Description: Schema creation, data population, and queries for ADS system
-- RDBMS: PostgreSQL
-- ==============================================================================

-- 1. DROP TABLES (to allow for clean re-runs)
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS surgeries;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS dentists;
DROP TABLE IF EXISTS addresses;

-- ==============================================================================
-- 2. CREATE TABLES
-- ==============================================================================

CREATE TABLE addresses (
    address_id SERIAL PRIMARY KEY,
    street     VARCHAR(255) NOT NULL,
    city       VARCHAR(100) NOT NULL,
    state      VARCHAR(50)  NOT NULL,
    zip_code   VARCHAR(20)  NOT NULL
);

CREATE TABLE dentists (
    dentist_id     SERIAL PRIMARY KEY,
    first_name     VARCHAR(50)  NOT NULL,
    last_name      VARCHAR(50)  NOT NULL,
    contact_phone  VARCHAR(20),
    email          VARCHAR(100) UNIQUE NOT NULL,
    specialization VARCHAR(100)
);

CREATE TABLE patients (
    patient_id              SERIAL PRIMARY KEY,
    first_name              VARCHAR(50)    NOT NULL,
    last_name               VARCHAR(50)    NOT NULL,
    contact_phone           VARCHAR(20),
    email                   VARCHAR(100)   UNIQUE NOT NULL,
    date_of_birth           DATE,
    outstanding_bill_balance NUMERIC(10, 2) DEFAULT 0.00,
    address_id              INT,
    CONSTRAINT fk_patient_address FOREIGN KEY (address_id) REFERENCES addresses(address_id)
);

CREATE TABLE surgeries (
    surgery_id       SERIAL PRIMARY KEY,
    name             VARCHAR(100) NOT NULL,
    telephone_number VARCHAR(20),
    address_id       INT,
    CONSTRAINT fk_surgery_address FOREIGN KEY (address_id) REFERENCES addresses(address_id)
);

CREATE TABLE appointments (
    appointment_id   SERIAL PRIMARY KEY,
    appointment_date DATE        NOT NULL,
    appointment_time TIME        NOT NULL,
    status           VARCHAR(20) DEFAULT 'Booked',
    dentist_id       INT         NOT NULL,
    patient_id       INT         NOT NULL,
    surgery_id       INT         NOT NULL,
    CONSTRAINT fk_dentist  FOREIGN KEY (dentist_id)  REFERENCES dentists(dentist_id)  ON DELETE CASCADE,
    CONSTRAINT fk_patient  FOREIGN KEY (patient_id)  REFERENCES patients(patient_id)  ON DELETE CASCADE,
    CONSTRAINT fk_surgery  FOREIGN KEY (surgery_id)  REFERENCES surgeries(surgery_id) ON DELETE CASCADE
);

CREATE TABLE roles (
    role_id SERIAL PRIMARY KEY,
    name    VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
    user_id  SERIAL PRIMARY KEY,
    username VARCHAR(50)  UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_ur_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_ur_role FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

-- ==============================================================================
-- 3. POPULATE DATABASE WITH DUMMY DATA
-- ==============================================================================

-- Roles & Users
INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');

INSERT INTO users (username, password) VALUES
('admin', 'admin123'),
('john',  'john123');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1), (2, 2);

-- Dentists
INSERT INTO dentists (first_name, last_name, contact_phone, email, specialization) VALUES
('John',    'Smith',   '555-1010', 'jsmith@ads.com',   'Orthodontics'),
('Sarah',   'Jenkins', '555-2020', 'sjenkins@ads.com', 'General Dentistry'),
('Michael', 'Chang',   '555-3030', 'mchang@ads.com',   'Periodontics');

-- Addresses for Patients
INSERT INTO addresses (street, city, state, zip_code) VALUES
('123 Elm St',  'Austin',  'TX', '78701'),   -- address_id = 1 (Alice)
('456 Oak St',  'Dallas',  'TX', '75201'),   -- address_id = 2 (Bob)
('789 Pine St', 'Houston', 'TX', '77001'),   -- address_id = 3 (Charlie)
('100 Main St', 'Austin',  'TX', '78702'),   -- address_id = 4 (ADS Austin Central)
('200 High St', 'Dallas',  'TX', '75202');   -- address_id = 5 (ADS Dallas North)

-- Patients
INSERT INTO patients (first_name, last_name, contact_phone, email, date_of_birth, outstanding_bill_balance, address_id) VALUES
('Alice',   'Williams', '555-8888', 'alice.w@email.com',   '1990-05-14', 0.00,   1),
('Bob',     'Miller',   '555-9999', 'bob.m@email.com',     '1985-11-22', 150.00, 2),
('Charlie', 'Brown',    '555-7777', 'charlie.b@email.com', '2001-02-10', 0.00,   3);

-- Surgeries
INSERT INTO surgeries (name, telephone_number, address_id) VALUES
('ADS Austin Central', '555-1111', 4),
('ADS Dallas North',   '555-2222', 5);

-- Appointments
INSERT INTO appointments (appointment_date, appointment_time, status, dentist_id, patient_id, surgery_id) VALUES
('2026-04-15', '09:00:00', 'Booked', 1, 1, 1), -- Dr. Smith sees Alice at Austin
('2026-04-15', '10:30:00', 'Booked', 2, 3, 2), -- Dr. Jenkins sees Charlie at Dallas
('2026-04-16', '14:00:00', 'Booked', 1, 2, 1); -- Dr. Smith sees Bob at Austin

-- ==============================================================================
-- 4. REQUIRED SQL QUERIES
-- ==============================================================================

-- Query A: All Dentists sorted ascending by lastName
SELECT * FROM dentists
ORDER BY last_name ASC;

-- Query B: All Appointments for dentist_id = 1, including Patient info
SELECT
    a.appointment_id,
    a.appointment_date,
    a.appointment_time,
    a.status,
    p.patient_id,
    p.first_name AS patient_first_name,
    p.last_name  AS patient_last_name,
    p.contact_phone AS patient_phone
FROM appointments a
JOIN patients p ON a.patient_id = p.patient_id
WHERE a.dentist_id = 1;

-- Query C: All Appointments at surgery_id = 1, including Surgery location
SELECT
    a.appointment_id,
    a.appointment_date,
    a.appointment_time,
    s.name AS surgery_name,
    ad.street,
    ad.city
FROM appointments a
JOIN surgeries s  ON a.surgery_id  = s.surgery_id
JOIN addresses ad ON s.address_id  = ad.address_id
WHERE s.surgery_id = 1;

-- Query D: Appointments for patient_id = 1 on 2026-04-15
SELECT * FROM appointments
WHERE patient_id = 1
  AND appointment_date = '2026-04-15';
