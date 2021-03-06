package com.show.showticketingservice.controller;

import com.show.showticketingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypages")
public class MypageController {

    private final UserService userService;

    @PostMapping("/unregister")
    public void deleteUser(@RequestParam String password) {

        userService.deleteUser(password);
    }

}
