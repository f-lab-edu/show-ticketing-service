package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.enumerations.AccessRoles;
import com.show.showticketingservice.model.showPlace.ShowPlace;
import com.show.showticketingservice.model.venue.VenueDetailInfoResponse;
import com.show.showticketingservice.model.venue.VenueListResponse;
import com.show.showticketingservice.model.venue.VenueUpdateRequest;
import com.show.showticketingservice.service.VenueService;
import com.show.showticketingservice.tool.annotation.UserAuthenticationNecessary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/venues")
public class VenueController {

    private final VenueService venueService;

    @PostMapping
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void insertVenue(@RequestBody @Valid ShowPlace showPlace) {
        venueService.insertVenue(showPlace.getVenueRequest(), showPlace.getVenueHallRequests());
    }

    @PutMapping("/{venueId}")
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void updateVenueInfo(@PathVariable int venueId, @RequestBody @Valid VenueUpdateRequest venueUpdateRequest) {
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

}
