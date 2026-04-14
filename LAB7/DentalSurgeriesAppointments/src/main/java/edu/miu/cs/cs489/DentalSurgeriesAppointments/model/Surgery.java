package edu.miu.cs.cs489.DentalSurgeriesAppointments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "surgeries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "appointments")
@EqualsAndHashCode(exclude = "appointments")
public class Surgery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surgeryId;

    @Column(nullable = false)
    private String name;

    private String telephoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "surgery", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments;
}
