package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.performance.SeatPriceRequest;
import com.show.showticketingservice.model.performance.SeatPriceRowNumData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeatPriceMapper {

    void insertSeatsPrice(List<SeatPriceRequest> seatPriceRequests, int performanceId);

    List<SeatPriceRowNumData> getSeatPriceRowNum(int performanceId);

    boolean isSeatPriceExists(int performanceId);

}
