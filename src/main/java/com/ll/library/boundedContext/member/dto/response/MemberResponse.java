package com.ll.library.boundedContext.member.dto.response;

import com.ll.library.boundedContext.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberResponse {
    private final Member member;
}
