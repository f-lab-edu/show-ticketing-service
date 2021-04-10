package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.enumerations.AccessRoles;
import com.show.showticketingservice.model.showPlace.ShowPlace;
import com.show.showticketingservice.model.venue.VenueDetailInfoResponse;
import com.show.showticketingservice.model.venue.VenueListResponse;
import com.show.showticketingservice.model.venue.VenueRequest;
import com.show.showticketingservice.model.venue.VenueResponse;
import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import com.show.showticketingservice.model.venueHall.VenueHallResponse;
import com.show.showticketingservice.service.VenueHallService;
import com.show.showticketingservice.service.VenueService;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/venues")
public class VenueController {

    private final VenueService venueService;

    private final VenueHallService venueHallService;

    @PostMapping
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void insertVenue(@RequestBody @Valid ShowPlace showPlace) {
        venueService.insertVenue(showPlace.getVenueRequest(), showPlace.getVenueHallRequests());
    }

    @PutMapping("/{venueId}")
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void updateVenueInfo(@PathVariable int venueId, @RequestBody @Valid VenueRequest venueUpdateRequest) {
        venueService.updateVenueInfo(venueId, venueUpdateRequest);
    }

    @DeleteMapping("/{venueId}")
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void deleteVenue(@PathVariable int venueId) {
        venueService.deleteVenue(venueId);
    }

    @GetMapping
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public VenueListResponse getVenueList(@RequestParam(value = "page", defaultValue = "1") int page) {
        return venueService.getVenueList(page);
    }

    @GetMapping("/{venueId}")
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public VenueDetailInfoResponse getVenueInfo(@PathVariable int venueId) {
        return venueService.getVenueInfo(venueId);
    }

    @PutMapping("{venueId}/halls/{hallId}")
    public void updateVenueHall(@RequestBody @Valid VenueHallRequest venueHallRequest,
                                @PathVariable String venueId,
                                @PathVariable String hallId) {

        venueHallService.updateVenueHall(venueHallRequest, venueId, hallId);
    }

    @DeleteMapping("{venueId}/halls")
    public void deleteVenueHalls(@PathVariable String venueId, @RequestBody List<String> hallIds) {
        venueHallService.deleteVenueHalls(venueId, hallIds);
    }

}
