package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venue.VenueAlreadyExistsException;
import com.show.showticketingservice.mapper.VenueMapper;
import com.show.showticketingservice.model.venue.Venue;
import com.show.showticketingservice.model.venue.VenueUpdateRequest;
import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueMapper venueMapper;

    private final VenueHallService venueHallService;

    @Transactional
    public void insertVenue(Venue venue, List<VenueHallRequest> venueHallRequests) {
        checkVenueExists(venue.getName());

        venueMapper.insertVenue(venue);

        venueHallService.insertVenueHalls(venueHallRequests, venue.getId());
    }

    public void checkVenueExists(String venueName) {
        if (venueMapper.isVenueExists(venueName)) {
            throw new VenueAlreadyExistsException();
        }
    }

    @Transactional
    public void updateVenueInfo(int venueId, VenueUpdateRequest venueUpdateRequest) {
        if(venueUpdateRequest.getVenue() != null) {
            checkVenueExists(venueUpdateRequest.getVenue().getName());
            venueMapper.updateVenueInfo(venueId, venueUpdateRequest.getVenue());
        }

        venueHallService.updateVenueHalls(venueUpdateRequest.getUpdateHallIds(), venueUpdateRequest.getVenueHallRequests(), venueId);

        venueHallService.deleteVenueHalls(venueId, venueUpdateRequest.getDeleteHallIds());
    }

    public void deleteVenue(int venueId) {
        venueMapper.deleteVenue(venueId);
    }

}
