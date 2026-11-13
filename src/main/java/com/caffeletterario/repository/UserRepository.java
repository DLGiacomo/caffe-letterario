package com.caffeletterario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.caffeletterario.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    User findById(int id);

    User findByEmail(String email);

    User findByCardNumber(String cardNumber);
}