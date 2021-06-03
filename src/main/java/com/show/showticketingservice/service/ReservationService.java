package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.reservation.ReserveAllowedQuantityExceededException;
import com.show.showticketingservice.exception.reservation.SeatsNotReservableException;
import com.show.showticketingservice.mapper.ReservationMapper;
import com.show.showticketingservice.model.reservation.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private static int MAX_RESERVATION_QUANTITY;

    private final ReservationMapper reservationMapper;

    private final PerformanceService performanceService;

    private final SeatService seatService;

    @Value("${service.value.max-reservation-quantity}")
    public void setMaxReservationQuantity(int quantity) {
        ReservationService.MAX_RESERVATION_QUANTITY = quantity;
    }

    @Transactional
    public void reserveSeats(int userId, ReservationRequest reservationRequest) {
        if (reservationRequest.getSeatIds().size() > MAX_RESERVATION_QUANTITY)
            throw new ReserveAllowedQuantityExceededException("최대 예매 가능 좌석 수는 4자리 입니다.");

        performanceService.checkPerfTimeIdExists(reservationRequest.getPerfTimeId());
        checkSeatsReservable(reservationRequest.getPerfTimeId(), reservationRequest.getSeatIds());

        seatService.setSeatsReserved(reservationRequest.getSeatIds());
        reservationMapper.reserveSeats(userId, reservationRequest.getSeatIds());
    }

    private void checkSeatsReservable(int perfTimeId, List<Integer> seatIds) {
        if (seatIds.size() != seatService.getReservableSeatsNum(perfTimeId, seatIds))
            throw new SeatsNotReservableException("이미 예매되었거나 다른 스케줄 또는 유효하지 않은 좌석이 존재합니다.");
    }

}
