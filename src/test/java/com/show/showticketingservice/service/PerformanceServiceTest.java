package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.NoKeywordException;
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
import static org.mockito.BDDMockito.willAnswer;
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

    @Test
    @DisplayName("공연명에만 매칭되는 키워드를 통한 공연 검색 성공 (lastPerfId를 입력하지 않은 첫번째 페이지)")
    public void searchingPerformancesWithPerfName() {

        List<PerformanceResponse> perfList = new ArrayList<>();
        perfList.add(getPerformance(1, "공연1", "장소1"));
        perfList.add(getPerformance(2, "공연2", "장소2"));

        Integer lastPerfId = null;
        PerformancePagingCriteria criteria = new PerformancePagingCriteria(lastPerfId);

        String keyword = "공연";

        willAnswer(invocationOnMock -> {
            List<PerformanceResponse> perfResult = new ArrayList<>();

            perfList.forEach(p -> {
                if (p.getTitle().contains(keyword))
                    perfResult.add(p);
            });

            return perfResult;
        }).given(performanceMapper).getPerformancesByKeyword(keyword, criteria);

        List<PerformanceResponse> result = performanceService.getPerformancesByKeyword(keyword, criteria);

        verify(performanceMapper).getPerformancesByKeyword(keyword, criteria);
        assertEquals(perfList, result);
    }

    @Test
    @DisplayName("공연명 또는 공연장소에 매칭되는 키워드를 통한 공연 검색 성공 (lastPerfId를 입력하지 않은 첫번째 페이지)")
    public void searchingPerformancesWithVenueName() {

        List<PerformanceResponse> perfList = new ArrayList<>();
        perfList.add(getPerformance(1, "공연1", "장소1"));
        perfList.add(getPerformance(2, "공연2", "장소2"));
        perfList.add(getPerformance(3, "공연3", "공연장"));
        perfList.add(getPerformance(4, "perf", "ven"));

        Integer lastPerfId = 3;
        PerformancePagingCriteria criteria = new PerformancePagingCriteria(lastPerfId);

        String keyword = "공연";

        willAnswer(invocationOnMock -> {
            List<PerformanceResponse> perfResult = new ArrayList<>();

            perfList.forEach(p -> {
                if (p.getTitle().contains(keyword) || p.getVenueName().contains(keyword))
                    perfResult.add(p);
            });

            return perfResult;
        }).given(performanceMapper).getPerformancesByKeyword(keyword, criteria);

        List<PerformanceResponse> result = performanceService.getPerformancesByKeyword(keyword, criteria);

        verify(performanceMapper).getPerformancesByKeyword(keyword, criteria);
        assertEquals(3, result.size());

        perfList.remove(3);
        assertEquals(perfList, result);
    }

    @Test
    @DisplayName("공연명 키워드와 paging 적용을 위한 lastPerfId를 통한 공연 검색 성공 (id가 3보다 작은 공연만 반환. 결과는 id 역순)")
    public void searchingPerformancesWithPerfNameAndPaging() {

        List<PerformanceResponse> perfList = new ArrayList<>();
        perfList.add(getPerformance(1, "공연1", "장소1"));
        perfList.add(getPerformance(2, "공연2", "장소2"));
        perfList.add(getPerformance(3, "공연3", "공연장"));

        Integer lastPerfId = 3;
        PerformancePagingCriteria criteria = new PerformancePagingCriteria(lastPerfId);

        String keyword = "공연";

        willAnswer(invocationOnMock -> {
            List<PerformanceResponse> perfResult = new ArrayList<>();

            perfList.forEach(p -> {
                if (p.getId() < lastPerfId &&
                        (p.getTitle().contains(keyword) || p.getVenueName().contains(keyword)))
                    perfResult.add(p);
            });

            return perfResult;
        }).given(performanceMapper).getPerformancesByKeyword(keyword, criteria);

        List<PerformanceResponse> result = performanceService.getPerformancesByKeyword(keyword, criteria);

        verify(performanceMapper).getPerformancesByKeyword(keyword, criteria);
        assertEquals(2, result.size());

        perfList.remove(2);
        assertEquals(perfList, result);
    }

    @Test
    @DisplayName("빈 키워드로 공연 검색 시 실패")
    public void searchingPerformancesWithBlankKeyword() {

        String keyword = "  ";

        PerformancePagingCriteria criteria = new PerformancePagingCriteria(null);

        assertThrows(NoKeywordException.class, () -> {
            performanceService.getPerformancesByKeyword(keyword, criteria);
        });

        verify(performanceMapper, times(0)).getPerformancesByKeyword(keyword, criteria);
    }

    private PerformanceResponse getPerformance(int id, String title, String VenueName) {
        return PerformanceResponse.builder()
                .id(id)
                .venueName(VenueName)
                .hallName("테스트 홀")
                .title(title)
                .imageFilePath(null)
                .performancePeriod(getPerfPeriod())
                .build();
    }

    private PerformancePeriod getPerfPeriod() {
        return PerformancePeriod.builder()
                .firstDay("2021-05-01")
                .lastDay("2021-05-31")
                .build();
    }

}
