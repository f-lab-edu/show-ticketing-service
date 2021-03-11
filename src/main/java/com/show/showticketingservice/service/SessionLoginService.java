package com.show.showticketingservice.service;

import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.model.user.UserResponse;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.show.showticketingservice.tool.constants.UserConstant.USER;

@Slf4j
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

        httpSession.setAttribute(USER, userResponse);

        log.info("Login Success - userId: '" + userResponse.getUserId() + "', userType: '" + userResponse.getUserType() + "'");
    }

    @Override
    public boolean isLoginUser() {

        return httpSession.getAttribute(USER) != null;
    }

    @Override
    public UserResponse getLoginUser() {
        return (UserResponse) httpSession.getAttribute(USER);
    }

}
