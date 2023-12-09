package com.ll.library.boundedContext.book.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BookModifyRequest {
    private String title; //제목

    private String author;//저자

    private String introduce; //책 설명
}
