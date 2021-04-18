package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.performance.PerformanceTimeRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PerformanceTimeMapper {

    void insertPerformanceTimes(List<PerformanceTimeRequest> performanceTimeRequests, int performanceId);

    List<PerformanceTimeRequest> getPerfTimes(int performanceId);

}
