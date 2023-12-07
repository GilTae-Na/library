package com.ll.library.boundedContext.book.controller;

import com.ll.library.base.rq.Rq;
import com.ll.library.base.rsData.RsData;
import com.ll.library.boundedContext.book.entity.Book;
import com.ll.library.boundedContext.book.service.BookService;
import com.ll.library.boundedContext.checkout.entity.CheckoutHistory;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/book", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1BookController", description = "로그인, 로그인 된 회원의 정보")
public class BookController {

    @Autowired
    private final BookService bookService;
    private final Rq rq;


    //-------------------------------등록----------

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

        RsData<Book> rsBook =  bookService.create(
                registerRequest.getTitle(),
                registerRequest.getAuthor(),
                registerRequest.getIntroduce(),
                member);

        if(rsBook.isFail()) return (RsData)rsBook;

        return RsData.of(
                rsBook.getResultCode(),
                rsBook.getMsg(),
                new RegisterResponse(rsBook.getData())
        );
    }


//-------------------------------수정----------

    @Data
    public static class ModifyRequest {
        @NotBlank
        private String title; //제목
        @NotBlank
        private String author;//저자
        @NotBlank
        private String introduce; //책 설명
    }

    @AllArgsConstructor
    @Getter
    public static class ModifyResponse {
        private final Book book;
    }


    @PatchMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "수정", security = @SecurityRequirement(name = "bearerAuth"))
    public RsData<ModifyResponse> modify(
            @Valid @RequestBody ModifyRequest modifyRequest,
            @PathVariable Long id
    ) {
        Member member = rq.getMember();

        Optional<Book> opBook =  bookService.findById(id);

        if(opBook.isEmpty()) return RsData.of(
                "F-1",
                "책이 존재하지 않습니다.",
                null
        );

        RsData canModifyRs =  bookService.canModify(member, opBook.get());

        if (canModifyRs.isFail()) return canModifyRs;

        RsData<Book> modifyRs = bookService.modify(opBook.get(), modifyRequest.getTitle(), modifyRequest.getAuthor(), modifyRequest.getIntroduce());

        return RsData.of(
                modifyRs.getResultCode(),
                modifyRs.getMsg(),
                new ModifyResponse(modifyRs.getData())
        );

    }


}
