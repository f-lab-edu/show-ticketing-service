package com.show.showticketingservice.exception.performance;

public class NoKeywordException extends RuntimeException {

    public NoKeywordException() {
        super("keyword가 존재하지 않습니다.");
    }
}
