package com.show.showticketingservice.exception.performance;

public class PerformanceTicketNotExistsException extends RuntimeException {

    public PerformanceTicketNotExistsException() {
        super("예매할 공연 티켓이 존재하지 않습니다.");
    }
}
