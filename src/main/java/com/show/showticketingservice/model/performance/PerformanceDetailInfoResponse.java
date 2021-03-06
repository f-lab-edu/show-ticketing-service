package com.show.showticketingservice.model.performance;

import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.venue.VenueResponse;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerformanceDetailInfoResponse {

    private String title;

    private String detail;

    private int ageLimit;

    private ShowType showType;

    private String imageFilePath;

    private List<PerformanceTimeResponse> performanceTimeResponses;

    private VenueResponse venueResponse;

    private List<SeatPriceResponse> seatPriceResponses;

    private String hallName;
}
