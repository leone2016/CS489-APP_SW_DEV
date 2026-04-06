o-- ==============================================================================
-- Script Name: myADSDentalSurgeryDBScript.sql
-- Description: Schema creation, data population, and queries for ADS system
-- RDBMS: PostgreSQL
-- ==============================================================================

-- 1. DROP TABLES (to allow for clean re-runs)
DROP TABLE IF EXISTS Appointment;
DROP TABLE IF EXISTS Surgery;
DROP TABLE IF EXISTS Patient;
DROP TABLE IF EXISTS Dentist;

-- ==============================================================================
-- 2. CREATE TABLES
-- ==============================================================================

CREATE TABLE Dentist (
    dentist_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    contact_phone VARCHAR(20),
    email VARCHAR(100) UNIQUE NOT NULL,
    specialization VARCHAR(100)
);

CREATE TABLE Patient (
    patient_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    contact_phone VARCHAR(20),
    email VARCHAR(100) UNIQUE NOT NULL,
    mailing_address VARCHAR(255),
    date_of_birth DATE,
    -- Tracks the unpaid bill business rule requirement
    outstanding_bill_balance NUMERIC(10, 2) DEFAULT 0.00 
);

CREATE TABLE Surgery (
    surgery_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location_address VARCHAR(255) NOT NULL,
    telephone_number VARCHAR(20)
);

CREATE TABLE Appointment (
    appointment_id SERIAL PRIMARY KEY,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    status VARCHAR(20) DEFAULT 'Booked',
    dentist_id INT NOT NULL,
    patient_id INT NOT NULL,
    surgery_id INT NOT NULL,
    CONSTRAINT fk_dentist FOREIGN KEY (dentist_id) REFERENCES Dentist(dentist_id) ON DELETE CASCADE,
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES Patient(patient_id) ON DELETE CASCADE,
    CONSTRAINT fk_surgery FOREIGN KEY (surgery_id) REFERENCES Surgery(surgery_id) ON DELETE CASCADE
);

-- ==============================================================================
-- 3. POPULATE DATABASE WITH DUMMY DATA
-- ==============================================================================

INSERT INTO Dentist (first_name, last_name, contact_phone, email, specialization) VALUES
('John', 'Smith', '555-1010', 'jsmith@ads.com', 'Orthodontics'),
('Sarah', 'Jenkins', '555-2020', 'sjenkins@ads.com', 'General Dentistry'),
('Michael', 'Chang', '555-3030', 'mchang@ads.com', 'Periodontics');

INSERT INTO Patient (first_name, last_name, contact_phone, email, mailing_address, date_of_birth, outstanding_bill_balance) VALUES
('Alice', 'Williams', '555-8888', 'alice.w@email.com', '123 Elm St, Austin, TX', '1990-05-14', 0.00),
('Bob', 'Miller', '555-9999', 'bob.m@email.com', '456 Oak St, Dallas, TX', '1985-11-22', 150.00),
('Charlie', 'Brown', '555-7777', 'charlie.b@email.com', '789 Pine St, Houston, TX', '2001-02-10', 0.00);

INSERT INTO Surgery (name, location_address, telephone_number) VALUES
('ADS Austin Central', '100 Main St, Austin, TX', '555-1111'),
('ADS Dallas North', '200 High St, Dallas, TX', '555-2222');

INSERT INTO Appointment (appointment_date, appointment_time, status, dentist_id, patient_id, surgery_id) VALUES
('2026-04-15', '09:00:00', 'Booked', 1, 1, 1), -- Dr. Smith sees Alice at Austin
('2026-04-15', '10:30:00', 'Booked', 2, 3, 2), -- Dr. Jenkins sees Charlie at Dallas
('2026-04-16', '14:00:00', 'Booked', 1, 2, 1); -- Dr. Smith sees Bob at Austin

-- ==============================================================================
-- 4. REQUIRED SQL QUERIES
-- ==============================================================================

-- Query A: Display the list of ALL Dentists registered in the system, sorted in ascending order of their lastNames
SELECT * FROM Dentist 
ORDER BY last_name ASC;

-- Query B: Display the list of ALL Appointments for a given Dentist by their dentist_Id number. Include in the result, the Patient information.
-- (Using dentist_id = 1 as the example)
SELECT 
    a.appointment_id, 
    a.appointment_date, 
    a.appointment_time, 
    a.status,
    p.patient_id, 
    p.first_name AS patient_first_name, 
    p.last_name AS patient_last_name, 
    p.contact_phone AS patient_phone
FROM Appointment a
JOIN Patient p ON a.patient_id = p.patient_id
WHERE a.dentist_id = 1;

-- Query C: Display the list of ALL Appointments that have been scheduled at a Surgery Location
-- (Using surgery_id = 1 as the example)
SELECT 
    a.appointment_id, 
    a.appointment_date, 
    a.appointment_time, 
    s.name AS surgery_name, 
    s.location_address
FROM Appointment a
JOIN Surgery s ON a.surgery_id = s.surgery_id
WHERE s.surgery_id = 1;

-- Query D: Display the list of the Appointments booked for a given Patient on a given Date.
-- (Using patient_id = 1 and date '2026-04-15' as the example)
SELECT * FROM Appointment 
WHERE patient_id = 1 
  AND appointment_date = '2026-04-15';
