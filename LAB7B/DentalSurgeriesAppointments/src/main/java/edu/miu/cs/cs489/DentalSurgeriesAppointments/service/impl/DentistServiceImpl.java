package edu.miu.cs.cs489.DentalSurgeriesAppointments.service.impl;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Dentist;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.repository.DentistRepository;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.DentistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DentistServiceImpl implements DentistService {

    private final DentistRepository dentistRepository;

    @Override
    public Dentist save(Dentist dentist) {
        return dentistRepository.save(dentist);
    }

    @Override
    public List<Dentist> findAll() {
        return dentistRepository.findAll();
    }

    @Override
    public List<Dentist> findAllSortedByLastName() {
        return dentistRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public Optional<Dentist> findById(Long id) {
        return dentistRepository.findById(id);
    }

    @Override
    public Dentist update(Long id, Dentist updated) {
        return dentistRepository.findById(id).map(existing -> {
            existing.setFirstName(updated.getFirstName());
            existing.setLastName(updated.getLastName());
            existing.setContactPhone(updated.getContactPhone());
            existing.setEmail(updated.getEmail());
            existing.setSpecialization(updated.getSpecialization());
            return dentistRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Dentist not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        dentistRepository.deleteById(id);
    }
}
