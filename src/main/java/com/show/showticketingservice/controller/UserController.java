package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.LoginDTO;
import com.show.showticketingservice.model.UserDTO;
import com.show.showticketingservice.service.loginService.LoginService;
import com.show.showticketingservice.service.UserService;
import com.show.showticketingservice.utils.Responses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<Void> addUser(@RequestBody @Valid UserDTO userDTO) {

        userService.duplicateUserId(userDTO.getUserId());

        userService.insertUser(userDTO);

        return Responses.CREATED;
    }

    @PostMapping("/login")
    public void loginUser(@RequestBody LoginDTO loginDTO) {

        loginService.login(loginDTO);
    }

    @GetMapping("/logout")
    public void logoutUser() {

        loginService.logout();
    }

}
