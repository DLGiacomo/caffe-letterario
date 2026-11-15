package com.caffeletterario.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.caffeletterario.model.Event;
import com.caffeletterario.repository.EventRepository;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    // metodo per salvare un evento nel db, controllando se esiste già un evento con lo stesso orario
    public Event saveEvent(Event event) {
        if (eventRepository.existsByStartTimeBeforeAndEndTimeAfter(event.getEndTime(), event.getStartTime())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Error saving event for overlapping time periods");
        }
        return eventRepository.save(event);

    }
}
