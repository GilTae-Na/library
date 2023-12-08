package com.ll.library.boundedContext.book.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.library.base.entity.BaseEntity;
import com.ll.library.boundedContext.checkout.entity.CheckoutHistory;
import com.ll.library.boundedContext.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Book extends BaseEntity {

    private String title; //제목

    private String author;//저자

    private String introduce; //책 설명

    @ManyToOne(fetch = FetchType.LAZY) //여러개의 책이 한 명의 사용자에게 등록될 수 있으므로
    private Member regMember; //등록한 멤버

    public void bookModify(String inputTitle, String inputAuthor, String InputIntroduce){
        if (title != null) this.title = inputTitle;
        if (author != null) this.author = inputAuthor;
        if (introduce != null) this.introduce = InputIntroduce;
    }

}