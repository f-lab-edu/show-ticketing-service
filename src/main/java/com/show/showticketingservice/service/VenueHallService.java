package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venueHall.VenueHallAlreadyExistsException;
import com.show.showticketingservice.exception.venueHall.SameVenueHallAdditionException;
import com.show.showticketingservice.mapper.VenueHallMapper;
import com.show.showticketingservice.model.venueHall.VenueHall;
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
    public void insertVenueHall(List<VenueHall> venueHalls, String venueId) {

        checkDuplicationVenueHallName(venueHalls);

        checkVenueHallExists(venueHalls, venueId);

        venueHallMapper.insertVenueHall(venueHalls, venueId);
    }

    public void checkDuplicationVenueHallName(List<VenueHall> venueHalls) {
        Map<String, Integer> hallNameMap = new HashMap<>();

        for(VenueHall venueHall : venueHalls) {
            if(hallNameMap.containsKey(venueHall.getName())) {
                throw new SameVenueHallAdditionException();
            }

            hallNameMap.put(venueHall.getName(), 1);
        }
    }

    public void checkVenueHallExists(List<VenueHall> venueHalls, String venueId) {
        if(venueHallMapper.isVenueHallExists(venueHalls, venueId)) {
            throw new VenueHallAlreadyExistsException();
        }
    }
}
