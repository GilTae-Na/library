package com.ll.library.boundedContext.book.controller;

import com.ll.library.base.rq.Rq;
import com.ll.library.base.rsData.RsData;
import com.ll.library.boundedContext.book.entity.Book;
import com.ll.library.boundedContext.book.service.BookService;
import com.ll.library.boundedContext.member.entity.Member;
import com.ll.library.boundedContext.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/book", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1BookController", description = "로그인, 로그인 된 회원의 정보")

public class BookController {
    private final BookService bookService;
    private final Rq rq;


    @Data
    public static class RegisterRequest {
        @NotBlank
        private String title; //제목
        @NotBlank
        private String author;//저자
        @NotBlank
        private String introduce; //책 설명
    }

    @AllArgsConstructor
    @Getter
    public static class RegisterResponse {
        private final Book book;
    }

    @PostMapping(value = "", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "등록", security = @SecurityRequirement(name = "bearerAuth"))
    public RsData create(@Valid @RequestBody RegisterRequest registerRequest) {
        Member member = rq.getMember();

        RsData<Book> bookRs =  bookService.create(
                registerRequest.getTitle(),
                registerRequest.getAuthor(),
                registerRequest.getIntroduce(),
                member);

        if(bookRs.isFail()) return (RsData)bookRs;

        return RsData.of(
                bookRs.getResultCode(),
                bookRs.getMsg(),
                new RegisterResponse(bookRs.getData())
        );
    }




}