package com.show.showticketingservice.service;

import com.show.showticketingservice.model.user.UserLoginRequest;

public interface LoginService {

    void login(UserLoginRequest userLoginRequest);

    void logout();

}
