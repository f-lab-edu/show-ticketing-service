package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.authentication.UserIdNotExistsException;
import com.show.showticketingservice.exception.authentication.UserPasswordWrongException;
import com.show.showticketingservice.mapper.UserMapper;
import com.show.showticketingservice.model.user.User;
import com.show.showticketingservice.tool.encryptor.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final Encryptor passwordEncryptor;

    public boolean signUp(User user) {
        if (isIdExists(user.getId()))
            return false;

        insertUser(user);
        return true;
    }

    public void insertUser(User user) {
        userMapper.insertUser(user.pwEncryptedUser(passwordEncryptor.encrypt(user.getPassword())));
    }

    public boolean isIdExists(String id) {
        return userMapper.isIdExists(id) > 0;
    }

    public User getUser(String userIdRequest, String passwordRequest) {
        if (!isIdExists(userIdRequest)) {
            throw new UserIdNotExistsException();
        }

        User user = userMapper.getUserByUserId(userIdRequest);

        if (!isPasswordMatches(passwordRequest, user.getPassword())) {
            throw new UserPasswordWrongException();
        }

        return user;
    }

    private boolean isPasswordMatches(String passwordRequest, String userPassword) {
        return passwordEncryptor.isMatched(passwordRequest, userPassword);
    }
}