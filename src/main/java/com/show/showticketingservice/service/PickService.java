package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.pick.PickAlreadyExistsException;
import com.show.showticketingservice.mapper.PickMapper;
import com.show.showticketingservice.model.criteria.PerformancePagingCriteria;
import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.performance.PerformanceResponse;
import com.show.showticketingservice.tool.constants.CacheConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PickService {

    private final PerformanceService performanceService;

    private final PickMapper pickMapper;

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheConstant.MAIN_PICK_LIST),
            @CacheEvict(cacheNames = CacheConstant.TYPED_PICK_LIST, allEntries = true)
    })
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

    @Caching(evict = {
            @CacheEvict(cacheNames = CacheConstant.MAIN_PICK_LIST),
            @CacheEvict(cacheNames = CacheConstant.TYPED_PICK_LIST, allEntries = true)
    })
    public void deletePick(int userId, int performanceId) {
        pickMapper.deletePick(userId, performanceId);
    }

    @Transactional(readOnly = true)
    @Caching(cacheable = {
            @Cacheable(
                    cacheNames = CacheConstant.MAIN_PICK_LIST,
                    condition = "#showType == null && #performancePagingCriteria.lastPerfId == null"),
            @Cacheable(
                    cacheNames = CacheConstant.TYPED_PICK_LIST,
                    condition = "#showType != null && #performancePagingCriteria.lastPerfId == null",
                    key = "#showType.toString()"),
    })
    public List<PerformanceResponse> getPicks(int userId, ShowType showType, PerformancePagingCriteria performancePagingCriteria) {
        return performanceService.getPickedPerformances(userId, showType, performancePagingCriteria);
    }

}
