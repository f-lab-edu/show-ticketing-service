package com.show.showticketingservice.exception.performance;

public class PerformanceAlreadyExistsException extends RuntimeException {

    public PerformanceAlreadyExistsException() {
        super("동일한 이름의 공연이 존재합니다.");
    }
}
