package com.show.showticketingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.show.showticketingservice.model.enumerations.UserType;
import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.model.user.UserResponse;
import com.show.showticketingservice.tool.encryptor.PasswordEncryptor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.show.showticketingservice.tool.constants.UserConstant.USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class MypageControllerTest {

    private UserRequest userRequest;

    private UserResponse sessionUserResponse;

    private MockMvc mockMvc;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        userRequest = userRequest.builder()
                .userId("sin7416")
                .password("123D!d4d4")
                .name("미미")
                .email("taa123@naver.com")
                .phoneNum("010-1234-5678")
                .address("경기도 부천시 오정구")
                .userType(UserType.GENERAL)
                .build();

        String hashPassword = passwordEncryptor.encrypt("123D!d4d4");

        sessionUserResponse = sessionUserResponse.builder()
                .userId("sin7416")
                .password(hashPassword)
                .name("미미")
                .email("taa123@naver.com")
                .phoneNum("010-1234-5678")
                .address("경기도 부천시 오정구")
                .userType(UserType.GENERAL)
                .build();

        String userContent = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post("/users")
                .content(userContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원탈퇴시 status code 200을 리턴합니다.")
    public void unregisterUser() throws Exception {

        mockMvc.perform(post("/my-pages/unregister")
                .sessionAttr(USER, sessionUserResponse)
                .param("passwordRequest", "123D!d4d4"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
