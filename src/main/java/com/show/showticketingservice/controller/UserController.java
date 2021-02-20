package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.UserDTO;
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

    @PostMapping("/signup")
    public ResponseEntity<Void> addMember(@RequestBody @Valid UserDTO userDTO) {

        if (userService.isDuplicateUserId(userDTO.getId())) {
            return Responses.CONFLICT;
        }

        userService.insertUser(userDTO);
        return Responses.CREATED;
    }

}
