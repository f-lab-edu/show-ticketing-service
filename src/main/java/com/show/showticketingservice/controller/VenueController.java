package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.enumerations.AccessRoles;
import com.show.showticketingservice.model.venue.Venue;
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
    public void insertVenue(@RequestBody @Valid Venue venue) {
        venueService.insertVenue(venue);
    }

    @PutMapping("/{venueId}")
    @UserAuthenticationNecessary(role = AccessRoles.MANAGER)
    public void updateVenueInfo(@PathVariable int venueId, @RequestBody @Valid Venue venueUpdateRequest) {
        venueService.updateVenueInfo(venueId, venueUpdateRequest);
    }

}
