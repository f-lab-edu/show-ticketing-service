package com.show.showticketingservice.service.loginService;

import com.show.showticketingservice.exception.NotUserException;
import com.show.showticketingservice.model.LoginDTO;
import com.show.showticketingservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.show.showticketingservice.utils.constant.UserConstant.LOGIN_ID;

@Service
@AllArgsConstructor
public class LoginServiceSession implements LoginService{

    private final UserService userService;

    private final HttpSession httpSession;

    public void login(LoginDTO loginDTO) throws NotUserException {

        httpSession.setAttribute(LOGIN_ID, userService.checkUser(loginDTO));

    }
}
