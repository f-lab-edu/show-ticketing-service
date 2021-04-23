package com.show.showticketingservice.tool.advice;

import com.show.showticketingservice.exception.performance.PerformanceAlreadyExistsException;
import com.show.showticketingservice.exception.performance.PerformanceNotExistsException;
import com.show.showticketingservice.exception.performance.PerformanceTimeConflictException;
import com.show.showticketingservice.model.responses.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class PerformanceExceptionAdvice {

    @ExceptionHandler(PerformanceAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> performanceAlreadyExistsException(final PerformanceAlreadyExistsException e, WebRequest request) {
        log.error("performance registration failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PerformanceTimeConflictException.class)
    public ResponseEntity<ExceptionResponse> performanceTimeConflictException(final PerformanceTimeConflictException e, WebRequest request) {
        log.error("performance schedule registration failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PerformanceNotExistsException.class)
    public ResponseEntity<ExceptionResponse> performanceNotExistsException(final PerformanceNotExistsException e, WebRequest request) {
        log.error("The performance information does not exist", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
