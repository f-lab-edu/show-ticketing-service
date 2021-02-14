package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    void insertUser(User user);

    int idDuplicated(String id);
}

