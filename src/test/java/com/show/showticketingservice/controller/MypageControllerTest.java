package com.show.showticketingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.show.showticketingservice.model.enumerations.UserType;
import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.model.user.UserSession;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class MypageControllerTest {

    private UserRequest userRequest;

    private UserSession userSession;

    private MockMvc mockMvc;

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

        userSession = new UserSession("sin7416", UserType.GENERAL);

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

        mockMvc.perform(post("/my-infos/unregister")
                .sessionAttr(USER, userSession)
                .param("passwordRequest", "123D!d4d4"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원탈퇴를 할 때 비밀번호가 일치하지 않을 시 status code 400을 리턴합니다.")
    public void unregisterUserfail() throws Exception {

        mockMvc.perform(post("/my-infos/unregister")
                .sessionAttr(USER, userSession)
                .param("passwordRequest", "124d4"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
