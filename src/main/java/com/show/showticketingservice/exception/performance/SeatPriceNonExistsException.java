package com.show.showticketingservice.exception.performance;

public class SeatPriceNonExistsException extends RuntimeException {

    public SeatPriceNonExistsException(String message) {
        super(message);
    }
}
