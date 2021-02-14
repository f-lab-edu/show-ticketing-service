package com.show.showticketingservice.service;

import com.show.showticketingservice.model.User;
import com.show.showticketingservice.repository.UserRepository;
import com.show.showticketingservice.tool.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    public void insertUser(User user) {
        userRepository.insertUser(user.pwEncryptedUser(passwordEncryptor.encrypt(user.getPassword())));
    }

    public boolean isIdDuplicated(String id) {
        return userRepository.isIdDuplicated(id);
    }

}
