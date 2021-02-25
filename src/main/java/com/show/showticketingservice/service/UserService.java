package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.NotUserException;
import com.show.showticketingservice.mapper.UserMapper;
import com.show.showticketingservice.model.UserDTO;
import com.show.showticketingservice.utils.encoder.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public int insertUser(UserDTO userDTO) {

        String hashPassword = passwordEncoder.encode(userDTO.getPassword());

        UserDTO hashUserDTO = UserDTO.builder()
                .userId(userDTO.getUserId())
                .password(hashPassword)
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .phoneNum(userDTO.getPhoneNum())
                .address(userDTO.getAddress())
                .build();

        return userMapper.insertUser(hashUserDTO);
    }

    public boolean isDuplicateUserId(String userId) {
        int result = userMapper.selectUserId(userId);

        return result != 0;
    }

    public String getUserId(String userId, String password) {
        String hashPassword = userMapper.selectUserPassword(userId);

        if(hashPassword == null || !passwordEncoder.isMatch(password, hashPassword)) {
            throw new NotUserException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        return userId;
    }

}
