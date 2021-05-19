package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.PerformanceNotExistsException;
import com.show.showticketingservice.exception.pick.PickAlreadyExistsException;
import com.show.showticketingservice.mapper.PickMapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class PickServiceTest {

    public static final int USER_NUM = 1;

    public static final int PERF_NUM = 11;

    @Mock
    private PickMapper pickMapper;

    @Mock
    private PerformanceService performanceService;

    @InjectMocks
    private PickService pickService;

    @Test
    @DisplayName("찜 목록 추가 성공")
    public void insertPick() {

        willDoNothing().given(performanceService).checkValidPerformanceId(PERF_NUM);
        given(pickMapper.isPickExists(USER_NUM, PERF_NUM)).willReturn(false);

        pickService.insertPick(USER_NUM, PERF_NUM);

        verify(pickMapper).insertPick(USER_NUM, PERF_NUM);
    }

    @Test
    @DisplayName("찜 추가 실패 - 해당 공연이 존재하지 않는 경우")
    public void insertPickWithInvalidPerf() {

        willThrow(PerformanceNotExistsException.class).given(performanceService).checkValidPerformanceId(PERF_NUM);

        assertThrows(PerformanceNotExistsException.class, () -> {
            pickService.insertPick(USER_NUM, PERF_NUM);
        });

        verify(pickMapper, never()).insertPick(USER_NUM, PERF_NUM);
    }

    @Test
    @DisplayName("찜 추가 실패 - 이미 추가된 공연을 중복 추가 하는 경우")
    public void insertPickWithDuplicatedPerf() {

        willDoNothing().given(performanceService).checkValidPerformanceId(PERF_NUM);
        given(pickMapper.isPickExists(USER_NUM, PERF_NUM)).willReturn(true);

        assertThrows(PickAlreadyExistsException.class, () -> {
            pickService.insertPick(USER_NUM, PERF_NUM);
        });

        verify(pickMapper, never()).insertPick(USER_NUM, PERF_NUM);
    }

    @Test
    @DisplayName("단일 찜 목록 삭제 성공")
    public void deletePick() {
        pickService.deletePick(USER_NUM, PERF_NUM);

        verify(pickMapper).deletePick(USER_NUM, PERF_NUM);
    }

    @Test
    @DisplayName("다중 찜 목록 삭제 성공")
    public void deletePicks() {
        Map<Integer, Integer> picks = new HashMap<>();
        picks.put(1, 1);
        picks.put(2, 2);
        picks.put(3, 3);
        picks.put(4, 4);

        List<Integer> perfIds = new ArrayList<>();
        perfIds.add(1);
        perfIds.add(2);

        willAnswer(invocationOnMock -> {
            perfIds.forEach(picks::remove);
            return null;
        }).given(pickMapper).deletePicks(USER_NUM, perfIds);

        pickService.deletePicks(USER_NUM, perfIds);

        verify(pickMapper).deletePicks(USER_NUM, perfIds);
        assertEquals(2, picks.size());
    }

    @Test
    @DisplayName("찜 목록 조회 성공 - 찜 목록은 공연ID 역순으로 반환")
    public void getPickList() {

        int perf1 = 1;
        int perf2 = 2;

        List<PerformanceResponse> pickList = new ArrayList<>();
        pickList.add(getPerformance(perf2));
        pickList.add(getPerformance(perf1));

        ShowType showType = null;
        Integer lastPerfId = null;
        PerformancePagingCriteria criteria = new PerformancePagingCriteria(lastPerfId);

        given(performanceService.getPickedPerformances(USER_NUM, showType, criteria)).willReturn(pickList);

        List<PerformanceResponse> pickListResult = pickService.getPicks(USER_NUM, showType, criteria);

        verify(performanceService).getPickedPerformances(USER_NUM, showType, criteria);
        assertEquals(2, pickListResult.size());
        assertEquals(pickList, pickListResult);
    }

    @Test
    @DisplayName("페이징 적용 찜 목록 조회 성공 - 커서 방식 페이징 / 1번 공연 이후의 찜 리스트 반환")
    public void getPickListWithPaging() {
        int perf1 = 1;
        int perf2 = 2;

        PerformanceResponse excludedPerfPick = getPerformance(perf1);

        List<PerformanceResponse> pickList = new ArrayList<>();
        pickList.add(getPerformance(perf2));
        pickList.add(excludedPerfPick);
        pickList.remove(excludedPerfPick);

        ShowType showType = null;
        Integer lastPerfId = 1;
        PerformancePagingCriteria criteria = new PerformancePagingCriteria(lastPerfId);

        given(performanceService.getPickedPerformances(USER_NUM, showType, criteria)).willReturn(pickList);

        List<PerformanceResponse> pickListResult = pickService.getPicks(USER_NUM, showType, criteria);

        verify(performanceService).getPickedPerformances(USER_NUM, showType, criteria);
        assertEquals(1, pickListResult.size());
        assertEquals(pickList, pickListResult);
    }

    private PerformanceResponse getPerformance(int id) {
        return PerformanceResponse.builder()
                .id(id)
                .venueName("테스트 공연장")
                .hallName("테스트 홀")
                .title("공연")
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