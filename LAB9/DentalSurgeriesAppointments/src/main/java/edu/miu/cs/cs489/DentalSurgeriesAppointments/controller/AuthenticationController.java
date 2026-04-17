package edu.miu.cs.cs489.DentalSurgeriesAppointments.controller;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.config.JwtUtility;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.dto.request.AuthRequest;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.dto.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String token = jwtUtility.generateToken(authRequest.getUsername());
            return new AuthResponse(token);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
