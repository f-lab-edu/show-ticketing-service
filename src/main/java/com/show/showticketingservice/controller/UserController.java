package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.user.User;
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
    public ResponseEntity<Void> login(@RequestBody UserLoginRequest loginRequest) {

        loginService.login(loginRequest);

        return HttpStatusResponse.HTTP_STATUS_OK;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> insertUser(@RequestBody @Valid User user) {
        if (userService.signUp(user))
            return HttpStatusResponse.HTTP_STATUS_CREATED;

        return HttpStatusResponse.HTTP_STATUS_CONFLICT;
    }

    @GetMapping("/user-exists/{userId}")
    public ResponseEntity<Void> checkIdDuplicated(@PathVariable String userId) {
        if (userService.isIdExists(userId))
            return HttpStatusResponse.HTTP_STATUS_CONFLICT;

        return HttpStatusResponse.HTTP_STATUS_OK;
    }

}
