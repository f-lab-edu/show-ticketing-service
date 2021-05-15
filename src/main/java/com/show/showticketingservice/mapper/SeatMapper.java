package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.performance.SeatRequest;
import com.show.showticketingservice.model.performance.SeatResponse;
import org.apache.ibatis.annotations.Mapper;
import java.util.*;

@Mapper
public interface SeatMapper {

    void insertSeatInfo(List<SeatRequest> seatRequests);

    List<SeatResponse> getPerfSeats(int perfTimeId);

    boolean isSeatExists(int perfTimeId);
}
