package edu.miu.cs.cs489.DentalSurgeriesAppointments.controller;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Address;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddressGraphQLController {

    private final AddressService addressService;

    @QueryMapping
    public List<Address> allAddresses() {
        return addressService.findAllSorted();
    }
}
