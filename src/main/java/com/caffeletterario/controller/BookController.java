package com.caffeletterario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caffeletterario.model.Book;
import com.caffeletterario.service.BookService;

@RestController 
@RequestMapping ("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping ("/{isbn}")
    public Book getBook(@PathVariable String isbn) {
        return bookService.findBookFromAPIByIsbn(isbn);
    }

}