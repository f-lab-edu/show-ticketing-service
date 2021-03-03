package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.model.user.UserResponse;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    void insertUser(UserRequest userRequest);

    int isIdExists(String id);

    UserResponse getUserByUserId(String userId);
}

