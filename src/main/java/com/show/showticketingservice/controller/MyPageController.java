package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.user.UserSession;
import com.show.showticketingservice.service.UserService;
import com.show.showticketingservice.tool.annotation.CurrentUser;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

}
