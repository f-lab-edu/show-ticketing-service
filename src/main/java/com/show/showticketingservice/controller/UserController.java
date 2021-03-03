package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.model.user.UserLoginRequest;
import com.show.showticketingservice.service.LoginService;
import com.show.showticketingservice.service.UserService;
import com.show.showticketingservice.tool.HttpStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/users")
    public ResponseEntity<Void> insertUser(@RequestBody @Valid UserRequest userRequest) {

        userService.signUp(userRequest);

        return HttpStatusResponse.CREATED;
    }

    @GetMapping("/user-exists/{userId}")
    public void checkIdDuplicated(@PathVariable String userId) {
        userService.checkIdExists(userId);
    }

}
