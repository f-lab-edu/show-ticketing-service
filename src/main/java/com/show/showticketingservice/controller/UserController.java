package com.show.showticketingservice.controller;

import com.show.showticketingservice.exception.NotUserException;
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
    public ResponseEntity<Void> addMember(@RequestBody @Valid UserDTO userDTO) {

        if(userService.isDuplicateUserId(userDTO.getId())) {
            return Responses.CONFLICT;
        }

        userService.insertUser(userDTO);
        return Responses.CREATED;
    }

    /**
     *  로그인 실패시 서비스부터 여기 컨트롤러까지 회피해서 bad_request로 보내려고 하는데 복구는 아니지만 서버에서 처리할 수 있는 최대한의 복구라고
     *  생각합니다. 더 좋은 방법이 있을까요? 가독성이 떨어지는거 같기도 해서요.
     *
     */
    @PostMapping("/login")
    public ResponseEntity<String> checkLogin(@RequestBody LoginDTO loginDTO) {

        try {
            loginService.login(loginDTO);
        } catch(NotUserException e) {
            return Responses.makeLoginFailBadRequest(e.getMessage());
        }

        return Responses.LOGIN_OK;
    }

}
