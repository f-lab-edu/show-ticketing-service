package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.criteria.PerformancePagingCriteria;
import com.show.showticketingservice.model.enumerations.AccessRoles;
import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.performance.PerformanceResponse;
import com.show.showticketingservice.model.pick.PickRequest;
import com.show.showticketingservice.model.user.UserSession;
import com.show.showticketingservice.service.PickService;
import com.show.showticketingservice.tool.annotation.CurrentUser;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @UserAuthenticationNecessary(role = AccessRoles.GENERAL)
    public List<PerformanceResponse> getPicks(@CurrentUser UserSession userSession,
                                              @RequestParam(value = "showType", required = false) ShowType showType,
                                              @RequestParam(value = "lastPerfId", required = false) Integer lastPerfId) {
        return pickService.getPicks(userSession.getUserId(), showType, new PerformancePagingCriteria(lastPerfId));
    }

}
