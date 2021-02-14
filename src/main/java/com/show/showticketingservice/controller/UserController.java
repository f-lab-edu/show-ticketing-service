package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.User;
import com.show.showticketingservice.service.UserService;
import com.show.showticketingservice.tool.HttpStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create_account")
    public ResponseEntity<Void> insertUser(@RequestBody User user) {

        if (userService.isIdDuplicated(user.getId()))
            return HttpStatusResponse.HTTP_STATUS_CONFLICT;

        userService.insertUser(user);

        return HttpStatusResponse.HTTP_STATUS_CREATED;
    }

    /*
    // id 중복 체크
    // insertUser()에 있는 ID 중복체크를
    // 회원가입 신청 전 추가적인 요청으로 분리 필요.
    @GetMapping("/check_id")
    public ResponseEntity<Void> checkIdDuplicated(@RequestParam String id) {
        if (userService.isIdDuplicated(id))
            return HttpStatusResponse.HTTP_STATUS_CONFLICT;

        return HttpStatusResponse.HTTP_STATUS_OK;
    }
    */

}
