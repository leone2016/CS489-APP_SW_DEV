package edu.miu.cs.employepensionplan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.miu.cs.employepensionplan.model.Employee;
import edu.miu.cs.employepensionplan.model.PensionPlan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class EmployePensionPlanApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployePensionPlanApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            List<Employee> employees = loadData();

            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1. All Employees List (Sorted by Salary DESC, Last Name ASC):");
            printEmployeesJson(employees.stream()
                    .sorted(Comparator.comparing(Employee::getYearlySalary).reversed()
                            .thenComparing(Employee::getLastName))
                    .collect(Collectors.toList()));

            System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("2. Current Quarterly Enrollees Report (Q2 2026):");
            LocalDate currentQStart = LocalDate.of(2026, 4, 1);
            LocalDate currentQEnd = LocalDate.of(2026, 6, 30);
            printEmployeesJson(filterEmployeesForQuarter(employees, currentQStart, currentQEnd, true));

            System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("3. Next Quarterly Upcoming Enrollees Report (Q3 2026):");
            LocalDate nextQStart = LocalDate.of(2026, 7, 1);
            LocalDate nextQEnd = LocalDate.of(2026, 9, 30);
            // Feature 3 sort: employment date DESC, then Yearly Salary ASC
            printEmployeesJson(filterEmployeesForQuarter(employees, nextQStart, nextQEnd, false));
        };
    }

    private List<Employee> loadData() {
        List<Employee> employees = new ArrayList<>();

        // #1 Daniel Agar
        Employee e1 = new Employee(1, "Daniel", "Agar", LocalDate.of(2025, 8, 17), 105945.50);
        employees.add(e1);

        // #2 Benard Shaw
        Employee e2 = new Employee(2, "Benard", "Shaw", LocalDate.of(2025, 2, 3), 197750.00);
        e2.setPensionPlan(new PensionPlan("EX0089", LocalDate.of(2026, 2, 3), 450.00));
        employees.add(e2);

        // #3 Carly Jones
        Employee e3 = new Employee(3, "Carly", "Jones", LocalDate.of(2024, 5, 16), 842000.75);
        e3.setPensionPlan(new PensionPlan("SM2307", LocalDate.of(2025, 5, 17), 1555.50));
        employees.add(e3);

        // #4 Wesley Schneider
        Employee e4 = new Employee(4, "Wesley", "Schneider", LocalDate.of(2025, 4, 30), 174500.00);
        employees.add(e4);

        // #5 Anna Wilford
        Employee e5 = new Employee(5, "Anna", "Wilford", LocalDate.of(2025, 9, 15), 185750.00);
        employees.add(e5);

        // #6 Yosef Tesfalem
        Employee e6 = new Employee(6, "Yosef", "Tesfalem", LocalDate.of(2025, 7, 31), 100000.00);
        employees.add(e6);

        // #7 Johnny Edwards
        Employee e7 = new Employee(7, "Johnny", "Edwards", LocalDate.of(2025, 7, 9), 95500.00);
        employees.add(e7);

        return employees;
    }

    private List<Employee> filterEmployeesForQuarter(List<Employee> employees, LocalDate qStart, LocalDate qEnd, boolean isCurrent) {
        List<Employee> filtered = employees.stream()
                .filter(e -> e.getPensionPlan() == null)
                .filter(e -> e.getYearlySalary() >= 100000)
                .filter(e -> {
                    LocalDate anniversary = e.getEmploymentDate().plusYears(1);
                    return (anniversary.isAfter(qStart) || anniversary.isEqual(qStart)) &&
                            (anniversary.isBefore(qEnd) || anniversary.isEqual(qEnd));
                })
                .collect(Collectors.toList());

        if (isCurrent) {
            // Sort Feature 2: descending order of employment dates
            return filtered.stream()
                    .sorted(Comparator.comparing(Employee::getEmploymentDate).reversed())
                    .collect(Collectors.toList());
        } else {
            // Sort Feature 3: descending order of employment dates and ascending order of Yearly Salary
            return filtered.stream()
                    .sorted(Comparator.comparing(Employee::getEmploymentDate).reversed()
                            .thenComparing(Employee::getYearlySalary))
                    .collect(Collectors.toList());
        }
    }

    private void printEmployeesJson(List<Employee> employees) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(employees));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
