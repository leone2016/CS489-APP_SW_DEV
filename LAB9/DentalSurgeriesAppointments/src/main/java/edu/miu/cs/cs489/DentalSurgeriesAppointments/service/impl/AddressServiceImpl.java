package edu.miu.cs.cs489.DentalSurgeriesAppointments.service.impl;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Address;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.repository.AddressRepository;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<Address> findAllSorted() {
        return addressRepository.findAllByOrderByCityAsc();
    }
}
