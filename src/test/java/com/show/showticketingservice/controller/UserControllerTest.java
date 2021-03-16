package com.show.showticketingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.show.showticketingservice.model.enumerations.UserType;
import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.tool.response.UserAuthorityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    private UserRequest testUser;
    private UserRequest managerAccount;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockHttpSession httpSession;

    @BeforeEach
    public void init() {
        testUser = new UserRequest("testId1", "testPW1234#", "Test User", "010-1111-1111", "user1@example.com", "Seoul, South Korea", UserType.GENERAL);
        managerAccount = new UserRequest("managerTest1", "testPW1234#", "Test Manager", "010-1111-1111", "user1@example.com", "Seoul, South Korea", UserType.MANAGER);
    }

    private void insertTestUser(UserRequest user) throws Exception {
        String content = objectMapper.writeValueAsString(user);

        mvc.perform(post("/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공 시 Http Status 200 (OK) 리턴")
    public void login() throws Exception {
        insertTestUser(testUser);

        UserLoginRequest userLoginRequest = new UserLoginRequest(testUser.getUserId(), testUser.getPassword());

        String content = objectMapper.writeValueAsString(userLoginRequest);

        mvc.perform(post("/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(httpSession))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("일반 계정으로 로그인 성공 시 Http Status 200 (OK) 과 GENERAL 권한(1) 리턴")
    public void loginWithGeneralAccount() throws Exception {
        insertTestUser(testUser);

        UserLoginRequest userLoginRequest = new UserLoginRequest(testUser.getUserId(), testUser.getPassword());

        String content = objectMapper.writeValueAsString(userLoginRequest);
        String response = objectMapper.writeValueAsString(UserAuthorityResponse.OK_GENERAL.getBody());

        mvc.perform(post("/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(httpSession))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(print());
    }

    @Test
    @DisplayName("관리자 계정으로 로그인 성공 시 Http Status 200 (OK) 과 MANAGER 권한(2) 리턴")
    public void loginWithManager() throws Exception {
        insertTestUser(managerAccount);

        UserLoginRequest userLoginRequest = new UserLoginRequest(managerAccount.getUserId(), managerAccount.getPassword());

        String content = objectMapper.writeValueAsString(userLoginRequest);
        String response = objectMapper.writeValueAsString(UserAuthorityResponse.OK_MANAGER.getBody());

        mvc.perform(post("/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(httpSession))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(print());
    }

    @Test
    @DisplayName("등록되지 않은 ID로 로그인 시 Http Status 400 (Bad Request) 리턴")
    public void loginWithInvalidId() throws Exception {
        insertTestUser(testUser);

        UserLoginRequest userLoginRequest = new UserLoginRequest("invalidUserId1234", testUser.getPassword());

        String content = objectMapper.writeValueAsString(userLoginRequest);

        mvc.perform(post("/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(httpSession))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("틀린 비밀번호로 로그인 시 Http Status 400 (Bad Request) 리턴")
    public void loginWithInvalidPassword() throws Exception {
        insertTestUser(testUser);

        UserLoginRequest userLoginRequest = new UserLoginRequest(testUser.getUserId(), "wrongPw1234@");

        String content = objectMapper.writeValueAsString(userLoginRequest);

        mvc.perform(post("/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(httpSession))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("이미 로그인 상태일 때 로그인 시도 시 Http 409 Status (Conflict) 리턴")
    public void loginDuplicated() throws Exception {
        login();

        UserLoginRequest userLoginRequest = new UserLoginRequest(testUser.getUserId(), testUser.getPassword());

        String content = objectMapper.writeValueAsString(userLoginRequest);

        mvc.perform(post("/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(httpSession))
                .andExpect(status().isConflict())
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

        UserRequest newUser = new UserRequest("testId1", "testPW1111#", "New UserRequest", "010-1111-1234", "user2@example.com", "Seoul, South Korea", UserType.GENERAL);

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

    @Test
    @DisplayName("로그아웃 성공 시 Http Status 200 (OK) 리턴")
    public void logout() throws Exception {
        login();

        mvc.perform(get("/logout")
                .session(httpSession))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 되지 않은 상태에서 로그아웃 시도 시 Http Status 401 (Unauthorized) 리턴")
    public void nullUserLogout() throws Exception {
        mvc.perform(get("/logout")
                .session(httpSession))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
