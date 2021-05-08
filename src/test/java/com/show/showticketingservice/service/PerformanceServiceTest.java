package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.PerformanceNotExistsException;
import com.show.showticketingservice.mapper.PerformanceMapper;
import com.show.showticketingservice.model.criteria.PerformancePagingCriteria;
import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.performance.PerformancePeriod;
import com.show.showticketingservice.model.performance.PerformanceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PerformanceServiceTest {

    @Mock
    PerformanceMapper performanceMapper;

    @InjectMocks
    PerformanceService performanceService;

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
}
