package edu.miu.cs.cs489.DentalSurgeriesAppointments.service;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient save(Patient patient);
    List<Patient> findAll();
    List<Patient> findAllSorted();
    List<Patient> searchPatients(String searchString);
    Optional<Patient> findById(Long id);
    Patient update(Long id, Patient patient);
    void deleteById(Long id);
}
