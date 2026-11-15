package com.caffeletterario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caffeletterario.model.Newsletter;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {

    Newsletter findByEmail(String email);

    List<Newsletter> findAllByIsActive(String email, int id);

}
