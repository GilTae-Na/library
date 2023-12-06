package com.ll.library.boundedContext.member.service;

import com.ll.library.base.jwt.JwtProvider;
import com.ll.library.base.rsData.RsData;
import com.ll.library.boundedContext.member.controller.ApiV1MemberController;
import com.ll.library.boundedContext.member.entity.Member;
import com.ll.library.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public RsData<Member> join(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        Member rsMember = memberRepository.save(member);

        return RsData.of(
                "S-1",
                "성공",
                rsMember
        );
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public String genAccessToken(String username, String password) {
        Member member = findByUsername(username).orElse(null);

        if(member == null){return null;}

        if(!passwordEncoder.matches(password, member.getPassword())){
            return null;
        }

        return jwtProvider.genToken(member.toClaims(), 60 * 60 * 24 * 365);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
}