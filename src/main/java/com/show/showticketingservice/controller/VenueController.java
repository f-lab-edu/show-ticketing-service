package com.show.showticketingservice.controller;

import com.show.showticketingservice.model.venue.VenueRequest;
import com.show.showticketingservice.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/venue")
public class VenueController {

    private final VenueService venueService;

    @PostMapping
    public void insertVenue(@RequestBody @Valid VenueRequest venueRequest) {
        venueService.insertVenue(venueRequest);
    }

}
