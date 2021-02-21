package com.show.showticketingservice.service;

import com.show.showticketingservice.model.LoginDTO;
import com.show.showticketingservice.repository.UserRepository;
import com.show.showticketingservice.utils.encoder.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final HttpSession httpSession;

    public boolean isCheckLoginUserId(LoginDTO loginDTO) {
        String hashPassword = userRepository.selectLoginUserId(loginDTO.getId());

        if(hashPassword == null || !passwordEncoder.isMatch(loginDTO.getPassword(), hashPassword)) {
            return false;
        }

        httpSession.setAttribute("loginId", loginDTO.getId());

        return true;
    }
}
