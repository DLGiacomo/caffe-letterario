package com.caffeletterario.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Column(name = "available_seats")
    private Integer availableSeats;

    // Relazione 1:N con Bookings
    @OneToMany(mappedBy = "event")
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();
}
