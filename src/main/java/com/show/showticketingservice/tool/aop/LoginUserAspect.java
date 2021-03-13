package com.show.showticketingservice.tool.aop;

import com.show.showticketingservice.model.user.UserResponse;
import com.show.showticketingservice.model.user.UserSession;
import com.show.showticketingservice.service.LoginService;
import com.show.showticketingservice.tool.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/*
  session으로 부터 로그인 된 유저의 정보를 불러올 일이 중복될 가능성이 크므로 aop를 설정.
  session을 담당하는 관심사는 SessionLoginService이기 때문에 이쪽에서 session정보를 불러 옴.
 */

@Component
@Aspect
@RequiredArgsConstructor
public class LoginUserAspect {

    private final LoginService loginService;

    @Around("execution(* *(.., @com.show.showticketingservice.tool.annotation.LoginUser (*), ..))")
    public Object convertLoginUser(ProceedingJoinPoint joinPoint) throws Throwable {

        UserSession userSession = loginService.getLoginUser();

        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Annotation[][] annotations = method.getParameterAnnotations();

        for(int i = 0; i < annotations.length; i++) {
            for(Annotation annotation : annotations[i]) {
                if(annotation instanceof LoginUser) {
                    args[i] = userSession;
                    break;
                }
            }
        }

        return joinPoint.proceed(args);

    }
}
