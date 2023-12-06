package com.ll.library.boundedContext.book.service;

import com.ll.library.base.rsData.RsData;
import com.ll.library.boundedContext.book.entity.Book;
import com.ll.library.boundedContext.book.repository.BookRepository;
import com.ll.library.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public RsData<Book> create(String title, String author, String introduce, Member regMember) {
        Book book = Book.builder()
                .title(title)
                .author(author)
                .introduce(introduce)
                .regMember(regMember)
                .build();

        return RsData.of("S-1", "책이 등록되었습니다.", book);
    }


}
