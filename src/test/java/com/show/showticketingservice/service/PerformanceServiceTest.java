package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.NoKeywordException;
import com.show.showticketingservice.exception.performance.PerformanceNotExistsException;
import com.show.showticketingservice.exception.performance.PerformanceTimeNotExistsException;
import com.show.showticketingservice.mapper.PerformanceMapper;
import com.show.showticketingservice.mapper.PerformanceTimeMapper;
import com.show.showticketingservice.model.criteria.PerformancePagingCriteria;
import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.performance.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PerformanceServiceTest {

    @Mock
    PerformanceMapper performanceMapper;

    @Mock
    PerformanceTimeMapper performanceTimeMapper;

    @InjectMocks
    PerformanceService performanceService;

    @Nested
    @DisplayName("공연 리스트 조회 테스트")
    class GetPerformancesTest {

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

    @Nested
    @DisplayName("공연 검색 테스트 - getPerformancesByKeyword()")
    class SearchingPerformances {

        private final List<PerformanceResponse> perfList = new ArrayList<>();

        private PerformancePagingCriteria criteria;

        @BeforeEach
        public void commonInit() {
            perfList.add(getPerformance(1, "공연1", "장소1"));
            perfList.add(getPerformance(2, "공연2", "장소2"));
            perfList.add(getPerformance(3, "공연3", "공연장"));
            perfList.add(getPerformance(4, "perf1", "ven1"));
            perfList.add(getPerformance(5, "perf2", "ven2"));
        }

        @Nested
        @DisplayName("검색 keyword만 입력했을 때 (페이징 미적용)")
        class SearchWithKeyword {

            @BeforeEach
            public void setPagingCriteria() {
                criteria = new PerformancePagingCriteria(null);
            }

            @Test
            @DisplayName("keyword가 공연명에만 매칭되었을 때 매칭된 공연 리스트 반환 성공")
            public void matchOnlyWithPerfName() {
                String keyword = "perf";

                willAnswer(invocationOnMock -> {
                    return searching(keyword);
                }).given(performanceMapper).getPerformancesByKeyword(keyword, criteria);

                List<PerformanceResponse> result = performanceService.getPerformancesByKeyword(keyword, criteria);

                verify(performanceMapper).getPerformancesByKeyword(keyword, criteria);
                assertEquals(2, result.size());
            }

            @Test
            @DisplayName("keyword가 공연명 또는 공연장소에 매칭되었을 때 매칭된 모든 공연 리스트 반환 성공")
            public void matchWithPerfNameOrVenueName() {
                String keyword = "공연";

                willAnswer(invocationOnMock -> {
                    return searching(keyword);
                }).given(performanceMapper).getPerformancesByKeyword(keyword, criteria);

                List<PerformanceResponse> result = performanceService.getPerformancesByKeyword(keyword, criteria);

                verify(performanceMapper).getPerformancesByKeyword(keyword, criteria);
                assertEquals(3, result.size());
            }

            @Test
            @DisplayName("keyword가 공연명과 공연장소 모두 매칭되지 않을 때 빈 리스트 반환")
            public void noMatchWithPerfNameNeitherVenueName() {
                String keyword = "명성황후";

                willAnswer(invocationOnMock -> {
                    return searching(keyword);
                }).given(performanceMapper).getPerformancesByKeyword(keyword, criteria);

                List<PerformanceResponse> result = performanceService.getPerformancesByKeyword(keyword, criteria);

                verify(performanceMapper).getPerformancesByKeyword(keyword, criteria);
                assertEquals(0, result.size());
            }

            @Test
            @DisplayName("아무것도 입력되지 않은 빈(공백) keyword로 검색 시 실패 - NoKeywordException 예외")
            public void searchWithBlankKeyword() {

                String keyword = "  ";

                assertThrows(NoKeywordException.class, () -> {
                    performanceService.getPerformancesByKeyword(keyword, criteria);
                });

                verify(performanceMapper, times(0)).getPerformancesByKeyword(keyword, criteria);
            }

            public List<PerformanceResponse> searching(String keyword) {

                List<PerformanceResponse> perfResult = new ArrayList<>();

                perfList.forEach(p -> {
                    if (p.getTitle().contains(keyword) || p.getVenueName().contains(keyword))
                        perfResult.add(p);
                });

                return perfResult;
            }

        }

        @Nested
        @DisplayName("검색 keyword와 lastPerfId를 입력했을 때 (커서 페이징 적용)")
        class SearchWithKeywordAndPagingCriteria {

            @Test
            @DisplayName("공연명 또는 공연장명과 매칭되는 공연 검색 성공 (결과는 id 역순으로 반환되므로 id가 lastPerfId 보다 작은 공연만 반환)")
            public void searchingPerformancesWithPerfNameAndPaging() {

                Integer lastPerfId = 3;
                criteria = new PerformancePagingCriteria(lastPerfId);

                String keyword = "공연";

                willAnswer(invocationOnMock -> {
                    return searching(keyword, lastPerfId);
                }).given(performanceMapper).getPerformancesByKeyword(keyword, criteria);

                List<PerformanceResponse> result = performanceService.getPerformancesByKeyword(keyword, criteria);

                verify(performanceMapper).getPerformancesByKeyword(keyword, criteria);
                assertEquals(2, result.size());
            }

            private List<PerformanceResponse> searching(String keyword, Integer lastPerfId) {

                List<PerformanceResponse> perfResult = new ArrayList<>();

                perfList.forEach(p -> {
                    if (p.getId() < lastPerfId &&
                            (p.getTitle().contains(keyword) || p.getVenueName().contains(keyword)))
                        perfResult.add(p);
                });

                return perfResult;
            }

        }

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

        List<RemainingSeatsResponse> remainingSeatsResponses = new ArrayList<>();
        RemainingSeatsResponse remainingSeatsResponse = RemainingSeatsResponse.builder()
                .ratingType(VIP)
                .remainingSeats(20)
                .build();
        remainingSeatsResponses.add(remainingSeatsResponse);

        PerfTimeAndRemainingSeatsResponse perfTimeAndRemainingSeatsResponse = PerfTimeAndRemainingSeatsResponse.builder()
                .perfTimeId(1)
                .startTime("12:00:00")
                .remainingSeatsResponses(remainingSeatsResponses)
                .build();

        when(performanceTimeMapper.getPerfTimeAndRemainingSeats(performanceId, perfTimeId)).thenReturn(perfTimeAndRemainingSeatsResponse);
        when(performanceMapper.isPerformanceIdExists(performanceId)).thenReturn(true);
        when(performanceTimeMapper.isPerfDateExists(performanceId, perfTimeId)).thenReturn(true);

        PerfTimeAndRemainingSeatsResponse resultPerfTimeAndRemainingSeatsResponse = performanceService.getPerfTimeAndRemainingSeats(performanceId, perfTimeId);

        verify(performanceTimeMapper, times(1)).getPerfTimeAndRemainingSeats(performanceId, perfTimeId);
        verify(performanceMapper, times(1)).isPerformanceIdExists(performanceId);
        verify(performanceTimeMapper, times(1)).isPerfDateExists(performanceId, perfTimeId);

        assertEquals(perfTimeAndRemainingSeatsResponse, resultPerfTimeAndRemainingSeatsResponse);
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
