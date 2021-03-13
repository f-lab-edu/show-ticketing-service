package com.show.showticketingservice.model.user;

import com.show.showticketingservice.model.enumerations.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSession {

    private final String userId;

    private final UserType userType;

}
