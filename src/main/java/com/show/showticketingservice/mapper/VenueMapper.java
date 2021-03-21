package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.venue.Venue;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VenueMapper {

    void insertVenue(Venue venue);

    boolean isVenueExists(String venueName);
}
