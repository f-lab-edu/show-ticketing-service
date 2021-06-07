package com.show.showticketingservice.tool.advice;

import com.show.showticketingservice.exception.reservation.ReserveAllowedQuantityExceededException;
import com.show.showticketingservice.exception.reservation.SeatsNotReservableException;
import com.show.showticketingservice.model.responses.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class ReservationExceptionAdvice {

    @ExceptionHandler(ReserveAllowedQuantityExceededException.class)
    public ResponseEntity<ExceptionResponse> ReserveAllowedQuantityExceededException(final ReserveAllowedQuantityExceededException e, WebRequest request) {
        log.error("reservation failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeatsNotReservableException.class)
    public ResponseEntity<ExceptionResponse> seatsNotReservableException(final SeatsNotReservableException e, WebRequest request) {
        log.error("reservation failed - request reservation with invalid seats", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
