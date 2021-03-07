package com.show.showticketingservice.service.loginService;

import com.show.showticketingservice.model.LoginDTO;

public interface LoginService {

    void login(LoginDTO loginDTO);

    void logout();

    String getLoginUserId();

    void existLoginUserId();

}
