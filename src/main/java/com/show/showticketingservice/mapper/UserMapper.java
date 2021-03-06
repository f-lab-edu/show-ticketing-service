package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int insertUser(UserDTO userDTO);

    boolean isDuplicateUserId(String userId);

    String getUserPasswordById(String userId);

    int deleteUserByUserId(String userId);

}
