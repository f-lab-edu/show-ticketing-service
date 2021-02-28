package com.show.showticketingservice.exception;

public class DuplicateIdException extends RuntimeException {

    public DuplicateIdException() {}

    public DuplicateIdException(String message) {
        super(message);
    }

}
