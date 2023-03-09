package com.sparta.quiz.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOOK_STORE_ID", nullable = false)
    private BookStore bookStore;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Long quantity;

    @OneToMany(mappedBy = "book")
    private List<Purchase> purchaseList;

}
