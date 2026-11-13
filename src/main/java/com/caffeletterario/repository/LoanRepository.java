package com.caffeletterario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caffeletterario.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findAll();

    Loan findById(int id);

    List<Loan> findByUserId(Long userId);

    List<Loan> findByBookTitle(String bookTitle);

    List<Loan> findByBookIsbn(String bookIsbn);
}
