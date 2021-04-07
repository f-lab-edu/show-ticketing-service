package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.PerformanceAlreadyExistsException;
import com.show.showticketingservice.mapper.PerformanceMapper;
import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.performance.PerformanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceMapper performanceMapper;

    public void insertPerformance(PerformanceRequest performanceRequest) {
        checkPerformanceExists(performanceRequest.getTitle(), performanceRequest.getShowType());

        performanceMapper.insertPerformance(performanceRequest);
    }

    public void checkPerformanceExists(String title, ShowType showType) {
        if(performanceMapper.isPerformanceExists(title, showType)) {
            throw new PerformanceAlreadyExistsException();
        }
    }
}
