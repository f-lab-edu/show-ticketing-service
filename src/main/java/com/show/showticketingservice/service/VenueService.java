package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venue.VenueAlreadyExistsException;
import com.show.showticketingservice.mapper.VenueMapper;
import com.show.showticketingservice.model.criteria.VenuePagingCriteria;
import com.show.showticketingservice.model.venue.VenueDetailInfoResponse;
import com.show.showticketingservice.model.venue.VenueListResponse;
import com.show.showticketingservice.model.venue.VenueRequest;
import com.show.showticketingservice.model.venue.VenueResponse;
import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import com.show.showticketingservice.model.venueHall.VenueHallResponse;
import com.show.showticketingservice.tool.constants.CacheConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueMapper venueMapper;

    private final VenueHallService venueHallService;

    @Transactional
    public void insertVenue(VenueRequest venueRequest, List<VenueHallRequest> venueHallRequests) {
        checkVenueExists(venueRequest.getName());

        venueMapper.insertVenue(venueRequest);

        venueHallService.insertVenueHalls(venueHallRequests, venueRequest.getId());
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

    public VenueListResponse getVenueList(int page) {

        int venueTotalCount = venueMapper.getVenueTotalCount();

        int venueTotalPage = 1;

        List<VenueResponse> venueResponseList = Collections.emptyList();

        if (venueTotalCount > 0) {
            VenuePagingCriteria pagingCriteria = new VenuePagingCriteria(page);

            int mod = venueTotalCount % pagingCriteria.getAmount();

            venueTotalPage = venueTotalCount / pagingCriteria.getAmount() + (mod != 0 ? 1 : 0);

            if (page <= 0 || venueTotalPage < page)
                pagingCriteria.setPage(1);

            venueResponseList = venueMapper.getVenueList(pagingCriteria);
        }

        return new VenueListResponse(venueTotalPage, venueResponseList);
    }

    @Cacheable(cacheNames = CacheConstant.VENUE, key = "#venueId")
    public VenueDetailInfoResponse getVenueInfo(int venueId) {

        VenueResponse venueResponse = venueMapper.getVenueInfo(venueId);

        List<VenueHallResponse> venueHallResponses = venueHallService.getVenueHalls(venueId);

        return new VenueDetailInfoResponse(venueResponse, venueHallResponses);
    }

}
