package com.show.showticketingservice.utils;

import com.show.showticketingservice.exception.NotUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AllException {

    @ExceptionHandler(NotUserException.class)
    public ResponseEntity<String> loginFailException(NotUserException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
