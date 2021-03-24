package com.show.showticketingservice.model.user;

import com.show.showticketingservice.model.enumerations.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class UserSession implements Serializable {

    private final String userId;

    private final UserType userType;

}
