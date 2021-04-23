package com.show.showticketingservice.tool.advice;

import com.show.showticketingservice.exception.performance.*;
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
    public ResponseEntity<ExceptionResponse> sameSeatRating(final SameSeatRatingListAdditionException e, WebRequest request) {
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
    public ResponseEntity<ExceptionResponse> SeatPriceAlreadyExistsException(final SeatPriceAlreadyExistsException e, WebRequest request) {
        log.error("ticket price registration failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
