package com.show.showticketingservice.utils.controllerAdvice;

import com.show.showticketingservice.exception.DuplicateIdException;
import com.show.showticketingservice.exception.IdUnconformityException;
import com.show.showticketingservice.exception.PasswordUnconformityException;
import com.show.showticketingservice.exception.UnAuthorityUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(IdUnconformityException.class)
    public ResponseEntity<String> loginFailException(IdUnconformityException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateIdException.class)
    public ResponseEntity<String> duplicateIdException(DuplicateIdException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordUnconformityException.class)
    public ResponseEntity<String> mismatchPassword(PasswordUnconformityException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
