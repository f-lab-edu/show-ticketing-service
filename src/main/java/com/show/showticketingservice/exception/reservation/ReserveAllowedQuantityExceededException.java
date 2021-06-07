package com.show.showticketingservice.exception.reservation;

public class ReserveAllowedQuantityExceededException extends RuntimeException {

    public ReserveAllowedQuantityExceededException(String message) {
        super(message);
    }

}
