package com.show.showticketingservice.tool.advice;

import com.show.showticketingservice.exception.pick.PickAlreadyExistsException;
import com.show.showticketingservice.model.responses.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class PickExceptionAdvice {

    @ExceptionHandler(PickAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> pickAlreadyExistsException(final PickAlreadyExistsException e, WebRequest request) {
        log.error("pick registration failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
