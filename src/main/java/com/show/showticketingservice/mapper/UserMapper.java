package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.user.UserUpdateRequest;
import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.model.user.UserResponse;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    void insertUser(UserRequest userRequest);

    boolean isIdExists(String userId);

    UserResponse getUserByUserId(String userId);

    void updateUserInfo(@Param("userId") String userId, @Param("updateRequest") UserUpdateRequest userUpdateRequest);

    void deleteUserByUserId(String userId);

    String getUserPasswordByUserId(String UserId);

    int getUserNum(String userId);

}
