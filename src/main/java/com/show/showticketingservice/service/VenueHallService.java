package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venueHall.VenueHallAlreadyExistsException;
import com.show.showticketingservice.exception.venueHall.SameVenueHallAdditionException;
import com.show.showticketingservice.mapper.VenueHallMapper;
import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import com.show.showticketingservice.model.venueHall.VenueHallResponse;
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

    public void checkVenueHallExists(VenueHallRequest venueHallRequest, String venueId) {
        if(venueHallMapper.isVenueHallExists(venueHallRequest, venueId)) {
            throw new VenueHallAlreadyExistsException();
        }
    }

    @Transactional
    public void updateVenueHall(VenueHallRequest venueHallRequest, String venueId, String hallId) {

        checkVenueHallExists(venueHallRequest, venueId);

        venueHallMapper.updateVenueHall(venueHallRequest, venueId, hallId);
    }

    public void deleteVenueHalls(String venueId, List<String> hallIds) {

        venueHallMapper.deleteVenueHalls(venueId, hallIds);
    }

    public List<VenueHallResponse> getVenueHalls(String venueId) {
        return venueHallMapper.getVenueHalls(venueId);
    }

}
