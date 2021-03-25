package com.show.showticketingservice.tool.interceptor;

import com.show.showticketingservice.exception.authentication.UserHaveNoAuthorityException;
import com.show.showticketingservice.exception.authentication.UserNotLoggedInException;
import com.show.showticketingservice.model.enumerations.AccessRoles;
import com.show.showticketingservice.model.enumerations.UserType;
import com.show.showticketingservice.model.user.UserSession;
import com.show.showticketingservice.service.LoginService;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.show.showticketingservice.tool.constants.UserConstant.USER;

@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        UserAuthenticationNecessary userAuthentication = handlerMethod.getMethodAnnotation(UserAuthenticationNecessary.class);

        if (userAuthentication != null) {
            loginCheck();

            UserSession userSession = (UserSession) request.getSession().getAttribute(USER);

            roleCheck(userAuthentication, userSession);
        }

        return true;
    }

    private void loginCheck() {
        if (!loginService.isUserLoggedIn())
            throw new UserNotLoggedInException();
    }

    private void roleCheck(UserAuthenticationNecessary userAuthentication, UserSession userSession) {
        if ((userAuthentication.role() == AccessRoles.GENERAL && userSession.getUserType() != UserType.GENERAL) ||
                (userAuthentication.role() == AccessRoles.MANAGER && userSession.getUserType() != UserType.MANAGER))
            throw new UserHaveNoAuthorityException();
    }
}
