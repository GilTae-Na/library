package com.ll.library.boundedContext.book.dto;

import com.ll.library.boundedContext.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class BookResponse {

        private final Book book;

}
