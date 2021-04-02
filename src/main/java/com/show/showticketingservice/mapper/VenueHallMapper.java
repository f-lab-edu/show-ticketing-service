package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import com.show.showticketingservice.model.venueHall.VenueHallResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VenueHallMapper {

    void insertVenueHalls(List<VenueHallRequest> venueHallRequests, int venueId);

    boolean isVenueHallExists(VenueHallRequest venueHallRequest, String venueId);

    void updateVenueHall(VenueHallRequest venueHallRequest, String venueId, String hallId);

    void deleteVenueHalls(String venueId, List<String> hallIds);

    List<VenueHallResponse> getVenueHalls(String venueId);

}
