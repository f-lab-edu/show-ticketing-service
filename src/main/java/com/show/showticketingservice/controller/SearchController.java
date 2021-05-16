package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.performance.PerformanceResponse;
import com.show.showticketingservice.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final PerformanceService performanceService;

    @GetMapping
    public List<PerformanceResponse> getPerformancesByKeyword(@RequestParam String keyword) {
        return performanceService.getPerformancesByKeyword(keyword);
    }

}
