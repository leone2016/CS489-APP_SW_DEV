package edu.miu.cs.cs489.DentalSurgeriesAppointments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "dentists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "appointments")
@EqualsAndHashCode(exclude = "appointments")
public class Dentist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dentistId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String contactPhone;

    @Column(unique = true, nullable = false)
    private String email;

    private String specialization;

    @OneToMany(mappedBy = "dentist", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments;
}
