package com.show.showticketingservice.exception.performance;

public class SeatPriceAlreadyExistsException extends RuntimeException {

    public SeatPriceAlreadyExistsException(){
        super("이 공연은 좌석 가격이 이미 등록되어 있습니다.");
    }
}
