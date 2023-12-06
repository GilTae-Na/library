package com.ll.library.boundedContext.member.controller;

import com.ll.library.base.rq.Rq;
import com.ll.library.base.rsData.RsData;
import com.ll.library.boundedContext.member.entity.Member;
import com.ll.library.boundedContext.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1MemberController", description = "로그인, 로그인 된 회원의 정보")
public class ApiV1MemberController {

    private final MemberService memberService;
    private final Rq rq;

    @Data
    public static class LoginRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @AllArgsConstructor
    @Getter
    public static class LoginResponse {
        private final String accessToken;
    }

    @PostMapping("/login")
    @Operation(summary = "로그인, 엑세스 토큰 발급")
    public RsData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String accessToken = memberService.genAccessToken(loginRequest.getUsername(), loginRequest.getPassword());

        return RsData.of(
                "S-1",
                "엑세스토큰이 생성되었습니다.",
                new LoginResponse(accessToken)
        );
    }

    @GetMapping(value ="/join", consumes = ALL_VALUE)
    public RsData showJoin() {

        return RsData.of(
                "S-1",
                "로그인 폼 입니다."
        );
    }

    @Data
    public static class JoinRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String email;

    }

    @AllArgsConstructor
    @Getter
    public static class JoinResponse {
        private final Member member;
    }

    @PostMapping("/join")
    public RsData<JoinResponse> join(@Valid @RequestBody JoinRequest joinRequest) {
        RsData<Member> joinRs = memberService.join(joinRequest.getUsername(), joinRequest.getPassword(), joinRequest.getEmail());

        if (joinRs.isFail()) {
            return RsData.of("F-1", "회원가입 실패");
        }

        return RsData.of(
                "S-1",
                "회원가입 완료.",
                new JoinResponse(joinRs.getData())
        );
    }





    @AllArgsConstructor
    @Getter
    public static class MeResponse {
        private final Member member;
    }

    // consumes = ALL_VALUE => 나는 딱히 JSON 을 입력받기를 고집하지 않겠다.
    @GetMapping(value = "/me", consumes = ALL_VALUE)
    @Operation(summary = "로그인된 사용자의 정보", security = @SecurityRequirement(name = "bearerAuth"))
    public RsData<MeResponse> me(@AuthenticationPrincipal User user) {
        Member member = memberService.findByUsername(user.getUsername()).get();

        return RsData.of(
                "S-1",
                "성공",
                new MeResponse(member)
        );
    }


}