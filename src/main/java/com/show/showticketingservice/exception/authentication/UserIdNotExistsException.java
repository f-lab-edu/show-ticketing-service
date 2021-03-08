package com.show.showticketingservice.exception.authentication;

public class UserIdNotExistsException extends RuntimeException {

    public UserIdNotExistsException() {
        super("존재하지 않는 ID 입니다.");
    }
}
