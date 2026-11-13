package com.caffeletterario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caffeletterario.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAll();

    Book findById(int id);

    Book findByIsbn(String isbn);

    Book findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByGenre(String genre);
}
