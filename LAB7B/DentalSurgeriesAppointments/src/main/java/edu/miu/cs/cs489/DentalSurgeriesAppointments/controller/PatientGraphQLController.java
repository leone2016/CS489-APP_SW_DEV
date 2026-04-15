package edu.miu.cs.cs489.DentalSurgeriesAppointments.controller;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Patient;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PatientGraphQLController {

    private final PatientService patientService;

    @QueryMapping
    public List<Patient> allPatients() {
        return patientService.findAllSorted();
    }

    @QueryMapping
    public Patient patientById(@Argument Long id) {
        return patientService.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Patient> searchPatients(@Argument String searchString) {
        return patientService.searchPatients(searchString);
    }

    @MutationMapping
    public Patient registerPatient(@Argument Patient patient) {
        return patientService.save(patient);
    }

    @MutationMapping
    public Patient updatePatient(@Argument Long id, @Argument Patient patient) {
        return patientService.update(id, patient);
    }

    @MutationMapping
    public Boolean deletePatient(@Argument Long id) {
        patientService.deleteById(id);
        return true;
    }
}
