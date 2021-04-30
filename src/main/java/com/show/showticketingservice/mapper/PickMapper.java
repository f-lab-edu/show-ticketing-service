package com.show.showticketingservice.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PickMapper {

    void insertPick(int userId, int performanceId);

    boolean isPickExists(int userId, int performanceId);

    void deletePick(int userId, int performanceId);
}
