package com.show.showticketingservice.model.venue;

import com.show.showticketingservice.model.venueHall.VenueHallResponse;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VenueDetailInfoResponse {

    private VenueResponse venueResponse;

    private List<VenueHallResponse> venueHallResponses;
}
