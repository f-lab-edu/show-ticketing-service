package com.show.showticketingservice.service;

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

}
