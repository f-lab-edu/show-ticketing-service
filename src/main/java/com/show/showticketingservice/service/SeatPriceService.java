package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.SeatNumberWrongException;
import com.show.showticketingservice.mapper.SeatPriceMapper;
import com.show.showticketingservice.model.performance.SeatPriceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SeatPriceService {

    private final VenueHallService venueHallService;

    private final SeatPriceMapper seatPriceMapper;

    public void checkStartSeat(List<SeatPriceRequest> seatPriceRequests) {
        seatPriceRequests.stream().forEach(seatPriceRequest -> {
            if(seatPriceRequest.getStartSeat() > seatPriceRequest.getEndSeat()) {
                throw new SeatNumberWrongException("좌석 시작 번호가 마지막 번호보다 큽니다.");
            }
        });
    }

    public void checkEndSeat(List<SeatPriceRequest> seatPriceRequests, int venueId, int hallId) {

        int seatingCapacity = venueHallService.getVenueHall(venueId, hallId);

        seatPriceRequests.stream().forEach(seatPriceRequest -> {
            if(seatPriceRequest.getEndSeat() > seatingCapacity) {
                throw new SeatNumberWrongException("좌석 번호가 공연 홀 좌석 수보다 큽니다.");
            }
        });
    }

    public void checkDuplicationSeatNumber(List<SeatPriceRequest> seatPriceRequests) {
        HashMap<Integer, Integer> seatsNumber = new HashMap<Integer, Integer>();

        seatPriceRequests.stream().forEach(seatPriceRequest -> {
            IntStream.range(seatPriceRequest.getStartSeat(), seatPriceRequest.getEndSeat() + 1)
                    .forEach(seat -> {
                        if(seatsNumber.containsKey(seat)) {
                            throw new SeatNumberWrongException("중복되는 좌석 번호가 존재합니다.");
                        }

                        seatsNumber.put(seat, 1);
                    });
        });
    }

    public void insertSeatsPrice(List<SeatPriceRequest> seatPriceRequests, int venueId, int hallId, int performanceId) {
        checkStartSeat(seatPriceRequests);

        checkEndSeat(seatPriceRequests, venueId, hallId);

        checkDuplicationSeatNumber(seatPriceRequests);

        seatPriceMapper.insertSeatsPrice(seatPriceRequests, performanceId);
    }
}
