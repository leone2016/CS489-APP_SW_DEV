package edu.miu.cs.cs489.DentalSurgeriesAppointments.repository;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Surgery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurgeryRepository extends JpaRepository<Surgery, Long> {
}
