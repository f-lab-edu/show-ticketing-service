package com.show.showticketingservice.exception.venueHall;

public class SameVenueHallAdditionException extends RuntimeException {

    public SameVenueHallAdditionException() {
        super("동일한 공연홀을 추가했습니다.");
    }
}
