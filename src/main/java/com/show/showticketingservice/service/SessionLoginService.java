package com.show.showticketingservice.service;

import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.model.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService{

    private final HttpSession httpSession;

    private final UserService userService;

    @Override
    public void login(UserLoginRequest userLoginRequest) {

        UserResponse userResponse = userService.getUser(userLoginRequest.getUserId(), userLoginRequest.getPassword());

        httpSession.setAttribute("userId", userResponse);

    }

}
