package com.ll.library.boundedContext.checkout.controller;

import com.ll.library.base.rq.Rq;
import com.ll.library.base.rsData.RsData;
import com.ll.library.boundedContext.book.controller.BookController;
import com.ll.library.boundedContext.book.entity.Book;
import com.ll.library.boundedContext.book.repository.BookRepository;
import com.ll.library.boundedContext.checkout.service.CheckoutHistoryService;
import com.ll.library.boundedContext.member.entity.Member;
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

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/checkout", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1CheckoutController", description = "책 대출 ")
public class CheckoutHistoryController {
    private final CheckoutHistoryService checkoutHistoryService;
    private final BookRepository bookRepository;
    private final Rq rq;


    //-----------------------대출확인--------------
    @Data
    public static class CheckoutRequest {
        @NotBlank
        private String title; //제목
    }

    @AllArgsConstructor
    @Getter
    public static class CheckoutResponse {
        private final Book book;
    }

    @PostMapping(value = "/checkout-history", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "대출이력을 확인, 로그인 안해도 확인 가능")
    public RsData confirmCheckout(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        Optional<Book> book = bookRepository.findByTitle(checkoutRequest.title);
        if(book.isEmpty()){
            return RsData.of("F-1", "%s 책은 없습니다.".formatted(checkoutRequest.title), null);
        }

        return checkoutHistoryService.CheckoutHistory(book.get());

    }

    //대출하기
    @PostMapping(value = "/checkout", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "대출하기, 로그인한 사용자만", security = @SecurityRequirement(name = "bearerAuth"))
    public RsData Checkout(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        Member member = rq.getMember();
        Optional<Book> book = bookRepository.findByTitle(checkoutRequest.title);

        if(book.isEmpty()){
            return RsData.of("F-1", "%s 책은 없습니다.".formatted(checkoutRequest.title), null);
        }

        RsData<Book> rsBook = checkoutHistoryService.addCheckoutHistory(book.get(), member);

        //if(rsBook.isFail()) return (RsData)rsBook;

        return RsData.of(
                rsBook.getResultCode(),
                rsBook.getMsg(),
                new CheckoutResponse(rsBook.getData()) //대출했으니 책 데이터를 준다.
        );
    }
}
