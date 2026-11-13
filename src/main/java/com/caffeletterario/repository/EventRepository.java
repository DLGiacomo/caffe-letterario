package com.caffeletterario.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caffeletterario.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAll();

    Event findById(int id);

    Event findByTitle(String title);

    List<Event> findByStartTime(LocalDateTime startTime);
}
