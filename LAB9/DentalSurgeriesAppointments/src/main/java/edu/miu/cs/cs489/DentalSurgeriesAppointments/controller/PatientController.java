package edu.miu.cs.cs489.DentalSurgeriesAppointments.controller;

import edu.miu.cs.cs489.DentalSurgeriesAppointments.exception.PatientNotFoundException;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.model.Patient;
import edu.miu.cs.cs489.DentalSurgeriesAppointments.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    // 1. GET /adsweb/api/v1/patients - Sorted by lastName ASC
    @GetMapping("/patients")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Patient>> getAllPatientsSorted() {
        return ResponseEntity.ok(patientService.findAllSorted());
    }

    // 2. GET /adsweb/api/v1/patients/1 - Get by PatientId
    @GetMapping("/patients/{patientId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long patientId) {
        return patientService.findById(patientId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));
    }

    // 3. POST /adsweb/api/v1/patients - Create new Patient
    @PostMapping("/patients")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient) {
        return new ResponseEntity<>(patientService.save(patient), HttpStatus.CREATED);
    }

    // 4. PUT /adsweb/api/v1/patient/1 - Update Patient
    @PutMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long patientId, @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.update(patientId, patient));
    }

    // 5. DELETE /adsweb/api/v1/patient/1 - Delete Patient
    @DeleteMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {
        patientService.deleteById(patientId);
        return ResponseEntity.noContent().build();
    }

    // 6. GET /adsweb/api/v1/patient/search/{searchString} - Search Patients
    @GetMapping("/patient/search/{searchString}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Patient>> searchPatients(@PathVariable String searchString) {
        return ResponseEntity.ok(patientService.searchPatients(searchString));
    }
}
