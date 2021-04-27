package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.enumerations.AccessRoles;
import com.show.showticketingservice.model.performance.PerformanceRequest;
import com.show.showticketingservice.model.performance.PerformanceTimeRequest;
import com.show.showticketingservice.model.performance.SeatPriceRequest;
import com.show.showticketingservice.model.performance.PerformanceUpdateRequest;
import com.show.showticketingservice.service.PerformanceService;
import com.show.showticketingservice.service.SeatPriceService;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/performances")
public class PerformanceController {

    private final PerformanceService performanceService;

    private final SeatPriceService seatPriceService;

    @PostMapping
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void insertPerformance(@RequestBody @Valid PerformanceRequest performanceRequest) {
        performanceService.insertPerformance(performanceRequest);
    }

    @PutMapping("/{performanceId}/image")
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void updatePosterImage(@PathVariable int performanceId, @RequestParam("image") MultipartFile image) {
        performanceService.updatePosterImage(performanceId, image);
    }

    @PostMapping("/{performanceId}/times")
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void insertPerfTime(@RequestBody @Valid List<PerformanceTimeRequest> performanceTimeRequests, @PathVariable int performanceId) {
        performanceService.insertPerformanceTimes(performanceTimeRequests, performanceId);
    }

    @PostMapping("/{performanceId}/prices")
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void insertPerfPrice(@RequestBody @Valid List<SeatPriceRequest> seatPriceRequests, @PathVariable int performanceId) {
        seatPriceService.insertSeatsPrice(seatPriceRequests, performanceId);
    }

    @PutMapping("/{performanceId}")
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void updatePerformanceInfo(@PathVariable int performanceId, @RequestBody @Valid PerformanceUpdateRequest perfUpdateRequest) {
        performanceService.updatePerformanceInfo(performanceId, perfUpdateRequest);
    }

    @DeleteMapping("/{performanceId}/times")
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void deletePerformanceTimes(@PathVariable int performanceId, @RequestBody List<Integer> timeIds) {
        performanceService.deletePerformanceTimes(performanceId, timeIds);
    }

    @DeleteMapping("/{performanceId}")
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void deletePerformance(@PathVariable int performanceId) {
        performanceService.deletePerformance(performanceId);
    }

}
