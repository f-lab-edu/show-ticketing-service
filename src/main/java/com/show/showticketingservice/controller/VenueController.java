package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.venue.Venue;
import com.show.showticketingservice.model.venueHall.VenueHall;
import com.show.showticketingservice.service.VenueHallService;
import com.show.showticketingservice.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/venue")
public class VenueController {

    private final VenueService venueService;

    private final VenueHallService venueHallService;

    @PostMapping
    public void insertVenue(@RequestBody @Valid Venue venue) {
        venueService.insertVenue(venue);
    }

    @PostMapping("{venueId}/halls")
    public void insertVenueHall(@RequestBody @Valid List<VenueHall> venueHalls, @PathVariable String venueId) {
        venueHallService.insertVenueHall(venueHalls, venueId);
    }

}
