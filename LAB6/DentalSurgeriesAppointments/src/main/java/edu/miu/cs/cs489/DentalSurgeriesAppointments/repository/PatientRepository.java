package edu.miu.cs.cs489.DentalSurgeriesAppointments.repository;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
