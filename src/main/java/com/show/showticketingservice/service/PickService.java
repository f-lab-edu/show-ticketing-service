package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.pick.PickAlreadyExistsException;
import com.show.showticketingservice.mapper.PickMapper;
import com.show.showticketingservice.model.criteria.PerformancePagingCriteria;
import com.show.showticketingservice.model.performance.PerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PickService {

    private final PerformanceService performanceService;

    private final PickMapper pickMapper;

    public void insertPick(int userId, int performanceId) {

        performanceService.checkValidPerformanceId(performanceId);

        checkPickDuplicated(userId, performanceId);

        pickMapper.insertPick(userId, performanceId);
    }

    private void checkPickDuplicated(int userId, int performanceId) {
        if (pickMapper.isPickExists(userId, performanceId)) {
            throw new PickAlreadyExistsException();
        }
    }

    public void deletePick(int userId, int performanceId) {
        pickMapper.deletePick(userId, performanceId);
    }

    public List<PerformanceResponse> getPicks(int userId, PerformancePagingCriteria performancePagingCriteria) {
        return performanceService.getPickedPerformances(userId, performancePagingCriteria);
    }

}
