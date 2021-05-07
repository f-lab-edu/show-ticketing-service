package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.criteria.VenuePagingCriteria;
import com.show.showticketingservice.model.enumerations.DatabaseServer;
import com.show.showticketingservice.model.venue.VenueRequest;
import com.show.showticketingservice.model.venue.VenueResponse;
import com.show.showticketingservice.tool.annotation.DatabaseSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VenueMapper {

    @DatabaseSource(DatabaseServer.MASTER)
    void insertVenue(VenueRequest venueRequest);

    @DatabaseSource(DatabaseServer.SLAVE)
    boolean isVenueExists(String venueName);

    @DatabaseSource(DatabaseServer.SLAVE)
    boolean isVenueIdExists(int venueId);

    @DatabaseSource(DatabaseServer.MASTER)
    void updateVenueInfo(@Param("venueId") int venueId, @Param("updateRequest") VenueRequest venueUpdateRequest);

    @DatabaseSource(DatabaseServer.MASTER)
    void deleteVenue(int venueId);

    @DatabaseSource(DatabaseServer.SLAVE)
    List<VenueResponse> getVenueList(VenuePagingCriteria venuePagingCriteria);

    @DatabaseSource(DatabaseServer.SLAVE)
    VenueResponse getVenueInfo(int venueId);

    @DatabaseSource(DatabaseServer.SLAVE)
    int getVenueTotalCount();

    @DatabaseSource(DatabaseServer.SLAVE)
    boolean isDuplicateVenueName(String venueName, int venueId);

}
