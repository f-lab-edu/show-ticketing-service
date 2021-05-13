package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.PerformanceNotExistsException;
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
    @DisplayName("공연 id를 통해 스케줄과 공연 이름을 조회합니다.")
    public void getPerformanceTimeAndTitleSuccess() {

        int performanceId = 1;

        List<PerformanceTimeResponse> performanceTimeResponses = new ArrayList<>();
        PerformanceTimeResponse performanceTimeResponse = PerformanceTimeResponse.builder()
                .id(2)
                .startTime("2021-05-04")
                .endTime("2021-05-04")
                .build();

        performanceTimeResponses.add(performanceTimeResponse);

        PerformanceTitleAndTimesResponse performanceTitleAndTimesResponse = PerformanceTitleAndTimesResponse.builder()
                .title("피터팬")
                .performanceTimeResponses(performanceTimeResponses)
                .build();

        when(performanceMapper.getPerformanceTitleAndTimes(performanceId)).thenReturn(performanceTitleAndTimesResponse);
        when(performanceMapper.isPerformanceIdExists(performanceId)).thenReturn(true);
        PerformanceTitleAndTimesResponse resultPerformanceTitleAndTimesResponse = performanceService.getPerformanceTitleAndTimes(performanceId);

        verify(performanceMapper, times(1)).getPerformanceTitleAndTimes(performanceId);
        verify(performanceMapper, times(1)).isPerformanceIdExists(performanceId);

        assertEquals(performanceTitleAndTimesResponse, resultPerformanceTitleAndTimesResponse);
    }

    @Test
    @DisplayName("공연 스케줄과 이름을 조회 시 요청한 공연 id가 존재하지 않으면 조회를 실패합니다.")
    public void failIfPerfIdDoseNotExistWhenGetPerformanceTimeAndTitle() {

        int performanceId = 1;

        when(performanceMapper.isPerformanceIdExists(performanceId)).thenReturn(false);

        assertThrows(PerformanceNotExistsException.class, () -> {
            performanceService.getPerformanceTitleAndTimes(performanceId);
        });

        verify(performanceMapper, times(1)).isPerformanceIdExists(performanceId);
    }

    @Test
    @DisplayName("공연 id와 공연 시간 id를 통해 시작 시간과 남은 좌석 정보를 조회합니다.")
    public void getPerformanceTimeAndRemainingSeatsSuccess() {
        int performanceId = 1;
        int perfTimeId = 2;

        List<RemainingSeatsResponse> remainingSeatsRespons = new ArrayList<>();
        RemainingSeatsResponse remainingSeatsResponse = RemainingSeatsResponse.builder()
                .ratingType(VIP)
                .remainingSeats(20)
                .build();
        remainingSeatsRespons.add(remainingSeatsResponse);

        List<PerfTimeAndRemainingSeatsResponse> perfTimeAndRemainingSeatsRespons = new ArrayList<>();
        PerfTimeAndRemainingSeatsResponse perfTimeAndRemainingSeatsResponse = PerfTimeAndRemainingSeatsResponse.builder()
                .perfTimeId(1)
                .startTime("12:00:00")
                .remainingSeatsRespons(remainingSeatsRespons)
                .build();
        perfTimeAndRemainingSeatsRespons.add(perfTimeAndRemainingSeatsResponse);

        when(performanceTimeMapper.getPerfTimeAndRemainingSeats(performanceId, perfTimeId)).thenReturn(perfTimeAndRemainingSeatsRespons);
        when(performanceMapper.isPerformanceIdExists(performanceId)).thenReturn(true);
        when(performanceTimeMapper.isPerfDateExists(performanceId, perfTimeId)).thenReturn(true);

        List<PerfTimeAndRemainingSeatsResponse> resultPerfTimeAndRemainingSeatsRespons = performanceService.getPerfTimeAndRemainingSeats(performanceId, perfTimeId);

        verify(performanceTimeMapper, times(1)).getPerfTimeAndRemainingSeats(performanceId, perfTimeId);
        verify(performanceMapper, times(1)).isPerformanceIdExists(performanceId);
        verify(performanceTimeMapper, times(1)).isPerfDateExists(performanceId, perfTimeId);

        assertEquals(perfTimeAndRemainingSeatsRespons, resultPerfTimeAndRemainingSeatsRespons);
    }

    @Test
    @DisplayName("공연 시간과 남은 좌석 정보를 조회 시 요청한 공연 id가 존재하지 않으면 조회를 실패합니다.")
    public void failIfPerfIdDoseNotExistWhenGetPerformanceTimeAndRemainingSeats() {

        int performanceId = 1;
        int perfTimeId = 2;

        when(performanceMapper.isPerformanceIdExists(performanceId)).thenReturn(false);

        assertThrows(PerformanceNotExistsException.class, () -> {
            performanceService.getPerfTimeAndRemainingSeats(performanceId, perfTimeId);
        });

        verify(performanceMapper, times(1)).isPerformanceIdExists(performanceId);
    }

    @Test
    @DisplayName("공연 시간과 남은 좌석 정보를 조회 시 요청한 공연 시간 id가 존재하지 않으면 조회를 실패합니다.")
    public void failIfPerfTimeIdDoseNotExistWhenGetPerformanceTimeAndRemainingSeats() {

        int performanceId = 1;
        int perfTimeId = 2;

        when(performanceMapper.isPerformanceIdExists(performanceId)).thenReturn(true);
        when(performanceTimeMapper.isPerfDateExists(performanceId, perfTimeId)).thenReturn(false);

        assertThrows(PerformanceTimeNotExistsException.class, () -> {
            performanceService.getPerfTimeAndRemainingSeats(performanceId, perfTimeId);
        });

        verify(performanceMapper, times(1)).isPerformanceIdExists(performanceId);
        verify(performanceTimeMapper, times(1)).isPerfDateExists(performanceId, perfTimeId);
    }
}
