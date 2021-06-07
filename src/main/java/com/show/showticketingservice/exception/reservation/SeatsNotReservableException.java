package com.show.showticketingservice.exception.reservation;

public class SeatsNotReservableException extends RuntimeException {

    public SeatsNotReservableException(String message) {
        super(message);
    }

}
