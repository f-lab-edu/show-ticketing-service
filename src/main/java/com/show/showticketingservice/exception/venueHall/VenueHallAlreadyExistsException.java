package com.show.showticketingservice.exception.venueHall;

public class VenueHallAlreadyExistsException extends RuntimeException {

    public VenueHallAlreadyExistsException() {
        super("기존에 이미 존재하는 공연홀이 있습니다.");
    }
}
