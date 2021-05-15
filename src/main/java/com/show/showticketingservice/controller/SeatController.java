package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.enumerations.AccessRoles;
import com.show.showticketingservice.model.performance.SeatResponse;
import com.show.showticketingservice.service.SeatService;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    @UserAuthenticationNecessary(role = AccessRoles.GENERAL)
    public List<SeatResponse> getPerfSeats(@RequestParam int perfTimeId) {
        return seatService.getPerfSeats(perfTimeId);
    }
}
