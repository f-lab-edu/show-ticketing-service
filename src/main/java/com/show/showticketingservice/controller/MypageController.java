package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.user.UserSession;
import com.show.showticketingservice.service.UserService;
import com.show.showticketingservice.tool.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myInfo")
public class MypageController {

    private final UserService userService;

    @PostMapping("/unregister")
    public void deleteUser(@LoginUser UserSession userSession, @RequestParam String passwordRequest) {

        userService.deleteUser(userSession, passwordRequest);
    }

}
