package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.venueHall.VenueHallAlreadyExistsException;
import com.show.showticketingservice.exception.venueHall.SameVenueHallAdditionException;
import com.show.showticketingservice.mapper.VenueHallMapper;
import com.show.showticketingservice.model.venueHall.VenueHall;
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

    @Transactional
    public void insertVenueHall(List<VenueHall> venueHalls, String venueId) {

        checkDuplicationVenueHallName(venueHalls);

        checkVenueHallsExists(venueHalls, venueId);

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

    public void checkVenueHallsExists(List<VenueHall> venueHalls, String venueId) {
        if(venueHallMapper.isVenueHallsExists(venueHalls, venueId)) {
            throw new VenueHallAlreadyExistsException();
        }
    }

    public void checkVenueHallExists(VenueHall venueHall, String venueId) {
        if(venueHallMapper.isVenueHallExists(venueHall, venueId)) {
            throw new VenueHallAlreadyExistsException();
        }
    }

    @Transactional
    public void updateVenueHall(VenueHall venueHall, String venueId, String hallId) {

        checkVenueHallExists(venueHall, venueId);

        venueHallMapper.updateVenueHall(venueHall, venueId, hallId);
    }

    public void deleteVenueHall(String venueId, List<String> hallIds) {

        venueHallMapper.deleteVenueHall(venueId, hallIds);
    }

    public List<VenueHallResponse> getVenueHalls(String venueId) {
        return venueHallMapper.getVenueHalls(venueId);
    }

}
