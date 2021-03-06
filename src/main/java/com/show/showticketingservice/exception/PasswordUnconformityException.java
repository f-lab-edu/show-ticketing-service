package com.show.showticketingservice.exception;

public class PasswordUnconformityException extends RuntimeException {

    public PasswordUnconformityException() {}

    public PasswordUnconformityException(String message) {
        super(message);
    }
}
