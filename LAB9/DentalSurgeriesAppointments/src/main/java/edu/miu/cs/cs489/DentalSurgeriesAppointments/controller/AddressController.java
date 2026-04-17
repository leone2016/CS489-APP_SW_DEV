package edu.miu.cs.cs489.DentalSurgeriesAppointments.controller;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Address;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // 7. GET /adsweb/api/v1/addresses - Sorted by city ASC
    @GetMapping("/addresses")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Address>> getAllAddressesSorted() {
        return ResponseEntity.ok(addressService.findAllSorted());
    }
}
