package edu.miu.cs.cs489.DentalSurgeriesAppointments.service;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Surgery;

import java.util.List;
import java.util.Optional;

public interface SurgeryService {
    Surgery save(Surgery surgery);
    List<Surgery> findAll();
    Optional<Surgery> findById(Long id);
    Surgery update(Long id, Surgery surgery);
    void deleteById(Long id);
}
