package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.performance.SeatRequest;
import com.show.showticketingservice.model.seat.SeatAndPriceResponse;
import org.apache.ibatis.annotations.Mapper;
import java.util.*;

@Mapper
public interface SeatMapper {

    void insertSeatInfo(List<SeatRequest> seatRequests);

    List<SeatAndPriceResponse> getPerfSeatsAndPrices(int perfTimeId);

}
