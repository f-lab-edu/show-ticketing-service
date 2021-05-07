package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.enumerations.DatabaseServer;
import com.show.showticketingservice.model.performance.PerformanceTimeRequest;
import com.show.showticketingservice.tool.annotation.DatabaseSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PerformanceTimeMapper {

    @DatabaseSource(DatabaseServer.MASTER)
    void insertPerformanceTimes(List<PerformanceTimeRequest> performanceTimeRequests, int performanceId);

    @DatabaseSource(DatabaseServer.SLAVE)
    List<PerformanceTimeRequest> getPerfTimes(int performanceId, List<PerformanceTimeRequest> performanceTimeRequests);

    @DatabaseSource(DatabaseServer.MASTER)
    void deletePerformanceTimes(int performanceId, List<Integer> timeIds);

}
