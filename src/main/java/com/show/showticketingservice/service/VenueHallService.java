package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venueHall.VenueHallAlreadyExistsException;
import com.show.showticketingservice.exception.venueHall.SameVenueHallAdditionException;
import com.show.showticketingservice.mapper.VenueHallMapper;
import com.show.showticketingservice.model.venueHall.VenueHallRequest;
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

    @Transactional
    public void insertVenueHalls(List<VenueHallRequest> venueHallRequests, String venueId) {

        checkDuplicationVenueHallName(venueHallRequests);

        checkVenueHallsExists(venueHallRequests, venueId);

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

    public void checkVenueHallsExists(List<VenueHallRequest> venueHallRequests, String venueId) {
        if(venueHallMapper.isVenueHallsExists(venueHallRequests, venueId)) {
            throw new VenueHallAlreadyExistsException();
        }
    }
}
