package com.show.showticketingservice.service;

import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.model.user.UserSession;

public interface LoginService {

    void login(UserLoginRequest userLoginRequest);

    void logout();

    boolean isUserLoggedIn();

    UserSession getCurrentUserSession();
}
