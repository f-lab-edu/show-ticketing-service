package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.SameSeatRatingListAdditionException;
import com.show.showticketingservice.exception.performance.SeatPriceAlreadyExistsException;
import com.show.showticketingservice.exception.performance.SeatRowNumWrongException;
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
                .startRowNum(1)
                .endRowNum(3)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.S)
                .startRowNum(4)
                .endRowNum(6)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.A)
                .startRowNum(7)
                .endRowNum(8)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(seatPriceMapper.isSeatPriceExists(performanceId)).thenReturn(false);
        when(venueHallService.getVenueHallRowNum(performanceId)).thenReturn(8);
        doNothing().when(seatPriceMapper).insertSeatsPrice(seatPriceRequests, performanceId);

        seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);

        verify(seatPriceMapper, times(1)).isSeatPriceExists(performanceId);
        verify(venueHallService, times(1)).getVenueHallRowNum(performanceId);
        verify(seatPriceMapper, times(1)).insertSeatsPrice(seatPriceRequests, performanceId);
    }

    @Test
    @DisplayName("좌석 가격 정보 추가 시 시작 행이 마지막 행보다 커서 실패합니다.")
    public void seatStartRowNumException() {
        SeatPriceRequest seatPriceRequestOne = SeatPriceRequest.builder()
                .price(60000)
                .ratingType(RatingType.VIP)
                .startRowNum(3)
                .endRowNum(1)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.S)
                .startRowNum(6)
                .endRowNum(4)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.A)
                .startRowNum(8)
                .endRowNum(7)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(seatPriceMapper.isSeatPriceExists(performanceId)).thenReturn(false);
        when(venueHallService.getVenueHallRowNum(performanceId)).thenReturn(8);

        assertThrows(SeatRowNumWrongException.class, () -> {
            seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);
        });

        verify(seatPriceMapper, times(1)).isSeatPriceExists(performanceId);
        verify(venueHallService, times(1)).getVenueHallRowNum(performanceId);
    }

    @Test
    @DisplayName("좌석 가격 정보 추가 시 마지막 행이 공연 홀 총 좌석 행보다 커서 실패합니다.")
    public void seatEndRowNumException() {
        SeatPriceRequest seatPriceRequestOne = SeatPriceRequest.builder()
                .price(60000)
                .ratingType(RatingType.VIP)
                .startRowNum(1)
                .endRowNum(3)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.S)
                .startRowNum(4)
                .endRowNum(6)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.A)
                .startRowNum(7)
                .endRowNum(50)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(seatPriceMapper.isSeatPriceExists(performanceId)).thenReturn(false);
        when(venueHallService.getVenueHallRowNum(performanceId)).thenReturn(8);

        assertThrows(SeatRowNumWrongException.class, () -> {
            seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);
        });

        verify(seatPriceMapper, times(1)).isSeatPriceExists(performanceId);
        verify(venueHallService, times(1)).getVenueHallRowNum(performanceId);
    }

    @Test
    @DisplayName("좌석 가격 정보 추가 시 중복되는 좌석 행(겹치는 행)이 존재하여 실패합니다.")
    public void duplicateSeatRowNumException() {
        SeatPriceRequest seatPriceRequestOne = SeatPriceRequest.builder()
                .price(60000)
                .ratingType(RatingType.VIP)
                .startRowNum(1)
                .endRowNum(6)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.S)
                .startRowNum(4)
                .endRowNum(6)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.A)
                .startRowNum(7)
                .endRowNum(8)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(seatPriceMapper.isSeatPriceExists(performanceId)).thenReturn(false);
        when(venueHallService.getVenueHallRowNum(performanceId)).thenReturn(8);

        assertThrows(SeatRowNumWrongException.class, () -> {
            seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);
        });

        verify(seatPriceMapper, times(1)).isSeatPriceExists(performanceId);
        verify(venueHallService, times(1)).getVenueHallRowNum(performanceId);
    }

    @Test
    @DisplayName("좌석 가격 정보 추가 시 비어있는 좌석 행이 존재하여 실패합니다.")
    public void seatEmptyRowNumException() {
        SeatPriceRequest seatPriceRequestOne = SeatPriceRequest.builder()
                .price(60000)
                .ratingType(RatingType.VIP)
                .startRowNum(1)
                .endRowNum(2)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.S)
                .startRowNum(5)
                .endRowNum(6)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.A)
                .startRowNum(7)
                .endRowNum(8)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(seatPriceMapper.isSeatPriceExists(performanceId)).thenReturn(false);
        when(venueHallService.getVenueHallRowNum(performanceId)).thenReturn(8);

        assertThrows(SeatRowNumWrongException.class, () -> {
            seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);
        });

        verify(seatPriceMapper, times(1)).isSeatPriceExists(performanceId);
        verify(venueHallService, times(1)).getVenueHallRowNum(performanceId);
    }

    @Test
    @DisplayName("좌석 가격 정보 추가 시 중복되는 등급을 추가하여 실패합니다.")
    public void duplicateSeatRatingException() {
        SeatPriceRequest seatPriceRequestOne = SeatPriceRequest.builder()
                .price(60000)
                .ratingType(RatingType.VIP)
                .startRowNum(1)
                .endRowNum(2)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.VIP)
                .startRowNum(3)
                .endRowNum(6)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.VIP)
                .startRowNum(7)
                .endRowNum(8)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(seatPriceMapper.isSeatPriceExists(performanceId)).thenReturn(false);
        when(venueHallService.getVenueHallRowNum(performanceId)).thenReturn(8);

        assertThrows(SameSeatRatingListAdditionException.class, () -> {
            seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);
        });

        verify(seatPriceMapper, times(1)).isSeatPriceExists(performanceId);
        verify(venueHallService, times(1)).getVenueHallRowNum(performanceId);
    }

    @Test
    @DisplayName("이미 DB에 좌석 가격 정보가 있을 시 실패합니다.")
    public void SeatPriceAlreadyExistsException() {
        SeatPriceRequest seatPriceRequestOne = SeatPriceRequest.builder()
                .price(60000)
                .ratingType(RatingType.VIP)
                .startRowNum(1)
                .endRowNum(3)
                .build();

        SeatPriceRequest seatPriceRequestTwo = SeatPriceRequest.builder()
                .price(40000)
                .ratingType(RatingType.S)
                .startRowNum(4)
                .endRowNum(6)
                .build();

        SeatPriceRequest seatPriceRequestThree = SeatPriceRequest.builder()
                .price(10000)
                .ratingType(RatingType.A)
                .startRowNum(7)
                .endRowNum(8)
                .build();

        seatPriceRequests.add(seatPriceRequestOne);
        seatPriceRequests.add(seatPriceRequestTwo);
        seatPriceRequests.add(seatPriceRequestThree);

        when(seatPriceMapper.isSeatPriceExists(performanceId)).thenReturn(true);

        assertThrows(SeatPriceAlreadyExistsException.class, () -> {
            seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);
        });

        verify(seatPriceMapper, times(1)).isSeatPriceExists(performanceId);
    }
}
