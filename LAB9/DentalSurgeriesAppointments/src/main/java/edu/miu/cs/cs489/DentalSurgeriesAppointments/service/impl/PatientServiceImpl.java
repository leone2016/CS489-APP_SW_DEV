package edu.miu.cs.cs489.DentalSurgeriesAppointments.service.impl;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Patient;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.repository.PatientRepository;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> findAllSorted() {
        return patientRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public List<Patient> searchPatients(String searchString) {
        return patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrContactPhoneContainingIgnoreCase(
                searchString, searchString, searchString, searchString);
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient update(Long id, Patient updated) {
        return patientRepository.findById(id).map(existing -> {
            existing.setFirstName(updated.getFirstName());
            existing.setLastName(updated.getLastName());
            existing.setContactPhone(updated.getContactPhone());
            existing.setEmail(updated.getEmail());
            existing.setDateOfBirth(updated.getDateOfBirth());
            existing.setOutstandingBillBalance(updated.getOutstandingBillBalance());
            existing.setAddress(updated.getAddress());
            return patientRepository.save(existing);
        }).orElseThrow(() -> new edu.miu.cs.cs489.DentalSurgeriesAppointments.exception.PatientNotFoundException("Patient not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }
}
