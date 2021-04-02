package com.show.showticketingservice.model.showPlace;

import com.show.showticketingservice.model.venue.Venue;
import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import java.util.List;

@Getter
@AllArgsConstructor
public class ShowPlace {

    @Valid
    private final Venue venue;

    @Valid
    private final List<VenueHallRequest> venueHallRequests;

}
