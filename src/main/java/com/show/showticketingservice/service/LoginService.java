package com.show.showticketingservice.service;

import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.model.user.UserResponse;

public interface LoginService {

    void login(UserLoginRequest userLoginRequest);

    boolean isLoginUser();

    UserResponse getLoginUser();

}
