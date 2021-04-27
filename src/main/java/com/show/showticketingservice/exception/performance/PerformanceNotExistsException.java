package com.show.showticketingservice.exception.performance;

public class PerformanceNotExistsException extends RuntimeException {

    public PerformanceNotExistsException() {
        super("공연 정보가 존재 하지 않습니다.");
    }
}
