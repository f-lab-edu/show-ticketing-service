package com.show.showticketingservice.exception.venueHall;

public class VenueHallDuplicationException extends RuntimeException {

    public VenueHallDuplicationException() {
        super("중복되는 공연홀이 존재합니다.");
    }
}
