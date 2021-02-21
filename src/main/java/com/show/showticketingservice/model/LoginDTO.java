package com.show.showticketingservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginDTO {

    private final String id;

    private final String password;
}
