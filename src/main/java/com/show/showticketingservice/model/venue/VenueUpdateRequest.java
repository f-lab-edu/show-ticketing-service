package com.show.showticketingservice.model.venue;

import com.show.showticketingservice.model.venueHall.VenueHallRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.Valid;
import java.util.List;

@Getter
@AllArgsConstructor
public class VenueUpdateRequest {

    @Valid
    private final Venue venue;

    @Valid
    private final List<VenueHallRequest> venueHallRequests;

    private final List<Integer> updateHallIds;

    private final List<Integer> deleteHallIds;

}
