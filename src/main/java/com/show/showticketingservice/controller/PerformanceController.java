package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.enumerations.AccessRoles;
import com.show.showticketingservice.model.performance.PerformanceRequest;
import com.show.showticketingservice.service.PerformanceService;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping("/{performanceId}/image")
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void updatePosterImage(@PathVariable int performanceId,  @RequestParam("image") MultipartFile image) {
        performanceService.updatePosterImage(performanceId, image);
    }

}
