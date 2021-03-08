package com.show.showticketingservice.exception.authentication;

public class UserIdAlreadyExistsException extends RuntimeException {

    public UserIdAlreadyExistsException() {
        super("이미 존재하는 ID 입니다.");
    }
}
