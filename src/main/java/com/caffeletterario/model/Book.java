package com.caffeletterario.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String isbn;

    @Column
    private String title;

    @Column
    private List<String> authors;

    @Column
    private List<String> categories;

    @Column
    private String coverImageUrl;

    @Column(name = "available_copies")
    private Integer availableCopies;

    @Column(name = "total_copies")
    private Integer totalCopies;

    @OneToMany(mappedBy = "book")
    @Builder.Default
    private List<Loan> loans = new ArrayList<>();
}