package com.show.showticketingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.show.showticketingservice.model.LoginDTO;
import com.show.showticketingservice.model.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.show.showticketingservice.utils.constant.UserConstant.LOGIN_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MypageControllerControllerTest {

    private UserDTO userDTO;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        userDTO = UserDTO.builder()
                .userId("sin7416")
                .password("123D!d4d4")
                .name("강신학")
                .email("tls7gkr@naver.com")
                .phoneNum("010-9905-7416")
                .address("경기도 부천시 오정구")
                .build();

        String userContent = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/users/signup")
                .content(userContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원탈퇴시 status code 200을 리턴합니다.")
    public void unregisterUser() throws Exception {

        mockMvc.perform(post("/mypages/unregister")
                .sessionAttr(LOGIN_ID, "sin7416")
                .param("password", "123D!d4d4"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
