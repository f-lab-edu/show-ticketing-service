package com.show.showticketingservice.service;

import com.show.showticketingservice.model.enumerations.UserType;
import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.model.user.UserSession;

public interface LoginService {

    UserType login(UserLoginRequest userLoginRequest);

    void logout();

    boolean isUserLoggedIn();

    UserSession getCurrentUserSession();
}
