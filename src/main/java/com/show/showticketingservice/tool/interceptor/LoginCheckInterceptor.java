package com.show.showticketingservice.tool.interceptor;

import com.show.showticketingservice.exception.authentication.UserNotLoggedInException;
import com.show.showticketingservice.service.LoginService;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Getter
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        UserAuthenticationNecessary userAuthentication = handlerMethod.getMethodAnnotation(UserAuthenticationNecessary.class);
        if (userAuthentication != null && !loginService.isUserLoggedIn()) {
            throw new UserNotLoggedInException();
        }

        return true;
    }
}
