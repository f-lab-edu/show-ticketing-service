package com.show.showticketingservice.model.venue;

import com.show.showticketingservice.model.venueHall.VenueHallUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.Valid;
import java.util.List;

@Getter
@AllArgsConstructor
public class VenueUpdateRequest {

    private final boolean isVenueInfoChanged;

    @Valid
    private final Venue venue;

    @Valid
    private final List<VenueHallUpdateRequest> venueHallUpdateRequests;

    private final List<Integer> deleteHallIds;

}
