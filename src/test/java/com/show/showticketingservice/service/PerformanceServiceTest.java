package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.PerformanceNotExistsException;
import com.show.showticketingservice.exception.performance.PerformanceTicketNotExistsException;
import com.show.showticketingservice.exception.performance.PerformanceTimeNotExistsException;
import com.show.showticketingservice.mapper.PerformanceMapper;
import com.show.showticketingservice.mapper.PerformanceTimeMapper;
import com.show.showticketingservice.model.criteria.PerformancePagingCriteria;
import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.performance.*;
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
public class PerformanceServiceTest {

    @Mock
    PerformanceMapper performanceMapper;

    @InjectMocks
    PerformanceService performanceService;

    @Mock
    PerformanceTimeMapper performanceTimeMapper;

    @Test
    @DisplayName("첫 페이지의 모든 공연 타입 정보 리스트 조회를 성공합니다.")
    public void getPerformanceListSuccess() {

        PerformancePeriod performancePeriod = PerformancePeriod.builder()
                .firstDay("2021-05-04")
                .lastDay("2021-06-04")
                .build();

        PerformanceResponse performanceResponse = PerformanceResponse.builder()
                .id(1)
                .venueName("공연장1")
                .hallName("A홀")
                .title("피터팬")
                .imageFilePath(null)
                .performancePeriod(performancePeriod)
                .build();

        ShowType showType = null;
        Integer lastPerfId = null;
        PerformancePagingCriteria performancePagingCriteria = new PerformancePagingCriteria(lastPerfId);

        List<PerformanceResponse> performanceResponses = new ArrayList<>();
        performanceResponses.add(performanceResponse);

        when(performanceMapper.isPerfIdAndShowTypeExists(showType, lastPerfId)).thenReturn(true);
        when(performanceMapper.getPerformances(showType, performancePagingCriteria)).thenReturn(performanceResponses);
        List<PerformanceResponse> resultPerformanceResponses = performanceService.getPerformances(showType, performancePagingCriteria);

        verify(performanceMapper, times(1)).isPerfIdAndShowTypeExists(showType, lastPerfId);
        verify(performanceMapper, times(1)).getPerformances(showType, performancePagingCriteria);
        assertEquals(performanceResponses, resultPerformanceResponses);
    }

    @Test
    @DisplayName("페이지 기능을 처리할 공연 id가 존재하지 않습니다.")
    public void getPerformanceListFailPageFunctionIdNotExists() {
        PerformancePeriod performancePeriod = PerformancePeriod.builder()
                .firstDay("2021-05-04")
                .lastDay("2021-06-04")
                .build();

        PerformanceResponse performanceResponse = PerformanceResponse.builder()
                .id(100)
                .venueName("공연장1")
                .hallName("A홀")
                .title("피터팬")
                .imageFilePath(null)
                .performancePeriod(performancePeriod)
                .build();

        ShowType showType = null;
        Integer lastPerfId = null;
        PerformancePagingCriteria performancePagingCriteria = new PerformancePagingCriteria(lastPerfId);

        List<PerformanceResponse> performanceResponses = new ArrayList<>();
        performanceResponses.add(performanceResponse);

        when(performanceMapper.isPerfIdAndShowTypeExists(showType, lastPerfId)).thenReturn(false);

        assertThrows(PerformanceNotExistsException.class, () -> {
            performanceService.getPerformances(showType, performancePagingCriteria);
        });

        verify(performanceMapper, times(1)).isPerfIdAndShowTypeExists(showType, lastPerfId);

    }

    @Test
    @DisplayName("공연 예매 시 먼저 스케줄과 공연 이름을 조회합니다.")
    public void getPerformanceTimeAndTitleSuccess() {
        List<PerformanceTimeResponse> performanceTimeResponses = new ArrayList<>();
        PerformanceTimeResponse performanceTimeResponse = PerformanceTimeResponse.builder()
                .id(2)
                .startTime("2021-05-04 13:00:00")
                .endTime("2021-05-04 15:00:00")
                .build();

        performanceTimeResponses.add(performanceTimeResponse);

        PerformanceTitleAndTimesResponse performanceTitleAndTimesResponse = PerformanceTitleAndTimesResponse.builder()
                .title("피터팬")
                .performanceTimeResponses(performanceTimeResponses)
                .build();

        int performanceId = 1;

        when(performanceMapper.isPerfTicket(performanceId)).thenReturn(true);
        when(performanceMapper.getPerformanceTitleAndTimes(performanceId)).thenReturn(performanceTitleAndTimesResponse);
        PerformanceTitleAndTimesResponse resultPerformanceTitleAndTimesResponse = performanceService.getPerformanceTitleAndTimes(performanceId);

        verify(performanceMapper, times(1)).isPerfTicket(performanceId);
        verify(performanceMapper, times(1)).getPerformanceTitleAndTimes(performanceId);

        assertEquals(performanceTitleAndTimesResponse, resultPerformanceTitleAndTimesResponse);
    }

    @Test
    @DisplayName("공연 예매 시 스케줄 또는 예약할 좌석이 없을 경우 조회가 실패합니다.")
    public void performanceTimeNotExistsOrSeatNotExists() {

        int performanceId = 1;

        when(performanceMapper.isPerfTicket(performanceId)).thenReturn(false);

        assertThrows(PerformanceTicketNotExistsException.class, () -> {
            performanceService.getPerformanceTitleAndTimes(performanceId);
        });

        verify(performanceMapper, times(1)).isPerfTicket(performanceId);

    }

    @Test
    @DisplayName("공연 예매 시 스케줄을 선택하면 남은 좌석 수를 조회합니다.")
    public void getPerformanceTimeAndSeatCapacitySuccess() {
        int performanceId = 1;
        String perfDate = "2021-12-11";

        List<SeatCapacityResponse> seatCapacityResponses = new ArrayList<>();
        SeatCapacityResponse seatCapacityResponse = SeatCapacityResponse.builder()
                .ratingType(VIP)
                .seatCapacity(20)
                .build();
        seatCapacityResponses.add(seatCapacityResponse);

        List<PerfTimeAndSeatCapacityResponse> perfTimeAndSeatCapacityResponses = new ArrayList<>();
        PerfTimeAndSeatCapacityResponse perfTimeAndSeatCapacityResponse = PerfTimeAndSeatCapacityResponse.builder()
                .perfTimeId(1)
                .startTime("12:00:00")
                .seatCapacityResponses(seatCapacityResponses)
                .build();
        perfTimeAndSeatCapacityResponses.add(perfTimeAndSeatCapacityResponse);

        when(performanceTimeMapper.getPerfTimeAndSeatCapacity(performanceId, perfDate)).thenReturn(perfTimeAndSeatCapacityResponses);
        when(performanceMapper.isPerformanceIdExists(performanceId)).thenReturn(true);
        when(performanceTimeMapper.isPerfDate(performanceId, perfDate)).thenReturn(true);

        List<PerfTimeAndSeatCapacityResponse> resultPerfTimeAndSeatCapacityResponses = performanceService.getPerfTimeAndSeatCapacity(performanceId, perfDate);

        verify(performanceTimeMapper, times(1)).getPerfTimeAndSeatCapacity(performanceId, perfDate);
        verify(performanceMapper, times(1)).isPerformanceIdExists(performanceId);
        verify(performanceTimeMapper, times(1)).isPerfDate(performanceId, perfDate);

        assertEquals(perfTimeAndSeatCapacityResponses, resultPerfTimeAndSeatCapacityResponses);
    }

    @Test
    @DisplayName("공연 예매 시 스케줄을 선택할 때 공연 id가 존재하지 않을 시 조회를 실패합니다.")
    public void performanceIdNotExists() {

        int performanceId = 1;
        String perfDate = "2021-12-11";

        when(performanceMapper.isPerformanceIdExists(performanceId)).thenReturn(false);

        assertThrows(PerformanceNotExistsException.class, () -> {
            performanceService.getPerfTimeAndSeatCapacity(performanceId, perfDate);
        });

        verify(performanceMapper, times(1)).isPerformanceIdExists(performanceId);
    }

    @Test
    @DisplayName("공연 예매 시 스케줄을 선택할 때 공연 시간이 존재하지 않을 시 조회를 실패합니다.")
    public void performanceTimeNotNotExists() {

        int performanceId = 1;
        String perfDate = "2021-12-11";

        when(performanceMapper.isPerformanceIdExists(performanceId)).thenReturn(true);
        when(performanceTimeMapper.isPerfDate(performanceId, perfDate)).thenReturn(false);

        assertThrows(PerformanceTimeNotExistsException.class, () -> {
            performanceService.getPerfTimeAndSeatCapacity(performanceId, perfDate);
        });

        verify(performanceMapper, times(1)).isPerformanceIdExists(performanceId);
        verify(performanceTimeMapper, times(1)).isPerfDate(performanceId, perfDate);
    }
}
