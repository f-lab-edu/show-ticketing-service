package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.performance.PerfTimeAndRemainingSeatsResponse;
import com.show.showticketingservice.model.performance.PerformanceTimeRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PerformanceTimeMapper {

    void insertPerformanceTimes(List<PerformanceTimeRequest> performanceTimeRequests, int performanceId);

    List<PerformanceTimeRequest> getPerfTimes(int performanceId, List<PerformanceTimeRequest> performanceTimeRequests);

    void deletePerformanceTimes(int performanceId, List<Integer> timeIds);

    List<PerfTimeAndRemainingSeatsResponse> getPerfTimeAndRemainingSeats(int performanceId, int perfTimeId);

    boolean isPerfDateExists(int performanceId, int perfTimeId);

    boolean isPerfTimeIdExists(int perfTimeId);

}
