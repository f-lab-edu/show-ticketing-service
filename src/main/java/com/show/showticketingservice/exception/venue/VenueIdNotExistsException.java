package com.show.showticketingservice.exception.venue;

public class VenueIdNotExistsException extends RuntimeException {
    
    public VenueIdNotExistsException() {
        super("공연장 id가 존재하지 않습니다.");
    }
}
