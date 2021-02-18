package com.show.showticketingservice.service;

import com.show.showticketingservice.model.UserDTO;
import com.show.showticketingservice.repository.UserRepository;
import com.show.showticketingservice.utils.hashPassword.PasswordBcryptImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordBcryptImple passwordBcryptImple;

    public int insertUser(UserDTO userDTO) {

        String hashPassword = passwordBcryptImple.encode(userDTO.getPassword());

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

    public boolean isDuplicateUserEmail(String email) {
        int result = userRepository.selectUserEmail(email);

        return result != 0;
    }

    public boolean isCheckDuplication(UserDTO userDTO) {
        return isDuplicateUserId(userDTO.getId()) || isDuplicateUserEmail(userDTO.getEmail());
    }

}
