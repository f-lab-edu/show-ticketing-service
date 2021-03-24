package com.show.showticketingservice.exception.authentication;

public class UserHaveNoAuthorityException extends RuntimeException {

    public UserHaveNoAuthorityException() {
        super("요청을 수행 할 권한이 없습니다.");
    }
}
