package com.show.showticketingservice.model.venue;

import com.show.showticketingservice.model.venueHall.VenueHallUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class VenueUpdateRequest {

    @Valid
    private final Venue venue;

    @Valid
    @NotNull
    private final List<VenueHallUpdateRequest> venueHallUpdateRequests;

    @NotNull
    private final List<Integer> deleteHallIds;

}
