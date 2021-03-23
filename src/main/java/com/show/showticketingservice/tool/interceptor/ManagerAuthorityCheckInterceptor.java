package com.show.showticketingservice.tool.interceptor;

import com.show.showticketingservice.exception.authentication.UserHaveNoAuthorityException;
import com.show.showticketingservice.model.enumerations.UserType;
import com.show.showticketingservice.model.user.UserSession;
import com.show.showticketingservice.tool.annotation.ManagerAuthority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.show.showticketingservice.tool.constants.UserConstant.USER;

@Component
@RequiredArgsConstructor
public class ManagerAuthorityCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        ManagerAuthority managerAuthority = handlerMethod.getMethodAnnotation(ManagerAuthority.class);

        UserSession userSession = (UserSession) request.getSession().getAttribute(USER);

        if (managerAuthority != null && userSession.getUserType() != UserType.MANAGER) {
            throw new UserHaveNoAuthorityException();
        }

        return true;
    }
}
