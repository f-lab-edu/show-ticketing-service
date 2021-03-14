package com.show.showticketingservice.service;

import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.model.user.UserResponse;
import com.show.showticketingservice.model.user.UserSession;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.show.showticketingservice.tool.constants.UserConstant.USER;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final HttpSession httpSession;

    private final UserService userService;

    @Override
    public void login(UserLoginRequest userLoginRequest) {

        if (httpSession.getAttribute(USER) != null) {
            throw new DuplicateRequestException("이미 로그인 된 상태입니다.");
        }

        UserResponse userResponse = userService.getUser(userLoginRequest.getUserId(), userLoginRequest.getPassword());

        UserSession userSession = new UserSession(userResponse.getUserId(), userResponse.getUserType());

        httpSession.setAttribute(USER, userSession);
    }

    @Override
    public void logout() {
        httpSession.invalidate();
    }

    @Override
    public boolean isUserLoggedIn() {
        return httpSession.getAttribute(USER) != null;
    }

    @Override
    public UserSession getCurrentUserSession() {
        return (UserSession) httpSession.getAttribute(USER);
    }

}
