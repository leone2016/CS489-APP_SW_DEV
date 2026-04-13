package edu.miu.cs.cs489.DentalSurgeriesAppointments.service;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment save(Appointment appointment);
    List<Appointment> findAll();
    Optional<Appointment> findById(Long id);
    List<Appointment> findByDentistId(Long dentistId);
    List<Appointment> findBySurgeryId(Long surgeryId);
    List<Appointment> findByPatientIdAndDate(Long patientId, LocalDate date);
    Appointment update(Long id, Appointment appointment);
    void deleteById(Long id);
}
