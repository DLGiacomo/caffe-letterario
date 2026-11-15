package com.caffeletterario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caffeletterario.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByIsbn(String isbn);

    Book findByTitle(String title);

    List<Book> findByAuthors(List<String> authors);

    List<Book> findByCategories(List<String> categories);
}
