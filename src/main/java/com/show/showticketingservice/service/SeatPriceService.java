package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.SameSeatRatingListAdditionException;
import com.show.showticketingservice.exception.performance.SeatColNumWrongException;
import com.show.showticketingservice.mapper.SeatPriceMapper;
import com.show.showticketingservice.model.enumerations.RatingType;
import com.show.showticketingservice.model.performance.SeatPriceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SeatPriceService {

    private final VenueHallService venueHallService;

    private final SeatPriceMapper seatPriceMapper;

    public void checkStartColNum(List<SeatPriceRequest> seatPriceRequests) {
        seatPriceRequests.stream().forEach(seatPriceRequest -> {
            if(seatPriceRequest.getStartRowNum() > seatPriceRequest.getEndRowNum()) {
                throw new SeatColNumWrongException("좌석 시작 행 번호가 마지막 행 번호보다 큽니다.");
            }
        });
    }

    public void checkEndColNum(List<SeatPriceRequest> seatPriceRequests, int totalRowNum) {

        seatPriceRequests.stream().forEach(seatPriceRequest -> {
            if(seatPriceRequest.getEndRowNum() > totalRowNum) {
                throw new SeatColNumWrongException("좌석 행 번호가 공연 홀 총 좌석 행보다 큽니다.");
            }
        });
    }

    public void checkDuplicationColNum(List<SeatPriceRequest> seatPriceRequests) {
        HashMap<Integer, Integer> seatsNumber = new HashMap<Integer, Integer>();

        seatPriceRequests.stream().forEach(seatPriceRequest -> {
            IntStream.range(seatPriceRequest.getStartRowNum(), seatPriceRequest.getEndRowNum() + 1)
                    .forEach(seat -> {
                        if(seatsNumber.containsKey(seat)) {
                            throw new SeatColNumWrongException("중복되는 좌석 행 번호가 존재합니다.");
                        }

                        seatsNumber.put(seat, 1);
                    });
        });
    }

    public void checkEmptyColNum(List<SeatPriceRequest> seatPriceRequests, int totalColNum) {

        int requestTotalColNum = seatPriceRequests.stream()
                .map(seatPriceRequest -> seatPriceRequest.getEndRowNum() - seatPriceRequest.getStartRowNum() + 1)
                .reduce(0,(acc, curr) -> acc + curr);

        if(totalColNum != requestTotalColNum) {
            throw new SeatColNumWrongException("비어있는 좌석 행 번호가 존재합니다.");
        }

    }

    public void checkDuplicationSeatsRatingList(List<SeatPriceRequest> seatPriceRequests) {
        HashMap<RatingType, Integer> seatRatingMap = new HashMap<>();

        seatPriceRequests.stream().forEach(seatPriceRequest -> {
            if(seatRatingMap.containsKey(seatPriceRequest.getRatingType())) {
                throw new SameSeatRatingListAdditionException();
            }

            seatRatingMap.put(seatPriceRequest.getRatingType(), 1);
        });
    }

    @Transactional
    public void insertSeatsPrice(List<SeatPriceRequest> seatPriceRequests, int performanceId) {

        int totalRowNum = venueHallService.getVenueHallRowNum(performanceId);

        checkStartColNum(seatPriceRequests);
        checkEndColNum(seatPriceRequests, totalRowNum);
        checkDuplicationColNum(seatPriceRequests);
        checkEmptyColNum(seatPriceRequests, totalRowNum);
        checkDuplicationSeatsRatingList(seatPriceRequests);

        seatPriceMapper.insertSeatsPrice(seatPriceRequests, performanceId);
    }

}
