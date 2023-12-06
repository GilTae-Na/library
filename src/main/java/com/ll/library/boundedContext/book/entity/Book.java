package com.ll.library.boundedContext.book.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.library.base.entity.BaseEntity;
import com.ll.library.boundedContext.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Book extends BaseEntity {

    private String title; //제목

    private String author;//저자

    private String introduce; //책 설명

    @ManyToOne //여러개의 책이 한 명의 사용자에게 등록될 수 있으므로
    private Member regMember; //등록한 멤버


}