package com.show.showticketingservice.repository;

import com.show.showticketingservice.mapper.UserMapper;
import com.show.showticketingservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

}
