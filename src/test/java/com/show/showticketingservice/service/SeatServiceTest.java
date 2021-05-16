package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.SeatNotExistsException;
import com.show.showticketingservice.mapper.SeatMapper;
import com.show.showticketingservice.model.seat.SeatAndPriceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static com.show.showticketingservice.model.enumerations.RatingType.VIP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    private final int perfTimeId = 1;

    @Mock
    private SeatMapper seatMapper;

    @InjectMocks
    private SeatService seatService;

    private List<SeatAndPriceResponse> seatAndPriceResponses;

    private SeatAndPriceResponse seatAndPriceResponses1;

    private SeatAndPriceResponse seatAndPriceResponses2;

    @BeforeEach
    public void createSeatResponsesInfo() {
        seatAndPriceResponses = new ArrayList<>();

        seatAndPriceResponses1 = SeatAndPriceResponse.builder()
                .id(1)
                .rowNum(1)
                .colNum(1)
                .price(40000)
                .ratingType(VIP)
                .reserved(false)
                .build();

        seatAndPriceResponses2 = SeatAndPriceResponse.builder()
                .id(2)
                .rowNum(1)
                .colNum(2)
                .price(40000)
                .ratingType(VIP)
                .reserved(true)
                .build();

        seatAndPriceResponses.add(seatAndPriceResponses1);
        seatAndPriceResponses.add(seatAndPriceResponses2);
    }

    @Test
    @DisplayName("공연 시간 id를 통해 공연 좌석 조회를 성공합니다.")
    public void getPerfSeatSuccess() {
        when(seatMapper.getPerfSeatsAndPrices(perfTimeId)).thenReturn(seatAndPriceResponses);
        when(seatMapper.isSeatsExists(perfTimeId)).thenReturn(true);

        List<SeatAndPriceResponse> resultSeatResponses = seatService.getPerfSeatsAndPrices(perfTimeId);

        verify(seatMapper, times(1)).getPerfSeatsAndPrices(perfTimeId);
        verify(seatMapper, times(1)).isSeatsExists(perfTimeId);
        assertEquals(seatAndPriceResponses, resultSeatResponses);
    }

    @Test
    @DisplayName("공연 시간 id에 좌석이 등록되어 있지 않거나 전부 예약되었을 시 조회를 실패합니다.")
    public void failIfPerfIdDoseNotExistOrAllReservedWhenGetPerfSeats() {
        when(seatMapper.isSeatsExists(perfTimeId)).thenReturn(false);

        assertThrows(SeatNotExistsException.class, () -> {
            seatService.getPerfSeatsAndPrices(perfTimeId);
        });

        verify(seatMapper, times(1)).isSeatsExists(perfTimeId);
    }
}
