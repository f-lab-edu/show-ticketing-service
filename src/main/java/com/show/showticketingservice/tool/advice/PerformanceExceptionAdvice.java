package com.show.showticketingservice.tool.advice;

import com.show.showticketingservice.exception.performance.PerformanceAlreadyExistsException;
import com.show.showticketingservice.exception.performance.SameSeatRatingListAdditionException;
import com.show.showticketingservice.exception.performance.PerformanceTimeConflictException;
import com.show.showticketingservice.exception.performance.*;
import com.show.showticketingservice.exception.performance.PerformanceNotExistsException;
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

    @ExceptionHandler(SeatRowNumWrongException.class)
    public ResponseEntity<ExceptionResponse> seatNumberWrongException(final SeatRowNumWrongException e, WebRequest request) {
        log.error("ticket price registration failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameSeatRatingListAdditionException.class)
    public ResponseEntity<ExceptionResponse> sameSeatRatingListAdditionException(final SameSeatRatingListAdditionException e, WebRequest request) {
        log.error("ticket price registration failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PerformanceTimeConflictException.class)
    public ResponseEntity<ExceptionResponse> performanceTimeConflictException(final PerformanceTimeConflictException e, WebRequest request) {
        log.error("performance schedule registration failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeatPriceAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> seatPriceAlreadyExistsException(final SeatPriceAlreadyExistsException e, WebRequest request) {
        log.error("ticket price registration failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeatPriceNotExistsException.class)
    public ResponseEntity<ExceptionResponse> seatPriceNonExistsException(final SeatPriceNotExistsException e, WebRequest request) {
        log.error("seat registration failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PerformanceNotExistsException.class)
    public ResponseEntity<ExceptionResponse> performanceNotExistsException(final PerformanceNotExistsException e, WebRequest request) {
        log.error("The performance information does not exist", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoKeywordException.class)
    public ResponseEntity<ExceptionResponse> noKeywordException(final NoKeywordException e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PerformanceTimeNotExistsException.class)
    public ResponseEntity<ExceptionResponse> performanceTimeNotExistsException(final PerformanceTimeNotExistsException e, WebRequest request) {
        log.error("The performance time does not exist", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
