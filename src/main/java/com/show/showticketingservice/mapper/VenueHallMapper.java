package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.enumerations.DatabaseServer;
import com.show.showticketingservice.model.venueHall.VenueHallColumnSeat;
import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import com.show.showticketingservice.model.venueHall.VenueHallResponse;
import com.show.showticketingservice.model.venueHall.VenueHallUpdateRequest;
import com.show.showticketingservice.tool.annotation.DatabaseSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VenueHallMapper {

    @DatabaseSource(DatabaseServer.MASTER)
    void insertVenueHalls(List<VenueHallRequest> venueHallRequests, int venueId);

    @DatabaseSource(DatabaseServer.SLAVE)
    boolean isVenueHallNamesExists(@Param("venueHallsUpdate") List<VenueHallUpdateRequest> venueHallUpdateRequests, int venueId);

    @DatabaseSource(DatabaseServer.MASTER)
    void updateVenueHalls(@Param("venueHallsUpdate") List<VenueHallUpdateRequest> venueHallUpdateRequests, int venueId);

    @DatabaseSource(DatabaseServer.MASTER)
    void deleteVenueHalls(int venueId, List<Integer> deleteHallIds);

    @DatabaseSource(DatabaseServer.SLAVE)
    List<VenueHallResponse> getVenueHalls(int venueId);

    @DatabaseSource(DatabaseServer.SLAVE)
    int getVenueHallCount(int venueId, List<Integer> hallIds);

    @DatabaseSource(DatabaseServer.SLAVE)
    int getVenueHallRowNum(int performanceId);

    @DatabaseSource(DatabaseServer.SLAVE)
    VenueHallColumnSeat getVenueHallColumnAndId(int performanceId);

}
