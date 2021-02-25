package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    int insertUser(UserDTO userDTO);

    int selectUserId(String userId);

    String selectUserPassword(String userId);

}
