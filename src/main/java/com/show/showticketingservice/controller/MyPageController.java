package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.user.UserSession;
import com.show.showticketingservice.model.user.UserUpdateRequest;
import com.show.showticketingservice.service.UserService;
import com.show.showticketingservice.tool.annotation.CurrentUser;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-infos")
public class MyPageController {

    private final UserService userService;

    @PostMapping("/unregister")
    @UserAuthenticationNecessary
    public void deleteUser(@CurrentUser UserSession userSession, @RequestParam String passwordRequest) {
        userService.deleteUser(userSession, passwordRequest);
    }

    @PutMapping
    @UserAuthenticationNecessary
    public void updateUserInfo(@CurrentUser UserSession userSession, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        userService.updateUserInfo(userSession, userUpdateRequest);
    }

}
