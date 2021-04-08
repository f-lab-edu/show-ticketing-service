package com.show.showticketingservice.exception.venueHall;

public class VenueHallIdNotExistsException extends RuntimeException {

    public VenueHallIdNotExistsException() {
        super("공연홀 id가 존재하지 않습니다.");
    }
}
