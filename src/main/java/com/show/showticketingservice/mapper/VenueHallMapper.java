package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.venueHall.VenueHall;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VenueHallMapper {

    void insertVenueHall(List<VenueHall> venueHalls, String venueId);

    boolean isVenueHallExists(List<VenueHall> venueHalls, String venueId);

}
