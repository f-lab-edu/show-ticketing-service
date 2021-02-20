package com.show.showticketingservice.repository;

import com.show.showticketingservice.mapper.UserMapper;
import com.show.showticketingservice.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public int insertUser(UserDTO userDTO) {
        return userMapper.insertUser(userDTO);
    }

    public int selectUserId(String id) {
        return userMapper.selectUserId(id);
    }

}
