package com.caffeletterario.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.caffeletterario.model.Book;
import com.caffeletterario.model.Loan;
import com.caffeletterario.model.User;
import com.caffeletterario.model.enums.LoanStatus;
import com.caffeletterario.repository.BookRepository;
import com.caffeletterario.repository.LoanRepository;
import com.caffeletterario.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public void saveLoan(Long userId, Long bookId, LocalDate dueDate) {
        Loan newLoan = new Loan();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with Id: " + userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with Id: " + bookId));

        newLoan.setUser(user);
        newLoan.setBook(book);
        newLoan.setLoanStatus(LoanStatus.ACTIVE);
        newLoan.setLoanDate(LocalDate.now());
        newLoan.setDueDate(dueDate);
        loanRepository.save(newLoan);
    }

    public void returnLoan(Long loanId, Long userId) {
        Loan loan = loanRepository.findByIdAndUserId(loanId, userId);
        loan.setReturnDate(LocalDateTime.now());
        loan.setLoanStatus(LoanStatus.RETURNED);
        loanRepository.save(loan);
    }
}
