package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.venue.VenueRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VenueMapper {

    void insertVenue(VenueRequest venueRequest);

}
