package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.PerformanceAlreadyExistsException;
import com.show.showticketingservice.mapper.PerformanceMapper;
import com.show.showticketingservice.mapper.PerformanceTimeMapper;
import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.performance.PerformanceRequest;
import com.show.showticketingservice.model.performance.PerformanceTimeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    @Value("${spring.servlet.multipart.location}")
    private String fileUploadPath;

    private final PerformanceMapper performanceMapper;

    private final PerformanceTimeMapper performanceTimeMapper;

    private final FileService fileService;

    @Transactional
    public void insertPerformance(PerformanceRequest performanceRequest) {
        checkPerformanceExists(performanceRequest.getTitle(), performanceRequest.getShowType());
        performanceMapper.insertPerformance(performanceRequest);
    }

    public void checkPerformanceExists(String title, ShowType showType) {
        if (performanceMapper.isPerformanceExists(title, showType)) {
            throw new PerformanceAlreadyExistsException();
        }
    }

    public void updatePosterImage(int performanceId, MultipartFile image) {

        fileService.checkFileContentType(image);

        String imagePath = performanceMapper.getImagePath(performanceId);

        if (imagePath != null) {
            fileService.deleteFile(imagePath);
        }

        imagePath = fileService.registerPosterImage(image);
        performanceMapper.updatePerfImagePath(performanceId, imagePath);
    }

    public void insertPerformanceTimes(List<PerformanceTimeRequest> performanceTimeRequests, int performanceId) {

        // 시간이 겹치는 지 확인 필요

        performanceTimeMapper.insertPerformanceTimes(performanceTimeRequests, performanceId);
    }
}
