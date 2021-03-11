package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.user.UserResponse;
import com.show.showticketingservice.service.UserService;
import com.show.showticketingservice.tool.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-pages")
public class MypageController {

    private final UserService userService;

    @PostMapping("/unregister")
    public void deleteUser(@LoginUser UserResponse userResponse, @RequestParam String passwordRequest) {

        userService.deleteUser(userResponse, passwordRequest);
    }

}
