package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.reservation.ReservationNumNotExistsException;
import com.show.showticketingservice.exception.reservation.ReserveAllowedQuantityExceededException;
import com.show.showticketingservice.exception.reservation.SeatsNotReservableException;
import com.show.showticketingservice.mapper.ReservationMapper;
import com.show.showticketingservice.model.reservation.ReservationInfoToCancelRequest;
import com.show.showticketingservice.model.reservation.ReservationRequest;
import com.show.showticketingservice.model.user.UserSession;
import com.show.showticketingservice.tool.constants.CacheConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    private final int maxReservationQuantity;

    private final ReservationMapper reservationMapper;

    private final PerformanceService performanceService;

    private final SeatService seatService;

    public ReservationService(@Value("${service.value.max-reservation-quantity}") int quantity,
                              ReservationMapper reservationMapper,
                              PerformanceService performanceService,
                              SeatService seatService) {
        this.maxReservationQuantity = quantity;
        this.reservationMapper = reservationMapper;
        this.performanceService = performanceService;
        this.seatService = seatService;
    }

    @Transactional
    public void reserveSeats(int userId, ReservationRequest reservationRequest) {
        if (reservationRequest.getSeatIds().size() > maxReservationQuantity)
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

    @Caching(evict = {
            @CacheEvict(cacheNames = CacheConstant.PERFORMANCE_SEAT_LIST, key = "#reservationInfoToCancelRequest.perfTimeId"),
            @CacheEvict(cacheNames = CacheConstant.PERFORMANCE_REMAINING_SEAT_NUM, key = "#reservationInfoToCancelRequest.performanceId + #reservationInfoToCancelRequest.perfTimeId")
    })
    @Transactional
    public void cancelSeats(int userId, ReservationInfoToCancelRequest reservationInfoToCancelRequest) {

        checkReservationIdExists(userId, reservationInfoToCancelRequest);

        seatService.setSeatsCancel(reservationInfoToCancelRequest.getReservationIds());

        reservationMapper.cancelSeats(reservationInfoToCancelRequest.getReservationIds());
    }

    private void checkReservationIdExists(int userId, ReservationInfoToCancelRequest reservationInfoToCancelRequest ) {
        if(reservationInfoToCancelRequest.getReservationIds().size() != reservationMapper.getSeatNumToCancel(userId, reservationInfoToCancelRequest)) {
            throw new ReservationNumNotExistsException("예매하신 예약 id가 존재하지 않거나 공연 정보와 관련된 예약 id 존재하지 않습니다.");
        }
    }
}
