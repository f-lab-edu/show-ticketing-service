package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.venue.Venue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VenueMapper {

    void insertVenue(Venue venue);

    boolean isVenueExists(String venueName);

    void updateVenueInfo(@Param("venueId") int venueId, @Param("updateRequest") Venue venueUpdateRequest);

    void deleteVenue(int venueId);
}
