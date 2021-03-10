package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.authentication.UserNotLoggedInException;
import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.model.user.UserResponse;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.show.showticketingservice.tool.constants.UserConstant.USER_ID;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final HttpSession httpSession;

    private final UserService userService;

    @Override
    public void login(UserLoginRequest userLoginRequest) {

        if (httpSession.getAttribute(USER_ID) != null) {
            throw new DuplicateRequestException("이미 로그인 된 상태입니다.");
        }

        UserResponse userResponse = userService.getUser(userLoginRequest.getUserId(), userLoginRequest.getPassword());

        httpSession.setAttribute(USER_ID, userResponse.getUserId());
    }

    @Override
    public void logout() {

        if(httpSession.getAttribute(USER_ID) == null) {
            throw new UserNotLoggedInException();
        }

        httpSession.invalidate();
    }

}
