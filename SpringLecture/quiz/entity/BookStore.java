package com.sparta.quiz.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class BookStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "bookStore")
    private List<Book> bookList;

//    일대다 단방향에 ERD 상 bookstore -> member로 되어있어 이렇게 구현
    @OneToMany
    @JoinColumn(name = "BOOK_STORE_ID")
    private List<Member> memberList;

    public BookStore(String location, String name) {
        this.location = location;
        this.name = name;
    }

}
