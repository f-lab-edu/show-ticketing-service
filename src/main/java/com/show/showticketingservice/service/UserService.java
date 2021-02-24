package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.NotUserException;
import com.show.showticketingservice.model.LoginDTO;
import com.show.showticketingservice.model.UserDTO;
import com.show.showticketingservice.repository.UserRepository;
import com.show.showticketingservice.utils.encoder.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public int insertUser(UserDTO userDTO) {

        String hashPassword = passwordEncoder.encode(userDTO.getPassword());

        UserDTO hashUserDTO = UserDTO.builder()
                .id(userDTO.getId())
                .password(hashPassword)
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .phoneNum(userDTO.getPhoneNum())
                .address(userDTO.getAddress())
                .build();

        return userRepository.insertUser(hashUserDTO);
    }

    public boolean isDuplicateUserId(String id) {
        int result = userRepository.selectUserId(id);

        return result != 0;
    }

    public String checkUser(LoginDTO loginDTO) throws NotUserException {
        String hashPassword = userRepository.selectUserPassword(loginDTO.getId());

        if(hashPassword == null || !passwordEncoder.isMatch(loginDTO.getPassword(), hashPassword)) {
            throw new NotUserException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        return loginDTO.getId();
    }

}
