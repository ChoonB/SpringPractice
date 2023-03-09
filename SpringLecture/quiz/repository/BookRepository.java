package com.sparta.quiz.repository;

import com.sparta.quiz.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
