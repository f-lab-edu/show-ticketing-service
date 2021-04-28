package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.enumerations.AccessRoles;
import com.show.showticketingservice.model.pick.PickRequest;
import com.show.showticketingservice.model.user.UserSession;
import com.show.showticketingservice.service.PickService;
import com.show.showticketingservice.tool.annotation.CurrentUser;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/picks")
public class PickController {

    private final PickService pickService;

    @PostMapping
    @UserAuthenticationNecessary(role = AccessRoles.GENERAL)
    public void insertPick(@CurrentUser UserSession userSession, @RequestBody PickRequest pickRequest) {
        pickService.insertPick(userSession.getUserId(), pickRequest.getPerformanceId());
    }

    @DeleteMapping("/{performanceId}")
    @UserAuthenticationNecessary(role = AccessRoles.GENERAL)
    public void deletePick(@CurrentUser UserSession userSession, @PathVariable int performanceId) {
        pickService.deletePick(userSession.getUserId(), performanceId);
    }
}
