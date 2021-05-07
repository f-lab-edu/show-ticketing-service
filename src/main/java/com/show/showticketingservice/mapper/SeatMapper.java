package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.enumerations.DatabaseServer;
import com.show.showticketingservice.model.performance.SeatRequest;
import com.show.showticketingservice.tool.annotation.DatabaseSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeatMapper {

    @DatabaseSource(DatabaseServer.MASTER)
    void insertSeatInfo(List<SeatRequest> seatRequests);

}
