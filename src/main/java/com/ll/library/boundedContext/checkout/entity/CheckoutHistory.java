package com.ll.library.boundedContext.checkout.entity;

import com.ll.library.base.entity.BaseEntity;
import com.ll.library.boundedContext.book.entity.Book;
import com.ll.library.boundedContext.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class CheckoutHistory extends BaseEntity {

    @OneToOne
    private Book book; // 책은 하나만 대출 가능

    @ManyToOne
    private Member member; //여러 회원들의 대출관리, 장바구니같은 느낌

    private LocalDateTime checkoutDate;

    private LocalDateTime returnDate;

    public void setReturnDate(LocalDateTime time){
        this.returnDate = time;
    }
}
