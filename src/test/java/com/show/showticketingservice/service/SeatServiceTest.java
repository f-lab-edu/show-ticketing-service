package com.show.showticketingservice.service;

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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    private final int perfTimeId = 1;

    @Mock
    private SeatMapper seatMapper;

    @Mock
    private PerformanceService performanceService;

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
        doNothing().when(performanceService).checkPerfTimeIdExists(perfTimeId);

        List<SeatAndPriceResponse> resultSeatResponses = seatService.getPerfSeatsAndPrices(perfTimeId);

        verify(seatMapper, times(1)).getPerfSeatsAndPrices(perfTimeId);
        verify(performanceService, times(1)).checkPerfTimeIdExists(perfTimeId);
        assertEquals(seatAndPriceResponses, resultSeatResponses);
    }

}
