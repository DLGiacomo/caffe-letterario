package com.caffeletterario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caffeletterario.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingSerivService) {
        this.bookingService = bookingSerivService;
    }

    @PostMapping("/event-registration")
    public ResponseEntity<String> saveBooking(@RequestParam Long eventId, @RequestParam String userEmail, @RequestParam String userLastName,
            @RequestParam int seatsBooked) {
        bookingService.saveBooking(eventId, userEmail, userLastName, seatsBooked);
        return ResponseEntity.ok("Booking saved");
    }
}
