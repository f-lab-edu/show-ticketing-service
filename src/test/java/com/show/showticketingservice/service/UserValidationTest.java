package com.show.showticketingservice.service;

import com.show.showticketingservice.model.UserDTO;
import org.junit.jupiter.api.*;
import javax.validation.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTest {

    private Validator validator;

    private UserDTO getUserId(String id) {
        return UserDTO.builder()
                .userId(id)
                .password("123456!aA")
                .email("tls7gkr@naver.com")
                .name("강신학")
                .phoneNum("010-9905-7416")
                .address("부천시 오정구")
                .build();
    }

    private UserDTO getUserPassword(String password) {
        return UserDTO.builder()
                .userId("tlsgkr7416")
                .password(password)
                .email("tls7gkr@naver.com")
                .name("강신학")
                .phoneNum("010-9905-7416")
                .address("부천시 오정구")
                .build();
    }

    private UserDTO getUserEmail(String email) {
        return UserDTO.builder()
                .userId("tlsgkr7416")
                .password("123456!aA")
                .email(email)
                .name("강신학")
                .phoneNum("010-9905-7416")
                .address("부천시 오정구")
                .build();
    }

    @BeforeEach
    public void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("20자리 이상 id를 입력할시 validation위반이 발생합니다.")
    public void checkUserIdLengeh() {
        String id = "aaaaaaaa123aaaaaaaaaaaa";
        UserDTO testMember = getUserId(id);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(testMember);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("id를 입력하지 않을시 validation위반이 발생합니다.")
    public void checkUserIdNotBlankFalse() {
        String id = "";
        UserDTO testMember = getUserId(id);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(testMember);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("비밀번호를 숫자,특수문자,영문 대,소문자가 포함된 6~20자리를 입력하지 않을시 validation위반이 발생합니다.")
    public void checkUserPasswordFalse() {
        String password = "1234";
        UserDTO testMember = getUserPassword(password);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(testMember);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("비밀번호를 숫자,특수문자,영문 대,소문자가 포함된 6~20자리를 입력할시 성공합니다..")
    public void checkUserPasswordTrue() {
        String password = "!1234aAb5";
        UserDTO testMember = getUserPassword(password);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(testMember);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Email형식을 위반할시 validation위반이 발생합니다.")
    public void checkUserEmailFalse() {
        String email = "aaa1naver.com";
        UserDTO testMember = getUserEmail(email);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(testMember);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Email형식을 사용할시 성공합니다.")
    public void checkUserEmailTrue() {
        String email = "aaa1@naver.com";
        UserDTO testMember = getUserEmail(email);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(testMember);
        assertTrue(violations.isEmpty());
    }
}
