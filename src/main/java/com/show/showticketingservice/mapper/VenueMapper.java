package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.criteria.VenuePagingCriteria;
import com.show.showticketingservice.model.venue.VenueRequest;
import com.show.showticketingservice.model.venue.VenueResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VenueMapper {

    void insertVenue(VenueRequest venueRequest);

    boolean isVenueExists(String venueName);

    boolean isVenueIdExists(int venueId);

    void updateVenueInfo(@Param("venueId") int venueId, @Param("updateRequest") VenueRequest venueUpdateRequest);

    void deleteVenue(int venueId);

    List<VenueResponse> getVenueList(VenuePagingCriteria venuePagingCriteria);

    VenueResponse getVenueInfo(int venueId);

    int getVenueTotalCount();

    boolean isDuplicateVenueName(String venueName, int venueId);

}
