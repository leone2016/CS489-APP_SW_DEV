package edu.miu.cs.cs489.DentalSurgeriesAppointments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "appointments")
@EqualsAndHashCode(exclude = "appointments")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String contactPhone;

    @Column(unique = true, nullable = false)
    private String email;

    private LocalDate dateOfBirth;

    @Column(precision = 10, scale = 2)
    private BigDecimal outstandingBillBalance = BigDecimal.ZERO;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments;
}
