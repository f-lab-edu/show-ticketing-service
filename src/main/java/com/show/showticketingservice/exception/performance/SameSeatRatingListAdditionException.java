package com.show.showticketingservice.exception.performance;

public class SameSeatRatingListAdditionException extends RuntimeException {

    public SameSeatRatingListAdditionException() {
        super("중복되는 좌석 등급을 추가하였습니다.");
    }
}
