package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venueHall.VenueHallAlreadyExistsException;
import com.show.showticketingservice.exception.venueHall.SameVenueHallAdditionException;
import com.show.showticketingservice.mapper.VenueHallMapper;
import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import com.show.showticketingservice.model.venueHall.VenueHallResponse;
import com.show.showticketingservice.model.venueHall.VenueHallUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VenueHallService {

    private final VenueHallMapper venueHallMapper;

    public void insertVenueHalls(List<VenueHallRequest> venueHallRequests, int venueId) {

        checkDuplicationVenueHallName(venueHallRequests);

        venueHallMapper.insertVenueHalls(venueHallRequests, venueId);
    }

    public void checkDuplicationVenueHallName(List<VenueHallRequest> venueHallRequests) {
        Map<String, Integer> hallNameMap = new HashMap<>();

        for(VenueHallRequest venueHallRequest : venueHallRequests) {
            if(hallNameMap.containsKey(venueHallRequest.getName())) {
                throw new SameVenueHallAdditionException();
            }

            hallNameMap.put(venueHallRequest.getName(), 1);
        }
    }

    public void checkVenueHallsExists(List<VenueHallUpdateRequest> venueHallUpdateRequests, int venueId) {
        if(venueHallMapper.isVenueHallsExists(venueHallUpdateRequests, venueId)) {
            throw new VenueHallAlreadyExistsException();
        }
    }

    @Transactional
    public void  updateVenueHalls(List<VenueHallUpdateRequest> venueHallUpdateRequests, int venueId) {

        if(!venueHallUpdateRequests.isEmpty()) {
            checkVenueHallsExists(venueHallUpdateRequests, venueId);
            venueHallMapper.updateVenueHalls(venueHallUpdateRequests, venueId);
        }
    }

    public void deleteVenueHalls(int venueId, List<Integer> deleteHallIds) {

        if(!deleteHallIds.isEmpty()) venueHallMapper.deleteVenueHalls(venueId, deleteHallIds);
    }

    public List<VenueHallResponse> getVenueHalls(String venueId) {
        return venueHallMapper.getVenueHalls(venueId);
    }

}
