package com.show.showticketingservice.repository;

import com.show.showticketingservice.mapper.UserMapper;
import com.show.showticketingservice.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    public int insertUser(UserDTO userDTO) {
        return userMapper.insertUser(userDTO);
    }

    public int selectUserId(String id) {
        return userMapper.selectUserId(id);
    }

    public int selectUserEmail(String email) {
        return userMapper.selectUserEmail(email);
    }

}
