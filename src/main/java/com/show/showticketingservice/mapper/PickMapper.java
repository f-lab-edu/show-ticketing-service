package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.enumerations.DatabaseServer;
import com.show.showticketingservice.tool.annotation.DatabaseSource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PickMapper {

    @DatabaseSource(DatabaseServer.MASTER)
    void insertPick(int userId, int performanceId);

    @DatabaseSource(DatabaseServer.SLAVE)
    boolean isPickExists(int userId, int performanceId);

    @DatabaseSource(DatabaseServer.MASTER)
    void deletePick(int userId, int performanceId);

}
