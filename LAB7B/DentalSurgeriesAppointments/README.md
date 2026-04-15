# Instructions

For this Lab Assignment 7a, you are required to create a **Spring Boot**, Spring Web MVC-based Web application project and implement a RESTful Web API solution, for the ADS Dental Surgeries Appointments management system.

In your solution, you are required to implement the following RESTful Web API endpoint URIs:

1. HTTP GET request: http://localhost:8080/adsweb/api/v1/patients - Displays the list of all Patients, including their primaryAddresses, sorted in ascending order by their lastName, in JSON format.

2. HTTP GET request: http://localhost:8080/adsweb/api/v1/patients/1 - Displays the data for Patient whose PatientId is 1 including the primaryAddress, in JSON format. Also, make sure to implement appropriate exception handling, for where patientId is invalid and not found.

3. HTTP POST request: http://localhost:8080/adsweb/api/v1/patients - Register a new Patient into the system. Note: You supply the correct/appropriate Patient data in JSON format

4. HTTP PUT request: http://localhost:8080/adsweb/api/v1/patient/1 - Retrieves and updates Patient data for the patient whose patientId is 1 (or any other valid patientId). Also, make sure to implement appropriate exception handling, for where patientId is invalid and not found.

5. HTTP DELETE request: http://localhost:8080/adsweb/api/v1/patient/1 - Deletes the Patient data for the patient whose patientId is 1 (or any other valid patientId).

6. http://localhost:8080/adsweb/api/v1/patient/search/{searchString} - Queries all the Patient data for the patient(s) whose data matches the input searchString.

7. HTTP GET request: http://localhost:8080/adsweb/api/v1/addresses - Displays the list of all Addresses, including the Patient data, sorted in ascending order by their city, in JSON format.

   When complete: create a file with curls uses of API output


-------------------------------------------------

CONTEXT OF THE PROJECT ONLY FOR UNDERSTANDING PURPOSES

El proyecto consiste en el desarrollo de una aplicación CLI basada en Spring Boot para la gestión de citas en el sistema ADS Dental Surgeries. La solución implementa persistencia de datos empresarial utilizando Spring Data JPA, siguiendo el modelo de dominio proporcionado.

La aplicación está diseñada para generar automáticamente el esquema de base de datos a partir del modelo definido en Java. Como resultado, se crean las tablas principales: pacientes, dentistas, cirugías, direcciones, citas, usuarios y roles, incluyendo sus respectivas relaciones de clave primaria y foránea.

Además, el sistema incorpora la carga de datos de ejemplo para poblar las tablas y facilitar pruebas funcionales. También se implementan operaciones CRUD básicas para manipular la información almacenada.

El modelo de entidades incluye:

Address: nueva entidad que almacena información de dirección (calle, ciudad, estado, código postal). Se relaciona uno a uno con Surgery y Patient.
Patient: actualizado para reemplazar el campo de dirección tipo texto por una relación @OneToOne con Address.
Surgery: también actualizado para usar Address mediante una relación @OneToOne.
Dentist: mantiene una relación @OneToMany con Appointment.
Appointment: entidad central que se relaciona mediante @ManyToOne con Dentist, Patient y Surgery.
Role: define roles del sistema como ROLE_ADMIN y ROLE_USER.
User: implementa una relación @ManyToMany con Role a través de una tabla intermedia user_roles.

La estructura del proyecto está organizada en:

model: contiene las entidades JPA.
repository: interfaces que extienden JpaRepository para cada entidad, incluyendo consultas personalizadas en AppointmentRepository.
service: define interfaces de servicio.
service/impl: contiene las implementaciones de los servicios con lógica CRUD.

En la configuración del proyecto:

Se agregaron las dependencias necesarias en build.gradle, incluyendo Spring Data JPA, el driver de PostgreSQL y Lombok.
application.properties define la conexión a PostgreSQL con la base de datos myadsdentalsurgerydb_lab6, utilizando las credenciales lab5/lab5 y la propiedad ddl-auto=create para generar el esquema automáticamente.

La aplicación principal incluye un CommandLineRunner que inicializa los datos de ejemplo, ejecuta consultas predefinidas y demuestra operaciones CRUD como actualización y cancelación de registros.

Finalmente, se actualizaron los archivos de base de datos:

EM.md: se añadieron las entidades Address, Role y User junto con sus relaciones.
myADSDentalSurgeryDBScript.sql: se reestructuró completamente para incluir la tabla addresses, actualizar claves foráneas en patients y surgeries, incorporar roles y usuarios, y ajustar consultas para reflejar las nuevas relaciones.


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