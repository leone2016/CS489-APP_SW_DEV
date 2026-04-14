package edu.miu.cs.cs489.DentalSurgeriesAppointments.repository;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DentistRepository extends JpaRepository<Dentist, Long> {
    List<Dentist> findAllByOrderByLastNameAsc();
}
