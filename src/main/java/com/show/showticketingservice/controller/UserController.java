package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.User;
import com.show.showticketingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/new")
    public String insertUser(@RequestBody User user) {
        int cnt = userService.insertUser(user);

        // return "redirect:/";
        return cnt + " 회원가입 완료!";
    }

}
