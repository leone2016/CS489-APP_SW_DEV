package edu.miu.cs.cs489.DentalSurgeriesAppointments.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
