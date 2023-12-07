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


    /*public RsData<Book> checkoutBook(Member member, String title) { //대출하기
        Optional<Book> book = bookRepository.findByTitle(title);

        if(book.isEmpty()){
            return RsData.of("F-1", "%s 책이 없습니다.".formatted(title), null);
        }

        CheckoutHistory checkoutHistory = CheckoutHistory
                .builder()
                .member(member)
                .book(book.get())
                .checkoutDate(LocalDateTime.now())
                .build();


        member.getCheckoutHistories().add(checkoutHistory); //이 멤버가 대출해갔음
        book.get().getCheckoutHistories().add(checkoutHistory);   //이 책은 대출된거임

        checkoutHistoryRepository.save(checkoutHistory);

        return RsData.of("S-1",
                "%s 도서가 대출되었습니다.".formatted(book.get().getTitle()),
                book.get());
    }*/

    //-----------------------------------------------------------------------------------------------------
    /*// 특정 회원이 대출한 책 목록 조회
    public List<CheckoutHistory> getCheckoutHistoryForMember(Member member) {
        return checkoutHistoryRepository.findByMemberOrderByCheckoutDateDesc(member);
    }*/


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


    // 책 대출 하기 추가
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
    public void updateReturnDate(CheckoutHistory checkoutHistory) {
        checkoutHistory.setReturnDate(LocalDateTime.now());
        checkoutHistoryRepository.save(checkoutHistory);
    }


}
