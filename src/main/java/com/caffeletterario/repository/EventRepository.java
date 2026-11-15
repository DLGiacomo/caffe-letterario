package com.caffeletterario.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caffeletterario.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByTitle(String title);

    boolean existsByStartTimeBeforeAndEndTimeAfter(LocalDateTime endTime, LocalDateTime startTime);
}
