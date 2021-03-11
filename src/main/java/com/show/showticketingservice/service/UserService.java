package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.authentication.UserIdAlreadyExistsException;
import com.show.showticketingservice.exception.authentication.UserIdNotExistsException;
import com.show.showticketingservice.exception.authentication.UserPasswordWrongException;
import com.show.showticketingservice.mapper.UserMapper;
import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.model.user.UserResponse;
import com.show.showticketingservice.tool.encryptor.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final PasswordEncryptor passwordEncryptor;

    public void signUp(UserRequest userRequest) {
        checkIdExists(userRequest.getUserId());

        insertUser(userRequest);

        log.info("sign up success");
    }

    public void insertUser(UserRequest userRequest) {
        userMapper.insertUser(userRequest.pwEncryptedUser(passwordEncryptor.encrypt(userRequest.getPassword())));
    }

    public void checkIdExists(String userId) {
        if(userMapper.isIdExists(userId)) {
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
        return passwordEncryptor.isMatched(passwordRequest, userPassword);
    }

    public void deleteUser(UserResponse userResponse, String passwordRequest) {

        if(!isPasswordMatches(passwordRequest, userResponse.getPassword())) {
            throw new UserPasswordWrongException();
        }

        userMapper.deleteUserById(userResponse.getUserId());
    }
}
