package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.authentication.UserAlreadyLoggedInException;
import com.show.showticketingservice.model.enumerations.UserType;
import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.model.user.UserResponse;
import com.show.showticketingservice.model.user.UserSession;
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
    public UserType login(UserLoginRequest userLoginRequest) {

        if (httpSession.getAttribute(USER) != null) {
            throw new UserAlreadyLoggedInException();
        }

        UserResponse userResponse = userService.getUser(userLoginRequest.getUserId(), userLoginRequest.getPassword());

        UserSession userSession = new UserSession(userResponse.getId(), userResponse.getUserType());

        httpSession.setAttribute(USER, userSession);

        return userResponse.getUserType();
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
