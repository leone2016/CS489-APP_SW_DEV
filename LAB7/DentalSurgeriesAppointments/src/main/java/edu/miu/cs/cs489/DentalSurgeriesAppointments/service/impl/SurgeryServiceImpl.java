package edu.miu.cs.cs489.DentalSurgeriesAppointments.service.impl;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Surgery;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.repository.SurgeryRepository;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.SurgeryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SurgeryServiceImpl implements SurgeryService {

    private final SurgeryRepository surgeryRepository;

    @Override
    public Surgery save(Surgery surgery) {
        return surgeryRepository.save(surgery);
    }

    @Override
    public List<Surgery> findAll() {
        return surgeryRepository.findAll();
    }

    @Override
    public Optional<Surgery> findById(Long id) {
        return surgeryRepository.findById(id);
    }

    @Override
    public Surgery update(Long id, Surgery updated) {
        return surgeryRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setTelephoneNumber(updated.getTelephoneNumber());
            existing.setAddress(updated.getAddress());
            return surgeryRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Surgery not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        surgeryRepository.deleteById(id);
    }
}
