package com.show.showticketingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.show.showticketingservice.model.enumerations.UserType;
import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.model.user.UserSession;
import com.show.showticketingservice.model.user.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class MyPageControllerTest {

    private UserRequest testUser;

    private UserSession userSession;

    private UserUpdateRequest updateRequest;

    private MockMvc mvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockHttpSession httpSession;

    @BeforeEach
    public void init() {
        testUser = new UserRequest("testId1", "testPW1234#", "Test User", "010-1111-1111", "user1@example.com", "Seoul, South Korea", UserType.GENERAL);

        updateRequest = new UserUpdateRequest("!validPW123", "010-1234-5678", "Busan, South Korea");

        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private void insertUser(UserRequest userRequest) throws Exception {
        String content = objectMapper.writeValueAsString(userRequest);

        mvc.perform(post("/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    private void loginUser(UserRequest userRequest) throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest(userRequest.getUserId(), userRequest.getPassword());

        String content = objectMapper.writeValueAsString(userLoginRequest);

        mvc.perform(post("/login")
                .content(content)
                .session(httpSession)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 탈퇴 시 status code 200을 리턴합니다.")
    public void unregisterUser() throws Exception {
        insertUser(testUser);
        loginUser(testUser);

        mvc.perform(post("/my-infos/unregister")
                .session(httpSession)
                .param("passwordRequest", testUser.getPassword()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원 탈퇴 요청 중 비밀번호가 일치하지 않을 시 status code 400을 리턴합니다.")
    public void unregisterUserfail() throws Exception {
        insertUser(testUser);
        loginUser(testUser);

        mvc.perform(post("/my-infos/unregister")
                .session(httpSession)
                .param("passwordRequest", "124d4"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 정보 수정 성공 시 Http Status 200 (OK) 리턴")
    public void updateUserInfo() throws Exception {
        insertUser(testUser);
        loginUser(testUser);

        String content = objectMapper.writeValueAsString(updateRequest);

        mvc.perform(put("/my-infos")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(httpSession))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 하지 않은 상태로 회원정보 수정 시도 시 Http Status 401 (Unauthorized) 리턴")
    public void updateUserInfoWithoutLogin() throws Exception {
        insertUser(testUser);

        String content = objectMapper.writeValueAsString(updateRequest);

        mvc.perform(put("/my-infos")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("invalid 한 값(PW)으로 회원정보 수정 시도 시 Http Status 400 (Bad Request) 리턴")
    public void updateUserWithInvalidPw() throws Exception {
        insertUser(testUser);
        loginUser(testUser);

        UserUpdateRequest invalidRequest = UserUpdateRequest.builder()
                .newPassword("invalidPw")
                .newPhoneNum(updateRequest.getNewPhoneNum())
                .newAddress(updateRequest.getNewAddress())
                .build();

        String content = objectMapper.writeValueAsString(invalidRequest);

        mvc.perform(put("/my-infos")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(httpSession))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("invalid 한 값(phoneNum)으로 회원정보 수정 시도 시 Http Status 400 (Bad Request) 리턴")
    public void updateUserWithInvalidPhoneNum() throws Exception {
        insertUser(testUser);
        loginUser(testUser);

        UserUpdateRequest invalidRequest = UserUpdateRequest.builder()
                .newPassword(updateRequest.getNewPassword())
                .newPhoneNum("010123-456")
                .newAddress(updateRequest.getNewAddress())
                .build();

        String content = objectMapper.writeValueAsString(invalidRequest);

        mvc.perform(put("/my-infos")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(httpSession))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}