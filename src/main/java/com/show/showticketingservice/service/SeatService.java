package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.SeatNotExistsException;
import com.show.showticketingservice.mapper.SeatMapper;
import com.show.showticketingservice.model.performance.SeatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatMapper seatMapper;

    public List<SeatResponse> getPerfSeats(int perfTimeId) {
        checkPerfSeatExists(perfTimeId);
        return seatMapper.getPerfSeats(perfTimeId);
    }

    public void checkPerfSeatExists(int perfTimeId) {
        if(!seatMapper.isSeatExists(perfTimeId)) {
            throw new SeatNotExistsException();
        }
    }

}
