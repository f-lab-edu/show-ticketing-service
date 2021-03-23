package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venueHall.VenueHallAlreadyExistsException;
import com.show.showticketingservice.exception.venueHall.VenueHallDuplicationException;
import com.show.showticketingservice.mapper.VenueHallMapper;
import com.show.showticketingservice.model.venueHall.VenueHall;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VenueHallService {

    private final VenueHallMapper venueHallMapper;

    public void insertVenueHall(List<VenueHall> venueHalls, String venueId) {

        checkDuplicationVenueHallName(venueHalls);

        checkVenueHallExists(venueHalls, venueId);

        venueHallMapper.insertVenueHall(venueHalls, venueId);
    }

    public void checkDuplicationVenueHallName(List<VenueHall> venueHalls) {
        Map<String, Integer> hashMap = new HashMap<>();

        for(VenueHall venueHall : venueHalls) {
            if(hashMap.containsKey(venueHall.getName())) {
                throw new VenueHallDuplicationException();
            }

            hashMap.put(venueHall.getName(), 1);
        }
    }

    public void checkVenueHallExists(List<VenueHall> venueHalls, String venueId) {
        if(venueHallMapper.isVenueHallExists(venueHalls, venueId)) {
            throw new VenueHallAlreadyExistsException();
        }
    }
}
