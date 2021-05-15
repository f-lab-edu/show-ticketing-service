package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.PerformanceNotExistsException;
import com.show.showticketingservice.exception.performance.SeatNotExistsException;
import com.show.showticketingservice.mapper.SeatMapper;
import com.show.showticketingservice.model.performance.SeatResponse;
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

    private List<SeatResponse> seatResponses;

    private SeatResponse seatResponse1;

    private SeatResponse seatResponse2;

    @BeforeEach
    public void createSeatResponsesInfo() {
        seatResponses = new ArrayList<>();

        seatResponse1 = SeatResponse.builder()
                .id(1)
                .rowNum(1)
                .colNum(1)
                .ratingType(VIP)
                .reserved(false)
                .build();

        seatResponse2 = SeatResponse.builder()
                .id(2)
                .rowNum(1)
                .colNum(2)
                .ratingType(VIP)
                .reserved(true)
                .build();

        seatResponses.add(seatResponse1);
        seatResponses.add(seatResponse2);
    }

    @Test
    @DisplayName("공연 시간 id를 통해 공연 좌석 조회를 성공합니다.")
    public void getPerfSeatSuccess() {
        when(seatMapper.getPerfSeats(perfTimeId)).thenReturn(seatResponses);
        when(seatMapper.isSeatExists(perfTimeId)).thenReturn(true);

        List<SeatResponse> resultSeatResponses = seatService.getPerfSeats(perfTimeId);

        verify(seatMapper, times(1)).getPerfSeats(perfTimeId);
        verify(seatMapper, times(1)).isSeatExists(perfTimeId);
        assertEquals(seatResponses, resultSeatResponses);
    }

    @Test
    @DisplayName("공연 시간 id에 좌석이 등록되어 있지 않거나 전부 예약되었을 시 조회를 실패합니다.")
    public void failIfPerfIdDoseNotExistOrAllReservedWhenGetPerfSeats() {
        when(seatMapper.isSeatExists(perfTimeId)).thenReturn(false);

        assertThrows(SeatNotExistsException.class, () -> {
            seatService.getPerfSeats(perfTimeId);
        });

        verify(seatMapper, times(1)).isSeatExists(perfTimeId);
    }
}
