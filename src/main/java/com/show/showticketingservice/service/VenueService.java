package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venue.VenueAlreadyExistsException;
import com.show.showticketingservice.mapper.VenueMapper;
import com.show.showticketingservice.model.venue.Venue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueMapper venueMapper;

    @Transactional
    public void insertVenue(Venue venue) {
        checkVenueExists(venue.getName());
        venueMapper.insertVenue(venue);
    }

    public void checkVenueExists(String venueName) {
        if (venueMapper.isVenueExists(venueName)) {
            throw new VenueAlreadyExistsException();
        }
    }

    public void updateVenueInfo(int venueId, Venue venueUpdateRequest) {
        venueMapper.updateVenueInfo(venueId, venueUpdateRequest);
    }
}
