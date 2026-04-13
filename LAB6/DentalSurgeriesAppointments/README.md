Assignment Instructions

For this Lab Assignment 6, you are required to create a Spring Boot CLI application project and implement enterprise Data Persistence using Spring Data JPA, for the ADS Dental Surgeries Appointments management system. See the sample Domain model attached.

In your solution, have your Java code generate/create the Database schema correctly based on the given designed domain model (i.e. there should be the following Database Tables: patients, dentists, surgeries, addresses, appointments, users and roles), including the PK/FK relationships as required.

Also, add Java code to populate your database tables with the sample Data, see attached.

For practice, implement code to perform the basic CRUD operations using the data.

> read DB folder, here you have the DB schema and sample data, I need to modify this with the following:
- entities
  - Address (This entity is not created yet, modify EM.md and .sql)
    - Surgery (1)------------------(1) Address (1)--------- (1) Patient
  - Appointment
  - Dentist
  - Patient
  - Surgery

project structure folder

- model
- repository (JpaRepository)
- service
  - impl/ (implementation interfaces services)
  - interfases services

DATABASE: Postgress

      POSTGRES_USER: lab5
      POSTGRES_PASSWORD: lab5
      POSTGRES_DB: myadsdentalsurgerydb_lab6
    url: jdbc:postgresql://localhost:5432/postgres

Alternatively, implement Data Persistence for your Project.






-------------------------------------------

All files are created. Here's a summary of what was implemented:
                                                                                          
  ---
  What was done                                                                                                                        
                                                                                                                                       
  build.gradle                                                                                                                         
                                                                                                                                       
  Added spring-boot-starter-data-jpa, postgresql driver, and lombok.                                                                   
   
  application.properties                                                                                                               
                                                            
  Configured PostgreSQL datasource (myadsdentalsurgerydb_lab6, user lab5/lab5) with ddl-auto=create.                                   
                                                            
  Model (model/)                                                                                                                       
                                                            
  ┌─────────────┬──────────────────────────────────────────────────────┐                                   
  │   Entity    │                        Notes                         │
  ├─────────────┼──────────────────────────────────────────────────────┤                           
  │ Address     │ New entity — street, city, state, zipCode            │
  ├─────────────┼──────────────────────────────────────────────────────┤
  │ Patient     │ Replaced mailing_address String → @OneToOne Address  │       
  ├─────────────┼──────────────────────────────────────────────────────┤                             
  │ Surgery     │ Replaced location_address String → @OneToOne Address │                                  
  ├─────────────┼──────────────────────────────────────────────────────┤                                    
  │ Dentist     │ @OneToMany → Appointments                            │
  ├─────────────┼──────────────────────────────────────────────────────┤                                    
  │ Appointment │ @ManyToOne Dentist, Patient, Surgery                 │
  ├─────────────┼──────────────────────────────────────────────────────┤                                    
  │ Role        │ name (ROLE_ADMIN, ROLE_USER)                         │
  ├─────────────┼──────────────────────────────────────────────────────┤                                    
  │ User        │ @ManyToMany → Roles via user_roles join table        │
  └─────────────┴──────────────────────────────────────────────────────┘                                                               
   
  Repository (repository/)                                                                                                             
                                                            
  JpaRepository for all 7 entities. AppointmentRepository has 3 custom finders for Queries B, C, D.                                    
   
  Service (service/ + service/impl/)                                                                                                   
                                                            
  Interface + @Service implementation for Dentist, Patient, Surgery, Appointment with full CRUD.                                       
   
  DentalSurgeriesAppointmentsApplication                                                                                               
                                                            
  CommandLineRunner populates all sample data and runs all 4 required queries + a CRUD update/cancel demo on startup.                  
   
  DB files updated                                                                                                                     
                                                            
  - EM.md — added ADDRESS, ROLE, USER entities and new relationships                                                                   
  - myADSDentalSurgeryDBScript.sql — full rewrite with addresses table, updated FK references in patients/surgeries, added
  roles/users/user_roles, and updated Query C to JOIN through addresses      