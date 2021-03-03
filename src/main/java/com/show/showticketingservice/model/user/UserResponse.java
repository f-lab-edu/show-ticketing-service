package com.show.showticketingservice.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private final int id;

    private final String userId;

    private final String password;

    private final String name;

    private final String phoneNum;

    private final String email;

    private final String address;

    private final int userType;

}
