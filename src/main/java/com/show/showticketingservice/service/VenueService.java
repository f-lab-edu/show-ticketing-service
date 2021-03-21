package com.show.showticketingservice.service;

import com.show.showticketingservice.mapper.VenueMapper;
import com.show.showticketingservice.model.venue.VenueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueMapper venueMapper;

    public void insertVenue(VenueRequest venueRequest) {
        venueMapper.insertVenue(venueRequest);
    }
}
