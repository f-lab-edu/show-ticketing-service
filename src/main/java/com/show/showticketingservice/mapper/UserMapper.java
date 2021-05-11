package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.model.user.UserResponse;
import com.show.showticketingservice.model.user.UserUpdateRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    void insertUser(UserRequest userRequest);

    boolean isIdExists(String userId);

    UserResponse getUserByUserId(String userId);

    void updateUserInfo(@Param("id") int id, @Param("updateRequest") UserUpdateRequest userUpdateRequest);

    void deleteUserById(int id);

    String getUserPasswordById(int id);

}
