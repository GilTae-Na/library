package com.ll.library.boundedContext.checkout.dto;

import com.ll.library.boundedContext.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CheckoutResponse {
    private final Book book;
}
