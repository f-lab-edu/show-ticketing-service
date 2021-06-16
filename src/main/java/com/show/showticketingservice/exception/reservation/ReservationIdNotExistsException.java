package com.show.showticketingservice.exception.reservation;

public class ReservationIdNotExistsException extends RuntimeException {

    public ReservationIdNotExistsException(String message) {
        super(message);
    }
}
