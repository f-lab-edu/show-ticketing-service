package com.show.showticketingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.show.showticketingservice.model.LoginDTO;
import com.show.showticketingservice.model.UserDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    private UserDTO userDTO;

    private LoginDTO loginDTO;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private void testSingUp() throws Exception {

        userDTO = UserDTO.builder()
                .userId("sin7416")
                .password("123D!d4d4")
                .name("강신학")
                .email("tls7gkr@naver.com")
                .phoneNum("010-9905-7416")
                .address("경기도 부천시 오정구")
                .build();

        String content = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/users/signup")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입에 성공할 경우 status code 201을 리턴합니다.")
    public void registerUser() throws Exception {
        testSingUp();
    }

    @Test
    @DisplayName("중복된 id 회원이 있을 경우 status code 409를 린턴합니다.")
    public void checkDuplicateIdUser() throws Exception {
        testSingUp();

        userDTO = UserDTO.builder()
                .userId("sin7416")
                .password("ff3D!d4d4")
                .name("이용")
                .email("le7@naver.com")
                .phoneNum("010-9908-7567")
                .address("서울특별시 강남구")
                .build();

        String content = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/users/signup")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("로그인 성공시 status code 200을 리턴합니다.")
    public void loginUserSuccess() throws Exception {
        testSingUp();

        loginDTO = new LoginDTO("sin7416", "123D!d4d4");

        String content = objectMapper.writeValueAsString(loginDTO);

        mockMvc.perform(post("/users/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않을시 status code 400을 리턴합니다.")
    public void loginUserPasswordFail() throws Exception {

        testSingUp();

        loginDTO = new LoginDTO("sin7416", "123456!A");

        String content = objectMapper.writeValueAsString(loginDTO);

        mockMvc.perform(post("/users/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("아이디가 존재하지 않을시 status code 401을 리턴합니다.")
    public void loginExistentUserIdFail() throws Exception {

        loginDTO = new LoginDTO("he7466", "123456!A");

        String content = objectMapper.writeValueAsString(loginDTO);

        mockMvc.perform(post("/users/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
