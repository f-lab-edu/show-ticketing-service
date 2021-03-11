package com.show.showticketingservice.exception.authentication;

public class LoginUserNotExistsException extends RuntimeException {

    public LoginUserNotExistsException() {
        super("로그인을 하셔야 입장 가능합니다.");
    }
}
