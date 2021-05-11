package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.pick.PickAlreadyExistsException;
import com.show.showticketingservice.mapper.PickMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PickService {

    private final PerformanceService performanceService;

    private final PickMapper pickMapper;

    @Transactional
    public void insertPick(int userId, int performanceId) {

        performanceService.checkValidPerformanceId(performanceId);

        checkPickDuplicated(userId, performanceId);

        pickMapper.insertPick(userId, performanceId);
    }

    private void checkPickDuplicated(int userNum, int performanceId) {
        if (pickMapper.isPickExists(userNum, performanceId)) {
            throw new PickAlreadyExistsException();
        }
    }

    @Transactional
    public void deletePick(int userId, int performanceId) {
        pickMapper.deletePick(userId, performanceId);
    }
}
