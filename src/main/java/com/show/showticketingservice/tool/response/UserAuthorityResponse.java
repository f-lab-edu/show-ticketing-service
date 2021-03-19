package com.show.showticketingservice.tool.response;

import com.show.showticketingservice.model.enumerations.UserType;
import com.show.showticketingservice.model.responses.Authority;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserAuthorityResponse {

    public static final ResponseEntity<Authority> OK_GENERAL = new ResponseEntity<>(new Authority(UserType.GENERAL),HttpStatus.OK);

    public static final ResponseEntity<Authority> OK_MANAGER = new ResponseEntity<>(new Authority(UserType.MANAGER),HttpStatus.OK);

}
