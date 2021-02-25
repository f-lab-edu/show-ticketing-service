package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.user.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    void insertUser(User user);

    int isIdExists(String id);

    User getUserByUserId(String userId);
}

