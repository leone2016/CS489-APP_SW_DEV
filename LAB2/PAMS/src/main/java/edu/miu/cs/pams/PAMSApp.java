package edu.miu.cs.pams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.miu.cs.pams.model.Patient;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class PAMSApp {

    public static void main(String[] args) {
        // 1. Create Patient objects using the provided data
        Patient[] patients = {
                new Patient(1L, "Daniel", "Agar", "(641) 123-0009", "dagar@m.as", "1 N Street", LocalDate.of(1987, 1, 19)),
                new Patient(2L, "Ana", "Smith", "", "amsith@te.edu", "", LocalDate.of(1948, 12, 5)),
                new Patient(3L, "Marcus", "Garvey", "(123) 292-0018", "", "4 East Ave", LocalDate.of(2001, 9, 18)),
                new Patient(4L, "Jeff", "Goldbloom", "(999) 165-1192", "jgold@es.co.za", "", LocalDate.of(1995, 2, 28)),
                new Patient(5L, "Mary", "Washington", "", "", "30 W Burlington", LocalDate.of(1932, 5, 31))
        };

        // 2. Sort Patients by Age in descending order (Oldest first)
        Arrays.sort(patients, Comparator.comparingInt(Patient::getAge).reversed());

        // 3. Convert to JSON and write to file
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Support LocalDate
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty print

        try {
            File outputFile = new File("patients.json");
            objectMapper.writeValue(outputFile, patients);
            System.out.println("Patient data successfully written to " + outputFile.getAbsolutePath());
            
            // For demonstration, print JSON to console too
            String jsonOutput = objectMapper.writeValueAsString(patients);
            System.out.println("\nJSON Output (Sorted by Age Descending):");
            System.out.println(jsonOutput);
            
        } catch (IOException e) {
            System.err.println("Error writing patient data to JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
