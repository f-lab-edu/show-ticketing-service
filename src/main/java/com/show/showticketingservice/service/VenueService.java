package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venue.VenueAlreadyExistsException;
import com.show.showticketingservice.exception.venueHall.SameVenueHallAdditionException;
import com.show.showticketingservice.exception.venueHall.VenueHallAlreadyExistsException;
import com.show.showticketingservice.mapper.VenueHallMapper;
import com.show.showticketingservice.mapper.VenueMapper;
import com.show.showticketingservice.model.venue.Venue;
import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void updateVenueInfo(int venueId, Venue venueUpdateRequest) {
        venueMapper.updateVenueInfo(venueId, venueUpdateRequest);
    }

    public void deleteVenue(int venueId) {
        venueMapper.deleteVenue(venueId);
    }

}
