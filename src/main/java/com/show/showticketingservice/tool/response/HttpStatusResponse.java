package com.show.showticketingservice.tool.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpStatusResponse {

    public static final ResponseEntity<Void> OK = new ResponseEntity<>(HttpStatus.OK);

    public static final ResponseEntity<Void> CREATED = new ResponseEntity<>(HttpStatus.CREATED);

    public static final ResponseEntity<Void> CONFLICT = new ResponseEntity<>(HttpStatus.CONFLICT);

}
