package com.show.showticketingservice.service;

import com.show.showticketingservice.model.user.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidationTest {

    private Validator validator;

    @BeforeEach
    public void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private UserRequest getUserWithId(String userId) {
        return UserRequest.builder()
                .userId(userId)
                .password("password1234!")
                .name("name")
                .phoneNum("010-1234-5678")
                .email("test@example.com")
                .address("Seoul, South Korea")
                .userType(1)
                .build();
    }

    private UserRequest getUserWithPw(String password) {
        return UserRequest.builder()
                .userId("idExample123")
                .password(password)
                .name("name")
                .phoneNum("010-1234-5678")
                .email("test@example.com")
                .address("Seoul, South Korea")
                .userType(1)
                .build();
    }

    private UserRequest getUserWithPhoneNum(String phoneNum) {
        return UserRequest.builder()
                .userId("idExample123")
                .password("password1234!")
                .name("name")
                .phoneNum(phoneNum)
                .email("test@example.com")
                .address("Seoul, South Korea")
                .userType(1)
                .build();
    }

    private UserRequest getUserWithEmail(String email) {
        return UserRequest.builder()
                .userId("idExample123")
                .password("password1234!")
                .name("name")
                .phoneNum("010-1234-5678")
                .email(email)
                .address("Seoul, South Korea")
                .userType(1)
                .build();
    }

    public void checkValidation(UserRequest userRequest, boolean assertFlag) {
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        if (assertFlag)
            assertTrue(violations.isEmpty());
        else
            assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("valid id: 영대/소문자 또는 숫자 포함 6~20자 이내")
    public void validId() {
        String id = "abcd123";
        UserRequest userRequest = getUserWithId(id);
        checkValidation(userRequest, true);
    }

    @Test
    @DisplayName("5자리 이하의 id는 invalid")
    public void shortId() {
        String id = "ab12";
        UserRequest userRequest = getUserWithId(id);
        checkValidation(userRequest, false);
    }

    @Test
    @DisplayName("20자리 초과의 id는 invalid")
    public void longId() {
        String id = "aaaaabbbbbccccc123456";
        UserRequest userRequest = getUserWithId(id);
        checkValidation(userRequest, false);
    }

    @Test
    @DisplayName("공백이 포함된 id는 invalid")
    public void idWithBlank() {
        String id = "abcd 123";
        UserRequest userRequest = getUserWithId(id);
        checkValidation(userRequest, false);
    }

    @Test
    @DisplayName("특수문자가 포함된 id는 invalid")
    public void idWithSpecialChar() {
        String id = "abcd@123";
        UserRequest userRequest = getUserWithId(id);
        checkValidation(userRequest, false);
    }

    @Test
    @DisplayName("영대/소문자, 숫자가 아닌 문자가 포함된 id는 invalid")
    public void idWithOtherChar() {
        String id = "abcd아이디123";
        UserRequest userRequest = getUserWithId(id);
        checkValidation(userRequest, false);
    }

    @Test
    @DisplayName("null인 id는 invalid")
    public void nullId() {
        String id = null;
        UserRequest userRequest = getUserWithId(id);
        checkValidation(userRequest, false);
    }

    @Test
    @DisplayName("valid password: 영대/소문자, 숫자, 특수문자 모두 포함 6~20자 이내")
    public void validPw() {
        String password = "pwpw1234@";
        UserRequest userRequest = getUserWithPw(password);
        checkValidation(userRequest, true);
    }

    @Test
    @DisplayName("5자리 이하의 pw는 invalid")
    public void shortPw() {
        String password = "Pw12@";
        UserRequest userRequest = getUserWithPw(password);
        checkValidation(userRequest, false);
    }

    @Test
    @DisplayName("20자리 초과의 pw는 invalid")
    public void longPw() {
        String password = "aaaaabbbbbccccc123456!@#";
        UserRequest ususerRequest = getUserWithPw(password);
        checkValidation(ususerRequest, false);
    }

    @Test
    @DisplayName("공백이 포함된 pw는 invalid")
    public void pwWithBlank() {
        String password = "abcd 123 !@#";
        UserRequest userRequest = getUserWithPw(password);
        checkValidation(userRequest, false);
    }

    @Test
    @DisplayName("특수문자가 생략된 pw는 invalid")
    public void pwWithSpecialChar() {
        String password = "pwpw123";
        UserRequest userRequest = getUserWithPw(password);
        checkValidation(userRequest, false);
    }

    @Test
    @DisplayName("null인 pw는 invalid")
    public void nullPw() {
        String password = null;
        UserRequest userRequest = getUserWithPw(password);
        checkValidation(userRequest, false);
    }

    @Test
    @DisplayName("valid name: 15자 이내")
    public void validName() {
        String name = "name example";
        UserRequest userRequest = UserRequest.builder()
                .userId("idExample123")
                .password("password123!@")
                .name(name)
                .phoneNum("010-1234-5678")
                .email("test@example.com")
                .address("Seoul, South Korea")
                .userType(1)
                .build();
        checkValidation(userRequest, true);
    }

    @Test
    @DisplayName("valid phoneNum: '-' 포함 '010-[4자리 숫자]-[4자리 숫자]' 휴대전화번호 형식")
    public void validPhoneNum() {
        String phoneNum = "010-1234-5678";
        UserRequest userRequest = getUserWithPhoneNum(phoneNum);
        checkValidation(userRequest, true);
    }

    @Test
    @DisplayName("형식에 맞지 않는 전화번호 형식")
    public void invalidPhoneNum() {
        String phoneNum = "010-12345-678";
        UserRequest userRequest = getUserWithPhoneNum(phoneNum);
        checkValidation(userRequest, false);
    }

    @Test
    @DisplayName("하이픈(-)이 없는 전화번호는 invalid")
    public void phoneNumWithOutHyphen() {
        String phoneNum = "01012345678";
        UserRequest userRequest = getUserWithPhoneNum(phoneNum);
        checkValidation(userRequest, false);
    }

    @Test
    public void validEmail() {
        String email = "user@example.com";
        UserRequest userRequest = getUserWithEmail(email);
        checkValidation(userRequest, true);
    }

    @Test
    public void invalidEmail() {
        String email = "user.example.com";
        UserRequest userRequest = getUserWithEmail(email);
        checkValidation(userRequest, false);
    }

    @Test
    @DisplayName("valid address: 100자 이내")
    public void validAddress() {
        String address = "대한민국 서울시 종로구 OO동 134-2번지";
        UserRequest userRequest = UserRequest.builder()
                .userId("idExample123")
                .password("password123!@")
                .name("name")
                .phoneNum("010-1234-5678")
                .email("test@example.com")
                .address(address)
                .userType(1)
                .build();
        checkValidation(userRequest, true);
    }

    @Test
    @DisplayName("userType의 범위(1~2)를 넘어가면 invalid")
    public void invalidUserType() {
        UserRequest userRequest = UserRequest.builder()
                .userId("idExample123")
                .password("password123!@")
                .name("name")
                .phoneNum("010-1234-5678")
                .email("test@example.com")
                .address("address")
                .userType(4)
                .build();
        checkValidation(userRequest, false);
    }
}
