package com.caffeletterario.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.caffeletterario.model.enums.Role;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String passwordHash;

    @Column
    private String fullName;

    @Column(unique = true)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @OneToMany(mappedBy = "user")
    @Builder.Default 
    private List<Loan> loans = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();
}