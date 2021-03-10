package com.show.showticketingservice.tool.advice;

import com.show.showticketingservice.exception.authentication.UserIdAlreadyExistsException;
import com.show.showticketingservice.exception.authentication.UserIdNotExistsException;
import com.show.showticketingservice.exception.authentication.UserNotLoggedInException;
import com.show.showticketingservice.exception.authentication.UserPasswordWrongException;
import com.show.showticketingservice.model.exception.ExceptionResponse;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class AuthenticationExceptionAdvice {

    @ExceptionHandler(UserIdNotExistsException.class)
    public ResponseEntity<ExceptionResponse> userIdNotExistsException(final UserIdNotExistsException e, WebRequest request) {
        log.error("login failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserPasswordWrongException.class)
    public ResponseEntity<ExceptionResponse> userPasswordWrongException(final UserPasswordWrongException e, WebRequest request) {
        log.error("login failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateRequestException.class)
    public ResponseEntity<ExceptionResponse> alreadyLoginException(final DuplicateRequestException e, WebRequest request) {
        log.error("login Failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserIdAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> userIdAreadyExistsException(final UserIdAlreadyExistsException e, WebRequest request) {
        log.error("ID registration Failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<ExceptionResponse> userNotLoggedInException(final UserNotLoggedInException e, WebRequest request) {
        log.error("Unable to find User - User is not logged in", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
}
