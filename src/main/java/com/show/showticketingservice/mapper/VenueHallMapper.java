package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import com.show.showticketingservice.model.venueHall.VenueHallResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VenueHallMapper {

    void insertVenueHalls(List<VenueHallRequest> venueHallRequests, int venueId);

    boolean isVenueHallsExists(@Param("venueHallsUpdate") List<VenueHallRequest> venueHallRequests, int venueId);

    void updateVenueHalls(List<Integer> updateHallIds, @Param("venueHallsUpdate") List<VenueHallRequest> venueHallRequests, int venueId);

    void deleteVenueHalls(int venueId, List<Integer> deleteHallIds);

    List<VenueHallResponse> getVenueHalls(String venueId);

}
