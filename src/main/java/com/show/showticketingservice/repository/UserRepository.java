package com.show.showticketingservice.repository;

import com.show.showticketingservice.mapper.UserMapper;
import com.show.showticketingservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private UserMapper userMapper;

    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    public boolean isIdDuplicated(String id) {
        return userMapper.idDuplicated(id) > 0;
    }
}
