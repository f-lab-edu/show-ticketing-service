package com.show.showticketingservice.tool.advice;

import com.show.showticketingservice.exception.venue.VenueAlreadyExistsException;
import com.show.showticketingservice.exception.venueHall.VenueHallAlreadyExistsException;
import com.show.showticketingservice.exception.venueHall.SameVenueHallAdditionException;
import com.show.showticketingservice.model.responses.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class VenueExceptionAdvice {

    @ExceptionHandler(VenueAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> venueAlreadyExistsException(final VenueAlreadyExistsException e, WebRequest request) {
        log.error("venue registration failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameVenueHallAdditionException.class)
    public ResponseEntity<ExceptionResponse> VenueHallDuplicationException(final SameVenueHallAdditionException e, WebRequest request) {
        log.error("venueHallName registration failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VenueHallAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> venueHallAlreadyExistsException(final VenueHallAlreadyExistsException e, WebRequest request) {
        log.error("venueHall registration failed", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
