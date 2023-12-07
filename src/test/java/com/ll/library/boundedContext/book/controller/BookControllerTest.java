package com.ll.library.boundedContext.book.controller;

import com.ll.library.boundedContext.book.entity.Book;
import com.ll.library.boundedContext.member.entity.Member;
import com.ll.library.boundedContext.member.repository.MemberRepository;
import com.ll.library.boundedContext.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("POST /articles/1, 책등록")
    @WithUserDetails("user1")
    void t3() throws Exception {
        // When
        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/book")
                                .content("""
                                        {
                                            "title": "제목 new",
                                            "author": "작가 new",
                                            "introduce": "설명 new"
                                        }
                                        """)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.resultCode").value("S-1"))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data").exists());
    }


    @Test
    @DisplayName("PATCH /book/id, 책 수정")
    @WithUserDetails("user1")
    void t4() throws Exception {
        // When
        ResultActions resultActions = mvc
                .perform(
                        patch("/api/v1/book/1")
                                .content("""
                                        {
                                            "title": "제목 수정1",
                                            "author": "작가 수정1",
                                            "introduce": "설명 수정1"
                                        }
                                        """)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.resultCode").value("S-1"))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data.book.title").value("제목 수정1"))
                .andExpect(jsonPath("$.data.book.author").value("작가 수정1"))
                .andExpect(jsonPath("$.data.book.introduce").value("설명 수정1"));
    }

}