package edu.miu.cs.cs489.DentalSurgeriesAppointments.repository;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAllByOrderByLastNameAsc();
    List<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrContactPhoneContainingIgnoreCase(
            String firstName, String lastName, String email, String contactPhone);
}
