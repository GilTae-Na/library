package com.ll.library.boundedContext.checkout.entity;

import com.ll.library.base.entity.BaseEntity;
import com.ll.library.boundedContext.book.entity.Book;
import com.ll.library.boundedContext.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class CheckoutHistory extends BaseEntity {

    @ManyToOne
    private Book book;

    @ManyToOne
    private Member member;

    private LocalDateTime checkoutDate;

    private LocalDateTime returnDate;
}
