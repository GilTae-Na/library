package com.ll.library.base.initData;

import com.ll.library.boundedContext.book.entity.Book;
import com.ll.library.boundedContext.book.service.BookService;
import com.ll.library.boundedContext.checkout.service.CheckoutHistoryService;
import com.ll.library.boundedContext.member.entity.Member;
import com.ll.library.boundedContext.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile({"dev", "test"})
public class NotProd {

    @Bean
    CommandLineRunner initData(MemberService memberService, PasswordEncoder passwordEncoder, BookService bookService, CheckoutHistoryService checkoutHistoryService) {
        String password = passwordEncoder.encode("1234");
        return args -> {
            Member member1 = memberService.join("user1", password, "user1@test.com").getData();
            Member member2 = memberService.join("user2", password, "user2@test.com").getData();

            Book book1 = bookService.create("제목 1", "작가 1", "내용1", member1).getData();
            Book book2 = bookService.create("제목 2", "작가 2", "내용2", member1).getData();
            Book book3 = bookService.create("제목 3", "작가 3", "내용3", member1).getData();

            Book book4 = bookService.create("제목 4", "작가 4", "내용4", member2).getData();
            Book book5 = bookService.create("제목 5", "작가 5", "내용5", member2).getData();
            Book book6 = bookService.create("제목 6", "작가 6", "내용6", member2).getData();

            checkoutHistoryService.addCheckoutHistory(book2, member1);
        };

    }
}