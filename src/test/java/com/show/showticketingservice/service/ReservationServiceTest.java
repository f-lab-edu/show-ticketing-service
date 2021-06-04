package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.PerformanceTimeNotExistsException;
import com.show.showticketingservice.exception.reservation.ReserveAllowedQuantityExceededException;
import com.show.showticketingservice.exception.reservation.SeatsNotReservableException;
import com.show.showticketingservice.mapper.ReservationMapper;
import com.show.showticketingservice.model.reservation.ReservationRequest;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    private int max_reservation_quantity = 4;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private PerformanceService performanceService;

    @Mock
    private SeatService seatService;

    private ReservationService reservationService;

    @BeforeEach
    public void init() {
        reservationService = new ReservationService(max_reservation_quantity, reservationMapper, performanceService,seatService);
    }

    @Nested
    @DisplayName("[좌석 예매 테스트]")
    class MakeReservationTest {

        int userId = 1;

        int perfTimeId = 1;

        private List<Integer> seatNums = new ArrayList<>();

        @AfterEach
        public void clearSeatNums() {
            seatNums.clear();
        }

        @Test
        @DisplayName("[예매 요청 좌석 수 검사 1] 최대 예매 가능 좌석 수(4개) 이내로 예매 요청 할 경우 예매 성공 (예매 요청 좌석 수: 2) ")
        public void multipleSeatsReservation() {
            insertSeatNums(2);
            ReservationRequest request = new ReservationRequest(perfTimeId, seatNums);

            given(seatService.getReservableSeatsNum(request.getPerfTimeId(), request.getSeatIds())).willReturn(2);

            reservationService.reserveSeats(userId, request);

            verify(performanceService, times(1)).checkPerfTimeIdExists(request.getPerfTimeId());
            verify(seatService, times(1)).setSeatsReserved(request.getSeatIds());
            verify(reservationMapper, times(1)).reserveSeats(userId, request.getSeatIds());
        }

        @Test
        @DisplayName("[예매 요청 좌석 수 검사 2] 최대 예매 가능 좌석 수(4개)를 초과하여 예매 요청할 경우 예매 실패 (예매 요청 좌석 수: 5) ")
        public void SingleSeatReservation() {
            insertSeatNums(5);
            ReservationRequest request = new ReservationRequest(perfTimeId, seatNums);

            assertThrows(ReserveAllowedQuantityExceededException.class, () -> {
                reservationService.reserveSeats(userId, request);
            });

            verify(performanceService, times(0)).checkPerfTimeIdExists(request.getPerfTimeId());
            verify(seatService, times(0)).setSeatsReserved(request.getSeatIds());
            verify(reservationMapper, times(0)).reserveSeats(userId, request.getSeatIds());
        }

        @Test
        @DisplayName("[공연 스케줄 유효 검사] 존재하지 않는 공연 스케줄에 대해 예매 요청한 경우 예매 실패")
        public void reserveWithNoExistingPerfTime() {
            insertSeatNums(2);
            ReservationRequest request = new ReservationRequest(perfTimeId, seatNums);

            willThrow(PerformanceTimeNotExistsException.class).given(performanceService).checkPerfTimeIdExists(request.getPerfTimeId());

            assertThrows(PerformanceTimeNotExistsException.class, () -> {
                reservationService.reserveSeats(userId, request);
            });

            verify(performanceService, times(1)).checkPerfTimeIdExists(request.getPerfTimeId());
            verify(seatService, times(0)).setSeatsReserved(request.getSeatIds());
            verify(reservationMapper, times(0)).reserveSeats(userId, request.getSeatIds());
        }

        @Test
        @DisplayName("[예매 요청 좌석 유효 검사 1] 존재하지 않는 좌석을 포함하여 예매 요청한 경우 예매 실패")
        public void reserveWithNoExistingSeat() {

            List<Seat> seatDB = initSeatDB();

            seatNums.add(1); // 정상 좌석
            seatNums.add(4); // 존재하지 않는 좌석

            ReservationRequest request = new ReservationRequest(perfTimeId, seatNums);

            willDoNothing().given(performanceService).checkPerfTimeIdExists(request.getPerfTimeId());
            given(seatService.getReservableSeatsNum(request.getPerfTimeId(), request.getSeatIds())).willReturn(getValidSeatNum(seatDB, seatNums, perfTimeId));

            assertThrows(SeatsNotReservableException.class, () -> {
                reservationService.reserveSeats(userId, request);
            });

            verify(performanceService, times(1)).checkPerfTimeIdExists(request.getPerfTimeId());
            verify(seatService, times(0)).setSeatsReserved(request.getSeatIds());
            verify(reservationMapper, times(0)).reserveSeats(userId, request.getSeatIds());
        }

        @Test
        @DisplayName("[예매 요청 좌석 유효 검사 2] 이미 예매 된 좌석을 포함하여 예매 요청한 경우 예매 실패")
        public void reserveWithAlreadyReservedSeat() {
            List<Seat> seatDB = initSeatDB();

            seatNums.add(1); // 정상 좌석
            seatNums.add(2); // 이미 예매된 좌석

            ReservationRequest request = new ReservationRequest(perfTimeId, seatNums);

            willDoNothing().given(performanceService).checkPerfTimeIdExists(request.getPerfTimeId());
            given(seatService.getReservableSeatsNum(request.getPerfTimeId(), request.getSeatIds())).willReturn(getValidSeatNum(seatDB, seatNums, perfTimeId));

            assertThrows(SeatsNotReservableException.class, () -> {
                reservationService.reserveSeats(userId, request);
            });

            verify(performanceService, times(1)).checkPerfTimeIdExists(request.getPerfTimeId());
            verify(seatService, times(0)).setSeatsReserved(request.getSeatIds());
            verify(reservationMapper, times(0)).reserveSeats(userId, request.getSeatIds());
        }

        @Test
        @DisplayName("[예매 요청 좌석 유효 검사 3] 스케줄(또는 공연)이 다른 좌석을 포함하여 예매 요청한 경우 예매 실패")
        public void reserveWithDifferentPerfTimeSeat() {
            List<Seat> seatDB = initSeatDB();

            seatNums.add(1); // 정상 좌석
            seatNums.add(3); // 공연 스케줄이 다른 좌석

            ReservationRequest request = new ReservationRequest(perfTimeId, seatNums);

            willDoNothing().given(performanceService).checkPerfTimeIdExists(request.getPerfTimeId());
            given(seatService.getReservableSeatsNum(request.getPerfTimeId(), request.getSeatIds())).willReturn(getValidSeatNum(seatDB, seatNums, perfTimeId));

            assertThrows(SeatsNotReservableException.class, () -> {
                reservationService.reserveSeats(userId, request);
            });

            verify(performanceService, times(1)).checkPerfTimeIdExists(request.getPerfTimeId());
            verify(seatService, times(0)).setSeatsReserved(request.getSeatIds());
            verify(reservationMapper, times(0)).reserveSeats(userId, request.getSeatIds());
        }

        public void insertSeatNums(int count) {
            for (int seat = 1; seat <= count; seat++) {
                seatNums.add(seat);
            }
        }

        public List<Seat> initSeatDB() {
            List<Seat> seatDB = new ArrayList<>();

            seatDB.add(new Seat(1, perfTimeId, false));
            seatDB.add(new Seat(2, perfTimeId, true));
            seatDB.add(new Seat(3, 2, false));

            return seatDB;
        }

        public int getValidSeatNum(List<Seat> seatDB, List<Integer> seatNums, int perfTimeId) {
            return (int) seatDB.stream()
                    .filter(s -> !s.reserved)
                    .filter(s -> seatNums.contains(s.seatId))
                    .filter(s -> s.perfTimeId == perfTimeId)
                    .count();
        }

        // 예매 테스트를 위한 Seat Class
        @AllArgsConstructor
        class Seat {
            int seatId;
            int perfTimeId;
            boolean reserved;
        }

    }

}