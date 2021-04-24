package com.show.showticketingservice.exception.performance;

public class SeatPriceAlreadyExistsException extends RuntimeException {

    public SeatPriceAlreadyExistsException(String message){
        super(message);
    }
}
