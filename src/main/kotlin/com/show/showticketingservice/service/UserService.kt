package com.show.showticketingservice.service

import com.show.showticketingservice.exception.authentication.UserIdAlreadyExistsException
import com.show.showticketingservice.exception.authentication.UserIdNotExistsException
import com.show.showticketingservice.exception.authentication.UserPasswordWrongException
import com.show.showticketingservice.mapper.UserMapper
import com.show.showticketingservice.model.user.UserRequest
import com.show.showticketingservice.model.user.UserResponse
import com.show.showticketingservice.model.user.UserSession
import com.show.showticketingservice.model.user.UserUpdateRequest
import com.show.showticketingservice.tool.encryptor.PasswordEncryptor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(

        private val userMapper: UserMapper,
        private val passwordEncryptor: PasswordEncryptor

) {

    @Transactional
    fun signUp(userRequest: UserRequest) {
        checkIdExists(userRequest.userId)
        insertUser(userRequest)
    }

    private fun insertUser(userRequest: UserRequest) {
        userMapper.insertUser(userRequest.pwEncryptedUser(passwordEncryptor.encrypt(userRequest.password)))
    }

    @Transactional(readOnly = true)
    fun checkIdExists(userId: String) {
        if (userMapper.isIdExists(userId)) {
            throw UserIdAlreadyExistsException()
        }
    }

    fun getUser(userId: String, password: String): UserResponse {
        val userResponse = userMapper.getUserByUserId(userId) ?: throw UserIdNotExistsException()

        if (!isPasswordMatches(password, userResponse.password)) {
            throw UserPasswordWrongException()
        }

        return userResponse
    }

    private fun isPasswordMatches(passwordRequest: String, userPassword: String): Boolean {
        return passwordEncryptor.isMatched(passwordRequest, userPassword)
    }

    @Transactional
    fun deleteUser(userSession: UserSession, passwordRequest: String) {
        val hashPassword = userMapper.getUserPasswordById(userSession.userId)

        if (!isPasswordMatches(passwordRequest, hashPassword)) {
            throw UserPasswordWrongException()
        }

        userMapper.deleteUserById(userSession.userId)
    }

    @Transactional
    fun updateUserInfo(userSession: UserSession, userUpdateRequest: UserUpdateRequest) {
        val newEncryptedPassword = passwordEncryptor.encrypt(userUpdateRequest.newPassword)
        userMapper.updateUserInfo(userSession.userId, userUpdateRequest.pwEncryptedUserUpdateRequest(newEncryptedPassword))
    }

}