package com.ll.library.boundedContext.book.repository;

import com.ll.library.boundedContext.book.entity.Book;
import com.ll.library.boundedContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
}
