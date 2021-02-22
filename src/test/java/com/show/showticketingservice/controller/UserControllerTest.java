package com.show.showticketingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.show.showticketingservice.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    private User testUser;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        testUser = new User("testId1", "testPW1234#", "Test User", "010-1111-1111", "user1@example.com", "Seoul, South Korea");
    }

    private void insertTestUser(User testUser) throws Exception {
        String content = objectMapper.writeValueAsString(testUser);

        mvc.perform(post("/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 성공 시 Http Status 201 (Created) 리턴")
    public void signUp() throws Exception {
        insertTestUser(testUser);
    }

    @Test
    @DisplayName("이미 등록된 ID로 회원가입 시도 시 Http Status 409 (Conflict) 리턴")
    public void duplicatedIdSignUp() throws Exception {
        insertTestUser(testUser);

        User newUser = new User("testId1", "testPW1111#", "New User", "010-1111-1234", "user2@example.com", "Seoul, South Korea");

        String content = objectMapper.writeValueAsString(newUser);

        mvc.perform(post("/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @DisplayName("ID 중복체크 시 중복되지 않을 때 Http Status 200 (Ok) 리턴")
    public void idNotDuplicated() throws Exception {
        mvc.perform(get("/user-exists/newId1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("ID 중복체크 시 중복될 때 Http Status 409 (Conflict) 리턴")
    public void idDuplicated() throws Exception {
        insertTestUser(testUser);

        mvc.perform(get("/user-exists/testId1"))
                .andExpect(status().isConflict())
                .andDo(print());
    }

}