package com.caffeletterario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "guest_last_name")
    private String guestLastName;

    @Column(name = "guest_email")
    private String guestEmail;

    @Column(name = "seats_booked")
    private Integer seatsBooked;

    @Column(name = "ticket_code", unique = true)
    private String ticketCode;
}
