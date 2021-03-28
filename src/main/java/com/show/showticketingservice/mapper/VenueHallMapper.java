package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VenueHallMapper {

    void insertVenueHalls(List<VenueHallRequest> venueHallRequests, String venueId);

    boolean isVenueHallsExists(List<VenueHallRequest> venueHallRequests, String venueId);
}
