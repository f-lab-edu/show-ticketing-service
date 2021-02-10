package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    int insertUser(User user);

}

