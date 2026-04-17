package edu.miu.cs.cs489.DentalSurgeriesAppointments.service;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Dentist;

import java.util.List;
import java.util.Optional;

public interface DentistService {
    Dentist save(Dentist dentist);
    List<Dentist> findAll();
    List<Dentist> findAllSortedByLastName();
    Optional<Dentist> findById(Long id);
    Dentist update(Long id, Dentist dentist);
    void deleteById(Long id);
}
