package edu.miu.cs.cs489.DentalSurgeriesAppointments.service;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Address;

import java.util.List;

public interface AddressService {
    List<Address> findAllSorted();
}
