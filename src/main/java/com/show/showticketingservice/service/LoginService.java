package com.show.showticketingservice.service;

import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.model.user.UserResponse;
import com.show.showticketingservice.model.user.UserSession;

public interface LoginService {

    void login(UserLoginRequest userLoginRequest);

    boolean isLoginUser();

    UserSession getLoginUser();

}
