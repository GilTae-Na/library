package com.ll.library.boundedContext.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BookRequest {

    @NotEmpty(message = "제목은 필수항목 입니다")
    private String title; //제목
    @NotNull
    private String author;//저자
    @NotNull
    private String introduce; //책 설명


}


