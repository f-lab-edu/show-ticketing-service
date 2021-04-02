package com.show.showticketingservice.model.venue;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class VenueListResponse {

    private final int venueTotalPage;

    private final List<VenueResponse> venueResponseList;

}
