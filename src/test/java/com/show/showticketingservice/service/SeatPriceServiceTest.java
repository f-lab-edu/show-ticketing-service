package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.SameSeatRatingListAdditionException;
import com.show.showticketingservice.exception.performance.SeatColNumWrongException;
import com.show.showticketingservice.mapper.SeatPriceMapper;
import com.show.showticketingservice.model.enumerations.RatingType;
import com.show.showticketingservice.model.performance.SeatPriceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatPriceServiceTest {

    @Mock
    VenueHallService venueHallService;

    @Mock
    SeatPriceMapper seatPriceMapper;

    @InjectMocks
    SeatPriceService seatPriceService;

    List<SeatPriceRequest> seatPriceRequests;

    int performanceId;

    @BeforeEach
    public void init() {
        performanceId = 1;
        seatPriceRequests = new ArrayList<>();
    }

    @Test
    @DisplayName("좌석 가격 정보 추가를 성공합니다.")
    public void insertSeatPriceSuccess() {
        SeatPriceRequest seatPriceRequestOne = SeatPriceRequest.builder()
                .price(60000)
                .ratingType(RatingType.VIP)
                .startColNum(1)
                .endColNum(3)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.S)
                .startColNum(4)
                .endColNum(6)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.A)
                .startColNum(7)
                .endColNum(8)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(venueHallService.getVenueHallcolNum(performanceId)).thenReturn(8);
        doNothing().when(seatPriceMapper).insertSeatsPrice(seatPriceRequests, performanceId);

        seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);

        verify(venueHallService, times(1)).getVenueHallcolNum(performanceId);
        verify(seatPriceMapper, times(1)).insertSeatsPrice(seatPriceRequests, performanceId);
    }

    @Test
    @DisplayName("좌석 가격 정보 추가 시 시작 행이 마지막 행보다 커서 실패합니다.")
    public void seatStartColNumException() {
        SeatPriceRequest seatPriceRequestOne = SeatPriceRequest.builder()
                .price(60000)
                .ratingType(RatingType.VIP)
                .startColNum(3)
                .endColNum(1)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.S)
                .startColNum(6)
                .endColNum(4)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.A)
                .startColNum(8)
                .endColNum(7)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(venueHallService.getVenueHallcolNum(performanceId)).thenReturn(8);

        assertThrows(SeatColNumWrongException.class, () -> {
            seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);
        });

        verify(venueHallService, times(1)).getVenueHallcolNum(performanceId);
    }

    @Test
    @DisplayName("좌석 가격 정보 추가 시 마지막 행이 공연 홀 총 좌석 행보다 커서 실패합니다.")
    public void seatEndColNumException() {
        SeatPriceRequest seatPriceRequestOne = SeatPriceRequest.builder()
                .price(60000)
                .ratingType(RatingType.VIP)
                .startColNum(1)
                .endColNum(3)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.S)
                .startColNum(4)
                .endColNum(6)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.A)
                .startColNum(7)
                .endColNum(50)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(venueHallService.getVenueHallcolNum(performanceId)).thenReturn(8);

        assertThrows(SeatColNumWrongException.class, () -> {
            seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);
        });

        verify(venueHallService, times(1)).getVenueHallcolNum(performanceId);
    }

    @Test
    @DisplayName("좌석 가격 정보 추가 시 중복되는 좌석 행(겹치는 행)이 존재하여 실패합니다.")
    public void duplicateSeatColNumException() {
        SeatPriceRequest seatPriceRequestOne = SeatPriceRequest.builder()
                .price(60000)
                .ratingType(RatingType.VIP)
                .startColNum(1)
                .endColNum(6)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.S)
                .startColNum(4)
                .endColNum(6)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.A)
                .startColNum(7)
                .endColNum(8)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(venueHallService.getVenueHallcolNum(performanceId)).thenReturn(8);

        assertThrows(SeatColNumWrongException.class, () -> {
            seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);
        });

        verify(venueHallService, times(1)).getVenueHallcolNum(performanceId);
    }

    @Test
    @DisplayName("좌석 가격 정보 추가 시 비어있는 좌석 행이 존재하여 실패합니다.")
    public void seatEmptyColNumException() {
        SeatPriceRequest seatPriceRequestOne = SeatPriceRequest.builder()
                .price(60000)
                .ratingType(RatingType.VIP)
                .startColNum(1)
                .endColNum(2)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.S)
                .startColNum(5)
                .endColNum(6)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.A)
                .startColNum(7)
                .endColNum(8)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(venueHallService.getVenueHallcolNum(performanceId)).thenReturn(8);

        assertThrows(SeatColNumWrongException.class, () -> {
            seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);
        });

        verify(venueHallService, times(1)).getVenueHallcolNum(performanceId);
    }

    @Test
    @DisplayName("좌석 가격 정보 추가 시 중복되는 등급을 추가하여 실패합니다.")
    public void duplicateSeatRatingException() {
        SeatPriceRequest seatPriceRequestOne = SeatPriceRequest.builder()
                .price(60000)
                .ratingType(RatingType.VIP)
                .startColNum(1)
                .endColNum(2)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.VIP)
                .startColNum(3)
                .endColNum(6)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.VIP)
                .startColNum(7)
                .endColNum(8)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(venueHallService.getVenueHallcolNum(performanceId)).thenReturn(8);

        assertThrows(SameSeatRatingListAdditionException.class, () -> {
            seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);
        });

        verify(venueHallService, times(1)).getVenueHallcolNum(performanceId);
    }
}
