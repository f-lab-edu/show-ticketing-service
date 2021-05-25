package com.show.showticketingservice.service;

import com.show.showticketingservice.mapper.SeatMapper;
import com.show.showticketingservice.model.seat.SeatAndPriceResponse;
import com.show.showticketingservice.tool.constants.CacheConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatMapper seatMapper;

    private final PerformanceService performanceService;

    @Cacheable(cacheNames = CacheConstant.PERFORMANCE_SEAT_LIST, key = "#perfTimeId")
    public List<SeatAndPriceResponse> getPerfSeatsAndPrices(int perfTimeId) {
        performanceService.checkPerfTimeIdExists(perfTimeId);
        return seatMapper.getPerfSeatsAndPrices(perfTimeId);
    }

    public void setSeatsReserved(List<Integer> seatIds) {
        seatMapper.setSeatsReserved(seatIds);
    }
}
