package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.venueHall.VenueHall;
import com.show.showticketingservice.model.venueHall.VenueHallResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VenueHallMapper {

    void insertVenueHall(List<VenueHall> venueHalls, String venueId);

    boolean isVenueHallsExists(List<VenueHall> venueHalls, String venueId);

    boolean isVenueHallExists(VenueHall venueHall, String venueId);

    void updateVenueHall(VenueHall venueHall, String venueId, String hallId);

    void deleteVenueHall(String venueId, List<String> hallIds);

    List<VenueHallResponse> getVenueHalls(String venueId);

}
