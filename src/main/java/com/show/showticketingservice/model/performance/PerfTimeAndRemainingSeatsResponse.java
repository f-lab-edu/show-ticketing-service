package com.show.showticketingservice.model.performance;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerfTimeAndRemainingSeatsResponse {

    private int perfTimeId;

    private String startTime;

    List<RemainingSeatsResponse> remainingSeatsResponses;
}
