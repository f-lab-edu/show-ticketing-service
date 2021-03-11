package com.show.showticketingservice.tool.aop;

import com.show.showticketingservice.model.user.UserResponse;
import com.show.showticketingservice.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
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

        UserResponse userResponse = loginService.getLoginUser();

        Object[] args = Arrays.stream(joinPoint.getArgs()).map(data -> {

            if(data instanceof UserResponse) {
                data = userResponse;
            }

            return data;
        }).toArray();

        return joinPoint.proceed(args);

    }
}
