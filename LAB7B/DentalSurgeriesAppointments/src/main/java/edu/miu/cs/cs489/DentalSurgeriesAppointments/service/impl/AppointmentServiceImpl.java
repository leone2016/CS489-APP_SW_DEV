package edu.miu.cs.cs489.DentalSurgeriesAppointments.service.impl;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Appointment;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.repository.AppointmentRepository;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> findByDentistId(Long dentistId) {
        return appointmentRepository.findByDentistDentistId(dentistId);
    }

    @Override
    public List<Appointment> findBySurgeryId(Long surgeryId) {
        return appointmentRepository.findBySurgerySurgeryId(surgeryId);
    }

    @Override
    public List<Appointment> findByPatientIdAndDate(Long patientId, LocalDate date) {
        return appointmentRepository.findByPatientPatientIdAndAppointmentDate(patientId, date);
    }

    @Override
    public Appointment update(Long id, Appointment updated) {
        return appointmentRepository.findById(id).map(existing -> {
            existing.setAppointmentDate(updated.getAppointmentDate());
            existing.setAppointmentTime(updated.getAppointmentTime());
            existing.setStatus(updated.getStatus());
            existing.setDentist(updated.getDentist());
            existing.setPatient(updated.getPatient());
            existing.setSurgery(updated.getSurgery());
            return appointmentRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }
}
