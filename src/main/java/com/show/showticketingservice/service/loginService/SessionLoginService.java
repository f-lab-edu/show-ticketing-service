package com.show.showticketingservice.service.loginService;

import com.show.showticketingservice.exception.UnAuthorityUserException;
import com.show.showticketingservice.model.LoginDTO;
import com.show.showticketingservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static com.show.showticketingservice.utils.constant.UserConstant.LOGIN_ID;

@Service
@AllArgsConstructor
public class SessionLoginService implements LoginService {

    private final UserService userService;

    private final HttpSession httpSession;

    @Override
    public void login(LoginDTO loginDTO) {

        httpSession.setAttribute(LOGIN_ID, userService.getUserId(loginDTO.getUserId(), loginDTO.getPassword()));
    }

    @Override
    public void logout() {

        httpSession.invalidate();
    }

    @Override
    public String getLoginUserId() {
        return (String)httpSession.getAttribute(LOGIN_ID);
    }

    @Override
    public void existLoginUserId() {

        if(httpSession.getAttribute(LOGIN_ID) == null) {

            throw new UnAuthorityUserException("로그인을 해야 접속이 가능합니다.");
        }
    }


}
