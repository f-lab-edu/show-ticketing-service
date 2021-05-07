package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.enumerations.DatabaseServer;
import com.show.showticketingservice.model.user.UserRequest;
import com.show.showticketingservice.model.user.UserResponse;
import com.show.showticketingservice.model.user.UserUpdateRequest;
import com.show.showticketingservice.tool.annotation.DatabaseSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    @DatabaseSource(DatabaseServer.MASTER)
    void insertUser(UserRequest userRequest);

    @DatabaseSource(DatabaseServer.SLAVE)
    boolean isIdExists(String userId);

    @DatabaseSource(DatabaseServer.SLAVE)
    UserResponse getUserByUserId(String userId);

    @DatabaseSource(DatabaseServer.MASTER)
    void updateUserInfo(@Param("id") int id, @Param("updateRequest") UserUpdateRequest userUpdateRequest);

    @DatabaseSource(DatabaseServer.MASTER)
    void deleteUserById(int id);

    @DatabaseSource(DatabaseServer.SLAVE)
    String getUserPasswordById(int id);

}
