package com.show.showticketingservice.exception.authentication;

public class UserAlreadyLoggedInException extends RuntimeException {

    public UserAlreadyLoggedInException() {
        super("이미 로그인되어 있습니다.");
    }
}
