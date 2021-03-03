package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.authentication.UserIdAlreadyExistsException;
import com.show.showticketingservice.exception.authentication.UserIdNotExistsException;
import com.show.showticketingservice.exception.authentication.UserPasswordWrongException;
import com.show.showticketingservice.mapper.UserMapper;
import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.model.user.UserResponse;
import com.show.showticketingservice.tool.encryptor.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final Encryptor bcryptPasswordEncryptor;

    public void signUp(UserRequest userRequest) {
        checkIdExists(userRequest.getUserId());

        insertUser(userRequest);
    }

    public void insertUser(UserRequest userRequest) {
        userMapper.insertUser(userRequest.pwEncryptedUser(bcryptPasswordEncryptor.encrypt(userRequest.getPassword())));
    }

    public void checkIdExists(String userId) {
        if(userMapper.isIdExists(userId) > 0) {
            throw new UserIdAlreadyExistsException();
        }
    }

    public UserResponse getUser(String userIdRequest, String passwordRequest) {
        UserResponse userResponse = userMapper.getUserByUserId(userIdRequest);

        if (userResponse == null) {
            throw new UserIdNotExistsException();
        }

        if (!isPasswordMatches(passwordRequest, userResponse.getPassword())) {
            throw new UserPasswordWrongException();
        }

        return userResponse;
    }

    private boolean isPasswordMatches(String passwordRequest, String userPassword) {
        return bcryptPasswordEncryptor.isMatched(passwordRequest, userPassword);
    }
}