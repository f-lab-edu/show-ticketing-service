package com.show.showticketingservice.tool;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpStatusResponse {

    public static final ResponseEntity<Void> HTTP_STATUS_OK = new ResponseEntity<>(HttpStatus.OK);
    public static final ResponseEntity<Void> HTTP_STATUS_CREATED = new ResponseEntity<>(HttpStatus.CREATED);
    public static final ResponseEntity<Void> HTTP_STATUS_CONFLICT = new ResponseEntity<>(HttpStatus.CONFLICT);

}
