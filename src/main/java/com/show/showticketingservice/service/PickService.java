package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.pick.PickAlreadyExistsException;
import com.show.showticketingservice.mapper.PickMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickService {

    private final UserService userService;

    private final PerformanceService performanceService;

    private final PickMapper pickMapper;

    public void insertPick(String userId, int performanceId) {

        performanceService.checkValidPerformanceId(performanceId);

        int userNum = userService.getUserNum(userId);

        checkPickDuplicated(userNum, performanceId);

        pickMapper.insertPick(userNum, performanceId);
    }

    private void checkPickDuplicated(int userNum, int performanceId) {
        if (pickMapper.isPickExists(userNum, performanceId)) {
            throw new PickAlreadyExistsException();
        }
    }

    public void deletePick(String userId, int performanceId) {

        int userNum = userService.getUserNum(userId);

        pickMapper.deletePick(userNum, performanceId);
    }
}
