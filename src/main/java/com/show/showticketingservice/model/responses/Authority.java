package com.show.showticketingservice.model.responses;

import com.show.showticketingservice.model.enumerations.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Authority {

    private final UserType userType;

}
