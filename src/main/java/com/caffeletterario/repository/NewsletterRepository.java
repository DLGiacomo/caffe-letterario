package com.caffeletterario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caffeletterario.model.Newsletter;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {

    List<Newsletter> findAll();

    Newsletter findById(int id);

    Newsletter findByEmail(String email);

}
