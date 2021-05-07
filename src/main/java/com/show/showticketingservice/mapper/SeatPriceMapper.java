package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.enumerations.DatabaseServer;
import com.show.showticketingservice.model.performance.SeatPriceRequest;
import com.show.showticketingservice.model.performance.SeatPriceRowNumData;
import com.show.showticketingservice.tool.annotation.DatabaseSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeatPriceMapper {

    @DatabaseSource(DatabaseServer.MASTER)
    void insertSeatsPrice(List<SeatPriceRequest> seatPriceRequests, int performanceId);

    @DatabaseSource(DatabaseServer.SLAVE)
    List<SeatPriceRowNumData> getSeatPriceRowNum(int performanceId);

    @DatabaseSource(DatabaseServer.SLAVE)
    boolean isSeatPriceExists(int performanceId);

}
