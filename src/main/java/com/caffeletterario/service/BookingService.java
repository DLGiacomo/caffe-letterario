package com.caffeletterario.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.caffeletterario.model.Booking;
import com.caffeletterario.model.Event;
import com.caffeletterario.model.User;
import com.caffeletterario.repository.BookingRepository;
import com.caffeletterario.repository.EventRepository;
import com.caffeletterario.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, EventRepository eventRepository,
            UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public List<Booking> getAllBookingFromUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    // salva la prenotazione ed assegna il numero di ticket
    @Transactional
    public Booking saveBooking(Long eventId, String userEmail, String userLastName, int seatsBooked) {
        Booking newBooking = new Booking();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with Id: " + eventId));

        availalbeSeatsValidation(event, seatsBooked);
        userValidation(event, userEmail, userLastName, newBooking);

        newBooking.setEvent(event);
        newBooking.setSeatsBooked(seatsBooked);
        newBooking.setTicketCode(UUID.randomUUID().toString().toUpperCase());
        return bookingRepository.save(newBooking);
    }

    // controllo sul numero di posti disponibili ed eventualmente si sottraggono dai disponibili quelli prenotati
    private void availalbeSeatsValidation(Event event, int seatsBooked) {
        int remainigSeats = event.getAvailableSeats();
        if (remainigSeats < seatsBooked)
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Not enaught slots available: " + remainigSeats);
        else {
            event.setAvailableSeats(remainigSeats - seatsBooked);
            eventRepository.save(event);
        }
    }

    // verifica su prenotazioni duplicate per email ed eventualemnte setting dei campi email, lastname e user
    private void userValidation(Event event, String userEmail, String userLastName, Booking newBooking) {
        if (bookingRepository.findByEventIdAndGuestEmail(event.getId(), userEmail) != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Alredy registered for the event", null);
        else {
            newBooking.setGuestEmail(userEmail);
            newBooking.setGuestLastName(userLastName);
            User user = userRepository.findByEmail(userEmail);
            if (user != null)
                newBooking.setUser(user);
        }
    }
}
