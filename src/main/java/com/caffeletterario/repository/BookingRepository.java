package com.caffeletterario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caffeletterario.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAll();

    Booking findById(int id);

    List<Booking> findByUserId(Long userId);
}
