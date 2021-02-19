package com.show.showticketingservice.service;

import com.show.showticketingservice.model.User;
import com.show.showticketingservice.repository.UserRepository;
import com.show.showticketingservice.tool.Encryptor.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final Encryptor passwordEncryptor;

    public boolean signUp(User user) {
        if (isIdDuplicated(user.getId()))
            return false;

        insertUser(user);
        return true;
    }

    public void insertUser(User user) {
        userRepository.insertUser(user.pwEncryptedUser(passwordEncryptor.encrypt(user.getPassword())));
    }

    public boolean isIdDuplicated(String id) {
        return userRepository.isIdDuplicated(id);
    }
}