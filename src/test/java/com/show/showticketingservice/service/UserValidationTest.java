package com.show.showticketingservice.service;

import com.show.showticketingservice.model.user.User;
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

    private User getUserWithId(String id) {
        return User.builder()
                .id(id)
                .password("password1234!")
                .name("name")
                .phoneNum("010-1234-5678")
                .email("test@example.com")
                .address("Seoul, South Korea")
                .build();
    }

    private User getUserWithPw(String password) {
        return User.builder()
                .id("idExample123")
                .password(password)
                .name("name")
                .phoneNum("010-1234-5678")
                .email("test@example.com")
                .address("Seoul, South Korea")
                .build();
    }

    private User getUserWithPhoneNum(String phoneNum) {
        return User.builder()
                .id("idExample123")
                .password("password1234!")
                .name("name")
                .phoneNum(phoneNum)
                .email("test@example.com")
                .address("Seoul, South Korea")
                .build();
    }

    private User getUserWithEmail(String email) {
        return User.builder()
                .id("idExample123")
                .password("password1234!")
                .name("name")
                .phoneNum("010-1234-5678")
                .email(email)
                .address("Seoul, South Korea")
                .build();
    }

    public void checkValidation(User user, boolean assertFlag) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (assertFlag)
            assertTrue(violations.isEmpty());
        else
            assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("valid id: 영대/소문자 또는 숫자 포함 6~20자 이내")
    public void validId() {
        String id = "abcd123";
        User user = getUserWithId(id);
        checkValidation(user, true);
    }

    @Test
    @DisplayName("5자리 이하의 id는 invalid")
    public void shortId() {
        String id = "ab12";
        User user = getUserWithId(id);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("20자리 초과의 id는 invalid")
    public void longId() {
        String id = "aaaaabbbbbccccc123456";
        User user = getUserWithId(id);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("공백이 포함된 id는 invalid")
    public void idWithBlank() {
        String id = "abcd 123";
        User user = getUserWithId(id);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("특수문자가 포함된 id는 invalid")
    public void idWithSpecialChar() {
        String id = "abcd@123";
        User user = getUserWithId(id);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("영대/소문자, 숫자가 아닌 문자가 포함된 id는 invalid")
    public void idWithOtherChar() {
        String id = "abcd아이디123";
        User user = getUserWithId(id);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("null인 id는 invalid")
    public void nullId() {
        String id = null;
        User user = getUserWithId(id);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("valid password: 영대/소문자, 숫자, 특수문자 모두 포함 6~20자 이내")
    public void validPw() {
        String password = "pwpw1234@";
        User user = getUserWithPw(password);
        checkValidation(user, true);
    }

    @Test
    @DisplayName("5자리 이하의 pw는 invalid")
    public void shortPw() {
        String password = "Pw12@";
        User user = getUserWithPw(password);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("20자리 초과의 pw는 invalid")
    public void longPw() {
        String password = "aaaaabbbbbccccc123456!@#";
        User user = getUserWithPw(password);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("공백이 포함된 pw는 invalid")
    public void pwWithBlank() {
        String password = "abcd 123 !@#";
        User user = getUserWithPw(password);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("특수문자가 생략된 pw는 invalid")
    public void pwWithSpecialChar() {
        String password = "pwpw123";
        User user = getUserWithPw(password);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("null인 pw는 invalid")
    public void nullPw() {
        String password = null;
        User user = getUserWithPw(password);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("valid name: 15자 이내")
    public void validName() {
        String name = "name example";
        User user = User.builder()
                .id("idExample123")
                .password("password123!@")
                .name(name)
                .phoneNum("010-1234-5678")
                .email("test@example.com")
                .address("Seoul, South Korea")
                .build();
        checkValidation(user, true);
    }

    @Test
    @DisplayName("valid phoneNum: '-' 포함 '010-[4자리 숫자]-[4자리 숫자]' 휴대전화번호 형식")
    public void validPhoneNum() {
        String phoneNum = "010-1234-5678";
        User user = getUserWithPhoneNum(phoneNum);
        checkValidation(user, true);
    }

    @Test
    @DisplayName("형식에 맞지 않는 전화번호 형식")
    public void invalidPhoneNum() {
        String phoneNum = "010-12345-678";
        User user = getUserWithPhoneNum(phoneNum);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("하이픈(-)이 없는 전화번호는 invalid")
    public void phoneNumWithOutHyphen() {
        String phoneNum = "01012345678";
        User user = getUserWithPhoneNum(phoneNum);
        checkValidation(user, false);
    }

    @Test
    public void validEmail() {
        String email = "user@example.com";
        User user = getUserWithEmail(email);
        checkValidation(user, true);
    }

    @Test
    public void invalidEmail() {
        String email = "user.example.com";
        User user = getUserWithEmail(email);
        checkValidation(user, false);
    }

    @Test
    @DisplayName("valid address: 100자 이내")
    public void validAddress() {
        String address = "대한민국 서울시 종로구 OO동 134-2번지";
        User user = User.builder()
                .id("idExample123")
                .password("password123!@")
                .name("name")
                .phoneNum("010-1234-5678")
                .email("test@example.com")
                .address(address)
                .build();
        checkValidation(user, true);
    }
}
