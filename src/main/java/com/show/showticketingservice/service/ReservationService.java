package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.reservation.ReserveAllowedQuantityExceededException;
import com.show.showticketingservice.mapper.ReservationMapper;
import com.show.showticketingservice.model.reservation.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final static int MAX_RESERVATION_QUANTITY = 4;

    private final ReservationMapper reservationMapper;

    private final PerformanceService performanceService;

    private final SeatService seatService;

    @Transactional
    public void reserveSeats(int userId, ReservationRequest reservationRequest) {
        if (reservationRequest.getSeatIds().size() > MAX_RESERVATION_QUANTITY)
            throw new ReserveAllowedQuantityExceededException("최대 예매 가능 좌석 수는 4자리 입니다.");

        performanceService.checkPerfTimeIdExists(reservationRequest.getPerfTimeId());
        seatService.setSeatsReserved(reservationRequest.getSeatIds());
        reservationMapper.reserveSeats(userId, reservationRequest.getSeatIds());
    }

}
