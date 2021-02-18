package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.UserDTO;
import com.show.showticketingservice.service.UserService;
import com.show.showticketingservice.utils.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/memberships")
    public ResponseEntity<Void> addMember(@RequestBody @Valid UserDTO userDTO) {

        if (userService.isCheckDuplication(userDTO)) {
            return Responses.CONFLICT;
        }

        userService.insertUser(userDTO);
        return Responses.CREATED;
    }

}
