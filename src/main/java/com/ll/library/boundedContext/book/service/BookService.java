package com.ll.library.boundedContext.book.service;

import com.ll.library.base.rsData.RsData;

import com.ll.library.boundedContext.book.entity.Book;
import com.ll.library.boundedContext.book.repository.BookRepository;

import com.ll.library.boundedContext.member.entity.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    @Autowired
    private final BookRepository bookRepository;


    public Optional<Book> findById(Long id){
        return bookRepository.findById(id);
    }

    public RsData<Book> create(String title, String author, String introduce, Member regMember) {
        Book book = Book.builder()
                .title(title)
                .author(author)
                .introduce(introduce)
                .regMember(regMember)
                .build();
        bookRepository.save(book);

        return RsData.of("S-1", "책이 등록되었습니다.", book);
    }


    public RsData<Book> modify(Book book, String title, String author, String introduce) {

        book.bookModify(title, author, introduce);

        bookRepository.save(book);

        return RsData.of(
                "S-1",
                "%s 도서가 수정되었습니다.".formatted(book.getTitle()),
                book
        );
    }

    public RsData canModify(Member actor, Book book) {
        if (Objects.equals(actor, book.getRegMember())) {
            return RsData.of(
                    "S-1",
                    "게시물을 수정할 수 있습니다."
            );
        }

        return RsData.of(
                "F-1",
                "게시물을 수정할 수 없습니다."
        );
    }



}
