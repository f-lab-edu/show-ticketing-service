package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.service.LoginService;
import com.show.showticketingservice.service.UserService;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final LoginService loginService;

    @PostMapping("/login")
    public void login(@RequestBody UserLoginRequest userLoginRequest) {
        loginService.login(userLoginRequest);
    }

    @GetMapping("/logout")
    @UserAuthenticationNecessary
    public void logout() {
        loginService.logout();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertUser(@RequestBody @Valid UserRequest userRequest) {
        userService.signUp(userRequest);
    }

    @GetMapping("/user-exists/{userId}")
    public void checkIdDuplicated(@PathVariable String userId) {
        userService.checkIdExists(userId);
    }

}
