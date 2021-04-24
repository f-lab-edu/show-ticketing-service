package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.SameSeatRatingListAdditionException;
import com.show.showticketingservice.exception.performance.SeatPriceNonExistsException;
import com.show.showticketingservice.exception.performance.SeatRowNumWrongException;
import com.show.showticketingservice.exception.performance.SeatPriceAlreadyExistsException;
import com.show.showticketingservice.mapper.SeatPriceMapper;
import com.show.showticketingservice.model.enumerations.RatingType;
import com.show.showticketingservice.model.performance.SeatPriceRowNumData;
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

    public void checkStartRowNum(List<SeatPriceRequest> seatPriceRequests) {
        seatPriceRequests.stream().forEach(seatPriceRequest -> {
            if(seatPriceRequest.getStartRowNum() > seatPriceRequest.getEndRowNum()) {
                throw new SeatRowNumWrongException("좌석 시작 행 번호가 마지막 행 번호보다 큽니다.");
            }
        });
    }

    public void checkEndRowNum(List<SeatPriceRequest> seatPriceRequests, int totalRowNum) {

        seatPriceRequests.stream().forEach(seatPriceRequest -> {
            if(seatPriceRequest.getEndRowNum() > totalRowNum) {
                throw new SeatRowNumWrongException("좌석 행 번호가 공연 홀 총 좌석 행보다 큽니다.");
            }
        });
    }

    public void checkDuplicationRowNum(List<SeatPriceRequest> seatPriceRequests) {
        HashMap<Integer, Integer> seatsNumber = new HashMap<Integer, Integer>();

        seatPriceRequests.stream().forEach(seatPriceRequest -> {
            IntStream.range(seatPriceRequest.getStartRowNum(), seatPriceRequest.getEndRowNum() + 1)
                    .forEach(seat -> {
                        if(seatsNumber.containsKey(seat)) {
                            throw new SeatRowNumWrongException("중복되는 좌석 행 번호가 존재합니다.");
                        }

                        seatsNumber.put(seat, 1);
                    });
        });
    }

    public void checkEmptyRowNum(List<SeatPriceRequest> seatPriceRequests, int totalRowNum) {

        int requestTotalRowNum = seatPriceRequests.stream()
                .map(seatPriceRequest -> seatPriceRequest.getEndRowNum() - seatPriceRequest.getStartRowNum() + 1)
                .reduce(0,(acc, curr) -> acc + curr);

        if(totalRowNum != requestTotalRowNum) {
            throw new SeatRowNumWrongException("비어있는 좌석 행 번호가 존재합니다.");
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

    public void insertSeatsPrice(List<SeatPriceRequest> seatPriceRequests, int performanceId) {

        checkSeatPriceAlreadyExistsException(performanceId);

        int totalRowNum = venueHallService.getVenueHallRowNum(performanceId);

        checkStartRowNum(seatPriceRequests);
        checkEndRowNum(seatPriceRequests, totalRowNum);
        checkDuplicationRowNum(seatPriceRequests);
        checkEmptyRowNum(seatPriceRequests, totalRowNum);
        checkDuplicationSeatsRatingList(seatPriceRequests);

        seatPriceMapper.insertSeatsPrice(seatPriceRequests, performanceId);
    }

    public List<SeatPriceRowNumData> getSeatPriceRowNum(int performanceId) {
        return seatPriceMapper.getSeatPriceRowNum(performanceId);
    }

    public void checkSeatPriceAlreadyExistsException(int performanceId) {
        if(seatPriceMapper.isSeatPriceExists(performanceId)) {
            throw new SeatPriceAlreadyExistsException("이 공연은 좌석 가격이 이미 등록되어 있습니다.");
        }

    }

    public void checkSeatPriceNonExistsException(int performanceId) {
        if(!seatPriceMapper.isSeatPriceExists(performanceId)) {
            throw new SeatPriceNonExistsException("공연 좌석 가격 정보가 존재하지 않습니다.");
        }

    }
}
