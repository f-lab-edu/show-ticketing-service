package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venueHall.VenueHallAlreadyExistsException;
import com.show.showticketingservice.exception.venueHall.SameVenueHallAdditionException;
import com.show.showticketingservice.exception.venueHall.VenueHallIdNotExistsException;
import com.show.showticketingservice.mapper.VenueHallMapper;
import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import com.show.showticketingservice.model.venueHall.VenueHallResponse;
import com.show.showticketingservice.model.venueHall.VenueHallUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VenueHallService {

    private final VenueHallMapper venueHallMapper;

    public void insertVenueHalls(List<VenueHallRequest> venueHallRequests, int venueId) {

        checkDuplicationVenueHallRequestNames(venueHallRequests);

        venueHallMapper.insertVenueHalls(venueHallRequests, venueId);
    }

    public void checkDuplicationVenueHallRequestNames(List<VenueHallRequest> venueHallRequests) {
        Map<String, Integer> hallNameMap = new HashMap<>();

        for(VenueHallRequest venueHallRequest : venueHallRequests) {
            if(hallNameMap.containsKey(venueHallRequest.getName())) {
                throw new SameVenueHallAdditionException();
            }

            hallNameMap.put(venueHallRequest.getName(), 1);
        }
    }

    public void checkDuplicationVenueHallUpdateRequestNames(List<VenueHallUpdateRequest> venueHallUpdateRequests) {
        Map<String, Integer> hallNameMap = new HashMap<>();

        for(VenueHallUpdateRequest venueHallUpdateRequest : venueHallUpdateRequests) {
            if(hallNameMap.containsKey(venueHallUpdateRequest.getName())) {
                throw new SameVenueHallAdditionException();
            }

            hallNameMap.put(venueHallUpdateRequest.getName(), 1);
        }
    }

    public void checkVenueHallNamesExists(List<VenueHallUpdateRequest> venueHallUpdateRequests, int venueId) {
        if(venueHallMapper.isVenueHallNamesExists(venueHallUpdateRequests, venueId)) {
            throw new VenueHallAlreadyExistsException();
        }
    }

    @Transactional
    public void updateVenueHalls(int venueId, List<VenueHallUpdateRequest> venueHallUpdateRequests) {

        List<Integer> hallIds = getVenueHallUpdateRequestId(venueHallUpdateRequests);

        checkVenueHallIdsExists(venueId, hallIds);

        checkDuplicationVenueHallUpdateRequestNames(venueHallUpdateRequests);

        checkVenueHallNamesExists(venueHallUpdateRequests, venueId);

        venueHallMapper.updateVenueHalls(venueHallUpdateRequests, venueId);
    }

    public List<Integer> getVenueHallUpdateRequestId(List<VenueHallUpdateRequest> venueHallUpdateRequests) {
        List<Integer> hallIds = new ArrayList<>();

        for(VenueHallUpdateRequest venueHallUpdateRequest : venueHallUpdateRequests) {
            hallIds.add(venueHallUpdateRequest.getId());
        }

        return hallIds;
    }

    @Transactional
    public void deleteVenueHalls(int venueId, List<Integer> deleteHallIds) {

        checkVenueHallIdsExists(venueId, deleteHallIds);

        venueHallMapper.deleteVenueHalls(venueId, deleteHallIds);
    }

    public void checkVenueHallIdsExists(int venueId, List<Integer> hallIds) {
        if(venueHallMapper.getVenueHallCount(venueId, hallIds) != hallIds.size()) {
            throw new VenueHallIdNotExistsException();
        }
    }

    public List<VenueHallResponse> getVenueHalls(int venueId) {
        return venueHallMapper.getVenueHalls(venueId);
    }

}
