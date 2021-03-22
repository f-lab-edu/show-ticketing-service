package com.show.showticketingservice.exception.venue;

public class VenueAlreadyExistsException extends RuntimeException {

    public VenueAlreadyExistsException() {
        super("이미 존재하는 공연장입니다.");
    }
}
