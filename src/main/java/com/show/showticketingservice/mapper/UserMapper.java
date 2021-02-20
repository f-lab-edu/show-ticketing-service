package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int insertUser(UserDTO userDTO);

    int selectUserId(String id);

}
