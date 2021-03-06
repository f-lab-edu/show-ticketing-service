package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.authentication.UserIdAlreadyExistsException;
import com.show.showticketingservice.exception.authentication.UserIdNotExistsException;
import com.show.showticketingservice.exception.authentication.UserPasswordWrongException;
import com.show.showticketingservice.mapper.UserMapper;
import com.show.showticketingservice.model.user.UserUpdateRequest;
import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.model.user.UserResponse;
import com.show.showticketingservice.model.user.UserSession;
import com.show.showticketingservice.tool.encryptor.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final PasswordEncryptor passwordEncryptor;

    @Transactional
    public void signUp(UserRequest userRequest) {
        checkIdExists(userRequest.getUserId());

        insertUser(userRequest);
    }

    public void insertUser(UserRequest userRequest) {
        userMapper.insertUser(userRequest.pwEncryptedUser(passwordEncryptor.encrypt(userRequest.getPassword())));
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public void deleteUser(UserSession userSession, String passwordRequest) {

        String hashPassword = userMapper.getUserPasswordById(userSession.getUserId());

        if(!isPasswordMatches(passwordRequest, hashPassword)) {
            throw new UserPasswordWrongException();
        }

        userMapper.deleteUserById(userSession.getUserId());
    }

    @Transactional
    public void updateUserInfo(UserSession userSession, UserUpdateRequest userUpdateRequest) {
        String newEncryptedPassword = passwordEncryptor.encrypt(userUpdateRequest.getNewPassword());
        userMapper.updateUserInfo(userSession.getUserId(), userUpdateRequest.pwEncryptedUserUpdateRequest(newEncryptedPassword));
    }

}
