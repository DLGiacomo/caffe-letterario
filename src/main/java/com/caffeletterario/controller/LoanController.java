package com.caffeletterario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caffeletterario.service.LoanService;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/active-loan")
    public ResponseEntity<String> saveLoan(@RequestParam Long userId, Long bookId, LocalDate dueDate) {
        loanService.saveLoan(userId, bookId, dueDate);
        return ResponseEntity.ok("Loan activated");
    }

    @PutMapping("/return-loan")
    public ResponseEntity<String> returnLoan(@RequestParam Long loanId, Long userId) {
        loanService.returnLoan(loanId, userId);
        return ResponseEntity.ok("Loan returned");
    }

}
