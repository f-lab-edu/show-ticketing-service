package com.show.showticketingservice.exception.performance;

public class SeatNotExistsException extends RuntimeException {

    public SeatNotExistsException() {
        super("예매할 좌석이 존재하지 않습니다.");
    }
}
