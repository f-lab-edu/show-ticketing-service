package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.venueHall.VenueHallColumnSeat;
import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import com.show.showticketingservice.model.venueHall.VenueHallResponse;
import com.show.showticketingservice.model.venueHall.VenueHallUpdateRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VenueHallMapper {

    void insertVenueHalls(List<VenueHallRequest> venueHallRequests, int venueId);

    boolean isVenueHallNamesExists(@Param("venueHallsUpdate") List<VenueHallUpdateRequest> venueHallUpdateRequests, int venueId);

    void updateVenueHalls(@Param("venueHallsUpdate") List<VenueHallUpdateRequest> venueHallUpdateRequests, int venueId);

    void deleteVenueHalls(int venueId, List<Integer> deleteHallIds);

    List<VenueHallResponse> getVenueHalls(int venueId);

    int getVenueHallCount(int venueId, List<Integer> hallIds);

    int getVenueHallRowNum(int performanceId);

    VenueHallColumnSeat getVenueHallColumnAndId(int performanceId);

}
