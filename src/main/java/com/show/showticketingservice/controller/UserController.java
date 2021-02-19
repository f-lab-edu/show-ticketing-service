package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.User;
import com.show.showticketingservice.service.UserService;
import com.show.showticketingservice.tool.HttpStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> insertUser(@RequestBody @Valid User user) {
        if (userService.signUp(user))
            return HttpStatusResponse.HTTP_STATUS_CREATED;

        return HttpStatusResponse.HTTP_STATUS_CONFLICT;
    }

    @GetMapping("/idCheck")
    public ResponseEntity<Void> checkIdDuplicated(@RequestParam String id) {
        if (userService.isIdDuplicated(id))
            return HttpStatusResponse.HTTP_STATUS_CONFLICT;

        return HttpStatusResponse.HTTP_STATUS_OK;
    }

}
