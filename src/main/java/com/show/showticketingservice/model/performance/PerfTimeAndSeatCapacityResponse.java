package com.show.showticketingservice.model.performance;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerfTimeAndSeatCapacityResponse {

    private int perfTimeId;

    private String startTime;

    List<SeatCapacityResponse> seatCapacityResponses;
}
