package com.show.showticketingservice.exception.authentication;

public class UserPasswordWrongException extends RuntimeException {

    public UserPasswordWrongException() {
        super("비밀번호가 틀렸습니다.");
    }
}
