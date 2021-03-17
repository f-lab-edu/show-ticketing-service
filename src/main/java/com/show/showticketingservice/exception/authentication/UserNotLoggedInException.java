package com.show.showticketingservice.exception.authentication;

public class UserNotLoggedInException extends RuntimeException{

    public UserNotLoggedInException() {
        super("로그인 된 계정이 없습니다.");
    }
}
