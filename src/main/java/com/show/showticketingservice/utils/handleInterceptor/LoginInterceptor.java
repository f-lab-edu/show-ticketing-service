package com.show.showticketingservice.utils.handleInterceptor;

import com.show.showticketingservice.exception.UnAuthorityUserException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static com.show.showticketingservice.utils.constant.UserConstant.LOGIN_ID;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    public List loginEssential = Arrays.asList("/mypages/**");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getSession().getAttribute(LOGIN_ID) == null) {
            throw new UnAuthorityUserException("로그인을 해야 접속이 가능합니다.");
        }

        return true;
    }
}
