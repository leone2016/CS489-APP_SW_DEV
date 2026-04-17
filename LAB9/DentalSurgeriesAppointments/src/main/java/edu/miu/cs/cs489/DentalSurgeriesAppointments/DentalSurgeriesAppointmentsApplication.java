package edu.miu.cs.cs489.DentalSurgeriesAppointments;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.*;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.repository.RoleRepository;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.repository.UserRepository;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.AppointmentService;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.DentistService;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.PatientService;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.SurgeryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@SpringBootApplication
public class DentalSurgeriesAppointmentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DentalSurgeriesAppointmentsApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(
            DentistService dentistService,
            PatientService patientService,
            SurgeryService surgeryService,
            AppointmentService appointmentService,
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            System.out.println("=== Populating database with sample data ===");

            // --- Roles & Users ---
            Role adminRole = roleRepository.save(new Role(null, "ROLE_ADMIN"));
            Role userRole  = roleRepository.save(new Role(null, "ROLE_USER"));

            userRepository.save(new User(null, "admin", passwordEncoder.encode("admin123"), Set.of(adminRole)));
            userRepository.save(new User(null, "john",  passwordEncoder.encode("john123"),  Set.of(userRole)));

            // --- Dentists ---
            Dentist d1 = dentistService.save(new Dentist(null, "John",    "Smith",   "555-1010", "jsmith@ads.com",   "Orthodontics",      null));
            Dentist d2 = dentistService.save(new Dentist(null, "Sarah",   "Jenkins", "555-2020", "sjenkins@ads.com", "General Dentistry", null));
            Dentist d3 = dentistService.save(new Dentist(null, "Michael", "Chang",   "555-3030", "mchang@ads.com",   "Periodontics",      null));

            // --- Patients (with Address) ---
            Patient p1 = new Patient();
            p1.setFirstName("Alice"); p1.setLastName("Williams");
            p1.setContactPhone("555-8888"); p1.setEmail("alice.w@email.com");
            p1.setDateOfBirth(LocalDate.of(1990, 5, 14));
            p1.setOutstandingBillBalance(BigDecimal.ZERO);
            p1.setAddress(new Address(null, "123 Elm St", "Austin", "TX", "78701"));
            p1 = patientService.save(p1);

            Patient p2 = new Patient();
            p2.setFirstName("Bob"); p2.setLastName("Miller");
            p2.setContactPhone("555-9999"); p2.setEmail("bob.m@email.com");
            p2.setDateOfBirth(LocalDate.of(1985, 11, 22));
            p2.setOutstandingBillBalance(new BigDecimal("150.00"));
            p2.setAddress(new Address(null, "456 Oak St", "Dallas", "TX", "75201"));
            p2 = patientService.save(p2);

            Patient p3 = new Patient();
            p3.setFirstName("Charlie"); p3.setLastName("Brown");
            p3.setContactPhone("555-7777"); p3.setEmail("charlie.b@email.com");
            p3.setDateOfBirth(LocalDate.of(2001, 2, 10));
            p3.setOutstandingBillBalance(BigDecimal.ZERO);
            p3.setAddress(new Address(null, "789 Pine St", "Houston", "TX", "77001"));
            p3 = patientService.save(p3);

            // --- Surgeries (with Address) ---
            Surgery s1 = new Surgery();
            s1.setName("ADS Austin Central"); s1.setTelephoneNumber("555-1111");
            s1.setAddress(new Address(null, "100 Main St", "Austin", "TX", "78702"));
            s1 = surgeryService.save(s1);

            Surgery s2 = new Surgery();
            s2.setName("ADS Dallas North"); s2.setTelephoneNumber("555-2222");
            s2.setAddress(new Address(null, "200 High St", "Dallas", "TX", "75202"));
            s2 = surgeryService.save(s2);

            // --- Appointments ---
            Appointment a1 = new Appointment(null, LocalDate.of(2026, 4, 15), LocalTime.of(9, 0),  "Booked", d1, p1, s1);
            Appointment a2 = new Appointment(null, LocalDate.of(2026, 4, 15), LocalTime.of(10, 30), "Booked", d2, p3, s2);
            Appointment a3 = new Appointment(null, LocalDate.of(2026, 4, 16), LocalTime.of(14, 0),  "Booked", d1, p2, s1);
            appointmentService.save(a1);
            appointmentService.save(a2);
            appointmentService.save(a3);

            System.out.println("=== Data population complete ===\n");

            // --- Query A: All dentists sorted by last name ---
            System.out.println("--- Query A: All Dentists sorted by lastName ---");
            dentistService.findAllSortedByLastName().forEach(d ->
                System.out.printf("  [%d] %s %s | %s | %s%n",
                    d.getDentistId(), d.getFirstName(), d.getLastName(),
                    d.getEmail(), d.getSpecialization())
            );

            // --- Query B: Appointments for dentist id=1 ---
            System.out.println("\n--- Query B: Appointments for Dentist ID=1 ---");
            appointmentService.findByDentistId(d1.getDentistId()).forEach(a ->
                System.out.printf("  [%d] %s %s | Patient: %s %s | %s%n",
                    a.getAppointmentId(), a.getAppointmentDate(), a.getAppointmentTime(),
                    a.getPatient().getFirstName(), a.getPatient().getLastName(), a.getStatus())
            );

            // --- Query C: Appointments at surgery id=1 ---
            System.out.println("\n--- Query C: Appointments at Surgery ID=1 ---");
            appointmentService.findBySurgeryId(s1.getSurgeryId()).forEach(a ->
                System.out.printf("  [%d] %s %s | Surgery: %s | %s%n",
                    a.getAppointmentId(), a.getAppointmentDate(), a.getAppointmentTime(),
                    a.getSurgery().getName(), a.getSurgery().getAddress().getCity())
            );

            // --- Query D: Appointments for patient id=1 on 2026-04-15 ---
            System.out.println("\n--- Query D: Appointments for Patient ID=1 on 2026-04-15 ---");
            appointmentService.findByPatientIdAndDate(p1.getPatientId(), LocalDate.of(2026, 4, 15)).forEach(a ->
                System.out.printf("  [%d] %s %s | %s%n",
                    a.getAppointmentId(), a.getAppointmentDate(), a.getAppointmentTime(), a.getStatus())
            );

            // --- CRUD Demo: Update & Delete ---
            System.out.println("\n--- CRUD: Update Dentist ID=3 specialization ---");
            dentistService.findById(d3.getDentistId()).ifPresent(d -> {
                d.setSpecialization("Endodontics");
                Dentist updated = dentistService.update(d.getDentistId(), d);
                System.out.printf("  Updated: %s %s -> %s%n",
                    updated.getFirstName(), updated.getLastName(), updated.getSpecialization());
            });

            System.out.println("\n--- CRUD: Cancel Appointment ID=2 ---");
            appointmentService.findById(a2.getAppointmentId()).ifPresent(a -> {
                a.setStatus("Cancelled");
                Appointment updated = appointmentService.update(a.getAppointmentId(), a);
                System.out.printf("  Appointment [%d] status -> %s%n",
                    updated.getAppointmentId(), updated.getStatus());
            });

            System.out.println("\n=== ADS Dental Surgery App - Done ===");
        };
    }
}
