package com.show.showticketingservice.tool.interceptor;

import com.show.showticketingservice.exception.authentication.UserNotLoggedInException;
import com.show.showticketingservice.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/*
 로그인을 한 사용자만 입장이 가능하도록 설정
 */

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final LoginService loginService;

    public List loginEssential = Arrays.asList("/my-pages/**");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!loginService.isLoginUser()) {
            throw new UserNotLoggedInException();
        }

        return true;

    }
}
