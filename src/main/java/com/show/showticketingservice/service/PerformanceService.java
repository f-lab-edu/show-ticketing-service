package com.show.showticketingservice.service;

import com.show.showticketingservice.exception.performance.PerformanceAlreadyExistsException;
import com.show.showticketingservice.mapper.PerformanceMapper;
import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.performance.PerformanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    @Value("${spring.servlet.multipart.location}")
    private String fileUploadPath;

    private final PerformanceMapper performanceMapper;

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

        if(imagePath != null) {
            fileService.deleteFile(imagePath);
        }

        imagePath = fileService.registerPosterImage(image);
        performanceMapper.updatePerfImagePath(performanceId, imagePath);
    }

}
