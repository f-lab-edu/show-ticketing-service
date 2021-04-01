package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venue.VenueAlreadyExistsException;
import com.show.showticketingservice.mapper.VenueMapper;
import com.show.showticketingservice.model.venue.VenueRequest;
import com.show.showticketingservice.model.venue.VenueResponse;
import com.show.showticketingservice.tool.constants.CacheConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueMapper venueMapper;

    @Transactional
    public void insertVenue(VenueRequest venueRequest) {
        checkVenueExists(venueRequest.getName());
        venueMapper.insertVenue(venueRequest);
    }

    public void checkVenueExists(String venueName) {
        if (venueMapper.isVenueExists(venueName)) {
            throw new VenueAlreadyExistsException();
        }
    }

    @CacheEvict(cacheNames = CacheConstant.VENUE, key = "#venueId")
    public void updateVenueInfo(int venueId, VenueRequest venueUpdateRequest) {
        venueMapper.updateVenueInfo(venueId, venueUpdateRequest);
    }

    @CacheEvict(cacheNames = CacheConstant.VENUE, key = "#venueId")
    public void deleteVenue(int venueId) {
        venueMapper.deleteVenue(venueId);
    }

    public List<VenueResponse> getAllVenues() {
        return venueMapper.getAllVenues();
    }

    @Cacheable(cacheNames = CacheConstant.VENUE, key = "#venueId")
    public VenueResponse getVenueInfo(int venueId) {
        return venueMapper.getVenueInfo(venueId);
    }
}
