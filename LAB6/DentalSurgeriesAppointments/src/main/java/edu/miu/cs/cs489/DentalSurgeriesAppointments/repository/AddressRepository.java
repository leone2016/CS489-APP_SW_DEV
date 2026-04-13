package edu.miu.cs.cs489.DentalSurgeriesAppointments.repository;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
