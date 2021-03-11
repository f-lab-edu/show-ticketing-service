package com.show.showticketingservice.model.user;

import com.show.showticketingservice.model.enumerations.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserResponse {

    private final Integer id;

    private final String userId;

    private final String password;

    private final String name;

    private final String phoneNum;

    private final String email;

    private final String address;

    private final UserType userType;

}
