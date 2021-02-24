package com.show.showticketingservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Responses {

    public static final ResponseEntity<Void> OK = new ResponseEntity<>(HttpStatus.OK);

    public static final ResponseEntity<String> LOGIN_OK = new ResponseEntity<>("로그인 성공", HttpStatus.OK);

    public static final ResponseEntity<Void> CREATED = new ResponseEntity<>(HttpStatus.CREATED);

    public static final ResponseEntity<Void> BAD_REQUEST = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    public static final ResponseEntity<Void> UNAUTHORIZED = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    public static final ResponseEntity<Void> CONFLICT = new ResponseEntity<>(HttpStatus.CONFLICT);

    public static ResponseEntity<String> makeLoginFailBadRequest(String message) {
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}
