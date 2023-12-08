package com.ll.library.boundedContext.checkout.service;

import com.ll.library.base.rsData.RsData;
import com.ll.library.boundedContext.book.entity.Book;
import com.ll.library.boundedContext.book.repository.BookRepository;
import com.ll.library.boundedContext.checkout.entity.CheckoutHistory;
import com.ll.library.boundedContext.checkout.repository.CheckoutHistoryRepository;
import com.ll.library.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckoutHistoryService {
    private  final CheckoutHistoryRepository checkoutHistoryRepository;
    private  final BookRepository bookRepository;


    public RsData CheckoutHistory(Book book) {
        Optional<CheckoutHistory> checkoutHistory = checkoutHistoryRepository.findByBook_Title(book.getTitle());

        if(checkoutHistory.isEmpty()){ //대출기록이 없음
            return RsData.of("S-1", "%s 책은 대출이 가능합니다..".formatted(book.getTitle()), null);
        }

        if(checkoutHistory.get().getReturnDate().isBefore(LocalDateTime.now())){ //대출기록이 있지만 반납됨
            RsData.of("S-1", "%s 책은 반납되어 대출이 가능합니다.".formatted(book.getTitle()), null);
        }

        return RsData.of("F-1", "%s 책은 대출이 불가능합니다.".formatted(book.getTitle()), null);


    }


    // 책 대출 하기
    public RsData<Book> addCheckoutHistory(Book book, Member member) {

        CheckoutHistory checkoutHistory = CheckoutHistory
                .builder()
                .book(book)
                .member(member)
                .checkoutDate(LocalDateTime.now())
                .build();

        checkoutHistoryRepository.save(checkoutHistory);
        return RsData.of("S-1", "대출했습니다", book);
    }

    // 책 반납 기록 갱신
    public RsData updateReturnDate(Book book) {
        Optional<CheckoutHistory> checkoutHistory = checkoutHistoryRepository.findByBook_Title(book.getTitle());

        if(checkoutHistory.isEmpty()){
            return RsData.of("F-1", "%s 책은 대출기록이 없습니다. 다시 확인해주세요.".formatted(book.getTitle()), null);
        }
        checkoutHistory.get().setReturnDate(LocalDateTime.now());
        checkoutHistoryRepository.save(checkoutHistory.get());
        return RsData.of("S-1", "%s 책을 반납했습니다.".formatted(book.getTitle()), null);
    }

}
