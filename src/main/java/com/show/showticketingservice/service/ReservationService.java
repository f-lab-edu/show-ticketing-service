package com.show.showticketingservice.service;

import com.show.showticketingservice.mapper.ReservationMapper;
import com.show.showticketingservice.model.reservation.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;

    private final PerformanceService performanceService;

    private final SeatService seatService;

    @Transactional
    public void reserveSeats(int userId, ReservationRequest reservationRequest) {
        performanceService.checkPerfTimeIdExists(reservationRequest.getPerfTimeId());
        seatService.setSeatsReserved(reservationRequest.getSeatIds());
        reservationMapper.reserveSeats(userId, reservationRequest.getSeatIds());
    }

}
