package com.show.showticketingservice.controller;

import com.show.showticketingservice.service.UserService;
import com.show.showticketingservice.service.loginService.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-pages")
public class MypageController {

    private final UserService userService;

    private final LoginService loginService;

    @PostMapping("/unregister")
    public void deleteUser(@RequestParam String password) {

        String userId = loginService.getLoginUserId();

        userService.deleteUser(password, userId);

        loginService.logout();
    }

}
