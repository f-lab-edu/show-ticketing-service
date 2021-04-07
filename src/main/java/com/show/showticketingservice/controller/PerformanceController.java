package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.enumerations.AccessRoles;
import com.show.showticketingservice.model.performance.PerformanceRequest;
import com.show.showticketingservice.service.PerformanceService;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performances")
public class PerformanceController {

    private final PerformanceService performanceService;

    @PostMapping
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void insertPerformance(@RequestBody @Valid PerformanceRequest performanceRequest) {
        performanceService.insertPerformance(performanceRequest);
    }

}
